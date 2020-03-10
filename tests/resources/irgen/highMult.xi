use io
use conv

main(args: int[][]) {
    a:int = 1152921504606846975; //0FFFFFFFFFFFFFFFF
    b:int = 1152921504606846975; //0FFFFFFFFFFFFFFFF
    print(unparseInt(a *>> b))
}
