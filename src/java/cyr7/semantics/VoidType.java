package cyr7.semantics;

public class VoidType implements OrdinaryType {
    // The empty array {} is an array type of ArrayType(VoidType.VOID)

    @Override
    public Type getType() {
        return Type.VOID;
    }

    @Override
    public boolean isSubtypeOf(OrdinaryType expectedSupertype) {
        return true;
    }
}
