.intel_syntax noprefix
.globl _Imain_paai
.align 4
_IhandleX_t2ibi:
    pushq rbp
    mov rbp, rsp
    subq rsp, 168
_l65:
    mov r10, [ rbp + -80 ]
    mov r10, rdi ## (MOVE (TEMP peek) (TEMP _ARG0))
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -80 ]
    mov r10, r11 ## (MOVE (TEMP _t30) (TEMP peek))
    mov [ rbp + -80 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -24 ]
    movabsq r10, 67 ## (CONST 67)
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -24 ]
    cmpq r10, r11 ## (EQ (TEMP _t30) (CONST 67))
    mov [ rbp + -24 ], r11
    mov [ rbp + -128 ], r10
    pushq rax
    mov rax, [ rbp + -120 ]
    setz al ## (EQ (TEMP _t30) (CONST 67))
    mov [ rbp + -120 ], rax
    popq rax
    mov r10, [ rbp + -40 ]
    pushq rax
    mov rax, [ rbp + -120 ]
    movzx r10, al ## (EQ (TEMP _t30) (CONST 67))
    mov [ rbp + -120 ], rax
    popq rax
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -40 ]
    cmpq r10, 1 ## (CJUMP (EQ (TEMP _t30) (CONST 67)) _l6)
    mov [ rbp + -40 ], r10
    je _l6 ## (CJUMP (EQ (TEMP _t30) (CONST 67)) _l6)
_l7:
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -80 ]
    mov r10, r11 ## (MOVE (TEMP _t31) (TEMP peek))
    mov [ rbp + -80 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -136 ]
    movabsq r10, 76 ## (CONST 76)
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -136 ]
    cmpq r10, r11 ## (EQ (TEMP _t31) (CONST 76))
    mov [ rbp + -136 ], r11
    mov [ rbp + -144 ], r10
    pushq rax
    mov rax, [ rbp + -160 ]
    setz al ## (EQ (TEMP _t31) (CONST 76))
    mov [ rbp + -160 ], rax
    popq rax
    mov r10, [ rbp + -152 ]
    pushq rax
    mov rax, [ rbp + -160 ]
    movzx r10, al ## (EQ (TEMP _t31) (CONST 76))
    mov [ rbp + -160 ], rax
    popq rax
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -152 ]
    cmpq r10, 1 ## (CJUMP (EQ (TEMP _t31) (CONST 76)) _l9)
    mov [ rbp + -152 ], r10
    je _l9 ## (CJUMP (EQ (TEMP _t31) (CONST 76)) _l9)
_l10:
    mov r10, [ rbp + -104 ]
    movabsq r10, 10 ## (CONST 10)
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -104 ]
    mov r10, r11 ## (MOVE (TEMP _t6) (CONST 10))
    mov [ rbp + -104 ], r11
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -56 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -8 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t7) (CONST 0))
    mov [ rbp + -56 ], r11
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -16 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t6))
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -8 ]
    mov rdx, r10 ## (MOVE (TEMP _RET1) (TEMP _t7))
    mov [ rbp + -8 ], r10
    jmp end__IhandleX_t2ibi ## (RETURN ())
_l66:
_l11:
_l8:
    jmp end__IhandleX_t2ibi ## (RETURN ())
_l9:
    mov r10, [ rbp + -64 ]
    movabsq r10, 40 ## (CONST 40)
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -64 ]
    mov r10, r11 ## (MOVE (TEMP _t8) (CONST 40))
    mov [ rbp + -64 ], r11
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -72 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -72 ]
    mov r10, r11 ## (MOVE (TEMP _t9) (CONST 1))
    mov [ rbp + -72 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -48 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t8))
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -32 ]
    mov rdx, r10 ## (MOVE (TEMP _RET1) (TEMP _t9))
    mov [ rbp + -32 ], r10
    jmp end__IhandleX_t2ibi ## (RETURN ())
_l6:
    mov r10, [ rbp + -88 ]
    movabsq r10, 90 ## (CONST 90)
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -88 ]
    mov r10, r11 ## (MOVE (TEMP _t10) (CONST 90))
    mov [ rbp + -88 ], r11
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -96 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -96 ]
    mov r10, r11 ## (MOVE (TEMP _t11) (CONST 1))
    mov [ rbp + -96 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -168 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t10))
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -112 ]
    mov rdx, r10 ## (MOVE (TEMP _RET1) (TEMP _t11))
    mov [ rbp + -112 ], r10
    jmp end__IhandleX_t2ibi ## (RETURN ())
end__IhandleX_t2ibi:
    addq rsp, 168
    mov rsp, rbp
    popq rbp
    retq
_Imain_paai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 320
_l70:
    mov r10, [ rbp + -72 ]
    mov r10, rdi ## (MOVE (TEMP args) (TEMP _ARG0))
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -216 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -216 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -216 ]
    mov r10, r11 ## (MOVE (TEMP i) (CONST 0))
    mov [ rbp + -216 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -72 ]
    mov r10, r11 ## (MOVE (TEMP _t77) (TEMP args))
    mov [ rbp + -72 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -224 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -232 ]
    mov r11, [ rbp + -112 ]
    mov r10, r11 ## (SUB (TEMP _t77) (CONST 8))
    mov [ rbp + -112 ], r11
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -232 ]
    mov r11, [ rbp + -224 ]
    subq r10, r11 ## (SUB (TEMP _t77) (CONST 8))
    mov [ rbp + -224 ], r11
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -104 ]
    mov r11, [ rbp + -232 ]
    mov r10, [ r11 ] ## (MOVE (TEMP size) (MEM (SUB (TEMP _t77) (CONST 8))))
    mov [ rbp + -232 ], r11
    mov [ rbp + -104 ], r10
_l58:
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (MOVE (TEMP _t78) (TEMP i))
    mov [ rbp + -32 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -104 ]
    cmpq r10, r11 ## (LT (TEMP _t78) (TEMP size))
    mov [ rbp + -104 ], r11
    mov [ rbp + -120 ], r10
    pushq rax
    mov rax, [ rbp + -248 ]
    setl al ## (LT (TEMP _t78) (TEMP size))
    mov [ rbp + -248 ], rax
    popq rax
    mov r10, [ rbp + -240 ]
    pushq rax
    mov rax, [ rbp + -248 ]
    movzx r10, al ## (LT (TEMP _t78) (TEMP size))
    mov [ rbp + -248 ], rax
    popq rax
    mov [ rbp + -240 ], r10
    mov r10, [ rbp + -240 ]
    cmpq r10, 1 ## (CJUMP (LT (TEMP _t78) (TEMP size)) _l59)
    mov [ rbp + -240 ], r10
    je _l59 ## (CJUMP (LT (TEMP _t78) (TEMP size)) _l59)
_l60:
    jmp end__Imain_paai ## (RETURN ())
_l59:
    mov r10, [ rbp + -256 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (MOVE (TEMP _t26) (TEMP i))
    mov [ rbp + -32 ], r11
    mov [ rbp + -256 ], r10
    mov r10, [ rbp + -288 ]
    mov r11, [ rbp + -72 ]
    mov r10, r11 ## (MOVE (TEMP _t25) (TEMP args))
    mov [ rbp + -72 ], r11
    mov [ rbp + -288 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -288 ]
    mov r10, r11 ## (MOVE (TEMP _t79) (TEMP _t25))
    mov [ rbp + -288 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -168 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -128 ]
    mov r10, r11 ## (SUB (TEMP _t79) (CONST 8))
    mov [ rbp + -128 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -168 ]
    subq r10, r11 ## (SUB (TEMP _t79) (CONST 8))
    mov [ rbp + -168 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -264 ]
    mov r11, [ rbp + -176 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t27) (MEM (SUB (TEMP _t79) (CONST 8))))
    mov [ rbp + -176 ], r11
    mov [ rbp + -264 ], r10
    mov r10, [ rbp + -184 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -184 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -184 ]
    mov r10, r11 ## (MOVE (TEMP _t80) (CONST 0))
    mov [ rbp + -184 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -256 ]
    cmpq r10, r11 ## (LEQ (TEMP _t80) (TEMP _t26))
    mov [ rbp + -256 ], r11
    mov [ rbp + -136 ], r10
    pushq rax
    mov rax, [ rbp + -200 ]
    setle al ## (LEQ (TEMP _t80) (TEMP _t26))
    mov [ rbp + -200 ], rax
    popq rax
    mov r10, [ rbp + -192 ]
    pushq rax
    mov rax, [ rbp + -200 ]
    movzx r10, al ## (LEQ (TEMP _t80) (TEMP _t26))
    mov [ rbp + -200 ], rax
    popq rax
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -192 ]
    mov r10, r11 ## (MOVE (TEMP _t82) (LEQ (TEMP _t80) (TEMP _t26)))
    mov [ rbp + -192 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -256 ]
    mov r10, r11 ## (MOVE (TEMP _t81) (TEMP _t26))
    mov [ rbp + -256 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -264 ]
    cmpq r10, r11 ## (LT (TEMP _t81) (TEMP _t27))
    mov [ rbp + -264 ], r11
    mov [ rbp + -144 ], r10
    pushq rax
    mov rax, [ rbp + -64 ]
    setl al ## (LT (TEMP _t81) (TEMP _t27))
    mov [ rbp + -64 ], rax
    popq rax
    mov r10, [ rbp + -208 ]
    pushq rax
    mov rax, [ rbp + -64 ]
    movzx r10, al ## (LT (TEMP _t81) (TEMP _t27))
    mov [ rbp + -64 ], rax
    popq rax
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -152 ]
    mov r10, r11 ## (AND (TEMP _t82) (LT (TEMP _t81) (TEMP _t27)))
    mov [ rbp + -152 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -208 ]
    andq r10, r11 ## (AND (TEMP _t82) (LT (TEMP _t81) (TEMP _t27)))
    mov [ rbp + -208 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -80 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t82) (LT (TEMP _t81) (TEMP _t27))) _l61)
    mov [ rbp + -80 ], r10
    je _l61 ## (CJUMP (AND (TEMP _t82) (LT (TEMP _t81) (TEMP _t27))) _l61)
