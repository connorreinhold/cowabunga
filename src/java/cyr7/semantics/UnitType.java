package cyr7.semantics;

public final class UnitType extends OrdinaryType {
    // The wildcard _ is a unit type, i.e. a supertype of every type.
    
    protected UnitType() {}
    
    @Override
    public Type getType() {
        return Type.UNIT;
    }

    @Override
    public String toString() {
        return "unit";
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

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UnitType;
    }

}
