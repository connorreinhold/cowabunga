use io
use conv
assert(b:bool, message:int[]) {
    if (b) println("No Clobbering: " + message)
    else { println("Clobbered: " + message) }
}

inc(a:int[]): int { a[0] = a[0] + 1 return a[0] }

test(b:int): int { b = 2 return b }

main(args:int[][]) {
    a:int[2]
    a[0] = 0
    b: int = 0
    c: int = inc(a)
    assert(a[0] != c, "Array")
    assert(b != test(b), "Int")
}