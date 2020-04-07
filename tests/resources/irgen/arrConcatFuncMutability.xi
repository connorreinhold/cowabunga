// from: https://piazza.com/class/k5eh0c04nur545?cid=291
use conv
use io

main (args : int[][])
{
   a : int[] = {1}
   b : int[] = a + foo(a)

   print("[")
   i: int = 0
   while i < length(b) {
        print(unparseInt(b[i]))
        print(" ")
        i = i + 1
   }
   print("]")
}

foo (a : int[]) : int[]
{
   a[0] = 2
   return {3}
}