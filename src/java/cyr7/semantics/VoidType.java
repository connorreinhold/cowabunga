package cyr7.semantics;

public class VoidType extends OrdinaryType {
    // The empty array {} is an array type of ArrayType(VoidType.VOID)

    protected VoidType() {}
    
    @Override
    public Type getType() {
        return Type.VOID;
    }

    @Override
    public String toString() {
        return "Any";
    }
    
    /**
     * Void is always a subtype of any other type.
     * <p>
     * Returns {@code true} for any type {@code expectedSupertype}.
     */
    @Override
    public boolean isSubtypeOf(OrdinaryType expectedSupertype) {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VoidType;
    }

}
