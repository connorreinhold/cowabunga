use io
main(args:int[][]){
  a:int, b:int[], c:int, d:int[][], e:int, f:int, g:int[], h:int[][] =
  foo(500,101,-1,-2,-3,-4,"andrew"+"myers",{{1,2,3},"my\x65rs"})
  error:int[] = "a"
  if(a != 500){x:int = error[10]}
  if(b[0]!=500|b[1]!=101|b[2]!=-1){x:int = error[10]}
  if(!(c== 19)){x:int = error[10]}
  if(!(d[0][0]==500)&!(d[1][1]==-2)){x:int = error[10]}
  if(e!='m'){x:int = error[10]}
  if(f!='e'){x:int = error[10]}
  if(g[10]!='s'){x:int = error[10]}
  if(h[0][1]!=2){x:int = error[10]}
  if(length(h[1])!=5){x:int = error[10]}
  println("success");
}
foo(a:int, b:int, c:int, d:int, e:int, f:int, g:int[], h:int[][]):
  int, int[], int, int[][], int, int, int[], int[][]{
  return a, {a,b,c}, ((a/b)*a)/b, {{a,b},{c,d}}, g[6], h[1][2], g, h
}