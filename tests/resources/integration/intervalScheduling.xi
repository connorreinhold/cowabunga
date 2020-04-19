use io
use conv

// To make life easier. The initial array is already sorted by finish time.
main(args: int[][]) {
    intervals: int[][] = {
        {2, 3}, {0, 4}, {2, 4}, {1, 6}, {4, 7}, 
        {5, 8}, {6, 8}, {7,8}
    }
    
    scheduledIntervals: int[][] = schedule(intervals)
    size: int = length(scheduledIntervals)
    println(unparseInt(size))
    
    i: int = 0
    while (i < size) {
        print(unparseInt(scheduledIntervals[i][0]))
        print(" ")
        print(unparseInt(scheduledIntervals[i][1]))
        println("")
        i = i + 1
    }
}

// Assumes {intervals} is sorted by finish time.
schedule(intervals: int[][]): int[][] {
    lastFinishTime: int = 0
    
    i: int = 0;
    numOfIntervals: int = length(intervals)
    
    scheduledIntervals: int[numOfIntervals][]
    scheduledIndex: int = 0
    
    while (i < numOfIntervals) {
        current: int[] = intervals[i]
        currentStart: int = current[0]
        currentFinish: int = current[1]
        
        if (currentStart >= lastFinishTime) {
            scheduledIntervals[scheduledIndex] = current
            scheduledIndex = scheduledIndex + 1
            lastFinishTime = currentFinish
        }
        i = i + 1
    }
    return split2DArray(scheduledIntervals, 0, scheduledIndex)
}

split2DArray(arr: int[][], start: int, end: int): int[][] {
    newArr: int[end - start][]
    i: int = start
    while (i < end) {
        newArr[i - start] = arr[i]
        i = i + 1
    }
    return newArr
}