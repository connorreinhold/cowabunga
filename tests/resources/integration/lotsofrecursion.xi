use io

main(args: int[][]) {
    recurse(0)
    chainRecurse(0)
}

recurse(level: int) {
    if (level >= 500) {
        return
    }
    recurse(level + 1)
}

chainRecurse(level: int) {
    if (level >= 300) {
        return
    }
    print("a")
    recurse2(level+1)
}

recurse2(level: int) {
    print("b")
    recurse3(level + 1)
}

recurse3(level: int) {
    print("c")
    recurse4(level + 1)
}

recurse4(level: int) {
    print("d")
    chainRecurse(level + 1)
}