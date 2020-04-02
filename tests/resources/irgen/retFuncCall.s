.intel_syntax noprefix
.globl _Imain_paai
.align 4
_Imain_paai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 64
_l0:
    mov r10, [ rbp + -24 ]
    mov r10, rdi ## (MOVE (TEMP args) (TEMP _ARG0))
    mov [ rbp + -24 ], r10
    callq _IgetPair_t2ii ## (CALL_STMT _t3 (NAME _IgetPair_t2ii))
    mov r10, [ rbp + -48 ]
    mov r10, rax ## (CALL_STMT _t3 (NAME _IgetPair_t2ii))
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -8 ]
    mov r10, rax ## (MOVE (TEMP a) (TEMP _RET0))
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -32 ]
    mov r10, rdx ## (MOVE (TEMP b) (TEMP _RET1))
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -64 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t4) (TEMP a))
    mov [ rbp + -8 ], r11
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -64 ]
    mov rdi, r10 ## (CALL_STMT _t5 (NAME _IunparseInt_aii) (TEMP _t4))
    mov [ rbp + -64 ], r10
    callq _IunparseInt_aii ## (CALL_STMT _t5 (NAME _IunparseInt_aii) (TEMP _t4))
    mov r10, [ rbp + -56 ]
    mov r10, rax ## (CALL_STMT _t5 (NAME _IunparseInt_aii) (TEMP _t4))
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t6) (TEMP _t5))
    mov [ rbp + -56 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -40 ]
    mov rdi, r10 ## (CALL_STMT _t7 (NAME _Iprint_pai) (TEMP _t6))
    mov [ rbp + -40 ], r10
    callq _Iprint_pai ## (CALL_STMT _t7 (NAME _Iprint_pai) (TEMP _t6))
    mov r10, [ rbp + -16 ]
    mov r10, rax ## (CALL_STMT _t7 (NAME _Iprint_pai) (TEMP _t6))
    mov [ rbp + -16 ], r10
    jmp end__Imain_paai ## (RETURN ())
end__Imain_paai:
    addq rsp, 64
    mov rsp, rbp
    popq rbp
    retq
_IgetNum_i:
    pushq rbp
    mov rbp, rsp
    subq rsp, 16
_l2:
    mov r10, [ rbp + -8 ]
    movabsq r10, 3 ## (CONST 3)
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t2) (CONST 3))
    mov [ rbp + -8 ], r11
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -16 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t2))
    mov [ rbp + -16 ], r10
    jmp end__IgetNum_i ## (RETURN ())
end__IgetNum_i:
    addq rsp, 16
    mov rsp, rbp
    popq rbp
    retq
_IgetPair_t2ii:
    pushq rbp
    mov rbp, rsp
    subq rsp, 32
_l1:
    mov r10, [ rbp + -32 ]
    movabsq r10, 4 ## (CONST 4)
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (MOVE (TEMP _t0) (CONST 4))
    mov [ rbp + -32 ], r11
    mov [ rbp + -24 ], r10
    callq _IgetNum_i ## (CALL_STMT _t8 (NAME _IgetNum_i))
    mov r10, [ rbp + -8 ]
    mov r10, rax ## (CALL_STMT _t8 (NAME _IgetNum_i))
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t1) (TEMP _t8))
    mov [ rbp + -8 ], r11
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -24 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t0))
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -16 ]
    mov rdx, r10 ## (MOVE (TEMP _RET1) (TEMP _t1))
    mov [ rbp + -16 ], r10
    jmp end__IgetPair_t2ii ## (RETURN ())
end__IgetPair_t2ii:
    addq rsp, 32
    mov rsp, rbp
    popq rbp
    retq
