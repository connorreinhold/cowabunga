use io
use conv

main(args: int[][]) {
    level1: int[10]
    level2: int[10][10]
    level3: int[10][10][10]
    level4: int[10][10][10][10]
    
    println(unparseInt(length(level1)))
    
    println(unparseInt(length(level2)))
    println(unparseInt(length(level2[0])))
    
    println(unparseInt(length(level3)))
    println(unparseInt(length(level3[0])))
    println(unparseInt(length(level3[0][0])))
    
    println(unparseInt(length(level4)))
    println(unparseInt(length(level4[0])))
    println(unparseInt(length(level4[0][0])))
    println(unparseInt(length(level4[0][0][0])))
}