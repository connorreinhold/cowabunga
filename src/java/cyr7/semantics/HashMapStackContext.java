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
    private final Deque<Map<Optional<String>, ContextType>> stack;

    /**
     * Instantiate an empty context
     */
    public HashMapStackContext() {
        stack = new LinkedList<>();
        stack.add(new HashMap<>());
    }

    private void add(Optional<String> id, ContextType type) {
        stack.peek().put(id, type);
    }


    @Override
    public void addVar(String id, OrdinaryType t) {

    }

    @Override
    public void addFn(String id, FunctionType t) {

    }

    @Override
    public void addRet(ExpandedType t) {

    }

    @Override
    public Optional<OrdinaryType> getVar(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<FunctionType> getFn(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<ExpandedType> getRet() {
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
