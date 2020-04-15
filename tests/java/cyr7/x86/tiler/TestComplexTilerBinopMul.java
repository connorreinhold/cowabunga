package cyr7.x86.tiler;

import cyr7.C;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.x86.asm.ASMAddrExpr.ScaleValues;
import cyr7.x86.asm.ASMArgFactory;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMTempArg.Size;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestComplexTilerBinopMul {

    private static final IRNodeFactory irNodeFactory = new IRNodeFactory_c(C.LOC);
    private static final ASMLineFactory lineFactory = new ASMLineFactory(irNodeFactory.IRSeq());
    private static final ASMArgFactory arg = ASMArgFactory.instance;

    static <T> T makeIR(Function<IRNodeFactory, T> block) {
        return block.apply(irNodeFactory);
    }

    static <T> T makeASM(Function<ASMLineFactory, T> block) {
        return block.apply(lineFactory);
    }

    static ComplexTiler makeTiler() {
        return new ComplexTiler(
            new DefaultIdGenerator(),
            0,
            "no",
            Optional.empty(),
            false);
    }

    @Test
    void testConstAndTemp() {
        ComplexTiler tiler = makeTiler();

        IRBinOp constTemp = makeIR(make ->
            make.IRBinOp(OpType.MUL,
                make.IRConst(4),
                make.IRTemp("bleh"))
        );

        constTemp.accept(tiler);

        assertEquals(
            makeASM(make ->
                List.of(
                    make.Lea(
                        arg.temp("_t0", Size.QWORD),
                        arg.mem(
                            arg.addr(
                                Optional.empty(),
                                ScaleValues.FOUR,
                                Optional.of(arg.temp("bleh", Size.QWORD)),
                                0
                            )
                        )
                    ))
            ),
            constTemp.getOptimalTiling().optimalInstructions);
    }

    //@Test
    void testTempAndConst() {
        ComplexTiler tiler = makeTiler();

        IRBinOp tempConst = makeIR(make ->
            make.IRBinOp(OpType.MUL,
                make.IRTemp("bleh"),
                make.IRConst(4))
        );

        tempConst.accept(tiler);

        assertEquals(
            makeASM(make ->
                List.of(
                    make.Lea(
                        arg.temp("_t0", Size.QWORD),
                        arg.mem(
                            arg.addr(
                                Optional.empty(),
                                ScaleValues.FOUR,
                                Optional.of(arg.temp("bleh", Size.QWORD)),
                                0
                            )
                        )
                    ))
            ),
            tempConst.getOptimalTiling().optimalInstructions);
    }

}
