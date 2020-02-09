package cyr7.ast;

public class FunctionArgNode {
	String identifier;
	TypeNode type;
	
	public FunctionArgNode(String id, TypeNode type) {
	    this.identifier = id;
	    this.type = type;
	}
}
