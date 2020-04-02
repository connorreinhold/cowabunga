.intel_syntax noprefix
.globl _Imain_paai
.align 4
_Imain_paai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 48
_l4:
    mov r10, [ rbp + -16 ]
    mov r10, rdi ## (MOVE (TEMP args) (TEMP _ARG0))
    mov [ rbp + -16 ], r10
    callq _Ifib_i ## (CALL_STMT _t4 (NAME _Ifib_i))
    mov r10, [ rbp + -48 ]
    mov r10, rax ## (CALL_STMT _t4 (NAME _Ifib_i))
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -48 ]
    mov r10, r11 ## (MOVE (TEMP _t5) (TEMP _t4))
    mov [ rbp + -48 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -40 ]
    mov rdi, r10 ## (CALL_STMT _t6 (NAME _IunparseInt_aii) (TEMP _t5))
    mov [ rbp + -40 ], r10
    callq _IunparseInt_aii ## (CALL_STMT _t6 (NAME _IunparseInt_aii) (TEMP _t5))
    mov r10, [ rbp + -24 ]
    mov r10, rax ## (CALL_STMT _t6 (NAME _IunparseInt_aii) (TEMP _t5))
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -8 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (MOVE (TEMP _t7) (TEMP _t6))
    mov [ rbp + -24 ], r11
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -8 ]
    mov rdi, r10 ## (CALL_STMT _t8 (NAME _Iprint_pai) (TEMP _t7))
    mov [ rbp + -8 ], r10
    callq _Iprint_pai ## (CALL_STMT _t8 (NAME _Iprint_pai) (TEMP _t7))
    mov r10, [ rbp + -32 ]
    mov r10, rax ## (CALL_STMT _t8 (NAME _Iprint_pai) (TEMP _t7))
    mov [ rbp + -32 ], r10
    jmp end__Imain_paai ## (RETURN ())
end__Imain_paai:
    addq rsp, 48
    mov rsp, rbp
    popq rbp
    retq
_Ifib_i:
    pushq rbp
    mov rbp, rsp
    subq rsp, 136
_l3:
    mov r10, [ rbp + -8 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP a1) (CONST 0))
    mov [ rbp + -8 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -136 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -136 ]
    mov r10, r11 ## (MOVE (TEMP a2) (CONST 1))
    mov [ rbp + -136 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -72 ]
    movabsq r10, 5 ## (CONST 5)
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -72 ]
    mov r10, r11 ## (MOVE (TEMP fibNum) (CONST 5))
    mov [ rbp + -72 ], r11
    mov [ rbp + -16 ], r10
_l0:
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t1) (TEMP fibNum))
    mov [ rbp + -16 ], r11
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -80 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -80 ]
    cmpq r10, r11 ## (GT (TEMP _t1) (CONST 0))
    mov [ rbp + -80 ], r11
    mov [ rbp + -88 ], r10
    pushq rax
    mov rax, [ rbp + -104 ]
    setg al ## (GT (TEMP _t1) (CONST 0))
    mov [ rbp + -104 ], rax
    popq rax
    mov r10, [ rbp + -96 ]
    pushq rax
    mov rax, [ rbp + -104 ]
    movzx r10, al ## (GT (TEMP _t1) (CONST 0))
    mov [ rbp + -104 ], rax
    popq rax
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -96 ]
    cmpq r10, 1 ## (CJUMP (GT (TEMP _t1) (CONST 0)) _l1)
    mov [ rbp + -96 ], r10
    je _l1 ## (CJUMP (GT (TEMP _t1) (CONST 0)) _l1)
_l2:
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -40 ]
    mov r10, r11 ## (MOVE (TEMP _t0) (TEMP a2))
    mov [ rbp + -40 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -112 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t0))
    mov [ rbp + -112 ], r10
    jmp end__Ifib_i ## (RETURN ())
_l1:
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t2) (TEMP fibNum))
    mov [ rbp + -16 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -48 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -128 ]
    mov r10, r11 ## (SUB (TEMP _t2) (CONST 1))
    mov [ rbp + -128 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -48 ]
    subq r10, r11 ## (SUB (TEMP _t2) (CONST 1))
    mov [ rbp + -48 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP fibNum) (SUB (TEMP _t2) (CONST 1)))
    mov [ rbp + -56 ], r11
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -40 ]
    mov r10, r11 ## (MOVE (TEMP temp) (TEMP a2))
    mov [ rbp + -40 ], r11
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (MOVE (TEMP _t3) (TEMP a1))
    mov [ rbp + -32 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -64 ]
    mov r11, [ rbp + -120 ]
    mov r10, r11 ## (ADD (TEMP _t3) (TEMP a2))
    mov [ rbp + -120 ], r11
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -64 ]
    mov r11, [ rbp + -40 ]
    addq r10, r11 ## (ADD (TEMP _t3) (TEMP a2))
    mov [ rbp + -40 ], r11
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -64 ]
    mov r10, r11 ## (MOVE (TEMP a2) (ADD (TEMP _t3) (TEMP a2)))
    mov [ rbp + -64 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (MOVE (TEMP a1) (TEMP temp))
    mov [ rbp + -24 ], r11
    mov [ rbp + -32 ], r10
    jmp _l0 ## (JUMP (NAME _l0))
end__Ifib_i:
    addq rsp, 136
    mov rsp, rbp
    popq rbp
    retq
