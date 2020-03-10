use conv
use io

main(args: int[][]) {
    a:int[] = {1}
    b:int[] = duplicateList(a)
    c:int = length(a)
}

duplicateList(a:int[]):int[] {
    return a + {1,2}
}