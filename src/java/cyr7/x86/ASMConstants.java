package cyr7.x86;

import cyr7.x86.asm.ASMReg;

public class ASMConstants {

    /**
     * The number of words from the base pointer of a callee function to the
     * memory holding the arguments pushed onto the stack by the caller
     * function.
     */
    public static int WORD_OFFSET_TO_ARGS = 2;

    public static final ASMReg[] RETURN_REGISTERS = {
        ASMReg.RAX,
        ASMReg.RDX
    };

    public static final ASMReg[] ARGUMENT_REGISTERS = {
        ASMReg.RDI,
        ASMReg.RSI,
        ASMReg.RDX,
        ASMReg.RCX,
        ASMReg.R8,
        ASMReg.R9
    };

    public static final ASMReg[] CALLEE_SAVED_REGISTERS = {
//        ASMReg.RBP,
        ASMReg.RBX,
        ASMReg.R12,
        ASMReg.R13,
        ASMReg.R14,
        ASMReg.R15
    };

    public static final ASMReg[] CALLER_SAVED_REGISTERS = {
        ASMReg.RCX,
        ASMReg.RSI,
        ASMReg.RDI,
        ASMReg.R8,
        ASMReg.R9,
        ASMReg.R10,
        ASMReg.R11,
        ASMReg.RSP
    };

    public static String returnLabel(String mangledName) {
        return "end_" + mangledName;
    }

}
