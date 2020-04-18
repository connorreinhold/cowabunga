package cyr7.x86.asm;

import java.util.List;

public enum ASMInstrType implements ASMInstrTypeI {

    // Arithmetic
    ADDQ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    SUBQ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    IMULQ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    IDIVQ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    LEAQ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },

    // Logical
    ANDQ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    ORQ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    XORQ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },

    // Shift Operators
    SHLQ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    SHRQ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    SARQ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },

    // Data Transfer
    MOV {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    PUSHQ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    POPQ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    MOVABSQ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    MOVZX {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },

    // Control Flow
    CMPQ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    CALLQ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    RETQ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },

    // Jumps
    JMP {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    JE {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    JNE {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    JLE {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    JGE {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    JL {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    JG {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },

    // Set Instructions
    SETZ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(); }
    },
    SETNE {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    SETLE {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    SETL {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    SETG {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    SETGE {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },

    // cqo
    CQO {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    };

    public String getIntelOpCode() {
        return this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return "ASMInstrType [type=" + this.name() + "]";
    }

}
