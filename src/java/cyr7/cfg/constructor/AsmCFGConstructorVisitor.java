package cyr7.cfg.constructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import cyr7.cfg.nodes.asm.AsmCFGIfNode;
import cyr7.cfg.nodes.asm.AsmCFGIfNode.ASMCompareType;
import cyr7.cfg.nodes.asm.AsmCFGNode;
import cyr7.cfg.nodes.asm.AsmCFGStartNode;
import cyr7.cfg.nodes.asm.AsmCFGStubNode;
import cyr7.cfg.nodes.ir.CFGIfNode;
import cyr7.cfg.nodes.ir.CFGMemAssignNode;
import cyr7.cfg.nodes.ir.CFGNode;
import cyr7.cfg.nodes.ir.CFGStubNode;
import cyr7.cfg.nodes.ir.CFGVarAssignNode;
import cyr7.ir.nodes.IRCJump;
import cyr7.ir.nodes.IRJump;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRMove;
import cyr7.ir.nodes.IRName;
import cyr7.ir.nodes.IRTemp;
import cyr7.x86.asm.ASMAddrExpr;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMInstrType;
import cyr7.x86.asm.ASMLabel;
import cyr7.x86.asm.ASMLabelArg;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMMemArg;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.visitor.AbstractASMVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;
import polyglot.util.Pair;

