use io
use conv

f(a: int[]): int {
    a[0] = 120;
    a[1] = 240;
    return a[0];
}

g(a: int[]): int {
    a[0] = 1000;
    a[1] = 430;
    return a[1];
}

main(args: int[][]) {
    a: int[] = {1,2,3,4,5,6,7,8,9};
    num: int = f(a) + g(a);
    
    // Expect 120 + 430 = 550
    println(unparseInt(num));
    
    // Expect 430 + 120 = 550
    num2: int = g(a) + f(a);
    println(unparseInt(num2));
    
}
