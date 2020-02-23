package cyr7.semantics;

public interface OrdinaryType {
    
    public enum Type {
        // OrdinaryType.Type.UNIT and OrdinaryType.Type are to be distinct
        // from ResultType.VOID and ResultType.UNIT.
        INT, BOOL, ARRAY, VOID, UNIT;
    }
    
    public Type getType();
    
    public boolean isSubtypeOf(OrdinaryType expectedSupertype);
}
