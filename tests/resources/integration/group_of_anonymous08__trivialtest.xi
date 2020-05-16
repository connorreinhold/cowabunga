use io
use conv
main(args:int[][]){
	a:int = 9223372036854775807
	b:int = -9223372036854775808
	d:int =  -9223372036854775808 + 9223372036854775807
	c:int = a * b;
	println(unparseInt(a+b));
	println(unparseInt(c));
	if(a + b != -1){
		x:int[] = {a};
		y:int = x[12121];
	}
}