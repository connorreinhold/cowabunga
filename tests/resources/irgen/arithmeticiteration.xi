use conv
use io

main(args: int[][]) {
    testArrayAddition()
    testArrayMultiDim()
}

testArrayAddition() {
    a:int[] = {3} + {2}
    println(unparseInt(a[0]))
    println(unparseInt(a[1]))
}

testArrayMultiDim() {
    a:int[][] = {{3,2,4,1}} + {{5,6} + {7,8}}
    println(unparseInt(a[1][2]))
    b:int[] = a[0]
    b = helperDuplicateArrayValues(b)
    println(unparseInt(b[5]))
    
}

helperDuplicateArrayValues(arr: int[]): int[] {
    toReturn: int[] = {}
    iterator: int = 0;
    while(iterator < length(arr)) {
        toReturn = toReturn + {2*arr[iterator]}
        iterator = iterator + 1
    }
    return toReturn;
    
}