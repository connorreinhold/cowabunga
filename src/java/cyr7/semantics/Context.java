package cyr7.semantics;

import cyr7.exceptions.UnbalancedPushPopException;

import java.util.Optional;

/**
 * A type-checking context, also called a symbol table.
 */
public interface Context {

    void add(String id, VariableType t);

    void add(String id, FunctionType t);

    void add(ReturnType t);

    Optional<VariableType> getVar(String id);

    Optional<FunctionType> getFn(String id);

    Optional<ReturnType> getRet();

    /**
     * Push a new commit onto the context.
     *
     * Calls to push must be balanced with a corresponding call to pop.
     *
     * @return this context
     */
    Context push();

    /**
     * Pops a commit off this context
     *
     * Calls to push must be balanced with a corresponding call to pop. Calling
     * pop restores this context to the same state it was in when commit was
     * called.
     *
     * @return this context
     * @throws UnbalancedPushPopException if there are more calls to pop than
     * to push.
     */
    Context pop() throws UnbalancedPushPopException;

}
