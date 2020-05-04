package cyr7.ir.cfg;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import cyr7.cfg.constructor.CFGConstructor;
import cyr7.cfg.nodes.ir.CFGNode;
import cyr7.ir.nodes.IRCJump;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRMem;
import cyr7.ir.nodes.IRMove;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRTemp;
import java_cup.runtime.ComplexSymbolFactory.Location;

class TestGenerateBasicCFG {

    @Test
    void testEmptyFunction() {
        Location loc = new Location(-1, -1);
        var func = new IRFuncDecl(loc, "empty", new IRSeq(loc, new IRReturn(loc)));
        var map = new HashMap<String, IRFuncDecl>();
        map.put("empty", func);

        var comp = new IRCompUnit(loc, "base", map);

        Map<String, CFGNode> result = CFGConstructor.constructCFG(comp);

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

        System.out.println(result);
    }


    @Test
    void testIfStmtFunction() {
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

        System.out.println(result);
    }

}
