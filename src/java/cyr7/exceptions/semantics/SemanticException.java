package cyr7.exceptions.semantics;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class SemanticException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -2906712701112586812L;

    protected SemanticException(String msg, Location loc) {
        super(String.format("%d:%d error:%s", 
                loc.getLine(), loc.getColumn(), msg));
    }

}
