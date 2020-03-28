package cyr7.x86;

import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IRUtil;
import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.typecheck.IxiFileOpener;
import cyr7.x86.asm.ASMLine;

import java.io.Reader;
import java.io.Writer;
import java.util.List;

public final class ASMUtil {

    public static List<ASMLine> generateASM(
        Reader reader,
        Writer writer,
        String filename,
        IxiFileOpener fileOpener
    ) throws Exception {
        IdGenerator idGenerator = new DefaultIdGenerator();
        IRCompUnit compUnit
            = IRUtil.generateIR(reader, writer, filename, fileOpener, idGenerator);
        ASMGenerator asmGenerator
            = new ASMTrivialRegAllocGenerator(TilerFactory.basicTilerFactory(), idGenerator);
        return asmGenerator.generate(compUnit);
    }

    public static void printDebugASM(
        Reader reader,
        Writer writer,
        String filename,
        IxiFileOpener fileOpener
    ) {
        try {
            List<ASMLine> lines = generateASM(reader, writer, filename, fileOpener);
            for (ASMLine line : lines) {
                System.out.println(line.getIntelAssembly());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ASMUtil() { }

}
