package cyr7.ir.cfg;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import cyr7.cfg.flatten.CFGFlattener;
import cyr7.cfg.nodes.CFGNode;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IRUtil;
import cyr7.ir.nodes.IRCJump;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRMem;
import cyr7.ir.nodes.IRMove;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;
import cyr7.ir.nodes.IRTemp;
import java_cup.runtime.ComplexSymbolFactory.Location;

class TestFlattenBasicCFG {

    private void testWithAlternateFlattener(CFGNode root) {
        IRStmt result = CFGFlattener.flatten(root);
        System.out.println(result);
    }

    @Disabled
    @Test
    void testAssignmentsFunction() {
        Location loc = new Location(-1, -1);
        var func = new IRFuncDecl(loc, "assign", new IRSeq(loc, new IRMove(loc,
                new IRTemp(loc, "target"), new IRConst(loc, 0)), new IRMove(loc,
                        new IRMem(loc, new IRConst(loc, 0)), new IRConst(loc,
                                0)), new IRReturn(loc)));

        var map = new HashMap<String, IRFuncDecl>();
        map.put("assign", func);
        var comp = new IRCompUnit(loc, "base", map);
        Map<String, CFGNode> result = CFGConstructor.constructCFG(comp);

        this.testWithAlternateFlattener(result.get("assign"));
        // IRNode node = CFGToIRGenerator.generateIR(result.get("assign"), new
        // DefaultIdGenerator());
        // System.out.println(node);
    }

    @Disabled
    @Test
    void testIFElseFunction() {
        Location loc = new Location(-1, -1);
        var func = new IRFuncDecl(loc, "if", new IRSeq(loc, new IRCJump(loc,
                new IRConst(loc, 0), "Hello_World"), new IRMove(loc, new IRTemp(
                        loc, "target"), new IRConst(loc, 0)), new IRLabel(loc,
                                "Hello_World"), new IRReturn(loc)));

        var map = new HashMap<String, IRFuncDecl>();
        map.put("if", func);

        var comp = new IRCompUnit(loc, "base", map);

        Map<String, CFGNode> result = CFGConstructor.constructCFG(comp);

        this.testWithAlternateFlattener(result.get("if"));
        // IRNode node = CFGToIRGenerator.generateIR(result.get("if"), new
        // DefaultIdGenerator());
        // System.out.println(node);
    }

    @Test
    void testWhileFunction() throws Exception {

        String prgmString = "main(): int { while (false) { while(true) { } }  if (133 > 0) { return 43 } return 12 }";

        IRCompUnit comp = IRUtil.generateIR(new StringReader(prgmString),
                "while.xi", null, new IRUtil.LowerConfiguration(true, true),
                new DefaultIdGenerator());

        Map<String, CFGNode> result = CFGConstructor.constructCFG(comp);
        var stuff = new LinkedList<>(result.values()).get(0);
        System.out.println(stuff);
        this.testWithAlternateFlattener(new LinkedList<>(result.values()).get(0));
        // IRNode node = CFGToIRGenerator.generateIR(result.get("if"), new
        // DefaultIdGenerator());
        // System.out.println(node);
    }

}
