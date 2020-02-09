package cyr7.ast;

public class ArrayTypeNode implements TypeNode {
    TypeNode t;
    
    public ArrayTypeNode(TypeNode t) {
        this.t = t;
    }

    @Override
    public int getDimensions() {
        return 1 + t.getDimensions();
    }
    
}