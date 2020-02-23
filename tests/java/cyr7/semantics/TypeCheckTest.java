package cyr7.semantics;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TypeCheckTest {

    @Test
    void testIntOrdinaryTypeMethods() {
        assertTrue(ExpandedType.intType.isASubtypeOf(ExpandedType.intType));
        assertTrue(ExpandedType.intType.isASubtypeOf(
                                            ExpandedType.unitOrdinaryType));
        assertFalse(ExpandedType.intType.isASubtypeOf(ExpandedType.boolType));
        assertFalse(ExpandedType.intType.isASubtypeOf(
                                            ExpandedType.voidOrdinaryType));
        assertFalse(ExpandedType.intType.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.intType))));
        assertFalse(ExpandedType.intType.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.boolType))));
        assertFalse(ExpandedType.intType.isASubtypeOf(
                new ExpandedType(new ArrayType(VoidType.value))));    
    }
    
    @Test
    void testBoolOrdinaryTypeMethods() {
        assertTrue(ExpandedType.boolType.isASubtypeOf(ExpandedType.boolType));
        assertTrue(ExpandedType.boolType.isASubtypeOf(
                                            ExpandedType.unitOrdinaryType));
        assertFalse(ExpandedType.boolType.isASubtypeOf(ExpandedType.intType));
        assertFalse(ExpandedType.boolType.isASubtypeOf(
                                            ExpandedType.voidOrdinaryType));
        assertFalse(ExpandedType.boolType.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.intType))));
        assertFalse(ExpandedType.boolType.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.boolType))));
        assertFalse(ExpandedType.boolType.isASubtypeOf(
                new ExpandedType(new ArrayType(VoidType.value))));    
    }
    
    
    @Test
    void testUnitOrdinaryTypeMethods() {
        assertTrue(ExpandedType.unitOrdinaryType.isASubtypeOf(
                ExpandedType.unitOrdinaryType));
        assertFalse(ExpandedType.unitOrdinaryType.isASubtypeOf(
                                                ExpandedType.boolType));
        assertFalse(ExpandedType.unitOrdinaryType.isASubtypeOf(
                                                ExpandedType.intType));
        assertFalse(ExpandedType.unitOrdinaryType.isASubtypeOf(
                                                ExpandedType.voidOrdinaryType));
        assertFalse(ExpandedType.unitOrdinaryType.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.intType))));
        assertFalse(ExpandedType.unitOrdinaryType.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.boolType))));
        assertFalse(ExpandedType.unitOrdinaryType.isASubtypeOf(
                new ExpandedType(new ArrayType(VoidType.value))));    
    }
    
    @Test
    void testVoidOrdinaryTypeMethods() {
        assertTrue(ExpandedType.voidOrdinaryType.isASubtypeOf(
                ExpandedType.voidOrdinaryType));
        assertTrue(ExpandedType.voidOrdinaryType.isASubtypeOf(
                                                ExpandedType.boolType));
        assertTrue(ExpandedType.voidOrdinaryType.isASubtypeOf(
                                                ExpandedType.unitOrdinaryType));
        assertTrue(ExpandedType.voidOrdinaryType.isASubtypeOf(
                                                ExpandedType.intType));
        assertTrue(ExpandedType.voidOrdinaryType.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.intType))));
        assertTrue(ExpandedType.voidOrdinaryType.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.boolType))));
        assertTrue(ExpandedType.voidOrdinaryType.isASubtypeOf(
                new ExpandedType(new ArrayType(VoidType.value))));    
    }
    
}
