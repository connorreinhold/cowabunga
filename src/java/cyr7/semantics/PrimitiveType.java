package cyr7.semantics;

public class PrimitiveType implements OrdinaryType {
    
    private Type type;
    
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
        return this.type == expectedSupertype.getType();
    }
}
