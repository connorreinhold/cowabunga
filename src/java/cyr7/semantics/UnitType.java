package cyr7.semantics;

public class UnitType extends OrdinaryType {
    // The wildcard _ is a unit type, i.e. a supertype of every type.

    @Override
    public Type getType() {
        return Type.UNIT;
    }

    /**
     * The only type that is a supertype of Unit is Unit itself.
     * <p>
     * Returns {@code true} if {@code expectedSupertype} is a Unit.
     * Otherwise, returns false.
     */
    @Override
    public boolean isSubtypeOf(OrdinaryType expectedSupertype) {
        return expectedSupertype.isUnit();
    }
}
