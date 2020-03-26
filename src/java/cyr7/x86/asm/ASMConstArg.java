package cyr7.x86.asm;

public class ASMConstArg implements ASMArg {
    public final long constant;

    public ASMConstArg(long constant) {
        this.constant = constant;
    }
}
