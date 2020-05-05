package cyr7.cfg.ir.dfa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import cyr7.cfg.ir.constructor.CFGConstructor;
import cyr7.cfg.ir.dfa.DominatorAnalysis.LatticeElement;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IRUtil;
import cyr7.ir.IRUtil.LowerConfiguration;
import cyr7.ir.nodes.IRCompUnit;

public class testStuff {
    public static void main(String[] args) throws Exception {
        File f = new File("tests/resources/testJunk.xi");
        FileReader fr = new FileReader(f);
        BufferedReader br  = new BufferedReader(fr);
        Reader reader = new BufferedReader(br);
        IRCompUnit lowered = IRUtil.generateIR(
            reader,
            "testJunk.xi",
            null,
            new LowerConfiguration(true, true),
            new DefaultIdGenerator());
        Map<String, CFGNode> cfgResult = CFGConstructor.constructCFG(lowered);
        CFGStartNode start = (CFGStartNode) cfgResult.get("_Imain_paai");
        Map<CFGNode, Map<CFGNode, LatticeElement>> result = 
                WorklistAnalysis.analyze(start, DominatorAnalysis.INSTANCE);
        for(Map.Entry<CFGNode, Map<CFGNode, LatticeElement>> pair: result.entrySet()) {
            System.out.println(pair.getKey()+":  "+pair.getValue());
        }
        
    }
}
