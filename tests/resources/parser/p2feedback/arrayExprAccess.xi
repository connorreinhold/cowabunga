f(x:int, y:int[]) {
  x = {1,2,3}[0]
  x = {1,2,3}['a']
  x = {1,2,3}[x]
  x = {1,2,3}[i()]
  x = {1,2,3}[y[0]]
  x = {1,2,3}[-1]
  x = {1,2,3}[0+0]
  x = {1,2,3}[(0)]
}

i(): int {
  return 0
}