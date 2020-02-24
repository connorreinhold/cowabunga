package cyr7.semantics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import cyr7.ast.ArrayAccessNode;
import cyr7.ast.Node;
import cyr7.ast.VariableAccessNode;
import cyr7.ast.expr.binexpr.AddExprNode;
import cyr7.ast.expr.binexpr.AndExprNode;
import cyr7.ast.expr.binexpr.DivExprNode;
import cyr7.ast.expr.binexpr.EqualsExprNode;
import cyr7.ast.expr.binexpr.GTEExprNode;
import cyr7.ast.expr.binexpr.GTExprNode;
import cyr7.ast.expr.binexpr.HighMultExprNode;
import cyr7.ast.expr.binexpr.LTEExprNode;
import cyr7.ast.expr.binexpr.LTExprNode;
import cyr7.ast.expr.binexpr.MultExprNode;
import cyr7.ast.expr.binexpr.NotEqualsExprNode;
import cyr7.ast.expr.binexpr.OrExprNode;
import cyr7.ast.expr.binexpr.RemExprNode;
import cyr7.ast.expr.binexpr.SubExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralCharExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.expr.literalexpr.LiteralStringExprNode;
import cyr7.ast.expr.unaryexpr.BoolNegExprNode;
import cyr7.ast.expr.unaryexpr.IntNegExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.typecheck.TypeCheckVisitor;
import cyr7.util.OneOfTwo;
import cyr7.visitor.AbstractVisitor;

class TypeCheckVisitorTests {

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
        node = new AddExprNode(null, new VariableAccessNode(null, "numbers"),
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
        node = new AddExprNode(null, new VariableAccessNode(null, "bools"),
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
        node = new EqualsExprNode(null, new VariableAccessNode(null, "numbers"),
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
        node = new EqualsExprNode(null, new VariableAccessNode(null, "bools"),
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
        node = new NotEqualsExprNode(null, new VariableAccessNode(null, "numbers"),
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
        node = new NotEqualsExprNode(null, new VariableAccessNode(null, "bools"),
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
                                     new VariableAccessNode(null, "numbers"),
                                     new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new MultExprNode(null, new VariableAccessNode(null, "numbers"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new RemExprNode(null, new VariableAccessNode(null, "numbers"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new SubExprNode(null, new VariableAccessNode(null, "numbers"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new DivExprNode(null, new VariableAccessNode(null, "numbers"),
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
        node = new HighMultExprNode(null, new VariableAccessNode(null, "bools"),
                                     new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new MultExprNode(null, new VariableAccessNode(null, "bools"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new RemExprNode(null, new VariableAccessNode(null, "bools"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new SubExprNode(null, new VariableAccessNode(null, "bools"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new DivExprNode(null, new VariableAccessNode(null, "bools"),
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
                                     new VariableAccessNode(null, "numbers"),
                                     new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new LTExprNode(null, new VariableAccessNode(null, "numbers"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTEExprNode(null, new VariableAccessNode(null, "numbers"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTExprNode(null, new VariableAccessNode(null, "numbers"),
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
        node = new LTEExprNode(null, new VariableAccessNode(null, "bools"),
                                     new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new LTExprNode(null, new VariableAccessNode(null, "bools"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTEExprNode(null, new VariableAccessNode(null, "bools"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new GTExprNode(null, new VariableAccessNode(null, "bools"),
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
        node = new AndExprNode(null, new VariableAccessNode(null, "numbers"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(null, new VariableAccessNode(null, "numbers"),
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
        node = new AndExprNode(null, new VariableAccessNode(null, "bools"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new OrExprNode(null, new VariableAccessNode(null, "bools"),
                new LiteralStringExprNode(null, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
    }

}
