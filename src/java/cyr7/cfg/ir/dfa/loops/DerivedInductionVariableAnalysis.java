package cyr7.cfg.ir.dfa.loops;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import cyr7.cfg.ir.dfa.ForwardDataflowAnalysis;
import cyr7.cfg.ir.dfa.ForwardTransferFunction;
import cyr7.cfg.ir.dfa.loops.inductionvars.InductionVariable;
import cyr7.cfg.ir.dfa.loops.inductionvars.DefinedInductionVariable;
import cyr7.cfg.ir.dfa.loops.DominatorAnalysis.InfiniteSet;
import cyr7.cfg.ir.dfa.loops.inductionvars.NotInductionVariable;
import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGSelfLoopNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRTemp;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.util.Sets;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.pattern.BiPatternBuilder;
/**
 * This class is used for determining all possible induction variables and their relationships 
 * to basic induction variables

    Note: Undefined IVs are not contained in a node's map.
 */
public class DerivedInductionVariableAnalysis implements ForwardDataflowAnalysis<Map<String, InductionVariable>> {
    private final Map<String, InductionVariable> top;
    
    public DerivedInductionVariableAnalysis(Set<String> basicVars) {
        Map<String, InductionVariable> temp = new HashMap<>();
        for(String variable: basicVars) {
            temp.put(variable, new DefinedInductionVariable(variable, 1, 0));
        }
        top = Collections.unmodifiableMap(temp);
    }

    @Override
    public Map<String, InductionVariable> topValue() {
        return top;
    }

    @Override
    public Map<String, InductionVariable> meet(Map<String, InductionVariable> lhs, 
            Map<String, InductionVariable> rhs) {

        Map<String, InductionVariable> meet = new HashMap<>();

        for(Map.Entry<String, InductionVariable> pair: rhs.entrySet()) {
            String variable = pair.getKey();
            if (lhs.containsKey(variable)) {
                if (rhs.get(variable) != NotInductionVariable.INSTANCE 
                        && rhs.get(variable).equals(lhs.get(variable))) {
                    // if the variables have the same mapping in lhs and rhs
                    meet.put(variable, rhs.get(variable));
                } else {
                    // if they aren't equal, set to impossible
                    // Feel like there might be an issue here
                    meet.put(variable, NotInductionVariable.INSTANCE);
                }
            } else {
                // if RHS has value and LHS is undefined
                meet.put(variable, rhs.get(variable));
            }
        }
        
        for(Map.Entry<String, InductionVariable> pair: lhs.entrySet()) {
            // if LHS has value and RHS is undefined
            if (!meet.containsKey(pair.getKey())) {
                meet.put(pair.getKey(), pair.getValue());
            }
        }
        
        return meet;
    }

    @Override
    public ForwardTransferFunction<Map<String, InductionVariable>> transfer() {
        return TransferFunction.INSTANCE;
    }
    
    private enum TransferFunction implements ForwardTransferFunction<Map<String, InductionVariable>> {
        INSTANCE;

        @Override
        public Map<String, InductionVariable> transfer(CFGCallNode n,
                Map<String, InductionVariable> in) {
            return in;
        }

        @Override
        public Map<String, InductionVariable> transferTrue(CFGIfNode n,
                Map<String, InductionVariable> in) {
            return in;
        }

        @Override
        public Map<String, InductionVariable> transferFalse(CFGIfNode n,
                Map<String, InductionVariable> in) {
            return in;
        }

        @Override
        public Map<String, InductionVariable> transfer(CFGMemAssignNode n,
                Map<String, InductionVariable> in) {
            return in;
        }

        @Override
        public Map<String, InductionVariable> transfer(CFGStartNode n,
                Map<String, InductionVariable> in) {
            return in;
        }

        @Override
        public Map<String, InductionVariable> transfer(CFGVarAssignNode n,
                Map<String, InductionVariable> in) {
            String variable = n.variable;
            if (n.value instanceof IRBinOp) {
                IRBinOp expr = (IRBinOp) n.value;
                
                var pattern = BiPatternBuilder
                    .left()
                    .instOf(IRConst.class)
                    .right()
                    .instOf(IRTemp.class)
                    .finish()
                    .enableCommutes();

                if (expr.opType() == OpType.MUL && pattern.matches(new Object[]{expr.left(), expr.right()})) {
                    long constant = pattern.leftObj().value();
                    String referenced = pattern.rightObj().name();
                    if (in.containsKey(referenced) && in.get(referenced) instanceof DefinedInductionVariable) {
                        InductionVariable newIV = new DefinedInductionVariable(
                                in.get(referenced).basicRef(),
                                in.get(referenced).getFactor()*constant,
                                in.get(referenced).getOffset()*constant);
                        in.put(variable, newIV);
                    }
                } else if("Replace" == variable) {
                    // Place more binop patterns here.
                } else {
                    // Does not match any binop patterns
                    in.put(variable, NotInductionVariable.INSTANCE);
                }
                        
            } else {
                in.put(variable, NotInductionVariable.INSTANCE);
            }
            return in;
        }

        @Override
        public Map<String, InductionVariable> transfer(CFGSelfLoopNode n,
                Map<String, InductionVariable> in) {
            return in;
        }

        
        
    }
}
