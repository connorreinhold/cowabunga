use io
use conv

main(args: int[][]) {
    val:int = 1000
    printInt(sumLE(val))
    printInt(sumGE(val))
    printInt(sumEQ(val))
    printInt(sumNE(val))
    printInt(sumLT(val))
    printInt(sumGT(val))
    printInt(sumMOD(val))
    printInt(sumGeometric(10,2,7))
}

printInt(val:int) {
    println(unparseInt(val))
}

sumLE(max:int):int {
    i:int = 0
    sum:int = 0
    while (i <= max) {
        sum = sum + i
        i = i + 1
    }
    return sum
}

sumGE(max:int):int {
    i:int = max
    sum:int = 0
    while (i >= 0) {
        sum = sum + i
        i = i - 1
    }
    return sum
}

sumEQ(max:int):int {
    i:int = max
    sum:int = 0
    continue:bool = true
    while(continue) {
        sum = sum + i
        i = i - 1
        if (i == 0) {
            continue = false
        }
    }
    return sum
}

sumNE(max:int):int {
    i:int = 0
    sum:int = 0
    while (i != max + 1) {
        sum = sum + i
        i = i + 1
    }
    return sum
}

sumLT(max:int):int {
    i:int = 0
    sum:int = 0
    while (i < max + 1) {
        sum = sum + i
        i = i + 1
    }
    return sum
}

sumGT(max:int):int {
    i:int = max
    sum:int = 0
    while (i > -1) {
        sum = sum + i
        i = i - 1
    }
    return sum
}

sumMOD(max:int):int {
    i:int = 1
    sum:int = 0
    while(i % (max+1) != 0) {
        sum = sum + i
        i = i + 1
    }
    return sum
}

sumGeometric(a:int, rNumerator:int, rDenominator:int):int {
    numerator:int = a
    denominator:int = 1
    nextNumerator:int = numerator
    i:int = 0
    while(i < 10) {
        nextNumerator = nextNumerator * rNumerator
        numerator = numerator*rDenominator + nextNumerator
        denominator = denominator * rDenominator
        i = i + 1
    }
    numerator = numerator + denominator/2
    // round to the nearest int
    return numerator/denominator
}
