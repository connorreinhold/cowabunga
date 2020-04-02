.intel_syntax noprefix
.globl _Imain_paai
.align 4
_Imain_paai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 984
_l12:
    mov r10, [ rbp + -744 ]
    mov r10, rdi ## (MOVE (TEMP args) (TEMP _ARG0))
    mov [ rbp + -744 ], r10
    mov r10, [ rbp + -296 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -296 ], r10
    mov r10, [ rbp + -496 ]
    mov r11, [ rbp + -296 ]
    mov r10, r11 ## (MOVE (TEMP i) (CONST 0))
    mov [ rbp + -296 ], r11
    mov [ rbp + -496 ], r10
    mov r10, [ rbp + -240 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -240 ], r10
    mov r10, [ rbp + -504 ]
    mov r11, [ rbp + -240 ]
    mov r10, r11 ## (MOVE (TEMP j) (CONST 0))
    mov [ rbp + -240 ], r11
    mov [ rbp + -504 ], r10
    mov r10, [ rbp + -248 ]
    movabsq r10, 10 ## (CONST 10)
    mov [ rbp + -248 ], r10
    mov r10, [ rbp + -728 ]
    mov r11, [ rbp + -248 ]
    mov r10, r11 ## (MOVE (TEMP rows) (CONST 10))
    mov [ rbp + -248 ], r11
    mov [ rbp + -728 ], r10
_l0:
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -496 ]
    mov r10, r11 ## (MOVE (TEMP _t8) (TEMP i))
    mov [ rbp + -496 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -728 ]
    cmpq r10, r11 ## (LT (TEMP _t8) (TEMP rows))
    mov [ rbp + -728 ], r11
    mov [ rbp + -32 ], r10
    pushq rax
    mov rax, [ rbp + -264 ]
    setl al ## (LT (TEMP _t8) (TEMP rows))
    mov [ rbp + -264 ], rax
    popq rax
    mov r10, [ rbp + -256 ]
    pushq rax
    mov rax, [ rbp + -264 ]
    movzx r10, al ## (LT (TEMP _t8) (TEMP rows))
    mov [ rbp + -264 ], rax
    popq rax
    mov [ rbp + -256 ], r10
    mov r10, [ rbp + -256 ]
    cmpq r10, 1 ## (CJUMP (LT (TEMP _t8) (TEMP rows)) _l1)
    mov [ rbp + -256 ], r10
    je _l1 ## (CJUMP (LT (TEMP _t8) (TEMP rows)) _l1)
_l2:
    mov r10, [ rbp + -232 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -504 ]
    mov r11, [ rbp + -232 ]
    mov r10, r11 ## (MOVE (TEMP j) (CONST 0))
    mov [ rbp + -232 ], r11
    mov [ rbp + -504 ], r10
    mov r10, [ rbp + -496 ]
    mov r11, [ rbp + -728 ]
    mov r10, r11 ## (MOVE (TEMP i) (TEMP rows))
    mov [ rbp + -728 ], r11
    mov [ rbp + -496 ], r10
_l6:
    mov r10, [ rbp + -104 ]
    mov r11, [ rbp + -496 ]
    mov r10, r11 ## (MOVE (TEMP _t31) (TEMP i))
    mov [ rbp + -496 ], r11
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -608 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -608 ], r10
    mov r10, [ rbp + -104 ]
    mov r11, [ rbp + -608 ]
    cmpq r10, r11 ## (GEQ (TEMP _t31) (CONST 0))
    mov [ rbp + -608 ], r11
    mov [ rbp + -104 ], r10
    pushq rax
    mov rax, [ rbp + -656 ]
    setge al ## (GEQ (TEMP _t31) (CONST 0))
    mov [ rbp + -656 ], rax
    popq rax
    mov r10, [ rbp + -616 ]
    pushq rax
    mov rax, [ rbp + -656 ]
    movzx r10, al ## (GEQ (TEMP _t31) (CONST 0))
    mov [ rbp + -656 ], rax
    popq rax
    mov [ rbp + -616 ], r10
    mov r10, [ rbp + -616 ]
    cmpq r10, 1 ## (CJUMP (GEQ (TEMP _t31) (CONST 0)) _l7)
    mov [ rbp + -616 ], r10
    je _l7 ## (CJUMP (GEQ (TEMP _t31) (CONST 0)) _l7)
_l8:
    jmp end__Imain_paai ## (RETURN ())
