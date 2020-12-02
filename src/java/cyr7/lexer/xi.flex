package cyr7.lexer;

import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import cyr7.parser.sym;
import java.math.BigInteger;

%%
%public
%class MyLexer
%unicode
%cup

%function next_token
%type java_cup.runtime.ComplexSymbolFactory.ComplexSymbol

%pack
%line
%column

%yylexthrow{
   cyr7.exceptions.lexer.LexerException
%yylexthrow}

%{

    private String filename;

    public MyLexer(java.io.Reader in, String filename) {
        this(in);
        this.filename = filename;
    }

    public final static String maxIntegerString = "9223372036854775808";
    public final static BigInteger maxInteger = new BigInteger(maxIntegerString); // 2^63

    protected ComplexSymbol symbol(int id) {
       String name = sym.terminalNames[id];
        return new ComplexSymbol(name, id,
            new Location(filename, yyline+1,yycolumn+1,(int)yychar),
            new Location(filename, yyline+1,yycolumn+yylength(),(int)yychar+yylength()));
    }

    protected ComplexSymbol symbol(int id, Object val) {
       String name = sym.terminalNames[id];
        Location left = new Location(filename, yyline+1,yycolumn+1,(int)yychar);
        Location right = new Location(filename, yyline+1,
                                yycolumn+yylength(),
                               (int)yychar+yylength());

        return new ComplexSymbol(name, id, left, right, val);
    }

    protected ComplexSymbol symbol(int id, String val, int line, int col) {
       String name = sym.terminalNames[id];
        Location left = new Location(filename, line+1,col+1);
        Location right = new Location(filename, line + 1, col + 1 + val.length());

        return new ComplexSymbol(name, id, left, right, val);
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

      public ComplexSymbol generateSymbol(int id) {
         return symbol(id, buffer.toString(), lineNumber, columnNumber);
      }

      public String toString() {
         return buffer.toString();
      }

   }

    private LexerStringBuffer stringBuffer = new LexerStringBuffer();
    private LexerStringBuffer charBuffer = new LexerStringBuffer();

%}

%eofval{
    return symbol(sym.EOF, "EOF", yyline, yycolumn);
%eofval}

LineEnd = \n|\r|\r\n
Whitespace = [ \t\f\r\n]
Newline = [\r\n]
Letter = [a-zA-Z]
Digit = [0-9]
Identifier = {Letter}({Digit}|{Letter}|_|')*
Integer = [1-9]{Digit}*
Hex = \\x(([(a-f|A-F)0-9]){1,4})

%state STRING
%state COMMENT
%state CHARACTER
%state CHAR_END

%%

<YYINITIAL> {
    {Whitespace}        { /* IGNORE */ }
    "//"            { yybegin(COMMENT); }

    "use"               { return symbol(sym.USE); }
    "if"                { return symbol(sym.IF); }
    "while"             { return symbol(sym.WHILE); }
    "else"              { return symbol(sym.ELSE); }
    "return"            { return symbol(sym.RETURN); }
    "length"            { return symbol(sym.LENGTH); }

    "int"               { return symbol(sym.TYPE_INT); }
    "bool"              { return symbol(sym.TYPE_BOOL); }

    "true"              { return symbol(sym.BOOL_LITERAL, true); }
    "false"             { return symbol(sym.BOOL_LITERAL, false); }

    0
       {
          return symbol(sym.INT_LITERAL, "0");
      }

    {Integer}
        {
            String num = yytext();
            BigInteger n;
            try {
                n = new BigInteger(num);
            } catch (Exception e) {
                return symbol(sym.INT_LITERAL, "0");
            }

            int result = n.compareTo(maxInteger);
            if (result < 0) { // n < maxInteger
                return symbol(sym.INT_LITERAL, num);
            } else if (result == 0) { // n == maxInteger
                return symbol(sym.INT_MAX);
            } else { // n > maxInteger
                throw new cyr7.exceptions.lexer.LexerIntegerOverflowException(
                                    num, yyline, yycolumn, filename);
            }
        }

    \'
       {
          charBuffer.init(yyline, yycolumn);
          yybegin(CHARACTER);
       }

    \"
       {
          stringBuffer.init(yyline, yycolumn);
          yybegin(STRING);
       }

    {Identifier}        { return symbol(sym.ID, yytext()); }

    "("                 { return symbol(sym.L_PAREN); }
    ")"                 { return symbol(sym.R_PAREN); }
    "["                 { return symbol(sym.L_SQ_BRKT); }
    "]"                 { return symbol(sym.R_SQ_BRKT); }
    "{"                 { return symbol(sym.L_BRACE); }
    "}"                 { return symbol(sym.R_BRACE); }
    ":"                 { return symbol(sym.COLON); }
    ";"                 { return symbol(sym.SEMICOLON); }
    ","                 { return symbol(sym.COMMA); }
    "_"                 { return symbol(sym.UNDERSCORE); }

    "="                 { return symbol(sym.ASSIGN); }

    "+"                 { return symbol(sym.PLUS); }
    "-"                 { return symbol(sym.MINUS); }
    "*"                 { return symbol(sym.MULT); }
    "*>>"               { return symbol(sym.HIGH_MULT); }
    "/"                 { return symbol(sym.DIVIDE); }
    "%"                 { return symbol(sym.REMAINDER); }

    "!"                 { return symbol(sym.NEG_BOOL); }

    "<"                 { return symbol(sym.LT); }
    "<="                { return symbol(sym.LTE); }
    ">"                 { return symbol(sym.GT); }
    ">="                { return symbol(sym.GTE); }

    "=="                { return symbol(sym.EQUALS); }
    "!="                { return symbol(sym.NOT_EQUALS); }

    "&"                 { return symbol(sym.LOGICAL_AND); }
    "|"                 { return symbol(sym.LOGICAL_OR); }

    .
       {
          throw new cyr7.exceptions.lexer.InvalidTokenException(yytext(),
                                              yyline,
                                              yycolumn,
                                              filename);
      }
}

<COMMENT> {
   {LineEnd}         { yybegin(YYINITIAL); }
   .               { /* IGNORE */ }
}

<CHARACTER> {
    /* No characters */
    {Newline}
       {
          yybegin(YYINITIAL);
          throw new cyr7.exceptions.lexer.MultiLineCharacterException(
                charBuffer.getLineNumber(),
                charBuffer.getColumnNumber(),
                filename);
       }

    \'
       {
          yybegin(YYINITIAL);
          throw new cyr7.exceptions.lexer.InvalidCharacterLiteralException(
                "''",
                charBuffer.getLineNumber(),
                charBuffer.getColumnNumber(),
                filename);
       }

    [\u0000-\uFFFF]     {yybegin(CHAR_END); charBuffer.append(yytext()); }
    \\n                {yybegin(CHAR_END); charBuffer.append("\n"); }
    \\t|\t            {yybegin(CHAR_END); charBuffer.append("\t"); }
    \\r               {yybegin(CHAR_END); charBuffer.append("\r"); }
    \\f|\f            {yybegin(CHAR_END); charBuffer.append("\f"); }

    {Hex}
       {
          yybegin(CHAR_END);
          charBuffer.append(fromHex(yytext()));
       }

    \\'               {yybegin(CHAR_END); charBuffer.append("'"); }
    \\\"            {yybegin(CHAR_END); charBuffer.append("\""); }
    \\\\            {yybegin(CHAR_END); charBuffer.append("\\"); }

    /*Invalid escape characters*/
    \\[^]
       {
          yybegin(YYINITIAL);
          throw new cyr7.exceptions.lexer.InvalidCharacterLiteralException(
             "'" + charBuffer.toString() + "'",
             charBuffer.getLineNumber(),
             charBuffer.getColumnNumber(),
             filename);
       }
}

