package cyr7.x86;

import cyr7.cfg.asm.reg.ASMRegAllocGenerator;
import cyr7.cli.CLI;
import cyr7.cli.OptConfig;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IRUtil;
import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.typecheck.IxiFileOpener;
import cyr7.x86.tiler.ComplexTiler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class testJunk {

    private static class Opener implements IxiFileOpener {

        @Override
        public Reader openIxiLibraryFile(String name) throws FileNotFoundException {
            if (name.equals("conv")) {
                return new StringReader(
                    "parseInt(str: int[]): int, bool\n"
                        + "unparseInt(n: int): int[]\n"
                );
            } else if (name.equals("io")) {
                return new StringReader(
                    "print(str: int[])\n"
                        + "println(str: int[])\n"
                        + "readln() : int[]\n"
                        + "getchar() : int\n"
                        + "eof() : bool\n"
                );
            } else {
                throw new FileNotFoundException(name);
            }
        }

    }

    public static void main(String[] args) throws Exception {
        String fileName = "testJunk.xi";
        test(getReader(fileName), fileName, false, new Opener(), OptConfig.allEnabled());
    }

    public static Reader getReader(String fileName) throws FileNotFoundException {
        Path sourcePath = Paths.get("tests/resources/", fileName);
        return new BufferedReader(new FileReader(sourcePath.toFile()));
    }
    public static void test(
            Reader reader,
            String filename,
            boolean isIXI,
            IxiFileOpener fileOpener,
            OptConfig optConfig) throws Exception {
        CLI.enableDebugPrinting();
        IdGenerator idGenerator = new DefaultIdGenerator();
        IRCompUnit compUnit = IRUtil.generateIR(reader, filename, fileOpener, optConfig, idGenerator);
        ASMRegAllocGenerator asmGenerator = new ASMRegAllocGenerator(ComplexTiler::new, idGenerator);
        asmGenerator.generate(compUnit);
    }

}
