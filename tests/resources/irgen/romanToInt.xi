use io
use conv

handleI(peek: int): int, bool {
    if (peek == 'X') {
        return 9, true;
    }
    else if (peek == 'V'){
        return 4, true;
    }
    else {
        return 1, false;
    }
}

handleX(peek: int): int, bool {
    if (peek == 'C') {
        return 90, true;
    }
    else if (peek == 'L'){
        return 40, true;
    }
    else {
        return 10, false;
    }
}

handleC(peek: int): int, bool {
    if (peek == 'M') {
        return 900, true;
    }
    else if (peek == 'D'){
        return 400, true;
    }
    else {
        return 100, false;
    }
}


convert(roman: int[]): int {
    value: int = 0;
    size: int = length(roman);
    i: int = 0
    while (i < size) {
        c: int = roman[i];
        peek: int = ' '
        if (i < size - 1) {
            peek = roman[i + 1];
        }
        if (c == 'I') {
            incr: int, skip: bool = handleI(peek);
            if (skip) {
                i = i + 1;
            }
            value = value + incr;
        } 
        else if (c == 'X') {
            incr: int, skip: bool = handleX(peek);
            if (skip) {
                i = i + 1;
            }
            value = value + incr;
        } 
        else if (c == 'C') {
            incr: int, skip: bool = handleC(peek);
            if (skip) {
                i = i + 1;
            }
            value = value + incr;
        } 
        else if (c == 'V'){
            value = value + 5;
        } 
        else if (c == 'L') {
            value = value + 50;
        } 
        else if (c == 'D') {
            value = value + 500;
        } 
        else if (c == 'M') {
            value = value + 1000;
        }
        i = i + 1;
    }
    return value;
}

main(args: int[][]) {
    i: int = 1;
    size: int = length(args);
    while (i < size) {
        println(unparseInt(convert(args[i])))
        i = i + 1;
    }
}