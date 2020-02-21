package cyr7.semantics;

public enum UnitType implements ExpandedType {
    UNIT;

    @Override
    public Type getType() {
        return Type.UNIT;
    }
}
