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
    arr: int[] = parseArgs(args)
    sort(arr)
    printArray(arr)
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

parseArgs(args: int[][]): int[] {
    size: int = length(args);
    arr: int[size - 1]
    i: int = 1
    while i < size {
        parsedInt: int, _ = parseInt(args[i])
        arr[i - 1] = parsedInt;
        i = i + 1
    }
    return arr;
}

