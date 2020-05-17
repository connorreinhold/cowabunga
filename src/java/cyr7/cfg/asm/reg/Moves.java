package cyr7.cfg.asm.reg;

import cyr7.util.Sets;
import cyr7.x86.asm.ASMTempRegArg;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

final class Moves {

    private final Collection<Integer> worklistMoves;

    public Moves(Collection<Integer> worklistMoves) {
        this.worklistMoves = worklistMoves;
    }

    public final HashSet<Integer> active = new HashSet<>();
    public final HashSet<Integer> frozen = new HashSet<>();
    public final HashSet<Integer> constrained = new HashSet<>();
    public final HashSet<Integer> coalesced = new HashSet<>();

    private final Map<ASMTempRegArg, Set<Integer>> nodeToMoves = new HashMap<>();

    public void assoc(ASMTempRegArg n, int move) {
        if (nodeToMoves.containsKey(n)) {
            nodeToMoves.get(n).add(move);
        } else {
            Set<Integer> moves = new HashSet<>(1);
            moves.add(move);
            nodeToMoves.put(n, moves);
        }
    }

    public Set<Integer> getMoves(ASMTempRegArg n) {
        return Sets.intersection(
            nodeToMoves.getOrDefault(n, Collections.emptySet()),
            Sets.union(active, worklistMoves) );
    }

    public boolean nodeIsMoveRelated(ASMTempRegArg n) {
        return !getMoves(n).isEmpty();
    }

    public void merge(ASMTempRegArg into, ASMTempRegArg from) {
        // moveList[into] <- moveList[into] u moveList[v]
        for (int move : getMoves(from)) {
            assoc(into, move);
        }
    }

    public void clearMoveList() {
        nodeToMoves.clear();
    }

}
