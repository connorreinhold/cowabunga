use io
use conv

main(args: int[][]) {
    
    // Should do literally nothing
    callDoNothingProcedure()
    callDoNothingProcedure()
    callDoNothingProcedure()
    
    a: int, b: bool, _, _, c: int, d:int = createValues() 
    // Prints "Called Create Values"
    
    println(unparseInt(a))  // Prints 5
    printBool(b)            // Prints true
    println(unparseInt(c))  // Prints 20
    println(unparseInt(d))  // Prints 40
    
    // Prints Called Create Arrays
    helloWorld: int[], compilers: int[], boolArr: bool[] = createArrays()
    
    println(helloWorld) // Prints Hello World
    println(compilers) // Prints Compilers
    println(unparseInt(length(boolArr))) // print 5
    printBool(boolArr[0]) // true
    printBool(boolArr[1]) // true
    printBool(boolArr[2]) // true
    printBool(boolArr[3]) // false
    printBool(boolArr[4]) // false
    
    boolArr = makeBoolArrayOfTen(true, true, true, true, true, false, false, false, false, false)
    // Print Called make Bool Array Of Ten
    // Print Called Create Arrays
    // print true
    // print true
    // print true
    // print true
    // print true
    // print false
    // print false
    // print false
    // print false
    // print false
    
    println(unparseInt(length(boolArr))) // Print 10
    printBool(boolArr[0]) // true
    printBool(boolArr[1]) // true
    printBool(boolArr[5]) // false
    printBool(boolArr[6]) // false
    
    // All x_i arrays should be the same, memory-wise.
    x1: bool[], x2: bool[], x3: bool[], x4: bool[], x5: bool[], x6: bool[], x7: bool[], x8: bool[] 
    = reverseEightBoolArrayOrder(boolArr, boolArr, boolArr, boolArr, boolArr, boolArr, boolArr, boolArr)
    
    // Print true
    printBool(sameForAll({x1, x2, x3, x4, x5, x6, x7, x8}))
}

sameForAll(arr: bool[][]): bool {
    isSame: bool = true
    i: int = 0
    size: int = length(arr)
    
    callDoNothingProcedure()
    
    while (i < size) {
        j: int = i + 1
        while (j < size) {
            isSame = isSame & arr[i] == arr[j]
            j = j + 1
        }
        i = i + 1
    }
    return isSame
}

callDoNothingProcedure() {
    doNothingProcedure()
    return
}

doNothingProcedure() {
    anotherDoNothingProcedure()
    return
}

anotherDoNothingProcedure() { 
    return
}

createValues(): int, bool, bool, bool, int, int {
    println("Called Create Values")
    return 5, true, false, true, 20, 40
}

createArrays(): int[], int[], bool[] {
    println("Called Create Arrays") 
    return "Hello World", "Compilers", {true, true, true, false, false}
}

makeBoolArrayOfTen(x1: bool, x2: bool, x3: bool, x4: bool, x5: bool, x6: bool, x7: bool, x8: bool, x9: bool, x10: bool): bool[] {
    println("Called make Bool Array Of Ten")
    callDoNothingProcedure()
    _, _, _ = createArrays()  // Will print "Called Create Arrays"
    printBool(x1)
    printBool(x2)
    printBool(x3)
    printBool(x4)
    printBool(x5)
    printBool(x6)
    printBool(x7)
    printBool(x8)
    printBool(x9)
    printBool(x10)
    return {x1, x2, x3, x4, x5, x6, x7, x8, x9, x10}
}

reverseEightBoolArrayOrder(a1: bool[], a2: bool[], a3: bool[], a4: bool[], a5: bool[], a6: bool[], a7: bool[], a8: bool[])
: bool[], bool[], bool[], bool[], bool[], bool[], bool[], bool[] {
    return a8, a7, a6, a5, a4, a3, a2, a1
}

printBool(b: bool) {
    if b {
        println("true")
    } 
    else {
        println("false")
    }
}
