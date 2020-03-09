package cyr7.ir.util;

import cyr7.ast.AbstractNode;
import cyr7.ir.AstToIrVisitor;
import cyr7.ir.interpret.IRSimulator;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.nodes.IRStmt;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class IRFactory {

    public static IRNode parseAstExpr(AbstractNode astNode) {
        AstToIrVisitor visitor = new AstToIrVisitor();
        IRNode result = astNode.accept(visitor).assertFirst();
        return result;
    }

    public static IRNode parseAstStmt(AbstractNode astNode) {
        AstToIrVisitor visitor = new AstToIrVisitor();
        IRNode result = astNode.accept(visitor).assertSecond();
        return result;
    }

    public static long testExpr(IRNode irNode) {
        return 0;
    }

    public static long testStmts(IRStmt irStmts) {
        Location loc = new Location(-1, -1);
        IRStmt bBody = irStmts;
        IRFuncDecl bFunc = new IRFuncDecl(loc, "function", bBody);
        IRCompUnit compUnit = new IRCompUnit(loc, "test");
        compUnit.appendFunc(bFunc);

        IRSimulator sim = new IRSimulator(compUnit);
        return sim.call("function");
    }

    // Cannot downcast stmt into IRFuncDecl
//    public static long testStatement(String program) throws Exception {
//        XiProgramNode node = (XiProgramNode) ParserUtil.parseNode(new StringReader(program), "test",
//                false);
//        TypeCheckUtil.typecheck(node);
//        IRCompUnit compUnit = new IRCompUnit(null, "test");
//        IRFuncDecl stmt = node.functions.get(0)
//                .accept(new AstToIrVisitor()).assertSecond();
//        compUnit.appendFunc(stmt);
//    }

}
