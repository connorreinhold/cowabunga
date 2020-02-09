package cyr7.ast;

import java.util.List;

public class FunctionDeclNode {
	String identifier;
	List<FunctionArgNode> args;
	List<TypeNode> returnTypes;
}
