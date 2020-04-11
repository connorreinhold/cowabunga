use io
use conv

main(args: int[][]) {
    n: int, W: int, v: int[], w: int[] = read(args)

    indexes: int[n]
    i: int = 0
    while i < n {
        indexes[i] = i
        i = i + 1
    }

    _, accValueA: int, knapsackA: bool[] = compute_knapsack(n, W, v, w, indexes, true)
    _, accValueB: int, knapsackB: bool[] = compute_knapsack(n, W, v, w, indexes, false)

    if accValueA > accValueB
        write(accValueA, knapsackA)
    else
        write(accValueB, knapsackB)
}

// returns n, W, v, and w
read(args: int[][]): int, int, int[], int[] {
    cursor: int, n: int = scanInt(args[1], 0)
    _, W: int = scanInt(args[1], cursor + 1)

    v: int[n]
    w: int[n]

    i: int = 0
    while i < n {
        cursor2: int, possibleV: int = scanInt(args[i + 1 + 1], 0)
        _, possibleW: int = scanInt(args[i + 1 + 1], cursor2 + 1)

        if (possibleW <= W) {
            v[i] = possibleV
            w[i] = possibleW
        } else {
            v[i] = 0
            w[i] = -1
        }

        i = i + 1
    }

    return n, W, v, w
}

// returns end, value
scanInt(line: int[], start: int): int, int {
        end: int = start
        while end < length(line) & line[end] != ' ' {
            end = end + 1
        }

        numString: int[end - start]
        k: int
        while k < length(numString) {
            numString[k] = line[start + k]
            k = k + 1
        }

        num: int, _ = parseInt(numString)

        return end, num
}

write(accValue: int, knapsack: bool[]) {
    println(unparseInt(accValue))
    i: int = 0
    one: int[] = "1"
    zero: int[] = "0"
    while i < length(knapsack) {
        if knapsack[i]
            println(one)
        else
            println(zero)

        i = i + 1
    }
}

compute_knapsack(n: int, W: int, v: int[], w: int[], indexes: int[], density: bool): int, int, bool[] {
    sort(indexes, 0, length(indexes) - 1, density, v, w)

    knapsack: bool[n]
    accWeight: int = 0
    accValue: int = 0
    i: int = 0
    while i < n {
        index: int = indexes[i]
        if accWeight + w[index] <= W {
            knapsack[index] = true
            accWeight = accWeight + w[index]
            accValue = accValue + v[index]
        }

        i = i + 1
    }
    return accWeight, accValue, knapsack
}

compare_density(v: int[], w: int[], o1: int, o2: int): bool {
    l: int = v[o1] * w[o2]
    r: int = v[o2] * w[o1]
    if l == r { return o1 < o2 }
    else { return l < r }
}

compare_value(v: int[], w: int[], o1: int, o2: int): bool {
    l: int = v[o1]
    r: int = v[o2]
    if l == r { return o1 < o2 }
    else { return l < r }
}

sort(a: int[], lo: int, hi: int, density: bool, v: int[], w: int[]) {
    if lo < hi {
        p: int = pivot(a, lo, hi, density, v, w)
        sort(a, lo, p - 1, density, v, w)
        sort(a, p + 1, hi, density, v, w)
    }
}

pivot(a: int[], lo: int, hi: int, density: bool, v: int[], w: int[]): int {
    p: int = a[hi]
    i: int = lo
    j: int = lo
    while j <= hi {
        if (density & compare_density(v, w, a[j], p)) | (!density & compare_value(v, w, a[j], p)) {
            swap(a, i, j)
            i = i + 1
        }
        j = j + 1
    }
    swap(a, i, hi)
    return i
}

swap(a: int[], i: int, j: int) {
    temp: int = a[i]
    a[i] = a[j]
    a[j] = temp
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
