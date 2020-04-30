package cyr7.cfg.nodes;

import java.util.List;
import cyr7.cfg.visitor.AbstractCFGVisitor;

public abstract class CFGNode {
    public abstract List<CFGNode> in();
    public abstract List<CFGNode> out();
    public abstract <T> void accept(AbstractCFGVisitor<T> visitor);
}