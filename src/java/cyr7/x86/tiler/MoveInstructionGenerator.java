package cyr7.x86.tiler;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import cyr7.ir.IdGenerator;
import cyr7.ir.interpret.Configuration;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRMove;
import cyr7.ir.nodes.IRTemp;
import cyr7.x86.ASMConstants;
import cyr7.x86.asm.ASMAddrExpr.ScaleValues;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMArgFactory;
import cyr7.x86.asm.ASMConstArg;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMMemArg;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.ASMRegSize;

public class MoveInstructionGenerator {

    private static final ASMArgFactory arg = ASMArgFactory.instance;

    private static boolean isAnArg(IRExpr n) {
        return (n instanceof IRTemp)
            && ((IRTemp) n).name().startsWith(Configuration.ABSTRACT_ARG_PREFIX);
    }

    private static int getArgIndex(IRExpr n) {
        String index = ((IRTemp) n).name()
            .substring(Configuration.ABSTRACT_ARG_PREFIX.length());
        return Integer.parseInt(index);
    }

    private static boolean isAReturn(IRExpr n) {
        return (n instanceof IRTemp)
            && ((IRTemp) n).name().startsWith(Configuration.ABSTRACT_RET_PREFIX);
    }

    private static int getReturnIndex(IRExpr n) {
        String index = ((IRTemp) n).name()
            .substring(Configuration.ABSTRACT_RET_PREFIX.length());
        return Integer.parseInt(index);
    }


    public static TilerData generate(
        IRMove n,
        final int cost,
        ASMArg target,
        ASMArg source,
        int numRetValues,
        IdGenerator generator,
        Optional<ASMTempArg> additionalRetValAddress,
        List<ASMLine> instrs) {

        ASMLineFactory make = new ASMLineFactory(n);
        TilerData result;
        if (isAnArg(n.target())) {
            // Case: Move(ARG_i, t)
            // Handled in CallStmt
            result = new TilerData(0, List.of(), Optional.empty());
            assert false;
        } else if (isAnArg(n.source())) {
            // Case Move(t, ARG_i)
            int i = getArgIndex(n.source());
            /*
             * If the number of return values in this callee function is greater
             * than 2, then the caller function must had allocated space in the
             * stack to store the third and onward return values. This address
             * is stored in the rdi register, the first argument. If this the
             * case, then the true first argument of the function would be in
             * rsi, usually used for the second argument.
             */
            if (numRetValues > 2) {
                switch (i) {
                    case 0:
                        instrs.add(make.Mov(target, ASMReg.RSI));
                        break;
                    case 1:
                        instrs.add(make.Mov(target, ASMReg.RDX));
                        break;
                    case 2:
                        instrs.add(make.Mov(target, ASMReg.RCX));
                        break;
                    case 3:
                        instrs.add(make.Mov(target, ASMReg.R8));
                        break;
                    case 4:
                        instrs.add(make.Mov(target, ASMReg.R9));
                        break;
                    default:
                        int offset =
                            8 * (i - 5 + ASMConstants.WORD_OFFSET_TO_ARGS);
                        var addr = arg.addr(Optional.of(ASMReg.RBP),
                            ScaleValues.ONE,
                            Optional.empty(),
                            offset);
                        var mem = arg.mem(addr);
                        instrs.add(make.Mov(target, mem));
                        break;
                }
            } else {
                switch (i) {
                    case 0:
                        instrs.add(make.Mov(target, ASMReg.RDI));
                        break;
                    case 1:
                        instrs.add(make.Mov(target, ASMReg.RSI));
                        break;
                    case 2:
                        instrs.add(make.Mov(target, ASMReg.RDX));
                        break;
                    case 3:
                        instrs.add(make.Mov(target, ASMReg.RCX));
                        break;
                    case 4:
                        instrs.add(make.Mov(target, ASMReg.R8));
                        break;
                    case 5:
                        instrs.add(make.Mov(target, ASMReg.R9));
                        break;
                    default:
                        int offset =
                            8 * (i - 6 + ASMConstants.WORD_OFFSET_TO_ARGS);
                        var addr = arg.addr(Optional.of(ASMReg.RBP),
                            ScaleValues.ONE,
                            Optional.empty(),
                            offset);
                        var mem = arg.mem(addr);
                        instrs.add(make.Mov(target, mem));
                        break;
                }
            }
            result = new TilerData(
                1 + cost,
                instrs,
                Optional.empty());

        } else if (isAReturn(n.target())) {
            // Case Move(RET_i, t)
            int i = getReturnIndex(n.target());
            switch (i) {
                case 0:
                    instrs.add(make.Mov(ASMReg.RAX, source));
                    break;
                case 1:
                    instrs.add(make.Mov(ASMReg.RDX, source));
                    break;
                default:
                    int offset = 8 * (i - 2);
                    var addr = arg.addr(additionalRetValAddress.map(m -> m),
                        ScaleValues.ONE,
                        Optional.empty(),
                        offset);
                    var mem = arg.mem(addr);
                    instrs.add(make.Mov(mem, source));
                    break;
            }
            result = new TilerData(
                cost,
                instrs,
                Optional.empty());
        } else if (isAReturn(n.source())) {
            // Case Move(t, RET_i)
            // Handled in CallStmt
            result = new TilerData(0, List.of(), Optional.empty());
            assert false;
        } else if (target instanceof ASMReg
            && source instanceof ASMConstArg) {
            instrs.add(make.MovAbs(target, source));
            result = new TilerData(
                cost,
                instrs,
                Optional.empty());
        } else if (target instanceof ASMMemArg
            && source instanceof ASMMemArg) {
            ASMTempArg sourceTemp = arg.temp(generator.newTemp(), ASMRegSize.QWORD);
            instrs.add(make.Mov(sourceTemp, source));
            instrs.add(make.Mov(target, sourceTemp));
            result = new TilerData(
                cost,
                instrs,
                Optional.empty());
        } else if (target instanceof ASMMemArg && source instanceof ASMConstArg) {
            ASMConstArg constArg = (ASMConstArg) source;
            if (constArg.constant < Integer.MIN_VALUE
                || Integer.MAX_VALUE < constArg.constant) {
                ASMTempArg sourceTemp = arg.temp(generator.newTemp(), ASMRegSize.QWORD);
                instrs.add(make.MovAbs(sourceTemp, source));
                instrs.add(make.Mov(target, sourceTemp));
                result = new TilerData(
                    cost,
                    instrs,
                    Optional.empty());
            } else {
                instrs.add(make.Mov(target, source));
                result = new TilerData(
                    cost,
                    instrs,
                    Optional.empty());
            }
        } else {
            instrs.add(make.Mov(target, source));
            result = new TilerData(
                cost,
                instrs,
                Optional.empty());
        }
        return result;
    }

}
