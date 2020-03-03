package cyr7.ast;

import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class AbstractNode implements Node {

    private final Location location;

    public AbstractNode(Location location) {
        assert location != null;
        this.location = location;
    }

    @Override
    public Location getLocation() {
        return location;
    }
}
