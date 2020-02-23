package cyr7.semantics;

import cyr7.exceptions.UnbalancedPushPopException;

import java.util.Optional;

/**
 * A type-checking context, also called a symbol table.
 */
public interface Context {

    void addVar(String id, OrdinaryType t);

    void addFn(String id, FunctionType t);

    void addRet(ExpandedType t);

    Optional<OrdinaryType> getVar(String id);

    Optional<FunctionType> getFn(String id);

    Optional<ExpandedType> getRet();

    /**
     * Returns {@code true} if there is a mapping with the symbol {@code id}.
     * Otherwise, returns {@code false}.
     * @param id
     */
    boolean contains(String id);

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
