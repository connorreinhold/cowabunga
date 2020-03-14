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
    arr: int[] = args[0]
    size: int = length(arr);
    i: int = 0;
    while (i < size) {
        index: int = binarySearch(arr[i], arr);
        println(unparseInt(index));
        i = i + 1;
    }
}