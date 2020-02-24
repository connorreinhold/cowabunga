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
import cyr7.exceptions.SemanticException;
import cyr7.typecheck.TypeCheckVisitor;
import cyr7.util.OneOfTwo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestTypeCheckVisitorExpr {

    Context context;
    TypeCheckVisitor visitor;
    Node node;
    OneOfTwo<ExpandedType, ResultType> result;

    @Test
    void testIntNegExprNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new IntNegExprNode(null, 
                new LiteralIntExprNode(null, "13243546"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new IntNegExprNode(null, 
                new LiteralStringExprNode(null, "Hello World"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new IntNegExprNode(null,
                new LiteralBoolExprNode(null, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }

    @Test
    void testBoolNegExprNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new BoolNegExprNode(null,
                new LiteralBoolExprNode(null, true));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new BoolNegExprNode(null, 
                new LiteralStringExprNode(null, "Hello World"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BoolNegExprNode(null, 
                new LiteralIntExprNode(null, "13243546"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }

    
    @Test
    void testLiterals() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LiteralBoolExprNode(null, false);
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LiteralCharExprNode(null, "a");
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LiteralIntExprNode(null, "1324");
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LiteralStringExprNode(null, "Hello World");
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
        node = new AddExprNode(null, new LiteralIntExprNode(null, "1324"), 
                                     new LiteralIntExprNode(null, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());


        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AddExprNode(null, new LiteralCharExprNode(null, "a"), 
                                     new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AddExprNode(null, new LiteralIntExprNode(null, "134"), 
                                     new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AddExprNode(null, new LiteralStringExprNode(null, "1324"), 
                                     new LiteralStringExprNode(null, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().isASubtypeOf(
                new ExpandedType(new ArrayType(OrdinaryType.intType))));
        assertTrue(result.assertFirst().isOrdinary());
        

        context = new HashMapStackContext();
        context.addVar("numbers", new ArrayType(OrdinaryType.intType));
        visitor = new TypeCheckVisitor(context);
        node = new AddExprNode(null, new VariableAccessExprNode(null, "numbers"),
                                     new LiteralStringExprNode(null, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().isASubtypeOf(
                new ExpandedType(new ArrayType(OrdinaryType.intType))));
        assertTrue(result.assertFirst().isOrdinary());

        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AddExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralBoolExprNode(null, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AddExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralIntExprNode(null, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AddExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AddExprNode(null, new LiteralIntExprNode(null, "65432"), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        context.addVar("bools", new ArrayType(OrdinaryType.boolType));
        visitor = new TypeCheckVisitor(context);
        node = new AddExprNode(null, new VariableAccessExprNode(null, "bools"),
                                     new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }
    
    @Test
    void testEquality() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(null, new LiteralIntExprNode(null, "1324"), 
                                     new LiteralIntExprNode(null, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());


        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(null, new LiteralCharExprNode(null, "a"), 
                                     new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(null, new LiteralIntExprNode(null, "134"), 
                                     new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(null, new LiteralStringExprNode(null, "1324"), 
                                     new LiteralStringExprNode(null, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        

        context = new HashMapStackContext();
        context.addVar("numbers", new ArrayType(OrdinaryType.intType));
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(null, new VariableAccessExprNode(null, "numbers"),
                                     new LiteralStringExprNode(null, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralBoolExprNode(null, false));
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralIntExprNode(null, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(null, new LiteralIntExprNode(null, "65432"), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        context.addVar("bools", new ArrayType(OrdinaryType.boolType));
        visitor = new TypeCheckVisitor(context);
        node = new EqualsExprNode(null, new VariableAccessExprNode(null, "bools"),
                                     new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }

    
    @Test
    void testInequality() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(null, new LiteralIntExprNode(null, "1324"), 
                                     new LiteralIntExprNode(null, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());


        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(null, new LiteralCharExprNode(null, "a"), 
                                     new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(null, new LiteralIntExprNode(null, "134"), 
                                     new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(null, new LiteralStringExprNode(null, "1324"), 
                                     new LiteralStringExprNode(null, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        

        context = new HashMapStackContext();
        context.addVar("numbers", new ArrayType(OrdinaryType.intType));
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(null, new VariableAccessExprNode(null, "numbers"),
                                     new LiteralStringExprNode(null, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralBoolExprNode(null, false));
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralIntExprNode(null, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(null, new LiteralIntExprNode(null, "65432"), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        context.addVar("bools", new ArrayType(OrdinaryType.boolType));
        visitor = new TypeCheckVisitor(context);
        node = new NotEqualsExprNode(null, new VariableAccessExprNode(null, "bools"),
                                     new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }
    
    
    @Test
    void testIntegerBinaryExpr() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(null, new LiteralIntExprNode(null, "1324"), 
                                     new LiteralIntExprNode(null, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new MultExprNode(null, new LiteralIntExprNode(null, "1324"), 
                new LiteralIntExprNode(null, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new RemExprNode(null, new LiteralIntExprNode(null, "1324"), 
                new LiteralIntExprNode(null, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new SubExprNode(null, new LiteralIntExprNode(null, "1324"), 
                new LiteralIntExprNode(null, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new DivExprNode(null, new LiteralIntExprNode(null, "1324"), 
                new LiteralIntExprNode(null, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());

        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(null, new LiteralCharExprNode(null, "a"), 
                                     new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new MultExprNode(null, new LiteralCharExprNode(null, "a"), 
                new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new RemExprNode(null, new LiteralCharExprNode(null, "a"), 
                new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new SubExprNode(null, new LiteralCharExprNode(null, "a"), 
                new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new DivExprNode(null, new LiteralCharExprNode(null, "a"), 
                new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(null, new LiteralIntExprNode(null, "134"), 
                                     new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new MultExprNode(null, new LiteralIntExprNode(null, "134"), 
                new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new RemExprNode(null, new LiteralIntExprNode(null, "134"), 
                new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new SubExprNode(null, new LiteralIntExprNode(null, "134"), 
                new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new DivExprNode(null, new LiteralIntExprNode(null, "134"), 
                new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(null, new LiteralStringExprNode(null, "str"), 
                                     new LiteralStringExprNode(null, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new MultExprNode(null, new LiteralStringExprNode(null, "str"), 
                new LiteralStringExprNode(null, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new RemExprNode(null, new LiteralStringExprNode(null, "str"), 
                new LiteralStringExprNode(null, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new SubExprNode(null, new LiteralStringExprNode(null, "str"), 
                new LiteralStringExprNode(null, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new DivExprNode(null, new LiteralStringExprNode(null, "str"), 
                new LiteralStringExprNode(null, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        context = new HashMapStackContext();
        context.addVar("numbers", new ArrayType(OrdinaryType.intType));
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(null, 
                                     new VariableAccessExprNode(null, "numbers"),
                                     new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new MultExprNode(null, new VariableAccessExprNode(null, "numbers"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new RemExprNode(null, new VariableAccessExprNode(null, "numbers"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new SubExprNode(null, new VariableAccessExprNode(null, "numbers"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new DivExprNode(null, new VariableAccessExprNode(null, "numbers"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralBoolExprNode(null, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new MultExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralBoolExprNode(null, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new RemExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralBoolExprNode(null, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new SubExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralBoolExprNode(null, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new DivExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralBoolExprNode(null, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralIntExprNode(null, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new MultExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralIntExprNode(null, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new RemExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralIntExprNode(null, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new SubExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralIntExprNode(null, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new DivExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralIntExprNode(null, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new MultExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new RemExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new SubExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new DivExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(null, new LiteralIntExprNode(null, "65432"), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new MultExprNode(null, new LiteralIntExprNode(null, "65432"), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new RemExprNode(null, new LiteralIntExprNode(null, "65432"), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new SubExprNode(null, new LiteralIntExprNode(null, "65432"), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new DivExprNode(null, new LiteralIntExprNode(null, "65432"), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        context = new HashMapStackContext();
        context.addVar("bools", new ArrayType(OrdinaryType.boolType));
        visitor = new TypeCheckVisitor(context);
        node = new HighMultExprNode(null, new VariableAccessExprNode(null, "bools"),
                                     new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new MultExprNode(null, new VariableAccessExprNode(null, "bools"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new RemExprNode(null, new VariableAccessExprNode(null, "bools"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new SubExprNode(null, new VariableAccessExprNode(null, "bools"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new DivExprNode(null, new VariableAccessExprNode(null, "bools"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }
    
    
    @Test
    void testComparisonExpr() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LTEExprNode(null, new LiteralIntExprNode(null, "1324"), 
                                     new LiteralIntExprNode(null, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new LTExprNode(null, new LiteralIntExprNode(null, "1324"), 
                new LiteralIntExprNode(null, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new GTEExprNode(null, new LiteralIntExprNode(null, "1324"), 
                new LiteralIntExprNode(null, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new GTExprNode(null, new LiteralIntExprNode(null, "1324"), 
                new LiteralIntExprNode(null, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());


        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new GTEExprNode(null, new LiteralCharExprNode(null, "a"), 
                                     new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new GTExprNode(null, new LiteralCharExprNode(null, "a"), 
                new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new LTEExprNode(null, new LiteralCharExprNode(null, "a"), 
                new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new LTExprNode(null, new LiteralCharExprNode(null, "a"), 
                new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());

        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LTEExprNode(null, new LiteralIntExprNode(null, "134"), 
                                     new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new LTExprNode(null, new LiteralIntExprNode(null, "134"), 
                new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new GTEExprNode(null, new LiteralIntExprNode(null, "134"), 
                new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new GTExprNode(null, new LiteralIntExprNode(null, "134"), 
                new LiteralCharExprNode(null, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LTEExprNode(null, new LiteralStringExprNode(null, "str"), 
                                     new LiteralStringExprNode(null, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new LTExprNode(null, new LiteralStringExprNode(null, "str"), 
                new LiteralStringExprNode(null, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTEExprNode(null, new LiteralStringExprNode(null, "str"), 
                new LiteralStringExprNode(null, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTExprNode(null, new LiteralStringExprNode(null, "str"), 
                new LiteralStringExprNode(null, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        context = new HashMapStackContext();
        context.addVar("numbers", new ArrayType(OrdinaryType.intType));
        visitor = new TypeCheckVisitor(context);
        node = new LTEExprNode(null, 
                                     new VariableAccessExprNode(null, "numbers"),
                                     new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new LTExprNode(null, new VariableAccessExprNode(null, "numbers"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTEExprNode(null, new VariableAccessExprNode(null, "numbers"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTExprNode(null, new VariableAccessExprNode(null, "numbers"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LTEExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralBoolExprNode(null, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new LTExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralBoolExprNode(null, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTEExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralBoolExprNode(null, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralBoolExprNode(null, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LTEExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralIntExprNode(null, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new LTExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralIntExprNode(null, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTEExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralIntExprNode(null, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralIntExprNode(null, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LTEExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new LTExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTEExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LTEExprNode(null, new LiteralIntExprNode(null, "65432"), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new LTExprNode(null, new LiteralIntExprNode(null, "65432"), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTEExprNode(null, new LiteralIntExprNode(null, "65432"), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTExprNode(null, new LiteralIntExprNode(null, "65432"), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        
        context = new HashMapStackContext();
        context.addVar("bools", new ArrayType(OrdinaryType.boolType));
        visitor = new TypeCheckVisitor(context);
        node = new LTEExprNode(null, new VariableAccessExprNode(null, "bools"),
                                     new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new LTExprNode(null, new VariableAccessExprNode(null, "bools"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTEExprNode(null, new VariableAccessExprNode(null, "bools"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTExprNode(null, new VariableAccessExprNode(null, "bools"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }

    
    @Test
    void testBooleanOperators() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(null, new LiteralIntExprNode(null, "1324"), 
                                     new LiteralIntExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(null, new LiteralIntExprNode(null, "1324"), 
                new LiteralIntExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(null, new LiteralCharExprNode(null, "a"), 
                                     new LiteralCharExprNode(null, "b"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(null, new LiteralCharExprNode(null, "a"), 
                new LiteralCharExprNode(null, "b"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(null, new LiteralIntExprNode(null, "134"), 
                new LiteralCharExprNode(null, "b"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(null, new LiteralIntExprNode(null, "134"), 
                                new LiteralCharExprNode(null, "b"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(null, new LiteralStringExprNode(null, "1324"), 
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(null, new LiteralStringExprNode(null, "1324"), 
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        

        context = new HashMapStackContext();
        context.addVar("numbers", new ArrayType(OrdinaryType.intType));
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(null, new VariableAccessExprNode(null, "numbers"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(null, new VariableAccessExprNode(null, "numbers"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralBoolExprNode(null, false));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new OrExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralBoolExprNode(null, false));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralIntExprNode(null, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralIntExprNode(null, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(null, new LiteralBoolExprNode(null, false), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(null, new LiteralIntExprNode(null, "65432"), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(null, new LiteralIntExprNode(null, "65432"), 
                new LiteralStringExprNode(null, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        context = new HashMapStackContext();
        context.addVar("bools", new ArrayType(OrdinaryType.boolType));
        visitor = new TypeCheckVisitor(context);
        node = new AndExprNode(null, new VariableAccessExprNode(null, "bools"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(null, new VariableAccessExprNode(null, "bools"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
    }

    
    @Test
    void testArrayExprNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new LiteralArrayExprNode(null,
                List.of(new LiteralIntExprNode(null, "9"),
                        new LiteralIntExprNode(null, "10"),
                        new LiteralIntExprNode(null, "21")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().getArrayType().child.isInt());


        node = new LiteralArrayExprNode(null,
                List.of(
                new LiteralArrayExprNode(null,
                        List.of(new LiteralIntExprNode(null, "9"))),
                new LiteralArrayExprNode(null,
                        List.of(new LiteralIntExprNode(null, "10"))),
                new LiteralArrayExprNode(null,
                        List.of(new LiteralIntExprNode(null, "21")))));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().getArrayType().child.isArray());
        assertTrue(result.assertFirst().isASubtypeOf(
                new ExpandedType(
                        new ArrayType(new ArrayType(OrdinaryType.intType)))));

        
        node = new LiteralArrayExprNode(null, List.of());
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().getArrayType().child.isVoid());
        
        
        node = new LiteralArrayExprNode(null,
                List.of(new LiteralBoolExprNode(null, true),
                        new LiteralIntExprNode(null, "10"),
                        new LiteralBoolExprNode(null, false)));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        node = new LiteralArrayExprNode(null,
                List.of(
                new LiteralArrayExprNode(null,
                        List.of(new LiteralIntExprNode(null, "9"))),
                new LiteralIntExprNode(null, "10"),
                new LiteralArrayExprNode(null,
                        List.of(new LiteralIntExprNode(null, "21")))));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }
    
    
    @Test
    void testFunctionCall() {
        context = new HashMapStackContext();
        context.addFn("print", new FunctionType(
                new ExpandedType(new ArrayType(OrdinaryType.intType)), 
                ExpandedType.unitExpandedType));

        
        visitor = new TypeCheckVisitor(context);
        node = new FunctionCallExprNode(null, "print", 
                List.of(new LiteralStringExprNode(null, "Hello World")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isUnit());
        
        
        
        context.addFn("duplicate", new FunctionType(
                new ExpandedType(new ArrayType(OrdinaryType.intType)), 
                new ExpandedType(new ArrayType(OrdinaryType.intType))));

        
        visitor = new TypeCheckVisitor(context);
        node = new FunctionCallExprNode(null, "duplicate", 
                List.of(new LiteralStringExprNode(null, "Hello World")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().getArrayType().child.isInt());

        
        
        context.addFn("exit", new FunctionType(
                ExpandedType.unitExpandedType, 
                ExpandedType.unitExpandedType));
        
        visitor = new TypeCheckVisitor(context);
        node = new FunctionCallExprNode(null, "exit", List.of());
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isUnit());


        
        context.addFn("random", new FunctionType(
                ExpandedType.unitExpandedType, ExpandedType.intType));
        
        visitor = new TypeCheckVisitor(context);
        node = new FunctionCallExprNode(null, "random", List.of());
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
        node = new FunctionCallExprNode(null, "genMany", List.of());
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isTuple());
        assertTrue(result.assertFirst().isASubtypeOf(tuple));

        node = new FunctionCallExprNode(null, "singleToMany", 
                List.of(new LiteralIntExprNode(null, "0")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isTuple());
        assertTrue(result.assertFirst().isASubtypeOf(tuple));
        
        
        node = new FunctionCallExprNode(null, "transform", 
                List.of(new LiteralIntExprNode(null, "0"),
                        new LiteralBoolExprNode(null, false),
                        new LiteralBoolExprNode(null, true)));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isTuple());
        assertTrue(result.assertFirst().isASubtypeOf(oppositeTuple));

        
        node = new FunctionCallExprNode(null, "consume", 
                List.of(new LiteralIntExprNode(null, "0"),
                        new LiteralBoolExprNode(null, false),
                        new LiteralBoolExprNode(null, true)));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isUnit());
        
        
        node = new FunctionCallExprNode(null, "manyToSingle", 
                List.of(new LiteralIntExprNode(null, "0"),
                        new LiteralBoolExprNode(null, false),
                        new LiteralBoolExprNode(null, true)));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().getOrdinaryType().isInt());
        
        
        node = new FunctionCallExprNode(null, "nonexistantFunction", List.of());
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        node = new FunctionCallExprNode(null, "manyToSingle", List.of());
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        node = new FunctionCallExprNode(null, "consume", List.of());
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        node = new FunctionCallExprNode(null, "genMany", 
                List.of(new LiteralStringExprNode(null, "Bad Input")));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
    }
    
    
    @Test
    void testAccessNodes() {

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        
        context.addVar("cash", OrdinaryType.intType);
        node = new VariableAssignNode(null, "cash");
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        
        // empty[0]
        context.addVar("empty", new ArrayType(OrdinaryType.intType));
        node = new ArrayAssignNode(null, 
                new VariableAssignNode(null, "empty"),
                new LiteralIntExprNode(null, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        
        
        // empty
        node = new VariableAssignNode(null, "empty");
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        

        // empty[true]
        node = new ArrayAssignNode(null, 
                new VariableAssignNode(null, "empty"),
                new LiteralBoolExprNode(null, true));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        // empty["this is not a number"]
        node = new ArrayAssignNode(null, 
                new VariableAssignNode(null, "empty"),
                new LiteralStringExprNode(null, "this is not a number"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        // empty[0][0]
        node = new ArrayAssignNode(null, 
                new ArrayAssignNode(
                        null,
                        new VariableAssignNode(null, "empty"),
                        new LiteralIntExprNode(null, "0")),
                new LiteralIntExprNode(null, "0"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context.addVar("twoDimensionMap", new ArrayType(
                new ArrayType(OrdinaryType.intType)));
        node = new ArrayAssignNode(null, 
                new VariableAssignNode(null, "twoDimensionMap"),
                new LiteralIntExprNode(null, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().getArrayType().child.isInt());

        
        node = new ArrayAssignNode(null, 
                new ArrayAssignNode(
                        null,
                        new VariableAssignNode(null, "twoDimensionMap"),
                        new LiteralIntExprNode(null, "0")),
                new LiteralIntExprNode(null, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());

        
        node = new ArrayAssignNode(null, 
                new ArrayAssignNode(
                        null,
                        new VariableAssignNode(null, "twoDimensionMap"),
                        new LiteralIntExprNode(null, "0")),
                new LiteralStringExprNode(null, "NaN"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
    }
    
    
    @Test
    void testArrayLiterals() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        
        node = new ArrayAccessExprNode(null,
                new LiteralArrayExprNode(null,
                        List.of(new LiteralIntExprNode(null, "0"))), 
                new LiteralIntExprNode(null, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        
        
        // Access to an empty array can be potentially any type.
        node = new ArrayAccessExprNode(null,
                new LiteralArrayExprNode(null, List.of()),
                new LiteralIntExprNode(null, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isSubtypeOfArray());

        
        
        
        // The array literal's type becomes the supertype of among the elements.
        // {{}, {1,2,3}}
        node = new LiteralArrayExprNode(null,
                List.of(
                        new LiteralArrayExprNode(null, List.of()),
                        new LiteralArrayExprNode(null, List.of(
                                new LiteralIntExprNode(null, "1"),
                                new LiteralIntExprNode(null, "2"),
                                new LiteralIntExprNode(null, "3")
                                ))
                        ));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().isASubtypeOf(
                new ExpandedType(
                        new ArrayType(new ArrayType(OrdinaryType.intType)))));

        
        
        // The array literal's type becomes the supertype of among the elements.
        // {{}, {1,2,3}}[0][0]
        node = new ArrayAccessExprNode(null,
                new ArrayAccessExprNode(null,
                        new LiteralArrayExprNode(null,
                          List.of(
                               new LiteralArrayExprNode(null, List.of()),
                               new LiteralArrayExprNode(null, List.of(
                                       new LiteralIntExprNode(null, "1"),
                                       new LiteralIntExprNode(null, "2"),
                                       new LiteralIntExprNode(null, "3")))
                               )), new LiteralIntExprNode(null, "0")),
                new LiteralIntExprNode(null, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().getOrdinaryType().isInt());
        
        
        
        
        
        // {{}, {}}[0][0]
        node = new ArrayAccessExprNode(null,
                new ArrayAccessExprNode(null,
                        new LiteralArrayExprNode(null,
                          List.of(
                               new LiteralArrayExprNode(null, List.of()),
                               new LiteralArrayExprNode(null, List.of())
                               )), new LiteralIntExprNode(null, "0")),
                new LiteralIntExprNode(null, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isSubtypeOfArray());
        assertTrue(result.assertFirst().isVoid());
        
        
        
        
        
        
        // Attempt to add an access to an empty array to an array
        node = new AddExprNode(null,
                new ArrayAccessExprNode(null,
                        new LiteralArrayExprNode(null, List.of()),
                        new LiteralIntExprNode(null, "0")), 
                new LiteralStringExprNode(null, "A string"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertFalse(result.assertFirst().isVoid());
        
        
        node = new AddExprNode(null,
                new ArrayAccessExprNode(null,
                        new LiteralArrayExprNode(null, List.of()),
                        new LiteralIntExprNode(null, "0")), 
                new LiteralIntExprNode(null, "132"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertFalse(result.assertFirst().isVoid());
        
        
        
        
        node = new DivExprNode(null,
                new ArrayAccessExprNode(null,
                        new LiteralArrayExprNode(null, List.of()),
                        new LiteralIntExprNode(null, "0")), 
                new ArrayAccessExprNode(null,
                        new LiteralArrayExprNode(null, List.of()),
                        new LiteralIntExprNode(null, "0")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertFalse(result.assertFirst().isVoid());
        
        
        
        node = new AddExprNode(null,
                new ArrayAccessExprNode(null,
                        new LiteralArrayExprNode(null, List.of()),
                        new LiteralIntExprNode(null, "0")), 
                new ArrayAccessExprNode(null,
                        new LiteralArrayExprNode(null, List.of()),
                        new LiteralIntExprNode(null, "0")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isVoid());
        
        
        
        node = new AndExprNode(null,
                new ArrayAccessExprNode(null,
                        new LiteralArrayExprNode(null, List.of()),
                        new LiteralIntExprNode(null, "0")), 
                new ArrayAccessExprNode(null,
                        new LiteralArrayExprNode(null, List.of()),
                        new LiteralIntExprNode(null, "0")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertFalse(result.assertFirst().isVoid());

    }
}
