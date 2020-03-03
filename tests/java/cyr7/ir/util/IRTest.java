package cyr7.ir.util;

import cyr7.ir.nodes.IRNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IRTest {

    public static void assertEq(IRNode expected, IRNode actual) {
        assertEquals(expected.toString(), actual.toString());
    }

}
