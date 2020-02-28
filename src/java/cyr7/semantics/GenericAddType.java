package cyr7.semantics;

/**
 * {@code GenericAddType} is the type of an addition operation when the two
 * operands are both void types. Addition must result in either an integer or
 * an array. {@code GenericAddType} is a subtype of integer, an array, and unit.
 * @author ayang
 *
 */
public class GenericAddType extends OrdinaryType {

    @Override
    public String toString() {
        return "(int | void[])";
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
        return expectedSupertype.isUnit() &&
                expectedSupertype.isArray() &&
                expectedSupertype.isInt() &&
                expectedSupertype.isGenericAdd();
    }
}
