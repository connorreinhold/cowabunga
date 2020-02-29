package cyr7.typecheck;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import cyr7.exceptions.ParserException;
import cyr7.exceptions.semantics.SemanticException;
import cyr7.parser.ParserUtil;

class TestEdgeCases {

    final String funcs = "f(): int, int { return 1, 2} g() {} "
            + "h(i: int): int {return 0} p(i: int, j: int) {} "
            + "empty(): int[] {return {}; }";

    /**
     * Creates a program string with pre-defined functions.
     * <p>
     * f: () -> int, int
     * <p>
     * g: () -> ()
     * <p>
     * h: int -> int
     * <p>
     * p: int, int -> ()
     * <p>
     * empty: () -> int[]
     */
    Function<String, String> create = s -> funcs + "\n " + s;

    void test(String prgm) throws Exception {
        ParserUtil.parseNode(new StringReader(prgm), "test.xi", false)
                  .accept(new TypeCheckVisitor(null));
    }

    @Test
    void testArrayInit() {
        String p1 = create.apply("main() { i: int[true]; }");
        String p2 = create.apply("main() { i: int[f()]; }");
        String p3 = create.apply("main() { i: int[g()]; }");

        assertThrows(SemanticException.class, () -> test(p1));
        assertThrows(SemanticException.class, () -> test(p2));
        assertThrows(SemanticException.class, () -> test(p3));
    }

    @Test
    void assignNonFunctionToWildcard() {
        String p1 = create.apply("main() { _ = 12; }");
        String p2 = create.apply("main() { _ = {2, 34, 5}; }");
        String p3 = create.apply("main() { _ = h(3); }");

        assertThrows(SemanticException.class, () -> test(p1));
        assertThrows(SemanticException.class, () -> test(p2));
        assertDoesNotThrow(() -> test(p3));
    }

    @Test
    void cannotReturnNonOrdinary() {
        String p1 = create.apply("main(): int, int { return f(); }");
        String p2 = create.apply("main(): int, int, int { return 2, f(); }");
        String p3 = create.apply("main() { return g(); }");

        assertThrows(SemanticException.class, () -> test(p1));
        assertThrows(SemanticException.class, () -> test(p2));
        assertThrows(SemanticException.class, () -> test(p3));
    }

    @Test
    void createArrayOfNonOrdinary() {
        String p1 = create.apply("main() { i: int[] = {f()}; }");
        String p2 = create.apply("main() { i: int[] = {g()}; }");
        String p3 = create.apply("main() { i: int[] = {h(1)}; }");

        assertThrows(SemanticException.class, () -> test(p1));
        assertThrows(SemanticException.class, () -> test(p2));
        assertDoesNotThrow(() -> test(p3));
    }

    @Test
    void nonOrdinaryArguments() {
        String p1 = create.apply("main() { p(f()); }");
        String p2 = create.apply("main() { g(g()); }");
        String p3 = create.apply("main() { p(1, 3); }");

        assertThrows(SemanticException.class, () -> test(p1));
        assertThrows(SemanticException.class, () -> test(p2));
        assertDoesNotThrow(() -> test(p3));
    }

    @Test
    void badCompare() {
        String good1 = create.apply(
                "main(): bool, bool { return 1 == 1, 1 <= 1; }");
        String good2 = create.apply(
                "main(): bool, bool { return h(3) == 1, 1 <= h(1); }");
        String p1 = create.apply("main(): bool { return f() == f(); }");
        String p2 = create.apply("main(): bool { return g() == g(); }");
        String p3 = create.apply("main(): bool { return f() == g(); }");

        assertDoesNotThrow(() -> test(good1));
        assertDoesNotThrow(() -> test(good2));
        assertThrows(SemanticException.class, () -> test(p1));
        assertThrows(SemanticException.class, () -> test(p2));
        assertThrows(SemanticException.class, () -> test(p3));
    }

