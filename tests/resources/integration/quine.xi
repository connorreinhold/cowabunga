use io
main(a: int[][]) {
    quote_string: int[] = { 34 }
    comma_string: int[] = { 44 }
    l: int[][] = {
        "use io",
        "main(a: int[][]) {",
        "    quote_string: int[] = { 34 }",
        "    comma_string: int[] = { 44 }",
        "    l: int[][] = {",
        "        ",
        "    }",
        "    i: int = 0",
        "    while i < 5 {",
        "        println(l[i])",
        "        i = i + 1",
        "    }",
        "    i = 0",
        "    while i < length(l) {",
        "        println(l[5] + quote_string + l[i] + quote_string + comma_string)",
        "        i = i + 1",
        "    }",
        "    i = 6",
        "    while i < length(l) {",
        "        println(l[i])",
        "        i = i + 1",
        "    }",
        "}",
    }
    i: int = 0
    while i < 5 {
        println(l[i])
        i = i + 1
    }
    i = 0
    while i < length(l) {
        println(l[5] + quote_string + l[i] + quote_string + comma_string)
        i = i + 1
    }
    i = 6
    while i < length(l) {
        println(l[i])
        i = i + 1
    }
}
