package cyr7.x86.asm;

import java.util.List;

public enum ASMInstrType implements ASMInstrTypeI {

    // Arithmetic
    ADDQ {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(0, 1); }
    },
    SUBQ {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(0, 1); }
    },
    IMULQ {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(0, 1); }
    },
    IDIVQ {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(0, 1); }
    },
    LEAQ {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(1); }
    },

    // Logical
    ANDQ {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(0, 1); }
    },
    ORQ {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(0, 1); }
    },
    XORQ {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(0, 1); }
    },

    // Shift Operators
    SHLQ {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(0, 1); }
    },
    SHRQ {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(0, 1); }
    },
    SARQ {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(0, 1); }
    },

    // Data Transfer
    MOVQ {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(0, 1); }
    },
    MOV {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(0, 1); }
    },
    PUSHQ {
        @Override
        public List<Integer> defs() { return List.of(); }
        @Override
        public List<Integer> uses() { return List.of(0); }
    },
    POPQ {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(); }
    },
    MOVABSQ {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(0, 1); }
    },
    MOVZX {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(1); }
    },

    // Control Flow
    CMPQ {
        @Override
        public List<Integer> defs() { return List.of(); }
        @Override
        public List<Integer> uses() { return List.of(0, 1); }
    },
    CALLQ {
        @Override
        public List<Integer> defs() { return List.of(); }
        @Override
        public List<Integer> uses() { return List.of(1); }
    },
    RETQ {
        @Override
        public List<Integer> defs() { return List.of(); }
        @Override
        public List<Integer> uses() { return List.of(); }
    },

    // Jumps
    JMP {
        @Override
        public List<Integer> defs() { return List.of(); }
        @Override
        public List<Integer> uses() { return List.of(0); }
    },
    JE {
        @Override
        public List<Integer> defs() { return List.of(); }
        @Override
        public List<Integer> uses() { return List.of(0); }
    },
    JNE {
        @Override
        public List<Integer> defs() { return List.of(); }
        @Override
        public List<Integer> uses() { return List.of(0); }
    },
    JLE {
        @Override
        public List<Integer> defs() { return List.of(); }
        @Override
        public List<Integer> uses() { return List.of(0); }
    },
    JGE {
        @Override
        public List<Integer> defs() { return List.of(); }
        @Override
        public List<Integer> uses() { return List.of(0); }
    },
    JL {
        @Override
        public List<Integer> defs() { return List.of(); }
        @Override
        public List<Integer> uses() { return List.of(0); }
    },
    JG {
        @Override
        public List<Integer> defs() { return List.of(); }
        @Override
        public List<Integer> uses() { return List.of(0); }
    },

    // Set Instructions
    SETZ {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(); }
    },
    SETNE {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(); }
    },
    SETLE {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(); }
    },
    SETL {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(); }
    },
    SETG {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(); }
    },
    SETGE {
        @Override
        public List<Integer> defs() { return List.of(0); }
        @Override
        public List<Integer> uses() { return List.of(); }
    },

    // cqo
    CQO {
        @Override
        public List<Integer> defs() { return List.of(); }
        @Override
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
