use io
use conv

main(args: int[][]) {
    r: int[2800 + 1]
    i: int = 0
    k: int = 2800
    b: int
    d: int
    c: int = 0

    while i < k {
        r[i] = 2000
        i = i + 1
    }

    while k > 0 {
        d = 0
        i = k
        while i > 0 {
            d = d * i
            d = d + r[i] * 10000
            b = 2 * i - 1

            r[i] = d % b
            d = d / b
            i = i - 1
        }
        value: int = c + d / 10000
        print(unparseInt(value))

        c = d % 10000
        k = k - 14
    }
}