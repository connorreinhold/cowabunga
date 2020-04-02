.intel_syntax noprefix
.globl _Imain_paai
.align 4
_Imain_paai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 288
_l5:
    mov r10, [ rbp + -64 ]
    mov r10, rdi ## (MOVE (TEMP args) (TEMP _ARG0))
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -248 ]
    mov r11, [ rbp + -64 ]
    mov r10, r11 ## (MOVE (TEMP _t3) (TEMP args))
    mov [ rbp + -64 ], r11
    mov [ rbp + -248 ], r10
    mov r10, [ rbp + -72 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -248 ]
    mov r10, r11 ## (SUB (TEMP _t3) (CONST 8))
    mov [ rbp + -248 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -72 ]
    subq r10, r11 ## (SUB (TEMP _t3) (CONST 8))
    mov [ rbp + -72 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -80 ]
    mov r10, [ r11 ] ## (MOVE (TEMP argc) (MEM (SUB (TEMP _t3) (CONST 8))))
    mov [ rbp + -80 ], r11
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -88 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -8 ]
    mov r11, [ rbp + -88 ]
    mov r10, r11 ## (MOVE (TEMP a) (CONST 0))
    mov [ rbp + -88 ], r11
    mov [ rbp + -8 ], r10
_l0:
    mov r10, [ rbp + -280 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t4) (TEMP a))
    mov [ rbp + -8 ], r11
    mov [ rbp + -280 ], r10
    mov r10, [ rbp + -280 ]
    mov r11, [ rbp + -24 ]
    cmpq r10, r11 ## (LT (TEMP _t4) (TEMP argc))
    mov [ rbp + -24 ], r11
    mov [ rbp + -280 ], r10
    pushq rax
    mov rax, [ rbp + -56 ]
    setl al ## (LT (TEMP _t4) (TEMP argc))
    mov [ rbp + -56 ], rax
    popq rax
    mov r10, [ rbp + -96 ]
    pushq rax
    mov rax, [ rbp + -56 ]
    movzx r10, al ## (LT (TEMP _t4) (TEMP argc))
    mov [ rbp + -56 ], rax
    popq rax
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -96 ]
    cmpq r10, 1 ## (CJUMP (LT (TEMP _t4) (TEMP argc)) _l1)
    mov [ rbp + -96 ], r10
    je _l1 ## (CJUMP (LT (TEMP _t4) (TEMP argc)) _l1)
_l2:
    jmp end__Imain_paai ## (RETURN ())
_l1:
    mov r10, [ rbp + -216 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t1) (TEMP a))
    mov [ rbp + -8 ], r11
    mov [ rbp + -216 ], r10
    mov r10, [ rbp + -232 ]
    mov r11, [ rbp + -64 ]
    mov r10, r11 ## (MOVE (TEMP _t0) (TEMP args))
    mov [ rbp + -64 ], r11
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -264 ]
    mov r11, [ rbp + -232 ]
    mov r10, r11 ## (MOVE (TEMP _t5) (TEMP _t0))
    mov [ rbp + -232 ], r11
    mov [ rbp + -264 ], r10
    mov r10, [ rbp + -272 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -272 ], r10
    mov r10, [ rbp + -288 ]
    mov r11, [ rbp + -264 ]
    mov r10, r11 ## (SUB (TEMP _t5) (CONST 8))
    mov [ rbp + -264 ], r11
    mov [ rbp + -288 ], r10
    mov r10, [ rbp + -288 ]
    mov r11, [ rbp + -272 ]
    subq r10, r11 ## (SUB (TEMP _t5) (CONST 8))
    mov [ rbp + -272 ], r11
    mov [ rbp + -288 ], r10
    mov r10, [ rbp + -256 ]
    mov r11, [ rbp + -288 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t2) (MEM (SUB (TEMP _t5) (CONST 8))))
    mov [ rbp + -288 ], r11
    mov [ rbp + -256 ], r10
    mov r10, [ rbp + -200 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -200 ]
    mov r10, r11 ## (MOVE (TEMP _t6) (CONST 0))
    mov [ rbp + -200 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -216 ]
    cmpq r10, r11 ## (LEQ (TEMP _t6) (TEMP _t1))
    mov [ rbp + -216 ], r11
    mov [ rbp + -32 ], r10
    pushq rax
    mov rax, [ rbp + -224 ]
    setle al ## (LEQ (TEMP _t6) (TEMP _t1))
    mov [ rbp + -224 ], rax
    popq rax
    mov r10, [ rbp + -208 ]
    pushq rax
    mov rax, [ rbp + -224 ]
    movzx r10, al ## (LEQ (TEMP _t6) (TEMP _t1))
    mov [ rbp + -224 ], rax
    popq rax
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -208 ]
    mov r10, r11 ## (MOVE (TEMP _t8) (LEQ (TEMP _t6) (TEMP _t1)))
    mov [ rbp + -208 ], r11
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -216 ]
    mov r10, r11 ## (MOVE (TEMP _t7) (TEMP _t1))
    mov [ rbp + -216 ], r11
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -256 ]
    cmpq r10, r11 ## (LT (TEMP _t7) (TEMP _t2))
    mov [ rbp + -256 ], r11
    mov [ rbp + -16 ], r10
    pushq rax
    mov rax, [ rbp + -168 ]
    setl al ## (LT (TEMP _t7) (TEMP _t2))
    mov [ rbp + -168 ], rax
    popq rax
    mov r10, [ rbp + -240 ]
    pushq rax
    mov rax, [ rbp + -168 ]
    movzx r10, al ## (LT (TEMP _t7) (TEMP _t2))
    mov [ rbp + -168 ], rax
    popq rax
    mov [ rbp + -240 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -48 ]
    mov r10, r11 ## (AND (TEMP _t8) (LT (TEMP _t7) (TEMP _t2)))
    mov [ rbp + -48 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -240 ]
    andq r10, r11 ## (AND (TEMP _t8) (LT (TEMP _t7) (TEMP _t2)))
    mov [ rbp + -240 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -176 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t8) (LT (TEMP _t7) (TEMP _t2))) _l3)
    mov [ rbp + -176 ], r10
    je _l3 ## (CJUMP (AND (TEMP _t8) (LT (TEMP _t7) (TEMP _t2))) _l3)
