use io
use conv

main(args: int[][]) {
    val:int = integrate(0,10,3)
    println(unparseInt(val))
    val = integrate(0,8,3)
    println(unparseInt(val))
    val = integrate(0,8,2)
    println(unparseInt(val))
    val = integrate(0,8,1)
    println(unparseInt(val))
    
}

f(numerator:int, denominator:int):int,int {
    return numerator*numerator,denominator*denominator
}

simplifyFraction(numerator:int, denominator:int, common:int):int,int {
    if (numerator == 0) {
        return 0,1
    }
    if (denominator % common == 0 & numerator % common == 0) {
    return numerator/common, denominator/common
    } else if (denominator % numerator == 0) {
    return 1, denominator/numerator
    } else if (numerator % denominator == 0) {
        return numerator/denominator, 1
    }
    return numerator, denominator

}

integrate(left:int, right: int, accuracy:int):int {
    intNum:int = 0
    intDenom:int = 1
    xNum:int = left*accuracy
    xDenom:int = accuracy
    while(xNum/xDenom < right) {
        yNum:int,yDenom:int = f(xNum, xDenom)
        intNum = intNum*yDenom + intDenom*yNum
        intDenom = intDenom*yDenom
        n:int, d:int = simplifyFraction(intNum, intDenom, accuracy)
        intNum = n
        intDenom = d
        xNum = xNum*accuracy + xDenom
        xDenom = xDenom * accuracy
        updatedNum:int, updatedDenom:int = simplifyFraction(xNum, xDenom, accuracy)
        xNum = updatedNum
        xDenom = updatedDenom
    }
    
 
    intDenom = intDenom * accuracy
    intNum = intNum + intDenom/2
    return intNum/intDenom
}