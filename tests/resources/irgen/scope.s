.intel_syntax noprefix
.globl _Imain_paai
.align 4
_If_p:
    pushq rbp
    mov rbp, rsp
    subq rsp, 40
_l1:
    mov r10, [ rbp + -16 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP i) (CONST 0))
    mov [ rbp + -16 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (MOVE (TEMP _t5) (TEMP i))
    mov [ rbp + -32 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -8 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -40 ]
    mov r10, r11 ## (ADD (TEMP _t5) (CONST 1))
    mov [ rbp + -40 ], r11
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -8 ]
    addq r10, r11 ## (ADD (TEMP _t5) (CONST 1))
    mov [ rbp + -8 ], r11
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (MOVE (TEMP i) (ADD (TEMP _t5) (CONST 1)))
    mov [ rbp + -24 ], r11
    mov [ rbp + -32 ], r10
    jmp end__If_p ## (RETURN ())
end__If_p:
    addq rsp, 40
    mov rsp, rbp
    popq rbp
    retq
_Imain_paai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 64
_l0:
    mov r10, [ rbp + -8 ]
    mov r10, rdi ## (MOVE (TEMP args) (TEMP _ARG0))
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -16 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP i) (CONST 0))
    mov [ rbp + -16 ], r11
    mov [ rbp + -24 ], r10
    callq _If_p ## (CALL_STMT _t0 (NAME _If_p))
    mov r10, [ rbp + -40 ]
    mov r10, rax ## (CALL_STMT _t0 (NAME _If_p))
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (MOVE (TEMP _t1) (TEMP i))
    mov [ rbp + -24 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -32 ]
    mov rdi, r10 ## (CALL_STMT _t2 (NAME _IunparseInt_aii) (TEMP _t1))
    mov [ rbp + -32 ], r10
    callq _IunparseInt_aii ## (CALL_STMT _t2 (NAME _IunparseInt_aii) (TEMP _t1))
    mov r10, [ rbp + -56 ]
    mov r10, rax ## (CALL_STMT _t2 (NAME _IunparseInt_aii) (TEMP _t1))
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t3) (TEMP _t2))
    mov [ rbp + -56 ], r11
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -48 ]
    mov rdi, r10 ## (CALL_STMT _t4 (NAME _Iprint_pai) (TEMP _t3))
    mov [ rbp + -48 ], r10
    callq _Iprint_pai ## (CALL_STMT _t4 (NAME _Iprint_pai) (TEMP _t3))
    mov r10, [ rbp + -64 ]
    mov r10, rax ## (CALL_STMT _t4 (NAME _Iprint_pai) (TEMP _t3))
    mov [ rbp + -64 ], r10
    jmp end__Imain_paai ## (RETURN ())
end__Imain_paai:
    addq rsp, 64
    mov rsp, rbp
    popq rbp
    retq
