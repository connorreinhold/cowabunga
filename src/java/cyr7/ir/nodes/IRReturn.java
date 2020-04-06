package cyr7.ir.nodes;

import cyr7.visitor.MyIRVisitor;
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
        p.startList();
        p.printAtom("RETURN");

        /*
        Update (4/6/2020): Don't include parentheses because course staff
        updated the IR grammar ¯\_(ツ)_/¯

        // Empty list because the s-exp parser is expecting one
        p.startList();
        p.endList();
         */

        p.endList();
    }

    @Override
    public int hashCode() {
        return IRReturn.class.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IRReturn)) {
            return false;
        }
        return true;
    }

    @Override
    public <T> T accept(MyIRVisitor<T> v) {
        return v.visit(this);
    }
}
