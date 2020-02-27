package cyr7.ast;

import cyr7.exceptions.LexerException;
import cyr7.exceptions.ParserException;
import cyr7.visitor.AbstractVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.Optional;

public interface Node {

    Optional<Location> getLocation();

    boolean equals(Object o);

    <T> T accept(AbstractVisitor<T> visitor);

}
