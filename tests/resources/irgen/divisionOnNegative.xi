use io
use conv

main(args: int[][]) {
    n: int = -23;
    i: int = n/64;
    // Expecting 0
    println(unparseInt(i));
}