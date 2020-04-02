.intel_syntax noprefix
.globl _Imain_paai
.align 4
_Imain_paai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 520
_l21:
    mov r10, [ rbp + -256 ]
    mov r10, rdi ## (MOVE (TEMP args) (TEMP _ARG0))
    mov [ rbp + -256 ], r10
    mov r10, [ rbp + -200 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -200 ]
    mov r10, r11 ## (MOVE (TEMP _t9) (CONST 0))
    mov [ rbp + -200 ], r11
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -256 ]
    mov r10, r11 ## (MOVE (TEMP _t8) (TEMP args))
    mov [ rbp + -256 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -72 ]
    mov r11, [ rbp + -40 ]
    mov r10, r11 ## (MOVE (TEMP _t38) (TEMP _t8))
    mov [ rbp + -40 ], r11
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -208 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -216 ]
    mov r11, [ rbp + -72 ]
    mov r10, r11 ## (SUB (TEMP _t38) (CONST 8))
    mov [ rbp + -72 ], r11
    mov [ rbp + -216 ], r10
    mov r10, [ rbp + -216 ]
    mov r11, [ rbp + -208 ]
    subq r10, r11 ## (SUB (TEMP _t38) (CONST 8))
    mov [ rbp + -208 ], r11
    mov [ rbp + -216 ], r10
    mov r10, [ rbp + -352 ]
    mov r11, [ rbp + -216 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t10) (MEM (SUB (TEMP _t38) (CONST 8))))
    mov [ rbp + -216 ], r11
    mov [ rbp + -352 ], r10
    mov r10, [ rbp + -224 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -224 ]
    mov r10, r11 ## (MOVE (TEMP _t39) (CONST 0))
    mov [ rbp + -224 ], r11
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -24 ]
    cmpq r10, r11 ## (LEQ (TEMP _t39) (TEMP _t9))
    mov [ rbp + -24 ], r11
    mov [ rbp + -88 ], r10
    pushq rax
    mov rax, [ rbp + -176 ]
    setle al ## (LEQ (TEMP _t39) (TEMP _t9))
    mov [ rbp + -176 ], rax
    popq rax
    mov r10, [ rbp + -168 ]
    pushq rax
    mov rax, [ rbp + -176 ]
    movzx r10, al ## (LEQ (TEMP _t39) (TEMP _t9))
    mov [ rbp + -176 ], rax
    popq rax
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -480 ]
    mov r11, [ rbp + -168 ]
    mov r10, r11 ## (MOVE (TEMP _t41) (LEQ (TEMP _t39) (TEMP _t9)))
    mov [ rbp + -168 ], r11
    mov [ rbp + -480 ], r10
    mov r10, [ rbp + -464 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (MOVE (TEMP _t40) (TEMP _t9))
    mov [ rbp + -24 ], r11
    mov [ rbp + -464 ], r10
    mov r10, [ rbp + -464 ]
    mov r11, [ rbp + -352 ]
    cmpq r10, r11 ## (LT (TEMP _t40) (TEMP _t10))
    mov [ rbp + -352 ], r11
    mov [ rbp + -464 ], r10
    pushq rax
    mov rax, [ rbp + -192 ]
    setl al ## (LT (TEMP _t40) (TEMP _t10))
    mov [ rbp + -192 ], rax
    popq rax
    mov r10, [ rbp + -184 ]
    pushq rax
    mov rax, [ rbp + -192 ]
    movzx r10, al ## (LT (TEMP _t40) (TEMP _t10))
    mov [ rbp + -192 ], rax
    popq rax
    mov [ rbp + -184 ], r10
    mov r10, [ rbp + -8 ]
    mov r11, [ rbp + -480 ]
    mov r10, r11 ## (AND (TEMP _t41) (LT (TEMP _t40) (TEMP _t10)))
    mov [ rbp + -480 ], r11
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -8 ]
    mov r11, [ rbp + -184 ]
    andq r10, r11 ## (AND (TEMP _t41) (LT (TEMP _t40) (TEMP _t10)))
    mov [ rbp + -184 ], r11
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -8 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t41) (LT (TEMP _t40) (TEMP _t10))) _l13)
    mov [ rbp + -8 ], r10
    je _l13 ## (CJUMP (AND (TEMP _t41) (LT (TEMP _t40) (TEMP _t10))) _l13)
_l14:
    callq _xi_out_of_bounds ## (CALL_STMT _t42 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -496 ]
    mov r10, rax ## (CALL_STMT _t42 (NAME _xi_out_of_bounds))
    mov [ rbp + -496 ], r10
