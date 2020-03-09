use io
use conv

main(args:int[][]) {
    x: int = 10
    y: int = 5
    print(unparseInt(f(x, y)));
}

f(x: int, y: int): int {
    return x + y
}
