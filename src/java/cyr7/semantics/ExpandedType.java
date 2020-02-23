package cyr7.semantics;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import cyr7.semantics.ExpandedType.Type;

public class ExpandedType implements AnyType {
    
    private static ExpandedType boolType = 
                new ExpandedType(new PrimitiveType(OrdinaryType.Type.BOOL));
    private static ExpandedType intType = 
            new ExpandedType(new PrimitiveType(OrdinaryType.Type.INT));

    
    private List<OrdinaryType> types;
    
    /**
     * Creates a Unit ExpandedType.
     */
    public ExpandedType() {
        this.types = Collections.unmodifiableList(List.of());
    }
    
    /**
     * Creates an OrdinaryType ExpandedType.
     */
    public ExpandedType(OrdinaryType type) {
        this.types = Collections.unmodifiableList(List.of(type));
    }

    
    
    /**
     * Creates a Tuple ExpandedType.
     */
    public ExpandedType(List<OrdinaryType> types) {
        this.types = Collections.unmodifiableList(types);
    }
    
    enum Type {
        ORDINARY, TUPLE, UNIT;
    }
    
    public List<OrdinaryType> getTypes() {
        return this.types;
    }
    
    
    /**
     * Returns {@code true} if {@code this} is a subtype of 
     * {@code supertypeSet}. The following rules determine if ExpandedType x 
     * is a subtype of ExpandedType y:
     * 
     * <ul>
     * <li> If x and y are both Unit, then x is a subtype of y because 
     * there are no ordinary types.</li> 
     * <li> If x and y are both Ordinary, then x is a subtype of y if and
     * only if the OrdinaryType of x is a subtype of the ordinary type of y.</li>
     * <li> If x and y are both Tuples, then x is a subtype of y if and only if
     * the size of x == size of y, and for each OrdinaryType x_i of x and each
     * OrdinaryType y_i in y, x_i is a subtype of y_i. </li>
     * <li> If size of x != size of y, then x is not a subtype of y.</li>
     * </ul>.  
     * 
     * @param supertypeSet 
     * @return
     */
    public boolean isASubtypeOf(ExpandedType supertypeSet) {
        if (this.types.size() != supertypeSet.types.size()) {
            return false;
        }
        
        Iterator<OrdinaryType> expectedSubtype = this.types.iterator();
        Iterator<OrdinaryType> expectedSupertype = supertypeSet.types.iterator();
        
        boolean isSubtype = true;
        while (expectedSubtype.hasNext() && expectedSupertype.hasNext()) {
            OrdinaryType subtype = expectedSubtype.next();
            OrdinaryType supertype = expectedSupertype.next();
            
            isSubtype &= subtype.isSubtypeOf(supertype);
        }
        
        return isSubtype;
    }
    
    public Type getType() {
        int size = this.types.size();
        switch (size) {
        case 0: return Type.UNIT;
        case 1: return Type.ORDINARY;
        default: return Type.TUPLE;
        }
    }
    
    public boolean isOrdinary() {
        return this.types.size() == 1;
    }
    
    public boolean isArray() {
        assert(this.isOrdinary());
        return this.getOrdinaryType().getType() == OrdinaryType.Type.ARRAY;
    }
    
    public OrdinaryType getOrdinaryType() {
        assert(this.isOrdinary());
        return this.types.get(0);
    }

    public ArrayType getArrayType() {
        assert(this.isOrdinary() && this.isArray());
        return (ArrayType)this.getOrdinaryType();
    }

    
    public boolean isTuple() {
        return this.types.size() >= 2;
    }
    
    public boolean isUnit() {
        return this.types.isEmpty();
    }
        
    public boolean isSubtypeOfInt() {
        return this.isASubtypeOf(ExpandedType.intType);
    }

    public boolean isSubtypeOfBool() {
        return this.isASubtypeOf(ExpandedType.boolType);
    }

    
}
