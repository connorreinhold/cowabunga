package cyr7.x86;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import cyr7.ir.IRUtil.LowerConfiguration;
import cyr7.typecheck.IxiFileOpener;
import cyr7.x86.asm.ASMLine;

public class testJunk {
    public static void main(String[] args) throws Exception {
        String fileName = "testJunk.xi";
        LowerConfiguration lowerConfiguration = new LowerConfiguration(true, true);
        test(getReader(fileName), fileName, false, null, lowerConfiguration);
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
            LowerConfiguration lowerConfiguration) throws Exception {
        
        boolean pDebug = false;
        if (pDebug) {
            ASMUtil.printDebugASM(
                    reader, filename, fileOpener, lowerConfiguration);
        } else {
            List<ASMLine> lines = ASMUtil.generateAbstractASM(
                    reader, filename, fileOpener, lowerConfiguration);
        }
    }
}
