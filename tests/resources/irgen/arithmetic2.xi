use conv
use io

main(args: int[][]) {
    testArrayAddition()
    testArrayMultiDim()
    testPigLatin()
    testCount100()
    testArithmeticHighMult()
}

testArrayAddition() {
    a:int[] = {3} + {2}
    println(unparseInt(a[0]))
    println(unparseInt(a[1]))
}

testArrayMultiDim() {
    a:int[][] = {{3,2,4,-1,0}} + {{5,6} + {7,8}}
    println(unparseInt(a[1][2]))
    b:int[] = a[0]
    b = b + {length(b)}
    b = helperDuplicateArrayValues(b)
    println(unparseInt(b[5]))
    
}

helperDuplicateArrayValues(arr: int[]): int[] {
    toReturn: int[] = {}
    iterator: int = 0;
    while(iterator < length(arr)) {
        toReturn = toReturn + {2*arr[iterator]}
        iterator = iterator + 1
    }
    return toReturn;
}

testPigLatin() {
    latin1: int[] = helperPigLatin("Hel")
    latin2: int[] = helperPigLatin("sm")
    latin3: int[] = helperPigLatin("AbCdefghijklmnopqrstuvwxyz")
    println(latin1)
    println(latin2)
    println(latin3)
}

helperPigLatin(a:int[]):int[] {
    toReturn: int[] = {}
    len: int = length(a)
    if (len < 3) {
        return a
    }
    else {
        i: int = 0;
        while (i < len - 2) {
            toReturn = toReturn + {a[i + 2]};
            i = i + 1
        }
        toReturn = toReturn + {a[0]} + {a[1]}
        return toReturn;
    }
}

testCount100() {
    println(unparseInt(firstCountTo100(0)))
    println(unparseInt(firstCountTo100(1)))
}

firstCountTo100(a:int):int {
    if (a >= 100) {
        return a
    }
    return secondCountTo100(a + 1)
}

secondCountTo100(a:int):int {
    return firstCountTo100(a + 1)
}

testArithmeticHighMult() {
    a:int = -100;
    b:int = 1323
    println(unparseInt(a *>> b));

}