_l62:
    callq _xi_out_of_bounds ## (CALL_STMT _t83 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -160 ]
    mov r10, rax ## (CALL_STMT _t83 (NAME _xi_out_of_bounds))
    mov [ rbp + -160 ], r10
_l61:
    mov r10, [ rbp + -304 ]
    mov r11, [ rbp + -288 ]
    mov r10, r11 ## (MOVE (TEMP _t85) (TEMP _t25))
    mov [ rbp + -288 ], r11
    mov [ rbp + -304 ], r10
    mov r10, [ rbp + -296 ]
    mov r11, [ rbp + -256 ]
    mov r10, r11 ## (MOVE (TEMP _t84) (TEMP _t26))
    mov [ rbp + -256 ], r11
    mov [ rbp + -296 ], r10
    mov r10, [ rbp + -88 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -296 ]
    mov r10, r11 ## (MUL (TEMP _t84) (CONST 8))
    mov [ rbp + -296 ], r11
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -88 ]
    imulq r10, r11 ## (MUL (TEMP _t84) (CONST 8))
    mov [ rbp + -88 ], r11
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -304 ]
    mov r10, r11 ## (ADD (TEMP _t85) (MUL (TEMP _t84) (CONST 8)))
    mov [ rbp + -304 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -96 ]
    addq r10, r11 ## (ADD (TEMP _t85) (MUL (TEMP _t84) (CONST 8)))
    mov [ rbp + -96 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -312 ]
    mov r11, [ rbp + -40 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t86) (MEM (ADD (TEMP _t85) (MUL (TEMP _t84) (CONST 8)))))
    mov [ rbp + -40 ], r11
    mov [ rbp + -312 ], r10
    mov r10, [ rbp + -312 ]
    mov rdi, r10 ## (CALL_STMT _t87 (NAME _Iconvert_iai) (TEMP _t86))
    mov [ rbp + -312 ], r10
    callq _Iconvert_iai ## (CALL_STMT _t87 (NAME _Iconvert_iai) (TEMP _t86))
    mov r10, [ rbp + -320 ]
    mov r10, rax ## (CALL_STMT _t87 (NAME _Iconvert_iai) (TEMP _t86))
    mov [ rbp + -320 ], r10
    mov r10, [ rbp + -272 ]
    mov r11, [ rbp + -320 ]
    mov r10, r11 ## (MOVE (TEMP _t88) (TEMP _t87))
    mov [ rbp + -320 ], r11
    mov [ rbp + -272 ], r10
    mov r10, [ rbp + -272 ]
    mov rdi, r10 ## (CALL_STMT _t89 (NAME _IunparseInt_aii) (TEMP _t88))
    mov [ rbp + -272 ], r10
    callq _IunparseInt_aii ## (CALL_STMT _t89 (NAME _IunparseInt_aii) (TEMP _t88))
    mov r10, [ rbp + -280 ]
    mov r10, rax ## (CALL_STMT _t89 (NAME _IunparseInt_aii) (TEMP _t88))
    mov [ rbp + -280 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -280 ]
    mov r10, r11 ## (MOVE (TEMP _t90) (TEMP _t89))
    mov [ rbp + -280 ], r11
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -24 ]
    mov rdi, r10 ## (CALL_STMT _t91 (NAME _Iprintln_pai) (TEMP _t90))
    mov [ rbp + -24 ], r10
    callq _Iprintln_pai ## (CALL_STMT _t91 (NAME _Iprintln_pai) (TEMP _t90))
    mov r10, [ rbp + -8 ]
    mov r10, rax ## (CALL_STMT _t91 (NAME _Iprintln_pai) (TEMP _t90))
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (MOVE (TEMP _t92) (TEMP i))
    mov [ rbp + -32 ], r11
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -48 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (ADD (TEMP _t92) (CONST 1))
    mov [ rbp + -16 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -48 ]
    addq r10, r11 ## (ADD (TEMP _t92) (CONST 1))
    mov [ rbp + -48 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP i) (ADD (TEMP _t92) (CONST 1)))
    mov [ rbp + -56 ], r11
    mov [ rbp + -32 ], r10
    jmp _l58 ## (JUMP (NAME _l58))
end__Imain_paai:
    addq rsp, 320
    mov rsp, rbp
    popq rbp
    retq
_IhandleI_t2ibi:
    pushq rbp
    mov rbp, rsp
    subq rsp, 168
_l63:
    mov r10, [ rbp + -64 ]
    mov r10, rdi ## (MOVE (TEMP peek) (TEMP _ARG0))
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -64 ]
    mov r10, r11 ## (MOVE (TEMP _t28) (TEMP peek))
    mov [ rbp + -64 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -40 ]
    movabsq r10, 88 ## (CONST 88)
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -40 ]
    cmpq r10, r11 ## (EQ (TEMP _t28) (CONST 88))
    mov [ rbp + -40 ], r11
    mov [ rbp + -112 ], r10
    pushq rax
    mov rax, [ rbp + -72 ]
    setz al ## (EQ (TEMP _t28) (CONST 88))
    mov [ rbp + -72 ], rax
    popq rax
    mov r10, [ rbp + -56 ]
    pushq rax
    mov rax, [ rbp + -72 ]
    movzx r10, al ## (EQ (TEMP _t28) (CONST 88))
    mov [ rbp + -72 ], rax
    popq rax
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -56 ]
    cmpq r10, 1 ## (CJUMP (EQ (TEMP _t28) (CONST 88)) _l0)
    mov [ rbp + -56 ], r10
    je _l0 ## (CJUMP (EQ (TEMP _t28) (CONST 88)) _l0)
_l1:
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -64 ]
    mov r10, r11 ## (MOVE (TEMP _t29) (TEMP peek))
    mov [ rbp + -64 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -80 ]
    movabsq r10, 86 ## (CONST 86)
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -80 ]
    cmpq r10, r11 ## (EQ (TEMP _t29) (CONST 86))
    mov [ rbp + -80 ], r11
    mov [ rbp + -120 ], r10
    pushq rax
    mov rax, [ rbp + -96 ]
    setz al ## (EQ (TEMP _t29) (CONST 86))
    mov [ rbp + -96 ], rax
    popq rax
    mov r10, [ rbp + -88 ]
    pushq rax
    mov rax, [ rbp + -96 ]
    movzx r10, al ## (EQ (TEMP _t29) (CONST 86))
    mov [ rbp + -96 ], rax
    popq rax
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -88 ]
    cmpq r10, 1 ## (CJUMP (EQ (TEMP _t29) (CONST 86)) _l3)
    mov [ rbp + -88 ], r10
    je _l3 ## (CJUMP (EQ (TEMP _t29) (CONST 86)) _l3)
_l4:
    mov r10, [ rbp + -104 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -104 ]
    mov r10, r11 ## (MOVE (TEMP _t0) (CONST 1))
    mov [ rbp + -104 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -8 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t1) (CONST 0))
    mov [ rbp + -8 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -136 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t0))
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -128 ]
    mov rdx, r10 ## (MOVE (TEMP _RET1) (TEMP _t1))
    mov [ rbp + -128 ], r10
    jmp end__IhandleI_t2ibi ## (RETURN ())