_l13:
    mov r10, [ rbp + -416 ]
    mov r11, [ rbp + -40 ]
    mov r10, r11 ## (MOVE (TEMP _t44) (TEMP _t8))
    mov [ rbp + -40 ], r11
    mov [ rbp + -416 ], r10
    mov r10, [ rbp + -512 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (MOVE (TEMP _t43) (TEMP _t9))
    mov [ rbp + -24 ], r11
    mov [ rbp + -512 ], r10
    mov r10, [ rbp + -32 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -512 ]
    mov r10, r11 ## (MUL (TEMP _t43) (CONST 8))
    mov [ rbp + -512 ], r11
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -32 ]
    imulq r10, r11 ## (MUL (TEMP _t43) (CONST 8))
    mov [ rbp + -32 ], r11
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -416 ]
    mov r10, r11 ## (ADD (TEMP _t44) (MUL (TEMP _t43) (CONST 8)))
    mov [ rbp + -416 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -48 ]
    addq r10, r11 ## (ADD (TEMP _t44) (MUL (TEMP _t43) (CONST 8)))
    mov [ rbp + -48 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -120 ]
    mov r10, [ r11 ] ## (MOVE (TEMP arr) (MEM (ADD (TEMP _t44) (MUL (TEMP _t43) (CONST 8)))))
    mov [ rbp + -120 ], r11
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -432 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t45) (TEMP arr))
    mov [ rbp + -16 ], r11
    mov [ rbp + -432 ], r10
    mov r10, [ rbp + -128 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -432 ]
    mov r10, r11 ## (SUB (TEMP _t45) (CONST 8))
    mov [ rbp + -432 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -128 ]
    subq r10, r11 ## (SUB (TEMP _t45) (CONST 8))
    mov [ rbp + -128 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -136 ]
    mov r10, [ r11 ] ## (MOVE (TEMP size) (MEM (SUB (TEMP _t45) (CONST 8))))
    mov [ rbp + -136 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -144 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -144 ]
    mov r10, r11 ## (MOVE (TEMP i) (CONST 0))
    mov [ rbp + -144 ], r11
    mov [ rbp + -56 ], r10
_l15:
    mov r10, [ rbp + -448 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t46) (TEMP i))
    mov [ rbp + -56 ], r11
    mov [ rbp + -448 ], r10
    mov r10, [ rbp + -448 ]
    mov r11, [ rbp + -80 ]
    cmpq r10, r11 ## (LT (TEMP _t46) (TEMP size))
    mov [ rbp + -80 ], r11
    mov [ rbp + -448 ], r10
    pushq rax
    mov rax, [ rbp + -104 ]
    setl al ## (LT (TEMP _t46) (TEMP size))
    mov [ rbp + -104 ], rax
    popq rax
    mov r10, [ rbp + -96 ]
    pushq rax
    mov rax, [ rbp + -104 ]
    movzx r10, al ## (LT (TEMP _t46) (TEMP size))
    mov [ rbp + -104 ], rax
    popq rax
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -96 ]
    cmpq r10, 1 ## (CJUMP (LT (TEMP _t46) (TEMP size)) _l16)
    mov [ rbp + -96 ], r10
    je _l16 ## (CJUMP (LT (TEMP _t46) (TEMP size)) _l16)
_l17:
    jmp end__Imain_paai ## (RETURN ())
_l16:
    mov r10, [ rbp + -288 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t12) (TEMP i))
    mov [ rbp + -56 ], r11
    mov [ rbp + -288 ], r10
    mov r10, [ rbp + -272 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t11) (TEMP arr))
    mov [ rbp + -16 ], r11
    mov [ rbp + -272 ], r10
    mov r10, [ rbp + -456 ]
    mov r11, [ rbp + -272 ]
    mov r10, r11 ## (MOVE (TEMP _t47) (TEMP _t11))
    mov [ rbp + -272 ], r11
    mov [ rbp + -456 ], r10
    mov r10, [ rbp + -112 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -368 ]
    mov r11, [ rbp + -456 ]
    mov r10, r11 ## (SUB (TEMP _t47) (CONST 8))
    mov [ rbp + -456 ], r11
    mov [ rbp + -368 ], r10
    mov r10, [ rbp + -368 ]
    mov r11, [ rbp + -112 ]
    subq r10, r11 ## (SUB (TEMP _t47) (CONST 8))
    mov [ rbp + -112 ], r11
    mov [ rbp + -368 ], r10
    mov r10, [ rbp + -304 ]
    mov r11, [ rbp + -368 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t13) (MEM (SUB (TEMP _t47) (CONST 8))))
    mov [ rbp + -368 ], r11
    mov [ rbp + -304 ], r10
    mov r10, [ rbp + -376 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -376 ], r10
    mov r10, [ rbp + -400 ]
    mov r11, [ rbp + -376 ]
    mov r10, r11 ## (MOVE (TEMP _t48) (CONST 0))
    mov [ rbp + -376 ], r11
    mov [ rbp + -400 ], r10
    mov r10, [ rbp + -400 ]
    mov r11, [ rbp + -288 ]
    cmpq r10, r11 ## (LEQ (TEMP _t48) (TEMP _t12))
    mov [ rbp + -288 ], r11
    mov [ rbp + -400 ], r10
    pushq rax
    mov rax, [ rbp + -392 ]
    setle al ## (LEQ (TEMP _t48) (TEMP _t12))
    mov [ rbp + -392 ], rax
    popq rax
    mov r10, [ rbp + -384 ]
    pushq rax
    mov rax, [ rbp + -392 ]
    movzx r10, al ## (LEQ (TEMP _t48) (TEMP _t12))
    mov [ rbp + -392 ], rax
    popq rax
    mov [ rbp + -384 ], r10
    mov r10, [ rbp + -240 ]
    mov r11, [ rbp + -384 ]
    mov r10, r11 ## (MOVE (TEMP _t50) (LEQ (TEMP _t48) (TEMP _t12)))
    mov [ rbp + -384 ], r11
    mov [ rbp + -240 ], r10
    mov r10, [ rbp + -408 ]
    mov r11, [ rbp + -288 ]
    mov r10, r11 ## (MOVE (TEMP _t49) (TEMP _t12))
    mov [ rbp + -288 ], r11
    mov [ rbp + -408 ], r10
    mov r10, [ rbp + -408 ]
    mov r11, [ rbp + -304 ]
    cmpq r10, r11 ## (LT (TEMP _t49) (TEMP _t13))
    mov [ rbp + -304 ], r11
    mov [ rbp + -408 ], r10
    pushq rax
    mov rax, [ rbp + -488 ]
    setl al ## (LT (TEMP _t49) (TEMP _t13))
    mov [ rbp + -488 ], rax
    popq rax
    mov r10, [ rbp + -472 ]
    pushq rax
    mov rax, [ rbp + -488 ]
    movzx r10, al ## (LT (TEMP _t49) (TEMP _t13))
    mov [ rbp + -488 ], rax
    popq rax
    mov [ rbp + -472 ], r10
    mov r10, [ rbp + -504 ]
    mov r11, [ rbp + -240 ]
    mov r10, r11 ## (AND (TEMP _t50) (LT (TEMP _t49) (TEMP _t13)))
    mov [ rbp + -240 ], r11
    mov [ rbp + -504 ], r10
    mov r10, [ rbp + -504 ]
    mov r11, [ rbp + -472 ]
    andq r10, r11 ## (AND (TEMP _t50) (LT (TEMP _t49) (TEMP _t13)))
    mov [ rbp + -472 ], r11
    mov [ rbp + -504 ], r10
    mov r10, [ rbp + -504 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t50) (LT (TEMP _t49) (TEMP _t13))) _l18)
    mov [ rbp + -504 ], r10
    je _l18 ## (CJUMP (AND (TEMP _t50) (LT (TEMP _t49) (TEMP _t13))) _l18)
