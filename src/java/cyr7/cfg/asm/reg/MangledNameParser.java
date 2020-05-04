package cyr7.cfg.asm.reg;

import cyr7.x86.asm.ASMReg;

import java.util.Set;

/**
 * Small utility class for parsing mangled names
 */
public enum MangledNameParser {
    ;

    /**
     * Return registers for the mangled name.
     */
    public static Set<ASMReg> returnRegisters(String mangledName) {
        return Set.of();
    }

    /**
     * The argument registers for a call to a function with the specified
     * mangled name, including the extra argument to store return values
     * beyond the 6th.
     */
    public static Set<ASMReg> argumentRegisters(String mangledName) {
        return Set.of();
    }

}
