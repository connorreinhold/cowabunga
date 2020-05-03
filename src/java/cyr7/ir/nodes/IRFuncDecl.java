package cyr7.ir.nodes;

import java.util.Objects;

import cyr7.ir.visit.AggregateVisitor;
import cyr7.ir.visit.IRVisitor;
import cyr7.ir.visit.InsnMapsBuilder;
import cyr7.semantics.types.FunctionType;
import cyr7.visitor.MyIRVisitor;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

/** An IR function declaration */
public class IRFuncDecl extends IRNode_c {
    private final String name;
    private final IRStmt body;
    private final FunctionType type;

    public IRFuncDecl(Location location, String name, IRStmt body,
            FunctionType type) {
        super(location);
        this.name = name;
        this.body = body;
        this.type = type;
    }

    public IRFuncDecl(Location location, String name, IRStmt body) {
        super(location);
        this.name = name;
        this.body = body;
        this.type = null;
    }

    public String name() {
        return name;
    }

    public IRStmt body() {
        return body;
    }

    public FunctionType type() {
        return this.type;
    }

    public int numOfArgs() {
        return this.type.input.getTypes().size();
    }

    public int numOfReturnValues() {
        return this.type.output.getTypes().size();
    }

    @Override
    public String label() {
        return "FUNC " + name;
    }

    @Override
    public IRNode visitChildren(IRVisitor v) {
        IRStmt stmt = (IRStmt) v.visit(this, body);

        if (stmt != body)
            return v.nodeFactory().IRFuncDecl(name, stmt, type);

        return this;
    }

    @Override
    public <T> T aggregateChildren(AggregateVisitor<T> v) {
        T result = v.unit();
        result = v.bind(result, v.visit(body));
        return result;
    }

    @Override
    public InsnMapsBuilder buildInsnMapsEnter(InsnMapsBuilder v) {
        v.addNameToCurrentIndex(name);
        v.addInsn(this);
        return v;
    }

    @Override
    public IRNode buildInsnMaps(InsnMapsBuilder v) {
        return this;
    }

    @Override
    public void printSExp(SExpPrinter p) {
        p.startList();
        p.printAtom("FUNC");
        p.printAtom(name);
        body.printSExp(p);
        p.endList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IRFuncDecl that = (IRFuncDecl) o;
        return Objects.equals(name, that.name) &&
            Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, body);
    }

    @Override
    public <T> T accept(MyIRVisitor<T> v) {
        return v.visit(this);
    }

    @Override
    public String userFriendlyString() {
        return "Function: " + this.name;
    }
}
