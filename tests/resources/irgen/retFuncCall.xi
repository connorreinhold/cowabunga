use io
use conv

main(args:int[][]) {
    a:int, b:int = getPair();
    print(unparseInt(a))
}

getPair():int,int {
    return 4, getNum();
}

getNum():int {
    return 3;
}