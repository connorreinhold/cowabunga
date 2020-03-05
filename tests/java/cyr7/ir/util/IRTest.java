package cyr7.ir.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cyr7.ir.nodes.IRNode;

public class IRTest {

    public static void assertEq(IRNode expected, IRNode actual) {
        assertEquals(expected.toString(), actual.toString());
    }

}
