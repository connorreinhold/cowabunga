package cyr7.lexer.ixi;

import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.Location;
import cyr7.parser.ixi.sym;

%%
%public
%class IxiLexer
%unicode
%cup

%line
%column

%yylexthrow{
	cyr7.exceptions.InvalidTokenException
%yylexthrow}


%{
    ComplexSymbolFactory symbolFactory;

    public IxiLexer(java.io.Reader in, ComplexSymbolFactory sf) {
	    this(in);
	    symbolFactory = sf;
    }

    private Symbol symbol(int id) {
    	String name = sym.terminalNames[id];
        return symbolFactory.newSymbol(name, id, 
            new Location(yyline+1,yycolumn+1,yychar),
            new Location(yyline+1,yycolumn+yylength(),yychar+yylength()));
    }
    
   
    private Symbol symbol(int id, Object val) {
    	String name = sym.terminalNames[id];
        Location left = new Location(yyline+1,yycolumn+1,yychar);
        Location right = new Location(yyline+1,
        						      yycolumn+yylength(), 
        							  yychar+yylength());
        							  
        return symbolFactory.newSymbol(name, id, left, right, val);
    }
    
%}

%eofval{
    return symbol(sym.EOF);
%eofval}
%eofclose

LineEnd = \n|\r|\r\n
Whitespace = [ \t\f\r\n]
Letter = [a-zA-Z]
Digit = [0-9]
Identifier = {Letter}({Digit}|{Letter}|_|')*

%state COMMENT

%%

<YYINITIAL> {
    {Whitespace}        { /* IGNORE */ }
    "//"				{ yybegin(COMMENT); }
    
    "int"               { return symbol(sym.TYPE_INT); }
    "bool"              { return symbol(sym.TYPE_BOOL); }

    {Identifier}        { return symbol(sym.ID, yytext()); }

    "("                 { return symbol(sym.L_PAREN); }
    ")"                 { return symbol(sym.R_PAREN); }
    "["                 { return symbol(sym.L_SQ_BRKT); }
    "]"                 { return symbol(sym.R_SQ_BRKT); }
    ":"                 { return symbol(sym.COLON); }
    ";"                 { return symbol(sym.SEMICOLON); }
    ","                 { return symbol(sym.COMMA); }
    
    .					
    	{ 
    		throw new cyr7.exceptions.InvalidTokenException(yytext(), 
    														yyline, 
    														yycolumn); 
		}
}

<COMMENT> {
	{LineEnd}			{ yybegin(YYINITIAL); }
	.					{ /* IGNORE */ }
}
[^]
    {
        throw new cyr7.exceptions.InvalidTokenException(
            yytext(),
            yyline,
            yycolumn
        );
    }
