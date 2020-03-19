package cyr7.typecheck;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.StringReader;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import cyr7.exceptions.parser.ParserException;
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

    void testInterface(String prgm) throws Exception {
        ParserUtil.parseNode(new StringReader(prgm), "test.ixi", true)
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
    void assignToWildcard() {
        String p1 = create.apply("main() { _ = 12; }");
        String p2 = create.apply("main() { _ = {2, 34, 5}; }");
        String p3 = create.apply("main() { _ = h(3); }");
        String p4 = create.apply("main() { _ = (h(3)); }");
        String p5 = create.apply("main() { _ = (h(3) + h(3)); }");
        String p6 = create.apply("main() { _ = f(); }");
        String p7 = create.apply("main() { _ = g(); }");

        assertThrows(SemanticException.class, () -> test(p1));
        assertThrows(SemanticException.class, () -> test(p2));
        assertDoesNotThrow(() -> test(p3));
        assertDoesNotThrow(() -> test(p4));

        // Because RHS is a sum, not a function call.
        assertThrows(SemanticException.class, () -> test(p5));

        assertThrows(SemanticException.class, () -> test(p6));
        assertThrows(SemanticException.class, () -> test(p7));
    }

    @Test
    void tupleAndUnitTypesDoNotWrap() {

        // Nonordinary expressions in return types.
        String p1 = create.apply("main(): int, int { return f(); }");
        String p2 = create.apply("main(): int, int, int { return 2, f(); }");
        String p3 = create.apply("main() { return g(); }");

        assertThrows(SemanticException.class, () -> test(p1));
        assertThrows(SemanticException.class, () -> test(p2));
        assertThrows(SemanticException.class, () -> test(p3));

        // Nonordinary expressions as array elements
        String p4 = create.apply("main() { i: int[] = {f()}; }");
        String p5 = create.apply("main() { i: int[] = {g()}; }");
        String p6 = create.apply("main() { i: int[] = {h(1)}; }");

        assertThrows(SemanticException.class, () -> test(p4));
        assertThrows(SemanticException.class, () -> test(p5));
        assertDoesNotThrow(() -> test(p6));

        // Nonordinary expressions in function calls
        String p7 = create.apply("main() { p(f()); }");
        String p8 = create.apply("main() { g(g()); }");
        String p9 = create.apply("main() { p(1, 3); }");

        assertThrows(SemanticException.class, () -> test(p7));
        assertThrows(SemanticException.class, () -> test(p8));
        assertDoesNotThrow(() -> test(p9));

        String p10 = create.apply("main() {return main()}");
        assertThrows(SemanticException.class, () -> test(p10));
    }

    @Test
    void badCompare() {
        String good1 = create
                .apply("main(): bool, bool { return 1 == 1, 1 <= 1; }");
        String good2 = create
                .apply("main(): bool, bool { return h(3) == 1, 1 <= h(1); }");
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

        String parseBad1 = create.apply("main() { {}[0] = true;}");
        String parseBad2 = create.apply("main() { (empty())[0] = true;}");

        String semanticBad1 = create.apply("main() { empty()[0] = true;}");
        String semanticBad2 = create.apply("main() { i: bool = empty()[0];}");
        String semanticBad3 = create.apply("main() { empty()[0][0] = 2;}");

        assertDoesNotThrow(() -> test(good1));
        assertDoesNotThrow(() -> test(good2));

        assertThrows(ParserException.class, () -> test(parseBad1));
        assertThrows(ParserException.class, () -> test(parseBad2));

        assertThrows(SemanticException.class, () -> test(semanticBad1));
        assertThrows(SemanticException.class, () -> test(semanticBad2));
        assertThrows(SemanticException.class, () -> test(semanticBad3));
    }

    @Test
    void multipleSameParamNames() {
        String good1 = create.apply(
                "main1(i: int) { } main2(i: int) { }" + " main3(i: bool) { }");
        String good2 = create
                .apply("main1(i: int, j:bool) { } main2(i: int) { }"
                        + " main3(i: bool) { }");

        String semanticBad1 = create.apply("main(i: int, i: bool) { }");
        String semanticBad2 = create.apply("main(i: int, i: int) { }");
        String semanticBad3 = create.apply("main1(i: int, j:bool, i:bool) { } "
                + "main2(i: int) { }" + " main3(i: bool) { }");

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
        String semanticBad5 = create
                .apply("main(): int { return length(true); }");

        String good1 = create.apply("main() { _ = length({1,4,6,7}); }");
        String good2 = create
                .apply("main(): int { return length({1,4,6,7}); }");
        String good3 = create
                .apply("main(): int { return length({}[0][0][0][0]); }");

        assertThrows(ParserException.class, () -> test(parseBad1));
        assertThrows(SemanticException.class, () -> test(semanticBad2));
        assertThrows(ParserException.class, () -> test(parseBad3));
        assertThrows(SemanticException.class, () -> test(semanticBad4));
        assertThrows(SemanticException.class, () -> test(semanticBad5));

        assertDoesNotThrow(() -> test(good1));
        assertDoesNotThrow(() -> test(good2));
        assertDoesNotThrow(() -> test(good3));
    }

    /**
     * Test cases for when a void type value is used in addition.
     */
    @Test
    void testAdding() {
        String good1 = create.apply(
                "main() { " + "i: int = {}[0] + {}[0]; " + "r: bool = i < 3;}");
        String good2 = create
                .apply("main() { " + "i: int = (2 + {}[0]) + 5; }");
        String good3 = create
                .apply("main() { " + "i: int = ({}[0] + 2) + 5; }");
        String good4 = create
                .apply("main() { " + "i: int[] = ({}[0] + {2}) + {5}; }");
        String good5 = create.apply("main() { " + "i: int[] = ({}[0] + {}); }");
        String good6 = create
                .apply("main() { " + "i: int = ({}[0] + \"\"[0]); }");
        String good7 = create
                .apply("main() { " + "i: int = ({}[0] + {}[0]); }");
        String good8 = create
                .apply("main() { " + "i: bool = {}[0] + {}[0] == {}; }");
        String good9 = create
                .apply("main() { " + "i: bool = {}[0] + {}[0] == {1, 2, 4}; }");
        String good10 = create
                .apply("main() { " + "i: bool = {}[0] + {}[0] == 32; }");
        String good11 = create
                .apply("main() { " + "i: int[] = ({}[0] + {}[0]) + {32}; }");
        String good12 = create
                .apply("main() { " + "i: int = length({}[0] + {}[0]); }");
        String good13 = create
                .apply("main() { " + "i: int = ({}[0] + {}[0])[0]; }");
        String good14 = create.apply(
                "main() { " + "i: int = (({}[0] + {}[0])[0][0] + {}[0])[0]; }");
        String good15 = create
                .apply("main() { " + "i: int = {}[0] + {}[0] + {}[0]; }");
        String good16 = create
                .apply("main() { " + "i: int[] = {}[0] + {}[0] + {}[0]; }");
        String good17 = create
                .apply("main() { " + "i: int[] = {{}[0] + {}[0] + {}[0]}; }");
        String good18 = create.apply("main() { "
                + "i: int[] = \"Hello\" + {}[0] + {}[0] + {}[0] + \"World\"; }");
        String good19 = create
                .apply("main() { " + "p({}[0] + {}[0], ({}[0] + {}[0])/2);}");
        String good20 = create
                .apply("main() { " + "a: bool = ({}[0] + {}[0]) == 12 }");
        String good21 = create
                .apply("main(i: int[]) { " + "main({}[0] + {}[0]) }");
        String good22 = create.apply(
                "main(i: int[]) { " + "a: bool = ({}[0] + {}[0]) < 12; }");
        String good23 = create.apply("main(i: int[]) { "
                + "a: bool = length({}[0] + {}[0]) < 12; }");
        String good24 = create.apply(
                "main(i: int[]): int[] { " + "return main({}[0] + {}[0]);}");

        String bad1 = create.apply(
                "main() { i: int[] = {}[0] + {}[0]; " + "r: bool = i < 3; }");
        String bad2 = create
                .apply("main() { " + "i: bool = ({}[0] + {}[0]) & true; }");
        String bad3 = create.apply("main() { " + "i: int = ({}[0] + {}); }");
        String bad4 = create
                .apply("main() { " + "i: int = ({}[0] & {}[0]) + 3; }");
        String bad5 = create
                .apply("main() { " + "i: bool[] = {{}[0] + {}[0] + {}[0]}; }");

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
        assertDoesNotThrow(() -> test(good13));
        assertDoesNotThrow(() -> test(good14));
        assertDoesNotThrow(() -> test(good15));
        assertDoesNotThrow(() -> test(good16));
        assertDoesNotThrow(() -> test(good17));
        assertDoesNotThrow(() -> test(good18));
        assertDoesNotThrow(() -> test(good18));
        assertDoesNotThrow(() -> test(good19));
        assertDoesNotThrow(() -> test(good20));
        assertDoesNotThrow(() -> test(good21));
        assertDoesNotThrow(() -> test(good22));
        assertDoesNotThrow(() -> test(good23));
        assertDoesNotThrow(() -> test(good24));

        assertThrows(SemanticException.class, () -> test(bad1));
        assertThrows(SemanticException.class, () -> test(bad2));
        assertThrows(SemanticException.class, () -> test(bad3));
        assertThrows(SemanticException.class, () -> test(bad4));
        assertThrows(SemanticException.class, () -> test(bad5));
    }

    @Test
    void returnBlock() {
        String bad1 = create.apply("main() { if (true) return else return}");
        assertThrows(ParserException.class, () -> test(bad1));

        String bad2 = create
                .apply("main() { if (true) { return } else return}");
        assertThrows(ParserException.class, () -> test(bad2));

        String bad3 = create
                .apply("main() { if (true) return else { return} }");
        assertThrows(ParserException.class, () -> test(bad3));

        String bad4 = create.apply("main() { if (true) return }");
        assertThrows(ParserException.class, () -> test(bad4));

        String bad5 = create
                .apply("main() { if (true) i: int = 4 else return}");
        assertThrows(ParserException.class, () -> test(bad5));

        String good1 = create
                .apply("main() { if (true) i: int = 4 else {return }}");
        assertDoesNotThrow(() -> test(good1));

        String good2 = create
                .apply("main() { if (true) {return} else {return }}");
        assertDoesNotThrow(() -> test(good2));

        String good3 = create.apply("main() {" + "{{{return; }}}" + "}");
        assertDoesNotThrow(() -> test(good3));
    }

    @Test
    void blockTests() {
        String good1 = create.apply("main(): int[] {"
                + "{}{}{}{}{}{}{}{}{}{} return {1,2,3};" + "}");
        assertDoesNotThrow(() -> test(good1));

        String good2 = create.apply("main(): int[] {"
                + "{}{}{}let: int = 12{}{}{}{}{}{return {let}}" + "}");
        assertDoesNotThrow(() -> test(good2));

        String bad1 = create.apply("main(): int[] {"
                + "{}{}{}let: int = 12{}{}{}{}{return {let}}{}" + "}");
        assertThrows(SemanticException.class, () -> test(bad1));

        // i becomes out of scope.
        String bad2 = create.apply(
                "main() : int {" + " {i: int = 4;}" + "i = 12; return i; }");
        assertThrows(SemanticException.class, () -> test(bad2));

        // i gets declared in an accessible scope.
        String bad3 = create.apply("main() : int {" + "i: int; {i: int = 4;}"
                + "i = 12; return i; }");
        assertThrows(SemanticException.class, () -> test(bad3));

        // Try not to confuse arrays with statement blocks
        String bad4 = create
                .apply("main() : int {" + "i: int[] = {return h(45)}}");
        assertThrows(ParserException.class, () -> test(bad4));
    }

    @Test
    void sameLineAssign() {
        String bad1 = create.apply("main() { i: int = i; }");
        assertThrows(SemanticException.class, () -> test(bad1));

        String bad2 = create.apply("main() : int {" + "return i: int;" + "}");
        assertThrows(ParserException.class, () -> test(bad2));
    }

    /**
     * These are test cases created based on posts/responses made by other
     * students/course staff on the CS4120 Piazza.
     * <p>
     * Each test program is named in the form p[num], where [num] is the post
     * number on Piazza.
     */
    @Test
    void piazzaExamplePrograms() {
        String p194 = "main() { f(g()) }\n" + "f(a:int, b: int) { }\n"
                + "g(): int, int { return 1, 1 }";
        assertThrows(SemanticException.class, () -> test(p194));

        String p190 = "foo() { }\n" + "bar() { x:int = foo() }";
        assertThrows(SemanticException.class, () -> test(p190));

        String p186 = "main() {\n" + "  if (true) return\n" + "  a:int = 2\n"
                + "}";
        // Because return statement is not the last statement in a block.
        assertThrows(ParserException.class, () -> test(p186));

        String p187 = "f(a: int[]) : int[] {\n" + "  b: int[] = a\n"
                + "  return b\n" + "}\n" + "main() {\n"
                + "   x: int[] = { 1, 2, 3, 4 }\n" + "   f(x)[0] = 42\n"
                + "   println(unparseInt(x[0]))\n" + "}\n\n"
                + "unparseInt(i: int): int[] { return \"\" }"
                + "println(s: int[]) { return; }";
        assertDoesNotThrow(() -> test(p187));

        String p185 = "foo1 (a: int) {}\n" + "foo2 (a: int) {}";
        assertDoesNotThrow(() -> test(p185));

        String p179 = "foo(){\n"
                + "   a:int[][] = {}[0][0][0][0][0][0][0][0][0][0][0]\n" + "}";
        assertDoesNotThrow(() -> test(p179));

        String p176 = "main() { x: int[]; x = {1,}\n" + "x = {1,2,3,} }";
        assertDoesNotThrow(() -> test(p176));

        String p175 = "f() : int {\n" + "  { return 5 }\n" + "  c: int = 5\n"
                + "}";
        assertThrows(SemanticException.class, () -> test(p175));

    }

    @Test
    void useFunction() {
        String p1 = "main(i: int) { i: int = 12;}";
        assertThrows(SemanticException.class, () -> test(p1));

        String p2 = "i(i: int) { i = 12;}";
        assertThrows(SemanticException.class, () -> test(p2));
    }

    @Test
    void interfaceFileTest() {
        // As stated in the documentation, and based on type-checking rules
        // it is fine to have multiple identifiers with the same name in the
        // arguments.
        String i1 = "main(i: int, i: int)";
        assertDoesNotThrow(() -> testInterface(i1));

        String i2 = "main(i: int, i: int, i: int)";
        assertDoesNotThrow(() -> testInterface(i2));
    }

    @Test
    void testCompoundAssignment() {
        String p1 = "main() { x: int = 12; x += 40; }";
        assertDoesNotThrow(() -> test(p1));

        String p2 = "main() { x: int[] = {2,3,};  x += x; }";
        assertDoesNotThrow(() -> test(p2));

        String bad1 = "main() { x: int[] = {};  x /= 12; }";
        assertThrows(SemanticException.class, () -> test(bad1));

    }

}
