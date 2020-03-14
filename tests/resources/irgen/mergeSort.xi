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

reverse(a: int[]) {
    k: int = 0
    len: int = length(a)
    while k < len / 2 {
        temp: int = a[k]
        a[k] = a[len - k - 1]
        a[len - k - 1] = temp

        k = k + 1
    }
}

main(args: int[][]) {
    b: int[length(args[0])]
    sort(args[0], b, length(args[0]))
    reverse(args[0])
    printArray(args[0])
}

sort(a: int[], b: int[], n: int) {
    copy(a, 0, n, b)
    topDownSplitMerge(b, 0, n, a)
}

topDownSplitMerge(b: int[], iBegin: int, iEnd: int, a: int[]) {
    if (iEnd - iBegin < 2) {
        return
    }

    iMiddle: int = (iEnd + iBegin) / 2
    topDownSplitMerge(a, iBegin, iMiddle, b)
    topDownSplitMerge(a, iMiddle, iEnd, b)

    topDownMerge(b, iBegin, iMiddle, iEnd, a)
}

topDownMerge(a: int[], iBegin: int, iMiddle: int, iEnd: int, b: int[]) {
    i: int = iBegin
    j: int = iMiddle

    k: int = iBegin
    while k < iEnd {
        if i < iMiddle & (j >= iEnd | a[i] >= a[j]) {
            b[k] = a[i]
            i = i + 1
        } else {
            b[k] = a[j]
            j = j + 1
        }

        k = k + 1
    }
}

copy(a: int[], iBegin: int, iEnd: int, b: int[]) {
    k: int = 0
    while k < iEnd {
        b[k] = a[k]

        k = k + 1
    }
}