<CHAR_END> {
   \'
      {
         yybegin(YYINITIAL);
         return charBuffer.generateSymbol(sym.CHAR_LITERAL);
      }

   [^\']
      {
         yybegin(YYINITIAL);
         throw new cyr7.exceptions.lexer.InvalidCharacterLiteralException(
            "'" + charBuffer.toString() + yytext(),
            charBuffer.getLineNumber(),
            charBuffer.getColumnNumber(),
            filename);
      }
   <<EOF>>
      {
            throw new cyr7.exceptions.lexer.NonTerminatingCharacterException(
                charBuffer.toString(),
                charBuffer.getLineNumber(),
                charBuffer.getColumnNumber(),
                filename);
      }
}

<STRING> {
   {Newline}
      {
         yybegin(YYINITIAL);
         throw new cyr7.exceptions.lexer.MultiLineStringException(
            stringBuffer.getLineNumber(),
            stringBuffer.getColumnNumber(),
            filename);
      }

    \"
       {
          yybegin(YYINITIAL);
          return stringBuffer.generateSymbol(sym.STRING_LITERAL);
       }

    \\n                {stringBuffer.append("\n");}
    \\t|\t            {stringBuffer.append("\t");}
    \\r               {stringBuffer.append("\r");}
    \\f|\f            {stringBuffer.append("\f");}
    {Hex}            {stringBuffer.append(fromHex(yytext())); }
    \\'               {stringBuffer.append("'");}
    \\\"            {stringBuffer.append("\"");}
    \\\\                 {stringBuffer.append("\\");}

            // Invalid Escape Characters
    \\[^]
       {
          throw new cyr7.exceptions.lexer.InvalidStringEscapeCharacterException(
             yytext(),
             yyline,
             yycolumn,
             filename);
       }

    <<EOF>>
        {
            throw new cyr7.exceptions.lexer.NonTerminatingStringException(
                stringBuffer.toString(),
                stringBuffer.getLineNumber(),
                stringBuffer.getColumnNumber(),
                filename);
        }

    .    {stringBuffer.append(yytext()); }
}

<<EOF>>  { return symbol(sym.EOF);}
[^]
    {
        throw new cyr7.exceptions.lexer.InvalidTokenException(
            yytext(),
            yyline,
            yycolumn,
            filename
        );
    }
