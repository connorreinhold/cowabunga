use io
use conv

// THIS FILE IS FOR THE PROBLEM:
// https://leetcode.com/problems/open-the-lock/


arrayEq(list1:int[], list2: int[]): bool {
    if (length(list1) != length(list2)) {
        return false;
    } else {
        i: int = 0;
        while(i < length(list1)) {    
            if (list1[i] != list2[i]) {
                return false;
            }
            i = i + 1
        }
        return true;
    }
}
// Brute force method to check if [target] is in [list] before [index]
contains(list:int[][], target: int[], index:int):bool {
    i: int = 0;
    while(i < index) {
        if (arrayEq(list[i],target)) {
            return true;
        }
        i = i + 1;
    }
    return false;
    
}

// this is done to avoid out of heap issues by creating so many arrays without garbage collecting
genEmptyBfsList():int[][] {
    toReturn: int[][] = {}
    i: int = 0;
    while (i < 2) {
        toReturn = toReturn + {
        {-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},
        {-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},
        {-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},
        {-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},
        {-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},
        {-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},
        
        {-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},
        {-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},
        {-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},
        {-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},
        {-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},
        {-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1}}
        i = i + 1;
    }
    return toReturn;
}

genEmptyNumSteps():int[] {
    toReturn: int[] = {}
    i: int = 0;
    while (i < 2) {
        toReturn = toReturn + {
        -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
        -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
        -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
        -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
        -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
        -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
        
        -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
        -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
        -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
        -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
        -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
        -1,-1,-1,-1,-1,-1,-1,-1,-1,-1
        }
        i = i + 1;
    }
    return toReturn;
}

openTheLock(deadends: int[][], target:int[], bfsList: int[][], numSteps: int[]):int {
    i:int = 0;
    // length keeps track of how many indices of bfsList actually matter. It is an array so we can pass by reference
    bfsLength: int[] = {1};
    
    while(bfsLength[0] > i) {
        if (arrayEq(bfsList[i], target)) {
            return numSteps[i];
        } else {
            genBfsListSteps(deadends, numSteps, bfsList, bfsLength, i)
        }
        i = i + 1
    }
    return -1;

}

genBfsListSteps(deadends: int[][], numSteps: int[], bfsList: int[][], bfsLength: int[], indexOfElmt: int) {
    i: int = 0
    elmt: int[] = bfsList[indexOfElmt]
    while(i < length(elmt)) {
        // for each possible index, check the step one above and the step one below
        consider1: int[] = {}+elmt
        consider1[i] = (consider1[i] + 1) % 10
        consider2: int[] = {}+elmt
        consider2[i] = (consider2[i] + 9) % 10
        
        if (!contains(deadends, consider1, length(deadends)) & !contains(bfsList, consider1, bfsLength[0])) {
            bfsList[bfsLength[0]] = consider1
            // numSteps[i] is how many steps it took to get to a specific point
            numSteps[bfsLength[0]] = numSteps[indexOfElmt] + 1
            bfsLength[0] = bfsLength[0] + 1
        }
        if (!contains(deadends, consider2, length(deadends)) & !contains(bfsList, consider2, bfsLength[0])) {
            bfsList[bfsLength[0]] = consider2
            numSteps[bfsLength[0]] = numSteps[indexOfElmt] + 1
            bfsLength[0] = bfsLength[0] + 1
        }
        i = i + 1
    }
}

main(args:int[][]) {
    // If it hits out of bounds, that is because the array lengths bfsList and numSteps are not long enough
    
    bfsList:int[][] = {{0,0}} + genEmptyBfsList()
    // keeps track of how many steps it took to get to each element in the bfs
    numSteps: int[] = {0} + genEmptyNumSteps()
    
    deadends: int[][] = {{1,0},{0,1},{9,0},{0,9}}
    target:int[] = {6,0}
    solution:int = openTheLock(deadends, target, bfsList, numSteps)
    println(unparseInt(solution))
    
    deadends = {{1,0},{0,1}}
    target = {2,0}
    solution = openTheLock(deadends, target, bfsList, numSteps)
    println(unparseInt(solution))
    
    deadends = {{1,0}, {2,0}}
    target = {2,1}
    solution = openTheLock(deadends, target, bfsList, numSteps)
    println(unparseInt(solution))
    
    deadends = {{1,0}, {2,1}, {0,9}}
    target = {3,0}
    solution = openTheLock(deadends, target, bfsList, numSteps)
    println(unparseInt(solution))
    
}
