main(args: int[][]) {
    this_works: int[][] = {{} + {}}
    this_works2: int[][] = {{}}

    this_breaks: int[][] = {{}} + {{} + {}}
    this_breaks_2: int[][] = {{} + {}} + {{}}
}
