use io
use conv

usage() {
    println("Please specify the input size")
}

main(args:int[][]) {
    testArray: int[] = {3,3,3,3,5,7,42,2,4,3,5,7};
    result: int[] = dedupe(testArray);
    i: int = 0
    size: int = length(result)
    while i < size {
        println(unparseInt(result[i]));
        i = i + 1;
    }
}

isMem(e: int, arr: int[]): bool {
    i: int = 0;
    size: int = length(arr);
    while i < size {
        if e == arr[i] {
            return true;
        }
        i = i + 1;
    }
    return false;
}

splice(arr: int[], start: int, end: int): int[] {
    size: int = end - start
    splicedArr: int[size]
    
    i: int = start
    while (i < end) {
        splicedArr[i - start] = arr[i]
        i = i + 1;
    }
    return splicedArr;
}

dedupe(arr:int[]): int[] {
    i: int = 0;
    size: int = length(arr);
    dedupedArr: int[] = {};
    while (i < size) {
        e: int = arr[i];
        splicedArr: int[] = splice(arr, i + 1, size);
        if (!isMem(e, splicedArr)) {
            dedupedArr = dedupedArr + {e};
        }
        i = i + 1;
    }
    return dedupedArr;
}
