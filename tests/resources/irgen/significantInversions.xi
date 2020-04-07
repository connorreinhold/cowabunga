use io
use conv


// returns the number of numbers in a between lStart and lEnd that are greater than val + 1
// precondition: a[lStart]...a[lEnd] sorted
countSigInversions(a:int[], lStart:int, lEnd:int, value:int):int {
    while(lStart <= lEnd & a[lStart] <= value + 1) {
        lStart = lStart + 1
    }
    return lEnd - lStart + 1;
}

// returns the number of significant inversions in a, based on Algo Fall 2019 ps4 Q1
sigInversions(a:int[], left: int, right: int):int {
    if (left == right) {
        return 0
    }
    median: int = (left + right)/2
    numLeftInv:int = sigInversions(a, left, median)
    numRightInv: int = sigInversions(a, median + 1, right)
    numSigInversions: int = numLeftInv + numRightInv;
    sortedArea: int[right - left + 1]
    li:int = left
    ri:int = median + 1
    si:int = 0;
    while(li <= median & ri <= right) {
        update:int = -1;
        if (a[ri] < a[li]) {
            update = a[ri]
            ri = ri + 1
            numSigInversions = numSigInversions + countSigInversions(a, li, median, update)
        } else {
            update = a[li]
            li = li + 1
        }
        sortedArea[si] = update
        si = si + 1
    }
    while(li <= median) {
        sortedArea[si] = a[li]
        li = li + 1
        si = si + 1
    }
    
    while(ri <= right) {
        sortedArea[si] = a[ri]
        ri = ri + 1
        si = si + 1
    }
    
    si = 0;
    while (si < length(sortedArea)) {
        a[left + si] = sortedArea[si]
        si = si + 1
    }
    return numSigInversions
}

parseData(arr:int[]):int[] {
    // add a space so the last number is parsed in the same way
    arr = arr + " "
    numSpace:int = 0
    i:int = 0;
    while(i < length(arr)) {
        if (arr[i] == ' ') {
            numSpace = numSpace + 1
        }
        i = i + 1
    }
    nums:int[numSpace]
    charInd:int = 0
    numsInd:int = 0;
    numberStart:int = 0;
    while(charInd < length(arr)) {
        if (arr[charInd] == ' ') {
            // convert the number to string and copy to nums
            ogNumberStart:int = numberStart
            numberString:int[charInd - numberStart]
            while(numberStart < charInd) {
                numberString[numberStart - ogNumberStart] = arr[numberStart]
                numberStart = numberStart + 1
            }
            numberStart = numberStart + 1
            val:int,_ = parseInt(numberString)
            nums[numsInd] = val;
            numsInd = numsInd + 1
            
            
        }
        charInd = charInd + 1
    }
    return nums;
}

main(args:int[][]) {
    i: int = 1
    while i < length(args) {
        data:int[] = parseData(args[i])
        numInv: int = sigInversions(data, 0, length(data) - 1)
        println(unparseInt(numInv));
        i = i + 1;
    }
}
