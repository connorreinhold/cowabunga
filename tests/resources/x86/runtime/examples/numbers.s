.intel_syntax noprefix
.globl _Imain_paai
.align 4
_Imain_paai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 112
_l0:
    mov r10, [ rbp + -56 ]
    mov r10, rdi ## (MOVE (TEMP args) (TEMP _ARG0))
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -104 ]
    movabsq r10, 23 ## (CONST 23)
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -104 ]
    mov r10, r11 ## (MOVE (TEMP i) (CONST 23))
    mov [ rbp + -104 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -72 ]
    mov r11, [ rbp + -40 ]
    mov r10, r11 ## (MOVE (TEMP _t1) (TEMP i))
    mov [ rbp + -40 ], r11
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -72 ]
    mov rdi, r10 ## (CALL_STMT _t2 (NAME _IunparseInt_aii) (TEMP _t1))
    mov [ rbp + -72 ], r10
    callq _IunparseInt_aii ## (CALL_STMT _t2 (NAME _IunparseInt_aii) (TEMP _t1))
    mov r10, [ rbp + -88 ]
    mov r10, rax ## (CALL_STMT _t2 (NAME _IunparseInt_aii) (TEMP _t1))
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -88 ]
    mov r10, r11 ## (MOVE (TEMP unparsed) (TEMP _t2))
    mov [ rbp + -88 ], r11
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -48 ]
    mov r10, r11 ## (MOVE (TEMP _t3) (TEMP unparsed))
    mov [ rbp + -48 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -80 ]
    mov rdi, r10 ## (CALL_STMT _t4 (NAME _Iprintln_pai) (TEMP _t3))
    mov [ rbp + -80 ], r10
    callq _Iprintln_pai ## (CALL_STMT _t4 (NAME _Iprintln_pai) (TEMP _t3))
    mov r10, [ rbp + -112 ]
    mov r10, rax ## (CALL_STMT _t4 (NAME _Iprintln_pai) (TEMP _t3))
    mov [ rbp + -112 ], r10
    callq _Igenerate_i ## (CALL_STMT _t5 (NAME _Igenerate_i))
    mov r10, [ rbp + -96 ]
    mov r10, rax ## (CALL_STMT _t5 (NAME _Igenerate_i))
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -64 ]
    mov r11, [ rbp + -96 ]
    mov r10, r11 ## (MOVE (TEMP generated) (TEMP _t5))
    mov [ rbp + -96 ], r11
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -64 ]
    mov r10, r11 ## (MOVE (TEMP _t6) (TEMP generated))
    mov [ rbp + -64 ], r11
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -16 ]
    mov rdi, r10 ## (CALL_STMT _t7 (NAME _IunparseInt_aii) (TEMP _t6))
    mov [ rbp + -16 ], r10
    callq _IunparseInt_aii ## (CALL_STMT _t7 (NAME _IunparseInt_aii) (TEMP _t6))
    mov r10, [ rbp + -8 ]
    mov r10, rax ## (CALL_STMT _t7 (NAME _IunparseInt_aii) (TEMP _t6))
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t8) (TEMP _t7))
    mov [ rbp + -8 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -32 ]
    mov rdi, r10 ## (CALL_STMT _t9 (NAME _Iprintln_pai) (TEMP _t8))
    mov [ rbp + -32 ], r10
    callq _Iprintln_pai ## (CALL_STMT _t9 (NAME _Iprintln_pai) (TEMP _t8))
    mov r10, [ rbp + -24 ]
    mov r10, rax ## (CALL_STMT _t9 (NAME _Iprintln_pai) (TEMP _t8))
    mov [ rbp + -24 ], r10
    jmp end__Imain_paai ## (RETURN ())
end__Imain_paai:
    addq rsp, 112
    mov rsp, rbp
    popq rbp
    retq
_Igenerate_i:
    pushq rbp
    mov rbp, rsp
    subq rsp, 16
_l1:
    mov r10, [ rbp + -8 ]
    movabsq r10, 1000 ## (CONST 1000)
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t0) (CONST 1000))
    mov [ rbp + -8 ], r11
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -16 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t0))
    mov [ rbp + -16 ], r10
    jmp end__Igenerate_i ## (RETURN ())
end__Igenerate_i:
    addq rsp, 16
    mov rsp, rbp
    popq rbp
    retq
