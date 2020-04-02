.intel_syntax noprefix
.globl _Imain_paai
.align 4
_Imain_paai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 168
_l2:
    mov r10, [ rbp + -40 ]
    mov r10, rdi ## (MOVE (TEMP args) (TEMP _ARG0))
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -128 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -128 ]
    mov r10, r11 ## (MOVE (TEMP _t4) (CONST 1))
    mov [ rbp + -128 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -136 ]
    movabsq r10, 2 ## (CONST 2)
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -136 ]
    mov r10, r11 ## (MOVE (TEMP _t5) (CONST 2))
    mov [ rbp + -136 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -160 ]
    movabsq r10, 3 ## (CONST 3)
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -160 ]
    mov r10, r11 ## (MOVE (TEMP _t6) (CONST 3))
    mov [ rbp + -160 ], r11
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -64 ]
    movabsq r10, 4 ## (CONST 4)
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -8 ]
    mov r11, [ rbp + -64 ]
    mov r10, r11 ## (MOVE (TEMP _t7) (CONST 4))
    mov [ rbp + -64 ], r11
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -80 ]
    movabsq r10, 5 ## (CONST 5)
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -80 ]
    mov r10, r11 ## (MOVE (TEMP _t8) (CONST 5))
    mov [ rbp + -80 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -96 ]
    movabsq r10, 6 ## (CONST 6)
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -96 ]
    mov r10, r11 ## (MOVE (TEMP _t9) (CONST 6))
    mov [ rbp + -96 ], r11
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -112 ]
    movabsq r10, 7 ## (CONST 7)
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -112 ]
    mov r10, r11 ## (MOVE (TEMP _t10) (CONST 7))
    mov [ rbp + -112 ], r11
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -152 ]
    mov rdi, r10 ## (CALL_STMT _t11 (NAME _Isum_it7iiiiiii) (TEMP _t4) (TEMP _t5) (TEMP _t6) (TEMP _t7) (TEMP _t8) (TEMP _t9) (TEMP _t10))
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -144 ]
    mov rsi, r10 ## (CALL_STMT _t11 (NAME _Isum_it7iiiiiii) (TEMP _t4) (TEMP _t5) (TEMP _t6) (TEMP _t7) (TEMP _t8) (TEMP _t9) (TEMP _t10))
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -16 ]
    mov rdx, r10 ## (CALL_STMT _t11 (NAME _Isum_it7iiiiiii) (TEMP _t4) (TEMP _t5) (TEMP _t6) (TEMP _t7) (TEMP _t8) (TEMP _t9) (TEMP _t10))
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -8 ]
    mov rcx, r10 ## (CALL_STMT _t11 (NAME _Isum_it7iiiiiii) (TEMP _t4) (TEMP _t5) (TEMP _t6) (TEMP _t7) (TEMP _t8) (TEMP _t9) (TEMP _t10))
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -32 ]
    mov r8, r10 ## (CALL_STMT _t11 (NAME _Isum_it7iiiiiii) (TEMP _t4) (TEMP _t5) (TEMP _t6) (TEMP _t7) (TEMP _t8) (TEMP _t9) (TEMP _t10))
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -24 ]
    mov r9, r10 ## (CALL_STMT _t11 (NAME _Isum_it7iiiiiii) (TEMP _t4) (TEMP _t5) (TEMP _t6) (TEMP _t7) (TEMP _t8) (TEMP _t9) (TEMP _t10))
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -168 ]
    pushq r10 ## (CALL_STMT _t11 (NAME _Isum_it7iiiiiii) (TEMP _t4) (TEMP _t5) (TEMP _t6) (TEMP _t7) (TEMP _t8) (TEMP _t9) (TEMP _t10))
    mov [ rbp + -168 ], r10
    callq _Isum_it7iiiiiii ## (CALL_STMT _t11 (NAME _Isum_it7iiiiiii) (TEMP _t4) (TEMP _t5) (TEMP _t6) (TEMP _t7) (TEMP _t8) (TEMP _t9) (TEMP _t10))
    mov r10, [ rbp + -72 ]
    mov r10, rax ## (CALL_STMT _t11 (NAME _Isum_it7iiiiiii) (TEMP _t4) (TEMP _t5) (TEMP _t6) (TEMP _t7) (TEMP _t8) (TEMP _t9) (TEMP _t10))
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -72 ]
    mov r10, r11 ## (MOVE (TEMP total) (TEMP _t11))
    mov [ rbp + -72 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t12) (TEMP total))
    mov [ rbp + -56 ], r11
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -88 ]
    mov rdi, r10 ## (CALL_STMT _t13 (NAME _IunparseInt_aii) (TEMP _t12))
    mov [ rbp + -88 ], r10
    callq _IunparseInt_aii ## (CALL_STMT _t13 (NAME _IunparseInt_aii) (TEMP _t12))
    mov r10, [ rbp + -104 ]
    mov r10, rax ## (CALL_STMT _t13 (NAME _IunparseInt_aii) (TEMP _t12))
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -104 ]
    mov r10, r11 ## (MOVE (TEMP _t14) (TEMP _t13))
    mov [ rbp + -104 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -120 ]
    mov rdi, r10 ## (CALL_STMT _t15 (NAME _Iprintln_pai) (TEMP _t14))
    mov [ rbp + -120 ], r10
    callq _Iprintln_pai ## (CALL_STMT _t15 (NAME _Iprintln_pai) (TEMP _t14))
    mov r10, [ rbp + -48 ]
    mov r10, rax ## (CALL_STMT _t15 (NAME _Iprintln_pai) (TEMP _t14))
    mov [ rbp + -48 ], r10
    jmp end__Imain_paai ## (RETURN ())
