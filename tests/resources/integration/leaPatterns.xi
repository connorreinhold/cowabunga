use io
use conv


printlnInt(n: int) {
    println(unparseInt(n));
}

main(args: int[][]) {
    
    // Distributive property when the product of the 
    // inner constant and outer constant is over 32 bits.
    // The four below covers s*(v + t), where t <= 32-bit and s*t > 32-bit
    v: int = 30
    
    result: int = 8 * (v + 1073741824) // 1073741824 == 2^30 
    println(unparseInt(result));  // 8589934832
    
    result = 8 * (v - 1073741824)
    println(unparseInt(result)); // -8589934352
    
    result = 8 * (v + 1099511627776) // 1099511627776 == 2^40
    println(unparseInt(result));  // 8796093022448
    
    result = 8 * (v - 1099511627776)
    println(unparseInt(result));  // -8796093021968
    
    // The four below covers s*(v + t), where t <= 32-bit and s*t > 32-bit
    result = -8 * (v + 1073741824) // 1073741824 == 2^30
    println(unparseInt(result)); // -8589934832
    
    result = -8 * (v - 1073741824)
    println(unparseInt(result)); // 8589934352
    
    result = -8 * (v + 1099511627776) // 1099511627776 == 2^40
    println(unparseInt(result)); // -8796093022448
    
    result = -8 * (v - 1099511627776)
    println(unparseInt(result));  // 8796093021968
    
    // t*s
    result = v * 4
    printlnInt(result) // 120
    
    result = 4 * v
    printlnInt(result) // 120
    
    temporary: int = 1073741824
    result = temporary * 4
    printlnInt(result) // 4294967296
    
    temporary = 1073741824
    result = 4 * temporary
    printlnInt(result) // 4294967296
    
    result = -v * 4
    printlnInt(result) // -120
    
    result = 4 * -v
    printlnInt(result) // -120
    
    temporary = -1073741824
    result = temporary * 4
    printlnInt(result) // -4294967296
    
    temporary = -1073741824
    result = 4 * temporary
    printlnInt(result) // -4294967296
    
    // t*s + n
    result = 4 * v + 1073741824 // 1073741824 == 2^30
    println(unparseInt(result)); // result == 1073741944
    
    result = 4 * v + 1099511627776 // 1099511627776 == 2^40
    println(unparseInt(result)); // result == 1099511627896
    
    // t*s - n
    result = 4 * v - 1073741824 // 1073741824 == 2^30
    println(unparseInt(result)); // result == -1073741704
    
    result = 4 * v - 1099511627776 // 1099511627776 == 2^40
    println(unparseInt(result)); // result == -1099511627656    
    
    // t_1 + t_2*s + n
    temporary = 12
    result = temporary + 4 * v + 1073741824 // 1073741956 == 2^30
    println(unparseInt(result)); // result == 1073741956
    
    result = temporary + 4 * v + 1099511627776 // 1099511627776 == 2^40
    println(unparseInt(result)); // result == 1099511627908   
    
    // t_1 + n 
    result = v + 1073741824 // 1073741824 == 2^30
    println(unparseInt(result)); // result == 1073741854
    
    result = v + 1099511627776 // 1099511627776 == 2^40
    println(unparseInt(result)); // result == 1099511627806
    
    // t_1 - n 
    result = v - 1073741824 // 1073741824 == 2^30
    println(unparseInt(result)); // result == -1073741794
    
    result = v - 1099511627776 // 1099511627776 == 2^40
    println(unparseInt(result)); // result == -1099511627746
}