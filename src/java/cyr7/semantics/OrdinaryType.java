package cyr7.semantics;

public abstract class OrdinaryType {
    
    public enum Type {
        // OrdinaryType.Type.UNIT and OrdinaryType.Type are to be distinct
        // from ResultType.VOID and ResultType.UNIT.
        INT, BOOL, ARRAY, VOID, UNIT;
    }
    
    public abstract Type getType();
    
    public boolean isUnit() {
        return this.getType() == Type.UNIT;
    }

    public boolean isVoid() {
        return this.getType() == Type.VOID;
    }
    
    public abstract boolean isSubtypeOf(OrdinaryType expectedSupertype);
}