_l1:
    mov r10, [ rbp + -664 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -664 ], r10
    mov r10, [ rbp + -504 ]
    mov r11, [ rbp + -664 ]
    mov r10, r11 ## (MOVE (TEMP j) (CONST 0))
    mov [ rbp + -664 ], r11
    mov [ rbp + -504 ], r10
_l3:
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -504 ]
    mov r10, r11 ## (MOVE (TEMP _t9) (TEMP j))
    mov [ rbp + -504 ], r11
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -496 ]
    cmpq r10, r11 ## (LT (TEMP _t9) (TEMP i))
    mov [ rbp + -496 ], r11
    mov [ rbp + -24 ], r10
    pushq rax
    mov rax, [ rbp + -680 ]
    setl al ## (LT (TEMP _t9) (TEMP i))
    mov [ rbp + -680 ], rax
    popq rax
    mov r10, [ rbp + -672 ]
    pushq rax
    mov rax, [ rbp + -680 ]
    movzx r10, al ## (LT (TEMP _t9) (TEMP i))
    mov [ rbp + -680 ], rax
    popq rax
    mov [ rbp + -672 ], r10
    mov r10, [ rbp + -672 ]
    cmpq r10, 1 ## (CJUMP (LT (TEMP _t9) (TEMP i)) _l4)
    mov [ rbp + -672 ], r10
    je _l4 ## (CJUMP (LT (TEMP _t9) (TEMP i)) _l4)
_l5:
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -496 ]
    mov r10, r11 ## (MOVE (TEMP _t22) (TEMP i))
    mov [ rbp + -496 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -624 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -624 ], r10
    mov r10, [ rbp + -632 ]
    mov r11, [ rbp + -152 ]
    mov r10, r11 ## (ADD (TEMP _t22) (CONST 1))
    mov [ rbp + -152 ], r11
    mov [ rbp + -632 ], r10
    mov r10, [ rbp + -632 ]
    mov r11, [ rbp + -624 ]
    addq r10, r11 ## (ADD (TEMP _t22) (CONST 1))
    mov [ rbp + -624 ], r11
    mov [ rbp + -632 ], r10
    mov r10, [ rbp + -496 ]
    mov r11, [ rbp + -632 ]
    mov r10, r11 ## (MOVE (TEMP i) (ADD (TEMP _t22) (CONST 1)))
    mov [ rbp + -632 ], r11
    mov [ rbp + -496 ], r10
    mov r10, [ rbp + -640 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -640 ], r10
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -640 ]
    mov r10, r11 ## (MOVE (TEMP _t24) (CONST 8))
    mov [ rbp + -640 ], r11
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -648 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -648 ], r10
    mov r10, [ rbp + -160 ]
    mov r11, [ rbp + -648 ]
    mov r10, r11 ## (MOVE (TEMP _t23) (CONST 0))
    mov [ rbp + -648 ], r11
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -472 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -472 ], r10
    mov r10, [ rbp + -480 ]
    mov r11, [ rbp + -160 ]
    mov r10, r11 ## (ADD (TEMP _t23) (CONST 1))
    mov [ rbp + -160 ], r11
    mov [ rbp + -480 ], r10
    mov r10, [ rbp + -480 ]
    mov r11, [ rbp + -472 ]
    addq r10, r11 ## (ADD (TEMP _t23) (CONST 1))
    mov [ rbp + -472 ], r11
    mov [ rbp + -480 ], r10
    mov r10, [ rbp + -488 ]
    mov r11, [ rbp + -168 ]
    mov r10, r11 ## (MUL (TEMP _t24) (ADD (TEMP _t23) (CONST 1)))
    mov [ rbp + -168 ], r11
    mov [ rbp + -488 ], r10
    mov r10, [ rbp + -488 ]
    mov r11, [ rbp + -480 ]
    imulq r10, r11 ## (MUL (TEMP _t24) (ADD (TEMP _t23) (CONST 1)))
    mov [ rbp + -480 ], r11
    mov [ rbp + -488 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -488 ]
    mov r10, r11 ## (MOVE (TEMP _t25) (MUL (TEMP _t24) (ADD (TEMP _t23) (CONST 1))))
    mov [ rbp + -488 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -176 ]
    mov rdi, r10 ## (CALL_STMT _t26 (NAME _xi_alloc) (TEMP _t25))
    mov [ rbp + -176 ], r10
    callq _xi_alloc ## (CALL_STMT _t26 (NAME _xi_alloc) (TEMP _t25))
    mov r10, [ rbp + -120 ]
    mov r10, rax ## (CALL_STMT _t26 (NAME _xi_alloc) (TEMP _t25))
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -944 ]
    mov r11, [ rbp + -120 ]
    mov r10, r11 ## (MOVE (TEMP _t2) (TEMP _t26))
    mov [ rbp + -120 ], r11
    mov [ rbp + -944 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -944 ]
    mov r10, r11 ## (MOVE (TEMP _t27) (TEMP _t2))
    mov [ rbp + -944 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -576 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -576 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -576 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t27)) (CONST 0))
    mov [ rbp + -576 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -944 ]
    mov r10, r11 ## (MOVE (TEMP _t28) (TEMP _t2))
    mov [ rbp + -944 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -584 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -584 ], r10
    mov r10, [ rbp + -592 ]
    mov r11, [ rbp + -136 ]
    mov r10, r11 ## (ADD (TEMP _t28) (CONST 8))
    mov [ rbp + -136 ], r11
    mov [ rbp + -592 ], r10
    mov r10, [ rbp + -592 ]
    mov r11, [ rbp + -584 ]
    addq r10, r11 ## (ADD (TEMP _t28) (CONST 8))
    mov [ rbp + -584 ], r11
    mov [ rbp + -592 ], r10
    mov r10, [ rbp + -928 ]
    mov r11, [ rbp + -592 ]
    mov r10, r11 ## (MOVE (TEMP _t3) (ADD (TEMP _t28) (CONST 8)))
    mov [ rbp + -592 ], r11
    mov [ rbp + -928 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -928 ]
    mov r10, r11 ## (MOVE (TEMP _t29) (TEMP _t3))
    mov [ rbp + -928 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -144 ]
    mov rdi, r10 ## (CALL_STMT _t30 (NAME _Iprintln_pai) (TEMP _t29))
    mov [ rbp + -144 ], r10
    callq _Iprintln_pai ## (CALL_STMT _t30 (NAME _Iprintln_pai) (TEMP _t29))
    mov r10, [ rbp + -96 ]
    mov r10, rax ## (CALL_STMT _t30 (NAME _Iprintln_pai) (TEMP _t29))
    mov [ rbp + -96 ], r10
    jmp _l0 ## (JUMP (NAME _l0))
