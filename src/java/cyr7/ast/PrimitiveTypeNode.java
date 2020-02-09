package cyr7.ast;

public class PrimitiveTypeNode implements TypeNode {
    PrimitiveEnum type;    
    
    public PrimitiveTypeNode(PrimitiveEnum type) {
        this.type = type;
    }

    @Override
    public int getDimensions() {
        return 0;
    }
}
