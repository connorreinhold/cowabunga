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
    arr: int[] = parseArgs(args);
    sort(arr);
    printArray(arr);
}

sort(a: int[]) {
    i: int = 1
    while i < length(a) {
        j: int = i
        while j > 0 & a[j - 1] > a[j] {
            swap(a, j, j - 1)
            j = j - 1
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