_l19:
    callq _xi_out_of_bounds ## (CALL_STMT _t51 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -328 ]
    mov r10, rax ## (CALL_STMT _t51 (NAME _xi_out_of_bounds))
    mov [ rbp + -328 ], r10
_l18:
    mov r10, [ rbp + -344 ]
    mov r11, [ rbp + -272 ]
    mov r10, r11 ## (MOVE (TEMP _t53) (TEMP _t11))
    mov [ rbp + -272 ], r11
    mov [ rbp + -344 ], r10
    mov r10, [ rbp + -336 ]
    mov r11, [ rbp + -288 ]
    mov r10, r11 ## (MOVE (TEMP _t52) (TEMP _t12))
    mov [ rbp + -288 ], r11
    mov [ rbp + -336 ], r10
    mov r10, [ rbp + -520 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -520 ], r10
    mov r10, [ rbp + -424 ]
    mov r11, [ rbp + -336 ]
    mov r10, r11 ## (MUL (TEMP _t52) (CONST 8))
    mov [ rbp + -336 ], r11
    mov [ rbp + -424 ], r10
    mov r10, [ rbp + -424 ]
    mov r11, [ rbp + -520 ]
    imulq r10, r11 ## (MUL (TEMP _t52) (CONST 8))
    mov [ rbp + -520 ], r11
    mov [ rbp + -424 ], r10
    mov r10, [ rbp + -440 ]
    mov r11, [ rbp + -344 ]
    mov r10, r11 ## (ADD (TEMP _t53) (MUL (TEMP _t52) (CONST 8)))
    mov [ rbp + -344 ], r11
    mov [ rbp + -440 ], r10
    mov r10, [ rbp + -440 ]
    mov r11, [ rbp + -424 ]
    addq r10, r11 ## (ADD (TEMP _t53) (MUL (TEMP _t52) (CONST 8)))
    mov [ rbp + -424 ], r11
    mov [ rbp + -440 ], r10
    mov r10, [ rbp + -360 ]
    mov r11, [ rbp + -440 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t54) (MEM (ADD (TEMP _t53) (MUL (TEMP _t52) (CONST 8)))))
    mov [ rbp + -440 ], r11
    mov [ rbp + -360 ], r10
    mov r10, [ rbp + -280 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t55) (TEMP arr))
    mov [ rbp + -16 ], r11
    mov [ rbp + -280 ], r10
    mov r10, [ rbp + -360 ]
    mov rdi, r10 ## (CALL_STMT _t56 (NAME _IbinarySearch_it2iai) (TEMP _t54) (TEMP _t55))
    mov [ rbp + -360 ], r10
    mov r10, [ rbp + -280 ]
    mov rsi, r10 ## (CALL_STMT _t56 (NAME _IbinarySearch_it2iai) (TEMP _t54) (TEMP _t55))
    mov [ rbp + -280 ], r10
    callq _IbinarySearch_it2iai ## (CALL_STMT _t56 (NAME _IbinarySearch_it2iai) (TEMP _t54) (TEMP _t55))
    mov r10, [ rbp + -296 ]
    mov r10, rax ## (CALL_STMT _t56 (NAME _IbinarySearch_it2iai) (TEMP _t54) (TEMP _t55))
    mov [ rbp + -296 ], r10
    mov r10, [ rbp + -64 ]
    mov r11, [ rbp + -296 ]
    mov r10, r11 ## (MOVE (TEMP index) (TEMP _t56))
    mov [ rbp + -296 ], r11
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -312 ]
    mov r11, [ rbp + -64 ]
    mov r10, r11 ## (MOVE (TEMP _t57) (TEMP index))
    mov [ rbp + -64 ], r11
    mov [ rbp + -312 ], r10
    mov r10, [ rbp + -312 ]
    mov rdi, r10 ## (CALL_STMT _t58 (NAME _IunparseInt_aii) (TEMP _t57))
    mov [ rbp + -312 ], r10
    callq _IunparseInt_aii ## (CALL_STMT _t58 (NAME _IunparseInt_aii) (TEMP _t57))
    mov r10, [ rbp + -320 ]
    mov r10, rax ## (CALL_STMT _t58 (NAME _IunparseInt_aii) (TEMP _t57))
    mov [ rbp + -320 ], r10
    mov r10, [ rbp + -264 ]
    mov r11, [ rbp + -320 ]
    mov r10, r11 ## (MOVE (TEMP _t59) (TEMP _t58))
    mov [ rbp + -320 ], r11
    mov [ rbp + -264 ], r10
    mov r10, [ rbp + -264 ]
    mov rdi, r10 ## (CALL_STMT _t60 (NAME _Iprintln_pai) (TEMP _t59))
    mov [ rbp + -264 ], r10
    callq _Iprintln_pai ## (CALL_STMT _t60 (NAME _Iprintln_pai) (TEMP _t59))
    mov r10, [ rbp + -152 ]
    mov r10, rax ## (CALL_STMT _t60 (NAME _Iprintln_pai) (TEMP _t59))
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -160 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t61) (TEMP i))
    mov [ rbp + -56 ], r11
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -248 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -248 ], r10
    mov r10, [ rbp + -232 ]
    mov r11, [ rbp + -160 ]
    mov r10, r11 ## (ADD (TEMP _t61) (CONST 1))
    mov [ rbp + -160 ], r11
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -232 ]
    mov r11, [ rbp + -248 ]
    addq r10, r11 ## (ADD (TEMP _t61) (CONST 1))
    mov [ rbp + -248 ], r11
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -232 ]
    mov r10, r11 ## (MOVE (TEMP i) (ADD (TEMP _t61) (CONST 1)))
    mov [ rbp + -232 ], r11
    mov [ rbp + -56 ], r10
    jmp _l15 ## (JUMP (NAME _l15))
