use io
use conv

printArray(a: int[]) {
    i: int = 0
    comma: int[] = ",";
    print("{")
    while i < length(a) {
        print(unparseInt(a[i]))
        print(comma);
        i = i + 1
    }
    println("}")
}

swap(a: int[], i: int, j: int) {
    temp: int = a[i]
    a[i] = a[j]
    a[j] = temp
}

main(args: int[][]) {
    sort(args[0])
    printArray(args[0])
}

sort(a: int[]) {
    i: int = 0
    len: int = length(a)

    while i < len - 1 {
        jMin: int = i
        j: int = i + 1
        while j < len {
            if a[j] < a[jMin] {
                jMin = j
            }
            j = j + 1
        }
        if (jMin != i) {
            swap(a, i, jMin)
        }
        i = i + 1
    }
}