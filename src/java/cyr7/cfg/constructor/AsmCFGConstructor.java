package cyr7.cfg.constructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import cyr7.cfg.nodes.asm.AsmCFGIfNode;
import cyr7.cfg.nodes.asm.AsmCFGNode;
import cyr7.cfg.nodes.asm.AsmCFGOpNode;
import cyr7.cfg.nodes.asm.AsmCFGReturnNode;
import cyr7.cfg.nodes.asm.AsmCFGSourceNode;
import cyr7.cfg.nodes.asm.AsmCFGStartNode;
import cyr7.cfg.nodes.asm.AsmCFGStubNode;
import cyr7.cfg.nodes.ir.CFGNode;
import cyr7.util.Pair;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMLabel;
import cyr7.x86.asm.ASMLabelArg;
import cyr7.x86.asm.ASMLine;

public class AsmCFGConstructor {

    private final Map<String, AsmCFGNode> labelToCFG;
    private final Queue<Pair<AsmCFGStubNode, String>> jumpTargetFromCFG;

    /**
     * Null until convertToCFG is called, where it is first defined in the
     * method's first for-loop body.
     */
    private AsmCFGNode successor;

    private final List<ASMLine> lines;

    public AsmCFGConstructor(List<ASMLine> lines) {
        this.labelToCFG = new HashMap<>();
        this.jumpTargetFromCFG = new LinkedList<>();
        this.lines = new ArrayList<>(lines);
    }

    public AsmCFGStartNode constructAsmCFG() {
        for (int i = lines.size() - 1; i >= 0; i--) {
            var line = lines.get(i);
            if (line instanceof ASMInstr) {
                successor = this.accept((ASMInstr) line, i);
            } else if (line instanceof ASMLabel) {
                this.accept((ASMLabel) line);
            }
        }

        while (!this.jumpTargetFromCFG.isEmpty()) {
            var nextPair = this.jumpTargetFromCFG.poll();
            AsmCFGStubNode stub = nextPair.left;
            String target = nextPair.right;

            if (this.labelToCFG.containsKey(target)) {
                AsmCFGNode targetNode = this.labelToCFG.get(target);
                for (AsmCFGNode incoming : stub.inNodes()) {
                    // Target node may be itself, which
                    // indicates an empty loop coming from the parent node.
                    if (targetNode == stub) {
                        incoming.convertFromStub(stub, incoming);
                    } else {
                        incoming.convertFromStub(stub, targetNode);
                    }
                }

            } else {
                throw new UnsupportedOperationException(
                    "Target label was never found in the program.");
            }
        }

        return new AsmCFGStartNode(successor);
    }

    public void accept(ASMLabel label) {
        this.labelToCFG.put(label.label, successor);
    }

    public AsmCFGSourceNode accept(ASMInstr instr, int index) {
        switch (instr.type) {
            // operations
            case ADDQ:
            case SUBQ:
            case IMULQ:
            case IDIVQ:
            case LEAQ:
            case ANDQ:
            case ORQ:
            case XORQ:
            case SHLQ:
            case SHRQ:
            case SARQ:
            case MOVQ:
            case MOV:
            case PUSHQ:
            case POPQ:
            case SETZ:
            case SETNE:
            case SETLE:
            case SETL:
            case SETG:
            case SETGE:
            case CQO:
            case MOVABSQ:
            case MOVZX:
            case CMPQ:
            case CALLQ:
                return new AsmCFGOpNode(index, instr, successor);
            // control flow
            case JE:
            case JNE:
            case JLE:
            case JGE:
            case JL:
            case JG:
                String trueLabel = ((ASMLabelArg) instr.args.get(0)).label;
                if (this.labelToCFG.containsKey(trueLabel)) {
                    AsmCFGNode trueBranch = this.labelToCFG.get(trueLabel);
                    return new AsmCFGIfNode(index, instr, trueBranch, successor);
                } else {
                    // Create stub node, and connect target to the stub node.
                    AsmCFGStubNode stub = new AsmCFGStubNode();
                    AsmCFGIfNode ifNode = new AsmCFGIfNode(index, instr, stub, successor);
                    this.jumpTargetFromCFG.add(new Pair<>(stub, trueLabel));
                    return ifNode;
                }
            case JMP:
                String target = ((ASMLabelArg) instr.args.get(0)).label;
                if (this.labelToCFG.containsKey(target)) {
                    // Make successor the target node.
                    return new AsmCFGOpNode(index, instr, this.labelToCFG.get(target));
                } else {
                    // Create a stub node for later computation
                    AsmCFGStubNode stub = new AsmCFGStubNode(index, instr);
                    this.jumpTargetFromCFG.add(new Pair<>(stub, target));
                    return stub;
                }
            // return
            case RETQ:
                return new AsmCFGReturnNode(index, instr);
            default:
                throw new AssertionError();
        }
    }

}