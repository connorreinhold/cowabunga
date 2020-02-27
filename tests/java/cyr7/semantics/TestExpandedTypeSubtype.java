package cyr7.semantics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class TestExpandedTypeSubtype {

    @Test
    void testUnitExpandedTypeMethods() {
        assertFalse(ExpandedType.unitExpandedType.isASubtypeOf(
                ExpandedType.voidOrdinaryType));
        assertFalse(ExpandedType.unitExpandedType.isASubtypeOf(
                                                ExpandedType.boolType));
        assertFalse(ExpandedType.unitExpandedType.isASubtypeOf(
                                                ExpandedType.unitOrdinaryType));
        assertFalse(ExpandedType.unitExpandedType.isASubtypeOf(
                                                ExpandedType.intType));
        assertFalse(ExpandedType.unitExpandedType.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.intDefault))));
        assertFalse(ExpandedType.unitExpandedType.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.boolDefault))));
        assertFalse(ExpandedType.unitExpandedType.isASubtypeOf(
                new ExpandedType(new ArrayType(VoidType.voidValue))));    
        
        
        assertTrue(ExpandedType.unitExpandedType.isUnit());
        assertFalse(ExpandedType.unitExpandedType.isOrdinary());
        assertFalse(ExpandedType.unitExpandedType.isSubtypeOfInt());
        assertFalse(ExpandedType.unitExpandedType.isSubtypeOfBool());
        assertFalse(ExpandedType.unitExpandedType.isArray());
        assertFalse(ExpandedType.unitExpandedType.isTuple());
    }

    @Test
    void testArrayOrdinaryTypeMethods() {
        ExpandedType intArray = new ExpandedType(
                new ArrayType(PrimitiveType.intDefault));
        ExpandedType intDoubleArray = new ExpandedType(
                new ArrayType(
                        new ArrayType(PrimitiveType.intDefault)));
        ExpandedType intTripleArray = new ExpandedType(
                new ArrayType(
                        new ArrayType(
                                new ArrayType(PrimitiveType.intDefault))));
        
        assertTrue(intArray.isASubtypeOf(intArray));
        assertTrue(intDoubleArray.isASubtypeOf(intDoubleArray));
        assertTrue(intTripleArray.isASubtypeOf(intTripleArray));

        assertFalse(intArray.isASubtypeOf(intDoubleArray));
        assertFalse(intArray.isASubtypeOf(intTripleArray));
        assertFalse(intDoubleArray.isASubtypeOf(intArray));
        assertFalse(intDoubleArray.isASubtypeOf(intTripleArray));
        assertFalse(intTripleArray.isASubtypeOf(intArray));
        assertFalse(intTripleArray.isASubtypeOf(intDoubleArray));
        
        
        assertTrue(intArray.isASubtypeOf(ExpandedType.unitOrdinaryType));
        assertFalse(intArray.isASubtypeOf(ExpandedType.unitExpandedType));
        assertFalse(intArray.isASubtypeOf(ExpandedType.voidOrdinaryType));
        assertFalse(intArray.isASubtypeOf(ExpandedType.intType));
        assertFalse(intArray.isASubtypeOf(ExpandedType.boolType));
        
        assertTrue(intDoubleArray.isASubtypeOf(ExpandedType.unitOrdinaryType));
        assertFalse(intDoubleArray.isASubtypeOf(ExpandedType.unitExpandedType));
        assertFalse(intDoubleArray.isASubtypeOf(ExpandedType.voidOrdinaryType));
        assertFalse(intDoubleArray.isASubtypeOf(ExpandedType.intType));
        assertFalse(intDoubleArray.isASubtypeOf(ExpandedType.boolType));

        assertTrue(intTripleArray.isASubtypeOf(ExpandedType.unitOrdinaryType));
        assertFalse(intTripleArray.isASubtypeOf(ExpandedType.unitExpandedType));
        assertFalse(intTripleArray.isASubtypeOf(ExpandedType.voidOrdinaryType));
        assertFalse(intTripleArray.isASubtypeOf(ExpandedType.intType));
        assertFalse(intTripleArray.isASubtypeOf(ExpandedType.boolType));
        

        
        assertTrue(intArray.isArray());
        assertTrue(intArray.isOrdinary());
        assertFalse(intArray.isSubtypeOfInt());
        assertFalse(intArray.isSubtypeOfBool());
        assertFalse(intArray.isTuple());
        assertFalse(intArray.isUnit());
        
        assertTrue(intDoubleArray.isArray());
        assertTrue(intDoubleArray.isOrdinary());
        assertFalse(intDoubleArray.isSubtypeOfInt());
        assertFalse(intDoubleArray.isSubtypeOfBool());
        assertFalse(intDoubleArray.isTuple());
        assertFalse(intDoubleArray.isUnit());

        
        assertTrue(intTripleArray.isArray());
        assertTrue(intTripleArray.isOrdinary());
        assertFalse(intTripleArray.isSubtypeOfInt());
        assertFalse(intTripleArray.isSubtypeOfBool());
        assertFalse(intTripleArray.isTuple());
        assertFalse(intTripleArray.isUnit());
        
    }

    
    
    @Test
    void testIntOrdinaryTypeMethods() {
        assertTrue(ExpandedType.intType.isASubtypeOf(ExpandedType.intType));
        assertTrue(ExpandedType.intType.isASubtypeOf(
                                            ExpandedType.unitOrdinaryType));
        
        assertTrue(ExpandedType.intType.isOrdinary());
        assertTrue(ExpandedType.intType.isSubtypeOfInt());
        assertFalse(ExpandedType.intType.isSubtypeOfBool());
        assertFalse(ExpandedType.intType.isArray());
        assertFalse(ExpandedType.intType.isUnit());
        assertFalse(ExpandedType.intType.isTuple());

        
        assertFalse(ExpandedType.intType.isASubtypeOf(
                ExpandedType.unitExpandedType));
        assertFalse(ExpandedType.intType.isASubtypeOf(ExpandedType.boolType));
        assertFalse(ExpandedType.intType.isASubtypeOf(
                                            ExpandedType.voidOrdinaryType));
        assertFalse(ExpandedType.intType.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.intDefault))));
        assertFalse(ExpandedType.intType.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.boolDefault))));
        assertFalse(ExpandedType.intType.isASubtypeOf(
                new ExpandedType(new ArrayType(VoidType.voidValue))));    
    }
    
    @Test
    void testBoolOrdinaryTypeMethods() {
        assertTrue(ExpandedType.boolType.isASubtypeOf(ExpandedType.boolType));
        assertTrue(ExpandedType.boolType.isASubtypeOf(
                                            ExpandedType.unitOrdinaryType));
        
        
        assertTrue(ExpandedType.boolType.isOrdinary());
        assertTrue(ExpandedType.boolType.isSubtypeOfBool());
        assertFalse(ExpandedType.boolType.isSubtypeOfInt());
        assertFalse(ExpandedType.boolType.isArray());
        assertFalse(ExpandedType.boolType.isUnit());
        assertFalse(ExpandedType.boolType.isTuple());
        
        assertFalse(ExpandedType.boolType.isASubtypeOf(ExpandedType.intType));
        assertFalse(ExpandedType.boolType.isASubtypeOf(
                                            ExpandedType.voidOrdinaryType));
        assertFalse(ExpandedType.boolType.isASubtypeOf(
                                               ExpandedType.unitExpandedType));
        assertFalse(ExpandedType.boolType.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.intDefault))));
        assertFalse(ExpandedType.boolType.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.boolDefault))));
        assertFalse(ExpandedType.boolType.isASubtypeOf(
                new ExpandedType(new ArrayType(VoidType.voidValue))));    
    }
    
    
    @Test
    void testUnitOrdinaryTypeMethods() {
        
        
        assertTrue(ExpandedType.unitOrdinaryType.isASubtypeOf(
                ExpandedType.unitOrdinaryType));
        assertFalse(ExpandedType.unitOrdinaryType.isASubtypeOf(
                                                ExpandedType.boolType));
        assertFalse(ExpandedType.unitOrdinaryType.isASubtypeOf(
                                                ExpandedType.unitExpandedType));
        assertFalse(ExpandedType.unitOrdinaryType.isASubtypeOf(
                                                ExpandedType.intType));
        assertFalse(ExpandedType.unitOrdinaryType.isASubtypeOf(
                                                ExpandedType.voidOrdinaryType));
        assertFalse(ExpandedType.unitOrdinaryType.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.intDefault))));
        assertFalse(ExpandedType.unitOrdinaryType.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.boolDefault))));
        assertFalse(ExpandedType.unitOrdinaryType.isASubtypeOf(
                new ExpandedType(new ArrayType(VoidType.voidValue))));    
        
        
        assertTrue(ExpandedType.unitOrdinaryType.isOrdinary());
        assertFalse(ExpandedType.unitOrdinaryType.isSubtypeOfInt());
        assertFalse(ExpandedType.unitOrdinaryType.isSubtypeOfBool());
        assertFalse(ExpandedType.unitOrdinaryType.isArray());
        assertFalse(ExpandedType.unitOrdinaryType.isUnit());
        assertFalse(ExpandedType.unitOrdinaryType.isTuple());
    }
    
    @Test
    void testVoidOrdinaryTypeMethods() {
        assertTrue(ExpandedType.voidOrdinaryType.isASubtypeOf(
                ExpandedType.voidOrdinaryType));
        assertTrue(ExpandedType.voidOrdinaryType.isASubtypeOf(
                                                ExpandedType.boolType));
        assertTrue(ExpandedType.voidOrdinaryType.isASubtypeOf(
                                                ExpandedType.unitOrdinaryType));
        assertFalse(ExpandedType.voidOrdinaryType.isASubtypeOf(
                                                ExpandedType.unitExpandedType));
        assertTrue(ExpandedType.voidOrdinaryType.isASubtypeOf(
                                                ExpandedType.intType));
        assertTrue(ExpandedType.voidOrdinaryType.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.intDefault))));
        assertTrue(ExpandedType.voidOrdinaryType.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.boolDefault))));
        assertTrue(ExpandedType.voidOrdinaryType.isASubtypeOf(
                new ExpandedType(new ArrayType(VoidType.voidValue))));   
        
        assertTrue(ExpandedType.voidOrdinaryType.isOrdinary());
        assertTrue(ExpandedType.voidOrdinaryType.isSubtypeOfInt());
        assertTrue(ExpandedType.voidOrdinaryType.isSubtypeOfBool());
        assertFalse(ExpandedType.voidOrdinaryType.isArray());
        assertFalse(ExpandedType.voidOrdinaryType.isUnit());
        assertFalse(ExpandedType.voidOrdinaryType.isTuple());
    }

    @Test
    void testTupleTypeMethods() {
        List<OrdinaryType> types = List.of(
                PrimitiveType.boolDefault, PrimitiveType.intDefault, 
                new ArrayType(PrimitiveType.intDefault));
        ExpandedType tuple = new ExpandedType(types);
        
        List<OrdinaryType> wildcards = List.of(
                UnitType.unitValue, UnitType.unitValue,
                UnitType.unitValue);
        ExpandedType wildcardTuple = new ExpandedType(wildcards);


        List<OrdinaryType> someWildcards = List.of(
                PrimitiveType.boolDefault, UnitType.unitValue,
                new ArrayType(PrimitiveType.intDefault));
        ExpandedType someWildTuple = new ExpandedType(someWildcards);
        
        List<OrdinaryType> incorrectSizeWildcards = List.of(
                UnitType.unitValue, UnitType.unitValue,
                UnitType.unitValue, UnitType.unitValue);

        ExpandedType incorrectSizeWildTuple = 
                    new ExpandedType(incorrectSizeWildcards);
        
        assertTrue(tuple.isTuple());

        assertTrue(tuple.isASubtypeOf(tuple));
        assertTrue(tuple.isASubtypeOf(wildcardTuple));
        assertTrue(tuple.isASubtypeOf(someWildTuple));
        assertFalse(tuple.isASubtypeOf(incorrectSizeWildTuple));
        
        assertFalse(tuple.isASubtypeOf(ExpandedType.voidOrdinaryType));
        assertFalse(tuple.isASubtypeOf(ExpandedType.boolType));
        assertFalse(tuple.isASubtypeOf(ExpandedType.unitOrdinaryType));
        assertFalse(tuple.isASubtypeOf(ExpandedType.unitExpandedType));
        assertFalse(tuple.isASubtypeOf(ExpandedType.intType));
        assertFalse(tuple.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.intDefault))));
        assertFalse(tuple.isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.boolDefault))));
        assertFalse(tuple.isASubtypeOf(
                new ExpandedType(new ArrayType(VoidType.voidValue))));   
        
        assertFalse(tuple.isOrdinary());
        assertFalse(tuple.isSubtypeOfInt());
        assertFalse(tuple.isSubtypeOfBool());
        assertFalse(tuple.isArray());
        assertFalse(tuple.isUnit());
    }

    
}
