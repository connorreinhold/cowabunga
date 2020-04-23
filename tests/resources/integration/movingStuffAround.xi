use io
use conv

main(args: int[][]) {
    x: int[] = {
        2,
        4294967296,
        1152921504606846976,
        -2,
        -4294967296,
        -1152921504606846976
    }

    y: int = 4294967296
    z: int = 1152921504606846976
    a: int[6]

    i: int = 0
    while i < length(x) {
        println(unparseInt(x[i]))
        i = i + 1
    }

    println(unparseInt(y));
    println(unparseInt(z));

    i = 0
    while i < length(x) {
        a[i] = x[i]
        i = i + 1
    }

    i = 0
    while i < length(x) {
        println(unparseInt(a[i]))
        i = i + 1
    }

    a[1] = y
    a[2] = z
    a[4] = -y
    a[5] = -z

    i = 0
    while i < length(x) {
        println(unparseInt(a[i]))
        i = i + 1
    }
}