end__Imain_paai:
    addq rsp, 520
    mov rsp, rbp
    popq rbp
    retq
_IbinarySearch_it2iai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 632
_l20:
    mov r10, [ rbp + -104 ]
    mov r10, rdi ## (MOVE (TEMP x) (TEMP _ARG0))
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -16 ]
    mov r10, rsi ## (MOVE (TEMP arr) (TEMP _ARG1))
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -304 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -304 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -304 ]
    mov r10, r11 ## (MOVE (TEMP l) (CONST 0))
    mov [ rbp + -304 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -464 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t14) (TEMP arr))
    mov [ rbp + -16 ], r11
    mov [ rbp + -464 ], r10
    mov r10, [ rbp + -312 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -312 ], r10
    mov r10, [ rbp + -320 ]
    mov r11, [ rbp + -464 ]
    mov r10, r11 ## (SUB (TEMP _t14) (CONST 8))
    mov [ rbp + -464 ], r11
    mov [ rbp + -320 ], r10
    mov r10, [ rbp + -320 ]
    mov r11, [ rbp + -312 ]
    subq r10, r11 ## (SUB (TEMP _t14) (CONST 8))
    mov [ rbp + -312 ], r11
    mov [ rbp + -320 ], r10
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -320 ]
    mov r10, [ r11 ] ## (MOVE (TEMP r) (MEM (SUB (TEMP _t14) (CONST 8))))
    mov [ rbp + -320 ], r11
    mov [ rbp + -88 ], r10