    @Test
    void allowCertainTrailingCommas() {
        String good1 = create.apply("main (): int[] { return {1,2,4,5,}; }");
        String good2 = create.apply("main(): int[] {return {1,   }; }");
        String good3 = create.apply("main (): int[], int[] { return {}, {}; }");
        String bad1 = create.apply("main (): int[] { return {,}; }");
        String bad2 = create.apply("main (): int[], int[], { return {}, {}; }");
        String bad3 = create.apply("main (): int[] { return {{}}; }");

        assertDoesNotThrow(() -> test(good1));
        assertDoesNotThrow(() -> test(good2));
        assertDoesNotThrow(() -> test(good3));
        assertThrows(ParserException.class, () -> test(bad1));
        assertThrows(ParserException.class, () -> test(bad2));
        assertThrows(SemanticException.class, () -> test(bad3));
    }

    @Test
    void assignToFunction() {
        String good1 = create.apply("main() { empty()[0] = 12;}");
        String good2 = create.apply("main() { i: int = empty()[0];}");
        String good3 = "f(a: int[]): int[] {" + "b: int[] = a; return b } "
                + "main() { x: int[] = { 1, 2, 3, 4 }\n" + "   f(x)[0] = 42\n"
                + "   println(unparseInt(x[0])); }"
                + " unparseInt(i: int): int[] { return \"\" }"
                + " println(s: int[]) { }";

        String parseBad1 = create.apply("main() { {}[0] = true;}");
        String parseBad2 = create.apply("main() { (empty())[0] = true;}");

        String semanticBad1 = create.apply("main() { empty()[0] = true;}");
        String semanticBad2 = create.apply("main() { i: bool = empty()[0];}");
        String semanticBad3 = create.apply("main() { empty()[0][0] = 2;}");

        assertDoesNotThrow(() -> test(good1));
        assertDoesNotThrow(() -> test(good2));
        assertDoesNotThrow(() -> test(good3));

        assertThrows(ParserException.class, () -> test(parseBad1));
        assertThrows(ParserException.class, () -> test(parseBad2));

        assertThrows(SemanticException.class, () -> test(semanticBad1));
        assertThrows(SemanticException.class, () -> test(semanticBad2));
        assertThrows(SemanticException.class, () -> test(semanticBad3));
    }

    @Test
    void multipleSameParamNames() {
        String good1 = create.apply("main1(i: int) { } main2(i: int) { }"
                + " main3(i: bool) { }");
        String good2 = create.apply("main1(i: int, j:bool) { } main2(i: int) { }"
                + " main3(i: bool) { }");

        String semanticBad1 = create.apply("main(i: int, i: bool) { }");
        String semanticBad2 = create.apply("main(i: int, i: int) { }");
        String semanticBad3 = create.apply("main1(i: int, j:bool, i:bool) { } "
                + "main2(i: int) { }"
                + " main3(i: bool) { }");

        assertDoesNotThrow(() -> test(good1));
        assertDoesNotThrow(() -> test(good2));
        assertThrows(SemanticException.class, () -> test(semanticBad1));
        assertThrows(SemanticException.class, () -> test(semanticBad2));
        assertThrows(SemanticException.class, () -> test(semanticBad3));
    }

    @Test
    void lengthTest() {
        String parseBad1 = create.apply("main() { length({}); }");
        String semanticBad2 = create.apply("main() { return length({}); }");
        String parseBad3 = create.apply("main(): int { return length(); }");
        String semanticBad4 = create.apply("main(): int { return length(2); }");
        String semanticBad5 = create.apply(
                "main(): int { return length(true); }");

        String good1 = create.apply("main() { _ = length({1,4,6,7}); }");
        String good2 = create.apply(
                "main(): int { return length({1,4,6,7}); }");
        String good3 = create.apply(
                "main(): int { return length({}[0][0][0][0]); }");

        assertThrows(ParserException.class, () -> test(parseBad1));
        assertThrows(SemanticException.class, () -> test(semanticBad2));
        assertThrows(ParserException.class, () -> test(parseBad3));
        assertThrows(SemanticException.class, () -> test(semanticBad4));
        assertThrows(SemanticException.class, () -> test(semanticBad5));

        assertDoesNotThrow(() -> test(good1));
        assertDoesNotThrow(() -> test(good2));
        assertDoesNotThrow(() -> test(good3));
    }

