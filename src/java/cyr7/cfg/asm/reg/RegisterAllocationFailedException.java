package cyr7.cfg.asm.reg;

import cyr7.x86.reg_allocator.ASMGeneratorException;

public class RegisterAllocationFailedException extends ASMGeneratorException {

    private final int iterations;

    public RegisterAllocationFailedException(int iterations) {
        super("Maximum number of register allocation iterations reached.");
        this.iterations = iterations;
    }

}
