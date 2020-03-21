package cyr7.x86.asm;

import java.util.List;

public abstract class ASMInstr {
    final List<ASMArg> args;
    final ASMInstrType type;

    protected ASMInstr(ASMInstrType type, List<ASMArg> args) {
        this.type = type;
        this.args = args;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
