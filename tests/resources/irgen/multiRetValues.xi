use io
use conv

main(args: int[][]) {
    f1: int, f2: int = simple_multi_ret()
    print("(") print(unparseInt(f1)) print(", ") print(unparseInt(f2)) println(")")

    g1: int, g2: int, g3: bool = func_call_in_ret()
    print("(") print(unparseInt(g1)) print(", ") print(unparseInt(g2)) print(", ") printBool(g3) println(")")

    c: int[] = { 4 }
    h1: int[], h2: int[], h3: int[], h4: int[], h5: int[], h6: int[], h7: int[], h8: int[], h9: int[] = return_array_with_aliasing(c)
    println("---")
    printArrayArray({c, h1, h2, h3, h4, h5, h6, h7, h8, h9})
    h1[1] = 5
    h2[0] = 6
    h3[0] = 7
    println("---")
    printArrayArray({c, h1, h2, h3, h4, h5, h6, h7, h8, h9})
    println("---")
}

simple_multi_ret(): int, int {
    return 3, 4
}

func_call_in_ret(): int, int, bool {
    return plus_one(3), plus_one(4), neg_bool(false);
}

duplicate(const: int): int, int {
    return const, const
}

plus_one(const: int): int {
    return const + 1
}

neg_bool(const: bool): bool {
    return !const
}

printBool(b: bool) {
    if b print("true") else print("false")
}

return_array_with_aliasing(c: int[]): int[], int[], int[], int[], int[], int[], int[], int[], int[] {
    a: int[] = { 1, 2 };
    b: int[] = { 3 };
    return a, b, c, a, b, c, a + b, b + c, a + c
}

printArray(c: int[]) {
    i: int = 0
    print("(")
    while i < length(c) {
        print(unparseInt(c[i]))
        print(", ")
        i = i + 1
    }
    print(")\n")
}
printArrayArray(c: int[][]) {
    i: int = 0
    while i < length(c) {
        printArray(c[i])
        i = i + 1
    }
}
