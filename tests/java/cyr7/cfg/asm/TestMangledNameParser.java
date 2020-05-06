package cyr7.cfg.asm;

import cyr7.cfg.asm.reg.MangledNameParser;
import cyr7.semantics.types.ArrayType;
import cyr7.semantics.types.ExpandedType;
import cyr7.semantics.types.FunctionType;
import cyr7.semantics.types.PrimitiveType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMangledNameParser {

    @Test
    void testProcedure() {
        assertEquals(
            new FunctionType(
                new ExpandedType(new ArrayType(new ArrayType(PrimitiveType.intDefault))),
                new ExpandedType(List.of())),
            MangledNameParser.parseFunctionType("_Imain_paai").get());

        assertEquals(
            new FunctionType(
                new ExpandedType(List.of(
                    PrimitiveType.intDefault,
                    PrimitiveType.intDefault
                )),
                new ExpandedType(List.of())),
            MangledNameParser.parseFunctionType("_Itesting_pii").get());

        assertEquals(
            new FunctionType(
                new ExpandedType(List.of(
                    new ArrayType(new ArrayType(PrimitiveType.intDefault)),
                    PrimitiveType.intDefault
                )),
                new ExpandedType(List.of())),
            MangledNameParser.parseFunctionType("_Itesting_paaii").get());

        assertEquals(
            new FunctionType(
                new ExpandedType(List.of(
                    new ArrayType(new ArrayType(PrimitiveType.intDefault)),
                    new ArrayType(new ArrayType(PrimitiveType.intDefault))
                )),
                new ExpandedType(List.of())),
            MangledNameParser.parseFunctionType("_Itesting_paaiaai").get());

        assertEquals(
            new FunctionType(
                new ExpandedType(List.of()),
                new ExpandedType(List.of())),
            MangledNameParser.parseFunctionType("_Itesting_p").get());
    }

    @Test
    void testOneReturn() {
        assertEquals(
            new FunctionType(
                new ExpandedType(List.of()),
                new ExpandedType(List.of(PrimitiveType.intDefault))),
            MangledNameParser.parseFunctionType("Itesting_i").get());

        assertEquals(
            new FunctionType(
                new ExpandedType(List.of(new ArrayType(PrimitiveType.intDefault))),
                new ExpandedType(List.of(PrimitiveType.intDefault))),
            MangledNameParser.parseFunctionType("Itesting_iai").get());

        assertEquals(
            new FunctionType(
                new ExpandedType(List.of(
                    new ArrayType(PrimitiveType.intDefault),
                    new ArrayType(PrimitiveType.intDefault))
                ),
                new ExpandedType(List.of(PrimitiveType.intDefault))),
            MangledNameParser.parseFunctionType("Itesting_iaiai").get());

        assertEquals(
            new FunctionType(
                new ExpandedType(List.of(
                    PrimitiveType.boolDefault
                )),
                new ExpandedType(List.of(
                    new ArrayType(PrimitiveType.intDefault))
                )),
            MangledNameParser.parseFunctionType("Itesting_aib").get());
    }

    @Test
    void testMultipleReturns() {
        assertEquals(
            new FunctionType(
                new ExpandedType(List.of()),
                new ExpandedType(List.of(
                    PrimitiveType.intDefault,
                    PrimitiveType.intDefault,
                    PrimitiveType.intDefault
                ))),
            MangledNameParser.parseFunctionType("Itesting_t3iii").get());

        assertEquals(
            new FunctionType(
                new ExpandedType(List.of(
                    PrimitiveType.intDefault
                )),
                new ExpandedType(List.of(
                    PrimitiveType.intDefault,
                    new ArrayType(PrimitiveType.intDefault)
                ))),
            MangledNameParser.parseFunctionType("Itesting_t2iaii").get());
    }

}
