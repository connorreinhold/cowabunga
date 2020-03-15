package cyr7.ir.fold;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cyr7.ir.fold.basic.DirectConstFoldVisitor;
import cyr7.ir.fold.main.MainConstantFolder;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.util.OneOfThree;
import cyr7.visitor.MyIRVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;
import polyglot.util.Pair;

public class TestCompleteBinop {

    /**
     * IRNodeFactory with a default location. Location is more or less
     * irrelevant during testing.
     */
    private final IRNodeFactory make = new IRNodeFactory_c(
            new Location(-1, -1));

    private IRExpr constant(long n) {
        return make.IRConst(n);
    }

    private IRExpr constant(boolean n) {
        return make.IRConst(n ? 1 : 0);
    }
    
    private IRExpr temp(String t) {
    	return make.IRTemp(t);
    }
    

    private IRExpr binop(OpType t, long l, long r) {
        return make.IRBinOp(t, constant(l), constant(r));
    }
    
    private IRExpr neg(IRExpr n) {
    	return binop(OpType.XOR, constant(1), n);
    }

    private IRExpr binop(OpType t, boolean l, boolean r) {
        return make.IRBinOp(t, constant(l ? 1 : 0), constant(r ? 1 : 0));
    }

    private IRExpr binop(OpType t, IRExpr l, IRExpr r) {
        return make.IRBinOp(t, l, r);
    }

    private void test(IRExpr expected, IRExpr actual) {
        assertEquals(expected, MainConstantFolder.foldExpr(actual));
    }
    
    
    @Test
    public void testAdditionAssociativity() {
        IRExpr test = binop(OpType.ADD,
                binop(OpType.ADD, constant(12), temp("t")),
                binop(OpType.ADD, constant(-3), temp("a")));
        IRExpr expected = binop(OpType.ADD, 
        				        binop(OpType.ADD, temp("t"), temp("a")), 
        				        constant(9));
        test(expected, test);
    }
    
    @Test
    public void testNegations() {
    	var base = temp("d");
        var actual = neg(neg(base));
        var expected = base;
        test(expected, actual);
        
        actual = neg(neg(neg(base)));
        expected = neg(base);
        test(expected, actual);
    }
    
    @Test
    public void testDeMorgan() {
        var actual = binop(OpType.OR, neg(temp("a")), neg(temp("b")));
        var expected = neg(binop(OpType.AND, temp("a"), temp("b")));
        test(expected, actual);
        
    }
       
	
}
