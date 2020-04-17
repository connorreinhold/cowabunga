use io
use conv

main(args: int[][]) {
    i: int = 1
    n: int = 10
    product: int = 1
    while (i <= n) {
        product = product * i
        i = i + 1
    }

    print("Factorial " + unparseInt(n) + " is: " + unparseInt(product))
}