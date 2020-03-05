package cyr7.ir.nodes;

import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.Objects;

/**
 * An intermediate representation for a temporary register
 * TEMP(name)
 */
public class IRTemp extends IRExpr_c {
    private String name;

    /**
     *
     * @param name name of this temporary register
     */
    public IRTemp(Location location, String name) {
        super(location);
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Override
    public String label() {
        return "TEMP(" + name + ")";
    }

    @Override
    public void printSExp(SExpPrinter p) {
        p.startList();
        p.printAtom("TEMP");
        p.printAtom(name);
        p.endList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IRTemp irTemp = (IRTemp) o;
        return Objects.equals(name, irTemp.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
