use io
use conv

main(args: int[][]) {
    
    // Distributive property when inner constant and outer constant is 
    // over 32 bits.
    v: int = 30
    result: int = 8 * (v + 1073741824) // 1073741824 == 2^30 
    println(unparseInt(result));
    
    result = 8 * (v - 1073741824)
    println(unparseInt(result));
    
    result = 8 * (v + 1099511627776) // 1099511627776 == 2^40
    println(unparseInt(result));
    
    result = 8 * (v - 1099511627776)
    println(unparseInt(result));
    
}