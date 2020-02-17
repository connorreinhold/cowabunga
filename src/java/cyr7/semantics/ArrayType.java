package cyr7.semantics;

public class ArrayType implements Type {

    public final Type child;

    ArrayType(Type child) {
        this.child = child;
    }

}