_l64:
_l5:
_l2:
    jmp end__IhandleI_t2ibi ## (RETURN ())
_l3:
    mov r10, [ rbp + -16 ]
    movabsq r10, 4 ## (CONST 4)
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t2) (CONST 4))
    mov [ rbp + -16 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -24 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (MOVE (TEMP _t3) (CONST 1))
    mov [ rbp + -24 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -152 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t2))
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -144 ]
    mov rdx, r10 ## (MOVE (TEMP _RET1) (TEMP _t3))
    mov [ rbp + -144 ], r10
    jmp end__IhandleI_t2ibi ## (RETURN ())
_l0:
    mov r10, [ rbp + -32 ]
    movabsq r10, 9 ## (CONST 9)
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (MOVE (TEMP _t4) (CONST 9))
    mov [ rbp + -32 ], r11
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -48 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -160 ]
    mov r11, [ rbp + -48 ]
    mov r10, r11 ## (MOVE (TEMP _t5) (CONST 1))
    mov [ rbp + -48 ], r11
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -168 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t4))
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -160 ]
    mov rdx, r10 ## (MOVE (TEMP _RET1) (TEMP _t5))
    mov [ rbp + -160 ], r10
    jmp end__IhandleI_t2ibi ## (RETURN ())
end__IhandleI_t2ibi:
    addq rsp, 168
    mov rsp, rbp
    popq rbp
    retq
_Iconvert_iai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 1064
_l69:
    mov r10, [ rbp + -352 ]
    mov r10, rdi ## (MOVE (TEMP roman) (TEMP _ARG0))
    mov [ rbp + -352 ], r10
    mov r10, [ rbp + -152 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -152 ]
    mov r10, r11 ## (MOVE (TEMP value) (CONST 0))
    mov [ rbp + -152 ], r11
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -352 ]
    mov r10, r11 ## (MOVE (TEMP _t34) (TEMP roman))
    mov [ rbp + -352 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -160 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -128 ]
    mov r10, r11 ## (SUB (TEMP _t34) (CONST 8))
    mov [ rbp + -128 ], r11
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -160 ]
    subq r10, r11 ## (SUB (TEMP _t34) (CONST 8))
    mov [ rbp + -160 ], r11
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -104 ]
    mov r11, [ rbp + -168 ]
    mov r10, [ r11 ] ## (MOVE (TEMP size) (MEM (SUB (TEMP _t34) (CONST 8))))
    mov [ rbp + -168 ], r11
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -80 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -560 ]
    mov r11, [ rbp + -80 ]
    mov r10, r11 ## (MOVE (TEMP i) (CONST 0))
    mov [ rbp + -80 ], r11
    mov [ rbp + -560 ], r10
_l18:
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -560 ]
    mov r10, r11 ## (MOVE (TEMP _t35) (TEMP i))
    mov [ rbp + -560 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -104 ]
    cmpq r10, r11 ## (LT (TEMP _t35) (TEMP size))
    mov [ rbp + -104 ], r11
    mov [ rbp + -136 ], r10
    pushq rax
    mov rax, [ rbp + -8 ]
    setl al ## (LT (TEMP _t35) (TEMP size))
    mov [ rbp + -8 ], rax
    popq rax
    mov r10, [ rbp + -88 ]
    pushq rax
    mov rax, [ rbp + -8 ]
    movzx r10, al ## (LT (TEMP _t35) (TEMP size))
    mov [ rbp + -8 ], rax
    popq rax
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -88 ]
    cmpq r10, 1 ## (CJUMP (LT (TEMP _t35) (TEMP size)) _l19)
    mov [ rbp + -88 ], r10
    je _l19 ## (CJUMP (LT (TEMP _t35) (TEMP size)) _l19)
_l20:
    mov r10, [ rbp + -192 ]
    mov r11, [ rbp + -200 ]
    mov r10, r11 ## (MOVE (TEMP _t24) (TEMP value))
    mov [ rbp + -200 ], r11
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -192 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t24))
    mov [ rbp + -192 ], r10
    jmp end__Iconvert_iai ## (RETURN ())
_l19:
    mov r10, [ rbp + -968 ]
    mov r11, [ rbp + -560 ]
    mov r10, r11 ## (MOVE (TEMP _t19) (TEMP i))
    mov [ rbp + -560 ], r11
    mov [ rbp + -968 ], r10
    mov r10, [ rbp + -976 ]
    mov r11, [ rbp + -352 ]
    mov r10, r11 ## (MOVE (TEMP _t18) (TEMP roman))
    mov [ rbp + -352 ], r11
    mov [ rbp + -976 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -976 ]
    mov r10, r11 ## (MOVE (TEMP _t36) (TEMP _t18))
    mov [ rbp + -976 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -16 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -144 ]
    mov r10, r11 ## (SUB (TEMP _t36) (CONST 8))
    mov [ rbp + -144 ], r11
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -16 ]
    subq r10, r11 ## (SUB (TEMP _t36) (CONST 8))
    mov [ rbp + -16 ], r11
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -208 ]
    mov r11, [ rbp + -24 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t20) (MEM (SUB (TEMP _t36) (CONST 8))))
    mov [ rbp + -24 ], r11
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -32 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (MOVE (TEMP _t37) (CONST 0))
    mov [ rbp + -32 ], r11
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -968 ]
    cmpq r10, r11 ## (LEQ (TEMP _t37) (TEMP _t19))
    mov [ rbp + -968 ], r11
    mov [ rbp + -96 ], r10
    pushq rax
    mov rax, [ rbp + -48 ]
    setle al ## (LEQ (TEMP _t37) (TEMP _t19))
    mov [ rbp + -48 ], rax
    popq rax
    mov r10, [ rbp + -40 ]
    pushq rax
    mov rax, [ rbp + -48 ]
    movzx r10, al ## (LEQ (TEMP _t37) (TEMP _t19))
    mov [ rbp + -48 ], rax
    popq rax
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -40 ]
    mov r10, r11 ## (MOVE (TEMP _t39) (LEQ (TEMP _t37) (TEMP _t19)))
    mov [ rbp + -40 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -968 ]
    mov r10, r11 ## (MOVE (TEMP _t38) (TEMP _t19))
    mov [ rbp + -968 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -208 ]
    cmpq r10, r11 ## (LT (TEMP _t38) (TEMP _t20))
    mov [ rbp + -208 ], r11
    mov [ rbp + -112 ], r10
    pushq rax
    mov rax, [ rbp + -64 ]
    setl al ## (LT (TEMP _t38) (TEMP _t20))
    mov [ rbp + -64 ], rax
    popq rax
    mov r10, [ rbp + -56 ]
    pushq rax
    mov rax, [ rbp + -64 ]
    movzx r10, al ## (LT (TEMP _t38) (TEMP _t20))
    mov [ rbp + -64 ], rax
    popq rax
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -72 ]
    mov r11, [ rbp + -120 ]
    mov r10, r11 ## (AND (TEMP _t39) (LT (TEMP _t38) (TEMP _t20)))
    mov [ rbp + -120 ], r11
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -72 ]
    mov r11, [ rbp + -56 ]
    andq r10, r11 ## (AND (TEMP _t39) (LT (TEMP _t38) (TEMP _t20)))
    mov [ rbp + -56 ], r11
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -72 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t39) (LT (TEMP _t38) (TEMP _t20))) _l21)
    mov [ rbp + -72 ], r10
    je _l21 ## (CJUMP (AND (TEMP _t39) (LT (TEMP _t38) (TEMP _t20))) _l21)
_l22:
    callq _xi_out_of_bounds ## (CALL_STMT _t40 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -456 ]
    mov r10, rax ## (CALL_STMT _t40 (NAME _xi_out_of_bounds))
    mov [ rbp + -456 ], r10
