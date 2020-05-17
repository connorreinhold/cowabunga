main(args: int[][]) {
    i: int = 0;
    a: int;
    while (i < 100) {
        a = 13;  // Copy propagation in loops
        b: int = a;
        c: int = b;
        d: int = c;
        a = d;
        i = i + a;
    }
    a = randomCall(1,2,3);
    z: int = {1, 2, 3, 4, 5, 6, 7}[a];
    if (z != 7) z = {}[0]; else return;
}
randomCall(a: int, b: int, c: int): int {
    // Copy propagate dead assignments
    v: int = b; w: int = a; x: int = a; y: int = b; z: int = c;
    return x + y + z;
}