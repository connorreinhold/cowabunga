package cyr7.cfg.graph;

import cyr7.ir.nodes.IRFuncDecl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public abstract class Graph<T> {

    public class Node {
        public T t;
        public List<Node> in;
        public List<Node> out;
        private int id;

        public Node(T t, List<Node> in, List<Node> out) {
            this.id = id++;
            this.in = in;
            this.out = out;
        }

        public Node(T t) {
            this(t, List.of(), List.of());
        }
    }

    private Node startNode;
    private List<Node> notStartNodes;

    public Graph(T t) {
        this.startNode = new Node(t);
        this.notStartNodes = List.of();
    }

    protected void addEdge(Node from, Node to) {
        from.out.add(to);
        to.in.add(from);
    }

    public List<Node> getAllNodes() {
        return startNode == null? notStartNodes : List.of(startNode, startNode);
    }

    public void show(String path) throws IOException {
        FileWriter fw = new FileWriter(path);
        fw.write("digraph g {\n");
        fw.write("\tnode [shape=record];\n");
        fw.write("\tforcelabels=true;\n");
        fw.write("\n");

        int i = 0;
        HashMap<String, Node> IDNodeMap = new HashMap<>();
        for (Node n: getAllNodes()) {
            if (!IDNodeMap.containsKey(n)) {
                String id = "n_"+i;
                IDNodeMap.put(id, n);

                String label = n.t.toString();

                fw.write("\tid\t [label=\"" + label + "\"]\n");
                i++;
            }
        }
        fw.write("\n");


        fw.write("\n}\n");
        fw.close();
    }

}
