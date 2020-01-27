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
        DOT,

    }

    class Token {

        TokenType type;
        Object attribute;
        final int line, column;

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

    StringBuffer stringBuffer = new StringBuffer();
%}

Whitespace = [ \t\f\r\n]
Letter = [a-zA-Z]
Digit = [0-9]
Identifier = {Letter}({Digit}|{Letter}|_|')*
Integer = "0"|"-"?[1-9]{Digit}*
Character = "'"[\u0000-\uFFFF]"'"

%state STRING

%%

<YYINITIAL> {
    {Whitespace}        { /* IGNORE */ }

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
    {Character}         { return new Token(TokenType.CHAR_LITERAL, yytext()); }
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

<STRING> {
    \"                  { yybegin(YYINITIAL); return new Token(TokenType.STRING_LITERAL, stringBuffer.toString()); }
    [^\n\r\"\\]+        { stringBuffer.append( yytext() ); }
    \\t                 { stringBuffer.append('\t'); }
    \\n                 { stringBuffer.append('\n'); }
    \\'                 { stringBuffer.append('\''); }
    \\\"                { stringBuffer.append('\"'); }
    \\                  { stringBuffer.append('\\'); }
}