_l4:
    mov r10, [ rbp + -600 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -600 ], r10
    mov r10, [ rbp + -784 ]
    mov r11, [ rbp + -600 ]
    mov r10, r11 ## (MOVE (TEMP _t11) (CONST 8))
    mov [ rbp + -600 ], r11
    mov [ rbp + -784 ], r10
    mov r10, [ rbp + -552 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -552 ], r10
    mov r10, [ rbp + -848 ]
    mov r11, [ rbp + -552 ]
    mov r10, r11 ## (MOVE (TEMP _t10) (CONST 1))
    mov [ rbp + -552 ], r11
    mov [ rbp + -848 ], r10
    mov r10, [ rbp + -560 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -560 ], r10
    mov r10, [ rbp + -568 ]
    mov r11, [ rbp + -848 ]
    mov r10, r11 ## (ADD (TEMP _t10) (CONST 1))
    mov [ rbp + -848 ], r11
    mov [ rbp + -568 ], r10
    mov r10, [ rbp + -568 ]
    mov r11, [ rbp + -560 ]
    addq r10, r11 ## (ADD (TEMP _t10) (CONST 1))
    mov [ rbp + -560 ], r11
    mov [ rbp + -568 ], r10
    mov r10, [ rbp + -864 ]
    mov r11, [ rbp + -784 ]
    mov r10, r11 ## (MUL (TEMP _t11) (ADD (TEMP _t10) (CONST 1)))
    mov [ rbp + -784 ], r11
    mov [ rbp + -864 ], r10
    mov r10, [ rbp + -864 ]
    mov r11, [ rbp + -568 ]
    imulq r10, r11 ## (MUL (TEMP _t11) (ADD (TEMP _t10) (CONST 1)))
    mov [ rbp + -568 ], r11
    mov [ rbp + -864 ], r10
    mov r10, [ rbp + -800 ]
    mov r11, [ rbp + -864 ]
    mov r10, r11 ## (MOVE (TEMP _t12) (MUL (TEMP _t11) (ADD (TEMP _t10) (CONST 1))))
    mov [ rbp + -864 ], r11
    mov [ rbp + -800 ], r10
    mov r10, [ rbp + -800 ]
    mov rdi, r10 ## (CALL_STMT _t13 (NAME _xi_alloc) (TEMP _t12))
    mov [ rbp + -800 ], r10
    callq _xi_alloc ## (CALL_STMT _t13 (NAME _xi_alloc) (TEMP _t12))
    mov r10, [ rbp + -808 ]
    mov r10, rax ## (CALL_STMT _t13 (NAME _xi_alloc) (TEMP _t12))
    mov [ rbp + -808 ], r10
    mov r10, [ rbp + -920 ]
    mov r11, [ rbp + -808 ]
    mov r10, r11 ## (MOVE (TEMP _t0) (TEMP _t13))
    mov [ rbp + -808 ], r11
    mov [ rbp + -920 ], r10
    mov r10, [ rbp + -816 ]
    mov r11, [ rbp + -920 ]
    mov r10, r11 ## (MOVE (TEMP _t14) (TEMP _t0))
    mov [ rbp + -920 ], r11
    mov [ rbp + -816 ], r10
    mov r10, [ rbp + -872 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -872 ], r10
    mov r10, [ rbp + -816 ]
    mov r11, [ rbp + -872 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t14)) (CONST 1))
    mov [ rbp + -872 ], r11
    mov [ rbp + -816 ], r10
    mov r10, [ rbp + -752 ]
    mov r11, [ rbp + -920 ]
    mov r10, r11 ## (MOVE (TEMP _t15) (TEMP _t0))
    mov [ rbp + -920 ], r11
    mov [ rbp + -752 ], r10
    mov r10, [ rbp + -880 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -880 ], r10
    mov r10, [ rbp + -888 ]
    mov r11, [ rbp + -752 ]
    mov r10, r11 ## (ADD (TEMP _t15) (CONST 8))
    mov [ rbp + -752 ], r11
    mov [ rbp + -888 ], r10
    mov r10, [ rbp + -888 ]
    mov r11, [ rbp + -880 ]
    addq r10, r11 ## (ADD (TEMP _t15) (CONST 8))
    mov [ rbp + -880 ], r11
    mov [ rbp + -888 ], r10
    mov r10, [ rbp + -912 ]
    mov r11, [ rbp + -888 ]
    mov r10, r11 ## (MOVE (TEMP _t1) (ADD (TEMP _t15) (CONST 8)))
    mov [ rbp + -888 ], r11
    mov [ rbp + -912 ], r10
    mov r10, [ rbp + -768 ]
    mov r11, [ rbp + -912 ]
    mov r10, r11 ## (MOVE (TEMP _t17) (TEMP _t1))
    mov [ rbp + -912 ], r11
    mov [ rbp + -768 ], r10
    mov r10, [ rbp + -936 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -936 ], r10
    mov r10, [ rbp + -760 ]
    mov r11, [ rbp + -936 ]
    mov r10, r11 ## (MOVE (TEMP _t16) (CONST 8))
    mov [ rbp + -936 ], r11
    mov [ rbp + -760 ], r10
    mov r10, [ rbp + -952 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -952 ], r10
    mov r10, [ rbp + -960 ]
    mov r11, [ rbp + -760 ]
    mov r10, r11 ## (MUL (TEMP _t16) (CONST 0))
    mov [ rbp + -760 ], r11
    mov [ rbp + -960 ], r10
    mov r10, [ rbp + -960 ]
    mov r11, [ rbp + -952 ]
    imulq r10, r11 ## (MUL (TEMP _t16) (CONST 0))
    mov [ rbp + -952 ], r11
    mov [ rbp + -960 ], r10
    mov r10, [ rbp + -976 ]
    mov r11, [ rbp + -768 ]
    mov r10, r11 ## (ADD (TEMP _t17) (MUL (TEMP _t16) (CONST 0)))
    mov [ rbp + -768 ], r11
    mov [ rbp + -976 ], r10
    mov r10, [ rbp + -976 ]
    mov r11, [ rbp + -960 ]
    addq r10, r11 ## (ADD (TEMP _t17) (MUL (TEMP _t16) (CONST 0)))
    mov [ rbp + -960 ], r11
    mov [ rbp + -976 ], r10
    mov r10, [ rbp + -776 ]
    mov r11, [ rbp + -976 ]
    mov r10, r11 ## (MOVE (TEMP _t18) (ADD (TEMP _t17) (MUL (TEMP _t16) (CONST 0))))
    mov [ rbp + -976 ], r11
    mov [ rbp + -776 ], r10
    mov r10, [ rbp + -896 ]
    movabsq r10, 42 ## (CONST 42)
    mov [ rbp + -896 ], r10
    mov r10, [ rbp + -776 ]
    mov r11, [ rbp + -896 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t18)) (CONST 42))
    mov [ rbp + -896 ], r11
    mov [ rbp + -776 ], r10
    mov r10, [ rbp + -736 ]
    mov r11, [ rbp + -912 ]
    mov r10, r11 ## (MOVE (TEMP _t19) (TEMP _t1))
    mov [ rbp + -912 ], r11
    mov [ rbp + -736 ], r10
    mov r10, [ rbp + -736 ]
    mov rdi, r10 ## (CALL_STMT _t20 (NAME _Iprint_pai) (TEMP _t19))
    mov [ rbp + -736 ], r10
    callq _Iprint_pai ## (CALL_STMT _t20 (NAME _Iprint_pai) (TEMP _t19))
    mov r10, [ rbp + -184 ]
    mov r10, rax ## (CALL_STMT _t20 (NAME _Iprint_pai) (TEMP _t19))
    mov [ rbp + -184 ], r10
    mov r10, [ rbp + -192 ]
    mov r11, [ rbp + -504 ]
    mov r10, r11 ## (MOVE (TEMP _t21) (TEMP j))
    mov [ rbp + -504 ], r11
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -904 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -904 ], r10
    mov r10, [ rbp + -720 ]
    mov r11, [ rbp + -192 ]
    mov r10, r11 ## (ADD (TEMP _t21) (CONST 1))
    mov [ rbp + -192 ], r11
    mov [ rbp + -720 ], r10
    mov r10, [ rbp + -720 ]
    mov r11, [ rbp + -904 ]
    addq r10, r11 ## (ADD (TEMP _t21) (CONST 1))
    mov [ rbp + -904 ], r11
    mov [ rbp + -720 ], r10
    mov r10, [ rbp + -504 ]
    mov r11, [ rbp + -720 ]
    mov r10, r11 ## (MOVE (TEMP j) (ADD (TEMP _t21) (CONST 1)))
    mov [ rbp + -720 ], r11
    mov [ rbp + -504 ], r10
    jmp _l3 ## (JUMP (NAME _l3))
