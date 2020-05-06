package cyr7.x86.abst;

import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.visitor.MyIRVisitor;
import cyr7.x86.ASMConstants;
import cyr7.x86.asm.ASMArgFactory;
import cyr7.x86.asm.ASMAssemblerDirective;
import cyr7.x86.asm.ASMLabel;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.asm.ASMRegSize;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.tiler.TilerData;
import cyr7.x86.tiler.TilerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum ASMAbstract {
    ;

    private static final ASMLineFactory make = ASMLineFactory.instance;
    private static final ASMArgFactory arg = ASMArgFactory.instance;

    public static List<ASMLine> generateBody(
        IRFuncDecl funcDecl,
        IdGenerator generator,
        TilerFactory tilerFactory) {

        String returnLabel = ASMConstants.returnLabel(funcDecl.name());

        List<ASMLine> body = new ArrayList<>();

        // save callee saved registers into temps

        String[] temporariesForCalleeSaved
            = new String[ASMConstants.CALLEE_SAVED_REGISTERS.length];
        for (int i = 0; i < temporariesForCalleeSaved.length; i++) {
            String temp = generator.newTemp();
            temporariesForCalleeSaved[i] = temp;
            body.add(make.Mov(
                arg.temp(temp, ASMRegSize.QWORD),
                ASMConstants.CALLEE_SAVED_REGISTERS[i]));
        }

        // add overspill ret values temp

        Optional<ASMTempArg> addrOfOverspillRetValues;
        if (funcDecl.numOfReturnValues() > 2) {
            addrOfOverspillRetValues =
                Optional.of(arg.temp(generator.newTemp(), ASMRegSize.QWORD));
            body.add(make.Mov(addrOfOverspillRetValues.get(), ASMReg.RDI));
        } else {
            addrOfOverspillRetValues = Optional.empty();
        }

        // add function body

        MyIRVisitor<TilerData> tiler = tilerFactory.constructTiler(
            generator,
            funcDecl.numOfReturnValues(),
            returnLabel,
            addrOfOverspillRetValues,
            true);
        body.addAll(funcDecl.body().accept(tiler).optimalInstructions);

        // add return label

        body.add(new ASMLabel(returnLabel));

        // move callee saved registers out of temps

        for (int i = 0; i < temporariesForCalleeSaved.length; i++) {
            String temp = temporariesForCalleeSaved[i];
            body.add(make.Mov(
                arg.temp(temp, ASMRegSize.QWORD),
                ASMConstants.CALLEE_SAVED_REGISTERS[i]));
        }

        return body;
    }

    public static List<ASMLine> createPrologue(
        String mangledFunctionName,
        long numberOfTemps) {

        List<ASMLine> lines = new ArrayList<>();

        lines.add(new ASMAssemblerDirective("globl", mangledFunctionName));

        lines.addAll(List.of(
            new ASMLabel(mangledFunctionName),
            make.Push(ASMReg.RBP),
            make.Mov(ASMReg.RBP, ASMReg.RSP),
            make.Sub(ASMReg.RSP, arg.constant(8L * numberOfTemps))));

        if (stackNeedsAdjustment(numberOfTemps)) {
            // make it so the stack is always 16-byte aligned on entry to the
            // function body
            lines.add(make.Sub(ASMReg.RSP, arg.constant(8)));
        }

        return lines;
    }

    public static List<ASMLine> createEpilogue(long numberOfTemps) {
        List<ASMLine> lines = new ArrayList<>();

        if (stackNeedsAdjustment(numberOfTemps)) {
            lines.add(make.Add(ASMReg.RSP, arg.constant(8)));
        }

        lines.addAll(List.of(
            make.Add(ASMReg.RSP, arg.constant(8L * numberOfTemps)),
            make.Mov(ASMReg.RSP, ASMReg.RBP),
            make.Pop(ASMReg.RBP),
            make.Ret()));

        return lines;
    }

    private static boolean stackNeedsAdjustment(long numberOfTemps) {
        return (1           // for when call saves pc onto the stack
            + 1             // we save rbp onto the stack
            + numberOfTemps // number of temps we push
        ) % 2 != 0;
    }

}
