package cyr7.ast;

import java.util.List;

public class FunctionDeclNode {
    String identifier;
    List<FunctionArgNode> args;
    List<TypeNode> returnTypes;

    public FunctionDeclNode(String id, List<FunctionArgNode> args,
            List<TypeNode> returnTypes) {
        this.identifier = id;
        this.args = args;
        this.returnTypes = returnTypes;
    }
    
}
