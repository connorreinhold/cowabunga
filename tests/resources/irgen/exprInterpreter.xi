use io
use conv

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

isADigit(c: int): bool {
    i: int = 0;
    size: int = 10;
    while (i < size) {
        if (c == i + 48) {
            return true;
        }
        i = i + 1;
    }
    return false;
}

isOperation(c: int): bool {
    return c == '+' |
    c == '-' |
    c == '*' |
    c == '/' |
    c == '%';
}

getOperation(c: int): int {
    if (c == '+') {return '+'};
    if (c == '-') {return '-'};
    if (c == '*') {return '*'};
    if (c == '/') {return '/'};
    if (c == '%') {return '%'};
    return -1;
}

nextNumber(s: int[]): int, int {
    i: int = 0;
    size: int = length(s);
    buffer: int[] = {};
    while (i < size) {
        char: int = s[i];
        if (!isADigit(char)) {
            num: int, _ = parseInt(buffer);
            return num, length(buffer);
        } 
        else {
            buffer = buffer + {char};    
        }
        i = i + 1;
    }
    num: int, _ = parseInt(buffer);
    return num, length(buffer);
}


performUnaryOp(op: int, val: int): int {
    if (op == '+') {return val}
    if (op == '-') {return -val}
    return -1;
}


performOp(lhs: int[], op: int, rhs: int): int {
    size: int = length(lhs);
    if (size == 0) {
        return performUnaryOp(op, rhs)
    }
    else if (size == 1) {
        lhsVal: int = lhs[0];
        if (op == '+') {
            return lhsVal + rhs;
        }    
        else if (op == '-') {
            return lhsVal - rhs;
        } 
        else if (op == '*') {
            return lhsVal * rhs;
        }
        else if (op == '/') {
            return lhsVal / rhs;
        }
        else if (op == '%') {
            return lhsVal % rhs;
        }
    }
    println("Something went wrong");
    return -1;
}


nextToken(s: int[]): bool, int, int[] {
    i: int = 0;
    size: int = length(s);
    while (i < size) {
        char: int = s[i];
        if (char == ' ') {}
        else if (isADigit(char)) {
            s = splice(s, i, size);
            num: int, magnitude: int = nextNumber(s);
            return true, num, splice(s, i + magnitude, size);
        }
        else if (isOperation(char)) {
            return false, getOperation(char), splice(s, i + 1, size);
        }
        i = i + 1;
    }
    return false, -2, {};
}

interpret(expr: int[]): int {
    lhs: int[] = {}
    op: int = -1;
    while (length(expr) > 0) {
        isNum: bool, token: int, nextExpr: int[] = nextToken(expr);
        if (isNum & op > 0) {
            lhs = {performOp(lhs, op, token)};
            op = -1;
        } 
        else if (isNum & op == -1) {
            lhs = {token};
        }
        else if (!isNum & op == -1) {
            op = token;
        }
        else if (token == -2) {
            expr = nextExpr;
        }
        else {
            println("Something wrong happened in interpret");
            return -1;
        }
        expr = nextExpr;
    }
    if (length(lhs) > 0) {
        return lhs[0];
    }
    else {
        return 0;
    }
}

main(args: int[][]) {
    i: int = 0;
    size: int = length(args);
    while (i < size) {
        println(unparseInt(interpret(args[i])));
        i = i + 1;
    }
}