_l0:
    mov r10, [ rbp + -424 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (MOVE (TEMP _t15) (TEMP l))
    mov [ rbp + -32 ], r11
    mov [ rbp + -424 ], r10
    mov r10, [ rbp + -424 ]
    mov r11, [ rbp + -88 ]
    cmpq r10, r11 ## (LEQ (TEMP _t15) (TEMP r))
    mov [ rbp + -88 ], r11
    mov [ rbp + -424 ], r10
    pushq rax
    mov rax, [ rbp + -480 ]
    setle al ## (LEQ (TEMP _t15) (TEMP r))
    mov [ rbp + -480 ], rax
    popq rax
    mov r10, [ rbp + -472 ]
    pushq rax
    mov rax, [ rbp + -480 ]
    movzx r10, al ## (LEQ (TEMP _t15) (TEMP r))
    mov [ rbp + -480 ], rax
    popq rax
    mov [ rbp + -472 ], r10
    mov r10, [ rbp + -472 ]
    cmpq r10, 1 ## (CJUMP (LEQ (TEMP _t15) (TEMP r)) _l1)
    mov [ rbp + -472 ], r10
    je _l1 ## (CJUMP (LEQ (TEMP _t15) (TEMP r)) _l1)
_l2:
    mov r10, [ rbp + -488 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -488 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -488 ]
    mov r10, r11 ## (MOVE (TEMP _t37) (CONST 0))
    mov [ rbp + -488 ], r11
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -496 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -496 ], r10
    mov r10, [ rbp + -456 ]
    mov r11, [ rbp + -96 ]
    mov r10, r11 ## (SUB (TEMP _t37) (CONST 1))
    mov [ rbp + -96 ], r11
    mov [ rbp + -456 ], r10
    mov r10, [ rbp + -456 ]
    mov r11, [ rbp + -496 ]
    subq r10, r11 ## (SUB (TEMP _t37) (CONST 1))
    mov [ rbp + -496 ], r11
    mov [ rbp + -456 ], r10
    mov r10, [ rbp + -8 ]
    mov r11, [ rbp + -456 ]
    mov r10, r11 ## (MOVE (TEMP _t7) (SUB (TEMP _t37) (CONST 1)))
    mov [ rbp + -456 ], r11
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -8 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t7))
    mov [ rbp + -8 ], r10
    jmp end__IbinarySearch_it2iai ## (RETURN ())