_l7:
    mov r10, [ rbp + -688 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -688 ], r10
    mov r10, [ rbp + -504 ]
    mov r11, [ rbp + -688 ]
    mov r10, r11 ## (MOVE (TEMP j) (CONST 0))
    mov [ rbp + -688 ], r11
    mov [ rbp + -504 ], r10
_l9:
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -504 ]
    mov r10, r11 ## (MOVE (TEMP _t32) (TEMP j))
    mov [ rbp + -504 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -496 ]
    cmpq r10, r11 ## (LEQ (TEMP _t32) (TEMP i))
    mov [ rbp + -496 ], r11
    mov [ rbp + -112 ], r10
    pushq rax
    mov rax, [ rbp + -704 ]
    setle al ## (LEQ (TEMP _t32) (TEMP i))
    mov [ rbp + -704 ], rax
    popq rax
    mov r10, [ rbp + -696 ]
    pushq rax
    mov rax, [ rbp + -704 ]
    movzx r10, al ## (LEQ (TEMP _t32) (TEMP i))
    mov [ rbp + -704 ], rax
    popq rax
    mov [ rbp + -696 ], r10
    mov r10, [ rbp + -696 ]
    cmpq r10, 1 ## (CJUMP (LEQ (TEMP _t32) (TEMP i)) _l10)
    mov [ rbp + -696 ], r10
    je _l10 ## (CJUMP (LEQ (TEMP _t32) (TEMP i)) _l10)
