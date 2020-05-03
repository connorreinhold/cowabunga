package cyr7.x86.reg_allocator;

import cyr7.exceptions.x86.ASMException;
import cyr7.x86.asm.ASMAddrExpr;
import cyr7.x86.asm.ASMAddrExpr.ScaleValues;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMArgFactory;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMInstrType;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMMemArg;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.ASMTempRegArg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ASMTrivialArgTranslator {

    private static final ASMLineFactory make = ASMLineFactory.instance;
    private static final ASMArgFactory arg = ASMArgFactory.instance;

    public static final ASMReg[] QWORD_REGS =
        {ASMReg.R10, ASMReg.R11, ASMReg.R12, ASMReg.R13, ASMReg.R14,
            ASMReg.R15};

    public static final ASMReg[] BYTE_REGS =
        {ASMReg.AL, ASMReg.BL, ASMReg.CL, ASMReg.DL};

    private final List<ASMLine> prelude = new ArrayList<>();
    private final List<ASMLine> postlude = new ArrayList<>();
    private final List<ASMArg> newArgs = new ArrayList<>();

    private int nextQwordReg = 0;
    private int nextByteReg = 0;

    private final Map<String, Integer> tempToIndexMap;

    public ASMTrivialArgTranslator(Map<String, Integer> tempToIndexMap) {
        this.tempToIndexMap = tempToIndexMap;
    }

    public List<ASMLine> translate(ASMInstr instr) {
        for (int i = 0; i < instr.args.size(); i++) {
            ASMArg arg = instr.args.get(i);

            if (arg instanceof ASMMemArg) {
                ASMMemArg memArg = (ASMMemArg) arg;
                allocateRegisterForMemory(memArg);
            } else if (arg instanceof ASMTempArg) {
                ASMTempArg tempArg = (ASMTempArg) arg;
                allocateMemoryForNormalTemporary(tempArg);
            } else {
                newArgs.add(arg);
            }
        }

        List<ASMLine> lines = new ArrayList<>();
        lines.addAll(prelude);
        lines.add(instr.withArgsReplaced(newArgs));
        lines.addAll(postlude);
        return lines;
    }

    // allocate registers for temporaries inside a mem arg
    protected void allocateRegisterForMemory(ASMMemArg arg) {
        Optional<ASMTempRegArg> base = Optional.empty();
        Optional<ASMTempRegArg> index = Optional.empty();

        if (arg.address.base.isPresent()) {
            ASMTempRegArg b = arg.address.base.get();
            base = Optional.of(allocateRegisterForMemoryOperand(b));
        }
        if (arg.address.index.isPresent()) {
            ASMTempRegArg i = arg.address.index.get();
            index = Optional.of(allocateRegisterForMemoryOperand(i));
        }

        ASMAddrExpr address = new ASMAddrExpr(
            base,
            arg.address.scale,
            index,
            arg.address.displacement);
        newArgs.add(new ASMMemArg(address));
    }

    // allocate registers for temporaries that are regular arguments to an
    // asm instruction (i.e. not inside memory access)
    protected void allocateMemoryForNormalTemporary(ASMTempArg arg) {
        switch (arg.size) {
            case BYTE: {
                ASMReg reg = BYTE_REGS[nextByteReg++];
                ASMReg qwordReg = reg.correspondingQWordReg();

                prelude.add(make.Push(qwordReg));
                prelude.add(make.Mov(qwordReg,
                    new ASMMemArg(addressOfTemporary(arg))));

                postlude.add(0, make.Pop(qwordReg));
                postlude.add(0,
                    make.Mov(new ASMMemArg(addressOfTemporary(arg)), qwordReg));

                newArgs.add(reg);

                break;
            }
            case QWORD: {
                ASMReg reg = QWORD_REGS[nextQwordReg++];

                prelude.add(make.Mov(reg,
                    new ASMMemArg(addressOfTemporary(arg))));

                postlude.add(0,
                    make.Mov(new ASMMemArg(addressOfTemporary(arg)), reg));

                newArgs.add(reg);

                break;
            }
        }
    }

    protected ASMTempRegArg allocateRegisterForMemoryOperand(ASMTempRegArg arg) {
        if (arg instanceof ASMReg) {
            return arg;
        } else if (arg instanceof ASMTempArg) {
            ASMTempArg tempArg = (ASMTempArg) arg;
            ASMReg reg = QWORD_REGS[nextQwordReg++];

            ASMAddrExpr address = addressOfTemporary(tempArg);

            prelude.add(make.Mov(reg, new ASMMemArg(address)));
            postlude.add(0, make.Mov(new ASMMemArg(address), reg));

            return reg;
        } else {
            throw new ASMException("ASMTempRegArg " + arg + " is neither " +
                "a temporary nor a register.");
        }
    }

    protected ASMAddrExpr addressOfTemporary(ASMTempArg tempArg) {
        Integer index = tempToIndexMap.get(tempArg.name);
        if (index == null) {
            throw new ASMException(
                "Could not find index for temporary \""
                    + tempArg.name
                    + "\"");
        }

        // [RBP + 8 * (index + 1)]
        return arg.addr(
            Optional.of(ASMReg.RBP),
            ScaleValues.ONE,
            Optional.empty(),
            -8 * (index + 1));
    }

}
