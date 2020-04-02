use io
use conv

main(args: int[][]) {
    num: int, good: bool = parseInt("12");
    println(unparseInt(num));
    v: int, b: int, c: int, _, _, lol: int, _ = int_of_bool(false);
    println(unparseInt(v + b + c + lol))
}

int_of_bool(b: bool): int, int, int, int, int, int, int {
    if (!b) {
        return 1, 1, 1,2,2,2,2
    }
    else {
        return 0, 0, 0,0,0,0,0
    }
}