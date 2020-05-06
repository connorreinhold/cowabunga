package cyr7.x86;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.sun.tools.attach.AgentInitializationException;
import cyr7.cfg.asm.reg.ASMRegAllocGenerator;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.IRUtil;
import cyr7.ir.IRUtil.LowerConfiguration;
import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.typecheck.IxiFileOpener;
import cyr7.x86.abst.ASMAbstract;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMReg;
import cyr7.x86.tiler.ComplexTiler;

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
        IdGenerator idGenerator = new DefaultIdGenerator();
        IRCompUnit compUnit = IRUtil.generateIR(reader, filename, fileOpener, lowerConfiguration, idGenerator);
        ASMRegAllocGenerator asmGenerator = new ASMRegAllocGenerator(ComplexTiler::new, idGenerator);
        System.out.println(asmGenerator.generate(compUnit));
    }

}
