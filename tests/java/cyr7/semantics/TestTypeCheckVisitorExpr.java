package cyr7.semantics;

import cyr7.ast.Node;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.access.ArrayAccessExprNode;
import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.*;
import cyr7.ast.expr.literalexpr.*;
import cyr7.ast.expr.unaryexpr.BoolNegExprNode;
import cyr7.ast.expr.unaryexpr.IntNegExprNode;
import cyr7.ast.stmt.assign.ArrayAssignNode;
import cyr7.ast.stmt.assign.VariableAssignNode;
import cyr7.exceptions.semantics.SemanticException;
import cyr7.typecheck.TypeCheckVisitor;
import cyr7.util.OneOfTwo;
import java_cup.runtime.ComplexSymbolFactory.Location;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestTypeCheckVisitorExpr {

    Context context;
    TypeCheckVisitor visitor;
    Node node;
    OneOfTwo<ExpandedType, ResultType> result;
    Location loc = new Location(0, 0);
    
    @Test
    void testIntNegExprNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new IntNegExprNode(loc, 
                new LiteralIntExprNode(loc, "13243546"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new IntNegExprNode(loc, 
                new LiteralStringExprNode(loc, "Hello World"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new IntNegExprNode(loc,
                new LiteralBoolExprNode(loc, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }

    @Test
    void testBoolNegExprNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new BoolNegExprNode(loc,
                new LiteralBoolExprNode(loc, true));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new BoolNegExprNode(loc, 
                new LiteralStringExprNode(loc, "Hello World"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BoolNegExprNode(loc, 
                new LiteralIntExprNode(loc, "13243546"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }

    
    @Test
    void testLiterals() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LiteralBoolExprNode(loc, false);
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LiteralCharExprNode(loc, "a");
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LiteralIntExprNode(loc, "1324");
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LiteralStringExprNode(loc, "Hello World");
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().isASubtypeOf(
                new ExpandedType(new ArrayType(OrdinaryType.intType))));
        assertTrue(result.assertFirst().isOrdinary());
    }   
    
    
    @Test
    void testAddExpr() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AddExprNode(loc, new LiteralIntExprNode(loc, "1324"), 
                                     new LiteralIntExprNode(loc, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());


        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AddExprNode(loc, new LiteralCharExprNode(loc, "a"), 
                                     new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AddExprNode(loc, new LiteralIntExprNode(loc, "134"), 
                                     new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AddExprNode(loc, new LiteralStringExprNode(loc, "1324"), 
                                     new LiteralStringExprNode(loc, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().isASubtypeOf(
                new ExpandedType(new ArrayType(OrdinaryType.intType))));
        assertTrue(result.assertFirst().isOrdinary());
        

        context = new HashMapStackContext();
        context.addVar("numbers", new ArrayType(OrdinaryType.intType));
        visitor = new TypeCheckVisitor(context);
        node = new AddExprNode(loc, new VariableAccessExprNode(loc, "numbers"),
                                     new LiteralStringExprNode(loc, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().isASubtypeOf(
                new ExpandedType(new ArrayType(OrdinaryType.intType))));
        assertTrue(result.assertFirst().isOrdinary());

        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AddExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralBoolExprNode(loc, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AddExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralIntExprNode(loc, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AddExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AddExprNode(loc, new LiteralIntExprNode(loc, "65432"), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        context.addVar("bools", new ArrayType(OrdinaryType.boolType));
        visitor = new TypeCheckVisitor(context);
        node = new AddExprNode(loc, new VariableAccessExprNode(loc, "bools"),
                                     new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }
    
    @Test
    void testEquality() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(loc, new LiteralIntExprNode(loc, "1324"), 
                                     new LiteralIntExprNode(loc, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());


        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(loc, new LiteralCharExprNode(loc, "a"), 
                                     new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(loc, new LiteralIntExprNode(loc, "134"), 
                                     new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(loc, new LiteralStringExprNode(loc, "1324"), 
                                     new LiteralStringExprNode(loc, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        

        context = new HashMapStackContext();
        context.addVar("numbers", new ArrayType(OrdinaryType.intType));
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(loc, new VariableAccessExprNode(loc, "numbers"),
                                     new LiteralStringExprNode(loc, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralBoolExprNode(loc, false));
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralIntExprNode(loc, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(loc, new LiteralIntExprNode(loc, "65432"), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        context.addVar("bools", new ArrayType(OrdinaryType.boolType));
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(loc, new VariableAccessExprNode(loc, "bools"),
                                     new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }

    
    @Test
    void testInequality() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(loc, new LiteralIntExprNode(loc, "1324"), 
                                     new LiteralIntExprNode(loc, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());


        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(loc, new LiteralCharExprNode(loc, "a"), 
                                     new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(loc, new LiteralIntExprNode(loc, "134"), 
                                     new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(loc, new LiteralStringExprNode(loc, "1324"), 
                                     new LiteralStringExprNode(loc, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        

        context = new HashMapStackContext();
        context.addVar("numbers", new ArrayType(OrdinaryType.intType));
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(loc, new VariableAccessExprNode(loc, "numbers"),
                                     new LiteralStringExprNode(loc, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralBoolExprNode(loc, false));
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralIntExprNode(loc, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(loc, new LiteralIntExprNode(loc, "65432"), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        context.addVar("bools", new ArrayType(OrdinaryType.boolType));
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(loc, new VariableAccessExprNode(loc, "bools"),
                                     new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }
    
    
    @Test
    void testIntegerBinaryExpr() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(loc, new LiteralIntExprNode(loc, "1324"), 
                                     new LiteralIntExprNode(loc, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new MultExprNode(loc, new LiteralIntExprNode(loc, "1324"), 
                new LiteralIntExprNode(loc, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new RemExprNode(loc, new LiteralIntExprNode(loc, "1324"), 
                new LiteralIntExprNode(loc, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new SubExprNode(loc, new LiteralIntExprNode(loc, "1324"), 
                new LiteralIntExprNode(loc, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new DivExprNode(loc, new LiteralIntExprNode(loc, "1324"), 
                new LiteralIntExprNode(loc, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());

        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(loc, new LiteralCharExprNode(loc, "a"), 
                                     new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new MultExprNode(loc, new LiteralCharExprNode(loc, "a"), 
                new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new RemExprNode(loc, new LiteralCharExprNode(loc, "a"), 
                new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new SubExprNode(loc, new LiteralCharExprNode(loc, "a"), 
                new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new DivExprNode(loc, new LiteralCharExprNode(loc, "a"), 
                new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(loc, new LiteralIntExprNode(loc, "134"), 
                                     new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new MultExprNode(loc, new LiteralIntExprNode(loc, "134"), 
                new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new RemExprNode(loc, new LiteralIntExprNode(loc, "134"), 
                new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new SubExprNode(loc, new LiteralIntExprNode(loc, "134"), 
                new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new DivExprNode(loc, new LiteralIntExprNode(loc, "134"), 
                new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(loc, new LiteralStringExprNode(loc, "str"), 
                                     new LiteralStringExprNode(loc, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new MultExprNode(loc, new LiteralStringExprNode(loc, "str"), 
                new LiteralStringExprNode(loc, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new RemExprNode(loc, new LiteralStringExprNode(loc, "str"), 
                new LiteralStringExprNode(loc, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new SubExprNode(loc, new LiteralStringExprNode(loc, "str"), 
                new LiteralStringExprNode(loc, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new DivExprNode(loc, new LiteralStringExprNode(loc, "str"), 
                new LiteralStringExprNode(loc, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        context = new HashMapStackContext();
        context.addVar("numbers", new ArrayType(OrdinaryType.intType));
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(loc, 
                                     new VariableAccessExprNode(loc, "numbers"),
                                     new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new MultExprNode(loc, new VariableAccessExprNode(loc, "numbers"),
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new RemExprNode(loc, new VariableAccessExprNode(loc, "numbers"),
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new SubExprNode(loc, new VariableAccessExprNode(loc, "numbers"),
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new DivExprNode(loc, new VariableAccessExprNode(loc, "numbers"),
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralBoolExprNode(loc, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new MultExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralBoolExprNode(loc, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new RemExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralBoolExprNode(loc, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new SubExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralBoolExprNode(loc, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new DivExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralBoolExprNode(loc, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralIntExprNode(loc, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new MultExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralIntExprNode(loc, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new RemExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralIntExprNode(loc, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new SubExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralIntExprNode(loc, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new DivExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralIntExprNode(loc, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new MultExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new RemExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new SubExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new DivExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(loc, new LiteralIntExprNode(loc, "65432"), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new MultExprNode(loc, new LiteralIntExprNode(loc, "65432"), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new RemExprNode(loc, new LiteralIntExprNode(loc, "65432"), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new SubExprNode(loc, new LiteralIntExprNode(loc, "65432"), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new DivExprNode(loc, new LiteralIntExprNode(loc, "65432"), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        context = new HashMapStackContext();
        context.addVar("bools", new ArrayType(OrdinaryType.boolType));
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(loc, new VariableAccessExprNode(loc, "bools"),
                                     new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new MultExprNode(loc, new VariableAccessExprNode(loc, "bools"),
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new RemExprNode(loc, new VariableAccessExprNode(loc, "bools"),
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new SubExprNode(loc, new VariableAccessExprNode(loc, "bools"),
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new DivExprNode(loc, new VariableAccessExprNode(loc, "bools"),
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }
    
    
    @Test
    void testComparisonExpr() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LTEExprNode(loc, new LiteralIntExprNode(loc, "1324"), 
                                     new LiteralIntExprNode(loc, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new LTExprNode(loc, new LiteralIntExprNode(loc, "1324"), 
                new LiteralIntExprNode(loc, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new GTEExprNode(loc, new LiteralIntExprNode(loc, "1324"), 
                new LiteralIntExprNode(loc, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new GTExprNode(loc, new LiteralIntExprNode(loc, "1324"), 
                new LiteralIntExprNode(loc, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());


        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new GTEExprNode(loc, new LiteralCharExprNode(loc, "a"), 
                                     new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new GTExprNode(loc, new LiteralCharExprNode(loc, "a"), 
                new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new LTEExprNode(loc, new LiteralCharExprNode(loc, "a"), 
                new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new LTExprNode(loc, new LiteralCharExprNode(loc, "a"), 
                new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());

        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LTEExprNode(loc, new LiteralIntExprNode(loc, "134"), 
                                     new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new LTExprNode(loc, new LiteralIntExprNode(loc, "134"), 
                new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new GTEExprNode(loc, new LiteralIntExprNode(loc, "134"), 
                new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new GTExprNode(loc, new LiteralIntExprNode(loc, "134"), 
                new LiteralCharExprNode(loc, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LTEExprNode(loc, new LiteralStringExprNode(loc, "str"), 
                                     new LiteralStringExprNode(loc, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new LTExprNode(loc, new LiteralStringExprNode(loc, "str"), 
                new LiteralStringExprNode(loc, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTEExprNode(loc, new LiteralStringExprNode(loc, "str"), 
                new LiteralStringExprNode(loc, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTExprNode(loc, new LiteralStringExprNode(loc, "str"), 
                new LiteralStringExprNode(loc, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        context = new HashMapStackContext();
        context.addVar("numbers", new ArrayType(OrdinaryType.intType));
        visitor = new TypeCheckVisitor(context);
        node = new LTEExprNode(loc, 
                                     new VariableAccessExprNode(loc, "numbers"),
                                     new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new LTExprNode(loc, new VariableAccessExprNode(loc, "numbers"),
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTEExprNode(loc, new VariableAccessExprNode(loc, "numbers"),
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTExprNode(loc, new VariableAccessExprNode(loc, "numbers"),
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LTEExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralBoolExprNode(loc, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new LTExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralBoolExprNode(loc, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTEExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralBoolExprNode(loc, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralBoolExprNode(loc, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LTEExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralIntExprNode(loc, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new LTExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralIntExprNode(loc, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTEExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralIntExprNode(loc, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralIntExprNode(loc, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LTEExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new LTExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTEExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LTEExprNode(loc, new LiteralIntExprNode(loc, "65432"), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new LTExprNode(loc, new LiteralIntExprNode(loc, "65432"), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTEExprNode(loc, new LiteralIntExprNode(loc, "65432"), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTExprNode(loc, new LiteralIntExprNode(loc, "65432"), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        
        context = new HashMapStackContext();
        context.addVar("bools", new ArrayType(OrdinaryType.boolType));
        visitor = new TypeCheckVisitor(context);
        node = new LTEExprNode(loc, new VariableAccessExprNode(loc, "bools"),
                                     new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new LTExprNode(loc, new VariableAccessExprNode(loc, "bools"),
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTEExprNode(loc, new VariableAccessExprNode(loc, "bools"),
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTExprNode(loc, new VariableAccessExprNode(loc, "bools"),
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }

    
    @Test
    void testBooleanOperators() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(loc, new LiteralIntExprNode(loc, "1324"), 
                                     new LiteralIntExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(loc, new LiteralIntExprNode(loc, "1324"), 
                new LiteralIntExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(loc, new LiteralCharExprNode(loc, "a"), 
                                     new LiteralCharExprNode(loc, "b"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(loc, new LiteralCharExprNode(loc, "a"), 
                new LiteralCharExprNode(loc, "b"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(loc, new LiteralIntExprNode(loc, "134"), 
                new LiteralCharExprNode(loc, "b"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(loc, new LiteralIntExprNode(loc, "134"), 
                                new LiteralCharExprNode(loc, "b"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(loc, new LiteralStringExprNode(loc, "1324"), 
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(loc, new LiteralStringExprNode(loc, "1324"), 
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        

        context = new HashMapStackContext();
        context.addVar("numbers", new ArrayType(OrdinaryType.intType));
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(loc, new VariableAccessExprNode(loc, "numbers"),
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(loc, new VariableAccessExprNode(loc, "numbers"),
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralBoolExprNode(loc, false));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new OrExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralBoolExprNode(loc, false));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralIntExprNode(loc, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralIntExprNode(loc, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(loc, new LiteralBoolExprNode(loc, false), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(loc, new LiteralIntExprNode(loc, "65432"), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(loc, new LiteralIntExprNode(loc, "65432"), 
                new LiteralStringExprNode(loc, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        context = new HashMapStackContext();
        context.addVar("bools", new ArrayType(OrdinaryType.boolType));
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(loc, new VariableAccessExprNode(loc, "bools"),
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(loc, new VariableAccessExprNode(loc, "bools"),
                new LiteralStringExprNode(loc, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
    }

    
    @Test
    void testArrayExprNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LiteralArrayExprNode(loc,
                List.of(new LiteralIntExprNode(loc, "9"),
                        new LiteralIntExprNode(loc, "10"),
                        new LiteralIntExprNode(loc, "21")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().getInnerArrayType().isInt());


        node = new LiteralArrayExprNode(loc,
                List.of(
                new LiteralArrayExprNode(loc,
                        List.of(new LiteralIntExprNode(loc, "9"))),
                new LiteralArrayExprNode(loc,
                        List.of(new LiteralIntExprNode(loc, "10"))),
                new LiteralArrayExprNode(loc,
                        List.of(new LiteralIntExprNode(loc, "21")))));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().getInnerArrayType().isArray());
        assertTrue(result.assertFirst().isASubtypeOf(
                new ExpandedType(
                        new ArrayType(new ArrayType(OrdinaryType.intType)))));

        
        node = new LiteralArrayExprNode(loc, List.of());
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().getInnerArrayType().isVoid());
        
        
        node = new LiteralArrayExprNode(loc,
                List.of(new LiteralBoolExprNode(loc, true),
                        new LiteralIntExprNode(loc, "10"),
                        new LiteralBoolExprNode(loc, false)));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        node = new LiteralArrayExprNode(loc,
                List.of(
                new LiteralArrayExprNode(loc,
                        List.of(new LiteralIntExprNode(loc, "9"))),
                new LiteralIntExprNode(loc, "10"),
                new LiteralArrayExprNode(loc,
                        List.of(new LiteralIntExprNode(loc, "21")))));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }
    
    
    @Test
    void testFunctionCall() {
        context = new HashMapStackContext();
        context.addFn("print", new FunctionType(
                new ExpandedType(new ArrayType(OrdinaryType.intType)), 
                ExpandedType.unitExpandedType));

        
        visitor = new TypeCheckVisitor(context);
        node = new FunctionCallExprNode(loc, "print", 
                List.of(new LiteralStringExprNode(loc, "Hello World")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isUnit());
        
        
        
        context.addFn("duplicate", new FunctionType(
                new ExpandedType(new ArrayType(OrdinaryType.intType)), 
                new ExpandedType(new ArrayType(OrdinaryType.intType))));

        
        visitor = new TypeCheckVisitor(context);
        node = new FunctionCallExprNode(loc, "duplicate", 
                List.of(new LiteralStringExprNode(loc, "Hello World")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().getInnerArrayType().isInt());

        
        
        context.addFn("exit", new FunctionType(
                ExpandedType.unitExpandedType, 
                ExpandedType.unitExpandedType));
        
        visitor = new TypeCheckVisitor(context);
        node = new FunctionCallExprNode(loc, "exit", List.of());
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isUnit());


        
        context.addFn("random", new FunctionType(
                ExpandedType.unitExpandedType, ExpandedType.intType));
        
        visitor = new TypeCheckVisitor(context);
        node = new FunctionCallExprNode(loc, "random", List.of());
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().getOrdinaryType().isInt());
        
        
        
        ExpandedType tuple = new ExpandedType(
                List.of(OrdinaryType.intType, OrdinaryType.boolType,
                        OrdinaryType.boolType)
                );
        ExpandedType oppositeTuple = new ExpandedType(
                List.of(OrdinaryType.boolType, OrdinaryType.intType,
                        OrdinaryType.intType)
                );
        context.addFn("genMany", 
                new FunctionType(ExpandedType.unitExpandedType, tuple));
        context.addFn("singleToMany",
                new FunctionType(ExpandedType.intType, tuple));
        context.addFn("transform", new FunctionType(tuple, oppositeTuple));
        context.addFn("consume", new FunctionType(
                tuple, ExpandedType.unitExpandedType));
        context.addFn("manyToSingle", 
                new FunctionType(tuple, ExpandedType.intType));

        
        visitor = new TypeCheckVisitor(context);
        node = new FunctionCallExprNode(loc, "genMany", List.of());
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isTuple());
        assertTrue(result.assertFirst().isASubtypeOf(tuple));

        node = new FunctionCallExprNode(loc, "singleToMany", 
                List.of(new LiteralIntExprNode(loc, "0")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isTuple());
        assertTrue(result.assertFirst().isASubtypeOf(tuple));
        
        
        node = new FunctionCallExprNode(loc, "transform", 
                List.of(new LiteralIntExprNode(loc, "0"),
                        new LiteralBoolExprNode(loc, false),
                        new LiteralBoolExprNode(loc, true)));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isTuple());
        assertTrue(result.assertFirst().isASubtypeOf(oppositeTuple));

        
        node = new FunctionCallExprNode(loc, "consume", 
                List.of(new LiteralIntExprNode(loc, "0"),
                        new LiteralBoolExprNode(loc, false),
                        new LiteralBoolExprNode(loc, true)));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isUnit());
        
        
        node = new FunctionCallExprNode(loc, "manyToSingle", 
                List.of(new LiteralIntExprNode(loc, "0"),
                        new LiteralBoolExprNode(loc, false),
                        new LiteralBoolExprNode(loc, true)));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().getOrdinaryType().isInt());
        
        
        node = new FunctionCallExprNode(loc, "nonexistantFunction", List.of());
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        node = new FunctionCallExprNode(loc, "manyToSingle", List.of());
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        node = new FunctionCallExprNode(loc, "consume", List.of());
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        node = new FunctionCallExprNode(loc, "genMany", 
                List.of(new LiteralStringExprNode(loc, "Bad Input")));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
    }
    
    
    @Test
    void testAccessNodes() {

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        
        context.addVar("cash", OrdinaryType.intType);
        node = new VariableAssignNode(loc, "cash");
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        
        // empty[0]
        context.addVar("empty", new ArrayType(OrdinaryType.intType));
        node = new ArrayAssignNode(loc, 
                new VariableAssignNode(loc, "empty"),
                new LiteralIntExprNode(loc, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        
        
        // empty
        node = new VariableAssignNode(loc, "empty");
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        

        // empty[true]
        node = new ArrayAssignNode(loc, 
                new VariableAssignNode(loc, "empty"),
                new LiteralBoolExprNode(loc, true));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        // empty["this is not a number"]
        node = new ArrayAssignNode(loc, 
                new VariableAssignNode(loc, "empty"),
                new LiteralStringExprNode(loc, "this is not a number"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        // empty[0][0]
        node = new ArrayAssignNode(loc, 
                new ArrayAssignNode(
                        loc,
                        new VariableAssignNode(loc, "empty"),
                        new LiteralIntExprNode(loc, "0")),
                new LiteralIntExprNode(loc, "0"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context.addVar("twoDimensionMap", new ArrayType(
                new ArrayType(OrdinaryType.intType)));
        node = new ArrayAssignNode(loc, 
                new VariableAssignNode(loc, "twoDimensionMap"),
                new LiteralIntExprNode(loc, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().getInnerArrayType().isInt());

        
        node = new ArrayAssignNode(loc, 
                new ArrayAssignNode(
                        loc,
                        new VariableAssignNode(loc, "twoDimensionMap"),
                        new LiteralIntExprNode(loc, "0")),
                new LiteralIntExprNode(loc, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());

        
        node = new ArrayAssignNode(loc, 
                new ArrayAssignNode(
                        loc,
                        new VariableAssignNode(loc, "twoDimensionMap"),
                        new LiteralIntExprNode(loc, "0")),
                new LiteralStringExprNode(loc, "NaN"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
    }
    
    
    @Test
    void testArrayLiterals() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        
        node = new ArrayAccessExprNode(loc,
                new LiteralArrayExprNode(loc,
                        List.of(new LiteralIntExprNode(loc, "0"))), 
                new LiteralIntExprNode(loc, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        
        
        // Access to an empty array can be potentially any type.
        node = new ArrayAccessExprNode(loc,
                new LiteralArrayExprNode(loc, List.of()),
                new LiteralIntExprNode(loc, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isSubtypeOfArray());

        
        
        
        // The array literal's type becomes the supertype of among the elements.
        // {{}, {1,2,3}}
        node = new LiteralArrayExprNode(loc,
                List.of(
                        new LiteralArrayExprNode(loc, List.of()),
                        new LiteralArrayExprNode(loc, List.of(
                                new LiteralIntExprNode(loc, "1"),
                                new LiteralIntExprNode(loc, "2"),
                                new LiteralIntExprNode(loc, "3")
                                ))
                        ));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().isASubtypeOf(
                new ExpandedType(
                        new ArrayType(new ArrayType(OrdinaryType.intType)))));

        
        
        // The array literal's type becomes the supertype of among the elements.
        // {{}, {1,2,3}}[0][0]
        node = new ArrayAccessExprNode(loc,
                new ArrayAccessExprNode(loc,
                        new LiteralArrayExprNode(loc,
                          List.of(
                               new LiteralArrayExprNode(loc, List.of()),
                               new LiteralArrayExprNode(loc, List.of(
                                       new LiteralIntExprNode(loc, "1"),
                                       new LiteralIntExprNode(loc, "2"),
                                       new LiteralIntExprNode(loc, "3")))
                               )), new LiteralIntExprNode(loc, "0")),
                new LiteralIntExprNode(loc, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().getOrdinaryType().isInt());
        
        
        
        
        
        // {{}, {}}[0][0]
        node = new ArrayAccessExprNode(loc,
                new ArrayAccessExprNode(loc,
                        new LiteralArrayExprNode(loc,
                          List.of(
                               new LiteralArrayExprNode(loc, List.of()),
                               new LiteralArrayExprNode(loc, List.of())
                               )), new LiteralIntExprNode(loc, "0")),
                new LiteralIntExprNode(loc, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isSubtypeOfArray());
        assertTrue(result.assertFirst().isVoid());
        
        
        
        
        
        
        // Attempt to add an access to an empty array to an array
        node = new AddExprNode(loc,
                new ArrayAccessExprNode(loc,
                        new LiteralArrayExprNode(loc, List.of()),
                        new LiteralIntExprNode(loc, "0")), 
                new LiteralStringExprNode(loc, "A string"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertFalse(result.assertFirst().isVoid());
        
        
        node = new AddExprNode(loc,
                new ArrayAccessExprNode(loc,
                        new LiteralArrayExprNode(loc, List.of()),
                        new LiteralIntExprNode(loc, "0")), 
                new LiteralIntExprNode(loc, "132"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertFalse(result.assertFirst().isVoid());
        
        
        
        
        node = new DivExprNode(loc,
                new ArrayAccessExprNode(loc,
                        new LiteralArrayExprNode(loc, List.of()),
                        new LiteralIntExprNode(loc, "0")), 
                new ArrayAccessExprNode(loc,
                        new LiteralArrayExprNode(loc, List.of()),
                        new LiteralIntExprNode(loc, "0")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertFalse(result.assertFirst().isVoid());
        
        
        
        node = new AddExprNode(loc,
                new ArrayAccessExprNode(loc,
                        new LiteralArrayExprNode(loc, List.of()),
                        new LiteralIntExprNode(loc, "0")), 
                new ArrayAccessExprNode(loc,
                        new LiteralArrayExprNode(loc, List.of()),
                        new LiteralIntExprNode(loc, "0")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isVoid());
        
        
        
        node = new AndExprNode(loc,
                new ArrayAccessExprNode(loc,
                        new LiteralArrayExprNode(loc, List.of()),
                        new LiteralIntExprNode(loc, "0")), 
                new ArrayAccessExprNode(loc,
                        new LiteralArrayExprNode(loc, List.of()),
                        new LiteralIntExprNode(loc, "0")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertFalse(result.assertFirst().isVoid());

    }
}
