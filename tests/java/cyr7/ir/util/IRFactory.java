package cyr7.ir.util;

import java.io.StringReader;

import cyr7.ast.AbstractNode;
import cyr7.ast.Node;
import cyr7.ast.toplevel.XiProgramNode;
import cyr7.ir.AstToIrVisitor;
import cyr7.ir.interpret.IRSimulator;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;
import cyr7.parser.ParserUtil;
import cyr7.typecheck.TypeCheckUtil;

public class IRFactory {

    public static IRNode parseAstExpr(AbstractNode astNode) {
        AstToIrVisitor visitor = new AstToIrVisitor();
        // not what we want eventually
        IRNode result = astNode.accept(visitor).assertFirst();
        return result;
    }

    public static IRNode parseAstStmt(AbstractNode astNode) {
        AstToIrVisitor visitor = new AstToIrVisitor();
        // not what we want eventually
        IRNode result = astNode.accept(visitor).assertSecond();
        return result;
    }

    public static long testExpr(IRNode irNode) {
//        IRExpr expr = (IRExpr) irNode;
//        IRStmt bBody = new IRSeq(new IRReturn());
//        IRFuncDecl bFunc = new IRFuncDecl("function", bBody);
//        IRCompUnit compUnit = new IRCompUnit("test");
//        compUnit.appendFunc(bFunc);
//
//        IRSimulator sim = new IRSimulator(compUnit);
//        return sim.call("function");
        return 0;
    }

    public static long testStmts(IRStmt irStmts) {
        // needs to be removed
//        IRStmt bBody = irStmts;
//        IRFuncDecl bFunc = new IRFuncDecl("function", bBody);
//        IRCompUnit compUnit = new IRCompUnit("test");
//        compUnit.appendFunc(bFunc);
//
//        IRSimulator sim = new IRSimulator(compUnit);
//        return sim.call("function");
        return 0;

    }

    public static long testStatement(String program) throws Exception {
        XiProgramNode node = (XiProgramNode) ParserUtil.parseNode(new StringReader(program), "test",
                false);
        TypeCheckUtil.typecheck(node);
        IRCompUnit compUnit = new IRCompUnit("test");
        IRFuncDecl stmt = (IRFuncDecl) node.functions.get(0).accept(new AstToIrVisitor()).assertSecond();
        compUnit.appendFunc 
    }

}
