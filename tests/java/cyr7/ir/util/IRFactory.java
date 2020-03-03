package cyr7.ir.util;

import cyr7.ast.AbstractNode;
import cyr7.ir.AstToIrVisitor;
import cyr7.ir.interpret.IRSimulator;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;

public class IRFactory {
    public static IRNode parseAst(AbstractNode astNode) {
        AstToIrVisitor visitor = new AstToIrVisitor();
        // not what we want eventually
        IRNode result = astNode.accept(visitor).assertFirst();
        return result;
    }

    public static long testExpr(IRNode irNode) {
        IRExpr expr = (IRExpr) irNode;
        IRStmt bBody = new IRSeq(new IRReturn(expr));
        IRFuncDecl bFunc = new IRFuncDecl("function", bBody);
        IRCompUnit compUnit = new IRCompUnit("test");
        compUnit.appendFunc(bFunc);

        IRSimulator sim = new IRSimulator(compUnit);
        return sim.call("function");
    }
}
