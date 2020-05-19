package cyr7.cfg.ir.nodes;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cyr7.cfg.ir.dfa.BackwardTransferFunction;
import cyr7.cfg.ir.dfa.ForwardTransferFunction;
import cyr7.cfg.ir.visitor.IrCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGReturnNode extends CFGNode {

    public CFGReturnNode(Location location) {
        super(location);
        this.updateIns();
        assert repOk();
    }

    @Override
    public List<CFGNode> out() {
        return List.of();
    }

    @Override
    public <T> T accept(IrCFGVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void replaceOutEdge(CFGNode stub, CFGNode n) {
        assert repOk();
        return;
    }

    @Override
    public <T> List<T> acceptForward(ForwardTransferFunction<T> transferFunction, T in) {
        return List.of();
    }

    @Override
    public <T> T acceptBackward(BackwardTransferFunction<T> transferFunction, T input) {
        return transferFunction.transfer(this, input);
    }

    @Override
    public String toString() {
        return "(return)";
    }

    @Override
    public CFGNode copy(List<CFGNode> out) {
        assert out.size() == 0;
        return new CFGReturnNode(this.location());
    }
    
    public Set<String> defs() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> uses() {
        return Collections.emptySet();
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
        return;
    }
}
