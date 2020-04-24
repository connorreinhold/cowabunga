package cyr7.x86.tiler;

import java.util.List;
import java.util.Optional;

import cyr7.ir.nodes.IRCallStmt;
import cyr7.x86.asm.ASMAddrExpr.ScaleValues;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMArgFactory;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMReg.Size;

public class CallInstructionGenerator {

    protected static final ASMArgFactory arg = ASMArgFactory.instance;

    public static TilerData generate(
            IRCallStmt n,
            final int cost,
            ASMArg targetFunction,
            List<String> collectors,
            List<ASMArg> arguments,
            List<ASMLine> insn,
            boolean stack16ByteAligned) {
        ASMLineFactory make = new ASMLineFactory(n);
        int lastRegisterArg;
        /*
         * If the callee function has more than two return values, then we store
         * memory address to a "saved stack space" to hold return values onto
         * the rdi register.
         */
        final int numReturnValues = collectors.size();
        if (numReturnValues > 2) {
            lastRegisterArg = Math.min(5, arguments.size());

            long size = (numReturnValues - 2) * 8;
            insn.add(make.Mov(ASMReg.RDI, arg.constant(size)));
            insn.add(make.Sub(ASMReg.RSP, ASMReg.RDI));
            insn.add(make.Mov(ASMReg.RDI, ASMReg.RSP)); // the first argument

            for (int i = 0; i < lastRegisterArg; i++) {
                ASMArg arg = arguments.get(i);
                switch (i) {
                case 0:
                    insn.add(make.Mov(ASMReg.RSI, arg));
                    break;
                case 1:
                    insn.add(make.Mov(ASMReg.RDX, arg));
                    break;
                case 2:
                    insn.add(make.Mov(ASMReg.RCX, arg));
                    break;
                case 3:
                    insn.add(make.Mov(ASMReg.R8, arg));
                    break;
                case 4:
                    insn.add(make.Mov(ASMReg.R9, arg));
                    break;
                default:
                    throw new RuntimeException("Unexpected Error with "
                            + "index");
                }
            }
        } else {
            lastRegisterArg = Math.min(6, arguments.size());
            for (int i = 0; i < lastRegisterArg; i++) {
                ASMArg arg = arguments.get(i);
                switch (i) {
                case 0:
                    insn.add(make.Mov(ASMReg.RDI, arg));
                    break;
                case 1:
                    insn.add(make.Mov(ASMReg.RSI, arg));
                    break;
                case 2:
                    insn.add(make.Mov(ASMReg.RDX, arg));
                    break;
                case 3:
                    insn.add(make.Mov(ASMReg.RCX, arg));
                    break;
                case 4:
                    insn.add(make.Mov(ASMReg.R8, arg));
                    break;
                case 5:
                    insn.add(make.Mov(ASMReg.R9, arg));
                    break;
                default:
                    throw new RuntimeException(
                        "Unexpected Error with index");
                }
            }
        }

        // align the stack depending on the number of pushes to the stack.
        final boolean stackNeedsAdjustment =
            (Math.max(arguments.size() - lastRegisterArg, 0) % 2 == 0)
                == stack16ByteAligned;
        if (stackNeedsAdjustment) {
            insn.add(make.Sub(ASMReg.RSP, arg.constant(8)));
        }

        // push additional arguments onto the stack
        for (int i = arguments.size() - 1; i >= lastRegisterArg; i--) {
            insn.add(make.Push(arguments.get(i)));
        }

        // Perform the call
        if (true)
        {
            insn.add(make.Mov(arg.temp("__rax_temp1", Size.QWORD), ASMReg.RAX));
            insn.add(make.Mov(arg.temp("__rdx_temp1", Size.QWORD), ASMReg.RDX));
            insn.add(make.Mov(ASMReg.RAX, ASMReg.RSP));
            insn.add(make.CQO());
            insn.add(make.Mov(ASMReg.R15, arg.constant(16)));
            insn.add(make.Div(ASMReg.R15));
            insn.add(make.Cmp(ASMReg.RDX, arg.constant(0))); // remainder cmp 0
            insn.add(make.JumpNE(arg.label("_xi_out_of_bounds")));

            insn.add(make.Mov(ASMReg.RAX, arg.temp("__rax_temp1", Size.QWORD)));
            insn.add(make.Mov(ASMReg.RDX, arg.temp("__rdx_temp1", Size.QWORD)));

        }
        insn.add(make.Call(targetFunction));

        // Add back the stack space we used for arguments 7 and beyond
        if (arguments.size() > lastRegisterArg) {
            insn.add(make.Add(ASMReg.RSP,
                    arg.constant(8 * (arguments.size() - lastRegisterArg))));
        }

        // adjust the stack back
        if (stackNeedsAdjustment) {
            insn.add(make.Add(ASMReg.RSP, arg.constant(8)));
        }

        // Move the temps from the return registers into the collectors
        for (int i = 0; i < n.collectors()
                             .size(); i++) {
            String tempName = n.collectors()
                               .get(i);
            if (tempName.equals("_")) {
                continue;
            }

            ASMArg target = arg.temp(tempName, Size.QWORD);
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

        if (numReturnValues > 2) {
            // we allocated some space on the stack for additional return values
            // that we now need to deallocate
            long size = (numReturnValues - 2) * 8;
            insn.add(make.Add(ASMReg.RSP, arg.constant(size)));
        }

        return new TilerData(cost, insn, Optional.empty());
    }

}
