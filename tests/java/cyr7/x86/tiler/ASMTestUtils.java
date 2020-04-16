package cyr7.x86.tiler;

import cyr7.C;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.x86.asm.ASMArgFactory;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import org.junit.jupiter.api.Assertions;

import java.util.Optional;
import java.util.function.Function;

public final class ASMTestUtils {

    public static final IRNodeFactory irNodeFactory = new IRNodeFactory_c(C.LOC);
    public static final ASMLineFactory lineFactory = new ASMLineFactory(irNodeFactory.IRSeq());
    public static final ASMArgFactory arg = ASMArgFactory.instance;

    public static <T> T makeIR(Function<IRNodeFactory, T> block) {
        return block.apply(irNodeFactory);
    }

    public static <T> T makeASM(Function<ASMLineFactory, T> block) {
        return block.apply(lineFactory);
    }

    public static ComplexTiler makeTiler() {
        return new ComplexTiler(
            new DefaultIdGenerator(),
            0,
            "no",
            Optional.empty(),
            false);
    }

    public static void assertEqualsASM(String expected, ASMLine line) {
        Assertions.assertEquals(expected, line.getIntelAssembly(false, false));
    }

    public static void assertEqualsTiled(IRNode node, String... assembly) {
        ComplexTiler tiler = makeTiler();
        node.accept(tiler);
        Assertions.assertEquals(node.getOptimalTiling().optimalInstructions.size(), assembly.length);
        for (int i = 0; i < assembly.length; i++) {
            assertEqualsASM(assembly[i], node.getOptimalTiling().optimalInstructions.get(i));
        }
    }

}