public class AsmCFGConstructorVisitor {

//    private final Map<String, AsmCFGNode> labelToCFG;
//    private final Queue<Pair<AsmCFGStubNode, String>> jumpTargetFromCFG;
//
//    /**
//     * Null until convertToCFG is called, where it is first defined in the
//     * method's first for-loop body.
//     */
//    private AsmCFGNode successor;
//
//    /**
//     * This boolean is for testing purposes, enforcing that IRSeq is only found
//     * at the top-level of the IRTree.
//     */
//    private boolean hasEnteredIRSeq;
//    private final List<ASMLine> instructions;
//
//    protected AsmCFGConstructorVisitor(List<ASMLine> instructions) {
//        this.labelToCFG = new HashMap<>();
//        this.jumpTargetFromCFG = new LinkedList<>();
//        this.hasEnteredIRSeq = false;
//        this.instructions = this.preprocessASM(instructions);
//    }
//
//    private AsmCFGStubNode createStubNode() {
//        return new AsmCFGStubNode();
//    }
//
//    private AsmCFGNodeFactory makeFactory(Location location) {
//        return new AsmCFGNodeFactory(location);
//    }
//
//
//    private class ASMIfLine implements ASMLine {
//
//        public final ASMInstr cmp;
//        public final ASMInstr jump;
//
//        public ASMIfLine(ASMInstr cmp, ASMInstr jump) {
//            this.cmp = cmp;
//            this.jump = jump;
//        }
//
//        @Override
//        public String getIntelAssembly(boolean indented, boolean withComments) {
//            throw new UnsupportedOperationException("No need to get literal assmebly");
//        }
//
//        @Override
//        public <R> R accept(AbstractASMVisitor<R> visitor) {
//            throw new UnsupportedOperationException("Cannot visit if assmebly instruction");
//        }
//    }
//
//    private ArrayList<ASMLine> preprocessASM(List<ASMLine> instructions) {
//        var processedInstructions = new ArrayList<ASMLine>();
//        instructions = new ArrayList<>(instructions);
//        for (int i = 0; i < instructions.size(); i++) {
//            var instr = instructions.get(i);
//            if (instr instanceof ASMInstr
//                    && ((ASMInstr)instr).type == ASMInstrType.CMPQ) {
//                ASMInstr cmp = (ASMInstr)instr;
//                if (i + 1 < instructions.size()
//                        && this.isAConditionalJump(instructions.get(i + 1))) {
//                    ASMInstr jump = (ASMInstr)instructions.get(i + 1);
//                    processedInstructions.add(new ASMIfLine(cmp, jump));
//                    i++;
//                } else {
//                    processedInstructions.add(cmp);
//                }
//            } else {
//                processedInstructions.add(instr);
//            }
//        }
//        return processedInstructions;
//    }
//
//    private boolean isAConditionalJump(ASMLine line) {
//        if (line instanceof ASMInstr) {
//            ASMInstr instr = (ASMInstr)line;
//            boolean isAJump = instr.type == ASMInstrType.JE;
//            isAJump |= instr.type == ASMInstrType.JNE;
//            isAJump |= instr.type == ASMInstrType.JL;
//            isAJump |= instr.type == ASMInstrType.JLE;
//            isAJump |= instr.type == ASMInstrType.JG;
//            isAJump |= instr.type == ASMInstrType.JGE;
//            return isAJump;
//        }
//        return false;
//    }
//
//
//    public AsmCFGNode convertToCFG() {
//        if (this.hasEnteredIRSeq) {
//            throw new UnsupportedOperationException(
//                    "Cannot enter the IRSeq visitor twice");
//        } else {
//            this.hasEnteredIRSeq = true;
//        }
//
//        for (int i = instructions.size() - 1; i >= 0; i--) {
//            var stmt = instructions.get(i);
//            successor = this.accept(stmt);
//        }
//
//        successor = new AsmCFGStartNode(new Location(-1, -1), successor);
//
//        while (!this.jumpTargetFromCFG.isEmpty()) {
//            var nextPair = this.jumpTargetFromCFG.poll();
//            AsmCFGStubNode stub = nextPair.part1();
//            String target = nextPair.part2();
//
//            if (this.labelToCFG.containsKey(target)) {
//                AsmCFGNode targetNode = this.labelToCFG.get(target);
//                for (AsmCFGNode incoming: stub.in()) {
//                    // Target node may be itself, which
//                    // indicates an empty loop coming from the parent node.
//                    if (targetNode == stub) {
//                        incoming.convertFromStub(stub, incoming);
//                    } else {
//                        incoming.convertFromStub(stub, targetNode);
//                    }
//                }
//
//            } else {
//                throw new UnsupportedOperationException(
//                        "Target label was never found in the program.");
//            }
//        }
//        return successor;
//    }
//
//
//    @Override
//    public CFGNode visit(IRCJump n) {
//        assert !n.hasFalseLabel();  // IR should be lowered, meaning false
//                                    // branches are fall-throughs.
//        String trueBranch = n.trueLabel();
//        if (this.labelToCFG.containsKey(trueBranch)) {
//            return new CFGIfNode(n.location(),
//                    this.labelToCFG.get(trueBranch),
//                    successor, n.cond());
//        } else {
//            // Create stub node, and connect target to the stub node.
//            CFGStubNode stub = this.createStubNode();
//            CFGIfNode ifNode = new CFGIfNode(n.location(),
//                    stub, successor, n.cond());
//            this.jumpTargetFromCFG.add(new Pair<>(stub, trueBranch));
//            return ifNode;
//        }
//    }
//
//
//    @Override
//    public CFGNode visit(IRJump n) {
//        if (n.target() instanceof IRName) {
//            String target = ((IRName) n.target()).name();
//            if (this.labelToCFG.containsKey(target)) {
//                // Make successor the target node.
//                return this.labelToCFG.get(target);
//            } else {
//                // Create a stub node for later computation
//                CFGStubNode stub = this.createStubNode();
//                this.jumpTargetFromCFG.add(new Pair<>(stub, target));
//                return stub;
//            }
//        } else {
//            throw new UnsupportedOperationException(
//                    "IRJump contains a non-name target.");
//        }
//    }
//
//
//    @Override
//    public CFGNode visit(IRLabel n) {
//        this.labelToCFG.put(n.name(), successor);
//        return successor;
//    }
//
//
//    @Override
//    public CFGNode visit(IRMove n) {
//        if (n.target() instanceof IRTemp) {
//            IRTemp temp = (IRTemp) n.target();
//            return new CFGVarAssignNode(n.location(),
//                        temp.name(), n.source(), successor);
//        } else {
//            return new CFGMemAssignNode(n.location(),
//                        n.target(), n.source(), successor);
//        }
//    }
//
//
//    private Location getLocation(ASMInstr instruction) {
//        return instruction.source.map(ir -> ir.location())
//                .orElse(new Location(-1, -1));
//    }
//
//    private AsmCFGNode createReturn(ASMInstr instruction) {
//        var make = this.makeFactory(this.getLocation(instruction));
//        return make.Return();
//    }
//
//    private AsmCFGNode createCall(ASMInstr instruction) {
//        var make = this.makeFactory(this.getLocation(instruction));
//        String target = ((ASMLabelArg)instruction.args.get(0)).label;
//        return make.Call(target, successor);
//    }
//
//
//    private AsmCFGNode accept(ASMLine line) {
//        if (line instanceof ASMInstr) {
//            return this.visitInstruction((ASMInstr)line);
//        } else if (line instanceof ASMIfLine) {
//            return this.visitIfLine((ASMIfLine)line);
//        } else if (line instanceof ASMLabel) {
//            return this.visitLabel((ASMLabel)line);
//        }
//        throw new UnsupportedOperationException("Found a directive");
//    }
//
//    private AsmCFGNode visitIfLine(ASMIfLine line) {
//
//        ASMCompareType type;
//        switch (line.jump.type) {
//        case JE:
//            type = ASMCompareType.EQ;
//            break;
//        case JG:
//            type = ASMCompareType.GT;
//            break;
//        case JGE:
//            type = ASMCompareType.GTE;
//            break;
//        case JL:
//            type = ASMCompareType.LT;
//            break;
//        case JLE:
//            type = ASMCompareType.LTE;
//            break;
//        case JNE:
//            type = ASMCompareType.NEQ;
//            break;
//        default:
//            throw new UnsupportedOperationException("Not a conditional jump");
//        }
//
//        String trueBranch = ((ASMLabelArg)line.jump.args.get(0)).label;
//        ASMArg leftOperand = (line.jump.args.get(0));
//        ASMArg rightOperand = (line.jump.args.get(1));
//
//        if (this.labelToCFG.containsKey(trueBranch)) {
//            return new AsmCFGIfNode(this.getLocation(line.cmp),
//                    this.labelToCFG.get(trueBranch),
//                    successor, type, leftOperand, rightOperand);
//        } else {
//            // Create stub node, and connect target to the stub node.
//            AsmCFGStubNode stub = this.createStubNode();
//            AsmCFGIfNode ifNode = new AsmCFGIfNode(this.getLocation(line.cmp),
//                    stub, successor, type, leftOperand, rightOperand);
//            this.jumpTargetFromCFG.add(new Pair<>(stub, trueBranch));
//            return ifNode;
//        }
//    }
//
//
//    private AsmCFGNode createJump(ASMInstr jumpInstr) {
//        String target = ((ASMLabelArg)jumpInstr.args.get(0)).label;
//        if (this.labelToCFG.containsKey(target)) {
//            // Make successor the target node.
//            return this.labelToCFG.get(target);
//        } else {
//            // Create a stub node for later computation
//            AsmCFGStubNode stub = this.createStubNode();
//            this.jumpTargetFromCFG.add(new Pair<>(stub, target));
//            return stub;
//        }
//    }
//
//
//    private AsmCFGNode createOperation(ASMInstr instr) {
//
//        ASMArg target = instr.args.get(0);
//        ASMArg source = instr.args.get(1);
//
//        if (target instanceof ASMMemArg) {
//            ASMAddrExpr address = ((ASMMemArg)target).address;
//            return new AsmCFGMemAssignNode(this.getLocation(instr),
//                    address, source, successor);
//        } else if (target instanceof ASMTempArg) {
//            var temp = (ASMTempArg)target;
//            String variable = temp.name;
//            ASMReg.Size size = temp.size;
//            return new AsmCFGVarAssignNode(this.getLocation(instr),
//                    variable, source, size, successor);
//        } else if (target instanceof ASMReg) {
//            var reg = (ASMReg)target;
//            return new AsmCFGRegAssignNode(this.getLocation(instr), reg, source, successor);
//        }
//    }
//
//    private AsmCFGNode createMove(ASMInstr instr) {
//        ASMArg target = instr.args.get(0);
//        ASMArg source = instr.args.get(1);
//        var args = new AsmCFGOperationExpr(ASMOperation.MOVE, List.of(source));
//
//        if (target instanceof ASMMemArg) {
//            ASMAddrExpr address = ((ASMMemArg)target).address;
//            return new AsmCFGMemAssignNode(this.getLocation(instr),
//                    address, args, successor);
//        } else if (target instanceof ASMTempArg) {
//            var temp = (ASMTempArg)target;
//            String variable = temp.name;
//            ASMReg.Size size = temp.size;
//            return new AsmCFGVarAssignNode(this.getLocation(instr),
//                    variable, args, size, successor);
//        } else if (target instanceof ASMReg) {
//            var reg = (ASMReg)target;
//            return new AsmCFGRegAssignNode(this.getLocation(instr),
//                    reg, args, successor);
//        }
//        throw new UnsupportedOperationException("Invalid target assembly argument for move.");
//    }
//
//
//    private AsmCFGNode visitInstruction(ASMInstr i) {
//        switch (i.type) {
//        case RETQ:
//            return this.createReturn(i);
//        case CALLQ:
//            return this.createCall(i);
//        case CQO:
//            break;
//
//        case JE:
//        case JG:
//        case JGE:
//        case JL:
//        case JLE:
//        case JNE:
//            throw new UnsupportedOperationException("Not supported as a individual line without compare instruction");
//
//        case JMP:
//            return this.createJump(i);
//
//
//        case ADDQ:
//            break;
//        case ANDQ:
//            break;
//        case CMPQ:
//            break;
//        case IDIVQ:
//            break;
//        case IMULQ:
//            break;
//        case LEAQ:
//            break;
//        case ORQ:
//            break;
//        case SARQ:
//            break;
//        case SETG:
//            break;
//        case SETGE:
//            break;
//        case SETL:
//            break;
//        case SETLE:
//            break;
//        case SETNE:
//            break;
//        case SETZ:
//            break;
//        case SHLQ:
//            break;
//        case SHRQ:
//            break;
//        case SUBQ:
//            break;
//        case XORQ:
//            break;
//
//
//        case MOV:
//        case MOVABSQ:
//        case MOVQ:
//        case MOVZX:
//            return this.createMove(i);
//
//        case POPQ:
//            break;
//        case PUSHQ:
//            break;
//        default:
//            break;
//        }
//        return null;
//    }
//
//    public AsmCFGNode visitLabel(ASMLabel lbl) {
//        this.labelToCFG.put(lbl.label, successor);
//        return successor;
//    }

}