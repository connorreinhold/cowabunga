package cyr7.cfg.nodes;

import java.util.List;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRNode;
import cyr7.cfg.visitor.AbstractCFGVisitor;

public class CFGVarAssignNode extends CFGNode {
	public final String variable;
	public final IRExpr value;
	private final CFGNode outNode;

	public CFGVarAssignNode(IRNode source, String variable, IRExpr value,
			CFGNode outNode) {
		super(source);
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
	public <T> void accept(AbstractCFGVisitor<T> visitor) {
		visitor.visit(this);
	}

}
