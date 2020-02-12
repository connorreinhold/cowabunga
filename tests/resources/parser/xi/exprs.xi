foo() {
    i:int[3][4];
    p:int, q:int = ratadd(2, 5, 1, 3)
    _, q':int = ratadd(1, 2, 1, 3)
}

bar() : int[] {
    i:int[3][4];
    return i;
}

