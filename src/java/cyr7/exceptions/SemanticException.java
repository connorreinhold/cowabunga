package cyr7.exceptions;

import java.util.Optional;

public class SemanticException extends RuntimeException {

    public SemanticException(String msg) {
        super(msg);
    }

    public SemanticException() {
        // TODO: Remove this and provide messages to all exceptions.
        super("");
    }

}
