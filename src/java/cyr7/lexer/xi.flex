%%

%public
%class MyLexer
%type Token
%function nextToken

%unicode
%pack
%line
%column

%{
    enum TokenType {
	IF,
	ID,
	INT,
	FLOAT,
	DOT
    }
    class Token {
	TokenType type;
	Object attribute;
	final int lineNumber, columnNumber;
	Token(TokenType tt) {
	    type = tt; attribute = null;
	    lineNumber = yyline;
	    columnNumber = yycolumn;
	}
	Token(TokenType tt, Object attr) {
	    type = tt; attribute = attr;
	    lineNumber = yyline;
	    columnNumber = yycolumn;
	}
	public String toString() {
	    return "" + type + "(" + attribute + ")";
	}
    }
%}

Whitespace = [ \t\f\r\n]
Letter = [a-zA-Z]
Digit = [0-9]
Identifier = {Letter}({Digit}|{Letter}|_)*
Integer = "0"|"-"?[1-9]{Digit}*
Float = {Digit}+ "." {Digit}+

%%

{Whitespace}  { /* ignore */ }
"if"          { return new Token(TokenType.IF); }
{Identifier}  { return new Token(TokenType.ID, yytext()); }
{Integer}     { return new Token(TokenType.INT,
				 Integer.parseInt(yytext())); }
{Float}       { return new Token(TokenType.FLOAT,
                                 Float.parseFloat(yytext())); }
"."           { return new Token(TokenType.DOT); }

