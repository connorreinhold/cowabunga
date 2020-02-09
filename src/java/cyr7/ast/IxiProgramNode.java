package cyr7.ast;

import java.util.List;

public class IxiProgramNode extends ProgramNode{
	List<FunctionDeclNode> functionDeclarations;
	
	public IxiProgramNode(List<FunctionDeclNode> lst)  {
	    this.functionDeclarations = lst;
	}
	
}
