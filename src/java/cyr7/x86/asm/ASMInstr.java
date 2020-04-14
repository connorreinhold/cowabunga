package cyr7.x86.asm;

import cyr7.ir.nodes.IRNode;
import cyr7.x86.visitor.AbstractASMVisitor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public final class ASMInstr implements ASMLine {
    public final List<ASMArg> args;
    public final ASMInstrType type;
    public final Optional<IRNode> source;

    protected ASMInstr(ASMInstrType type, List<ASMArg> args, Optional<IRNode> source) {
        this.type = type;
        this.args = args;
        this.source = source;
    }

    public ASMInstr withArgsReplaced(List<ASMArg> newArgs) {
        return new ASMInstr(type, newArgs, source);
    }

    @Override
    public String getIntelAssembly() {
        String opCode = type.getIntelOpCode();
        String arguments = String.join(", ", args.stream().map(a -> a.getIntelArg()).collect(Collectors.toList()));
        String src = source.map(x -> x.toString().replace("\n", "")).orElse(null);
        return "    " + opCode + (arguments.length() != 0 ? " " + arguments : "") + (src == null ? "" : " ## " + src);
    }

    @Override
    public String toString() {
        return "ASMInstr [args=" + args + ", type=" + type + "]";
    }

    @Override
    public <R> R accept(AbstractASMVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ASMInstr asmInstr = (ASMInstr) o;
        return Objects.equals(args, asmInstr.args) &&
            type == asmInstr.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(args, type);
    }
}
