use conv
use io
main(args: int[][]) {
    i: int = 0
    x: int[] = "Hi"
    counter: int = 0
    asserter: int[] = {0}
    while i < 10000 {
        x = x + "Hello"
        i = i + 1

        y: int = asserter[length(x) - (2 + 5 * i)]
    }
}
