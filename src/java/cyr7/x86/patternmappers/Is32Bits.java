package cyr7.x86.patternmappers;

public class Is32Bits {

    public static boolean check(long n) {
        return n <= Integer.MAX_VALUE && n >= Integer.MIN_VALUE;
    }

}