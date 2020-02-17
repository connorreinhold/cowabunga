package cyr7.semantics;

public class ArrayType implements Type {

    public final Type child;

    public ArrayType(Type child) {
        this.child = child;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof ArrayType) {
            return this.child.equals(((ArrayType)o).child);
        }
        return false;
    }

}
