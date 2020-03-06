package cyr7.ir.ctranslation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import cyr7.C;
import cyr7.ast.Node;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.binexpr.AndExprNode;
import cyr7.ast.expr.binexpr.OrExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ir.CTranslationVisitor;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;
import cyr7.semantics.types.ExpandedType;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class TestCTranslation {

    private final IRNodeFactory make = new IRNodeFactory_c(
            new Location(-1, -1));

    @Test
    void testTrue() {
        LookaheadIdGenerator generator = new LookaheadIdGenerator();
        String t = generator.newLabel();
        String f = generator.newLabel();

        Node node = new LiteralBoolExprNode(C.LOC, true);
        IRStmt expected = make.IRJump(make.IRName(t));
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
        IRStmt expected = make.IRJump(make.IRName(f));
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
        IRStmt expected = make.IRSeq(make.IRJump(make.IRName(l1)),
                make.IRLabel(l1), make.IRJump(make.IRName(f))
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
        IRStmt expected = make.IRSeq(make.IRSeq(make.IRJump(make.IRName(l3)),
                make.IRLabel(l3), make.IRJump(make.IRName(f))
            ),
                make.IRLabel(l2), make.IRSeq(make.IRJump(make.IRName(l4)),
                        make.IRLabel(l4), make.IRJump(make.IRName(t))
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
        IRStmt expected = make.IRSeq(make.IRJump(make.IRName(t)),
                make.IRLabel(l1), make.IRJump(make.IRName(f))
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
        IRStmt expected = make.IRSeq(make.IRSeq(make.IRJump(make.IRName(l3)),
                make.IRLabel(l3), make.IRJump(make.IRName(t))
            ),
                make.IRLabel(l2), make.IRSeq(make.IRJump(make.IRName(t)),
                        make.IRLabel(l4), make.IRJump(make.IRName(f))
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
        IRNode expected = make.IRSeq(make.IRSeq(
                make.IRCJump(make.IRCall(make.IRName("_If_b")), l3, l2),
                make.IRLabel(l3),
                make.IRCJump(make.IRCall(make.IRName("_Ig_b")), t, l2)
            ),
                make.IRLabel(l2),
                make.IRSeq(
                        make.IRCJump(make.IRCall(make.IRName("_Ih_b")), l4, f),
                        make.IRLabel(l4),
                        make.IRCJump(make.IRCall(make.IRName("_Ii_b")), t, f)
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
        IRNode expected = make.IRCJump(make.IRCall(make.IRName("_If_b")), t, f);
        assertEquals(
            expected,
            node.accept(new CTranslationVisitor(generator, t, f))
        );
    }

}
