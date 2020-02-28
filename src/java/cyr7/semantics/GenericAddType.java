package cyr7.semantics;

public class GenericAddType extends OrdinaryType {

    @Override
    public String toString() {
        return "(int | Void [])";
    }

    final public static GenericAddType value = new GenericAddType();

    private GenericAddType() {}

    @Override
    public Type getType() {
        return Type.GENERIC_ADD;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof GenericAddType;
    }

    @Override
    public boolean isSubtypeOf(OrdinaryType expectedSupertype) {
        if (expectedSupertype.isUnit()) {
            return true;
        }
        if (expectedSupertype.isArray()) {
            return true;
        }
        if (expectedSupertype.isInt()) {
            return true;
        }
        return false;
    }
}
