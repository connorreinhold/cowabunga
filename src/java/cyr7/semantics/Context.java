package cyr7.semantics;

import cyr7.exceptions.UnbalancedPushPopException;

import java.util.Optional;

/**
 * A type-checking context, also called a symbol table.
 */
public interface Context {

    /**
     * Associates an identifier to a type in this context
     *
     * @param id An identifier
     * @param t A type to associate with {@code id}
     */
    void add(String id, ContextType t);

    /**
     * Gets the type of an identifier in this context
     *
     * @param id An id
     * @return The type of id, or {@code Optional.empty()} if id is not in this
     * context
     */
    Optional<ContextType> get(String id);

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
