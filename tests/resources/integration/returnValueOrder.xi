use io
use conv

main(args: int[][]) {
    x1: int, x2: int, x3: int, x4: int, x5: int, x6: int = doSomePrintingAlso()
    arr: int[] = {x1,x2,x3,x4,x5,x6}
    
    i: int = 0
    while (i < length(arr)) {
        println(unparseInt(arr[i]))
        i = i + 1
    }
}


doSomePrintingAlso(): int, int, int, int, int, int {
    return a(), b(), c(), d(), e(), f()
}

a(): int {
    println("Hello World")
    return 1
}

b(): int {
    println("Goodbye World")
    return 2
}

c(): int {
    println("Johnny Said Hi")
    return 3
}

d(): int {
    println("<Insert JoJo's Reference>")
    return 4
}

e(): int {
    println("You are Cowabunga")
    return 5
}

f(): int {
    println("You are (Not) Cowabunga")
    return 6
}