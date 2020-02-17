package cyr7.semantics;

public class VariableType implements ContextType {
	OrdinaryType type;

	public VariableType(OrdinaryType t) {
		this.type = t;
	}
}
