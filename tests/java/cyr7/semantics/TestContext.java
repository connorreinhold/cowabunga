package cyr7.semantics;

import cyr7.exceptions.UnbalancedPushPopException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TestContext {

    abstract Context createEmptyContext();

    @Test
    void testUnbalancedPopThrowsException() throws UnbalancedPushPopException {
        assertThrows(UnbalancedPushPopException.class, () ->
            createEmptyContext().pop()
        );

        // popping from context twice should throw the exception both times
        Context context = createEmptyContext();
        context.push().pop();
        assertThrows(UnbalancedPushPopException.class, context::pop);
        assertThrows(UnbalancedPushPopException.class, context::pop);

        context = createEmptyContext();
        context.push().push().pop().push().pop().pop();
        assertThrows(UnbalancedPushPopException.class, context::pop);
    }

    @Test
    void testAddGetInteraction() {
        MockType type1 = new MockType("type1");
        MockType type2 = new MockType("type2");
        MockType type3 = new MockType("type3");

        Context context = createEmptyContext();
        context.add("x", new VariableType(PrimitiveType.INT));
        context.add("y", new VariableType(PrimitiveType.BOOL));
        context.add("z", new VariableType(new ArrayType(PrimitiveType.INT)));

        assertEquals(type1, context.get("var1").get());
        assertEquals(type2, context.get("var2").get());
        assertEquals(type3, context.get("var3").get());

        assertEquals(type3, context.get("var3").get());
        assertEquals(type1, context.get("var1").get());
        assertEquals(type2, context.get("var2").get());

        assertTrue(context.get("var4").isEmpty());
        context.add("var4", type1);
        assertEquals(type1, context.get("var4").get());

        assertEquals(type1, context.get("var1").get());
        assertEquals(type2, context.get("var2").get());
        assertEquals(type3, context.get("var3").get());

        assertTrue(context.get("whatever").isEmpty());
    }

    @Test
    void testContext1() throws UnbalancedPushPopException {
        MockType type1 = new MockType("type1");
        MockType type2 = new MockType("type2");
        MockType type3 = new MockType("type3");

        Context context = createEmptyContext();

        context.add("level1", type1);
        assertEquals(type1, context.get("level1").get());
        assertTrue(context.get("level2").isEmpty());
        assertTrue(context.get("level3").isEmpty());

        context.push();
        assertEquals(type1, context.get("level1").get());
        assertTrue(context.get("level2").isEmpty());
        assertTrue(context.get("level3").isEmpty());

        context.add("level2", type2);
        assertEquals(type1, context.get("level1").get());
        assertEquals(type2, context.get("level2").get());
        assertTrue(context.get("level3").isEmpty());

        context.push();
        assertEquals(type1, context.get("level1").get());
        assertEquals(type2, context.get("level2").get());
        assertTrue(context.get("level3").isEmpty());

        context.add("level3", type3);
        assertEquals(type1, context.get("level1").get());
        assertEquals(type2, context.get("level2").get());
        assertEquals(type3, context.get("level3").get());

        context.pop();
        assertEquals(type1, context.get("level1").get());
        assertEquals(type2, context.get("level2").get());
        assertTrue(context.get("level3").isEmpty());

        context.pop();
        assertEquals(type1, context.get("level1").get());
        assertTrue(context.get("level2").isEmpty());
        assertTrue(context.get("level3").isEmpty());

        assertThrows(UnbalancedPushPopException.class, context::pop);
    }

}
