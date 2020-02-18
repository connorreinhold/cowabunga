package cyr7.semantics;

import java.util.List;

public final class TupleType implements ExpandedType{
	public final List<OrdinaryType> elements;
	
	public TupleType(List<OrdinaryType> elements) {
		this.elements = elements;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TupleType tupleType = (TupleType) o;
		return elements.equals(tupleType.elements);
	}

	@Override
	public int hashCode() {
		return 0;
	}

}
