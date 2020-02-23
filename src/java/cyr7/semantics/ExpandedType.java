package cyr7.semantics;

public interface ExpandedType extends AnyType {

    enum Type {
        PRIMITIVE, UNIT, TUPLE, ARRAY, VOID;
    }

    Type getType();

    default boolean isOrdinary() {
        Type type = getType();
        return type == Type.PRIMITIVE || type == Type.VOID || type == Type.ARRAY;
    }

    default boolean isExpanded() {
        Type type = getType();
        return type == Type.TUPLE || type == Type.UNIT;
    }
    
    default boolean isTuple() {
        return getType() == Type.TUPLE;
    }
    
    default boolean isUnit() {
        return getType() == Type.UNIT;
    }
    
    default boolean isArray() {
        return getType() == Type.ARRAY;
    }
    
}
