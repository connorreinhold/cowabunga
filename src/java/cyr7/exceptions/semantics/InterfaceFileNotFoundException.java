package cyr7.exceptions.semantics;

import java_cup.runtime.ComplexSymbolFactory;

public class InterfaceFileNotFoundException extends SemanticException {

    /**
     *
     */
    private static final long serialVersionUID = 5737163481300461998L;

    public InterfaceFileNotFoundException(String interfaceFileName,
                                          ComplexSymbolFactory.Location loc) {
        super("Interface file not found: " + interfaceFileName, loc);
    }

}
