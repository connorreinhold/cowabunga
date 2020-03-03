package cyr7.ir.ctranslation;

import cyr7.C;
import cyr7.ast.Node;
import cyr7.ast.expr.binexpr.AndExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ir.CTranslationVisitor;
import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRJump;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRName;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;
import org.junit.jupiter.api.Test;

import static cyr7.ir.util.IRTest.assertEq;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCTranslation {

    @Test
    void testTrue() {
        LookaheadIdGenerator generator = new LookaheadIdGenerator();
        String t = generator.newLabel();
        String f = generator.newLabel();

        Node node = new LiteralBoolExprNode(C.LOC, true);
        IRStmt expected = new IRJump(new IRName(t));
        assertEq(
            expected,
            node.accept(new CTranslationVisitor(generator, t, f))
        );
    }

    @Test
    void testFalse() {
        LookaheadIdGenerator generator = new LookaheadIdGenerator();
        String t = generator.newLabel();
        String f = generator.newLabel();

        Node node = new LiteralBoolExprNode(C.LOC, false);
        IRStmt expected = new IRJump(new IRName(f));
        assertEq(
            expected,
            node.accept(new CTranslationVisitor(generator, t, f))
        );
    }

    @Test
    void testAnd() {
        LookaheadIdGenerator generator = new LookaheadIdGenerator();
        String t = generator.newLabel();
        String f = generator.newLabel();
        String l1 = generator.peekLabel(0);

        Node node = new AndExprNode(C.LOC,
            new LiteralBoolExprNode(C.LOC, true),
            new LiteralBoolExprNode(C.LOC, false)
        );
        IRStmt expected = new IRSeq(
            new IRJump(new IRName(l1)),
            new IRLabel(l1),
            new IRJump(new IRName(f))
        );
        assertEq(
            expected,
            node.accept(new CTranslationVisitor(generator, t, f))
        );
    }

}
