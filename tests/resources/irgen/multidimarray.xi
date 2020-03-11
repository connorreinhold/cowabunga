use io
use conv

main(args: int[][]) {
    test1()
    test2()
}

test1() {
    x: int[5][5];
    i: int = 0
    k: int = 0
    while i < length(x) {
        j: int = 0
        while j < length(x) {
            x[i][j] = 'a' + k
            j = j + 1
            k = k + 1
        }
        i = i + 1
        k = k + 1
    }

    i = 0
    while i < length(x) {
        print(x[i])
        i = i + 1
    }
    println("")
}

test2() {

    prefData: int[][] = {
        { 1, 3, 2, 0 },
        { 0, 3, 1, 2 },
        { 0, 2, 3, 1 },
        { 3, 0, 2, 1 }
    }

    n: int = length(prefData)
    a: int[n][n]

    i: int = 0
    while i < n {
        j: int = 0
        line: int[] = prefData[i]
        while j < n {
            a[i][line[j]] = j
            j = j + 1
        }
        i = i + 1
    }

    printArrayArray(a)
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

