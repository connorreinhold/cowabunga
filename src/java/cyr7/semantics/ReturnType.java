package cyr7.semantics;

public class ReturnType implements ContextType{
	public final ExpandedType type;
	
	public ReturnType(ExpandedType type) {
		this.type = type;
	}
}