_l11:
    mov r10, [ rbp + -416 ]
    mov r11, [ rbp + -496 ]
    mov r10, r11 ## (MOVE (TEMP _t45) (TEMP i))
    mov [ rbp + -496 ], r11
    mov [ rbp + -416 ], r10
    mov r10, [ rbp + -712 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -712 ], r10
    mov r10, [ rbp + -824 ]
    mov r11, [ rbp + -416 ]
    mov r10, r11 ## (SUB (TEMP _t45) (CONST 1))
    mov [ rbp + -416 ], r11
    mov [ rbp + -824 ], r10
    mov r10, [ rbp + -824 ]
    mov r11, [ rbp + -712 ]
    subq r10, r11 ## (SUB (TEMP _t45) (CONST 1))
    mov [ rbp + -712 ], r11
    mov [ rbp + -824 ], r10
    mov r10, [ rbp + -496 ]
    mov r11, [ rbp + -824 ]
    mov r10, r11 ## (MOVE (TEMP i) (SUB (TEMP _t45) (CONST 1)))
    mov [ rbp + -824 ], r11
    mov [ rbp + -496 ], r10
    mov r10, [ rbp + -832 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -832 ], r10
    mov r10, [ rbp + -432 ]
    mov r11, [ rbp + -832 ]
    mov r10, r11 ## (MOVE (TEMP _t47) (CONST 8))
    mov [ rbp + -832 ], r11
    mov [ rbp + -432 ], r10
    mov r10, [ rbp + -840 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -840 ], r10
    mov r10, [ rbp + -424 ]
    mov r11, [ rbp + -840 ]
    mov r10, r11 ## (MOVE (TEMP _t46) (CONST 0))
    mov [ rbp + -840 ], r11
    mov [ rbp + -424 ], r10
    mov r10, [ rbp + -856 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -856 ], r10
    mov r10, [ rbp + -792 ]
    mov r11, [ rbp + -424 ]
    mov r10, r11 ## (ADD (TEMP _t46) (CONST 1))
    mov [ rbp + -424 ], r11
    mov [ rbp + -792 ], r10
    mov r10, [ rbp + -792 ]
    mov r11, [ rbp + -856 ]
    addq r10, r11 ## (ADD (TEMP _t46) (CONST 1))
    mov [ rbp + -856 ], r11
    mov [ rbp + -792 ], r10
    mov r10, [ rbp + -512 ]
    mov r11, [ rbp + -432 ]
    mov r10, r11 ## (MUL (TEMP _t47) (ADD (TEMP _t46) (CONST 1)))
    mov [ rbp + -432 ], r11
    mov [ rbp + -512 ], r10
    mov r10, [ rbp + -512 ]
    mov r11, [ rbp + -792 ]
    imulq r10, r11 ## (MUL (TEMP _t47) (ADD (TEMP _t46) (CONST 1)))
    mov [ rbp + -792 ], r11
    mov [ rbp + -512 ], r10
    mov r10, [ rbp + -384 ]
    mov r11, [ rbp + -512 ]
    mov r10, r11 ## (MOVE (TEMP _t48) (MUL (TEMP _t47) (ADD (TEMP _t46) (CONST 1))))
    mov [ rbp + -512 ], r11
    mov [ rbp + -384 ], r10
    mov r10, [ rbp + -384 ]
    mov rdi, r10 ## (CALL_STMT _t49 (NAME _xi_alloc) (TEMP _t48))
    mov [ rbp + -384 ], r10
    callq _xi_alloc ## (CALL_STMT _t49 (NAME _xi_alloc) (TEMP _t48))
    mov r10, [ rbp + -400 ]
    mov r10, rax ## (CALL_STMT _t49 (NAME _xi_alloc) (TEMP _t48))
    mov [ rbp + -400 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -400 ]
    mov r10, r11 ## (MOVE (TEMP _t6) (TEMP _t49))
    mov [ rbp + -400 ], r11
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t50) (TEMP _t6))
    mov [ rbp + -16 ], r11
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -520 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -520 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -520 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t50)) (CONST 0))
    mov [ rbp + -520 ], r11
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -272 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t51) (TEMP _t6))
    mov [ rbp + -16 ], r11
    mov [ rbp + -272 ], r10
    mov r10, [ rbp + -528 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -528 ], r10
    mov r10, [ rbp + -536 ]
    mov r11, [ rbp + -272 ]
    mov r10, r11 ## (ADD (TEMP _t51) (CONST 8))
    mov [ rbp + -272 ], r11
    mov [ rbp + -536 ], r10
    mov r10, [ rbp + -536 ]
    mov r11, [ rbp + -528 ]
    addq r10, r11 ## (ADD (TEMP _t51) (CONST 8))
    mov [ rbp + -528 ], r11
    mov [ rbp + -536 ], r10
    mov r10, [ rbp + -8 ]
    mov r11, [ rbp + -536 ]
    mov r10, r11 ## (MOVE (TEMP _t7) (ADD (TEMP _t51) (CONST 8)))
    mov [ rbp + -536 ], r11
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -280 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t52) (TEMP _t7))
    mov [ rbp + -8 ], r11
    mov [ rbp + -280 ], r10
    mov r10, [ rbp + -280 ]
    mov rdi, r10 ## (CALL_STMT _t53 (NAME _Iprintln_pai) (TEMP _t52))
    mov [ rbp + -280 ], r10
    callq _Iprintln_pai ## (CALL_STMT _t53 (NAME _Iprintln_pai) (TEMP _t52))
    mov r10, [ rbp + -288 ]
    mov r10, rax ## (CALL_STMT _t53 (NAME _Iprintln_pai) (TEMP _t52))
    mov [ rbp + -288 ], r10
    jmp _l6 ## (JUMP (NAME _l6))
