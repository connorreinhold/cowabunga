package cyr7.ir.ctranslation;

import cyr7.C;
import cyr7.ast.Node;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.binexpr.AndExprNode;
import cyr7.ast.expr.binexpr.OrExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ir.CTranslationVisitor;
import cyr7.ir.nodes.IRCJump;
import cyr7.ir.nodes.IRCall;
import cyr7.ir.nodes.IRJump;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRName;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;
import cyr7.semantics.types.ExpandedType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCTranslation {

    @Test
    void testTrue() {
        LookaheadIdGenerator generator = new LookaheadIdGenerator();
        String t = generator.newLabel();
        String f = generator.newLabel();

        Node node = new LiteralBoolExprNode(C.LOC, true);
        IRStmt expected = new IRJump(new IRName(t));
        assertEquals(
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
        assertEquals(
            expected,
            node.accept(new CTranslationVisitor(generator, t, f))
        );
    }

    @Test
    void testAndLiterals() {
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
        assertEquals(
            expected,
            node.accept(new CTranslationVisitor(generator, t, f))
        );
    }

    @Test
    void testNestedAnd() {
        LookaheadIdGenerator generator = new LookaheadIdGenerator();
        String t = generator.newLabel();
        String f = generator.newLabel();
        String l2 = generator.peekLabel(0);
        String l3 = generator.peekLabel(1);
        String l4 = generator.peekLabel(2);

        // (true & false) & (true & true)
        Node node = new AndExprNode(C.LOC,
            new AndExprNode(C.LOC,
                new LiteralBoolExprNode(C.LOC, true),
                new LiteralBoolExprNode(C.LOC, false)
            ),
            new AndExprNode(C.LOC,
                new LiteralBoolExprNode(C.LOC, true),
                new LiteralBoolExprNode(C.LOC, true)
            )
        );
        IRStmt expected = new IRSeq(
            new IRSeq(
                new IRJump(new IRName(l3)),
                new IRLabel(l3),
                new IRJump(new IRName(f))
            ),
            new IRLabel(l2),
            new IRSeq(
                new IRJump(new IRName(l4)),
                new IRLabel(l4),
                new IRJump(new IRName(t))
            )
        );
        assertEquals(
            expected,
            node.accept(new CTranslationVisitor(generator, t, f))
        );
    }

    @Test
    void testOrLiteral() {
        LookaheadIdGenerator generator = new LookaheadIdGenerator();
        String t = generator.newLabel();
        String f = generator.newLabel();
        String l1 = generator.peekLabel(0);

        Node node = new OrExprNode(C.LOC,
            new LiteralBoolExprNode(C.LOC, true),
            new LiteralBoolExprNode(C.LOC, false)
        );
        IRStmt expected = new IRSeq(
            new IRJump(new IRName(t)),
            new IRLabel(l1),
            new IRJump(new IRName(f))
        );
        assertEquals(
            expected,
            node.accept(new CTranslationVisitor(generator, t, f))
        );
    }

    @Test
    void testNestedOr() {
        LookaheadIdGenerator generator = new LookaheadIdGenerator();
        String t = generator.newLabel();
        String f = generator.newLabel();
        String l2 = generator.peekLabel(0);
        String l3 = generator.peekLabel(1);
        String l4 = generator.peekLabel(2);

        // (false | true) | (true | false)
        Node node = new OrExprNode(C.LOC,
            new OrExprNode(C.LOC,
                new LiteralBoolExprNode(C.LOC, false),
                new LiteralBoolExprNode(C.LOC, true)
            ),
            new OrExprNode(C.LOC,
                new LiteralBoolExprNode(C.LOC, true),
                new LiteralBoolExprNode(C.LOC, false)
            )
        );
        IRStmt expected = new IRSeq(
            new IRSeq(
                new IRJump(new IRName(l3)),
                new IRLabel(l3),
                new IRJump(new IRName(t))
            ),
            new IRLabel(l2),
            new IRSeq(
                new IRJump(new IRName(t)),
                new IRLabel(l4),
                new IRJump(new IRName(f))
            )
        );
        assertEquals(
            expected,
            node.accept(new CTranslationVisitor(generator, t, f))
        );
    }

    @Test
    void testOrNestingAnd() {
        LookaheadIdGenerator generator = new LookaheadIdGenerator();
        String t = generator.newLabel();
        String f = generator.newLabel();
        String l2 = generator.peekLabel(0);
        String l3 = generator.peekLabel(1);
        String l4 = generator.peekLabel(2);

        // (f() & g()) | (h() & i())
        Node node = new OrExprNode(C.LOC,
            new AndExprNode(C.LOC,
                new FunctionCallExprNode(C.LOC, "f", List.of()).typed(ExpandedType.boolType),
                new FunctionCallExprNode(C.LOC, "g", List.of()).typed(ExpandedType.boolType)
            ).typed(ExpandedType.boolType),
            new AndExprNode(C.LOC,
                new FunctionCallExprNode(C.LOC, "h", List.of()).typed(ExpandedType.boolType),
                new FunctionCallExprNode(C.LOC, "i", List.of()).typed(ExpandedType.boolType)
            ).typed(ExpandedType.boolType)
        ).typed(ExpandedType.boolType);
        IRNode expected = new IRSeq(
            new IRSeq(
                new IRCJump(new IRCall(new IRName("_If_b")), l3, l2),
                new IRLabel(l3),
                new IRCJump(new IRCall(new IRName("_Ig_b")), t, l2)
            ),
            new IRLabel(l2),
            new IRSeq(
                new IRCJump(new IRCall(new IRName("_Ih_b")), l4, f),
                new IRLabel(l4),
                new IRCJump(new IRCall(new IRName("_Ii_b")), t, f)
            )
        );
        assertEquals(
            expected,
            node.accept(new CTranslationVisitor(generator, t, f))
        );
    }

    @Test
    void testFunctionCall() {
        LookaheadIdGenerator generator = new LookaheadIdGenerator();
        String t = generator.newLabel();
        String f = generator.newLabel();

        Node node = new FunctionCallExprNode(C.LOC, "f", List.of()).typed(ExpandedType.boolType);
        IRNode expected = new IRCJump(new IRCall(new IRName("_If_b")), t, f);
        assertEquals(
            expected,
            node.accept(new CTranslationVisitor(generator, t, f))
        );
    }

}
