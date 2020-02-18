package cyr7.semantics;

public class TypeCheckUtil {
    /**
     * Returns true if t1 and t2 are equivalent under our type system
     * 
     * @param t1
     * @param t2
     * @return whether t1 and t2 have equivalent types
     */
    public static boolean checkTypeEquality(ExpandedType t1, ExpandedType t2) {
        if (t1 instanceof UnitType && t2 instanceof UnitType) {
            return true;
        } else if (t1 instanceof TupleType && t2 instanceof TupleType) {
            TupleType tup1 = (TupleType) t1;
            TupleType tup2 = (TupleType) t2;
            if (tup1.elements.size() != tup2.elements.size()) {
                return false;
            }
            // Ensure all types of the tuple are compatible
            for (int i = 0; i < tup1.elements.size(); i++) {
                if (!checkTypeEquality(tup1.elements.get(i),
                        tup2.elements.get(i))) {
                    return false;
                }
            }
            return true;
        } else if (t1 == PrimitiveType.ANY || t2 == PrimitiveType.ANY) {
            // Only allow PrimitiveType.ANY to return true with OrdinaryTypes
            return true;
        } else if (t1 instanceof PrimitiveType) {
            return t1 == t2;
        } else if (t1 instanceof ArrayType && t2 instanceof ArrayType) {
            return checkTypeEquality(((ArrayType) t1).child,
                    ((ArrayType) t2).child);
        } else {
            return false;
        }
    }
}
