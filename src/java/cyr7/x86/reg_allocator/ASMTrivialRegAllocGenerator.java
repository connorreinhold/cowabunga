package cyr7.x86.reg_allocator;

import cyr7.exceptions.x86.ASMException;
import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.visitor.MyIRVisitor;
import cyr7.x86.asm.ASMAddrExpr;
import cyr7.x86.asm.ASMAddrExpr.ScaleValues;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMArgFactory;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMLabel;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMMemArg;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.ASMTempArg.Size;
import cyr7.x86.asm.ASMTempRegArg;
import cyr7.x86.tiler.TilerData;
import cyr7.x86.tiler.TilerFactory;
import cyr7.x86.visitor.TempVisitor;
import polyglot.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASMTrivialRegAllocGenerator implements ASMGenerator {

    private static final ASMLineFactory make = ASMLineFactory.instance;
    private static final ASMArgFactory arg = ASMArgFactory.instance;

    private final TilerFactory tilerFactory;
    private final IdGenerator generator;

    public ASMTrivialRegAllocGenerator(TilerFactory tilerFactory,
                                       IdGenerator generator) {
        this.tilerFactory = tilerFactory;
        this.generator = generator;
    }

    @Override
    public List<ASMLine> generate(IRCompUnit compUnit) {
        List<ASMLine> lines = new ArrayList<>();

        Set<Entry<String, IRFuncDecl>> nameFuncDeclPairs =
            compUnit.functions().entrySet();
        for (Entry<String, IRFuncDecl> nameFuncDecl : nameFuncDeclPairs) {
            lines.addAll(generate(
                nameFuncDecl.getKey(),
                nameFuncDecl.getValue(),
                nameFuncDecl.getValue().numOfReturnValues()));
        }

        return lines;
    }

    protected Pair<List<ASMLine>, List<ASMTempArg>> generateAbstractAssembly(
        String funcName,
        IRFuncDecl funcDecl,
        int numRetValues) {

        String returnLbl = "end_" + funcName;

        Optional<ASMTempArg> addrOfOverspillRetValues;
        if (numRetValues > 2) {
            addrOfOverspillRetValues = Optional.of(arg.temp(generator.newTemp(), Size.QWORD));
        } else {
            addrOfOverspillRetValues = Optional.empty();
        }

        MyIRVisitor<TilerData> tiler = tilerFactory.constructTiler(
            generator,
            numRetValues,
            returnLbl,
            addrOfOverspillRetValues,
            true);

        List<ASMLine> functionBody = funcDecl.body()
            .accept(tiler).optimalInstructions;
        List<ASMTempArg> uniqueTemps = uniqueTemps(functionBody);
        addrOfOverspillRetValues.ifPresent(uniqueTemps::add);

        List<ASMLine> lines = new ArrayList<>();
        lines.addAll(createPrologue(funcName, uniqueTemps.size(), addrOfOverspillRetValues));
        lines.addAll(functionBody);
        lines.addAll(createEpilogue(returnLbl, uniqueTemps.size()));

        return new Pair<>(lines, uniqueTemps);
    }

    private List<ASMLine> generate(String funcName, IRFuncDecl funcDecl,
            int numOfReturnValues) {
        var result = generateAbstractAssembly(
            funcName,
            funcDecl,
            numOfReturnValues);
        return replaceTemporariesWithMemoryAccess(result.part1(), result.part2());
    }

    /**
     * Counts the number of unique temporaries used in a list of instructions.
     *
     * @param lines
     * @return
     */
    private List<ASMTempArg> uniqueTemps(List<ASMLine> lines) {
        Set<ASMTempArg> temps = new HashSet<>();
        var tempVisitor = new TempVisitor();
        lines.forEach(l -> temps.addAll(l.accept(tempVisitor)));
        return new ArrayList<>(temps);
    }

    private List<ASMLine> createPrologue(
        String mangledFunctionName,
        long numberOfTemps,
        Optional<ASMTempArg> addrOfOverspillRetValues) {

        List<ASMLine> lines = new ArrayList<>();
        lines.addAll(List.of(
            new ASMLabel(mangledFunctionName),
            make.Push(ASMReg.RBP),
            make.Mov(ASMReg.RBP, ASMReg.RSP),
            make.Sub(ASMReg.RSP, arg.constant(8L * numberOfTemps))));

        // TODO: Better callee-saved registers
        // Definitely do not need to save RSP and RBP, but doing it for the sake
        // of testing for now
        lines.addAll(List.of(
            make.Push(ASMReg.RBX),
            make.Push(ASMReg.RSP),
            make.Push(ASMReg.RBP),
            make.Push(ASMReg.R12),
            make.Push(ASMReg.R13),
            make.Push(ASMReg.R14),
            make.Push(ASMReg.R15)
        ));

        if (!stack16ByteAlignedBeforeAdjustment(numberOfTemps)) {
            // make it so the stack is always 16-byte aligned on entry to the
            // function body
            lines.add(make.Sub(ASMReg.RSP, arg.constant(8)));
        }

        addrOfOverspillRetValues.ifPresent(addr -> {
            // move the first argument containing the address in the stack where
            // the caller has allocated space for the overspill return values,
            // into addr

            lines.add(make.Mov(addr, ASMReg.RDI));
        });

        return lines;
    }

    private List<ASMLine> createEpilogue(String returnLbl, long numberOfTemps) {
        List<ASMLine> lines = new ArrayList<>();

        lines.add(new ASMLabel(returnLbl));

        if (!stack16ByteAlignedBeforeAdjustment(numberOfTemps)) {
            lines.add(make.Add(ASMReg.RSP, arg.constant(8)));
        }

        lines.addAll(List.of(
            make.Pop(ASMReg.R15),
            make.Pop(ASMReg.R14),
            make.Pop(ASMReg.R13),
            make.Pop(ASMReg.R12),
            make.Pop(ASMReg.RBP),
            make.Pop(ASMReg.RSP),
            make.Pop(ASMReg.RBX)
        ));

        lines.addAll(List.of(
            make.Add(ASMReg.RSP, arg.constant(8L * numberOfTemps)),
            make.Mov(ASMReg.RSP, ASMReg.RBP),
            make.Pop(ASMReg.RBP),
            make.Ret()));

        return lines;
    }

    private boolean stack16ByteAlignedBeforeAdjustment(long numberOfTemps) {
        return (1 // for when call saves pc onto the stack
            + 1 // we save rbp onto the stack
            + numberOfTemps // number of temps we push
            + 7) // callee-saved registers
        % 2 == 0;
    }

    private List<ASMLine> replaceTemporariesWithMemoryAccess(
        List<ASMLine> lines, List<ASMTempArg> tempArgs) {

        Map<String, Integer> temporaryToIndexMap = new HashMap<>();
        for (int i = 0; i < tempArgs.size(); i++) {
            ASMTempArg tempArg = tempArgs.get(i);
            temporaryToIndexMap.put(tempArg.name, i);
        }

        return lines.stream().flatMap(asmLine -> {
            if (asmLine instanceof ASMInstr) {
                return replaceInstrTempArgsWithMemoryAccess(
                    (ASMInstr) asmLine,
                    temporaryToIndexMap).stream();
            } else {
                return Stream.of(asmLine);
            }
        }).collect(Collectors.toUnmodifiableList());
    }

    private List<ASMLine> replaceInstrTempArgsWithMemoryAccess(
        ASMInstr instr,
        Map<String, Integer> temporaryToIndexMap) {

        TempToMemoryArgTranslator translator
            = new TempToMemoryArgTranslator(temporaryToIndexMap);
        return translator.translate(instr);
    }

    private static final class TempToMemoryArgTranslator {

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

        TempToMemoryArgTranslator(Map<String, Integer> tempToIndexMap) {
            this.tempToIndexMap = tempToIndexMap;
        }

        List<ASMLine> translate(ASMInstr instr) {
            for (ASMArg arg : instr.args) {
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
        private void allocateRegisterForMemory(ASMMemArg arg) {
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
        private void allocateMemoryForNormalTemporary(ASMTempArg arg) {
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

        private ASMTempRegArg allocateRegisterForMemoryOperand(ASMTempRegArg arg) {
            if (arg instanceof ASMReg) {
                return arg;
            } else if (arg instanceof ASMTempArg) {
                ASMTempArg tempArg = (ASMTempArg) arg;
                ASMReg reg = QWORD_REGS[nextQwordReg];

                ASMAddrExpr address = addressOfTemporary(tempArg);
                prelude.add(make.Mov(reg, new ASMMemArg(address)));
                postlude.add(0, make.Mov(new ASMMemArg(address), reg));
                nextQwordReg++;
                return reg;
            } else {
                throw new ASMException("ASMTempRegArg " + arg + " is neither " +
                    "a temporary nor a register.");
            }

        }

        private ASMAddrExpr addressOfTemporary(ASMTempArg tempArg) {
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

}

