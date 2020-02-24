package cyr7.ast;

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
