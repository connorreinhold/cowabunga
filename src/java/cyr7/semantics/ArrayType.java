package cyr7.semantics;

public class ArrayType extends OrdinaryType {

    /**
     * May be a primitive type or another array.
     */
    public final OrdinaryType child;

    public ArrayType(OrdinaryType child) {
        this.child = child;
    }

    @Override
    public Type getType() {
        return Type.ARRAY;
    }
    
    @Override
    public String toString() {
        return child.toString() + "[]";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ArrayType) {
            return this.child.equals(((ArrayType)o).child);
        }
        return false;
    }

    @Override
    public boolean isSubtypeOf(OrdinaryType expectedSupertype) {
        if (expectedSupertype.isUnit()) {
            return true;
        }
        if (expectedSupertype instanceof ArrayType) {
            return this.child.isSubtypeOf(((ArrayType)expectedSupertype).child);
        }
        return false;
    }

}
