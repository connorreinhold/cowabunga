package cyr7.semantics;

public class TestHashMapStackContextTest extends TestContext {

    @Override
    Context createEmptyContext() {
        return new HashMapStackContext();
    }

}
