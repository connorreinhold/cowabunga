use io
use conv

binarySearch(x: int, arr: int[]): int {
    l: int = 0;
    r: int = length(arr);
    while (l <= r) { 
        m: int = l + (r - l) / 2; 
        
        // Check if x is present at mid 
        if (arr[m] == x) {
            return m; 
        }
        
        if (arr[m] < x) 
        // If x greater, ignore left half 
        l = m + 1;  
        else
        // If x is smaller, ignore right half 
        r = m - 1; 
    } 
    // if we reach here, then element was 
    // not present 
    return -1; 
}

main(args: int[][]) {
    value: int = args[0][0]
    arr: int[] = args[1]
    
    index: int = binarySearch(value, arr);
    println(unparseInt(index));
}