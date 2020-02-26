package cyr7.semantics;

import java.util.Objects;

public final class FunctionType {

    public final ExpandedType input;
    public final ExpandedType output;
    
    public FunctionType(ExpandedType input, ExpandedType output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionType that = (FunctionType) o;
        return Objects.equals(input, that.input) &&
            Objects.equals(output, that.output);
    }

    @Override
    public int hashCode() {
        return Objects.hash(input, output);
    }

    @Override
    public String toString() {
        return input.toString() + " -> " + output.toString();
    }
}
