use io
use conv

main(args: int[][]) {
    a:int = 1152921504606846975; //0FFFFFFFFFFFFFFFF
    b:int = 1152921504606846975; //0FFFFFFFFFFFFFFFF
    println(unparseInt(a *>> b))
    
    a = 773437014593704226 // 0ABBCCDDEEFF1122
    b = 1234605616436508552 // 1122334455667788
    println(unparseInt(a *>> b)) // b7e7b2399ff67025
    
}

