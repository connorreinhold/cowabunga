use io
use conv

main(args: int[][]) {
    // a: int[] = {3,4,5,6};
    // tryout(a, 23);
    // println(unparseInt(a[0]));  // Expect 23
    
    // tryout(a, 2200);
    // println(unparseInt(a[0]));  // Expect 2200
    
    // b: int[] = {23, 42, 34, 64} + {23, 42, 34, 64};
    // println(unparseInt(length(b))); // Expected 8
    // a: int = {}[3];
    total: int = sum(1,2,3,4,5,6,7);
    println(unparseInt(total));  // Expected 28 
}

tryout(arr: int[], num: int) {
    arr[0] = num;
}

sum(a: int, b:int, c:int, d:int, e:int, f:int, g:int): int {
    return a + b + c + d + e + f + g;
}