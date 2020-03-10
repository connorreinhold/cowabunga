package cyr7.ir.integration;

public class testJunk {
    public static long highMult(long x, long y) {
        long x_high = x >>> 32;
        long x_low = x & 0xFFFFFFFFL;
        long y_high = y >>> 32;
        long y_low = y & 0xFFFFFFFFL;
        long z2 = x_low * y_low;
        long t = x_high * y_low + (z2 >>> 32);
        long z1 = t & 0xFFFFFFFFL;
        long z0 = t >>> 32;
        z1 += x_low * y_high;
        return x_high * y_high + z0 + (z1 >>> 32);
    }
    public static void main(String[] args) {
        long x = -100;
        long y = 10000;
        System.out.println(highMult(x,y));
    }
}
