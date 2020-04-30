package cyr7.cfg.nodes;

import java.util.List;
import cyr7.ir.nodes.IRExpr;
import cyr7.cfg.visitor.AbstractCFGVisitor;

public class CFGVarAssignNode extends CFGNode {
	private final List<CFGNode> in;
	public final String variable;
	public final IRExpr value;
	private final CFGNode outNode;

	public CFGVarAssignNode(List<CFGNode> in, String variable, IRExpr value,
			CFGNode outNode) {
		this.in = in;
		this.variable = variable;
		this.value = value;
		this.outNode = outNode;
	}

	@Override
	public List<CFGNode> in() {
		return in;
	}

	@Override
	public List<CFGNode> out() {
		return List.of(outNode);
	}

	@Override
	public <T> void accept(AbstractCFGVisitor<T> visitor) {
		visitor.visit(this);
	}

}
