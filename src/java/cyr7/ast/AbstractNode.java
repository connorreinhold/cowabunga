package cyr7.ast;

import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class AbstractNode implements INode {

    private final Location location;

    public AbstractNode(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
    
    public abstract boolean equals(Object o);

}
