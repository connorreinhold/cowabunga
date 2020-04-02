.intel_syntax noprefix
.globl _Imain_paai
.align 4
_Imain_paai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 32
_l3:
    mov r10, [ rbp + -8 ]
    mov r10, rdi ## (MOVE (TEMP a) (TEMP _ARG0))
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -32 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (MOVE (TEMP _t2) (CONST 1))
    mov [ rbp + -32 ], r11
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -24 ]
    mov rdi, r10 ## (CALL_STMT _t3 (NAME _If_ib) (TEMP _t2))
    mov [ rbp + -24 ], r10
    callq _If_ib ## (CALL_STMT _t3 (NAME _If_ib) (TEMP _t2))
    mov r10, [ rbp + -16 ]
    mov r10, rax ## (CALL_STMT _t3 (NAME _If_ib) (TEMP _t2))
    mov [ rbp + -16 ], r10
    jmp end__Imain_paai ## (RETURN ())
end__Imain_paai:
    addq rsp, 32
    mov rsp, rbp
    popq rbp
    retq
_If_ib:
    pushq rbp
    mov rbp, rsp
    subq rsp, 40
_l4:
    mov r10, [ rbp + -16 ]
    mov r10, rdi ## (MOVE (TEMP x) (TEMP _ARG0))
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -16 ]
    cmpq r10, 1 ## (CJUMP (TEMP x) _l0)
    mov [ rbp + -16 ], r10
    je _l0 ## (CJUMP (TEMP x) _l0)
_l1:
    mov r10, [ rbp + -40 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -40 ]
    mov r10, r11 ## (MOVE (TEMP _t0) (CONST 1))
    mov [ rbp + -40 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -32 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t0))
    mov [ rbp + -32 ], r10
    jmp end__If_ib ## (RETURN ())
_l5:
_l2:
    jmp end__If_ib ## (RETURN ())
_l0:
    mov r10, [ rbp + -8 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t1) (CONST 0))
    mov [ rbp + -8 ], r11
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -24 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t1))
    mov [ rbp + -24 ], r10
    jmp end__If_ib ## (RETURN ())
end__If_ib:
    addq rsp, 40
    mov rsp, rbp
    popq rbp
    retq
