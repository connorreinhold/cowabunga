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
        public List<Integer> uses() { return List.of(1); }
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
        public List<Integer> defs() { return List.of(); }
        public List<Integer> uses() { return List.of(0); }
    },
    POPQ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(); }
    },
    MOVABSQ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    MOVZX {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(1); }
    },

    // Control Flow
    CMPQ {
        public List<Integer> defs() { return List.of(); }
        public List<Integer> uses() { return List.of(0, 1); }
    },
    CALLQ {
        public List<Integer> defs() { return List.of(); }
        public List<Integer> uses() { return List.of(1); }
    },
    RETQ {
        public List<Integer> defs() { return List.of(); }
        public List<Integer> uses() { return List.of(); }
    },

    // Jumps
    JMP {
        public List<Integer> defs() { return List.of(); }
        public List<Integer> uses() { return List.of(0); }
    },
    JE {
        public List<Integer> defs() { return List.of(); }
        public List<Integer> uses() { return List.of(0); }
    },
    JNE {
        public List<Integer> defs() { return List.of(); }
        public List<Integer> uses() { return List.of(0); }
    },
    JLE {
        public List<Integer> defs() { return List.of(); }
        public List<Integer> uses() { return List.of(0); }
    },
    JGE {
        public List<Integer> defs() { return List.of(); }
        public List<Integer> uses() { return List.of(0); }
    },
    JL {
        public List<Integer> defs() { return List.of(); }
        public List<Integer> uses() { return List.of(0); }
    },
    JG {
        public List<Integer> defs() { return List.of(); }
        public List<Integer> uses() { return List.of(0); }
    },

    // Set Instructions
    SETZ {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(); }
    },
    SETNE {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(); }
    },
    SETLE {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(); }
    },
    SETL {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(); }
    },
    SETG {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(); }
    },
    SETGE {
        public List<Integer> defs() { return List.of(0); }
        public List<Integer> uses() { return List.of(); }
    },

    // cqo
    CQO {
        public List<Integer> defs() { return List.of(); }
        public List<Integer> uses() { return List.of(); }
    };

    public String getIntelOpCode() {
        return this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return "ASMInstrType [type=" + this.name() + "]";
    }

}
