.intel_syntax noprefix
.globl _Imain_paai
.align 4
_Imain_paai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 464
_l3:
    mov r10, [ rbp + -256 ]
    mov r10, rdi ## (MOVE (TEMP args) (TEMP _ARG0))
    mov [ rbp + -256 ], r10
    mov r10, [ rbp + -464 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -464 ], r10
    mov r10, [ rbp + -280 ]
    mov r11, [ rbp + -464 ]
    mov r10, r11 ## (MOVE (TEMP _t17) (CONST 8))
    mov [ rbp + -464 ], r11
    mov [ rbp + -280 ], r10
    mov r10, [ rbp + -392 ]
    movabsq r10, 2 ## (CONST 2)
    mov [ rbp + -392 ], r10
    mov r10, [ rbp + -272 ]
    mov r11, [ rbp + -392 ]
    mov r10, r11 ## (MOVE (TEMP _t16) (CONST 2))
    mov [ rbp + -392 ], r11
    mov [ rbp + -272 ], r10
    mov r10, [ rbp + -400 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -400 ], r10
    mov r10, [ rbp + -408 ]
    mov r11, [ rbp + -272 ]
    mov r10, r11 ## (ADD (TEMP _t16) (CONST 1))
    mov [ rbp + -272 ], r11
    mov [ rbp + -408 ], r10
    mov r10, [ rbp + -408 ]
    mov r11, [ rbp + -400 ]
    addq r10, r11 ## (ADD (TEMP _t16) (CONST 1))
    mov [ rbp + -400 ], r11
    mov [ rbp + -408 ], r10
    mov r10, [ rbp + -424 ]
    mov r11, [ rbp + -280 ]
    mov r10, r11 ## (MUL (TEMP _t17) (ADD (TEMP _t16) (CONST 1)))
    mov [ rbp + -280 ], r11
    mov [ rbp + -424 ], r10
    mov r10, [ rbp + -424 ]
    mov r11, [ rbp + -408 ]
    imulq r10, r11 ## (MUL (TEMP _t17) (ADD (TEMP _t16) (CONST 1)))
    mov [ rbp + -408 ], r11
    mov [ rbp + -424 ], r10
    mov r10, [ rbp + -288 ]
    mov r11, [ rbp + -424 ]
    mov r10, r11 ## (MOVE (TEMP _t18) (MUL (TEMP _t17) (ADD (TEMP _t16) (CONST 1))))
    mov [ rbp + -424 ], r11
    mov [ rbp + -288 ], r10
    mov r10, [ rbp + -288 ]
    mov rdi, r10 ## (CALL_STMT _t19 (NAME _xi_alloc) (TEMP _t18))
    mov [ rbp + -288 ], r10
    callq _xi_alloc ## (CALL_STMT _t19 (NAME _xi_alloc) (TEMP _t18))
    mov r10, [ rbp + -248 ]
    mov r10, rax ## (CALL_STMT _t19 (NAME _xi_alloc) (TEMP _t18))
    mov [ rbp + -248 ], r10
    mov r10, [ rbp + -432 ]
    mov r11, [ rbp + -248 ]
    mov r10, r11 ## (MOVE (TEMP _t0) (TEMP _t19))
    mov [ rbp + -248 ], r11
    mov [ rbp + -432 ], r10
    mov r10, [ rbp + -216 ]
    mov r11, [ rbp + -432 ]
    mov r10, r11 ## (MOVE (TEMP _t20) (TEMP _t0))
    mov [ rbp + -432 ], r11
    mov [ rbp + -216 ], r10
    mov r10, [ rbp + -376 ]
    movabsq r10, 2 ## (CONST 2)
    mov [ rbp + -376 ], r10
    mov r10, [ rbp + -216 ]
    mov r11, [ rbp + -376 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t20)) (CONST 2))
    mov [ rbp + -376 ], r11
    mov [ rbp + -216 ], r10
    mov r10, [ rbp + -232 ]
    mov r11, [ rbp + -432 ]
    mov r10, r11 ## (MOVE (TEMP _t21) (TEMP _t0))
    mov [ rbp + -432 ], r11
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -384 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -384 ], r10
    mov r10, [ rbp + -240 ]
    mov r11, [ rbp + -232 ]
    mov r10, r11 ## (ADD (TEMP _t21) (CONST 8))
    mov [ rbp + -232 ], r11
    mov [ rbp + -240 ], r10
    mov r10, [ rbp + -240 ]
    mov r11, [ rbp + -384 ]
    addq r10, r11 ## (ADD (TEMP _t21) (CONST 8))
    mov [ rbp + -384 ], r11
    mov [ rbp + -240 ], r10
    mov r10, [ rbp + -416 ]
    mov r11, [ rbp + -240 ]
    mov r10, r11 ## (MOVE (TEMP _t1) (ADD (TEMP _t21) (CONST 8)))
    mov [ rbp + -240 ], r11
    mov [ rbp + -416 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -416 ]
    mov r10, r11 ## (MOVE (TEMP _t23) (TEMP _t1))
    mov [ rbp + -416 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -328 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -328 ], r10
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -328 ]
    mov r10, r11 ## (MOVE (TEMP _t22) (CONST 8))
    mov [ rbp + -328 ], r11
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -336 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -336 ], r10
    mov r10, [ rbp + -344 ]
    mov r11, [ rbp + -168 ]
    mov r10, r11 ## (MUL (TEMP _t22) (CONST 0))
    mov [ rbp + -168 ], r11
    mov [ rbp + -344 ], r10
    mov r10, [ rbp + -344 ]
    mov r11, [ rbp + -336 ]
    imulq r10, r11 ## (MUL (TEMP _t22) (CONST 0))
    mov [ rbp + -336 ], r11
    mov [ rbp + -344 ], r10
    mov r10, [ rbp + -352 ]
    mov r11, [ rbp + -176 ]
    mov r10, r11 ## (ADD (TEMP _t23) (MUL (TEMP _t22) (CONST 0)))
    mov [ rbp + -176 ], r11
    mov [ rbp + -352 ], r10
    mov r10, [ rbp + -352 ]
    mov r11, [ rbp + -344 ]
    addq r10, r11 ## (ADD (TEMP _t23) (MUL (TEMP _t22) (CONST 0)))
    mov [ rbp + -344 ], r11
    mov [ rbp + -352 ], r10
    mov r10, [ rbp + -184 ]
    mov r11, [ rbp + -352 ]
    mov r10, r11 ## (MOVE (TEMP _t24) (ADD (TEMP _t23) (MUL (TEMP _t22) (CONST 0))))
    mov [ rbp + -352 ], r11
    mov [ rbp + -184 ], r10
    mov r10, [ rbp + -296 ]
    movabsq r10, 49 ## (CONST 49)
    mov [ rbp + -296 ], r10
    mov r10, [ rbp + -184 ]
    mov r11, [ rbp + -296 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t24)) (CONST 49))
    mov [ rbp + -296 ], r11
    mov [ rbp + -184 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -416 ]
    mov r10, r11 ## (MOVE (TEMP _t26) (TEMP _t1))
    mov [ rbp + -416 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -304 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -304 ], r10
    mov r10, [ rbp + -192 ]
    mov r11, [ rbp + -304 ]
    mov r10, r11 ## (MOVE (TEMP _t25) (CONST 8))
    mov [ rbp + -304 ], r11
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -312 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -312 ], r10
    mov r10, [ rbp + -320 ]
    mov r11, [ rbp + -192 ]
    mov r10, r11 ## (MUL (TEMP _t25) (CONST 1))
    mov [ rbp + -192 ], r11
    mov [ rbp + -320 ], r10
    mov r10, [ rbp + -320 ]
    mov r11, [ rbp + -312 ]
    imulq r10, r11 ## (MUL (TEMP _t25) (CONST 1))
    mov [ rbp + -312 ], r11
    mov [ rbp + -320 ], r10
    mov r10, [ rbp + -264 ]
    mov r11, [ rbp + -136 ]
    mov r10, r11 ## (ADD (TEMP _t26) (MUL (TEMP _t25) (CONST 1)))
    mov [ rbp + -136 ], r11
    mov [ rbp + -264 ], r10
    mov r10, [ rbp + -264 ]
    mov r11, [ rbp + -320 ]
    addq r10, r11 ## (ADD (TEMP _t26) (MUL (TEMP _t25) (CONST 1)))
    mov [ rbp + -320 ], r11
    mov [ rbp + -264 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -264 ]
    mov r10, r11 ## (MOVE (TEMP _t27) (ADD (TEMP _t26) (MUL (TEMP _t25) (CONST 1))))
    mov [ rbp + -264 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -112 ]
    movabsq r10, 50 ## (CONST 50)
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -112 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t27)) (CONST 50))
    mov [ rbp + -112 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -416 ]
    mov r10, r11 ## (MOVE (TEMP _t28) (TEMP _t1))
    mov [ rbp + -416 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -152 ]
    mov rdi, r10 ## (CALL_STMT _t29 (NAME _IparseInt_t2ibai) (TEMP _t28))
    mov [ rbp + -152 ], r10
    callq _IparseInt_t2ibai ## (CALL_STMT _t29 (NAME _IparseInt_t2ibai) (TEMP _t28))
    mov r10, [ rbp + -160 ]
    mov r10, rax ## (CALL_STMT _t29 (NAME _IparseInt_t2ibai) (TEMP _t28))
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -128 ]
    mov r10, rax ## (MOVE (TEMP num) (TEMP _RET0))
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -368 ]
    mov r10, rdx ## (MOVE (TEMP good) (TEMP _RET1))
    mov [ rbp + -368 ], r10
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -128 ]
    mov r10, r11 ## (MOVE (TEMP _t30) (TEMP num))
    mov [ rbp + -128 ], r11
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -88 ]
    mov rdi, r10 ## (CALL_STMT _t31 (NAME _IunparseInt_aii) (TEMP _t30))
    mov [ rbp + -88 ], r10
    callq _IunparseInt_aii ## (CALL_STMT _t31 (NAME _IunparseInt_aii) (TEMP _t30))
    mov r10, [ rbp + -96 ]
    mov r10, rax ## (CALL_STMT _t31 (NAME _IunparseInt_aii) (TEMP _t30))
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -104 ]
    mov r11, [ rbp + -96 ]
    mov r10, r11 ## (MOVE (TEMP _t32) (TEMP _t31))
    mov [ rbp + -96 ], r11
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -104 ]
    mov rdi, r10 ## (CALL_STMT _t33 (NAME _Iprintln_pai) (TEMP _t32))
    mov [ rbp + -104 ], r10
    callq _Iprintln_pai ## (CALL_STMT _t33 (NAME _Iprintln_pai) (TEMP _t32))
    mov r10, [ rbp + -56 ]
    mov r10, rax ## (CALL_STMT _t33 (NAME _Iprintln_pai) (TEMP _t32))
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -120 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -64 ]
    mov r11, [ rbp + -120 ]
    mov r10, r11 ## (MOVE (TEMP _t34) (CONST 0))
    mov [ rbp + -120 ], r11
    mov [ rbp + -64 ], r10
    movabsq rdi, 40 ## (CALL_STMT _t35 (NAME _Iint__of__bool_t7iiiiiiib) (TEMP _t34))
    subq rsp, rdi ## (CALL_STMT _t35 (NAME _Iint__of__bool_t7iiiiiiib) (TEMP _t34))
    mov rdi, rsp ## (CALL_STMT _t35 (NAME _Iint__of__bool_t7iiiiiiib) (TEMP _t34))
    mov r10, [ rbp + -64 ]
    mov rsi, r10 ## (CALL_STMT _t35 (NAME _Iint__of__bool_t7iiiiiiib) (TEMP _t34))
    mov [ rbp + -64 ], r10
    callq _Iint__of__bool_t7iiiiiiib ## (CALL_STMT _t35 (NAME _Iint__of__bool_t7iiiiiiib) (TEMP _t34))
    mov r10, [ rbp + -72 ]
    mov r10, rax ## (CALL_STMT _t35 (NAME _Iint__of__bool_t7iiiiiiib) (TEMP _t34))
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -32 ]
    mov r10, rax ## (MOVE (TEMP v) (TEMP _RET0))
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -8 ]
    mov r10, rdx ## (MOVE (TEMP b) (TEMP _RET1))
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -16 ]
    mov r10, [ rdi + 8 ] ## (MOVE (TEMP c) (TEMP _RET2))
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -360 ]
    mov r10, [ rdi + 32 ] ## (MOVE (TEMP lol) (TEMP _RET5))
    mov [ rbp + -360 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (MOVE (TEMP _t36) (TEMP v))
    mov [ rbp + -32 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -80 ]
    mov r10, r11 ## (ADD (TEMP _t36) (TEMP b))
    mov [ rbp + -80 ], r11
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -8 ]
    addq r10, r11 ## (ADD (TEMP _t36) (TEMP b))
    mov [ rbp + -8 ], r11
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -200 ]
    mov r10, r11 ## (MOVE (TEMP _t37) (ADD (TEMP _t36) (TEMP b)))
    mov [ rbp + -200 ], r11
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -208 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (ADD (TEMP _t37) (TEMP c))
    mov [ rbp + -24 ], r11
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -208 ]
    mov r11, [ rbp + -16 ]
    addq r10, r11 ## (ADD (TEMP _t37) (TEMP c))
    mov [ rbp + -16 ], r11
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -208 ]
    mov r10, r11 ## (MOVE (TEMP _t38) (ADD (TEMP _t37) (TEMP c)))
    mov [ rbp + -208 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -224 ]
    mov r11, [ rbp + -40 ]
    mov r10, r11 ## (ADD (TEMP _t38) (TEMP lol))
    mov [ rbp + -40 ], r11
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -224 ]
    mov r11, [ rbp + -360 ]
    addq r10, r11 ## (ADD (TEMP _t38) (TEMP lol))
    mov [ rbp + -360 ], r11
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -224 ]
    mov r10, r11 ## (MOVE (TEMP _t39) (ADD (TEMP _t38) (TEMP lol)))
    mov [ rbp + -224 ], r11
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -48 ]
    mov rdi, r10 ## (CALL_STMT _t40 (NAME _IunparseInt_aii) (TEMP _t39))
    mov [ rbp + -48 ], r10
    callq _IunparseInt_aii ## (CALL_STMT _t40 (NAME _IunparseInt_aii) (TEMP _t39))
    mov r10, [ rbp + -440 ]
    mov r10, rax ## (CALL_STMT _t40 (NAME _IunparseInt_aii) (TEMP _t39))
    mov [ rbp + -440 ], r10
    mov r10, [ rbp + -448 ]
    mov r11, [ rbp + -440 ]
    mov r10, r11 ## (MOVE (TEMP _t41) (TEMP _t40))
    mov [ rbp + -440 ], r11
    mov [ rbp + -448 ], r10
    mov r10, [ rbp + -448 ]
    mov rdi, r10 ## (CALL_STMT _t42 (NAME _Iprintln_pai) (TEMP _t41))
    mov [ rbp + -448 ], r10
    callq _Iprintln_pai ## (CALL_STMT _t42 (NAME _Iprintln_pai) (TEMP _t41))
    mov r10, [ rbp + -456 ]
    mov r10, rax ## (CALL_STMT _t42 (NAME _Iprintln_pai) (TEMP _t41))
    mov [ rbp + -456 ], r10
    jmp end__Imain_paai ## (RETURN ())
