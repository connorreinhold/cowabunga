package cyr7.semantics;

import java.util.List;

public class TupleType implements ExpandedType{
	public final List<OrdinaryType> elements;
	
	public TupleType(List<OrdinaryType> elements) {
		this.elements = elements;
	}
}
