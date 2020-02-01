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

%yylexthrow{
	cyr7.exceptions.InvalidCharacterLiteralException, 
	cyr7.exceptions.InvalidStringEscapeCharacterException,
	cyr7.exceptions.LeadingZeroIntegerException
%yylexthrow}

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
        
        Token(TokenType tt, Object attr, int lineNumber, int col) {
            type = tt; attribute = attr;
            line = lineNumber;
            column = col;
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

	public class LexerStringBuffer {
		private StringBuffer buffer;
		private int lineNumber;
		private int columnNumber;
		
		public LexerStringBuffer() {
			buffer = new StringBuffer();
			lineNumber = -1;
			columnNumber = -1;
		}
		
		private void clear() {
			buffer.delete(0, buffer.length());
			lineNumber = -1;
			columnNumber = -1;
		}
		
		public void init(int line, int column) {
			this.clear();
			lineNumber = line;
			columnNumber = column;
		}
		
		public void append(String s) {
			buffer.append(s);
		}
		
		public int getLineNumber() {
			return lineNumber;
		}
		
		public int getColumnNumber() {
			return columnNumber;
		}
		
		public Token generateToken(TokenType type) {
			return new Token(type, buffer.toString(), lineNumber, columnNumber);
		}
		
	}
	
    private LexerStringBuffer stringBuffer = new LexerStringBuffer();
    private LexerStringBuffer charBuffer = new LexerStringBuffer();
    
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
    {Integer}           { return new Token(TokenType.INT_LITERAL, yytext()); }
    0{Integer}+			{ throw new cyr7.exceptions.LeadingZeroIntegerException(yytext(), yyline, yycolumn); }
    \'		            { charBuffer.init(yyline, yycolumn); yybegin(CHARACTER); }
    \"                  { stringBuffer.init(yyline, yycolumn); yybegin(STRING); }

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
	\n|\r|\r\n			{ yybegin(YYINITIAL); }
	.					{ /* IGNORE */ }
}

<CHARACTER> {
    \'					{yybegin(YYINITIAL); throw new cyr7.exceptions.InvalidCharacterLiteralException("'" + yytext(), charBuffer.getLineNumber(), charBuffer.getColumnNumber());}
    [\u0000-\uFFFF]     {yybegin(CHAR_END); charBuffer.append(yytext()); }
    \\n				    {yybegin(CHAR_END); charBuffer.append("\n"); }
    \\t					{yybegin(CHAR_END); charBuffer.append("\t"); }
    \\r					{yybegin(CHAR_END); charBuffer.append("\r"); }
    \\f					{yybegin(CHAR_END); charBuffer.append("\f"); }
    {Hex}				{yybegin(CHAR_END); charBuffer.append(fromHex(yytext())); }
    \\'					{yybegin(CHAR_END); charBuffer.append("'"); }
    \\\"				{yybegin(CHAR_END); charBuffer.append("\""); }
    \\\\				{yybegin(CHAR_END); charBuffer.append("\\"); }
    /* More than one character inside single quote string OR no characters */
    \\[^]				{yybegin(CHAR_END); throw new cyr7.exceptions.InvalidCharacterLiteralException(yytext(), charBuffer.getLineNumber(), charBuffer.getColumnNumber());}  /*Invalid escape characters*/
}

<CHAR_END> {
	\'					{yybegin(YYINITIAL); return charBuffer.generateToken(TokenType.CHAR_LITERAL); }
	[^\']				{yybegin(YYINITIAL); throw new cyr7.exceptions.InvalidCharacterLiteralException("'" + yytext(), charBuffer.getLineNumber(), charBuffer.getColumnNumber());}
}

<STRING> {
    \"                  {yybegin(YYINITIAL); return stringBuffer.generateToken(TokenType.STRING_LITERAL); }
    \\n				    {stringBuffer.append("\n");}
    \\t					{stringBuffer.append("\t");}
    \\r					{stringBuffer.append("\r");}
    \\f					{stringBuffer.append("\f");}
    {Hex}				{stringBuffer.append(fromHex(yytext())); }
    \\'					{stringBuffer.append("'");}
    \\\"				{stringBuffer.append("\"");}
    \\\\				{stringBuffer.append("\\");} 
    \\[^]				{throw new cyr7.exceptions.InvalidStringEscapeCharacterException(yytext(), stringBuffer.getLineNumber(), stringBuffer.getColumnNumber());}
    [^\n\f\t\r\"\\]+    {stringBuffer.append(yytext()); }
}