package cyr7.x86.asm;

import java.util.List;

public class ASMInstr implements ASMLine {
    public final List<ASMArg> args;
    public final ASMInstrType type;

    protected ASMInstr(ASMInstrType type, List<ASMArg> args) {
        this.type = type;
        this.args = args;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
