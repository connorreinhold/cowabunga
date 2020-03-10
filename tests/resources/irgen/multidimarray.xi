use io

main(args: int[][]) {
    x: int[5][5];
    i: int = 0
    while i < length(x) {
        j: int = 0
        while j < length(x) {
            x[i][j] = 'a' + i + j
            j = j + 1
        }
        i = i + 1
    }

    i = 0
    while i < length(x) {
        print(x[i])
        i = i + 1
    }
}