end__Imain_paai:
    addq rsp, 464
    mov rsp, rbp
    popq rbp
    retq
_Iint__of__bool_t7iiiiiiib:
    pushq rbp
    mov rbp, rsp
    subq rsp, 232
_l4:
    mov r10, [ rbp + -16 ]
    mov r10, rsi ## (MOVE (TEMP b) (TEMP _ARG0))
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -16 ]
    cmpq r10, 1 ## (CJUMP (TEMP b) _l1)
    mov [ rbp + -16 ], r10
    je _l1 ## (CJUMP (TEMP b) _l1)
_l0:
    mov r10, [ rbp + -224 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -224 ]
    mov r10, r11 ## (MOVE (TEMP _t9) (CONST 1))
    mov [ rbp + -224 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -168 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -168 ]
    mov r10, r11 ## (MOVE (TEMP _t10) (CONST 1))
    mov [ rbp + -168 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -176 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -176 ]
    mov r10, r11 ## (MOVE (TEMP _t11) (CONST 1))
    mov [ rbp + -176 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -184 ]
    movabsq r10, 2 ## (CONST 2)
    mov [ rbp + -184 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -184 ]
    mov r10, r11 ## (MOVE (TEMP _t12) (CONST 2))
    mov [ rbp + -184 ], r11
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -192 ]
    movabsq r10, 2 ## (CONST 2)
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -192 ]
    mov r10, r11 ## (MOVE (TEMP _t13) (CONST 2))
    mov [ rbp + -192 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -24 ]
    movabsq r10, 2 ## (CONST 2)
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (MOVE (TEMP _t14) (CONST 2))
    mov [ rbp + -24 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -48 ]
    movabsq r10, 2 ## (CONST 2)
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -72 ]
    mov r11, [ rbp + -48 ]
    mov r10, r11 ## (MOVE (TEMP _t15) (CONST 2))
    mov [ rbp + -48 ], r11
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -40 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t9))
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -152 ]
    mov rdx, r10 ## (MOVE (TEMP _RET1) (TEMP _t10))
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -80 ]
    mov [ rdi + 8 ], r10 ## (MOVE (TEMP _RET2) (TEMP _t11))
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -96 ]
    mov [ rdi + 16 ], r10 ## (MOVE (TEMP _RET3) (TEMP _t12))
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -112 ]
    mov [ rdi + 24 ], r10 ## (MOVE (TEMP _RET4) (TEMP _t13))
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -120 ]
    mov [ rdi + 32 ], r10 ## (MOVE (TEMP _RET5) (TEMP _t14))
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -72 ]
    mov [ rdi + 40 ], r10 ## (MOVE (TEMP _RET6) (TEMP _t15))
    mov [ rbp + -72 ], r10
    jmp end__Iint__of__bool_t7iiiiiiib ## (RETURN ())
