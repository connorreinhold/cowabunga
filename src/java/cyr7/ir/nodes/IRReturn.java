package cyr7.ir.nodes;

import cyr7.ir.fold.MyIRVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

/** RETURN statement */
public class IRReturn extends IRStmt {

    public IRReturn(Location location) {
        super(location);
    }

    @Override
    public String label() {
        return "RETURN";
    }

    @Override
    public void printSExp(SExpPrinter p) {
        p.printAtom("RETURN");
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof IRReturn);
    }

    @Override
    public <T> T accept(MyIRVisitor<T> v) {
        return v.visit(this);
    }
}
