use io
use conv

fib(): int {
	a1: int = 0
	a2: int = 1
	fibNum: int = 5
	while(fibNum > 0) {
		fibNum = fibNum - 1
		temp: int = a2
		a2 = a1 + a2
		a1 = temp
	}
	return a2;
}

main(args: int[][]) {
	print(unparseInt(fib()))
}