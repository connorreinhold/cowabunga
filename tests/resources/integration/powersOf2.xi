use io
use conv

main(args: int[][]) {
    i: int = 1
    j: int = 1
    while j <= 64 {
        i = 2 * i
        println(unparseInt(i))
        j = j + 1
    }

    // i = 2^64 mod 2^64 = 0

    i = 256 * 256 * 256 * 256 * 256 * 256 * 256 * 128; // 2^63
    j = 63

    while j >= 1 {
        i = i / 2
        println(unparseInt(i))
        j = j - 1
    }
}