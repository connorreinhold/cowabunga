package cyr7.cfg.asm.reg;

import cyr7.cfg.asm.dfa.BackwardDataflowAnalysis;
import cyr7.cfg.asm.nodes.AsmCFGNode;
import cyr7.util.Sets;
import cyr7.x86.asm.ASMTempRegArg;

import java.util.Collections;
import java.util.Set;

public final class LiveVariableAnalysis
    implements BackwardDataflowAnalysis<Set<ASMTempRegArg>> {

    private final UsesVisitor uses;
    private final DefsVisitor defs;

    public LiveVariableAnalysis() {
        this.uses = new UsesVisitor();
        this.defs = new DefsVisitor();
    }

    @Override
    public Set<ASMTempRegArg> topValue() {
        return Collections.emptySet();
    }

    @Override
    public Set<ASMTempRegArg> transfer(AsmCFGNode n, Set<ASMTempRegArg> out) {
        Set<ASMTempRegArg> uses = n.accept(this.uses);
        Set<ASMTempRegArg> defs = n.accept(this.defs);

        // use[n] u (out[n] - def[n])
        return Sets.union(uses, Sets.difference(out, defs));
    }

    @Override
    public Set<ASMTempRegArg> meet(Set<ASMTempRegArg> lhs,
                                   Set<ASMTempRegArg> rhs) {
        return Sets.union(lhs, rhs);
    }

}
