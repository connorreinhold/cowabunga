package cyr7.cfg;


import cyr7.C;
import cyr7.cfg.ir.nodes.CFGNodeFactory;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;

public class TestDominatorAnalysis {

    private static final CFGNodeFactory make = new CFGNodeFactory(C.LOC);
    private static final IRNodeFactory makeIR = new IRNodeFactory_c(C.LOC);

}
