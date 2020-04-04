use io
use conv

printlnBool(b: bool) {
    if (b) {
        println("true")
        } else {
        println("false")
    }
}

main(args: int[][]) {
    
    a: int[] = {1,2,3,4,5,6,7,8,9,0};
    copyOfA: int[] = a + {};
    
    printlnBool(a == copyOfA);  // Should be false.
    i: int = 0;
    size: int = length(copyOfA)
    while (i < size) {
        copyOfA[i] = copyOfA[i] - 1;
        printlnBool(a[i] - copyOfA[i] == 1)  // Should print true
        i = i + 1;
    }
}
