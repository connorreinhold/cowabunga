package cyr7.ast;

import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public interface Node {

    Location getLocation();

    boolean equals(Object o);

    <T> T accept(AbstractVisitor<T> visitor);

}
