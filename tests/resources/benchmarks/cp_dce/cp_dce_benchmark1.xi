main(args: int[][]) {
    i: int = 0;
    a: int;
    while (i < 100) {
        a: int = 13;  // Copy propagation in loops
        b: int = a;
        c: int = b;
        a = c;
        i = i + a;
    }
    a = randomCall(1,2,3);
    z: int = {0, 0, 0, 0, 0, 0, 0}[a];
}

randomCall(a: int, b: int, c: int): int {
    // Copy propagate dead assignments
    x: int = a; y: int = b; z: int = c;
    return x + y + z;
}