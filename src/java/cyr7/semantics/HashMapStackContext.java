package cyr7.semantics;

import cyr7.exceptions.UnbalancedPushPopException;

import java.util.*;

/**
 * Implementation of a context as a stack of hash tables.
 */
public final class HashMapStackContext implements Context {

    // Invariant: stack is always non-empty
    // Abstraction function: the head of the deque is the "top" of the stack.
    // Read more here: https://docs.oracle.com/javase/7/docs/api/java/util/Deque.html

    // Why use Deque instead of Stack? Deque is preferred by Java implementers
    private final Deque<Map<String, Type>> stack;

    /**
     * Instantiate an empty context
     */
    public HashMapStackContext() {
        stack = new LinkedList<>();
        stack.add(new HashMap<>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(String id, Type t) {
        assert id != null;
        assert t != null;

        stack.peek().put(id, t);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Type> get(String id) {
        assert id != null;

        // iterate over the stack from top to bottom
        for (Map<String, Type> level : stack) {
            Type result = level.get(id);
            if (result != null) {
                return Optional.of(result);
            }
        }

        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Context push() {
        stack.push(new HashMap<>());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Context pop() throws UnbalancedPushPopException {
        if (stack.size() == 1) {
            // we're about to pop the last element off the stack
            throw new UnbalancedPushPopException("Unbalanced pop call made");
        }
        stack.pop();

        return this;
    }

    @Override
    public String toString() {
        return stack.toString();
    }

}
