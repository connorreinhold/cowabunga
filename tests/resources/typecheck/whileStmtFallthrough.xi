main(b: bool): bool {
    while (b) {
        return false
    }

    // while can always fall through

    return true
}