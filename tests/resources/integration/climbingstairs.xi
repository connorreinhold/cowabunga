use io
use conv

main(args: int[][]) {
	val1:int = climbStairs(1);
	val2:int = climbStairs(5);
	val3:int = climbStairs(6);
	val4:int = climbStairs(-1);
	val5:int = climbStairs(10);
	println(unparseInt(val1));
    println(unparseInt(val2));
    println(unparseInt(val3));
    println(unparseInt(val4));
    println(unparseInt(val5));
	
}

// return the number of ways to take n steps where you can take 1 or 2 steps at a time
climbStairs(n:int):int {
    if (n < 1) {
        return 0;
    }
    arr:int[] = {1,2};
    i: int = 2;
    while(i < n) {
        arr = arr + {arr[i-1]+arr[i-2]}
        i = i + 1;
    }
    return arr[n-1];
}