_l21:
    mov r10, [ rbp + -472 ]
    mov r11, [ rbp + -976 ]
    mov r10, r11 ## (MOVE (TEMP _t42) (TEMP _t18))
    mov [ rbp + -976 ], r11
    mov [ rbp + -472 ], r10
    mov r10, [ rbp + -464 ]
    mov r11, [ rbp + -968 ]
    mov r10, r11 ## (MOVE (TEMP _t41) (TEMP _t19))
    mov [ rbp + -968 ], r11
    mov [ rbp + -464 ], r10
    mov r10, [ rbp + -1064 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -1064 ], r10
    mov r10, [ rbp + -984 ]
    mov r11, [ rbp + -464 ]
    mov r10, r11 ## (MUL (TEMP _t41) (CONST 8))
    mov [ rbp + -464 ], r11
    mov [ rbp + -984 ], r10
    mov r10, [ rbp + -984 ]
    mov r11, [ rbp + -1064 ]
    imulq r10, r11 ## (MUL (TEMP _t41) (CONST 8))
    mov [ rbp + -1064 ], r11
    mov [ rbp + -984 ], r10
    mov r10, [ rbp + -992 ]
    mov r11, [ rbp + -472 ]
    mov r10, r11 ## (ADD (TEMP _t42) (MUL (TEMP _t41) (CONST 8)))
    mov [ rbp + -472 ], r11
    mov [ rbp + -992 ], r10
    mov r10, [ rbp + -992 ]
    mov r11, [ rbp + -984 ]
    addq r10, r11 ## (ADD (TEMP _t42) (MUL (TEMP _t41) (CONST 8)))
    mov [ rbp + -984 ], r11
    mov [ rbp + -992 ], r10
    mov r10, [ rbp + -496 ]
    mov r11, [ rbp + -992 ]
    mov r10, [ r11 ] ## (MOVE (TEMP c) (MEM (ADD (TEMP _t42) (MUL (TEMP _t41) (CONST 8)))))
    mov [ rbp + -992 ], r11
    mov [ rbp + -496 ], r10
    mov r10, [ rbp + -1000 ]
    movabsq r10, 32 ## (CONST 32)
    mov [ rbp + -1000 ], r10
    mov r10, [ rbp + -240 ]
    mov r11, [ rbp + -1000 ]
    mov r10, r11 ## (MOVE (TEMP peek) (CONST 32))
    mov [ rbp + -1000 ], r11
    mov [ rbp + -240 ], r10
    mov r10, [ rbp + -424 ]
    mov r11, [ rbp + -560 ]
    mov r10, r11 ## (MOVE (TEMP _t44) (TEMP i))
    mov [ rbp + -560 ], r11
    mov [ rbp + -424 ], r10
    mov r10, [ rbp + -480 ]
    mov r11, [ rbp + -104 ]
    mov r10, r11 ## (MOVE (TEMP _t43) (TEMP size))
    mov [ rbp + -104 ], r11
    mov [ rbp + -480 ], r10
    mov r10, [ rbp + -1008 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -1008 ], r10
    mov r10, [ rbp + -1016 ]
    mov r11, [ rbp + -480 ]
    mov r10, r11 ## (SUB (TEMP _t43) (CONST 1))
    mov [ rbp + -480 ], r11
    mov [ rbp + -1016 ], r10
    mov r10, [ rbp + -1016 ]
    mov r11, [ rbp + -1008 ]
    subq r10, r11 ## (SUB (TEMP _t43) (CONST 1))
    mov [ rbp + -1008 ], r11
    mov [ rbp + -1016 ], r10
    mov r10, [ rbp + -424 ]
    mov r11, [ rbp + -1016 ]
    cmpq r10, r11 ## (LT (TEMP _t44) (SUB (TEMP _t43) (CONST 1)))
    mov [ rbp + -1016 ], r11
    mov [ rbp + -424 ], r10
    pushq rax
    mov rax, [ rbp + -1032 ]
    setl al ## (LT (TEMP _t44) (SUB (TEMP _t43) (CONST 1)))
    mov [ rbp + -1032 ], rax
    popq rax
    mov r10, [ rbp + -1024 ]
    pushq rax
    mov rax, [ rbp + -1032 ]
    movzx r10, al ## (LT (TEMP _t44) (SUB (TEMP _t43) (CONST 1)))
    mov [ rbp + -1032 ], rax
    popq rax
    mov [ rbp + -1024 ], r10
    mov r10, [ rbp + -1024 ]
    cmpq r10, 1 ## (CJUMP (LT (TEMP _t44) (SUB (TEMP _t43) (CONST 1))) _l23)
    mov [ rbp + -1024 ], r10
    je _l23 ## (CJUMP (LT (TEMP _t44) (SUB (TEMP _t43) (CONST 1))) _l23)
_l24:
_l25:
    mov r10, [ rbp + -304 ]
    mov r11, [ rbp + -496 ]
    mov r10, r11 ## (MOVE (TEMP _t53) (TEMP c))
    mov [ rbp + -496 ], r11
    mov [ rbp + -304 ], r10
    mov r10, [ rbp + -1040 ]
    movabsq r10, 73 ## (CONST 73)
    mov [ rbp + -1040 ], r10
    mov r10, [ rbp + -304 ]
    mov r11, [ rbp + -1040 ]
    cmpq r10, r11 ## (EQ (TEMP _t53) (CONST 73))
    mov [ rbp + -1040 ], r11
    mov [ rbp + -304 ], r10
    pushq rax
    mov rax, [ rbp + -1056 ]
    setz al ## (EQ (TEMP _t53) (CONST 73))
    mov [ rbp + -1056 ], rax
    popq rax
    mov r10, [ rbp + -1048 ]
    pushq rax
    mov rax, [ rbp + -1056 ]
    movzx r10, al ## (EQ (TEMP _t53) (CONST 73))
    mov [ rbp + -1056 ], rax
    popq rax
    mov [ rbp + -1048 ], r10
    mov r10, [ rbp + -1048 ]
    cmpq r10, 1 ## (CJUMP (EQ (TEMP _t53) (CONST 73)) _l28)
    mov [ rbp + -1048 ], r10
    je _l28 ## (CJUMP (EQ (TEMP _t53) (CONST 73)) _l28)
_l29:
    mov r10, [ rbp + -312 ]
    mov r11, [ rbp + -496 ]
    mov r10, r11 ## (MOVE (TEMP _t54) (TEMP c))
    mov [ rbp + -496 ], r11
    mov [ rbp + -312 ], r10
    mov r10, [ rbp + -856 ]
    movabsq r10, 88 ## (CONST 88)
    mov [ rbp + -856 ], r10
    mov r10, [ rbp + -312 ]
    mov r11, [ rbp + -856 ]
    cmpq r10, r11 ## (EQ (TEMP _t54) (CONST 88))
    mov [ rbp + -856 ], r11
    mov [ rbp + -312 ], r10
    pushq rax
    mov rax, [ rbp + -872 ]
    setz al ## (EQ (TEMP _t54) (CONST 88))
    mov [ rbp + -872 ], rax
    popq rax
    mov r10, [ rbp + -864 ]
    pushq rax
    mov rax, [ rbp + -872 ]
    movzx r10, al ## (EQ (TEMP _t54) (CONST 88))
    mov [ rbp + -872 ], rax
    popq rax
    mov [ rbp + -864 ], r10
    mov r10, [ rbp + -864 ]
    cmpq r10, 1 ## (CJUMP (EQ (TEMP _t54) (CONST 88)) _l31)
    mov [ rbp + -864 ], r10
    je _l31 ## (CJUMP (EQ (TEMP _t54) (CONST 88)) _l31)
_l32:
    mov r10, [ rbp + -256 ]
    mov r11, [ rbp + -496 ]
    mov r10, r11 ## (MOVE (TEMP _t55) (TEMP c))
    mov [ rbp + -496 ], r11
    mov [ rbp + -256 ], r10
    mov r10, [ rbp + -880 ]
    movabsq r10, 67 ## (CONST 67)
    mov [ rbp + -880 ], r10
    mov r10, [ rbp + -256 ]
    mov r11, [ rbp + -880 ]
    cmpq r10, r11 ## (EQ (TEMP _t55) (CONST 67))
    mov [ rbp + -880 ], r11
    mov [ rbp + -256 ], r10
    pushq rax
    mov rax, [ rbp + -896 ]
    setz al ## (EQ (TEMP _t55) (CONST 67))
    mov [ rbp + -896 ], rax
    popq rax
    mov r10, [ rbp + -888 ]
    pushq rax
    mov rax, [ rbp + -896 ]
    movzx r10, al ## (EQ (TEMP _t55) (CONST 67))
    mov [ rbp + -896 ], rax
    popq rax
    mov [ rbp + -888 ], r10
    mov r10, [ rbp + -888 ]
    cmpq r10, 1 ## (CJUMP (EQ (TEMP _t55) (CONST 67)) _l34)
    mov [ rbp + -888 ], r10
    je _l34 ## (CJUMP (EQ (TEMP _t55) (CONST 67)) _l34)
