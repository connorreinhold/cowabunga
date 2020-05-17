use io
use conv
main(args:int[][]) {

    lo:int = -9223372036854775808
    hi:int = lo *>> 4
    c:int = 9223372036854775807 *>> 4

    println(unparseInt(c))
    println(unparseInt(hi))

}