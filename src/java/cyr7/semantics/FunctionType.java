package cyr7.semantics;

public class FunctionType {
    public final ExpandedType input;
    public final ExpandedType output;
    
    public FunctionType(ExpandedType input, ExpandedType output) {
        this.input = input;
        this.output = output;
    }
}
