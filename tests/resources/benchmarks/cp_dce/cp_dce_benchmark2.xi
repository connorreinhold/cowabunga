main(args: int[][]) {
    a: int = 150;
    b: int = a;
    arr: int[] = {1,2,3,4,5,6,7,8,9,10}
    b = doMischievousComp(arr);
    if (arr[0] != 1000) {
        // Should not run if doMischievousComp(arr) is kept
        arr[1000] = 12;
    }
}
doBigComputations(arr: int[]): int {
    i: int = 0;
    while (i < length(arr)) {
        a: int = 12; b: int = 123; c: int = 1243;
        d: int = 9; e: int = 0; f: int = a;
        i = i + 1;
    }
    arr[0] = 1000;
    return 1000;
}