package cyr7.semantics;

public enum PrimitiveType implements OrdinaryType {
    INT, 
    BOOL,
    ANY // The empty array {} is an array type of ArrayType(PrimitiveType.ANY)
}
