foo(): bool, int {
  ok = {1,4,5,6,3} 
  expr: int = 1 - 2 * 3 * -4 * 32 * length({})
  pred: bool = true & true | false;
  if (expr <= 47) { }
  else pred = !pred
  if (pred) { expr = 59 };;;;;;;
  return pred
}

bar() {
  _, i: int = foo()
  b: int[i][]
  b[0] = {1, 0}
}
