package cyr7.visitor;

import cyr7.ast.Node;

public class PostOrderReduceTraversal<T, V extends AbstractVisitor<T> & PostOrderReduceTraversal.ReduceVisitor<T>> {

    public interface ReduceVisitor<T> {
        T unit();
        T combine(T left, T right);
    }

    private final V visitor;

    public PostOrderReduceTraversal(V visitor) {
        this.visitor = visitor;
    }

    public T traverse(Node n) {
        T value = visitor.unit();
        for (Node child : n.getChildren()) {
            value = visitor.combine(value, traverse(child));
        }
        value = visitor.combine(value, n.accept(visitor));
        return value;
    }

}
