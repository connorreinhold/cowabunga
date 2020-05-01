package cyr7.cfg.nodes;

import java.util.List;
import java.util.Objects;

import cyr7.cfg.dfa.BackwardTransferFunction;
import cyr7.cfg.dfa.ForwardTransferFunction;
import cyr7.ir.nodes.IRExpr;
import cyr7.cfg.visitor.CFGVisitor;
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
	public <T> T accept(CFGVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public <T> List<T> acceptForward(ForwardTransferFunction<T> transferFunction, T in) {
		return List.of(transferFunction.transfer(this, in));
	}

	@Override
	public <T> T acceptBackward(BackwardTransferFunction<T> transferFunction, T input) {
		return transferFunction.transfer(this, input);
	}

	@Override
	public String toString() {
		return "(" + variable + " = " + value.label() + ")";
	}
}
