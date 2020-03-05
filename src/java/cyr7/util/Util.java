package cyr7.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Util {

    /**
     * Replace whitespace characters that were escaped by the lexer back into
     * their escaped form.
     *
     * For example: {@code unescapeString("\t")`} is a literal backslash and character
     * 't'
     *
     * @param s The string to unescape
     * @return The escaped string
     */
    public static String unescapeString(String s){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++)
            switch (s.charAt(i)){
            case '\n': sb.append("\\n"); break;
            case '\t': sb.append("\\t"); break;
            case '\r': sb.append("\\r"); break;
            case '\f': sb.append("\\f"); break;
            case '\\':  sb.append("\\\\"); break;
            case '\'': sb.append("\\'"); break;
            case '"':  sb.append("\\\""); break;
            default: sb.append(s.charAt(i));
            }
        return sb.toString();
    }



    /**
     * Replace whitespace characters that were escaped by the lexer back into
     * their escaped form. There is a different rule for printing double-quotes
     * in a character in contrast to printing double-quotes inside a string.
     * Double-quotes will not be escaped when printing as a character.
     *
     * For example: {@code unescapeCharacterString("\t")`}
     * is a literal backslash and character 't'
     *
     * Requires: {@code s} is the String value of a Character symbol.
     *
     * @param s The string to unescape
     * @return The escaped string
     */
    public static String unescapeCharacterString(String s){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++)
            switch (s.charAt(i)){
            case '\n': sb.append("\\n"); break;
            case '\t': sb.append("\\t"); break;
            case '\r': sb.append("\\r"); break;
            case '\f': sb.append("\\f"); break;
            case '\\':  sb.append("\\\\"); break;
            case '"':  sb.append('"'); break;
            default: sb.append(s.charAt(i));
            }
        return sb.toString();
    }


    public static <T> List<T> immutableCopy(List<? extends T> list) {
        return Collections.unmodifiableList(new ArrayList<>(list));
    }

    private Util() { }

}
