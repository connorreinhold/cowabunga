package cyr7.semantics;

import java.util.Optional;

public interface Context {

    void add(String id, Type t);

    Optional<Type> get(String id);

    Context push();

    Context pop();

}