    @Test
    void testAdding() {
        String good1 = create.apply("main() { "
                + "i: int = {}[0] + {}[0]; "
                + "r: bool = i < 3;}");
        String good2 = create.apply("main() { "
                + "i: int = (2 + {}[0]) + 5; }");
        String good3 = create.apply("main() { "
                + "i: int = ({}[0] + 2) + 5; }");
        String good4 = create.apply("main() { "
                + "i: int[] = ({}[0] + {2}) + {5}; }");
        String good5 = create.apply("main() { "
                + "i: int[] = ({}[0] + {}); }");
        String good6 = create.apply("main() { "
                + "i: int = ({}[0] + \"\"[0]); }");
        String good7 = create.apply("main() { "
                + "i: int = ({}[0] + {}[0]); }");
        String good8 = create.apply("main() { "
                + "i: bool = {}[0] + {}[0] == {}; }");
        String good9 = create.apply("main() { "
                + "i: bool = {}[0] + {}[0] == {1, 2, 4}; }");
        String good10 = create.apply("main() { "
                + "i: bool = {}[0] + {}[0] == 32; }");
        String good11 = create.apply("main() { "
                + "i: int[] = ({}[0] + {}[0]) + {32}; }");
        String good12 = create.apply("main() { "
                + "i: int = length({}[0] + {}[0]); }");

        String bad1 = create.apply("main() { i: int[] = {}[0] + {}[0]; "
                + "r: bool = i < 3; }");
        String bad2 = create.apply("main() { "
                + "i: bool = ({}[0] + {}[0]) & true; }");
        String bad3 = create.apply("main() { "
                + "i: int = ({}[0] + {}); }");

        assertDoesNotThrow(() -> test(good1));
        assertDoesNotThrow(() -> test(good2));
        assertDoesNotThrow(() -> test(good3));
        assertDoesNotThrow(() -> test(good4));
        assertDoesNotThrow(() -> test(good5));
        assertDoesNotThrow(() -> test(good6));
        assertDoesNotThrow(() -> test(good7));
        assertDoesNotThrow(() -> test(good8));
        assertDoesNotThrow(() -> test(good9));
        assertDoesNotThrow(() -> test(good10));
        assertDoesNotThrow(() -> test(good11));
        assertDoesNotThrow(() -> test(good12));

        assertThrows(SemanticException.class, () -> test(bad1));
        assertThrows(SemanticException.class, () -> test(bad2));
        assertThrows(SemanticException.class, () -> test(bad3));

    }

    @Test
    void returnBlock() {
        String bad1 = create.apply("main() { if (true) return else return}");
        assertThrows(ParserException.class, () -> test(bad1));

        String bad2 = create.apply(
                "main() { if (true) { return } else return}");
        assertThrows(ParserException.class, () -> test(bad2));

        String bad3 = create.apply(
                "main() { if (true) return else { return} }");
        assertThrows(ParserException.class, () -> test(bad3));

        String bad4 = create.apply("main() { if (true) return }");
        assertThrows(ParserException.class, () -> test(bad4));

        String bad5 = create.apply(
                "main() { if (true) i: int = 4 else return}");
        assertThrows(ParserException.class, () -> test(bad5));

        String good1 = create.apply(
                "main() { if (true) i: int = 4 else {return }}");
        assertDoesNotThrow(() -> test(good1));

        String good2 = create.apply(
                "main() { if (true) {return} else {return }}");
        assertDoesNotThrow(() -> test(good2));

    }

}
