package cyr7.x86;

import cyr7.cli.OptConfig;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IRUtil;
import cyr7.ir.IdGenerator;
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

    public enum TilerConf {
        BASIC, COMPLEX;

        TilerFactory getFactory() {
            switch (this) {
                case BASIC: return TilerFactory.basicTilerFactory();
                case COMPLEX: return TilerFactory.complexTilerFactory();
                default: throw new RuntimeException("Unknown tiler conf case: " + this);
            }
        }
    }

    public static List<ASMLine> generateASM(
        Reader reader,
        String filename,
        IxiFileOpener fileOpener,
        OptConfig optConfig,
        TilerConf tiler
    ) throws Exception {
        IdGenerator idGenerator = new DefaultIdGenerator();
        IRCompUnit compUnit
            = IRUtil.generateIR(reader, filename, fileOpener, optConfig, idGenerator);
        ASMGenerator asmGenerator
            = new ASMTrivialRegAllocGenerator(tiler.getFactory(), idGenerator);
        return asmGenerator.generate(compUnit);
    }

    public static void writeASM(
            Reader reader,
            Writer writer,
            String filename,
            IxiFileOpener fileOpener,
            OptConfig optConfig,
            TilerConf tiler
    ) {
        try {
            writer.append(".intel_syntax noprefix").append(System.lineSeparator());

            List<ASMLine> lines = generateASM(reader, filename, fileOpener, optConfig, tiler);
            for (ASMLine line: lines) {
                writer.append(line.getIntelAssembly()).append(System.lineSeparator());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<ASMLine> generateAbstractASM(
        Reader reader,
        String filename,
        IxiFileOpener fileOpener,
        OptConfig optConfig
    ) throws Exception {
        IdGenerator idGenerator = new DefaultIdGenerator();
        IRCompUnit compUnit
            = IRUtil.generateIR(reader, filename, fileOpener, optConfig, idGenerator);
        ASMGenerator asmGenerator
            = new ASMAbstractGenerator(TilerFactory.complexTilerFactory(), idGenerator);
        return asmGenerator.generate(compUnit);
    }

    public static void printDebugASM(
        Reader reader,
        String filename,
        IxiFileOpener fileOpener,
        OptConfig optConfig
    ) {
        try {
            List<ASMLine> lines = generateAbstractASM(reader, filename, fileOpener, optConfig);
            for (ASMLine line : lines) {
                System.out.println(line.getIntelAssembly());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ASMUtil() { }

}
