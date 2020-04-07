package cyr7.x86;

import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IRUtil;
import cyr7.ir.IdGenerator;
import cyr7.ir.IRUtil.LowerConfiguration;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.typecheck.IxiFileOpener;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.reg_allocator.ASMAbstractGenerator;
import cyr7.x86.reg_allocator.ASMGenerator;
import cyr7.x86.reg_allocator.ASMTrivialRegAllocGenerator;
import cyr7.x86.tiler.TilerFactory;

import java.io.Reader;
import java.io.Writer;
import java.util.List;

public final class ASMUtil {

    public static List<ASMLine> generateASM(
        Reader reader,
        String filename,
        IxiFileOpener fileOpener,
        LowerConfiguration lowerConfiguration
    ) throws Exception {
        IdGenerator idGenerator = new DefaultIdGenerator();
        IRCompUnit compUnit
            = IRUtil.generateIR(reader, filename, fileOpener, lowerConfiguration, idGenerator);
        ASMGenerator asmGenerator
            = new ASMTrivialRegAllocGenerator(TilerFactory.basicTilerFactory(), idGenerator);
        return asmGenerator.generate(compUnit);
    }
    

    public static void writeASM(
            Reader reader,
            Writer writer,
            String filename,
            IxiFileOpener fileOpener,
            LowerConfiguration lowerConfiguration
    ) {
        try {
            writer.append(".intel_syntax noprefix").append(System.lineSeparator());
            writer.append(".globl _Imain_paai").append(System.lineSeparator());
            writer.append(".align 4").append(System.lineSeparator());

            List<ASMLine> lines = generateASM(reader, filename, fileOpener, lowerConfiguration);
            for (ASMLine line: lines) {
                writer.append(line.getIntelAssembly()).append(System.lineSeparator());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<ASMLine> generateAbstractASM(
        Reader reader,
        String filename,
        IxiFileOpener fileOpener,
        LowerConfiguration lowerConfiguration
    ) throws Exception {
        IdGenerator idGenerator = new DefaultIdGenerator();
        IRCompUnit compUnit
            = IRUtil.generateIR(reader, filename, fileOpener, lowerConfiguration, idGenerator);
        ASMGenerator asmGenerator
            = new ASMAbstractGenerator(TilerFactory.basicTilerFactory(), idGenerator);
        return asmGenerator.generate(compUnit);
    }

    public static void printDebugASM(
        Reader reader,
        String filename,
        IxiFileOpener fileOpener,
        LowerConfiguration lowerConfiguration
    ) {
        try {
            List<ASMLine> lines = generateAbstractASM(reader, filename, fileOpener, lowerConfiguration);
            for (ASMLine line : lines) {
                System.out.println(line.getIntelAssembly());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ASMUtil() { }

}