end__Imain_paai:
    addq rsp, 168
    mov rsp, rbp
    popq rbp
    retq
_Isum_it7iiiiiii:
    pushq rbp
    mov rbp, rsp
    subq rsp, 160
_l4:
    mov r10, [ rbp + -8 ]
    mov r10, rdi ## (MOVE (TEMP a) (TEMP _ARG0))
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -16 ]
    mov r10, rsi ## (MOVE (TEMP b) (TEMP _ARG1))
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -24 ]
    mov r10, rdx ## (MOVE (TEMP c) (TEMP _ARG2))
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -32 ]
    mov r10, rcx ## (MOVE (TEMP d) (TEMP _ARG3))
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -40 ]
    mov r10, r8 ## (MOVE (TEMP e) (TEMP _ARG4))
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -48 ]
    mov r10, r9 ## (MOVE (TEMP f) (TEMP _ARG5))
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -56 ]
    mov r10, [ rbp + 16 ] ## (MOVE (TEMP g) (TEMP _ARG6))
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t24) (TEMP a))
    mov [ rbp + -8 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -64 ]
    mov r11, [ rbp + -120 ]
    mov r10, r11 ## (ADD (TEMP _t24) (TEMP b))
    mov [ rbp + -120 ], r11
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -64 ]
    mov r11, [ rbp + -16 ]
    addq r10, r11 ## (ADD (TEMP _t24) (TEMP b))
    mov [ rbp + -16 ], r11
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -64 ]
    mov r10, r11 ## (MOVE (TEMP _t25) (ADD (TEMP _t24) (TEMP b)))
    mov [ rbp + -64 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -128 ]
    mov r10, r11 ## (ADD (TEMP _t25) (TEMP c))
    mov [ rbp + -128 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -24 ]
    addq r10, r11 ## (ADD (TEMP _t25) (TEMP c))
    mov [ rbp + -24 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -72 ]
    mov r11, [ rbp + -80 ]
    mov r10, r11 ## (MOVE (TEMP _t26) (ADD (TEMP _t25) (TEMP c)))
    mov [ rbp + -80 ], r11
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -72 ]
    mov r10, r11 ## (ADD (TEMP _t26) (TEMP d))
    mov [ rbp + -72 ], r11
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -32 ]
    addq r10, r11 ## (ADD (TEMP _t26) (TEMP d))
    mov [ rbp + -32 ], r11
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -96 ]
    mov r10, r11 ## (MOVE (TEMP _t27) (ADD (TEMP _t26) (TEMP d)))
    mov [ rbp + -96 ], r11
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -88 ]
    mov r10, r11 ## (ADD (TEMP _t27) (TEMP e))
    mov [ rbp + -88 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -40 ]
    addq r10, r11 ## (ADD (TEMP _t27) (TEMP e))
    mov [ rbp + -40 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -104 ]
    mov r11, [ rbp + -136 ]
    mov r10, r11 ## (MOVE (TEMP _t28) (ADD (TEMP _t27) (TEMP e)))
    mov [ rbp + -136 ], r11
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -104 ]
    mov r10, r11 ## (ADD (TEMP _t28) (TEMP f))
    mov [ rbp + -104 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -48 ]
    addq r10, r11 ## (ADD (TEMP _t28) (TEMP f))
    mov [ rbp + -48 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -152 ]
    mov r10, r11 ## (MOVE (TEMP _t29) (ADD (TEMP _t28) (TEMP f)))
    mov [ rbp + -152 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -160 ]
    mov r11, [ rbp + -112 ]
    mov r10, r11 ## (ADD (TEMP _t29) (TEMP g))
    mov [ rbp + -112 ], r11
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -160 ]
    mov r11, [ rbp + -56 ]
    addq r10, r11 ## (ADD (TEMP _t29) (TEMP g))
    mov [ rbp + -56 ], r11
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -160 ]
    mov r10, r11 ## (MOVE (TEMP _t3) (ADD (TEMP _t29) (TEMP g)))
    mov [ rbp + -160 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -144 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t3))
    mov [ rbp + -144 ], r10
    jmp end__Isum_it7iiiiiii ## (RETURN ())
end__Isum_it7iiiiiii:
    addq rsp, 160
    mov rsp, rbp
    popq rbp
    retq
_Itryout_pt2aii:
    pushq rbp
    mov rbp, rsp
    subq rsp, 200
