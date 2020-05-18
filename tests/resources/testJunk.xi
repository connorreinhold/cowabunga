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

parseArgs(args: int[][]): int[] {
    size: int = length(args)
    arr: int[size]
    i: int = 1
    while i < size + 1 {
        n: int, _ = parseInt(args[i - 1])
        arr[i - 1] = n
        i = i + 1
    }
    return arr
}

main(args: int[][]) {
    args = {"72", "101", "108", "110", "111", "119", "120", "124", "138"}
    arr: int[] = parseArgs(args)
    size: int = length(arr);
    i: int = 0;
    while (i < size) {
        index: int = binarySearch(arr[i], arr);
        println(unparseInt(index));
        i = i + 1;
    }
}