_l35:
    mov r10, [ rbp + -264 ]
    mov r11, [ rbp + -496 ]
    mov r10, r11 ## (MOVE (TEMP _t56) (TEMP c))
    mov [ rbp + -496 ], r11
    mov [ rbp + -264 ], r10
    mov r10, [ rbp + -904 ]
    movabsq r10, 86 ## (CONST 86)
    mov [ rbp + -904 ], r10
    mov r10, [ rbp + -264 ]
    mov r11, [ rbp + -904 ]
    cmpq r10, r11 ## (EQ (TEMP _t56) (CONST 86))
    mov [ rbp + -904 ], r11
    mov [ rbp + -264 ], r10
    pushq rax
    mov rax, [ rbp + -928 ]
    setz al ## (EQ (TEMP _t56) (CONST 86))
    mov [ rbp + -928 ], rax
    popq rax
    mov r10, [ rbp + -912 ]
    pushq rax
    mov rax, [ rbp + -928 ]
    movzx r10, al ## (EQ (TEMP _t56) (CONST 86))
    mov [ rbp + -928 ], rax
    popq rax
    mov [ rbp + -912 ], r10
    mov r10, [ rbp + -912 ]
    cmpq r10, 1 ## (CJUMP (EQ (TEMP _t56) (CONST 86)) _l37)
    mov [ rbp + -912 ], r10
    je _l37 ## (CJUMP (EQ (TEMP _t56) (CONST 86)) _l37)
_l38:
    mov r10, [ rbp + -272 ]
    mov r11, [ rbp + -496 ]
    mov r10, r11 ## (MOVE (TEMP _t57) (TEMP c))
    mov [ rbp + -496 ], r11
    mov [ rbp + -272 ], r10
    mov r10, [ rbp + -944 ]
    movabsq r10, 76 ## (CONST 76)
    mov [ rbp + -944 ], r10
    mov r10, [ rbp + -272 ]
    mov r11, [ rbp + -944 ]
    cmpq r10, r11 ## (EQ (TEMP _t57) (CONST 76))
    mov [ rbp + -944 ], r11
    mov [ rbp + -272 ], r10
    pushq rax
    mov rax, [ rbp + -664 ]
    setz al ## (EQ (TEMP _t57) (CONST 76))
    mov [ rbp + -664 ], rax
    popq rax
    mov r10, [ rbp + -656 ]
    pushq rax
    mov rax, [ rbp + -664 ]
    movzx r10, al ## (EQ (TEMP _t57) (CONST 76))
    mov [ rbp + -664 ], rax
    popq rax
    mov [ rbp + -656 ], r10
    mov r10, [ rbp + -656 ]
    cmpq r10, 1 ## (CJUMP (EQ (TEMP _t57) (CONST 76)) _l40)
    mov [ rbp + -656 ], r10
    je _l40 ## (CJUMP (EQ (TEMP _t57) (CONST 76)) _l40)
_l41:
    mov r10, [ rbp + -280 ]
    mov r11, [ rbp + -496 ]
    mov r10, r11 ## (MOVE (TEMP _t58) (TEMP c))
    mov [ rbp + -496 ], r11
    mov [ rbp + -280 ], r10
    mov r10, [ rbp + -672 ]
    movabsq r10, 68 ## (CONST 68)
    mov [ rbp + -672 ], r10
    mov r10, [ rbp + -280 ]
    mov r11, [ rbp + -672 ]
    cmpq r10, r11 ## (EQ (TEMP _t58) (CONST 68))
    mov [ rbp + -672 ], r11
    mov [ rbp + -280 ], r10
    pushq rax
    mov rax, [ rbp + -688 ]
    setz al ## (EQ (TEMP _t58) (CONST 68))
    mov [ rbp + -688 ], rax
    popq rax
    mov r10, [ rbp + -680 ]
    pushq rax
    mov rax, [ rbp + -688 ]
    movzx r10, al ## (EQ (TEMP _t58) (CONST 68))
    mov [ rbp + -688 ], rax
    popq rax
    mov [ rbp + -680 ], r10
    mov r10, [ rbp + -680 ]
    cmpq r10, 1 ## (CJUMP (EQ (TEMP _t58) (CONST 68)) _l43)
    mov [ rbp + -680 ], r10
    je _l43 ## (CJUMP (EQ (TEMP _t58) (CONST 68)) _l43)
_l44:
    mov r10, [ rbp + -248 ]
    mov r11, [ rbp + -496 ]
    mov r10, r11 ## (MOVE (TEMP _t59) (TEMP c))
    mov [ rbp + -496 ], r11
    mov [ rbp + -248 ], r10
    mov r10, [ rbp + -696 ]
    movabsq r10, 77 ## (CONST 77)
    mov [ rbp + -696 ], r10
    mov r10, [ rbp + -248 ]
    mov r11, [ rbp + -696 ]
    cmpq r10, r11 ## (EQ (TEMP _t59) (CONST 77))
    mov [ rbp + -696 ], r11
    mov [ rbp + -248 ], r10
    pushq rax
    mov rax, [ rbp + -712 ]
    setz al ## (EQ (TEMP _t59) (CONST 77))
    mov [ rbp + -712 ], rax
    popq rax
    mov r10, [ rbp + -704 ]
    pushq rax
    mov rax, [ rbp + -712 ]
    movzx r10, al ## (EQ (TEMP _t59) (CONST 77))
    mov [ rbp + -712 ], rax
    popq rax
    mov [ rbp + -704 ], r10
    mov r10, [ rbp + -704 ]
    cmpq r10, 1 ## (CJUMP (EQ (TEMP _t59) (CONST 77)) _l46)
    mov [ rbp + -704 ], r10
    je _l46 ## (CJUMP (EQ (TEMP _t59) (CONST 77)) _l46)
_l47:
_l48:
_l45:
_l42:
_l39:
_l36:
_l33:
_l30:
    mov r10, [ rbp + -632 ]
    mov r11, [ rbp + -560 ]
    mov r10, r11 ## (MOVE (TEMP _t76) (TEMP i))
    mov [ rbp + -560 ], r11
    mov [ rbp + -632 ], r10
    mov r10, [ rbp + -728 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -728 ], r10
    mov r10, [ rbp + -736 ]
    mov r11, [ rbp + -632 ]
    mov r10, r11 ## (ADD (TEMP _t76) (CONST 1))
    mov [ rbp + -632 ], r11
    mov [ rbp + -736 ], r10
    mov r10, [ rbp + -736 ]
    mov r11, [ rbp + -728 ]
    addq r10, r11 ## (ADD (TEMP _t76) (CONST 1))
    mov [ rbp + -728 ], r11
    mov [ rbp + -736 ], r10
    mov r10, [ rbp + -560 ]
    mov r11, [ rbp + -736 ]
    mov r10, r11 ## (MOVE (TEMP i) (ADD (TEMP _t76) (CONST 1)))
    mov [ rbp + -736 ], r11
    mov [ rbp + -560 ], r10
    jmp _l18 ## (JUMP (NAME _l18))
