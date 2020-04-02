use io
use conv

main(args: int[][]) {
    i: int = 23;
    unparsed: int[] = unparseInt(i);
    println(unparsed);
    
    generated: int = generate();
    println(unparseInt(generated));
}

generate(): int {
    return 1000;
}