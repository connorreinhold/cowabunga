package cyr7.cfg.ir.dfa.loops;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cyr7.cfg.ir.dfa.ForwardDataflowAnalysis;
import cyr7.cfg.ir.dfa.ForwardTransferFunction;
import cyr7.cfg.ir.dfa.loops.inductionvars.InductionVariable;
import cyr7.cfg.ir.dfa.loops.inductionvars.DefinedInductionVariable;
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
import cyr7.ir.nodes.IRTemp;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.x86.pattern.BiPatternBuilder;
/**
 * This class is used for determining all possible induction variables and their relationships 
 * to basic induction variables

    Note: Undefined IVs are not contained in a node's map.
 */
public class DerivedInductionVariableAnalysis implements ForwardDataflowAnalysis<Map<String, InductionVariable>> {
    private final Map<String, InductionVariable> basicIVMap;
    private final CFGNode start;
    
    public DerivedInductionVariableAnalysis(Set<String> basicVars, CFGNode start) {
        Map<String, InductionVariable> temp = new HashMap<>();
        for(String variable: basicVars) {
            temp.put(variable, new DefinedInductionVariable(variable, 1, 0));
        }
        this.basicIVMap = Collections.unmodifiableMap(temp);
        this.start = start;
        
    }

    @Override
    public Map<String, InductionVariable> topValue() {
        return new HashMap<>();
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
        TransferFunction.INSTANCE.updateTransferFunction(basicIVMap, start);
        return TransferFunction.INSTANCE;
    }
    
    private enum TransferFunction implements ForwardTransferFunction<Map<String, InductionVariable>> {
        INSTANCE;

        private CFGNode start;
        private Map<String, InductionVariable> basicIVMap;
        
        private void updateTransferFunction(Map<String, InductionVariable> basicIVMap, CFGNode start) {
            this.start = start;
            this.basicIVMap = basicIVMap;
        }
        
        @Override
        public Map<String, InductionVariable> transfer(CFGCallNode n,
                Map<String, InductionVariable> in) {
            if (n == start) {
                in = basicIVMap;
            }
            return in;
        }

        @Override
        public Map<String, InductionVariable> transferTrue(CFGIfNode n,
                Map<String, InductionVariable> in) {
            if (n == start) {
                in = basicIVMap;
            }
            return in;
        }

        @Override
        public Map<String, InductionVariable> transferFalse(CFGIfNode n,
                Map<String, InductionVariable> in) {
            if (n == start) {
                in = basicIVMap;
            }
            return in;
        }

        @Override
        public Map<String, InductionVariable> transfer(CFGMemAssignNode n,
                Map<String, InductionVariable> in) {
            if (n == start) {
                in = basicIVMap;
            }
            return in;
        }

        @Override
        public Map<String, InductionVariable> transfer(CFGStartNode n,
                Map<String, InductionVariable> in) {
            if (n == start) {
                in = basicIVMap;
            }
            return in;
        }

        @Override
        public Map<String, InductionVariable> transfer(CFGVarAssignNode n,
                Map<String, InductionVariable> in) {
            if (n == start) {
                in = basicIVMap;
            }
            String variable = n.variable;
            Map<String, InductionVariable> updatedMap = new HashMap<String, InductionVariable>(in);
            if (n.value instanceof IRBinOp) {
                IRBinOp expr = (IRBinOp) n.value;
                
                var constTempCommute = BiPatternBuilder
                    .left()
                    .instOf(IRConst.class)
                    .right()
                    .instOf(IRTemp.class)
                    .and(temp -> updatedMap.containsKey(temp.name()) 
                            && updatedMap.get(temp.name()) instanceof DefinedInductionVariable)
                    .finish()
                    .enableCommutes();
                
                var tempMinusConst = BiPatternBuilder
                        .left()
                        .instOf(IRTemp.class)
                        .and(temp -> updatedMap.containsKey(temp.name()) 
                                && updatedMap.get(temp.name()) instanceof DefinedInductionVariable)
                        .right()
                        .instOf(IRConst.class)
                        .finish();
                InductionVariable newIV = null;
                if (expr.opType() == OpType.MUL && constTempCommute.matches(new Object[]{expr.left(), expr.right()})) {
                    // a = i * c
                    long constant = constTempCommute.leftObj().value();
                    String referenced = constTempCommute.rightObj().name();
                    newIV = new DefinedInductionVariable(
                        updatedMap.get(referenced).basicRef(),
                        updatedMap.get(referenced).getFactor()*constant,
                        updatedMap.get(referenced).getOffset()*constant);
                } else if(expr.opType() == OpType.ADD && constTempCommute.matches(new Object[]{expr.left(), expr.right()})) {
                     // a = i + c
                    long constant = constTempCommute.leftObj().value();
                    String referenced = constTempCommute.rightObj().name();
                    newIV = new DefinedInductionVariable(
                        updatedMap.get(referenced).basicRef(),
                        updatedMap.get(referenced).getFactor(),
                        updatedMap.get(referenced).getOffset()+constant);
                } else if (expr.opType() == OpType.SUB && tempMinusConst.matches(new Object[]{expr.left(), expr.right()})) {
                    // a = i - c
                    long constant = tempMinusConst.rightObj().value();
                    String referenced = tempMinusConst.leftObj().name();
                    newIV = new DefinedInductionVariable(
                        updatedMap.get(referenced).basicRef(),
                        updatedMap.get(referenced).getFactor(),
                        updatedMap.get(referenced).getOffset()-constant);
                }
                if (newIV == null) {
                    updatedMap.put(variable, NotInductionVariable.INSTANCE);
                } else {
                    updatedMap.put(variable, newIV);
                }
                        
            } else {
                updatedMap.put(variable, NotInductionVariable.INSTANCE);
            }
            return updatedMap;
        }

        @Override
        public Map<String, InductionVariable> transfer(CFGSelfLoopNode n,
                Map<String, InductionVariable> in) {
            if (n == start) {
                in = basicIVMap;
            }
            return in;
        }

        
        
    }
}
