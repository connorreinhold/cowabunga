// This program demonstrates how CTranslationVisitor can insert labels after
// the return statement of a function and how the program's semantics are not
// modified because of it.

main(a: int[][]) {
    _ = f(true)
}

f(x: bool): int {
    if x {
        return 0
    } else {
        return 1
    }
}
