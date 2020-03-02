package cyr7.ir.nodes;

public interface IRExpr extends IRNode {
    boolean isConstant();

    long constant();
}
