package cyr7.x86.asm;

public class ASMRegArg extends ASMArg {

    public final Register register;

    public ASMRegArg(Register register) {
      this.register = register;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ASMRegArg that = (ASMRegArg) o;
        return Objects.equals(register, that.register);
    }

    @Override
    public String toString() {
        return register.toString();
    }

}