_l4:
    callq _xi_out_of_bounds ## (CALL_STMT _t9 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -40 ]
    mov r10, rax ## (CALL_STMT _t9 (NAME _xi_out_of_bounds))
    mov [ rbp + -40 ], r10
_l3:
    mov r10, [ rbp + -104 ]
    mov r11, [ rbp + -232 ]
    mov r10, r11 ## (MOVE (TEMP _t11) (TEMP _t0))
    mov [ rbp + -232 ], r11
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -216 ]
    mov r10, r11 ## (MOVE (TEMP _t10) (TEMP _t1))
    mov [ rbp + -216 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -184 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -184 ], r10
    mov r10, [ rbp + -192 ]
    mov r11, [ rbp + -152 ]
    mov r10, r11 ## (MUL (TEMP _t10) (CONST 8))
    mov [ rbp + -152 ], r11
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -192 ]
    mov r11, [ rbp + -184 ]
    imulq r10, r11 ## (MUL (TEMP _t10) (CONST 8))
    mov [ rbp + -184 ], r11
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -104 ]
    mov r10, r11 ## (ADD (TEMP _t11) (MUL (TEMP _t10) (CONST 8)))
    mov [ rbp + -104 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -192 ]
    addq r10, r11 ## (ADD (TEMP _t11) (MUL (TEMP _t10) (CONST 8)))
    mov [ rbp + -192 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -136 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t12) (MEM (ADD (TEMP _t11) (MUL (TEMP _t10) (CONST 8)))))
    mov [ rbp + -136 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -112 ]
    mov rdi, r10 ## (CALL_STMT _t13 (NAME _Iprintln_pai) (TEMP _t12))
    mov [ rbp + -112 ], r10
    callq _Iprintln_pai ## (CALL_STMT _t13 (NAME _Iprintln_pai) (TEMP _t12))
    mov r10, [ rbp + -120 ]
    mov r10, rax ## (CALL_STMT _t13 (NAME _Iprintln_pai) (TEMP _t12))
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t14) (TEMP a))
    mov [ rbp + -8 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -144 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -160 ]
    mov r11, [ rbp + -128 ]
    mov r10, r11 ## (ADD (TEMP _t14) (CONST 1))
    mov [ rbp + -128 ], r11
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -160 ]
    mov r11, [ rbp + -144 ]
    addq r10, r11 ## (ADD (TEMP _t14) (CONST 1))
    mov [ rbp + -144 ], r11
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -8 ]
    mov r11, [ rbp + -160 ]
    mov r10, r11 ## (MOVE (TEMP a) (ADD (TEMP _t14) (CONST 1)))
    mov [ rbp + -160 ], r11
    mov [ rbp + -8 ], r10
    jmp _l0 ## (JUMP (NAME _l0))
end__Imain_paai:
    addq rsp, 288
    mov rsp, rbp
    popq rbp
    retq
