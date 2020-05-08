package cyr7.cfg.ir.nodes;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cyr7.cfg.ir.dfa.BackwardTransferFunction;
import cyr7.cfg.ir.dfa.ForwardTransferFunction;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.visit.IRExprVarsVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGCallNode extends CFGNode {

    // This includes both procedures and function calls
    public IRCallStmt call;
    private CFGNode out;

    private Set<String> useSet;
    private Set<String> defSet;
    private Set<String> killSet;

    public CFGCallNode(Location location, IRCallStmt call, CFGNode out) {
        super(location);
        this.call = call;
        this.out = out;

        this.refreshDfaSets();

        this.updateIns();
        repOk();
    }

    public CFGNode outNode() {
        return out;
    }

    @Override
    public List<CFGNode> out() {
        return List.of(out);
    }

    @Override
    public <T> T accept(IrCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void replaceOutEdge(CFGNode previous, CFGNode n) {
        if (out == previous) {
            this.out = n;
            this.updateIns();
        } else {
            throw new UnsupportedOperationException(
                    "Cannot replace node arbitrarily.");
        }
        repOk();
    }

    @Override
    public <T> List<T> acceptForward(
            ForwardTransferFunction<T> transferFunction, T in) {
        return List.of(transferFunction.transfer(this, in));
    }

    @Override
    public <T> T acceptBackward(BackwardTransferFunction<T> transferFunction,
            T input) {
        return transferFunction.transfer(this, input);
    }

    @Override
    public String toString() {
        String callString = call.toString().replaceAll("\n", "");
        return callString;
    }

    @Override
    public Set<String> defs() {
        return Collections.unmodifiableSet(this.defSet);
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
        return Collections.unmodifiableSet(this.killSet);
    }

    @Override
    public void refreshDfaSets() {
        this.useSet = call.args().stream().flatMap(arg -> {
            return arg.accept(IRExprVarsVisitor.INSTANCE).stream();
        }).collect(Collectors.toSet());
        this.defSet = Set.copyOf(call.collectors());
        this.killSet = Set.copyOf(call.collectors());
    }
}
