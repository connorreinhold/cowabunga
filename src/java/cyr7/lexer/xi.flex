%%

%public
%class MyLexer
%public
%type Token
%public
%function nextToken

%unicode
%pack
%line
%column

%{
    public enum TokenType {

        // keywords
        IF,
        ELSE,
        RETURN,
        USE,
        WHILE,
        LENGTH,

        // primitives
        TYPE_INT,
        TYPE_BOOL,

        // literals
        INT_LITERAL,
        CHAR_LITERAL,
        STRING_LITERAL,
        BOOL_LITERAL,

        // punctuation
        L_PAREN,
        R_PAREN,
        L_SQ_BRKT,
        R_SQ_BRKT,
        L_BRACE,
        R_BRACE,
        COLON,
        SEMICOLON,
        COMMA,
        UNDERSCORE,

        // operators
        ASSIGN, // =
        PLUS,
        MINUS,
        MULT,
        HIGH_MULT, // *>>
        DIVIDE,
        EQUALS, // ==
        NOT_EQUALS, // !=
        LT, // <
        LTE, // <=
        GT, // >
        GTE, // >=
        NEG_BOOL, // !
        REMAINDER, // %
        LOGICAL_AND, // &
        LOGICAL_OR, // |

        // misc
        ID,

    }

    public class Token {

        final TokenType type;
        final Object attribute;
        final public int line, column;

        Token(TokenType tt) {
            type = tt; attribute = null;
            line = yyline;
            column = yycolumn;
        }

        Token(TokenType tt, Object attr) {
            type = tt; attribute = attr;
            line = yyline;
            column = yycolumn;
        }

        public String toString() {
            return "" + type + "(" + attribute + ")";
        }

    }
    public String fromHex(String hex) {
    	hex = hex.substring(2);
    	int hexVal = Integer.parseInt(hex, 16);
    	return ""+(char)hexVal;
    }

    private StringBuffer stringBuffer = new StringBuffer();
    
%}

Whitespace = [ \t\f\r\n]
Letter = [a-zA-Z]
Digit = [0-9]
Identifier = {Letter}({Digit}|{Letter}|_|')*
Integer = "0"|[1-9]{Digit}*
Hex = \\x(([(a-f|A-F)0-9]){1,4})

%state STRING
%state COMMENT
%state CHARACTER
%state CHAR_END

%%

<YYINITIAL> {
    {Whitespace}        { /* IGNORE */ }
    "//"				{ yybegin(COMMENT); }
    
    "use"               { return new Token(TokenType.USE); }
    "if"                { return new Token(TokenType.IF); }
    "while"             { return new Token(TokenType.WHILE); }
    "else"              { return new Token(TokenType.ELSE); }
    "return"            { return new Token(TokenType.RETURN); }
    "length"            { return new Token(TokenType.LENGTH); }

    "int"               { return new Token(TokenType.TYPE_INT); }
    "bool"              { return new Token(TokenType.TYPE_BOOL); }

    "true"              { return new Token(TokenType.BOOL_LITERAL, true); }
    "false"             { return new Token(TokenType.BOOL_LITERAL, false); }
    {Integer}           { return new Token(TokenType.INT_LITERAL, Integer.parseInt(yytext())); }
    \'		            { yybegin(CHARACTER); }
    \"                  { stringBuffer.delete(0, stringBuffer.length()); yybegin(STRING); }

    {Identifier}        { return new Token(TokenType.ID, yytext()); }

    "("                 { return new Token(TokenType.L_PAREN); }
    ")"                 { return new Token(TokenType.R_PAREN); }
    "["                 { return new Token(TokenType.L_SQ_BRKT); }
    "]"                 { return new Token(TokenType.R_SQ_BRKT); }
    "{"                 { return new Token(TokenType.L_BRACE); }
    "}"                 { return new Token(TokenType.R_BRACE); }
    ":"                 { return new Token(TokenType.COLON); }
    ";"                 { return new Token(TokenType.SEMICOLON); }
    ","                 { return new Token(TokenType.COMMA); }
    "_"                 { return new Token(TokenType.UNDERSCORE); }

    "="                 { return new Token(TokenType.ASSIGN); }

    "+"                 { return new Token(TokenType.PLUS); }
    "-"                 { return new Token(TokenType.MINUS); }
    "*"                 { return new Token(TokenType.MULT); }
    "*>>"               { return new Token(TokenType.HIGH_MULT); }
    "/"                 { return new Token(TokenType.DIVIDE); }
    "%"                 { return new Token(TokenType.REMAINDER); }

    "!"                 { return new Token(TokenType.NEG_BOOL); }

    "<"                 { return new Token(TokenType.LT); }
    "<="                { return new Token(TokenType.LTE); }
    ">"                 { return new Token(TokenType.GT); }
    ">="                { return new Token(TokenType.GTE); }

    "=="                { return new Token(TokenType.EQUALS); }
    "!="                { return new Token(TokenType.NOT_EQUALS); }

    "&"                 { return new Token(TokenType.LOGICAL_AND); }
    "|"                 { return new Token(TokenType.LOGICAL_OR); }

}

<COMMENT> {
	"\n"				{ yybegin(YYINITIAL); }
	.					{ /* IGNORE */ }
}

<CHARACTER> {
    [\u0000-\uFFFF]     {yybegin(CHAR_END); return new Token(TokenType.CHAR_LITERAL, yytext()); }
    \\n				    {yybegin(CHAR_END); return new Token(TokenType.CHAR_LITERAL, "\n");}
    \\t					{yybegin(CHAR_END); return new Token(TokenType.CHAR_LITERAL, "\t");}
    \\r					{yybegin(CHAR_END); return new Token(TokenType.CHAR_LITERAL, "\r");}
    \\f					{yybegin(CHAR_END); return new Token(TokenType.CHAR_LITERAL, "\f");}
    {Hex}				{yybegin(CHAR_END); return new Token(TokenType.CHAR_LITERAL, fromHex(yytext())); }
    \\'					{yybegin(CHAR_END); return new Token(TokenType.CHAR_LITERAL, "'");}
    \\\"				{yybegin(CHAR_END); return new Token(TokenType.CHAR_LITERAL, "\"");}
    \\\\				{yybegin(CHAR_END); return new Token(TokenType.CHAR_LITERAL, "\\");} 
}

<CHAR_END> {
	\'					{yybegin(YYINITIAL);}
}

<STRING> {
    \"                  { yybegin(YYINITIAL); return new Token(TokenType.STRING_LITERAL, stringBuffer.toString()); }
    \\n				    {stringBuffer.append("\n");}
    \\t					{stringBuffer.append("\t");}
    \\r					{stringBuffer.append("\r");}
    \\f					{stringBuffer.append("\f");}
    {Hex}				{stringBuffer.append(fromHex(yytext())); }
    \\'					{stringBuffer.append("'");}
    \\\"				{stringBuffer.append("\"");}
    \\\\				{stringBuffer.append("\\");} 
    [^\n\f\t\r\"\\]+        { stringBuffer.append( yytext() ); }
}