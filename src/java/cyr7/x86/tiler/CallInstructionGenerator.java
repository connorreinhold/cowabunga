package cyr7.x86.tiler;

import java.util.List;
import java.util.Optional;

import cyr7.cli.CLI;
import cyr7.ir.nodes.IRCallStmt;
import cyr7.x86.ASMConstants;
import cyr7.x86.asm.ASMAddrExpr.ScaleValues;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMArgFactory;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMRegSize;

public class CallInstructionGenerator {

    private static final ASMArgFactory arg = ASMArgFactory.instance;

    private final IRCallStmt n;
    private final int cost;
    private final ASMArg targetFunction;
    private final List<String> collectors;
    private final List<ASMArg> arguments;
    private final List<ASMLine> insn;
    private final boolean stack16ByteAligned;

    private final ASMLineFactory make;

    public CallInstructionGenerator(
        IRCallStmt n,
        int cost,
        ASMArg targetFunction,
        List<String> collectors,
        List<ASMArg> arguments,
        List<ASMLine> insn,
        boolean stack16ByteAligned) {

        this.n = n;
        this.cost = cost;
        this.targetFunction = targetFunction;
        this.collectors = collectors;
        this.arguments = arguments;
        this.insn = insn;
        this.stack16ByteAligned = stack16ByteAligned;

        this.make = new ASMLineFactory(n);
    }

    public TilerData generate() {
        precallMoveArgumentsIntoRegisters();

        precallEnsure16ByteAlignment();

        // push additional arguments onto the stack
        precallMoveArgumentsOntoStack();

        if (CLI.assemblyLevelAssertionsEnabled) {
            precallAddAssemblyLevelStackAlignmentCheck();
        }

        insn.add(make.Call(targetFunction));

        // Add back the stack space we used for arguments 7 and beyond
        postcallRemoveArgumentsOnStack();

        // adjust the stack back
        postcallEnsure16ByteAlignment();

        // Move the temps from the return registers into the collectors
        postcallMoveReturnsIntoCollectors();

        // we may have allocated some space on the stack for additional
        // return values that we now need to deallocate
        postcallDeallocateSpaceForAdditionalReturnArguments();

        return new TilerData(cost, insn, Optional.empty());
    }

    private void precallMoveArgumentsIntoRegisters() {
        int argRegOffset;
        int numReturnValues = collectors.size();
        if (numReturnValues > 2) {
            long size = (numReturnValues - 2) * 8;

            insn.add(make.Mov(ASMReg.RDI, arg.constant(size)));
            insn.add(make.Sub(ASMReg.RSP, ASMReg.RDI));
            insn.add(make.Mov(ASMReg.RDI, ASMReg.RSP)); // the first argument

            argRegOffset = 1;
        } else {
            argRegOffset = 0;
        }

        int argsToMove = Math.min (
                arguments.size(),
                ASMConstants.ARGUMENT_REGISTERS.length - argRegOffset);
        for (int i = 0; i < argsToMove; i++) {
            ASMArg arg = arguments.get(i);
            insn.add(make.Mov(
                ASMConstants.ARGUMENT_REGISTERS[i + argRegOffset],
                arg));
        }
    }

    private void postcallDeallocateSpaceForAdditionalReturnArguments() {
        if (collectors.size() > 2) {
            // we allocated some space on the stack for additional return values
            // that we now need to deallocate
            long size = (collectors.size() - 2) * 8;
            insn.add(make.Add(ASMReg.RSP, arg.constant(size)));
        }
    }

    private void precallMoveArgumentsOntoStack() {
        // push additional arguments onto the stack
        int lastRegisterArg = lastRegisterArg();

        for (int i = arguments.size() - 1; i >= lastRegisterArg; i--) {
            insn.add(make.Push(arguments.get(i)));
        }
    }

    private void postcallRemoveArgumentsOnStack() {
        int lastRegisterArg = lastRegisterArg();
        if (arguments.size() > lastRegisterArg) {
            insn.add(make.Add(ASMReg.RSP,
                arg.constant(8 * (arguments.size() - lastRegisterArg))));
        }
    }

    private void precallEnsure16ByteAlignment() {
        if (stackNeedsAdjustment()) {
            insn.add(make.Sub(ASMReg.RSP, arg.constant(8)));
        }
    }

    private void postcallEnsure16ByteAlignment() {
        if (stackNeedsAdjustment()) {
            insn.add(make.Add(ASMReg.RSP, arg.constant(8)));
        }
    }

    private void postcallMoveReturnsIntoCollectors() {
        for (int i = 0; i < n.collectors().size(); i++) {
            String tempName = n.collectors().get(i);
            if (tempName.equals("_")) {
                continue;
            }

            ASMArg target = arg.temp(tempName, ASMRegSize.QWORD);
            switch (i) {
                case 0:
                    insn.add(make.Mov(target, ASMReg.RAX));
                    break;
                case 1:
                    insn.add(make.Mov(target, ASMReg.RDX));
                    break;
                default:
                    int offset = 8 * (i - 2);
                    var addr = arg.addr(Optional.of(ASMReg.RSP),
                        ScaleValues.ONE,
                        Optional.empty(),
                        offset);
                    var mem = arg.mem(addr);
                    insn.add(make.Mov(target, mem));
                    break;
            }
        }
    }

    private void precallAddAssemblyLevelStackAlignmentCheck() {
        insn.add(make.Mov(arg.temp("__rax_temp1", ASMRegSize.QWORD),
            ASMReg.RAX));
        insn.add(make.Mov(arg.temp("__rdx_temp1", ASMRegSize.QWORD),
            ASMReg.RDX));
        insn.add(make.Mov(ASMReg.RAX, ASMReg.RSP));
        insn.add(make.CQO());
        insn.add(make.Mov(ASMReg.R15, arg.constant(16)));
        insn.add(make.Div(ASMReg.R15));
        insn.add(make.Cmp(ASMReg.RDX, arg.constant(0))); // remainder cmp 0
        insn.add(make.JumpNE(arg.label("_xi_out_of_bounds")));
        insn.add(make.Mov(ASMReg.RAX, arg.temp("__rax_temp1",
            ASMRegSize.QWORD)));
        insn.add(make.Mov(ASMReg.RDX, arg.temp("__rdx_temp1",
            ASMRegSize.QWORD)));
    }

    private boolean stackNeedsAdjustment() {
        int numValuesPushed = Math.max(collectors.size() - 2, 0);
        numValuesPushed += Math.max(arguments.size() - lastRegisterArg(), 0);
        return (numValuesPushed % 2 == 0) != stack16ByteAligned;
    }

    private int lastRegisterArg() {
        int lastRegisterArg;
        if (collectors.size() > 2) {
            lastRegisterArg = Math.min(5, arguments.size());
        } else {
            lastRegisterArg = Math.min(6, arguments.size());
        }
        return lastRegisterArg;
    }

}
