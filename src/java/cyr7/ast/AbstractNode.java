package cyr7.ast;

import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.Optional;

public abstract class AbstractNode {

    private final Location location;

    public AbstractNode(Location location) {
        this.location = location;
    }

    public Optional<Location> getLocation() {
        return Optional.ofNullable(location);
    }

    public abstract boolean equals(Object o);
    
    /**
     * @param printer - the printer object to output s-expressions to
     */
    public abstract void prettyPrint(SExpPrinter printer);

}
