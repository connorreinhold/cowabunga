package cyr7.semantics;

public class VariableType implements ContextType {
	public final OrdinaryType type;

	public VariableType(OrdinaryType t) {
		this.type = t;
	}
}
