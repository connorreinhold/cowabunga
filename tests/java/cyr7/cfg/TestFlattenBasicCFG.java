package cyr7.cfg;

<<<<<<< HEAD:tests/java/cyr7/cfg/TestFlattenBasicCFG.java
import java.io.PrintWriter;
=======
import java.io.StringReader;
>>>>>>> fold-and-construct-cfg:tests/java/cyr7/ir/cfg/TestFlattenBasicCFG.java
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.junit.jupiter.api.Test;

<<<<<<< HEAD:tests/java/cyr7/cfg/TestFlattenBasicCFG.java
import cyr7.cfg.CFGUtil;
import cyr7.cfg.nodes.CFGNode;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.cfg.CFGConstructor;
=======
import cyr7.cfg.flatten.CFGFlattener;
import cyr7.cfg.nodes.CFGNode;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IRUtil;
>>>>>>> fold-and-construct-cfg:tests/java/cyr7/ir/cfg/TestFlattenBasicCFG.java
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

<<<<<<< HEAD:tests/java/cyr7/cfg/TestFlattenBasicCFG.java
    //@Test
=======
    private void testWithAlternateFlattener(CFGNode root) {
        IRStmt result = CFGFlattener.flatten(root);
        System.out.println(result);
    }

    @Test
>>>>>>> fold-and-construct-cfg:tests/java/cyr7/ir/cfg/TestFlattenBasicCFG.java
    void testAssignmentsFunction() {
        Location loc = new Location(-1, -1);
        var func = new IRFuncDecl(loc, "assign", new IRSeq(loc,
                new IRMove(loc, new IRTemp(loc, "target"), new IRConst(loc, 0)),
                new IRMove(loc, new IRMem(loc, new IRConst(loc, 0)), new IRConst(loc, 0)),
                new IRReturn(loc, 0)));

        var map = new HashMap<String, IRFuncDecl>();
        map.put("assign", func);
        var comp = new IRCompUnit(loc, "base", map);
        Map<String, CFGNode> result = CFGConstructor.constructCFG(comp);
<<<<<<< HEAD:tests/java/cyr7/cfg/TestFlattenBasicCFG.java
        IRNode node = CFGUtil.generateIR(result.get("assign"), new DefaultIdGenerator());
        System.out.println(node);
    }
    
    //@Test
=======

        this.testWithAlternateFlattener(result.get("assign"));
        // IRNode node = CFGToIRGenerator.generateIR(result.get("assign"), new
        // DefaultIdGenerator());
        // System.out.println(node);
    }

    @Test
>>>>>>> fold-and-construct-cfg:tests/java/cyr7/ir/cfg/TestFlattenBasicCFG.java
    void testIFElseFunction() {
        Location loc = new Location(-1, -1);
        var func = new IRFuncDecl(loc, "if", new IRSeq(loc,
                new IRCJump(loc, new IRConst(loc, 0), "Hello_World"),
                new IRMove(loc, new IRTemp(loc, "target"), new IRConst(loc, 0)),
                new IRLabel(loc, "Hello_World"),
                new IRReturn(loc, 0)));

        var map = new HashMap<String, IRFuncDecl>();
        map.put("if", func);
        var comp = new IRCompUnit(loc, "base", map);
        Map<String, CFGNode> result = CFGConstructor.constructCFG(comp);

<<<<<<< HEAD:tests/java/cyr7/cfg/TestFlattenBasicCFG.java
        IRNode node = CFGUtil.generateIR(result.get("if"), new DefaultIdGenerator());
        System.out.println(node);
=======
        this.testWithAlternateFlattener(result.get("if"));
        // IRNode node = CFGToIRGenerator.generateIR(result.get("if"), new
        // DefaultIdGenerator());
        // System.out.println(node);
    }

    @Test
    void testWhileFunction() throws Exception {

        String prgmString = "main(): int { while (false) { while(true) { } } if (133 > 0) { return 43 } return 12 }";

        IRCompUnit comp = IRUtil.generateIR(new StringReader(prgmString),
                "while.xi", null, new IRUtil.LowerConfiguration(true, true),
                new DefaultIdGenerator());

        Map<String, CFGNode> result = CFGConstructor.constructCFG(comp);
        this.testWithAlternateFlattener(new LinkedList<>(result.values()).get(0));
        // IRNode node = CFGToIRGenerator.generateIR(result.get("if"), new
        // DefaultIdGenerator());
        // System.out.println(node);
    }



    @Test
    void testJustReturnFunction() throws Exception {

        String prgmString = "main(): int { return 13 }";

        IRCompUnit comp = IRUtil.generateIR(new StringReader(prgmString),
                "return.xi", null, new IRUtil.LowerConfiguration(true, true),
                new DefaultIdGenerator());

        Map<String, CFGNode> result = CFGConstructor.constructCFG(comp);
        this.testWithAlternateFlattener(new LinkedList<>(result.values()).get(0));
        // IRNode node = CFGToIRGenerator.generateIR(result.get("if"), new
        // DefaultIdGenerator());
        // System.out.println(node);
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
        // IRNode node = CFGToIRGenerator.generateIR(result.get("if"), new
        // DefaultIdGenerator());
        // System.out.println(node);
    }


    @Test
    void testEmpty() throws Exception {

        String prgmString = "main() { }";

        IRCompUnit comp = IRUtil.generateIR(new StringReader(prgmString),
                "nestedControls.xi", null, new IRUtil.LowerConfiguration(true, true),
                new DefaultIdGenerator());

        Map<String, CFGNode> result = CFGConstructor.constructCFG(comp);
        this.testWithAlternateFlattener(new LinkedList<>(result.values()).get(0));
        // IRNode node = CFGToIRGenerator.generateIR(result.get("if"), new
        // DefaultIdGenerator());
        // System.out.println(node);
>>>>>>> fold-and-construct-cfg:tests/java/cyr7/ir/cfg/TestFlattenBasicCFG.java
    }
    
    @Test
    void tempDotGeneration() {
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
        
        CFGUtil.outputToDot(result.get("if"), new PrintWriter(System.out));

        IRNode node = CFGUtil.generateIR(result.get("if"), new DefaultIdGenerator());
    }
    


}
