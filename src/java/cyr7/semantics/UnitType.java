package cyr7.semantics;

public class UnitType implements OrdinaryType {
    // The wildcard _ is a unit type, i.e. a supertype of every type.

    @Override
    public Type getType() {
        return Type.VOID;
    }

    @Override
    public boolean isSubtypeOf(OrdinaryType expectedSupertype) {
        return expectedSupertype.getType() == Type.UNIT;
    }
}
