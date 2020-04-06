package cyr7.ir;

/**
 * Generates temporary names, argument temps, return value temps, and label
 * names. No two names created by an instance of IdGenerator are the same.
 */
public interface IdGenerator {

    /**
     * Creates a new label.
     */
    String newLabel();

    /**
     * Creates a new temporary name.
     */
    String newTemp();

    /**
     * Generating a temp for the nth return value
     */
    String retTemp(int n);

    /**
     * Generating a temp for the nth argument.
     */
    String argTemp(int n);

}