_l1:
    mov r10, [ rbp + -56 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -208 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t2) (CONST 0))
    mov [ rbp + -56 ], r11
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -128 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -128 ]
    mov r10, r11 ## (MOVE (TEMP _t3) (CONST 0))
    mov [ rbp + -128 ], r11
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -136 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -232 ]
    mov r11, [ rbp + -136 ]
    mov r10, r11 ## (MOVE (TEMP _t4) (CONST 0))
    mov [ rbp + -136 ], r11
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -144 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -216 ]
    mov r11, [ rbp + -144 ]
    mov r10, r11 ## (MOVE (TEMP _t5) (CONST 0))
    mov [ rbp + -144 ], r11
    mov [ rbp + -216 ], r10
    mov r10, [ rbp + -160 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -160 ]
    mov r10, r11 ## (MOVE (TEMP _t6) (CONST 0))
    mov [ rbp + -160 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -88 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -8 ]
    mov r11, [ rbp + -88 ]
    mov r10, r11 ## (MOVE (TEMP _t7) (CONST 0))
    mov [ rbp + -88 ], r11
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -104 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -64 ]
    mov r11, [ rbp + -104 ]
    mov r10, r11 ## (MOVE (TEMP _t8) (CONST 0))
    mov [ rbp + -104 ], r11
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -208 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t2))
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -200 ]
    mov rdx, r10 ## (MOVE (TEMP _RET1) (TEMP _t3))
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -232 ]
    mov [ rdi + 8 ], r10 ## (MOVE (TEMP _RET2) (TEMP _t4))
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -216 ]
    mov [ rdi + 16 ], r10 ## (MOVE (TEMP _RET3) (TEMP _t5))
    mov [ rbp + -216 ], r10
    mov r10, [ rbp + -32 ]
    mov [ rdi + 24 ], r10 ## (MOVE (TEMP _RET4) (TEMP _t6))
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -8 ]
    mov [ rdi + 32 ], r10 ## (MOVE (TEMP _RET5) (TEMP _t7))
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -64 ]
    mov [ rdi + 40 ], r10 ## (MOVE (TEMP _RET6) (TEMP _t8))
    mov [ rbp + -64 ], r10
    jmp end__Iint__of__bool_t7iiiiiiib ## (RETURN ())
_l5:
_l2:
    jmp end__Iint__of__bool_t7iiiiiiib ## (RETURN ())
end__Iint__of__bool_t7iiiiiiib:
    addq rsp, 232
    mov rsp, rbp
    popq rbp
    retq
