package cyr7.cli;

import java.util.Optional;

public enum Optimization {

    CF,
    REG,
    MC,
    CSE,
    ALG,
    COPY,
    DCE,
    INL,
    SR,
    LU,
    LICM,
    PRE,
    CP,
    VN;

    @Override
    public String toString() {
        switch(this) {
            case CF:
                return "Constant folding";
            case REG:
                return "Register allocation";
            case MC:
                return "Move coalescing (and register allocation";
            case CSE:
                return "Common subexpression elimination";
            case ALG:
                return "Algebraic optimizations";
            case COPY:
                return "Copy propagation";
            case DCE:
                return "Dead code elimination";
            case INL:
                return "Inlining";
            case SR:
                return "Strength reduction";
            case LU:
                return "Loop unrolling";
            case LICM:
                return "Loop-invariant code motion";
            case PRE:
                return "Partial redundancy elimination";
            case CP:
                return "Constant propagation";
            case VN:
                return "Local value numbering";
            default:
                return "";
        }
    }

    public static Optional<Optimization> parse(String s) {
        try {
            return Optional.of(Optimization.valueOf(s.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}