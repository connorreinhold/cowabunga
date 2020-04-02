.intel_syntax noprefix
.globl _Imain_paai
.align 4
_Imain_paai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 96
_l9:
    mov r10, [ rbp + -8 ]
    mov r10, rdi ## (MOVE (TEMP args) (TEMP _ARG0))
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -56 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP res2) (CONST 0))
    mov [ rbp + -56 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -80 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -80 ]
    mov r10, r11 ## (MOVE (TEMP res3) (CONST 0))
    mov [ rbp + -80 ], r11
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -72 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -72 ]
    mov r10, r11 ## (MOVE (TEMP b) (CONST 0))
    mov [ rbp + -72 ], r11
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -96 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -64 ]
    mov r11, [ rbp + -96 ]
    mov r10, r11 ## (MOVE (TEMP _t0) (CONST 1))
    mov [ rbp + -96 ], r11
    mov [ rbp + -64 ], r10
_l1:
    mov r10, [ rbp + -64 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t0) (TEMP b))
    mov [ rbp + -16 ], r11
    mov [ rbp + -64 ], r10
_l0:
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -64 ]
    mov r10, r11 ## (MOVE (TEMP c) (TEMP _t0))
    mov [ rbp + -64 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -16 ]
    cmpq r10, 1 ## (CJUMP (TEMP b) _l3)
    mov [ rbp + -16 ], r10
    je _l3 ## (CJUMP (TEMP b) _l3)
_l4:
_l5:
    mov r10, [ rbp + -32 ]
    cmpq r10, 1 ## (CJUMP (TEMP c) _l6)
    mov [ rbp + -32 ], r10
    je _l6 ## (CJUMP (TEMP c) _l6)
_l7:
_l8:
    jmp end__Imain_paai ## (RETURN ())
_l2:
    mov r10, [ rbp + -16 ]
    cmpq r10, 1 ## (CJUMP (TEMP b) _l0)
    mov [ rbp + -16 ], r10
    je _l0 ## (CJUMP (TEMP b) _l0)
    jmp _l1 ## (JUMP (NAME _l1))
_l3:
    mov r10, [ rbp + -88 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -88 ]
    mov r10, r11 ## (MOVE (TEMP res2) (CONST 1))
    mov [ rbp + -88 ], r11
    mov [ rbp + -40 ], r10
    jmp _l5 ## (JUMP (NAME _l5))
_l6:
    mov r10, [ rbp + -24 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (MOVE (TEMP res3) (CONST 1))
    mov [ rbp + -24 ], r11
    mov [ rbp + -48 ], r10
    jmp _l8 ## (JUMP (NAME _l8))
end__Imain_paai:
    addq rsp, 96
    mov rsp, rbp
    popq rbp
    retq
