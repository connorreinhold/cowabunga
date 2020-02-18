package cyr7.semantics;

import java.util.Collections;
import java.util.List;

public final class TupleType implements ExpandedType{
	public final List<ExpandedType> elements;
	
	public TupleType(List<ExpandedType> elements) {
		this.elements = Collections.unmodifiableList(elements);
	}

	@Override
	public boolean equals(Object o) {
	    if (o instanceof OrdinaryType && elements.size() == 1) {
	        OrdinaryType type = (OrdinaryType) o;
            return type.equals(elements.get(0));
	    }
	    if (o instanceof TupleType) {
	        TupleType tupleType = (TupleType) o;
	        return elements.equals(tupleType.elements);
	    }
	    return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

}
