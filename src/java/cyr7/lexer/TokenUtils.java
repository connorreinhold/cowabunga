package cyr7.lexer;

public class TokenUtils {

    public static String fullDescription(MyLexer.Token token) {
        return (token.line + 1) + ":" + (token.column + 1) + " " + TokenUtils.typeAttributeDescription(token);
    }

    public static String typeAttributeDescription(MyLexer.Token token) {
        switch (token.type) {
            case USE: return "use";
            case IF: return "if";
            case WHILE: return "while";
            case ELSE: return "else";
            case RETURN: return "return";
            case LENGTH: return "length";

            case TYPE_INT: return "int";
            case TYPE_BOOL: return "bool";

            //since true and false are keywords, should they be their own tokens?
            case BOOL_LITERAL: return "boolean " + token.attribute;
            case INT_LITERAL: return "integer " + token.attribute;
            case CHAR_LITERAL: return "character " + token.attribute;

            case ID: return "id " + token.attribute;

            case L_PAREN: return "(";
            case R_PAREN: return ")";
            case L_SQ_BRKT: return "[";
            case R_SQ_BRKT: return "]";
            case L_BRACE: return "{";
            case R_BRACE: return "}";
            case COLON: return ":";
            case SEMICOLON: return ";";

            case ASSIGN: return "=";

            case PLUS: return "+";
            case MINUS: return "-";
            case MULT: return "*";
            case HIGH_MULT: return "*>>";
            case DIVIDE: return "/";
            case REMAINDER: return "%";

            case NEG_BOOL: return "!";

            case LT: return "<";
            case LTE: return "<=";
            case GT: return ">";
            case GTE: return ">=";

            case EQUALS: return "==";
            case NOT_EQUALS: return "!=";

            case LOGICAL_AND: return "&";
            case LOGICAL_OR: return "|";

            default: return "";
        }
    }

}
