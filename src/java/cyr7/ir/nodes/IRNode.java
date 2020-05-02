package cyr7.ir.nodes;

import cyr7.ir.visit.AggregateVisitor;
import cyr7.ir.visit.CheckCanonicalIRVisitor;
import cyr7.ir.visit.CheckConstFoldedIRVisitor;
import cyr7.ir.visit.IRVisitor;
import cyr7.ir.visit.InsnMapsBuilder;
import cyr7.visitor.MyIRVisitor;
import cyr7.x86.tiler.TilerData;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

/**
 * A node in an intermediate-representation abstract syntax tree.
 */
public interface IRNode {

    /**
     * Visit the children of this IR node.
     * @param v the visitor
     * @return the result of visiting children of this node
     */
    IRNode visitChildren(IRVisitor v);

    <T> T aggregateChildren(AggregateVisitor<T> v);

    InsnMapsBuilder buildInsnMapsEnter(InsnMapsBuilder v);

    IRNode buildInsnMaps(InsnMapsBuilder v);

    CheckCanonicalIRVisitor checkCanonicalEnter(CheckCanonicalIRVisitor v);

    boolean isCanonical(CheckCanonicalIRVisitor v);

    boolean isConstFolded(CheckConstFoldedIRVisitor v);

    String label();

    <T> T accept(MyIRVisitor<T> v);

    boolean hasOptimalTiling();

    TilerData getOptimalTiling();

    void setOptimalTilingOnce(TilerData tilerData);

    /**
     * Print an S-expression representation of this IR node.
     * @param p the S-expression printer
     */
    void printSExp(SExpPrinter p);

    Location location();

    String userFriendlyString();

}
