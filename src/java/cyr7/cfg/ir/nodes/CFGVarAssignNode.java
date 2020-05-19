package cyr7.cfg.ir.nodes;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cyr7.cfg.ir.dfa.BackwardTransferFunction;
import cyr7.cfg.ir.dfa.ForwardTransferFunction;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import cyr7.ir.interpret.Configuration;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRTemp;
import cyr7.ir.visit.IRExprVarsVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGVarAssignNode extends CFGNode {

    public String variable;
    public IRExpr value;
    private CFGNode outNode;
    private Set<String> useSet;
    private Set<String> defSet;
    private Set<String> killSet;
    private Map<String, String> genSet;

    public CFGVarAssignNode(Location location, String variable, IRExpr value,
            CFGNode outNode) {
        super(location);
        this.variable = variable;
        this.value = value;
        this.outNode = outNode;

        this.refreshDfaSets();

        this.updateIns();
        repOk();
    }

    @Override
    public List<CFGNode> out() {
        return List.of(outNode);
    }

    public CFGNode outNode() {
        return outNode;
    }

    @Override
    public <T> T accept(IrCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void replaceOutEdge(CFGNode previous, CFGNode n) {
        if (outNode == previous) {
            this.outNode = n;
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
        String valueString = value.toString()
                                  .replaceAll("\n", "");
        return String.format("%s=%s", variable, valueString);
    }

    @Override
    public CFGNode copy(List<CFGNode> out) {
        assert out.size() == 1;
        return new CFGVarAssignNode(this.location(), variable, value, out.get(0));
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
        return Collections.unmodifiableMap(this.genSet);
    }

    @Override
    public Set<String> kills() {
        return Collections.unmodifiableSet(this.killSet);
    }

    @Override
    public void refreshDfaSets() {
        if (this.value instanceof IRTemp
            && ((IRTemp)this.value).name().equals(this.variable)) {
            this.useSet = Collections.emptySet();
        } else {
            this.useSet = value.accept(IRExprVarsVisitor.INSTANCE);
        }
        this.defSet = Collections.singleton(variable);

        this.killSet = Collections.singleton(variable);
        this.genSet = new HashMap<>();
        if (value instanceof IRTemp) {
            String source = ((IRTemp)value).name();
            if (!source.startsWith(Configuration.ABSTRACT_ARG_PREFIX)
                    && !source.equals(this.variable)) {
                genSet.put(variable, source);
            }
        }
    }
}