_l1:
    mov r10, [ rbp + -448 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (MOVE (TEMP _t18) (TEMP l))
    mov [ rbp + -32 ], r11
    mov [ rbp + -448 ], r10
    mov r10, [ rbp + -432 ]
    mov r11, [ rbp + -88 ]
    mov r10, r11 ## (MOVE (TEMP _t16) (TEMP r))
    mov [ rbp + -88 ], r11
    mov [ rbp + -432 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -432 ]
    mov r10, r11 ## (SUB (TEMP _t16) (TEMP l))
    mov [ rbp + -432 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -32 ]
    subq r10, r11 ## (SUB (TEMP _t16) (TEMP l))
    mov [ rbp + -32 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -440 ]
    mov r11, [ rbp + -40 ]
    mov r10, r11 ## (MOVE (TEMP _t17) (SUB (TEMP _t16) (TEMP l)))
    mov [ rbp + -40 ], r11
    mov [ rbp + -440 ], r10
    mov r10, [ rbp + -48 ]
    movabsq r10, 2 ## (CONST 2)
    mov [ rbp + -48 ], r10
    pushq rax ## (DIV (TEMP _t17) (CONST 2))
    pushq rdx ## (DIV (TEMP _t17) (CONST 2))
    mov r10, [ rbp + -440 ]
    mov rax, r10 ## (DIV (TEMP _t17) (CONST 2))
    mov [ rbp + -440 ], r10
    cqo ## (DIV (TEMP _t17) (CONST 2))
    mov r10, [ rbp + -48 ]
    idivq r10 ## (DIV (TEMP _t17) (CONST 2))
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -64 ]
    mov r10, rax ## (DIV (TEMP _t17) (CONST 2))
    mov [ rbp + -64 ], r10
    popq rdx ## (DIV (TEMP _t17) (CONST 2))
    popq rax ## (DIV (TEMP _t17) (CONST 2))
    mov r10, [ rbp + -72 ]
    mov r11, [ rbp + -448 ]
    mov r10, r11 ## (ADD (TEMP _t18) (DIV (TEMP _t17) (CONST 2)))
    mov [ rbp + -448 ], r11
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -72 ]
    mov r11, [ rbp + -64 ]
    addq r10, r11 ## (ADD (TEMP _t18) (DIV (TEMP _t17) (CONST 2)))
    mov [ rbp + -64 ], r11
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -72 ]
    mov r10, r11 ## (MOVE (TEMP m) (ADD (TEMP _t18) (DIV (TEMP _t17) (CONST 2))))
    mov [ rbp + -72 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -592 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t1) (TEMP m))
    mov [ rbp + -56 ], r11
    mov [ rbp + -592 ], r10
    mov r10, [ rbp + -600 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t0) (TEMP arr))
    mov [ rbp + -16 ], r11
    mov [ rbp + -600 ], r10
    mov r10, [ rbp + -384 ]
    mov r11, [ rbp + -600 ]
    mov r10, r11 ## (MOVE (TEMP _t19) (TEMP _t0))
    mov [ rbp + -600 ], r11
    mov [ rbp + -384 ], r10
    mov r10, [ rbp + -80 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -552 ]
    mov r11, [ rbp + -384 ]
    mov r10, r11 ## (SUB (TEMP _t19) (CONST 8))
    mov [ rbp + -384 ], r11
    mov [ rbp + -552 ], r10
    mov r10, [ rbp + -552 ]
    mov r11, [ rbp + -80 ]
    subq r10, r11 ## (SUB (TEMP _t19) (CONST 8))
    mov [ rbp + -80 ], r11
    mov [ rbp + -552 ], r10
    mov r10, [ rbp + -616 ]
    mov r11, [ rbp + -552 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t2) (MEM (SUB (TEMP _t19) (CONST 8))))
    mov [ rbp + -552 ], r11
    mov [ rbp + -616 ], r10
    mov r10, [ rbp + -560 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -560 ], r10
    mov r10, [ rbp + -288 ]
    mov r11, [ rbp + -560 ]
    mov r10, r11 ## (MOVE (TEMP _t20) (CONST 0))
    mov [ rbp + -560 ], r11
    mov [ rbp + -288 ], r10
    mov r10, [ rbp + -288 ]
    mov r11, [ rbp + -592 ]
    cmpq r10, r11 ## (LEQ (TEMP _t20) (TEMP _t1))
    mov [ rbp + -592 ], r11
    mov [ rbp + -288 ], r10
    pushq rax
    mov rax, [ rbp + -576 ]
    setle al ## (LEQ (TEMP _t20) (TEMP _t1))
    mov [ rbp + -576 ], rax
    popq rax
    mov r10, [ rbp + -568 ]
    pushq rax
    mov rax, [ rbp + -576 ]
    movzx r10, al ## (LEQ (TEMP _t20) (TEMP _t1))
    mov [ rbp + -576 ], rax
    popq rax
    mov [ rbp + -568 ], r10
    mov r10, [ rbp + -256 ]
    mov r11, [ rbp + -568 ]
    mov r10, r11 ## (MOVE (TEMP _t22) (LEQ (TEMP _t20) (TEMP _t1)))
    mov [ rbp + -568 ], r11
    mov [ rbp + -256 ], r10
    mov r10, [ rbp + -296 ]
    mov r11, [ rbp + -592 ]
    mov r10, r11 ## (MOVE (TEMP _t21) (TEMP _t1))
    mov [ rbp + -592 ], r11
    mov [ rbp + -296 ], r10
    mov r10, [ rbp + -296 ]
    mov r11, [ rbp + -616 ]
    cmpq r10, r11 ## (LT (TEMP _t21) (TEMP _t2))
    mov [ rbp + -616 ], r11
    mov [ rbp + -296 ], r10
    pushq rax
    mov rax, [ rbp + -504 ]
    setl al ## (LT (TEMP _t21) (TEMP _t2))
    mov [ rbp + -504 ], rax
    popq rax
    mov r10, [ rbp + -584 ]
    pushq rax
    mov rax, [ rbp + -504 ]
    movzx r10, al ## (LT (TEMP _t21) (TEMP _t2))
    mov [ rbp + -504 ], rax
    popq rax
    mov [ rbp + -584 ], r10
    mov r10, [ rbp + -512 ]
    mov r11, [ rbp + -256 ]
    mov r10, r11 ## (AND (TEMP _t22) (LT (TEMP _t21) (TEMP _t2)))
    mov [ rbp + -256 ], r11
    mov [ rbp + -512 ], r10
    mov r10, [ rbp + -512 ]
    mov r11, [ rbp + -584 ]
    andq r10, r11 ## (AND (TEMP _t22) (LT (TEMP _t21) (TEMP _t2)))
    mov [ rbp + -584 ], r11
    mov [ rbp + -512 ], r10
    mov r10, [ rbp + -512 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t22) (LT (TEMP _t21) (TEMP _t2))) _l6)
    mov [ rbp + -512 ], r10
    je _l6 ## (CJUMP (AND (TEMP _t22) (LT (TEMP _t21) (TEMP _t2))) _l6)
_l7:
    callq _xi_out_of_bounds ## (CALL_STMT _t23 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -264 ]
    mov r10, rax ## (CALL_STMT _t23 (NAME _xi_out_of_bounds))
    mov [ rbp + -264 ], r10
