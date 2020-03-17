package cyr7.typecheck;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import cyr7.ast.Node;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.access.ArrayAccessExprNode;
import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.binexpr.BinExprNode;
import cyr7.ast.expr.binexpr.BinExprNode.ASTOpType;
import cyr7.ast.expr.literalexpr.LiteralArrayExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralCharExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.expr.literalexpr.LiteralStringExprNode;
import cyr7.ast.expr.unaryexpr.BoolNegExprNode;
import cyr7.ast.expr.unaryexpr.IntNegExprNode;
import cyr7.exceptions.semantics.SemanticException;
import cyr7.semantics.context.Context;
import cyr7.semantics.context.HashMapStackContext;
import cyr7.semantics.types.ArrayType;
import cyr7.semantics.types.ExpandedType;
import cyr7.semantics.types.FunctionType;
import cyr7.semantics.types.PrimitiveType;
import java_cup.runtime.ComplexSymbolFactory.Location;

class TestTypeCheckVisitorExpr {

    Context context;
    TypeCheckVisitor visitor;
    Node node;
    TypeCheckVisitor.Result result;
    Location LOC = new Location(0, 0);
    IxiFileOpener opener = null;

    @Test
    void testIntNegExprNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new IntNegExprNode(LOC, new LiteralIntExprNode(LOC, "13243546"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new IntNegExprNode(LOC,
                new LiteralStringExprNode(LOC, "Hello World"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new IntNegExprNode(LOC, new LiteralBoolExprNode(LOC, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }

    @Test
    void testBoolNegExprNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BoolNegExprNode(LOC, new LiteralBoolExprNode(LOC, true));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BoolNegExprNode(LOC,
                new LiteralStringExprNode(LOC, "Hello World"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BoolNegExprNode(LOC,
                new LiteralIntExprNode(LOC, "13243546"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }

    @Test
    void testLiterals() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new LiteralBoolExprNode(LOC, false);
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new LiteralCharExprNode(LOC, "a");
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new LiteralIntExprNode(LOC, "1324");
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new LiteralStringExprNode(LOC, "Hello World");
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.intDefault))));
        assertTrue(result.assertFirst().isOrdinary());
    }

    @Test
    void testAddExpr() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.ADD,
                new LiteralIntExprNode(LOC, "1324"),
                new LiteralIntExprNode(LOC, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.ADD,
                new LiteralCharExprNode(LOC, "a"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.ADD,
                new LiteralIntExprNode(LOC, "134"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.ADD,
                new LiteralStringExprNode(LOC, "1324"),
                new LiteralStringExprNode(LOC, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.intDefault))));
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addVar("numbers",
                new ArrayType(PrimitiveType.intDefault));
        node = new BinExprNode(LOC, ASTOpType.ADD,
                new VariableAccessExprNode(LOC, "numbers"),
                new LiteralStringExprNode(LOC, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().isASubtypeOf(
                new ExpandedType(new ArrayType(PrimitiveType.intDefault))));
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.ADD,
                new LiteralBoolExprNode(LOC, false),
                new LiteralBoolExprNode(LOC, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.ADD,
                new LiteralBoolExprNode(LOC, false),
                new LiteralIntExprNode(LOC, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.ADD,
                new LiteralBoolExprNode(LOC, false),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.ADD,
                new LiteralIntExprNode(LOC, "65432"),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addVar("bools",
                new ArrayType(PrimitiveType.boolDefault));
        node = new BinExprNode(LOC, ASTOpType.ADD,
                new VariableAccessExprNode(LOC, "bools"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }

    @Test
    void testEquality() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new LiteralIntExprNode(LOC, "1324"),
                new LiteralIntExprNode(LOC, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new LiteralCharExprNode(LOC, "a"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new LiteralIntExprNode(LOC, "134"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new LiteralStringExprNode(LOC, "1324"),
                new LiteralStringExprNode(LOC, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addVar("numbers",
                new ArrayType(PrimitiveType.intDefault));
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new VariableAccessExprNode(LOC, "numbers"),
                new LiteralStringExprNode(LOC, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new LiteralBoolExprNode(LOC, false),
                new LiteralBoolExprNode(LOC, false));
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new LiteralBoolExprNode(LOC, false),
                new LiteralIntExprNode(LOC, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new LiteralBoolExprNode(LOC, false),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new LiteralIntExprNode(LOC, "65432"),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addVar("bools",
                new ArrayType(PrimitiveType.boolDefault));
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new VariableAccessExprNode(LOC, "bools"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }

    @Test
    void testInequality() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new LiteralIntExprNode(LOC, "1324"),
                new LiteralIntExprNode(LOC, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new LiteralCharExprNode(LOC, "a"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new LiteralIntExprNode(LOC, "134"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new LiteralStringExprNode(LOC, "1324"),
                new LiteralStringExprNode(LOC, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addVar("numbers",
                new ArrayType(PrimitiveType.intDefault));
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new VariableAccessExprNode(LOC, "numbers"),
                new LiteralStringExprNode(LOC, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new LiteralBoolExprNode(LOC, false),
                new LiteralBoolExprNode(LOC, false));
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new LiteralBoolExprNode(LOC, false),
                new LiteralIntExprNode(LOC, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new LiteralBoolExprNode(LOC, false),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new LiteralIntExprNode(LOC, "65432"),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addVar("bools",
                new ArrayType(PrimitiveType.boolDefault));
        node = new BinExprNode(LOC, ASTOpType.EQ,
                new VariableAccessExprNode(LOC, "bools"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }

    @Test
    void testIntegerBinaryExpr() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new LiteralIntExprNode(LOC, "1324"),
                new LiteralIntExprNode(LOC, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new LiteralIntExprNode(LOC, "1324"),
                new LiteralIntExprNode(LOC, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.MOD,
                new LiteralIntExprNode(LOC, "1324"),
                new LiteralIntExprNode(LOC, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.SUB,
                new LiteralIntExprNode(LOC, "1324"),
                new LiteralIntExprNode(LOC, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.DIV,
                new LiteralIntExprNode(LOC, "1324"),
                new LiteralIntExprNode(LOC, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new LiteralCharExprNode(LOC, "a"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new LiteralCharExprNode(LOC, "a"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.MOD,
                new LiteralCharExprNode(LOC, "a"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.SUB,
                new LiteralCharExprNode(LOC, "a"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.DIV,
                new LiteralCharExprNode(LOC, "a"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new LiteralIntExprNode(LOC, "134"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new LiteralIntExprNode(LOC, "134"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.MOD,
                new LiteralIntExprNode(LOC, "134"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.SUB,
                new LiteralIntExprNode(LOC, "134"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.DIV,
                new LiteralIntExprNode(LOC, "134"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new LiteralStringExprNode(LOC, "str"),
                new LiteralStringExprNode(LOC, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new LiteralStringExprNode(LOC, "str"),
                new LiteralStringExprNode(LOC, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.MOD,
                new LiteralStringExprNode(LOC, "str"),
                new LiteralStringExprNode(LOC, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.SUB,
                new LiteralStringExprNode(LOC, "str"),
                new LiteralStringExprNode(LOC, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.DIV,
                new LiteralStringExprNode(LOC, "str"),
                new LiteralStringExprNode(LOC, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor.context.addVar("numbers",
                new ArrayType(PrimitiveType.intDefault));
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new VariableAccessExprNode(LOC, "numbers"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new VariableAccessExprNode(LOC, "numbers"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.MOD,
                new VariableAccessExprNode(LOC, "numbers"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.SUB,
                new VariableAccessExprNode(LOC, "numbers"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.DIV,
                new VariableAccessExprNode(LOC, "numbers"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new LiteralBoolExprNode(LOC, false),
                new LiteralBoolExprNode(LOC, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new LiteralBoolExprNode(LOC, false),
                new LiteralBoolExprNode(LOC, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.MOD,
                new LiteralBoolExprNode(LOC, false),
                new LiteralBoolExprNode(LOC, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.SUB,
                new LiteralBoolExprNode(LOC, false),
                new LiteralBoolExprNode(LOC, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.DIV,
                new LiteralBoolExprNode(LOC, false),
                new LiteralBoolExprNode(LOC, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new LiteralBoolExprNode(LOC, false),
                new LiteralIntExprNode(LOC, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new LiteralBoolExprNode(LOC, false),
                new LiteralIntExprNode(LOC, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.MOD,
                new LiteralBoolExprNode(LOC, false),
                new LiteralIntExprNode(LOC, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.SUB,
                new LiteralBoolExprNode(LOC, false),
                new LiteralIntExprNode(LOC, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.DIV,
                new LiteralBoolExprNode(LOC, false),
                new LiteralIntExprNode(LOC, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new LiteralBoolExprNode(LOC, false),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new LiteralBoolExprNode(LOC, false),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.MOD,
                new LiteralBoolExprNode(LOC, false),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.SUB,
                new LiteralBoolExprNode(LOC, false),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.DIV,
                new LiteralBoolExprNode(LOC, false),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new LiteralIntExprNode(LOC, "65432"),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new LiteralIntExprNode(LOC, "65432"),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.MOD,
                new LiteralIntExprNode(LOC, "65432"),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.SUB,
                new LiteralIntExprNode(LOC, "65432"),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.DIV,
                new LiteralIntExprNode(LOC, "65432"),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor.context.addVar("bools",
                new ArrayType(PrimitiveType.boolDefault));
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new VariableAccessExprNode(LOC, "bools"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.MUL,
                new VariableAccessExprNode(LOC, "bools"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.MOD,
                new VariableAccessExprNode(LOC, "bools"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.SUB,
                new VariableAccessExprNode(LOC, "bools"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.DIV,
                new VariableAccessExprNode(LOC, "bools"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }

    @Test
    void testComparisonExpr() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.LEQ,
                new LiteralIntExprNode(LOC, "1324"),
                new LiteralIntExprNode(LOC, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.LT,
                new LiteralIntExprNode(LOC, "1324"),
                new LiteralIntExprNode(LOC, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.GEQ,
                new LiteralIntExprNode(LOC, "1324"),
                new LiteralIntExprNode(LOC, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.GT,
                new LiteralIntExprNode(LOC, "1324"),
                new LiteralIntExprNode(LOC, "1324"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.GEQ,
                new LiteralCharExprNode(LOC, "a"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.GT,
                new LiteralCharExprNode(LOC, "a"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.LEQ,
                new LiteralCharExprNode(LOC, "a"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.LT,
                new LiteralCharExprNode(LOC, "a"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.LEQ,
                new LiteralIntExprNode(LOC, "134"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.LT,
                new LiteralIntExprNode(LOC, "134"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.GEQ,
                new LiteralIntExprNode(LOC, "134"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.GT,
                new LiteralIntExprNode(LOC, "134"),
                new LiteralCharExprNode(LOC, "b"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.LEQ,
                new LiteralStringExprNode(LOC, "str"),
                new LiteralStringExprNode(LOC, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.LT,
                new LiteralStringExprNode(LOC, "str"),
                new LiteralStringExprNode(LOC, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.GEQ,
                new LiteralStringExprNode(LOC, "str"),
                new LiteralStringExprNode(LOC, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.GT,
                new LiteralStringExprNode(LOC, "str"),
                new LiteralStringExprNode(LOC, "world"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor.context.addVar("numbers",
                new ArrayType(PrimitiveType.intDefault));
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.LEQ,
                new VariableAccessExprNode(LOC, "numbers"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.LT,
                new VariableAccessExprNode(LOC, "numbers"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.GEQ,
                new VariableAccessExprNode(LOC, "numbers"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.GT,
                new VariableAccessExprNode(LOC, "numbers"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.LEQ,
                new LiteralBoolExprNode(LOC, false),
                new LiteralBoolExprNode(LOC, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.LT,
                new LiteralBoolExprNode(LOC, false),
                new LiteralBoolExprNode(LOC, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.GEQ,
                new LiteralBoolExprNode(LOC, false),
                new LiteralBoolExprNode(LOC, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.GT,
                new LiteralBoolExprNode(LOC, false),
                new LiteralBoolExprNode(LOC, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.LEQ,
                new LiteralBoolExprNode(LOC, false),
                new LiteralIntExprNode(LOC, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.LT,
                new LiteralBoolExprNode(LOC, false),
                new LiteralIntExprNode(LOC, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.GEQ,
                new LiteralBoolExprNode(LOC, false),
                new LiteralIntExprNode(LOC, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.GT,
                new LiteralBoolExprNode(LOC, false),
                new LiteralIntExprNode(LOC, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.LEQ,
                new LiteralBoolExprNode(LOC, false),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.LT,
                new LiteralBoolExprNode(LOC, false),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.GEQ,
                new LiteralBoolExprNode(LOC, false),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.GT,
                new LiteralBoolExprNode(LOC, false),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.LEQ,
                new LiteralIntExprNode(LOC, "65432"),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.LT,
                new LiteralIntExprNode(LOC, "65432"),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.GEQ,
                new LiteralIntExprNode(LOC, "65432"),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.GT,
                new LiteralIntExprNode(LOC, "65432"),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor.context.addVar("bools",
                new ArrayType(PrimitiveType.boolDefault));
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.LEQ,
                new VariableAccessExprNode(LOC, "bools"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.LT,
                new VariableAccessExprNode(LOC, "bools"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.GEQ,
                new VariableAccessExprNode(LOC, "bools"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.GT,
                new VariableAccessExprNode(LOC, "bools"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }

    @Test
    void testBooleanOperators() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.AND,
                new LiteralIntExprNode(LOC, "1324"),
                new LiteralIntExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.OR,
                new LiteralIntExprNode(LOC, "1324"),
                new LiteralIntExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.AND,
                new LiteralCharExprNode(LOC, "a"),
                new LiteralCharExprNode(LOC, "b"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.OR,
                new LiteralCharExprNode(LOC, "a"),
                new LiteralCharExprNode(LOC, "b"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.AND,
                new LiteralIntExprNode(LOC, "134"),
                new LiteralCharExprNode(LOC, "b"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.OR,
                new LiteralIntExprNode(LOC, "134"),
                new LiteralCharExprNode(LOC, "b"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.AND,
                new LiteralStringExprNode(LOC, "1324"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.OR,
                new LiteralStringExprNode(LOC, "1324"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor.context.addVar("numbers",
                new ArrayType(PrimitiveType.intDefault));
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.AND,
                new VariableAccessExprNode(LOC, "numbers"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.OR,
                new VariableAccessExprNode(LOC, "numbers"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.AND,
                new LiteralBoolExprNode(LOC, false),
                new LiteralBoolExprNode(LOC, false));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());
        node = new BinExprNode(LOC, ASTOpType.OR,
                new LiteralBoolExprNode(LOC, false),
                new LiteralBoolExprNode(LOC, false));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isOrdinary());

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.AND,
                new LiteralBoolExprNode(LOC, false),
                new LiteralIntExprNode(LOC, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.OR,
                new LiteralBoolExprNode(LOC, false),
                new LiteralIntExprNode(LOC, "124"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.AND,
                new LiteralBoolExprNode(LOC, false),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.OR,
                new LiteralBoolExprNode(LOC, false),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.AND,
                new LiteralIntExprNode(LOC, "65432"),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.OR,
                new LiteralIntExprNode(LOC, "65432"),
                new LiteralStringExprNode(LOC, "This is a string"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        context = new HashMapStackContext();
        visitor.context.addVar("bools",
                new ArrayType(PrimitiveType.boolDefault));
        visitor = new TypeCheckVisitor(null);
        node = new BinExprNode(LOC, ASTOpType.AND,
                new VariableAccessExprNode(LOC, "bools"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new BinExprNode(LOC, ASTOpType.OR,
                new VariableAccessExprNode(LOC, "bools"),
                new LiteralStringExprNode(LOC, "1324"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

    }

    @Test
    void testArrayExprNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new LiteralArrayExprNode(LOC,
                List.of(new LiteralIntExprNode(LOC, "9"),
                        new LiteralIntExprNode(LOC, "10"),
                        new LiteralIntExprNode(LOC, "21")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().getInnerArrayType().isInt());

        node = new LiteralArrayExprNode(LOC,
                List.of(new LiteralArrayExprNode(LOC,
                        List.of(new LiteralIntExprNode(LOC, "9"))),
                        new LiteralArrayExprNode(LOC,
                                List.of(new LiteralIntExprNode(LOC, "10"))),
                        new LiteralArrayExprNode(LOC,
                                List.of(new LiteralIntExprNode(LOC, "21")))));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().getInnerArrayType().isArray());
        assertTrue(result.assertFirst().isASubtypeOf(new ExpandedType(
                new ArrayType(new ArrayType(PrimitiveType.intDefault)))));

        node = new LiteralArrayExprNode(LOC, List.of());
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().getInnerArrayType().isVoid());

        node = new LiteralArrayExprNode(LOC,
                List.of(new LiteralBoolExprNode(LOC, true),
                        new LiteralIntExprNode(LOC, "10"),
                        new LiteralBoolExprNode(LOC, false)));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        node = new LiteralArrayExprNode(LOC, List.of(
                new LiteralArrayExprNode(LOC,
                        List.of(new LiteralIntExprNode(LOC, "9"))),
                new LiteralIntExprNode(LOC, "10"), new LiteralArrayExprNode(LOC,
                        List.of(new LiteralIntExprNode(LOC, "21")))));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }

    @Test
    void testFunctionCall() {
        context = new HashMapStackContext();

        visitor = new TypeCheckVisitor(null);
        visitor.context.addFn("print",
                new FunctionType(
                        new ExpandedType(
                                new ArrayType(PrimitiveType.intDefault)),
                        ExpandedType.unitExpandedType));
        node = new FunctionCallExprNode(LOC, "print",
                List.of(new LiteralStringExprNode(LOC, "Hello World")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isUnit());

        visitor = new TypeCheckVisitor(null);
        visitor.context.addFn("duplicate", new FunctionType(
                new ExpandedType(new ArrayType(PrimitiveType.intDefault)),
                new ExpandedType(new ArrayType(PrimitiveType.intDefault))));
        node = new FunctionCallExprNode(LOC, "duplicate",
                List.of(new LiteralStringExprNode(LOC, "Hello World")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().getInnerArrayType().isInt());

        visitor = new TypeCheckVisitor(null);
        visitor.context.addFn("exit", new FunctionType(
                ExpandedType.unitExpandedType, ExpandedType.unitExpandedType));
        node = new FunctionCallExprNode(LOC, "exit", List.of());
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isUnit());

        visitor = new TypeCheckVisitor(null);
        visitor.context.addFn("random", new FunctionType(
                ExpandedType.unitExpandedType, ExpandedType.intType));
        node = new FunctionCallExprNode(LOC, "random", List.of());
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().getOrdinaryType().isInt());

        ExpandedType tuple = new ExpandedType(List.of(PrimitiveType.intDefault,
                PrimitiveType.boolDefault, PrimitiveType.boolDefault));
        ExpandedType oppositeTuple = new ExpandedType(
                List.of(PrimitiveType.boolDefault, PrimitiveType.intDefault,
                        PrimitiveType.intDefault));

        visitor = new TypeCheckVisitor(null);
        visitor.context.addFn("genMany",
                new FunctionType(ExpandedType.unitExpandedType, tuple));
        visitor.context.addFn("singleToMany",
                new FunctionType(ExpandedType.intType, tuple));
        visitor.context.addFn("transform",
                new FunctionType(tuple, oppositeTuple));
        visitor.context.addFn("consume",
                new FunctionType(tuple, ExpandedType.unitExpandedType));
        visitor.context.addFn("manyToSingle",
                new FunctionType(tuple, ExpandedType.intType));

        node = new FunctionCallExprNode(LOC, "genMany", List.of());
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isTuple());
        assertTrue(result.assertFirst().isASubtypeOf(tuple));

        node = new FunctionCallExprNode(LOC, "singleToMany",
                List.of(new LiteralIntExprNode(LOC, "0")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isTuple());
        assertTrue(result.assertFirst().isASubtypeOf(tuple));

        node = new FunctionCallExprNode(LOC, "transform",
                List.of(new LiteralIntExprNode(LOC, "0"),
                        new LiteralBoolExprNode(LOC, false),
                        new LiteralBoolExprNode(LOC, true)));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isTuple());
        assertTrue(result.assertFirst().isASubtypeOf(oppositeTuple));

        node = new FunctionCallExprNode(LOC, "consume",
                List.of(new LiteralIntExprNode(LOC, "0"),
                        new LiteralBoolExprNode(LOC, false),
                        new LiteralBoolExprNode(LOC, true)));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isUnit());

        node = new FunctionCallExprNode(LOC, "manyToSingle",
                List.of(new LiteralIntExprNode(LOC, "0"),
                        new LiteralBoolExprNode(LOC, false),
                        new LiteralBoolExprNode(LOC, true)));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().getOrdinaryType().isInt());

        node = new FunctionCallExprNode(LOC, "nonexistantFunction", List.of());
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        node = new FunctionCallExprNode(LOC, "manyToSingle", List.of());
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        node = new FunctionCallExprNode(LOC, "consume", List.of());
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        node = new FunctionCallExprNode(LOC, "genMany",
                List.of(new LiteralStringExprNode(LOC, "Bad Input")));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

    }

    @Test
    void testAccessNodes() {

        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);

        visitor.context.addVar("cash", PrimitiveType.intDefault);
        node = new VariableAccessExprNode(LOC, "cash");
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());

        // empty[0]
        visitor.context.addVar("empty",
                new ArrayType(PrimitiveType.intDefault));
        node = new ArrayAccessExprNode(LOC,
                new VariableAccessExprNode(LOC, "empty"),
                new LiteralIntExprNode(LOC, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());

        // empty
        node = new VariableAccessExprNode(LOC, "empty");
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());

        // empty[true]
        node = new ArrayAccessExprNode(LOC,
                new VariableAccessExprNode(LOC, "empty"),
                new LiteralBoolExprNode(LOC, true));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        // empty["this is not a number"]
        node = new ArrayAccessExprNode(LOC,
                new VariableAccessExprNode(LOC, "empty"),
                new LiteralStringExprNode(LOC, "this is not a number"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        // empty[0][0]
        node = new ArrayAccessExprNode(LOC,
                new ArrayAccessExprNode(LOC,
                        new VariableAccessExprNode(LOC, "empty"),
                        new LiteralIntExprNode(LOC, "0")),
                new LiteralIntExprNode(LOC, "0"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        visitor.context.addVar("twoDimensionMap",
                new ArrayType(new ArrayType(PrimitiveType.intDefault)));
        node = new ArrayAccessExprNode(LOC,
                new VariableAccessExprNode(LOC, "twoDimensionMap"),
                new LiteralIntExprNode(LOC, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().getInnerArrayType().isInt());

        node = new ArrayAccessExprNode(LOC,
                new ArrayAccessExprNode(LOC,
                        new VariableAccessExprNode(LOC, "twoDimensionMap"),
                        new LiteralIntExprNode(LOC, "0")),
                new LiteralIntExprNode(LOC, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());

        node = new ArrayAccessExprNode(LOC,
                new ArrayAccessExprNode(LOC,
                        new VariableAccessExprNode(LOC, "twoDimensionMap"),
                        new LiteralIntExprNode(LOC, "0")),
                new LiteralStringExprNode(LOC, "NaN"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

    }

    @Test
    void testArrayLiterals() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);

        node = new ArrayAccessExprNode(LOC,
                new LiteralArrayExprNode(LOC,
                        List.of(new LiteralIntExprNode(LOC, "0"))),
                new LiteralIntExprNode(LOC, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());

        // Access to an empty array can be potentially any type.
        node = new ArrayAccessExprNode(LOC,
                new LiteralArrayExprNode(LOC, List.of()),
                new LiteralIntExprNode(LOC, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isSubtypeOfArray());

        // The array literal's type becomes the supertype of among the elements.
        // Example: {{}, {1,2,3}}
        node = new LiteralArrayExprNode(LOC,
                List.of(new LiteralArrayExprNode(LOC, List.of()),
                        new LiteralArrayExprNode(LOC,
                                List.of(new LiteralIntExprNode(LOC, "1"),
                                        new LiteralIntExprNode(LOC, "2"),
                                        new LiteralIntExprNode(LOC, "3")))));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertTrue(result.assertFirst().isASubtypeOf(new ExpandedType(
                new ArrayType(new ArrayType(PrimitiveType.intDefault)))));

        // The array literal's type becomes the supertype of among the elements.
        // {{}, {1,2,3}}[0][0]
        node = new ArrayAccessExprNode(LOC, new ArrayAccessExprNode(LOC,
                new LiteralArrayExprNode(LOC, List.of(
                        new LiteralArrayExprNode(LOC, List.of()),
                        new LiteralArrayExprNode(LOC,
                                List.of(new LiteralIntExprNode(LOC, "1"),
                                        new LiteralIntExprNode(LOC, "2"),
                                        new LiteralIntExprNode(LOC, "3"))))),
                new LiteralIntExprNode(LOC, "0")),
                new LiteralIntExprNode(LOC, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().getOrdinaryType().isInt());

        // {{}, {}}[0][0]
        node = new ArrayAccessExprNode(LOC,
                new ArrayAccessExprNode(LOC, new LiteralArrayExprNode(LOC,
                        List.of(new LiteralArrayExprNode(LOC, List.of()),
                                new LiteralArrayExprNode(LOC, List.of()))),
                        new LiteralIntExprNode(LOC, "0")),
                new LiteralIntExprNode(LOC, "0"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertTrue(result.assertFirst().isSubtypeOfArray());
        assertTrue(result.assertFirst().isVoid());

        // Attempt to add an access to an empty array to an array
        node = new BinExprNode(LOC, ASTOpType.ADD,
                new ArrayAccessExprNode(LOC,
                        new LiteralArrayExprNode(LOC, List.of()),
                        new LiteralIntExprNode(LOC, "0")),
                new LiteralStringExprNode(LOC, "A string"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isArray());
        assertFalse(result.assertFirst().isVoid());

        node = new BinExprNode(LOC, ASTOpType.ADD,
                new ArrayAccessExprNode(LOC,
                        new LiteralArrayExprNode(LOC, List.of()),
                        new LiteralIntExprNode(LOC, "0")),
                new LiteralIntExprNode(LOC, "132"));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertFalse(result.assertFirst().isVoid());

        node = new BinExprNode(LOC, ASTOpType.DIV,
                new ArrayAccessExprNode(LOC,
                        new LiteralArrayExprNode(LOC, List.of()),
                        new LiteralIntExprNode(LOC, "0")),
                new ArrayAccessExprNode(LOC,
                        new LiteralArrayExprNode(LOC, List.of()),
                        new LiteralIntExprNode(LOC, "0")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfInt());
        assertFalse(result.assertFirst().isVoid());

        node = new BinExprNode(LOC, ASTOpType.ADD,
                new ArrayAccessExprNode(LOC,
                        new LiteralArrayExprNode(LOC, List.of()),
                        new LiteralIntExprNode(LOC, "0")),
                new ArrayAccessExprNode(LOC,
                        new LiteralArrayExprNode(LOC, List.of()),
                        new LiteralIntExprNode(LOC, "0")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isGenericAdd());

        node = new BinExprNode(LOC, ASTOpType.AND,
                new ArrayAccessExprNode(LOC,
                        new LiteralArrayExprNode(LOC, List.of()),
                        new LiteralIntExprNode(LOC, "0")),
                new ArrayAccessExprNode(LOC,
                        new LiteralArrayExprNode(LOC, List.of()),
                        new LiteralIntExprNode(LOC, "0")));
        result = node.accept(visitor);
        assertTrue(result.assertFirst().isSubtypeOfBool());
        assertFalse(result.assertFirst().isVoid());

    }
}
