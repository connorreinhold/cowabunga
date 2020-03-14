use conv
use io

main(args: int[][]) {
    testBooleanExpr()
    testIfStmts()
    testBoolArrays()
}

testBooleanExpr() {
    res1: int = 0
    res2: int = 0
    res3: int = 0
    a: bool = (true | false & true | false) | false & (true | false) // true
    b: bool = a & a & a & a & !a & a // false
    c: bool = a & (a & (a & (a & b) & b) & b) | b // false
    if (a) res1 = 1
    if (b) res2 = 1
    if (c) res3 = 1
    println(unparseInt(res1))
    println(unparseInt(res2))
    println(unparseInt(res3))
}

testIfStmts() {
    res:int = 0
    a: bool = true
    b: bool = false
    if (!a | false) {
        a = false
    } else if (b | true) {
        b = true
        if (true | a == b) {
            a = !false | false | (true & false)
            if (trueGen() & false) {
            } else if (trueGen()) {
                res = 5
            }
        }
    }
    println(unparseInt(res));
}
trueGen(): bool {
    return true;
}


testBoolArrays() {
    barr:bool[][] = {}
    i: int = 0
    j: int = 0
    while (i < 10) {
        j = 0
        add: bool[] = {}
        while (j < 10) {
            if ((i + j) % 2 != 1 | (i + j) % 2 == 0) { 
                add = add + {true}
            } else {
                add = add + {false}
            }
            j = j + 1
        }
        barr = barr + {add}
        i = i + 1
    }
    res: int = 0
    i = 0
    while (i < 10) {
        j = 0
        while (j < 10) {
            if (barr[i][j]) {
                res = res + 1
            }
            j = j + 1
        }
        i = i + 1
    }
    println(unparseInt(res));
}

