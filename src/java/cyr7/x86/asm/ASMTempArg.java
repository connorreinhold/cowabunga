package cyr7.x86.asm;

import java.util.Objects;

public class ASMTempArg implements ASMArg {

    public final String name;

    public ASMTempArg(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ASMTempArg)) {
            return false;
        }
        ASMTempArg other = (ASMTempArg) obj;
        return Objects.equals(name, other.name);
    }

    @Override
    public String getIntelArg() {
        return this.name;
    }

    @Override
    public String toString() {
        return "ASMTempArg [name=" + name + "]";
    }
}
