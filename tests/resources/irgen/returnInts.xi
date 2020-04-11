use io
use conv

main(args: int[][]) {
    _, _, a:int = compute_knapsack(1,2,3,4,5,6)
    println(unparseInt(a))
}

compute_knapsack(a:int, b:int, c:int, d:int, e:int, f:int): int, int, int {
    return 0, 0, 0
}