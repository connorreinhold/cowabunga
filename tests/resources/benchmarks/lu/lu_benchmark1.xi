main(args: int[][]) {
     a:int[10000][10000]
     rows:int = length(a) - 1
     cols:int = length(a[0]) - 1
     i:int = 0
     j:int = 0
     while(i < rows) {
	while (j < cols) {
             a[i][j] = i*j
             j = j + 1
        }
        i = i + 1
     }
}