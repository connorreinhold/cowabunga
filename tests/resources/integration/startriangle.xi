use io

main(args: int[][]) {
    i: int = 0
    j: int = 0
    rows: int = 10

    while i < rows {
        j = 0
        while j < i {
            print("*")
            j = j + 1
        }
        i = i + 1
        println("")
    }

    j = 0
    i = rows
    while i >= 0 {
        j = 0
        while j <= i {
            print("*")
            j = j + 1
        }
        i = i - 1
        println("")
    }
}