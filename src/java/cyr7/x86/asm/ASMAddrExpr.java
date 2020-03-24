
package cyr7.x86.asm;

import java.util.Optional;

public class ASMAddrExpr {
    // General Memory Access: [base + scale * index + displacement]

    public enum ScaleValues {
        ONE, TWO, FOUR, EIGHT;

        @Override
        public String toString() {
            switch (this) {
                case ONE:
                    return "1";
                case TWO:
                    return "2";
                case FOUR:
                    return "4";
                case EIGHT:
                    return "8";
                default:
                    return "";
            }
        }
    }

    public final Optional<Register> base;
    public final ScaleValues scale;
    public final Optional<Register> index;
    public final int displacement;

    public ASMAddrExpr(Optional<Register> base, ScaleValues scale, Optional<Register> index, int displacement) {
        this.base = base;
        this.scale = scale;
        this.index = index;
        this.displacement = displacement;
    }

    @Override
    public String toString() {
        String baseString = base.isPresent() ? base.toString() : "";
        String indexString = index.isPresent() ? index.toString() : "1";
        return "[" + baseString + scale + " * " + indexString + " + " + displacement + "]";
    }
}
