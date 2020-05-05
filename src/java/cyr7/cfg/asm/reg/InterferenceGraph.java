package cyr7.cfg.asm.reg;

import cyr7.util.Sets;
import cyr7.x86.asm.ASMTempRegArg;

import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class InterferenceGraph {

    private final Map<ASMTempRegArg, Set<ASMTempRegArg>> edges;
    public final Map<ASMTempRegArg, ASMTempRegArg> alias;

    private final Set<ASMTempRegArg> precolored;
    private final Deque<ASMTempRegArg> selectStack;
    private final Set<ASMTempRegArg> coalescedNodes;

    public InterferenceGraph(
        Set<ASMTempRegArg> precolored,
        Deque<ASMTempRegArg> selectStack,
        Set<ASMTempRegArg> coalescedNodes) {

        this.edges = new HashMap<>();
        this.alias = new HashMap<>();

        this.precolored = precolored;
        this.selectStack = selectStack;
        this.coalescedNodes = coalescedNodes;
    }

    public void addEdge(ASMTempRegArg lhs, ASMTempRegArg rhs) {
        addPartialEdge(lhs, rhs);
        addPartialEdge(rhs, lhs);
    }

    private void addPartialEdge(ASMTempRegArg from, ASMTempRegArg to) {
        if (this.edges.containsKey(from)) {
            this.edges.get(from).add(to);
        } else {
            HashSet<ASMTempRegArg> set = new HashSet<>(4);
            this.edges.put(from, set);
            set.add(to);
        }
    }

    public Set<ASMTempRegArg> adjacent(ASMTempRegArg node) {
        if (!edges.containsKey(node) || precolored.contains(node)) {
            return Collections.emptySet();
        }

        return Sets.difference(
            edges.get(node),
            Sets.union(selectStack, coalescedNodes));
    }

    public int degree(ASMTempRegArg node) {
        return adjacent(node).size();
    }

    public boolean adjacentSet(ASMTempRegArg lhs, ASMTempRegArg rhs) {
        boolean fromLeft = edges.getOrDefault(lhs, Collections.emptySet()).contains(rhs);
        assert fromLeft == edges.getOrDefault(rhs, Collections.emptySet()).contains(lhs);
        return fromLeft;
    }

    public ASMTempRegArg getAlias(ASMTempRegArg n) {
        if (coalescedNodes.contains(n)) {
            return getAlias(alias.get(n));
        } else {
            return n;
        }
    }

}
