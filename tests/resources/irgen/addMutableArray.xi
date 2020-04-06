use io
use conv

changeA(a: int[]): int[] {
    a[0] = 123;
    return a;
}

changeB(a: int[]): int[] {
    a[0] = 500;
    return a;
}

main(args: int[][]) {
    a: int[] = {23, 43, 65};
    b: int[] = a + changeA(a);
    printlnArrays(b);
    
    c: int[] = changeA(a) + changeB(a)
    printlnArrays(c)
}


printlnArrays(a: int[]) {
    i: int = 0;
    size: int = length(a);
    while (i < size) {
        println(unparseInt(a[i]));
        i = i + 1;
    }
}
