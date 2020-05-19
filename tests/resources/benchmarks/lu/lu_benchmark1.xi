main(args: int[][]) {
     a:int[][][] = {{{1},{2},{3}},{{4},{5},{6}},{{7},{8},{9}},{{10},{11},{12}}}
     b:int[f()][f()][f()]
     longstr:int[] = "aaaaaa"
     while (length(longstr) < 10000) {
          longstr = longstr + longstr
     }
     i:int = length(longstr) - 1
     otherlongstr = ""
     while(i > 0) {
        d:int = 5
        c:int = f()*f()*f()
        e:int = 9876/1234
        otherlongstr = otherlongstr + longstr[i]
        i = i - 1
     }
}
f():int {
   arr:int={1,2,3,4}
   d:int = 2
   while (d > 1) {
       d = d - 1
       return arr[0];
   }
   return -1;
}