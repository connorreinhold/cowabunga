package cyr7.semantics;

public enum PrimitiveType implements OrdinaryType {
    INT, 
    BOOL;

    @Override
    public Type getType() {
        return Type.PRIMITIVE;
    }
}
