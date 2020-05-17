package cyr7.ir.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import cyr7.visitor.MyIRVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class IRPhiFuncNode extends IRExpr_c {

    private final List<String> arguments;

    public IRPhiFuncNode(Location location, String variable, int count) {
        super(location);
        final var args = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            args.add(variable);
        }
        this.arguments = Collections.unmodifiableList(args);
    }

    @Override
    public <T> T accept(MyIRVisitor<T> v) {
        throw new UnsupportedOperationException("This class is meant to be used with CFGs only.");
    }

    @Override
    public String userFriendlyString() {
        return "φ(" + String.join(", ", arguments) + ")";
    }

    @Override
    public String label() {
        return "Phi(" + String.join(", ", arguments) + ")";
    }

    @Override
    public void printSExp(SExpPrinter p) {
        throw new UnsupportedOperationException("This class is meant to be used with CFGs only.");
    }


    @Override
    public int hashCode() {
        return Objects.hash(arguments);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IRPhiFuncNode)) {
            return false;
        }
        IRPhiFuncNode other = (IRPhiFuncNode) obj;
        return Objects.equals(arguments, other.arguments);
    }

}
