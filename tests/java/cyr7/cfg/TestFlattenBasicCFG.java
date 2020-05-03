package cyr7.cfg;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.junit.jupiter.api.Test;

import cyr7.cfg.constructor.CFGConstructor;
import cyr7.cfg.flatten.CFGFlattener;
import cyr7.cfg.nodes.ir.CFGNode;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IRUtil;
import cyr7.ir.cfg.CFGConstructor;
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

    @Test
    void testAssignmentsFunction() {
        Location loc = new Location(-1, -1);
        var func = new IRFuncDecl(loc, "assign", new IRSeq(loc,
                new IRMove(loc, new IRTemp(loc, "target"), new IRConst(loc, 0)),
                new IRMove(loc, new IRMem(loc, new IRConst(loc, 0)), new IRConst(loc, 0)),
                new IRReturn(loc)));

        var map = new HashMap<String, IRFuncDecl>();
        map.put("assign", func);
        var comp = new IRCompUnit(loc, "base", map);
        Map<String, CFGNode> result = CFGConstructor.constructCFG(comp);

        this.testWithAlternateFlattener(result.get("assign"));
    }

    @Test
    void testIFElseFunction() {
        Location loc = new Location(-1, -1);
        var func = new IRFuncDecl(loc, "if", new IRSeq(loc,
                new IRCJump(loc, new IRConst(loc, 0), "Hello_World"),
                new IRMove(loc, new IRTemp(loc, "target"), new IRConst(loc, 0)),
                new IRLabel(loc, "Hello_World"),
                new IRReturn(loc)));

        var map = new HashMap<String, IRFuncDecl>();
        map.put("if", func);
        var comp = new IRCompUnit(loc, "base", map);
        Map<String, CFGNode> result = CFGConstructor.constructCFG(comp);

        this.testWithAlternateFlattener(new LinkedList<>(result.values()).get(0));
    }

    @Test
    void testWhileFunction() throws Exception {

        String prgmString = "main(): int { a:int = 3; while (true) { while(true) { } } if (133 > 0) { return 43 } return 12 }";

        IRCompUnit comp = IRUtil.generateIR(new StringReader(prgmString),
                "while.xi", null, new IRUtil.LowerConfiguration(true, true),
                new DefaultIdGenerator());

        Map<String, CFGNode> result = CFGConstructor.constructCFG(comp);
        this.testWithAlternateFlattener(new LinkedList<>(result.values()).get(0));
    }


    @Test
    void testBasicInfiniteLoop() throws Exception {

        String prgmString = "main() { while (true) { } }";

        IRCompUnit comp = IRUtil.generateIR(new StringReader(prgmString),
                "while.xi", null, new IRUtil.LowerConfiguration(true, true),
                new DefaultIdGenerator());

        Map<String, CFGNode> result = CFGConstructor.constructCFG(comp);
        this.testWithAlternateFlattener(new LinkedList<>(result.values()).get(0));
    }



    @Test
    void testJustReturnFunction() throws Exception {

        String prgmString = "main(): int { return 13 }";

        IRCompUnit comp = IRUtil.generateIR(new StringReader(prgmString),
                "return.xi", null, new IRUtil.LowerConfiguration(true, true),
                new DefaultIdGenerator());
        Map<String, CFGNode> result = CFGConstructor.constructCFG(comp);
        this.testWithAlternateFlattener(new LinkedList<>(result.values()).get(0));
    }


    @Test
    void testNestedControls() throws Exception {

        String prgmString = "main() { i: int = 232 "
                + "while (i < 32) {"
                + "    if (i%2 == 0) { i = 32 } else {"
                + "        k: int = 0"
                + "        while (k < 12) k = k + 1"
                + "    }"
                + "    i = i + 1"
                + "}"
                + "}";

        IRCompUnit comp = IRUtil.generateIR(new StringReader(prgmString),
                "nestedControls.xi", null, new IRUtil.LowerConfiguration(true, true),
                new DefaultIdGenerator());

        Map<String, CFGNode> result = CFGConstructor.constructCFG(comp);
        this.testWithAlternateFlattener(new LinkedList<>(result.values()).get(0));
    }

    @Test
    void testEmpty() throws Exception {

        String prgmString = "main() { }";

        IRCompUnit comp = IRUtil.generateIR(new StringReader(prgmString),
                "nestedControls.xi", null, new IRUtil.LowerConfiguration(true, true),
                new DefaultIdGenerator());

        Map<String, CFGNode> result = CFGConstructor.constructCFG(comp);

        this.testWithAlternateFlattener(new LinkedList<>(result.values()).get(0));
    }
}