_l3:
    mov r10, [ rbp + -8 ]
    mov r10, rdi ## (MOVE (TEMP arr) (TEMP _ARG0))
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -88 ]
    mov r10, rsi ## (MOVE (TEMP num) (TEMP _ARG1))
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -192 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -192 ]
    mov r10, r11 ## (MOVE (TEMP _t1) (CONST 0))
    mov [ rbp + -192 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -160 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t0) (TEMP arr))
    mov [ rbp + -8 ], r11
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -160 ]
    mov r10, r11 ## (MOVE (TEMP _t16) (TEMP _t0))
    mov [ rbp + -160 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -112 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (SUB (TEMP _t16) (CONST 8))
    mov [ rbp + -32 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -112 ]
    subq r10, r11 ## (SUB (TEMP _t16) (CONST 8))
    mov [ rbp + -112 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -128 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t2) (MEM (SUB (TEMP _t16) (CONST 8))))
    mov [ rbp + -128 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -152 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -152 ]
    mov r10, r11 ## (MOVE (TEMP _t17) (CONST 0))
    mov [ rbp + -152 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -144 ]
    cmpq r10, r11 ## (LEQ (TEMP _t17) (TEMP _t1))
    mov [ rbp + -144 ], r11
    mov [ rbp + -40 ], r10
    pushq rax
    mov rax, [ rbp + -96 ]
    setle al ## (LEQ (TEMP _t17) (TEMP _t1))
    mov [ rbp + -96 ], rax
    popq rax
    mov r10, [ rbp + -168 ]
    pushq rax
    mov rax, [ rbp + -96 ]
    movzx r10, al ## (LEQ (TEMP _t17) (TEMP _t1))
    mov [ rbp + -96 ], rax
    popq rax
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -168 ]
    mov r10, r11 ## (MOVE (TEMP _t19) (LEQ (TEMP _t17) (TEMP _t1)))
    mov [ rbp + -168 ], r11
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -144 ]
    mov r10, r11 ## (MOVE (TEMP _t18) (TEMP _t1))
    mov [ rbp + -144 ], r11
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -176 ]
    cmpq r10, r11 ## (LT (TEMP _t18) (TEMP _t2))
    mov [ rbp + -176 ], r11
    mov [ rbp + -48 ], r10
    pushq rax
    mov rax, [ rbp + -16 ]
    setl al ## (LT (TEMP _t18) (TEMP _t2))
    mov [ rbp + -16 ], rax
    popq rax
    mov r10, [ rbp + -104 ]
    pushq rax
    mov rax, [ rbp + -16 ]
    movzx r10, al ## (LT (TEMP _t18) (TEMP _t2))
    mov [ rbp + -16 ], rax
    popq rax
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (AND (TEMP _t19) (LT (TEMP _t18) (TEMP _t2)))
    mov [ rbp + -24 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -104 ]
    andq r10, r11 ## (AND (TEMP _t19) (LT (TEMP _t18) (TEMP _t2)))
    mov [ rbp + -104 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -56 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t19) (LT (TEMP _t18) (TEMP _t2))) _l0)
    mov [ rbp + -56 ], r10
    je _l0 ## (CJUMP (AND (TEMP _t19) (LT (TEMP _t18) (TEMP _t2))) _l0)
_l1:
    callq _xi_out_of_bounds ## (CALL_STMT _t20 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -184 ]
    mov r10, rax ## (CALL_STMT _t20 (NAME _xi_out_of_bounds))
    mov [ rbp + -184 ], r10
_l0:
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -160 ]
    mov r10, r11 ## (MOVE (TEMP _t22) (TEMP _t0))
    mov [ rbp + -160 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -144 ]
    mov r10, r11 ## (MOVE (TEMP _t21) (TEMP _t1))
    mov [ rbp + -144 ], r11
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -64 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -72 ]
    mov r11, [ rbp + -200 ]
    mov r10, r11 ## (MUL (TEMP _t21) (CONST 8))
    mov [ rbp + -200 ], r11
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -72 ]
    mov r11, [ rbp + -64 ]
    imulq r10, r11 ## (MUL (TEMP _t21) (CONST 8))
    mov [ rbp + -64 ], r11
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -120 ]
    mov r10, r11 ## (ADD (TEMP _t22) (MUL (TEMP _t21) (CONST 8)))
    mov [ rbp + -120 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -72 ]
    addq r10, r11 ## (ADD (TEMP _t22) (MUL (TEMP _t21) (CONST 8)))
    mov [ rbp + -72 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -80 ]
    mov r10, r11 ## (MOVE (TEMP _t23) (ADD (TEMP _t22) (MUL (TEMP _t21) (CONST 8))))
    mov [ rbp + -80 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -88 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t23)) (TEMP num))
    mov [ rbp + -88 ], r11
    mov [ rbp + -136 ], r10
    jmp end__Itryout_pt2aii ## (RETURN ())
end__Itryout_pt2aii:
    addq rsp, 200
    mov rsp, rbp
    popq rbp
    retq
