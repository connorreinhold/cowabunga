use io
use conv

exponential(n: int,  x: int): int {
    sum:int = 100000
    offset:int = sum

    i:int = n - 1
    while (i > 0) {
        sum = offset + (x * offset * sum) / (i * offset)
        i = i - 1
    }

    return sum;
}

main(args: int[][]) {
    n:int = 10
    x:int = 1
    print(unparseInt(exponential(n, x)))
}