_l23:
    mov r10, [ rbp + -432 ]
    mov r11, [ rbp + -560 ]
    mov r10, r11 ## (MOVE (TEMP _t45) (TEMP i))
    mov [ rbp + -560 ], r11
    mov [ rbp + -432 ], r10
    mov r10, [ rbp + -512 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -512 ], r10
    mov r10, [ rbp + -528 ]
    mov r11, [ rbp + -432 ]
    mov r10, r11 ## (ADD (TEMP _t45) (CONST 1))
    mov [ rbp + -432 ], r11
    mov [ rbp + -528 ], r10
    mov r10, [ rbp + -528 ]
    mov r11, [ rbp + -512 ]
    addq r10, r11 ## (ADD (TEMP _t45) (CONST 1))
    mov [ rbp + -512 ], r11
    mov [ rbp + -528 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -528 ]
    mov r10, r11 ## (MOVE (TEMP _t22) (ADD (TEMP _t45) (CONST 1)))
    mov [ rbp + -528 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -216 ]
    mov r11, [ rbp + -352 ]
    mov r10, r11 ## (MOVE (TEMP _t21) (TEMP roman))
    mov [ rbp + -352 ], r11
    mov [ rbp + -216 ], r10
    mov r10, [ rbp + -440 ]
    mov r11, [ rbp + -216 ]
    mov r10, r11 ## (MOVE (TEMP _t46) (TEMP _t21))
    mov [ rbp + -216 ], r11
    mov [ rbp + -440 ], r10
    mov r10, [ rbp + -536 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -536 ], r10
    mov r10, [ rbp + -544 ]
    mov r11, [ rbp + -440 ]
    mov r10, r11 ## (SUB (TEMP _t46) (CONST 8))
    mov [ rbp + -440 ], r11
    mov [ rbp + -544 ], r10
    mov r10, [ rbp + -544 ]
    mov r11, [ rbp + -536 ]
    subq r10, r11 ## (SUB (TEMP _t46) (CONST 8))
    mov [ rbp + -536 ], r11
    mov [ rbp + -544 ], r10
    mov r10, [ rbp + -184 ]
    mov r11, [ rbp + -544 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t23) (MEM (SUB (TEMP _t46) (CONST 8))))
    mov [ rbp + -544 ], r11
    mov [ rbp + -184 ], r10
    mov r10, [ rbp + -552 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -552 ], r10
    mov r10, [ rbp + -448 ]
    mov r11, [ rbp + -552 ]
    mov r10, r11 ## (MOVE (TEMP _t47) (CONST 0))
    mov [ rbp + -552 ], r11
    mov [ rbp + -448 ], r10
    mov r10, [ rbp + -448 ]
    mov r11, [ rbp + -176 ]
    cmpq r10, r11 ## (LEQ (TEMP _t47) (TEMP _t22))
    mov [ rbp + -176 ], r11
    mov [ rbp + -448 ], r10
    pushq rax
    mov rax, [ rbp + -576 ]
    setle al ## (LEQ (TEMP _t47) (TEMP _t22))
    mov [ rbp + -576 ], rax
    popq rax
    mov r10, [ rbp + -568 ]
    pushq rax
    mov rax, [ rbp + -576 ]
    movzx r10, al ## (LEQ (TEMP _t47) (TEMP _t22))
    mov [ rbp + -576 ], rax
    popq rax
    mov [ rbp + -568 ], r10
    mov r10, [ rbp + -416 ]
    mov r11, [ rbp + -568 ]
    mov r10, r11 ## (MOVE (TEMP _t49) (LEQ (TEMP _t47) (TEMP _t22)))
    mov [ rbp + -568 ], r11
    mov [ rbp + -416 ], r10
    mov r10, [ rbp + -408 ]
    mov r11, [ rbp + -176 ]
    mov r10, r11 ## (MOVE (TEMP _t48) (TEMP _t22))
    mov [ rbp + -176 ], r11
    mov [ rbp + -408 ], r10
    mov r10, [ rbp + -408 ]
    mov r11, [ rbp + -184 ]
    cmpq r10, r11 ## (LT (TEMP _t48) (TEMP _t23))
    mov [ rbp + -184 ], r11
    mov [ rbp + -408 ], r10
    pushq rax
    mov rax, [ rbp + -592 ]
    setl al ## (LT (TEMP _t48) (TEMP _t23))
    mov [ rbp + -592 ], rax
    popq rax
    mov r10, [ rbp + -584 ]
    pushq rax
    mov rax, [ rbp + -592 ]
    movzx r10, al ## (LT (TEMP _t48) (TEMP _t23))
    mov [ rbp + -592 ], rax
    popq rax
    mov [ rbp + -584 ], r10
    mov r10, [ rbp + -600 ]
    mov r11, [ rbp + -416 ]
    mov r10, r11 ## (AND (TEMP _t49) (LT (TEMP _t48) (TEMP _t23)))
    mov [ rbp + -416 ], r11
    mov [ rbp + -600 ], r10
    mov r10, [ rbp + -600 ]
    mov r11, [ rbp + -584 ]
    andq r10, r11 ## (AND (TEMP _t49) (LT (TEMP _t48) (TEMP _t23)))
    mov [ rbp + -584 ], r11
    mov [ rbp + -600 ], r10
    mov r10, [ rbp + -600 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t49) (LT (TEMP _t48) (TEMP _t23))) _l26)
    mov [ rbp + -600 ], r10
    je _l26 ## (CJUMP (AND (TEMP _t49) (LT (TEMP _t48) (TEMP _t23))) _l26)
_l27:
    callq _xi_out_of_bounds ## (CALL_STMT _t50 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -232 ]
    mov r10, rax ## (CALL_STMT _t50 (NAME _xi_out_of_bounds))
    mov [ rbp + -232 ], r10
_l26:
    mov r10, [ rbp + -296 ]
    mov r11, [ rbp + -216 ]
    mov r10, r11 ## (MOVE (TEMP _t52) (TEMP _t21))
    mov [ rbp + -216 ], r11
    mov [ rbp + -296 ], r10
    mov r10, [ rbp + -288 ]
    mov r11, [ rbp + -176 ]
    mov r10, r11 ## (MOVE (TEMP _t51) (TEMP _t22))
    mov [ rbp + -176 ], r11
    mov [ rbp + -288 ], r10
    mov r10, [ rbp + -320 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -320 ], r10
    mov r10, [ rbp + -328 ]
    mov r11, [ rbp + -288 ]
    mov r10, r11 ## (MUL (TEMP _t51) (CONST 8))
    mov [ rbp + -288 ], r11
    mov [ rbp + -328 ], r10
    mov r10, [ rbp + -328 ]
    mov r11, [ rbp + -320 ]
    imulq r10, r11 ## (MUL (TEMP _t51) (CONST 8))
    mov [ rbp + -320 ], r11
    mov [ rbp + -328 ], r10
    mov r10, [ rbp + -336 ]
    mov r11, [ rbp + -296 ]
    mov r10, r11 ## (ADD (TEMP _t52) (MUL (TEMP _t51) (CONST 8)))
    mov [ rbp + -296 ], r11
    mov [ rbp + -336 ], r10
    mov r10, [ rbp + -336 ]
    mov r11, [ rbp + -328 ]
    addq r10, r11 ## (ADD (TEMP _t52) (MUL (TEMP _t51) (CONST 8)))
    mov [ rbp + -328 ], r11
    mov [ rbp + -336 ], r10
    mov r10, [ rbp + -240 ]
    mov r11, [ rbp + -336 ]
    mov r10, [ r11 ] ## (MOVE (TEMP peek) (MEM (ADD (TEMP _t52) (MUL (TEMP _t51) (CONST 8)))))
    mov [ rbp + -336 ], r11
    mov [ rbp + -240 ], r10
    jmp _l25 ## (JUMP (NAME _l25))
_l46:
    mov r10, [ rbp + -640 ]
    mov r11, [ rbp + -200 ]
    mov r10, r11 ## (MOVE (TEMP _t60) (TEMP value))
    mov [ rbp + -200 ], r11
    mov [ rbp + -640 ], r10
    mov r10, [ rbp + -344 ]
    movabsq r10, 1000 ## (CONST 1000)
    mov [ rbp + -344 ], r10
    mov r10, [ rbp + -360 ]
    mov r11, [ rbp + -640 ]
    mov r10, r11 ## (ADD (TEMP _t60) (CONST 1000))
    mov [ rbp + -640 ], r11
    mov [ rbp + -360 ], r10
    mov r10, [ rbp + -360 ]
    mov r11, [ rbp + -344 ]
    addq r10, r11 ## (ADD (TEMP _t60) (CONST 1000))
    mov [ rbp + -344 ], r11
    mov [ rbp + -360 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -360 ]
    mov r10, r11 ## (MOVE (TEMP value) (ADD (TEMP _t60) (CONST 1000)))
    mov [ rbp + -360 ], r11
    mov [ rbp + -200 ], r10
    jmp _l48 ## (JUMP (NAME _l48))
_l43:
    mov r10, [ rbp + -648 ]
    mov r11, [ rbp + -200 ]
    mov r10, r11 ## (MOVE (TEMP _t61) (TEMP value))
    mov [ rbp + -200 ], r11
    mov [ rbp + -648 ], r10
    mov r10, [ rbp + -368 ]
    movabsq r10, 500 ## (CONST 500)
    mov [ rbp + -368 ], r10
    mov r10, [ rbp + -376 ]
    mov r11, [ rbp + -648 ]
    mov r10, r11 ## (ADD (TEMP _t61) (CONST 500))
    mov [ rbp + -648 ], r11
    mov [ rbp + -376 ], r10
    mov r10, [ rbp + -376 ]
    mov r11, [ rbp + -368 ]
    addq r10, r11 ## (ADD (TEMP _t61) (CONST 500))
    mov [ rbp + -368 ], r11
    mov [ rbp + -376 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -376 ]
    mov r10, r11 ## (MOVE (TEMP value) (ADD (TEMP _t61) (CONST 500)))
    mov [ rbp + -376 ], r11
    mov [ rbp + -200 ], r10
    jmp _l45 ## (JUMP (NAME _l45))
