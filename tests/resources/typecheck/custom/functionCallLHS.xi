hello(a: int):int[]{
    return {1,2,3}
}

helloLong(a:int): int[][] {
    return {{1,2},{2,3}}
}

boolTest(): bool[] {
    return {true,false}
}

main() {
    hello(3)[2] = 4
    hello(4)[1] = 4
    hello(5)[4] = 4
    hello(1)[5] = 4
    helloLong(1)[0][0] = 2
    boolTest()[0] = boolTest()[0]
}

