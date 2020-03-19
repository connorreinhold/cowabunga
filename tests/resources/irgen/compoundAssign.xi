use io
use conv

main(args: int[][]) {
    x: int = 21;
    x /= x;
    println(unparseInt(x));
    
    a: int = 45;
    a += 32;
    println(unparseInt(a));
    
    b: int[] = {1,2,3,4,5,6};
    b += b;
    i: int = 0;
    size: int = length(b);
    while (i < size) {
        println(unparseInt(b[i]));
        i += 1;
    }
}
