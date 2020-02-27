
main(): int {
  a: int[] = "Hello" + " " + "World"
  b: int[] = {1,24,6,} + {3,5,7} + {};
  c: int = (3) - (4 + '2') * 'a' / '\t' % 2 + main()
  high: int = 4 *>> 12

  
  d: bool = (1 == 1) & (2 != 2) | true | false & false & (c < c) | (c > c) | (c <= main()) & (1 >= main()); 

  d = !d

  size: int = length({{},{},{},{},{}});

  multi_d: int[][] = {{}, {}, {}} + {{1,2,3}, {2,4,6}}
  result: bool = helper(1, true, {{}}) == true

  return exit();
}



helper(x: int, y: bool, z: int[][]): bool {
  return true
}

exit(): int {
  return 0
}

