package cyr7.cfg.ir.nodes;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cyr7.cfg.ir.dfa.BackwardTransferFunction;
import cyr7.cfg.ir.dfa.ForwardTransferFunction;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.visit.IRExprVarsVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGIfNode extends CFGNode {

    private CFGNode trueBranch;
    private CFGNode falseBranch;
    public IRExpr cond;

    private Set<String> useSet;

    public CFGIfNode(Location location, CFGNode trueBranch,
            CFGNode falseBranch, IRExpr cond) {
        super(location);
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
        this.cond = cond;

        this.refreshDfaSets();

        this.updateIns();
        repOk();
    }

    @Override
    public <T> T accept(IrCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public CFGNode falseBranch() {
        return falseBranch;
    }

    public CFGNode trueBranch() {
        return trueBranch;
    }

    @Override
    public List<CFGNode> out() {
        return List.of(trueBranch, falseBranch);
    }

    @Override
    public void replaceOutEdge(CFGNode previous, CFGNode n) {
        if (falseBranch != previous && trueBranch != previous) {
            throw new UnsupportedOperationException(
                    "Cannot replace node arbitrarily.");
        }

        if (trueBranch == previous) {
            this.trueBranch = n;
        }

        if (falseBranch == previous) {
            this.falseBranch = n;
        }

        this.updateIns();
        repOk();
    }


    @Override
    public <T> List<T> acceptForward(ForwardTransferFunction<T> transferFunction, T in) {
        return List.of(
            transferFunction.transferTrue(this, in),
            transferFunction.transferFalse(this, in));
    }

    @Override
    public <T> T acceptBackward(BackwardTransferFunction<T> transferFunction, T input) {
        return transferFunction.transfer(this, input);
    }

    @Override
    public String toString() {
        String condString = cond.toString().replaceAll("\n", "");
        return String.format("if(%s)", condString);
    }

    @Override
    public Set<String> defs() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> uses() {
        return Collections.unmodifiableSet(this.useSet);
    }

    @Override
    public Map<String, String> gens() {
        return Collections.emptyMap();
    }

    @Override
    public Set<String> kills() {
        return Collections.emptySet();
    }

    @Override
    public void refreshDfaSets() {
        this.useSet = cond.accept(IRExprVarsVisitor.INSTANCE);
    }

}