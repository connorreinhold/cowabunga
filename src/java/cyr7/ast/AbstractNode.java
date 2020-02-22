package cyr7.ast;

import cyr7.visitor.AbstractVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.Optional;

public abstract class AbstractNode implements Node {

    private final Location location;

    public AbstractNode(Location location) {
        this.location = location;
    }

    public Optional<Location> getLocation() {
        return Optional.ofNullable(location);
    }

}