_l40:
    mov r10, [ rbp + -824 ]
    mov r11, [ rbp + -200 ]
    mov r10, r11 ## (MOVE (TEMP _t62) (TEMP value))
    mov [ rbp + -200 ], r11
    mov [ rbp + -824 ], r10
    mov r10, [ rbp + -384 ]
    movabsq r10, 50 ## (CONST 50)
    mov [ rbp + -384 ], r10
    mov r10, [ rbp + -392 ]
    mov r11, [ rbp + -824 ]
    mov r10, r11 ## (ADD (TEMP _t62) (CONST 50))
    mov [ rbp + -824 ], r11
    mov [ rbp + -392 ], r10
    mov r10, [ rbp + -392 ]
    mov r11, [ rbp + -384 ]
    addq r10, r11 ## (ADD (TEMP _t62) (CONST 50))
    mov [ rbp + -384 ], r11
    mov [ rbp + -392 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -392 ]
    mov r10, r11 ## (MOVE (TEMP value) (ADD (TEMP _t62) (CONST 50)))
    mov [ rbp + -392 ], r11
    mov [ rbp + -200 ], r10
    jmp _l42 ## (JUMP (NAME _l42))
_l37:
    mov r10, [ rbp + -832 ]
    mov r11, [ rbp + -200 ]
    mov r10, r11 ## (MOVE (TEMP _t63) (TEMP value))
    mov [ rbp + -200 ], r11
    mov [ rbp + -832 ], r10
    mov r10, [ rbp + -400 ]
    movabsq r10, 5 ## (CONST 5)
    mov [ rbp + -400 ], r10
    mov r10, [ rbp + -920 ]
    mov r11, [ rbp + -832 ]
    mov r10, r11 ## (ADD (TEMP _t63) (CONST 5))
    mov [ rbp + -832 ], r11
    mov [ rbp + -920 ], r10
    mov r10, [ rbp + -920 ]
    mov r11, [ rbp + -400 ]
    addq r10, r11 ## (ADD (TEMP _t63) (CONST 5))
    mov [ rbp + -400 ], r11
    mov [ rbp + -920 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -920 ]
    mov r10, r11 ## (MOVE (TEMP value) (ADD (TEMP _t63) (CONST 5)))
    mov [ rbp + -920 ], r11
    mov [ rbp + -200 ], r10
    jmp _l39 ## (JUMP (NAME _l39))
_l34:
    mov r10, [ rbp + -840 ]
    mov r11, [ rbp + -240 ]
    mov r10, r11 ## (MOVE (TEMP _t64) (TEMP peek))
    mov [ rbp + -240 ], r11
    mov [ rbp + -840 ], r10
    mov r10, [ rbp + -840 ]
    mov rdi, r10 ## (CALL_STMT _t65 (NAME _IhandleC_t2ibi) (TEMP _t64))
    mov [ rbp + -840 ], r10
    callq _IhandleC_t2ibi ## (CALL_STMT _t65 (NAME _IhandleC_t2ibi) (TEMP _t64))
    mov r10, [ rbp + -848 ]
    mov r10, rax ## (CALL_STMT _t65 (NAME _IhandleC_t2ibi) (TEMP _t64))
    mov [ rbp + -848 ], r10
    mov r10, [ rbp + -224 ]
    mov r10, rax ## (MOVE (TEMP incr) (TEMP _RET0))
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -720 ]
    mov r10, rdx ## (MOVE (TEMP skip) (TEMP _RET1))
    mov [ rbp + -720 ], r10
    mov r10, [ rbp + -720 ]
    cmpq r10, 1 ## (CJUMP (TEMP skip) _l49)
    mov [ rbp + -720 ], r10
    je _l49 ## (CJUMP (TEMP skip) _l49)
_l50:
_l51:
    mov r10, [ rbp + -800 ]
    mov r11, [ rbp + -200 ]
    mov r10, r11 ## (MOVE (TEMP _t67) (TEMP value))
    mov [ rbp + -200 ], r11
    mov [ rbp + -800 ], r10
    mov r10, [ rbp + -936 ]
    mov r11, [ rbp + -800 ]
    mov r10, r11 ## (ADD (TEMP _t67) (TEMP incr))
    mov [ rbp + -800 ], r11
    mov [ rbp + -936 ], r10
    mov r10, [ rbp + -936 ]
    mov r11, [ rbp + -224 ]
    addq r10, r11 ## (ADD (TEMP _t67) (TEMP incr))
    mov [ rbp + -224 ], r11
    mov [ rbp + -936 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -936 ]
    mov r10, r11 ## (MOVE (TEMP value) (ADD (TEMP _t67) (TEMP incr)))
    mov [ rbp + -936 ], r11
    mov [ rbp + -200 ], r10
    jmp _l36 ## (JUMP (NAME _l36))
_l49:
    mov r10, [ rbp + -792 ]
    mov r11, [ rbp + -560 ]
    mov r10, r11 ## (MOVE (TEMP _t66) (TEMP i))
    mov [ rbp + -560 ], r11
    mov [ rbp + -792 ], r10
    mov r10, [ rbp + -952 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -952 ], r10
    mov r10, [ rbp + -960 ]
    mov r11, [ rbp + -792 ]
    mov r10, r11 ## (ADD (TEMP _t66) (CONST 1))
    mov [ rbp + -792 ], r11
    mov [ rbp + -960 ], r10
    mov r10, [ rbp + -960 ]
    mov r11, [ rbp + -952 ]
    addq r10, r11 ## (ADD (TEMP _t66) (CONST 1))
    mov [ rbp + -952 ], r11
    mov [ rbp + -960 ], r10
    mov r10, [ rbp + -560 ]
    mov r11, [ rbp + -960 ]
    mov r10, r11 ## (MOVE (TEMP i) (ADD (TEMP _t66) (CONST 1)))
    mov [ rbp + -960 ], r11
    mov [ rbp + -560 ], r10
    jmp _l51 ## (JUMP (NAME _l51))
_l31:
    mov r10, [ rbp + -808 ]
    mov r11, [ rbp + -240 ]
    mov r10, r11 ## (MOVE (TEMP _t68) (TEMP peek))
    mov [ rbp + -240 ], r11
    mov [ rbp + -808 ], r10
    mov r10, [ rbp + -808 ]
    mov rdi, r10 ## (CALL_STMT _t69 (NAME _IhandleX_t2ibi) (TEMP _t68))
    mov [ rbp + -808 ], r10
    callq _IhandleX_t2ibi ## (CALL_STMT _t69 (NAME _IhandleX_t2ibi) (TEMP _t68))
    mov r10, [ rbp + -816 ]
    mov r10, rax ## (CALL_STMT _t69 (NAME _IhandleX_t2ibi) (TEMP _t68))
    mov [ rbp + -816 ], r10
    mov r10, [ rbp + -224 ]
    mov r10, rax ## (MOVE (TEMP incr) (TEMP _RET0))
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -720 ]
    mov r10, rdx ## (MOVE (TEMP skip) (TEMP _RET1))
    mov [ rbp + -720 ], r10
    mov r10, [ rbp + -720 ]
    cmpq r10, 1 ## (CJUMP (TEMP skip) _l52)
    mov [ rbp + -720 ], r10
    je _l52 ## (CJUMP (TEMP skip) _l52)
_l53:
_l54:
    mov r10, [ rbp + -504 ]
    mov r11, [ rbp + -200 ]
    mov r10, r11 ## (MOVE (TEMP _t71) (TEMP value))
    mov [ rbp + -200 ], r11
    mov [ rbp + -504 ], r10
    mov r10, [ rbp + -744 ]
    mov r11, [ rbp + -504 ]
    mov r10, r11 ## (ADD (TEMP _t71) (TEMP incr))
    mov [ rbp + -504 ], r11
    mov [ rbp + -744 ], r10
    mov r10, [ rbp + -744 ]
    mov r11, [ rbp + -224 ]
    addq r10, r11 ## (ADD (TEMP _t71) (TEMP incr))
    mov [ rbp + -224 ], r11
    mov [ rbp + -744 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -744 ]
    mov r10, r11 ## (MOVE (TEMP value) (ADD (TEMP _t71) (TEMP incr)))
    mov [ rbp + -744 ], r11
    mov [ rbp + -200 ], r10
    jmp _l33 ## (JUMP (NAME _l33))
_l52:
    mov r10, [ rbp + -488 ]
    mov r11, [ rbp + -560 ]
    mov r10, r11 ## (MOVE (TEMP _t70) (TEMP i))
    mov [ rbp + -560 ], r11
    mov [ rbp + -488 ], r10
    mov r10, [ rbp + -752 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -752 ], r10
    mov r10, [ rbp + -760 ]
    mov r11, [ rbp + -488 ]
    mov r10, r11 ## (ADD (TEMP _t70) (CONST 1))
    mov [ rbp + -488 ], r11
    mov [ rbp + -760 ], r10
    mov r10, [ rbp + -760 ]
    mov r11, [ rbp + -752 ]
    addq r10, r11 ## (ADD (TEMP _t70) (CONST 1))
    mov [ rbp + -752 ], r11
    mov [ rbp + -760 ], r10
    mov r10, [ rbp + -560 ]
    mov r11, [ rbp + -760 ]
    mov r10, r11 ## (MOVE (TEMP i) (ADD (TEMP _t70) (CONST 1)))
    mov [ rbp + -760 ], r11
    mov [ rbp + -560 ], r10
    jmp _l54 ## (JUMP (NAME _l54))
