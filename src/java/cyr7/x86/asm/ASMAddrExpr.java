
package cyr7.x86.asm;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import cyr7.x86.visitor.AbstractASMVisitor;

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

    public final Optional<ASMTempRegArg> base;
    public final ScaleValues scale;
    public final Optional<ASMTempRegArg> index;
    public final int displacement;

    public ASMAddrExpr(Optional<ASMTempRegArg> base, ScaleValues scale,
            Optional<ASMTempRegArg> index, int displacement) {
        this.base = base;
        this.scale = scale;
        this.index = index;
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

}
