package cyr7.cfg.asm.reg;

import cyr7.util.Pair;
import cyr7.util.Sets;
import cyr7.x86.asm.ASMTempRegArg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class InterferenceGraph {

    private final Set<Pair<ASMTempRegArg, ASMTempRegArg>> adjSet = new HashSet<>();
    private final Map<ASMTempRegArg, List<ASMTempRegArg>> adjList = new HashMap<>();
    private final Map<ASMTempRegArg, Integer> degree = new HashMap<>();

    private final Set<ASMTempRegArg> precolored;
    private final Deque<ASMTempRegArg> selectStack;
    private final Set<ASMTempRegArg> coalescedNodes;

    public InterferenceGraph(
        Set<ASMTempRegArg> precolored,
        Deque<ASMTempRegArg> selectStack,
        Set<ASMTempRegArg> coalescedNodes) {

        this.precolored = precolored;
        this.selectStack = selectStack;
        this.coalescedNodes = coalescedNodes;
    }

    void addEdge(ASMTempRegArg u, ASMTempRegArg v) {
        if (!adjSet.contains(new Pair<>(u, v)) && !u.equals(v)) {
            adjSet.add(new Pair<>(u, v));
            adjSet.add(new Pair<>(v, u));

            if (!precolored.contains(u)) {
                if (adjList.containsKey(u)) {
                    adjList.get(u).add(v);
                    degree.put(u, degree.get(u) + 1);
                } else {
                    ArrayList<ASMTempRegArg> list = new ArrayList<>();
                    list.add(v);
                    adjList.put(u, list);
                    degree.put(u, 1);
                }
            }

            if (!precolored.contains(v)) {
                if (adjList.containsKey(v)) {
                    adjList.get(v).add(u);
                    degree.put(v, degree.get(v) + 1);
                } else {
                    ArrayList<ASMTempRegArg> list = new ArrayList<>();
                    list.add(u);
                    adjList.put(v, list);
                    degree.put(v, 1);
                }
            }
        }
    }

    public List<ASMTempRegArg> adjList(ASMTempRegArg node) {
        return adjList.getOrDefault(node, Collections.emptyList());
    }

    public List<ASMTempRegArg> adjacent(ASMTempRegArg node) {
        List<ASMTempRegArg> list = new ArrayList<>(adjList(node));
        list.removeAll(selectStack);
        list.removeAll(coalescedNodes);
        return list;
    }

    public int degree(ASMTempRegArg node) {
        return degree.getOrDefault(node, 0);
    }

    public int decrementDegree(ASMTempRegArg node) {
        int deg = degree.getOrDefault(node, 0);
        degree.put(node, deg - 1);
        return deg;
    }

    public boolean adjacentSet(ASMTempRegArg lhs, ASMTempRegArg rhs) {
        boolean result = adjSet.contains(new Pair<>(lhs, rhs));
        assert result == adjSet.contains(new Pair<>(rhs, lhs));
        return result;
    }

}

//final class InterferenceGraph {
//
//    private final Map<ASMTempRegArg, Set<ASMTempRegArg>> edges;
//
//    private final Set<ASMTempRegArg> precolored;
//    private final Deque<ASMTempRegArg> selectStack;
//    private final Set<ASMTempRegArg> coalescedNodes;
//
//    private final Map<ASMTempRegArg, Integer> degree;
//
//    public InterferenceGraph(
//        Set<ASMTempRegArg> precolored,
//        Deque<ASMTempRegArg> selectStack,
//        Set<ASMTempRegArg> coalescedNodes) {
//
//        this.edges = new HashMap<>();
//
//        this.precolored = precolored;
//        this.selectStack = selectStack;
//        this.coalescedNodes = coalescedNodes;
//        this.degree = new HashMap<>();
//    }
//
//    public void addEdge(ASMTempRegArg lhs, ASMTempRegArg rhs) {
//        if (lhs.equals(rhs) || adjacentSet(lhs, rhs)) {
//            return;
//        }
//
//        addPartialEdge(lhs, rhs);
//        addPartialEdge(rhs, lhs);
//    }
//
//    private void addPartialEdge(ASMTempRegArg from, ASMTempRegArg to) {
//        if (this.edges.containsKey(from)) {
//            this.edges.get(from).add(to);
//        } else {
//            HashSet<ASMTempRegArg> set = new HashSet<>(4);
//            this.edges.put(from, set);
//            set.add(to);
//        }
//
//        if (!precolored.contains(from)) {
//            int deg = degree.getOrDefault(from, 0);
//            degree.put(from, deg + 1);
//        }
//    }
//
//    public Set<ASMTempRegArg> adjList(ASMTempRegArg node) {
//        if (!edges.containsKey(node) || precolored.contains(node)) {
//            return Collections.emptySet();
//        }
//
//        return edges.get(node);
//    }
//
//    public set<asmtempregarg> adjacent(asmtempregarg node) {
//        if (!edges.containskey(node) || precolored.contains(node)) {
//            return collections.emptyset();
//        }
//
//        return sets.difference(
//            edges.get(node),
//            sets.union(selectstack, coalescednodes));
//    }
//
//    public int degree(asmtempregarg node) {
//        return degree.getordefault(node, 0);
//    }
//
//    public int decrementdegree(asmtempregarg node) {
//        int deg = degree.getordefault(node, 0);
//        degree.put(node, deg - 1);
//        return deg;
//    }
//
//    public int degree(asmtempregarg node) {
//        return adjacent(node).size();
//    }
//
//    public boolean adjacentset(asmtempregarg lhs, asmtempregarg rhs) {
//        boolean fromleft = edges.getordefault(lhs, collections.emptyset()).contains(rhs);
//        assert fromleft == edges.getordefault(rhs, collections.emptyset()).contains(lhs);
//        return fromleft;
//    }
//
//}
