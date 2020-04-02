.intel_syntax noprefix
.globl _Imain_paai
.align 4
_Imain_paai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 280
_l5:
    mov r10, [ rbp + -56 ]
    mov r10, rdi ## (MOVE (TEMP args) (TEMP _ARG0))
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -64 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -64 ]
    mov r10, r11 ## (MOVE (TEMP i) (CONST 0))
    mov [ rbp + -64 ], r11
    mov [ rbp + -40 ], r10
_l0:
    mov r10, [ rbp + -272 ]
    mov r11, [ rbp + -40 ]
    mov r10, r11 ## (MOVE (TEMP _t4) (TEMP i))
    mov [ rbp + -40 ], r11
    mov [ rbp + -272 ], r10
    mov r10, [ rbp + -240 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t3) (TEMP args))
    mov [ rbp + -56 ], r11
    mov [ rbp + -240 ], r10
    mov r10, [ rbp + -72 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -240 ]
    mov r10, r11 ## (SUB (TEMP _t3) (CONST 8))
    mov [ rbp + -240 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -72 ]
    subq r10, r11 ## (SUB (TEMP _t3) (CONST 8))
    mov [ rbp + -72 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -272 ]
    mov r11, [ rbp + -80 ]
    cmpq r10, [ r11 ] ## (LT (TEMP _t4) (MEM (SUB (TEMP _t3) (CONST 8))))
    mov [ rbp + -80 ], r11
    mov [ rbp + -272 ], r10
    pushq rax
    mov rax, [ rbp + -48 ]
    setl al ## (LT (TEMP _t4) (MEM (SUB (TEMP _t3) (CONST 8))))
    mov [ rbp + -48 ], rax
    popq rax
    mov r10, [ rbp + -88 ]
    pushq rax
    mov rax, [ rbp + -48 ]
    movzx r10, al ## (LT (TEMP _t4) (MEM (SUB (TEMP _t3) (CONST 8))))
    mov [ rbp + -48 ], rax
    popq rax
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -88 ]
    cmpq r10, 1 ## (CJUMP (LT (TEMP _t4) (MEM (SUB (TEMP _t3) (CONST 8)))) _l1)
    mov [ rbp + -88 ], r10
    je _l1 ## (CJUMP (LT (TEMP _t4) (MEM (SUB (TEMP _t3) (CONST 8)))) _l1)
_l2:
    jmp end__Imain_paai ## (RETURN ())
_l1:
    mov r10, [ rbp + -208 ]
    mov r11, [ rbp + -40 ]
    mov r10, r11 ## (MOVE (TEMP _t1) (TEMP i))
    mov [ rbp + -40 ], r11
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -224 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t0) (TEMP args))
    mov [ rbp + -56 ], r11
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -256 ]
    mov r11, [ rbp + -224 ]
    mov r10, r11 ## (MOVE (TEMP _t5) (TEMP _t0))
    mov [ rbp + -224 ], r11
    mov [ rbp + -256 ], r10
    mov r10, [ rbp + -264 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -264 ], r10
    mov r10, [ rbp + -280 ]
    mov r11, [ rbp + -256 ]
    mov r10, r11 ## (SUB (TEMP _t5) (CONST 8))
    mov [ rbp + -256 ], r11
    mov [ rbp + -280 ], r10
    mov r10, [ rbp + -280 ]
    mov r11, [ rbp + -264 ]
    subq r10, r11 ## (SUB (TEMP _t5) (CONST 8))
    mov [ rbp + -264 ], r11
    mov [ rbp + -280 ], r10
    mov r10, [ rbp + -248 ]
    mov r11, [ rbp + -280 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t2) (MEM (SUB (TEMP _t5) (CONST 8))))
    mov [ rbp + -280 ], r11
    mov [ rbp + -248 ], r10
    mov r10, [ rbp + -192 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -192 ]
    mov r10, r11 ## (MOVE (TEMP _t6) (CONST 0))
    mov [ rbp + -192 ], r11
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -208 ]
    cmpq r10, r11 ## (LEQ (TEMP _t6) (TEMP _t1))
    mov [ rbp + -208 ], r11
    mov [ rbp + -16 ], r10
    pushq rax
    mov rax, [ rbp + -216 ]
    setle al ## (LEQ (TEMP _t6) (TEMP _t1))
    mov [ rbp + -216 ], rax
    popq rax
    mov r10, [ rbp + -200 ]
    pushq rax
    mov rax, [ rbp + -216 ]
    movzx r10, al ## (LEQ (TEMP _t6) (TEMP _t1))
    mov [ rbp + -216 ], rax
    popq rax
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -200 ]
    mov r10, r11 ## (MOVE (TEMP _t8) (LEQ (TEMP _t6) (TEMP _t1)))
    mov [ rbp + -200 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -8 ]
    mov r11, [ rbp + -208 ]
    mov r10, r11 ## (MOVE (TEMP _t7) (TEMP _t1))
    mov [ rbp + -208 ], r11
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -8 ]
    mov r11, [ rbp + -248 ]
    cmpq r10, r11 ## (LT (TEMP _t7) (TEMP _t2))
    mov [ rbp + -248 ], r11
    mov [ rbp + -8 ], r10
    pushq rax
    mov rax, [ rbp + -160 ]
    setl al ## (LT (TEMP _t7) (TEMP _t2))
    mov [ rbp + -160 ], rax
    popq rax
    mov r10, [ rbp + -232 ]
    pushq rax
    mov rax, [ rbp + -160 ]
    movzx r10, al ## (LT (TEMP _t7) (TEMP _t2))
    mov [ rbp + -160 ], rax
    popq rax
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (AND (TEMP _t8) (LT (TEMP _t7) (TEMP _t2)))
    mov [ rbp + -32 ], r11
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -232 ]
    andq r10, r11 ## (AND (TEMP _t8) (LT (TEMP _t7) (TEMP _t2)))
    mov [ rbp + -232 ], r11
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -168 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t8) (LT (TEMP _t7) (TEMP _t2))) _l3)
    mov [ rbp + -168 ], r10
    je _l3 ## (CJUMP (AND (TEMP _t8) (LT (TEMP _t7) (TEMP _t2))) _l3)
