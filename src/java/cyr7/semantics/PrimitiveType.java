package cyr7.semantics;

public class PrimitiveType extends OrdinaryType {
    
    final private Type type;
    
    public static PrimitiveType intType = 
            new PrimitiveType(OrdinaryType.Type.INT);

    public static PrimitiveType boolType = 
            new PrimitiveType(OrdinaryType.Type.BOOL);

    public PrimitiveType(Type type) {
        assert(type != Type.ARRAY);
        this.type = type;
    }
    
    @Override
    public Type getType() {
        return this.type;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof PrimitiveType) {
            return this.type.equals(((PrimitiveType)o).type);
        }
        return false;
    }

    @Override
    public boolean isSubtypeOf(OrdinaryType expectedSupertype) {
        return expectedSupertype.isUnit() || 
                    this.type == expectedSupertype.getType();
    }
}
