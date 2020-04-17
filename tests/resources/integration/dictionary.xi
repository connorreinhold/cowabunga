use io
use conv

printlnBool(b: bool) {
    if (b) {
        println("true")
        } else {
        println("false")
    }
}

splice(arr: int[][], start: int, end: int): int[][] {
    size: int = end - start
    splicedArr: int[size][]
    
    i: int = start
    while (i < end) {
        splicedArr[i - start] = arr[i]
        i = i + 1;
    }
    return splicedArr;
}

init_int_dict(): int[][] {
    return {};
}

add_int_dict(dict: int[][], key: int, value: int): int[][] {
    i: int = 0;
    size: int = length(dict);
    while (i < size) {
        if (dict[i][0] == key) {
            dict[i][1] = value;
            return dict;
        }
        i = i + 1;
    }
    return dict + {{key, value}};
}

rm_int_dict(dict: int[][], key: int): int[][] {
    i: int = 0;
    size: int = length(dict);
    while (i < size) {
        if (dict[i][0] == key) {
            return splice(dict, 0, i) + splice(dict, i + 1, size);
        }
        i = i + 1;
    }
    return dict;
}

get_int_dict(dict: int[][], key: int): int, bool {
    i: int = 0;
    size: int = length(dict);
    while (i < size) {
        if (dict[i][0] == key) {
            return dict[i][1], true;
        }
        i = i + 1;
    }
    return 0, false;
}

main(args: int[][]) {
    dict: int[][] = init_int_dict();
    dict = add_int_dict(dict, 1, 12);
    dict = add_int_dict(dict, 2, 22);
    dict = add_int_dict(dict, 3, 32);
    println("Length: " + unparseInt(length(dict)));
    
    dict = rm_int_dict(dict, 2);
    println("Length: " + unparseInt(length(dict)));
    dict = rm_int_dict(dict, 123);
    println("Length: " + unparseInt(length(dict)));
    
    value1: int, found1: bool = get_int_dict(dict, 1);
    value2: int, found2: bool = get_int_dict(dict, 2);
    println("Length: " + unparseInt(length(dict)));
    println("Value1: " + unparseInt(value1));
    print("Found1: ");
    printlnBool(found1);
    
    println("Value2: " + unparseInt(value2));
    print("Found2: ");
    printlnBool(found2);
    
}