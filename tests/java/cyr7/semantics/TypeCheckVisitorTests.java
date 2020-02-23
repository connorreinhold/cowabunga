package cyr7.semantics;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import cyr7.ast.Node;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
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
    
    @Test
    void testIntNegExprNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new IntNegExprNode(null, 
                new LiteralIntExprNode(null, "13243546"));
        OneOfTwo<ExpandedType, ResultType> result = node.accept(visitor);
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
        OneOfTwo<ExpandedType, ResultType> result = node.accept(visitor);
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

    
}
