package cyr7.semantics;

public class VoidType extends OrdinaryType {
    // The empty array {} is an array type of ArrayType(VoidType.VOID)

    public static final VoidType value = new VoidType();
    private VoidType() {}
    
    @Override
    public Type getType() {
        return Type.VOID;
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
}