_l6:
    mov r10, [ rbp + -280 ]
    mov r11, [ rbp + -600 ]
    mov r10, r11 ## (MOVE (TEMP _t25) (TEMP _t0))
    mov [ rbp + -600 ], r11
    mov [ rbp + -280 ], r10
    mov r10, [ rbp + -272 ]
    mov r11, [ rbp + -592 ]
    mov r10, r11 ## (MOVE (TEMP _t24) (TEMP _t1))
    mov [ rbp + -592 ], r11
    mov [ rbp + -272 ], r10
    mov r10, [ rbp + -520 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -520 ], r10
    mov r10, [ rbp + -528 ]
    mov r11, [ rbp + -272 ]
    mov r10, r11 ## (MUL (TEMP _t24) (CONST 8))
    mov [ rbp + -272 ], r11
    mov [ rbp + -528 ], r10
    mov r10, [ rbp + -528 ]
    mov r11, [ rbp + -520 ]
    imulq r10, r11 ## (MUL (TEMP _t24) (CONST 8))
    mov [ rbp + -520 ], r11
    mov [ rbp + -528 ], r10
    mov r10, [ rbp + -536 ]
    mov r11, [ rbp + -280 ]
    mov r10, r11 ## (ADD (TEMP _t25) (MUL (TEMP _t24) (CONST 8)))
    mov [ rbp + -280 ], r11
    mov [ rbp + -536 ], r10
    mov r10, [ rbp + -536 ]
    mov r11, [ rbp + -528 ]
    addq r10, r11 ## (ADD (TEMP _t25) (MUL (TEMP _t24) (CONST 8)))
    mov [ rbp + -528 ], r11
    mov [ rbp + -536 ], r10
    mov r10, [ rbp + -224 ]
    mov r11, [ rbp + -536 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t26) (MEM (ADD (TEMP _t25) (MUL (TEMP _t24) (CONST 8)))))
    mov [ rbp + -536 ], r11
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -224 ]
    mov r11, [ rbp + -104 ]
    cmpq r10, r11 ## (EQ (TEMP _t26) (TEMP x))
    mov [ rbp + -104 ], r11
    mov [ rbp + -224 ], r10
    pushq rax
    mov rax, [ rbp + -392 ]
    setz al ## (EQ (TEMP _t26) (TEMP x))
    mov [ rbp + -392 ], rax
    popq rax
    mov r10, [ rbp + -544 ]
    pushq rax
    mov rax, [ rbp + -392 ]
    movzx r10, al ## (EQ (TEMP _t26) (TEMP x))
    mov [ rbp + -392 ], rax
    popq rax
    mov [ rbp + -544 ], r10
    mov r10, [ rbp + -544 ]
    cmpq r10, 1 ## (CJUMP (EQ (TEMP _t26) (TEMP x)) _l3)
    mov [ rbp + -544 ], r10
    je _l3 ## (CJUMP (EQ (TEMP _t26) (TEMP x)) _l3)
_l4:
_l5:
    mov r10, [ rbp + -624 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t5) (TEMP m))
    mov [ rbp + -56 ], r11
    mov [ rbp + -624 ], r10
    mov r10, [ rbp + -632 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t4) (TEMP arr))
    mov [ rbp + -16 ], r11
    mov [ rbp + -632 ], r10
    mov r10, [ rbp + -232 ]
    mov r11, [ rbp + -632 ]
    mov r10, r11 ## (MOVE (TEMP _t27) (TEMP _t4))
    mov [ rbp + -632 ], r11
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -400 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -400 ], r10
    mov r10, [ rbp + -408 ]
    mov r11, [ rbp + -232 ]
    mov r10, r11 ## (SUB (TEMP _t27) (CONST 8))
    mov [ rbp + -232 ], r11
    mov [ rbp + -408 ], r10
    mov r10, [ rbp + -408 ]
    mov r11, [ rbp + -400 ]
    subq r10, r11 ## (SUB (TEMP _t27) (CONST 8))
    mov [ rbp + -400 ], r11
    mov [ rbp + -408 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -408 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t6) (MEM (SUB (TEMP _t27) (CONST 8))))
    mov [ rbp + -408 ], r11
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -416 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -416 ], r10
    mov r10, [ rbp + -240 ]
    mov r11, [ rbp + -416 ]
    mov r10, r11 ## (MOVE (TEMP _t28) (CONST 0))
    mov [ rbp + -416 ], r11
    mov [ rbp + -240 ], r10
    mov r10, [ rbp + -240 ]
    mov r11, [ rbp + -624 ]
    cmpq r10, r11 ## (LEQ (TEMP _t28) (TEMP _t5))
    mov [ rbp + -624 ], r11
    mov [ rbp + -240 ], r10
    pushq rax
    mov rax, [ rbp + -336 ]
    setle al ## (LEQ (TEMP _t28) (TEMP _t5))
    mov [ rbp + -336 ], rax
    popq rax
    mov r10, [ rbp + -328 ]
    pushq rax
    mov rax, [ rbp + -336 ]
    movzx r10, al ## (LEQ (TEMP _t28) (TEMP _t5))
    mov [ rbp + -336 ], rax
    popq rax
    mov [ rbp + -328 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -328 ]
    mov r10, r11 ## (MOVE (TEMP _t30) (LEQ (TEMP _t28) (TEMP _t5)))
    mov [ rbp + -328 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -248 ]
    mov r11, [ rbp + -624 ]
    mov r10, r11 ## (MOVE (TEMP _t29) (TEMP _t5))
    mov [ rbp + -624 ], r11
    mov [ rbp + -248 ], r10
    mov r10, [ rbp + -248 ]
    mov r11, [ rbp + -24 ]
    cmpq r10, r11 ## (LT (TEMP _t29) (TEMP _t6))
    mov [ rbp + -24 ], r11
    mov [ rbp + -248 ], r10
    pushq rax
    mov rax, [ rbp + -352 ]
    setl al ## (LT (TEMP _t29) (TEMP _t6))
    mov [ rbp + -352 ], rax
    popq rax
    mov r10, [ rbp + -344 ]
    pushq rax
    mov rax, [ rbp + -352 ]
    movzx r10, al ## (LT (TEMP _t29) (TEMP _t6))
    mov [ rbp + -352 ], rax
    popq rax
    mov [ rbp + -344 ], r10
    mov r10, [ rbp + -360 ]
    mov r11, [ rbp + -144 ]
    mov r10, r11 ## (AND (TEMP _t30) (LT (TEMP _t29) (TEMP _t6)))
    mov [ rbp + -144 ], r11
    mov [ rbp + -360 ], r10
    mov r10, [ rbp + -360 ]
    mov r11, [ rbp + -344 ]
    andq r10, r11 ## (AND (TEMP _t30) (LT (TEMP _t29) (TEMP _t6)))
    mov [ rbp + -344 ], r11
    mov [ rbp + -360 ], r10
    mov r10, [ rbp + -360 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t30) (LT (TEMP _t29) (TEMP _t6))) _l11)
    mov [ rbp + -360 ], r10
    je _l11 ## (CJUMP (AND (TEMP _t30) (LT (TEMP _t29) (TEMP _t6))) _l11)
