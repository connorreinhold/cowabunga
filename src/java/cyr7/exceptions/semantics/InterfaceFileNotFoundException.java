package cyr7.exceptions.semantics;

import java_cup.runtime.ComplexSymbolFactory;

public class InterfaceFileNotFoundException extends SemanticException {

    public InterfaceFileNotFoundException(String interfaceFileName,
                                          ComplexSymbolFactory.Location loc) {
        super("Interface file not found: " + interfaceFileName, loc);
    }

}