_l10:
    mov r10, [ rbp + -544 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -544 ], r10
    mov r10, [ rbp + -72 ]
    mov r11, [ rbp + -544 ]
    mov r10, r11 ## (MOVE (TEMP _t34) (CONST 8))
    mov [ rbp + -544 ], r11
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -352 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -352 ], r10
    mov r10, [ rbp + -64 ]
    mov r11, [ rbp + -352 ]
    mov r10, r11 ## (MOVE (TEMP _t33) (CONST 1))
    mov [ rbp + -352 ], r11
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -360 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -360 ], r10
    mov r10, [ rbp + -368 ]
    mov r11, [ rbp + -64 ]
    mov r10, r11 ## (ADD (TEMP _t33) (CONST 1))
    mov [ rbp + -64 ], r11
    mov [ rbp + -368 ], r10
    mov r10, [ rbp + -368 ]
    mov r11, [ rbp + -360 ]
    addq r10, r11 ## (ADD (TEMP _t33) (CONST 1))
    mov [ rbp + -360 ], r11
    mov [ rbp + -368 ], r10
    mov r10, [ rbp + -376 ]
    mov r11, [ rbp + -72 ]
    mov r10, r11 ## (MUL (TEMP _t34) (ADD (TEMP _t33) (CONST 1)))
    mov [ rbp + -72 ], r11
    mov [ rbp + -376 ], r10
    mov r10, [ rbp + -376 ]
    mov r11, [ rbp + -368 ]
    imulq r10, r11 ## (MUL (TEMP _t34) (ADD (TEMP _t33) (CONST 1)))
    mov [ rbp + -368 ], r11
    mov [ rbp + -376 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -376 ]
    mov r10, r11 ## (MOVE (TEMP _t35) (MUL (TEMP _t34) (ADD (TEMP _t33) (CONST 1))))
    mov [ rbp + -376 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -80 ]
    mov rdi, r10 ## (CALL_STMT _t36 (NAME _xi_alloc) (TEMP _t35))
    mov [ rbp + -80 ], r10
    callq _xi_alloc ## (CALL_STMT _t36 (NAME _xi_alloc) (TEMP _t35))
    mov r10, [ rbp + -88 ]
    mov r10, rax ## (CALL_STMT _t36 (NAME _xi_alloc) (TEMP _t35))
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -984 ]
    mov r11, [ rbp + -88 ]
    mov r10, r11 ## (MOVE (TEMP _t4) (TEMP _t36))
    mov [ rbp + -88 ], r11
    mov [ rbp + -984 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -984 ]
    mov r10, r11 ## (MOVE (TEMP _t37) (TEMP _t4))
    mov [ rbp + -984 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -392 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -392 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -392 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t37)) (CONST 1))
    mov [ rbp + -392 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -984 ]
    mov r10, r11 ## (MOVE (TEMP _t38) (TEMP _t4))
    mov [ rbp + -984 ], r11
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -304 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -304 ], r10
    mov r10, [ rbp + -312 ]
    mov r11, [ rbp + -48 ]
    mov r10, r11 ## (ADD (TEMP _t38) (CONST 8))
    mov [ rbp + -48 ], r11
    mov [ rbp + -312 ], r10
    mov r10, [ rbp + -312 ]
    mov r11, [ rbp + -304 ]
    addq r10, r11 ## (ADD (TEMP _t38) (CONST 8))
    mov [ rbp + -304 ], r11
    mov [ rbp + -312 ], r10
    mov r10, [ rbp + -968 ]
    mov r11, [ rbp + -312 ]
    mov r10, r11 ## (MOVE (TEMP _t5) (ADD (TEMP _t38) (CONST 8)))
    mov [ rbp + -312 ], r11
    mov [ rbp + -968 ], r10
    mov r10, [ rbp + -440 ]
    mov r11, [ rbp + -968 ]
    mov r10, r11 ## (MOVE (TEMP _t40) (TEMP _t5))
    mov [ rbp + -968 ], r11
    mov [ rbp + -440 ], r10
    mov r10, [ rbp + -320 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -320 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -320 ]
    mov r10, r11 ## (MOVE (TEMP _t39) (CONST 8))
    mov [ rbp + -320 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -328 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -328 ], r10
    mov r10, [ rbp + -336 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MUL (TEMP _t39) (CONST 0))
    mov [ rbp + -56 ], r11
    mov [ rbp + -336 ], r10
    mov r10, [ rbp + -336 ]
    mov r11, [ rbp + -328 ]
    imulq r10, r11 ## (MUL (TEMP _t39) (CONST 0))
    mov [ rbp + -328 ], r11
    mov [ rbp + -336 ], r10
    mov r10, [ rbp + -344 ]
    mov r11, [ rbp + -440 ]
    mov r10, r11 ## (ADD (TEMP _t40) (MUL (TEMP _t39) (CONST 0)))
    mov [ rbp + -440 ], r11
    mov [ rbp + -344 ], r10
    mov r10, [ rbp + -344 ]
    mov r11, [ rbp + -336 ]
    addq r10, r11 ## (ADD (TEMP _t40) (MUL (TEMP _t39) (CONST 0)))
    mov [ rbp + -336 ], r11
    mov [ rbp + -344 ], r10
    mov r10, [ rbp + -448 ]
    mov r11, [ rbp + -344 ]
    mov r10, r11 ## (MOVE (TEMP _t41) (ADD (TEMP _t40) (MUL (TEMP _t39) (CONST 0))))
    mov [ rbp + -344 ], r11
    mov [ rbp + -448 ], r10
    mov r10, [ rbp + -208 ]
    movabsq r10, 42 ## (CONST 42)
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -448 ]
    mov r11, [ rbp + -208 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t41)) (CONST 42))
    mov [ rbp + -208 ], r11
    mov [ rbp + -448 ], r10
    mov r10, [ rbp + -456 ]
    mov r11, [ rbp + -968 ]
    mov r10, r11 ## (MOVE (TEMP _t42) (TEMP _t5))
    mov [ rbp + -968 ], r11
    mov [ rbp + -456 ], r10
    mov r10, [ rbp + -456 ]
    mov rdi, r10 ## (CALL_STMT _t43 (NAME _Iprint_pai) (TEMP _t42))
    mov [ rbp + -456 ], r10
    callq _Iprint_pai ## (CALL_STMT _t43 (NAME _Iprint_pai) (TEMP _t42))
    mov r10, [ rbp + -464 ]
    mov r10, rax ## (CALL_STMT _t43 (NAME _Iprint_pai) (TEMP _t42))
    mov [ rbp + -464 ], r10
    mov r10, [ rbp + -408 ]
    mov r11, [ rbp + -504 ]
    mov r10, r11 ## (MOVE (TEMP _t44) (TEMP j))
    mov [ rbp + -504 ], r11
    mov [ rbp + -408 ], r10
    mov r10, [ rbp + -216 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -216 ], r10
    mov r10, [ rbp + -224 ]
    mov r11, [ rbp + -408 ]
    mov r10, r11 ## (ADD (TEMP _t44) (CONST 1))
    mov [ rbp + -408 ], r11
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -224 ]
    mov r11, [ rbp + -216 ]
    addq r10, r11 ## (ADD (TEMP _t44) (CONST 1))
    mov [ rbp + -216 ], r11
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -504 ]
    mov r11, [ rbp + -224 ]
    mov r10, r11 ## (MOVE (TEMP j) (ADD (TEMP _t44) (CONST 1)))
    mov [ rbp + -224 ], r11
    mov [ rbp + -504 ], r10
    jmp _l9 ## (JUMP (NAME _l9))
end__Imain_paai:
    addq rsp, 984
    mov rsp, rbp
    popq rbp
    retq
