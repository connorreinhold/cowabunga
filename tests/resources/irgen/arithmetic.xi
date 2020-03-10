use conv
use io

main(args: int[][]) {
    p(test1())
}

test1(): int {
    str: int[] = { 1, 3, 5, 7 }
    x: int = 0
    sum: int = 0
    while (x < 4) {
        sum = sum + str[x];
        x = x + 1;
    }
    return sum
}

p(i: int) {
    println(unparseInt(i))
}