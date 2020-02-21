package cyr7.semantics;

import java.util.Collections;
import java.util.List;

public final class TupleType implements ExpandedType {
    public final List<ExpandedType> elements;

    public TupleType(List<ExpandedType> elements) {
        assert(elements.size() >= 2);
        this.elements = Collections.unmodifiableList(elements);
    }

    @Override
    public Type getType() {
        return Type.TUPLE;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TupleType) {
            TupleType tupleType = (TupleType) o;
            return elements.equals(tupleType.elements);
        }
        return false;
    }

}
