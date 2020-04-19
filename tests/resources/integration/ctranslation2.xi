main(args: int[][]) {
    res2: int = 0
    res3: int = 0
    b: bool = false
    c: bool = (false & b) | b // false
    if (b) res2 = 1
    if (c) res3 = 1
}

