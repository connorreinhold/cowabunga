package cyr7.ir;

public interface IRExpr extends IRNode {
    boolean isConstant();

    long constant();
}
