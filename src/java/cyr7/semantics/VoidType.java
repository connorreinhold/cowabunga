package cyr7.semantics;

public enum VoidType implements OrdinaryType {
    VOID // The empty array {} is an array type of ArrayType(VoidType.VOID)
    ;

    @Override
    public Type getType() {
        return Type.VOID;
    }
}
