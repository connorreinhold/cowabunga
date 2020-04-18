use io
use conv

matrixMult(a:int[][], b:int[][]): int[][] {
    numRows: int = length(a)
    numCols: int = length(b[0])
    matrix:int[numRows][numCols]
    i:int = 0
    j:int = 0
    while(i < numRows) {
        j = 0;
        while(j < numCols) {
            iterator: int = 0
            acc:int = 0
            while(iterator < length(a[0])) {
                acc = acc + a[i][iterator]*b[iterator][j]
                iterator = iterator + 1
            }
            matrix[i][j] = acc
            
            j = j + 1
            
        }
        i = i + 1
    }
    return matrix
}

printArray(a: int[]) {
    i: int = 0
    print("[")
    while (i < length(a)) {
        print(unparseInt(a[i]))
        if (i < length(a) - 1)
        print(", ")
        i = i + 1
    }
    print("]")
}

printArrayArray(a: int[][]) {
    i: int = 0
    print("[")
    while (i < length(a)) {
        printArray(a[i])
        i = i + 1
    }
    print("]")
}

main(args: int[][]) {
    a:int[][] = {{1,2,3},{4,5,6}}
    b:int[][] = {{1,2,3},{4,5,6},{7,8,9}}
    printArrayArray(matrixMult(a,b))
    println("")
    
    a = {{1,2},{8,4}}
    b = {{2,2,2,2,2,2},{4,4,0,8,8,1}}
    printArrayArray(matrixMult(a,b))
    println("")
    
    a = {{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}}
    b = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}}
    printArrayArray(matrixMult(a,b))
}