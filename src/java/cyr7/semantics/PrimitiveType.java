package cyr7.semantics;

public final class PrimitiveType extends OrdinaryType {
    
    final private Type type;
    
    protected PrimitiveType(Type type) {
        assert type != Type.ARRAY;
        this.type = type;
    }
    
    @Override
    public String toString() {
        return this.type.toString().toLowerCase();
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