_l28:
    mov r10, [ rbp + -520 ]
    mov r11, [ rbp + -240 ]
    mov r10, r11 ## (MOVE (TEMP _t72) (TEMP peek))
    mov [ rbp + -240 ], r11
    mov [ rbp + -520 ], r10
    mov r10, [ rbp + -520 ]
    mov rdi, r10 ## (CALL_STMT _t73 (NAME _IhandleI_t2ibi) (TEMP _t72))
    mov [ rbp + -520 ], r10
    callq _IhandleI_t2ibi ## (CALL_STMT _t73 (NAME _IhandleI_t2ibi) (TEMP _t72))
    mov r10, [ rbp + -608 ]
    mov r10, rax ## (CALL_STMT _t73 (NAME _IhandleI_t2ibi) (TEMP _t72))
    mov [ rbp + -608 ], r10
    mov r10, [ rbp + -224 ]
    mov r10, rax ## (MOVE (TEMP incr) (TEMP _RET0))
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -720 ]
    mov r10, rdx ## (MOVE (TEMP skip) (TEMP _RET1))
    mov [ rbp + -720 ], r10
    mov r10, [ rbp + -720 ]
    cmpq r10, 1 ## (CJUMP (TEMP skip) _l55)
    mov [ rbp + -720 ], r10
    je _l55 ## (CJUMP (TEMP skip) _l55)
_l56:
_l57:
    mov r10, [ rbp + -624 ]
    mov r11, [ rbp + -200 ]
    mov r10, r11 ## (MOVE (TEMP _t75) (TEMP value))
    mov [ rbp + -200 ], r11
    mov [ rbp + -624 ], r10
    mov r10, [ rbp + -768 ]
    mov r11, [ rbp + -624 ]
    mov r10, r11 ## (ADD (TEMP _t75) (TEMP incr))
    mov [ rbp + -624 ], r11
    mov [ rbp + -768 ], r10
    mov r10, [ rbp + -768 ]
    mov r11, [ rbp + -224 ]
    addq r10, r11 ## (ADD (TEMP _t75) (TEMP incr))
    mov [ rbp + -224 ], r11
    mov [ rbp + -768 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -768 ]
    mov r10, r11 ## (MOVE (TEMP value) (ADD (TEMP _t75) (TEMP incr)))
    mov [ rbp + -768 ], r11
    mov [ rbp + -200 ], r10
    jmp _l30 ## (JUMP (NAME _l30))
_l55:
    mov r10, [ rbp + -616 ]
    mov r11, [ rbp + -560 ]
    mov r10, r11 ## (MOVE (TEMP _t74) (TEMP i))
    mov [ rbp + -560 ], r11
    mov [ rbp + -616 ], r10
    mov r10, [ rbp + -776 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -776 ], r10
    mov r10, [ rbp + -784 ]
    mov r11, [ rbp + -616 ]
    mov r10, r11 ## (ADD (TEMP _t74) (CONST 1))
    mov [ rbp + -616 ], r11
    mov [ rbp + -784 ], r10
    mov r10, [ rbp + -784 ]
    mov r11, [ rbp + -776 ]
    addq r10, r11 ## (ADD (TEMP _t74) (CONST 1))
    mov [ rbp + -776 ], r11
    mov [ rbp + -784 ], r10
    mov r10, [ rbp + -560 ]
    mov r11, [ rbp + -784 ]
    mov r10, r11 ## (MOVE (TEMP i) (ADD (TEMP _t74) (CONST 1)))
    mov [ rbp + -784 ], r11
    mov [ rbp + -560 ], r10
    jmp _l57 ## (JUMP (NAME _l57))
end__Iconvert_iai:
    addq rsp, 1064
    mov rsp, rbp
    popq rbp
    retq
_IhandleC_t2ibi:
    pushq rbp
    mov rbp, rsp
    subq rsp, 168
_l67:
    mov r10, [ rbp + -48 ]
    mov r10, rdi ## (MOVE (TEMP peek) (TEMP _ARG0))
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -48 ]
    mov r10, r11 ## (MOVE (TEMP _t32) (TEMP peek))
    mov [ rbp + -48 ], r11
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -16 ]
    movabsq r10, 77 ## (CONST 77)
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -16 ]
    cmpq r10, r11 ## (EQ (TEMP _t32) (CONST 77))
    mov [ rbp + -16 ], r11
    mov [ rbp + -168 ], r10
    pushq rax
    mov rax, [ rbp + -40 ]
    setz al ## (EQ (TEMP _t32) (CONST 77))
    mov [ rbp + -40 ], rax
    popq rax
    mov r10, [ rbp + -32 ]
    pushq rax
    mov rax, [ rbp + -40 ]
    movzx r10, al ## (EQ (TEMP _t32) (CONST 77))
    mov [ rbp + -40 ], rax
    popq rax
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -32 ]
    cmpq r10, 1 ## (CJUMP (EQ (TEMP _t32) (CONST 77)) _l12)
    mov [ rbp + -32 ], r10
    je _l12 ## (CJUMP (EQ (TEMP _t32) (CONST 77)) _l12)
_l13:
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -48 ]
    mov r10, r11 ## (MOVE (TEMP _t33) (TEMP peek))
    mov [ rbp + -48 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -56 ]
    movabsq r10, 68 ## (CONST 68)
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -56 ]
    cmpq r10, r11 ## (EQ (TEMP _t33) (CONST 68))
    mov [ rbp + -56 ], r11
    mov [ rbp + -136 ], r10
    pushq rax
    mov rax, [ rbp + -72 ]
    setz al ## (EQ (TEMP _t33) (CONST 68))
    mov [ rbp + -72 ], rax
    popq rax
    mov r10, [ rbp + -64 ]
    pushq rax
    mov rax, [ rbp + -72 ]
    movzx r10, al ## (EQ (TEMP _t33) (CONST 68))
    mov [ rbp + -72 ], rax
    popq rax
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -64 ]
    cmpq r10, 1 ## (CJUMP (EQ (TEMP _t33) (CONST 68)) _l15)
    mov [ rbp + -64 ], r10
    je _l15 ## (CJUMP (EQ (TEMP _t33) (CONST 68)) _l15)
_l16:
    mov r10, [ rbp + -80 ]
    movabsq r10, 100 ## (CONST 100)
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -80 ]
    mov r10, r11 ## (MOVE (TEMP _t12) (CONST 100))
    mov [ rbp + -80 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -88 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -88 ]
    mov r10, r11 ## (MOVE (TEMP _t13) (CONST 0))
    mov [ rbp + -88 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -144 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t12))
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -152 ]
    mov rdx, r10 ## (MOVE (TEMP _RET1) (TEMP _t13))
    mov [ rbp + -152 ], r10
    jmp end__IhandleC_t2ibi ## (RETURN ())
_l68:
_l17:
_l14:
    jmp end__IhandleC_t2ibi ## (RETURN ())
_l15:
    mov r10, [ rbp + -96 ]
    movabsq r10, 400 ## (CONST 400)
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -160 ]
    mov r11, [ rbp + -96 ]
    mov r10, r11 ## (MOVE (TEMP _t14) (CONST 400))
    mov [ rbp + -96 ], r11
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -104 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -104 ]
    mov r10, r11 ## (MOVE (TEMP _t15) (CONST 1))
    mov [ rbp + -104 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -160 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t14))
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -112 ]
    mov rdx, r10 ## (MOVE (TEMP _RET1) (TEMP _t15))
    mov [ rbp + -112 ], r10
    jmp end__IhandleC_t2ibi ## (RETURN ())
_l12:
    mov r10, [ rbp + -8 ]
    movabsq r10, 900 ## (CONST 900)
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t16) (CONST 900))
    mov [ rbp + -8 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -24 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (MOVE (TEMP _t17) (CONST 1))
    mov [ rbp + -24 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -120 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t16))
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -128 ]
    mov rdx, r10 ## (MOVE (TEMP _RET1) (TEMP _t17))
    mov [ rbp + -128 ], r10
    jmp end__IhandleC_t2ibi ## (RETURN ())
end__IhandleC_t2ibi:
    addq rsp, 168
    mov rsp, rbp
    popq rbp
    retq
