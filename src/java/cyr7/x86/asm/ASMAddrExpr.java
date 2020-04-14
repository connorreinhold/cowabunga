
package cyr7.x86.asm;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import cyr7.x86.visitor.AbstractASMVisitor;

public final class ASMAddrExpr {
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
        
        public static Optional<ScaleValues> fromConst(long constant) {
            switch ((int) constant) {
                case 1: return Optional.of(ONE);
                case 2: return Optional.of(TWO);
                case 4: return Optional.of(FOUR);
                case 8: return Optional.of(EIGHT);
                default: return Optional.empty();
            }
        }
    }

    public final Optional<ASMTempRegArg> base;
    public final ScaleValues scale;
    public final Optional<ASMTempRegArg> index;
    public final int displacement;

    public ASMAddrExpr(Optional<? extends ASMTempRegArg> base,
            ScaleValues scale, Optional<? extends ASMTempRegArg> index,
            int displacement) {
        this.base = base.map(b -> b);
        this.scale = scale;
        this.index = index.map(i -> i);
        this.displacement = displacement;
    }

    public ASMAddrExpr(ASMTempArg base) {
        this(Optional.of(base), ScaleValues.ONE, Optional.empty(), 0);
    }

    public String getIntelExpr() {
        List<String> terms = new LinkedList<>();
        base.ifPresent(b -> terms.add(b.getIntelArg()));
        index.ifPresent(i -> terms.add(this.scale + " * " + i.getIntelArg()));
        if (this.displacement != 0) {
            terms.add(String.valueOf(displacement));
        }
        return String.join(" + ", terms);
    }

    @Override
    public String toString() {
        return "ASMAddrExpr [base=" + base + ", displacement=" + displacement
                + ", index=" + index + ", scale=" + scale + "]";
    }

    public <R> R accept(AbstractASMVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ASMAddrExpr that = (ASMAddrExpr) o;
        return displacement == that.displacement &&
            Objects.equals(base, that.base) &&
            scale == that.scale &&
            Objects.equals(index, that.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, scale, index, displacement);
    }
}
