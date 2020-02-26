// Tests that we can properly use indexes.
main(ar:int[]) : int {
    a:int = ar["Hello"[{1, 3, 2}[1]]]

    return a
}