package cyr7.x86;

import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.semantics.types.FunctionType;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ASMTrivialRegAllocGenerator implements ASMGenerator {

    private static final ASMLineFactory make = ASMLineFactory.instance;
    private static final ASMArgFactory arg = ASMArgFactory.instance;

    private final TilerFactory tilerFactory;
    private final IdGenerator generator;

    public ASMTrivialRegAllocGenerator(TilerFactory tilerFactory,
            IdGenerator generator) {
        this.tilerFactory = tilerFactory;
        this.generator = generator;
    }

    public List<ASMLine> generate(IRCompUnit compUnit) {
        List<ASMLine> lines = new ArrayList<>();

        Set<Entry<String, IRFuncDecl>> nameFuncDeclPairs = compUnit.functions()
                                                                   .entrySet();

        Map<String, FunctionType> fMap = new HashMap<>();
        for (Entry<String, IRFuncDecl> nameFuncDecl : nameFuncDeclPairs) {
            fMap.put(nameFuncDecl.getKey(),
                    nameFuncDecl.getValue()
                                .type());
        }

        for (Entry<String, IRFuncDecl> nameFuncDecl : nameFuncDeclPairs) {
            lines.addAll(generate(nameFuncDecl.getKey(),
                    nameFuncDecl.getValue(),
                    fMap));
        }

        return lines;
    }

    private List<ASMLine> generate(String funcName, IRFuncDecl funcDecl,
            Map<String, FunctionType> fMap) {

        String returnLbl = "end_" + funcName;

        MyIRVisitor<TilerData> tiler = tilerFactory.constructTiler(generator,
                funcName,
                fMap,
                returnLbl);

        List<ASMLine> functionBody = funcDecl.body()
                                             .accept(tiler).optimalInstructions;
        List<ASMTempArg> uniqueTemps = uniqueTemps(functionBody);
        List<ASMLine> concreteFunctionBody = replaceTemporariesWithMemoryAccess(
                functionBody,
                uniqueTemps);

        List<ASMLine> lines = new ArrayList<>();
        lines.addAll(createPrologue(funcName, uniqueTemps.size()));
        lines.addAll(concreteFunctionBody);
        lines.addAll(createEpilogue(returnLbl, uniqueTemps.size()));

        return lines;
    }

    /**
     * Counts the number of unique temporaries used in a list of instructions.
     *
     * @param lines
     * @return
     */
    private List<ASMTempArg> uniqueTemps(List<ASMLine> lines) {
        Set<ASMTempArg> temps = new HashSet<>();

        for (ASMLine line : lines) {
            if (line instanceof ASMInstr) {
                ASMInstr instr = (ASMInstr) line;

                for (ASMArg arg : instr.args) {
                    if (arg instanceof ASMTempArg) {
                        temps.add((ASMTempArg) arg);
                    }
                }
            }
        }

        return new ArrayList<>(temps);
    }

    private List<ASMLine> createPrologue(String mangledFunctionName,
            long numberOfTemps) {
        return List.of(new ASMLabel(mangledFunctionName),
                make.Push(ASMReg.RBP),
                make.Mov(ASMReg.RBP, ASMReg.RSP),
                make.Sub(ASMReg.RSP, arg.constant(8L * numberOfTemps)));
    }

    private List<ASMLine> createEpilogue(String returnLbl, long numberOfTemps) {
        return List.of(new ASMLabel(returnLbl),
                make.Add(ASMReg.RSP, arg.constant(8L * numberOfTemps)),
                make.Mov(ASMReg.RSP, ASMReg.RBP),
                make.Pop(ASMReg.RBP),
                make.Ret());
    }

    private List<ASMLine> replaceTemporariesWithMemoryAccess(
            List<ASMLine> lines, List<ASMTempArg> tempArgs) {
        Map<String, Integer> temporaryToIndexMap = new HashMap<>();
        for (int i = 0; i < tempArgs.size(); i++) {
            ASMTempArg tempArg = tempArgs.get(i);
            temporaryToIndexMap.put(tempArg.name, i);
        }

        return lines.stream()
                    .flatMap(asmLine -> {
                        if (asmLine instanceof ASMInstr) {
                            return replaceInstrTempArgsWithMemoryAccess(
                                    (ASMInstr) asmLine,
                                    temporaryToIndexMap).stream();
                        } else {
                            return Stream.of(asmLine);
                        }
                    })
                    .collect(Collectors.toUnmodifiableList());
    }

    private List<ASMLine> replaceInstrTempArgsWithMemoryAccess(ASMInstr instr,
            Map<String, Integer> temporaryToIndexMap) {

        // assumption: availableQword and availableByte regs are disjoint

        ASMReg[] availableQwordRegisters =
            { ASMReg.R10, ASMReg.R11, ASMReg.R12, ASMReg.R13, ASMReg.R14,
                    ASMReg.R15 };
        int indexOfNextAvailableQwordRegister = 0;

        ASMReg[] availableByteRegisters =
            { ASMReg.AL, ASMReg.BL, ASMReg.CL, ASMReg.DL };
        int indexOfNextAvailableByteRegister = 0;

        List<ASMLine> prelude = new ArrayList<>();
        List<ASMLine> postlude = new ArrayList<>();
        List<ASMArg> argsWithTempsReplacedForRegisters = new ArrayList<>();

        for (ASMArg arg : instr.args) {
            if (arg instanceof ASMTempArg) {
                ASMTempArg tempArg = (ASMTempArg) arg;

                switch (tempArg.size) {
                case BYTE: {
                    ASMReg reg = availableByteRegisters[indexOfNextAvailableByteRegister];
                    ASMReg qwordReg = reg.correspondingQWordReg();

                    prelude.add(make.Push(qwordReg));
                    // clear the upper 7 bytes of the "surrounding" register.
                    prelude.add(make.Xor(qwordReg, qwordReg)); 
                    prelude.add(make.Mov(reg,
                            new ASMMemArg(addressOfTemporary(tempArg,
                                    temporaryToIndexMap).get())));

                    postlude.add(0, make.Pop(qwordReg));
                    postlude.add(0, make.Mov(new ASMMemArg(addressOfTemporary(
                            tempArg,
                            temporaryToIndexMap).get()), reg));

                    argsWithTempsReplacedForRegisters.add(reg);

                    indexOfNextAvailableByteRegister++;

                    break;
                }
                case QWORD: {
                    ASMReg reg = availableQwordRegisters[indexOfNextAvailableQwordRegister];

                    prelude.add(make.Mov(reg,
                            new ASMMemArg(addressOfTemporary(tempArg,
                                    temporaryToIndexMap).get())));

                    postlude.add(0, make.Mov(new ASMMemArg(addressOfTemporary(
                            tempArg,
                            temporaryToIndexMap).get()), reg));

                    argsWithTempsReplacedForRegisters.add(reg);

                    indexOfNextAvailableQwordRegister++;

                    break;
                }
                }
            } else {
                argsWithTempsReplacedForRegisters.add(arg);
            }
        }

        List<ASMLine> lines = new ArrayList<>();
        lines.addAll(prelude);
        lines.add(instr.withArgsReplaced(argsWithTempsReplacedForRegisters));
        lines.addAll(postlude);
        return lines;
    }

    private Optional<ASMAddrExpr> addressOfTemporary(ASMTempArg tempArg,
            Map<String, Integer> temporaryToIndexMap) {

        Integer index = temporaryToIndexMap.get(tempArg.name);
        if (index == null) {
            return Optional.empty();
        }

        // [RBP + 8 * (index + 1)]
        return Optional.of(arg.addr(Optional.of(ASMReg.RBP),
                ScaleValues.ONE,
                Optional.empty(),
                -8 * (index + 1)));
    }

}
