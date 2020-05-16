use io
use conv

fn(a:int, b:int, c:int, d:int, e:int, f:int, g:int, h:int) : int, int[] {
    // checks if call to alloc messes with the first return value
    return a + b + c + d + e + f + g + h, {0}
}

main(args:int[][]) {
    a:int, b:int[] = fn(1, 2, 3, 4, 5, 6, 7, 8)
    b[a - 36] = a
}