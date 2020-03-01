f(): int, int {
    return 0, 0
}

g(): int, int, bool {
    return 1, 2, false
}

h(): int, int, int, int[] {
    return 1, 2, 3, {1, 2, 3}
}

main() {
    _, i: int = f()
    _, k: int, _ = g()
    _, _, _, _ = h()
}