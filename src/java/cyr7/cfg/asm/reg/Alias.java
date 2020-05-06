package cyr7.cfg.asm.reg;

import cyr7.x86.asm.ASMTempRegArg;

import java.util.HashMap;
import java.util.HashSet;

final class Alias {

    private final HashMap<ASMTempRegArg, ASMTempRegArg> alias;
    private final HashSet<ASMTempRegArg> coalescedNodes;

    Alias(HashSet<ASMTempRegArg> coalescedNodes) {
        this.coalescedNodes = coalescedNodes;
        this.alias = new HashMap<>();
    }

    public void make(ASMTempRegArg from, ASMTempRegArg to) {
        alias.put(from, to);
    }

    public ASMTempRegArg get(ASMTempRegArg n) {
        if (coalescedNodes.contains(n)) {
            return get(alias.get(n));
        } else {
            return n;
        }
    }

}