_l4:
    callq _xi_out_of_bounds ## (CALL_STMT _t9 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -24 ]
    mov r10, rax ## (CALL_STMT _t9 (NAME _xi_out_of_bounds))
    mov [ rbp + -24 ], r10
_l3:
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -224 ]
    mov r10, r11 ## (MOVE (TEMP _t11) (TEMP _t0))
    mov [ rbp + -224 ], r11
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -208 ]
    mov r10, r11 ## (MOVE (TEMP _t10) (TEMP _t1))
    mov [ rbp + -208 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -176 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -184 ]
    mov r11, [ rbp + -144 ]
    mov r10, r11 ## (MUL (TEMP _t10) (CONST 8))
    mov [ rbp + -144 ], r11
    mov [ rbp + -184 ], r10
    mov r10, [ rbp + -184 ]
    mov r11, [ rbp + -176 ]
    imulq r10, r11 ## (MUL (TEMP _t10) (CONST 8))
    mov [ rbp + -176 ], r11
    mov [ rbp + -184 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -96 ]
    mov r10, r11 ## (ADD (TEMP _t11) (MUL (TEMP _t10) (CONST 8)))
    mov [ rbp + -96 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -184 ]
    addq r10, r11 ## (ADD (TEMP _t11) (MUL (TEMP _t10) (CONST 8)))
    mov [ rbp + -184 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -104 ]
    mov r11, [ rbp + -128 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t12) (MEM (ADD (TEMP _t11) (MUL (TEMP _t10) (CONST 8)))))
    mov [ rbp + -128 ], r11
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -104 ]
    mov rdi, r10 ## (CALL_STMT _t13 (NAME _Iprintln_pai) (TEMP _t12))
    mov [ rbp + -104 ], r10
    callq _Iprintln_pai ## (CALL_STMT _t13 (NAME _Iprintln_pai) (TEMP _t12))
    mov r10, [ rbp + -112 ]
    mov r10, rax ## (CALL_STMT _t13 (NAME _Iprintln_pai) (TEMP _t12))
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -40 ]
    mov r10, r11 ## (MOVE (TEMP _t14) (TEMP i))
    mov [ rbp + -40 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -136 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -120 ]
    mov r10, r11 ## (ADD (TEMP _t14) (CONST 1))
    mov [ rbp + -120 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -136 ]
    addq r10, r11 ## (ADD (TEMP _t14) (CONST 1))
    mov [ rbp + -136 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -152 ]
    mov r10, r11 ## (MOVE (TEMP i) (ADD (TEMP _t14) (CONST 1)))
    mov [ rbp + -152 ], r11
    mov [ rbp + -40 ], r10
    jmp _l0 ## (JUMP (NAME _l0))
end__Imain_paai:
    addq rsp, 280
    mov rsp, rbp
    popq rbp
    retq
