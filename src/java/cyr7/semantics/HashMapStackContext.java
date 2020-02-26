package cyr7.semantics;

import cyr7.exceptions.UnbalancedPushPopException;

import java.util.*;

/**
 * Implementation of a context as a stack of hash tables.
 */
public final class HashMapStackContext implements Context {

    private interface Sigma {
    }

    private final static class Var implements Sigma {

        public final OrdinaryType type;

        public Var(OrdinaryType t) {
            this.type = t;
        }

        @Override
        public String toString() {
            return type.toString();
        }
    }

    private final static class Fn implements Sigma {

        public final FunctionType type;

        public Fn(FunctionType t) {
            this.type = t;
        }

        @Override
        public String toString() {
            return type.toString();
        }

    }

    private final static class Ret implements Sigma {

        public final ExpandedType type;

        private Ret(ExpandedType type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Ret " + type.toString();
        }
    }

    // Invariant: stack is always non-empty
    // Abstraction function: the head of the deque is the "top" of the stack.
    // Read more here: https://docs.oracle.com/javase/7/docs/api/java/util/Deque.html

    // Why use Deque instead of Stack? Deque is preferred by Java implementers
    private final Deque<Map<Optional<String>, Sigma>> stack;

    /**
     * Instantiate an empty context
     */
    public HashMapStackContext() {
        stack = new LinkedList<>();
        stack.add(new HashMap<>());
    }

    private void add(Optional<String> id, Sigma type) {
        assert id != null;
        assert type != null;

        stack.peek().put(id, type);

//        System.out.println(this);
    }

    @Override
    public void addVar(String id, OrdinaryType t) {
        add(Optional.of(id), new Var(t));
    }

    @Override
    public void addFn(String id, FunctionType t) {
        add(Optional.of(id), new Fn(t));
    }

    @Override
    public void addRet(ExpandedType t) {
        add(Optional.empty(), new Ret(t));
    }

    private Optional<Sigma> get(Optional<String> id) {
        // the head of the list is the top of the stack
        for (Map<Optional<String>, Sigma> commit : stack) {
            Sigma val = commit.get(id);
            if (val != null) {
                return Optional.of(val);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<OrdinaryType> getVar(String id) {
        Optional<Sigma> val = get(Optional.of(id));
        if (val.isPresent() && val.get() instanceof Var) {
            return Optional.of(((Var) val.get()).type);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<FunctionType> getFn(String id) {
        Optional<Sigma> val = get(Optional.of(id));
        if (val.isPresent() && val.get() instanceof Fn) {
            return Optional.of(((Fn) val.get()).type);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ExpandedType> getRet() {
        Optional<Sigma> val = get(Optional.empty());
        if (val.isPresent() && val.get() instanceof Ret) {
            return Optional.of(((Ret) val.get()).type);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean contains(String id) {
        assert id != null;

        return stack.stream().anyMatch(c -> c.containsKey(Optional.of(id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Context push() {
        stack.push(new HashMap<>());
//        System.out.println(this);
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
//        System.out.println(this);

        return this;
    }

    @Override
    public String toString() {
        return stack.toString().replaceAll("Optional", "");
    }

}