_l12:
    callq _xi_out_of_bounds ## (CALL_STMT _t31 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -152 ]
    mov r10, rax ## (CALL_STMT _t31 (NAME _xi_out_of_bounds))
    mov [ rbp + -152 ], r10
_l11:
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -632 ]
    mov r10, r11 ## (MOVE (TEMP _t33) (TEMP _t4))
    mov [ rbp + -632 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -160 ]
    mov r11, [ rbp + -624 ]
    mov r10, r11 ## (MOVE (TEMP _t32) (TEMP _t5))
    mov [ rbp + -624 ], r11
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -368 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -368 ], r10
    mov r10, [ rbp + -376 ]
    mov r11, [ rbp + -160 ]
    mov r10, r11 ## (MUL (TEMP _t32) (CONST 8))
    mov [ rbp + -160 ], r11
    mov [ rbp + -376 ], r10
    mov r10, [ rbp + -376 ]
    mov r11, [ rbp + -368 ]
    imulq r10, r11 ## (MUL (TEMP _t32) (CONST 8))
    mov [ rbp + -368 ], r11
    mov [ rbp + -376 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -112 ]
    mov r10, r11 ## (ADD (TEMP _t33) (MUL (TEMP _t32) (CONST 8)))
    mov [ rbp + -112 ], r11
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -376 ]
    addq r10, r11 ## (ADD (TEMP _t33) (MUL (TEMP _t32) (CONST 8)))
    mov [ rbp + -376 ], r11
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -200 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t34) (MEM (ADD (TEMP _t33) (MUL (TEMP _t32) (CONST 8)))))
    mov [ rbp + -200 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -104 ]
    cmpq r10, r11 ## (LT (TEMP _t34) (TEMP x))
    mov [ rbp + -104 ], r11
    mov [ rbp + -120 ], r10
    pushq rax
    mov rax, [ rbp + -216 ]
    setl al ## (LT (TEMP _t34) (TEMP x))
    mov [ rbp + -216 ], rax
    popq rax
    mov r10, [ rbp + -208 ]
    pushq rax
    mov rax, [ rbp + -216 ]
    movzx r10, al ## (LT (TEMP _t34) (TEMP x))
    mov [ rbp + -216 ], rax
    popq rax
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -208 ]
    cmpq r10, 1 ## (CJUMP (LT (TEMP _t34) (TEMP x)) _l8)
    mov [ rbp + -208 ], r10
    je _l8 ## (CJUMP (LT (TEMP _t34) (TEMP x)) _l8)
_l9:
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t35) (TEMP m))
    mov [ rbp + -56 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -168 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -128 ]
    mov r10, r11 ## (SUB (TEMP _t35) (CONST 1))
    mov [ rbp + -128 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -168 ]
    subq r10, r11 ## (SUB (TEMP _t35) (CONST 1))
    mov [ rbp + -168 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -176 ]
    mov r10, r11 ## (MOVE (TEMP r) (SUB (TEMP _t35) (CONST 1)))
    mov [ rbp + -176 ], r11
    mov [ rbp + -88 ], r10
_l10:
    jmp _l0 ## (JUMP (NAME _l0))
_l3:
    mov r10, [ rbp + -608 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t3) (TEMP m))
    mov [ rbp + -56 ], r11
    mov [ rbp + -608 ], r10
    mov r10, [ rbp + -608 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t3))
    mov [ rbp + -608 ], r10
    jmp end__IbinarySearch_it2iai ## (RETURN ())
_l8:
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t36) (TEMP m))
    mov [ rbp + -56 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -184 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -184 ], r10
    mov r10, [ rbp + -192 ]
    mov r11, [ rbp + -136 ]
    mov r10, r11 ## (ADD (TEMP _t36) (CONST 1))
    mov [ rbp + -136 ], r11
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -192 ]
    mov r11, [ rbp + -184 ]
    addq r10, r11 ## (ADD (TEMP _t36) (CONST 1))
    mov [ rbp + -184 ], r11
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -192 ]
    mov r10, r11 ## (MOVE (TEMP l) (ADD (TEMP _t36) (CONST 1)))
    mov [ rbp + -192 ], r11
    mov [ rbp + -32 ], r10
    jmp _l10 ## (JUMP (NAME _l10))
end__IbinarySearch_it2iai:
    addq rsp, 632
    mov rsp, rbp
    popq rbp
    retq
