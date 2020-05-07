package cyr7.cfg.ir.dfa.loops;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import cyr7.cfg.ir.dfa.ForwardDataflowAnalysis;
import cyr7.cfg.ir.dfa.ForwardTransferFunction;
import cyr7.cfg.ir.dfa.loops.DerivedInductionVariableAnalysis.LatticeElement;

public class DerivedInductionVariableAnalysis implements ForwardDataflowAnalysis<LatticeElement> {

    public interface LatticeElement {

    }

    @Override
    public LatticeElement topValue() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LatticeElement meet(LatticeElement lhs, LatticeElement rhs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ForwardTransferFunction<LatticeElement> transfer() {
        // TODO Auto-generated method stub
        return null;
    }
}
