package cyr7.cfg.nodes;

import java.util.List;
import cyr7.ir.nodes.IRExpr;
import cyr7.cfg.visitor.AbstractCFGVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class CFGVarAssignNode extends CFGNode {
	public final String variable;
	public final IRExpr value;
	private final CFGNode outNode;

	public CFGVarAssignNode(Location location, String variable, IRExpr value,
			CFGNode outNode) {
		super(location);
		this.variable = variable;
		this.value = value;
		this.outNode = outNode;

		this.updateIns();
	}

	@Override
	public List<CFGNode> out() {
		return List.of(outNode);
	}

	@Override
	public <T> T accept(AbstractCFGVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
