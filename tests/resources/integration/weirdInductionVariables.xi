use io
use conv

main(args: int[][]) {
    test1()
    test2()
    test3()
}

test1() {
    println("TEST 1:")
    i: int = 0
    j: int = 0
    while i < 10 {
        if j % 2 == 0 {
            j = j + 1
        } else if i % 2 == 0 {
            i = i + 1
        } else {
            i = i + 1
            j = j + 1
        }
        println("(" + unparseInt(i) + ", " + unparseInt(j) + ")")
    }
}

test2() {
    println("TEST 2:")
    a: int[] = { 1 }
    x: int[f(a)][f(a)][f(a)][f(a)][f(a)]
    println(unparseInt(length(x)))
    println(unparseInt(length(x[0])))
    println(unparseInt(length(x[0][0])))
    println(unparseInt(length(x[0][0][0])))
    println(unparseInt(length(x[0][0][0][0])))
}

f(a: int[]): int {
    a[0] = a[0] * 2
    return a[0]
}

test3() {
    println("TEST 3:")
    sieve: bool[200]
    i: int = 0
    while i < length(sieve) {
        sieve[i] = true
        i = i + 1
    }
    i = 2
    while i < length(sieve) {
        j: int = 2
        while i * j < length(sieve) {
            sieve[i * j] = false
            j = j + 1
        }
        i = i + 1
    }
    i = 0
    while i < length(sieve) {
        if sieve[i] {
            println(unparseInt(i))
        }
        i = i + 1
    }
}