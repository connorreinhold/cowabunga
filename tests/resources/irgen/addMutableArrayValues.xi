use io
use conv

changeA(a: int[]): int[] {
    a[0] = 123;
    return a;
}

main(args: int[][]) {
    a: int[] = {23, 43, 65};
    b: int = a[0] + changeA(a)[0];
    println(unparseInt(b));
    
    c: int = a[0] * 2;
    println(unparseInt(c));
}
