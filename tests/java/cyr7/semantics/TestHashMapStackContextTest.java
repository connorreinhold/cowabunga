package cyr7.semantics;

import cyr7.semantics.context.Context;
import cyr7.semantics.context.HashMapStackContext;

public class TestHashMapStackContextTest extends TestContext {

    @Override
    Context createEmptyContext() {
        return new HashMapStackContext();
    }

}
