use io
use conv

printlnBool(b: bool) {
    if (b) {
        println("true")
        } else {
        println("false")
    }
}
main(args: int[][]) {
    arr1: int[] = {2,3,5,7};
    arr2: int[] = {2,3,5,7};
    
    printlnBool(arr1 == arr2) // Should be false
    printlnBool(arr1 == arr1) // Should be true
    printlnBool(arr2 == arr2) // Should be true
    printlnBool({1,2,3,4} == {1,2,3,4}) // Should be false
    printlnBool({} == {}) // Should be false
}
