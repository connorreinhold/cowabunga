package cyr7.cfg.asm.reg;

import cyr7.cfg.asm.dfa.BackwardDataflowAnalysis;
import cyr7.cfg.asm.nodes.AsmCFGNode;
import cyr7.util.Sets;
import cyr7.x86.asm.ASMTempRegArg;

import java.util.Collections;
import java.util.Set;

public final class LiveVariableAnalysis
    implements BackwardDataflowAnalysis<Set<? extends ASMTempRegArg>> {

    private final UsesVisitor uses;
    private final DefsVisitor defs;

    public LiveVariableAnalysis(String mangledName) {
        this.uses = new UsesVisitor(mangledName);
        this.defs = new DefsVisitor();
    }

    @Override
    public Set<ASMTempRegArg> topValue() {
        return Collections.emptySet();
    }

    @Override
    public Set<? extends ASMTempRegArg> transfer(AsmCFGNode n, Set<? extends ASMTempRegArg> out) {
        Set<? extends ASMTempRegArg> uses = n.accept(this.uses);
        Set<? extends ASMTempRegArg> defs = n.accept(this.defs);

        // use[n] u (out[n] - def[n])
        return Sets.union(uses, Sets.difference(out, defs));
    }

    @Override
    public Set<? extends ASMTempRegArg> meet(Set<? extends ASMTempRegArg> lhs,
                                   Set<? extends ASMTempRegArg> rhs) {
        return Sets.union(lhs, rhs);
    }

}
