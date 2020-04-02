.intel_syntax noprefix
.globl _Imain_paai
.align 4
_Imain_paai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 352
_l17:
    mov r10, [ rbp + -16 ]
    mov r10, rdi ## (MOVE (TEMP args) (TEMP _ARG0))
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -192 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -280 ]
    mov r11, [ rbp + -192 ]
    mov r10, r11 ## (MOVE (TEMP _t23) (CONST 1))
    mov [ rbp + -192 ], r11
    mov [ rbp + -280 ], r10
    mov r10, [ rbp + -280 ]
    mov rdi, r10 ## (CALL_STMT _t24 (NAME _IclimbStairs_ii) (TEMP _t23))
    mov [ rbp + -280 ], r10
    callq _IclimbStairs_ii ## (CALL_STMT _t24 (NAME _IclimbStairs_ii) (TEMP _t23))
    mov r10, [ rbp + -296 ]
    mov r10, rax ## (CALL_STMT _t24 (NAME _IclimbStairs_ii) (TEMP _t23))
    mov [ rbp + -296 ], r10
    mov r10, [ rbp + -264 ]
    mov r11, [ rbp + -296 ]
    mov r10, r11 ## (MOVE (TEMP val1) (TEMP _t24))
    mov [ rbp + -296 ], r11
    mov [ rbp + -264 ], r10
    mov r10, [ rbp + -200 ]
    movabsq r10, 5 ## (CONST 5)
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -312 ]
    mov r11, [ rbp + -200 ]
    mov r10, r11 ## (MOVE (TEMP _t25) (CONST 5))
    mov [ rbp + -200 ], r11
    mov [ rbp + -312 ], r10
    mov r10, [ rbp + -312 ]
    mov rdi, r10 ## (CALL_STMT _t26 (NAME _IclimbStairs_ii) (TEMP _t25))
    mov [ rbp + -312 ], r10
    callq _IclimbStairs_ii ## (CALL_STMT _t26 (NAME _IclimbStairs_ii) (TEMP _t25))
    mov r10, [ rbp + -216 ]
    mov r10, rax ## (CALL_STMT _t26 (NAME _IclimbStairs_ii) (TEMP _t25))
    mov [ rbp + -216 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -216 ]
    mov r10, r11 ## (MOVE (TEMP val2) (TEMP _t26))
    mov [ rbp + -216 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -208 ]
    movabsq r10, 6 ## (CONST 6)
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -232 ]
    mov r11, [ rbp + -208 ]
    mov r10, r11 ## (MOVE (TEMP _t27) (CONST 6))
    mov [ rbp + -208 ], r11
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -232 ]
    mov rdi, r10 ## (CALL_STMT _t28 (NAME _IclimbStairs_ii) (TEMP _t27))
    mov [ rbp + -232 ], r10
    callq _IclimbStairs_ii ## (CALL_STMT _t28 (NAME _IclimbStairs_ii) (TEMP _t27))
    mov r10, [ rbp + -248 ]
    mov r10, rax ## (CALL_STMT _t28 (NAME _IclimbStairs_ii) (TEMP _t27))
    mov [ rbp + -248 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -248 ]
    mov r10, r11 ## (MOVE (TEMP val3) (TEMP _t28))
    mov [ rbp + -248 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -160 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -256 ]
    mov r11, [ rbp + -160 ]
    mov r10, r11 ## (MOVE (TEMP _t29) (CONST 0))
    mov [ rbp + -160 ], r11
    mov [ rbp + -256 ], r10
    mov r10, [ rbp + -168 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -256 ]
    mov r10, r11 ## (SUB (TEMP _t29) (CONST 1))
    mov [ rbp + -256 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -168 ]
    subq r10, r11 ## (SUB (TEMP _t29) (CONST 1))
    mov [ rbp + -168 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -176 ]
    mov r10, r11 ## (MOVE (TEMP _t30) (SUB (TEMP _t29) (CONST 1)))
    mov [ rbp + -176 ], r11
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -88 ]
    mov rdi, r10 ## (CALL_STMT _t31 (NAME _IclimbStairs_ii) (TEMP _t30))
    mov [ rbp + -88 ], r10
    callq _IclimbStairs_ii ## (CALL_STMT _t31 (NAME _IclimbStairs_ii) (TEMP _t30))
    mov r10, [ rbp + -104 ]
    mov r10, rax ## (CALL_STMT _t31 (NAME _IclimbStairs_ii) (TEMP _t30))
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -104 ]
    mov r10, r11 ## (MOVE (TEMP val4) (TEMP _t31))
    mov [ rbp + -104 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -184 ]
    movabsq r10, 10 ## (CONST 10)
    mov [ rbp + -184 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -184 ]
    mov r10, r11 ## (MOVE (TEMP _t32) (CONST 10))
    mov [ rbp + -184 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -120 ]
    mov rdi, r10 ## (CALL_STMT _t33 (NAME _IclimbStairs_ii) (TEMP _t32))
    mov [ rbp + -120 ], r10
    callq _IclimbStairs_ii ## (CALL_STMT _t33 (NAME _IclimbStairs_ii) (TEMP _t32))
    mov r10, [ rbp + -48 ]
    mov r10, rax ## (CALL_STMT _t33 (NAME _IclimbStairs_ii) (TEMP _t32))
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -48 ]
    mov r10, r11 ## (MOVE (TEMP val5) (TEMP _t33))
    mov [ rbp + -48 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -264 ]
    mov r10, r11 ## (MOVE (TEMP _t34) (TEMP val1))
    mov [ rbp + -264 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -56 ]
    mov rdi, r10 ## (CALL_STMT _t35 (NAME _IunparseInt_aii) (TEMP _t34))
    mov [ rbp + -56 ], r10
    callq _IunparseInt_aii ## (CALL_STMT _t35 (NAME _IunparseInt_aii) (TEMP _t34))
    mov r10, [ rbp + -64 ]
    mov r10, rax ## (CALL_STMT _t35 (NAME _IunparseInt_aii) (TEMP _t34))
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -72 ]
    mov r11, [ rbp + -64 ]
    mov r10, r11 ## (MOVE (TEMP _t36) (TEMP _t35))
    mov [ rbp + -64 ], r11
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -72 ]
    mov rdi, r10 ## (CALL_STMT _t37 (NAME _Iprintln_pai) (TEMP _t36))
    mov [ rbp + -72 ], r10
    callq _Iprintln_pai ## (CALL_STMT _t37 (NAME _Iprintln_pai) (TEMP _t36))
    mov r10, [ rbp + -24 ]
    mov r10, rax ## (CALL_STMT _t37 (NAME _Iprintln_pai) (TEMP _t36))
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -152 ]
    mov r10, r11 ## (MOVE (TEMP _t38) (TEMP val2))
    mov [ rbp + -152 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -32 ]
    mov rdi, r10 ## (CALL_STMT _t39 (NAME _IunparseInt_aii) (TEMP _t38))
    mov [ rbp + -32 ], r10
    callq _IunparseInt_aii ## (CALL_STMT _t39 (NAME _IunparseInt_aii) (TEMP _t38))
    mov r10, [ rbp + -40 ]
    mov r10, rax ## (CALL_STMT _t39 (NAME _IunparseInt_aii) (TEMP _t38))
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -328 ]
    mov r11, [ rbp + -40 ]
    mov r10, r11 ## (MOVE (TEMP _t40) (TEMP _t39))
    mov [ rbp + -40 ], r11
    mov [ rbp + -328 ], r10
    mov r10, [ rbp + -328 ]
    mov rdi, r10 ## (CALL_STMT _t41 (NAME _Iprintln_pai) (TEMP _t40))
    mov [ rbp + -328 ], r10
    callq _Iprintln_pai ## (CALL_STMT _t41 (NAME _Iprintln_pai) (TEMP _t40))
    mov r10, [ rbp + -336 ]
    mov r10, rax ## (CALL_STMT _t41 (NAME _Iprintln_pai) (TEMP _t40))
    mov [ rbp + -336 ], r10
    mov r10, [ rbp + -344 ]
    mov r11, [ rbp + -144 ]
    mov r10, r11 ## (MOVE (TEMP _t42) (TEMP val3))
    mov [ rbp + -144 ], r11
    mov [ rbp + -344 ], r10
    mov r10, [ rbp + -344 ]
    mov rdi, r10 ## (CALL_STMT _t43 (NAME _IunparseInt_aii) (TEMP _t42))
    mov [ rbp + -344 ], r10
    callq _IunparseInt_aii ## (CALL_STMT _t43 (NAME _IunparseInt_aii) (TEMP _t42))
    mov r10, [ rbp + -352 ]
    mov r10, rax ## (CALL_STMT _t43 (NAME _IunparseInt_aii) (TEMP _t42))
    mov [ rbp + -352 ], r10
    mov r10, [ rbp + -272 ]
    mov r11, [ rbp + -352 ]
    mov r10, r11 ## (MOVE (TEMP _t44) (TEMP _t43))
    mov [ rbp + -352 ], r11
    mov [ rbp + -272 ], r10
    mov r10, [ rbp + -272 ]
    mov rdi, r10 ## (CALL_STMT _t45 (NAME _Iprintln_pai) (TEMP _t44))
    mov [ rbp + -272 ], r10
    callq _Iprintln_pai ## (CALL_STMT _t45 (NAME _Iprintln_pai) (TEMP _t44))
    mov r10, [ rbp + -288 ]
    mov r10, rax ## (CALL_STMT _t45 (NAME _Iprintln_pai) (TEMP _t44))
    mov [ rbp + -288 ], r10
    mov r10, [ rbp + -304 ]
    mov r11, [ rbp + -136 ]
    mov r10, r11 ## (MOVE (TEMP _t46) (TEMP val4))
    mov [ rbp + -136 ], r11
    mov [ rbp + -304 ], r10
    mov r10, [ rbp + -304 ]
    mov rdi, r10 ## (CALL_STMT _t47 (NAME _IunparseInt_aii) (TEMP _t46))
    mov [ rbp + -304 ], r10
    callq _IunparseInt_aii ## (CALL_STMT _t47 (NAME _IunparseInt_aii) (TEMP _t46))
    mov r10, [ rbp + -320 ]
    mov r10, rax ## (CALL_STMT _t47 (NAME _IunparseInt_aii) (TEMP _t46))
    mov [ rbp + -320 ], r10
    mov r10, [ rbp + -224 ]
    mov r11, [ rbp + -320 ]
    mov r10, r11 ## (MOVE (TEMP _t48) (TEMP _t47))
    mov [ rbp + -320 ], r11
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -224 ]
    mov rdi, r10 ## (CALL_STMT _t49 (NAME _Iprintln_pai) (TEMP _t48))
    mov [ rbp + -224 ], r10
    callq _Iprintln_pai ## (CALL_STMT _t49 (NAME _Iprintln_pai) (TEMP _t48))
    mov r10, [ rbp + -240 ]
    mov r10, rax ## (CALL_STMT _t49 (NAME _Iprintln_pai) (TEMP _t48))
    mov [ rbp + -240 ], r10
    mov r10, [ rbp + -8 ]
    mov r11, [ rbp + -128 ]
    mov r10, r11 ## (MOVE (TEMP _t50) (TEMP val5))
    mov [ rbp + -128 ], r11
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -8 ]
    mov rdi, r10 ## (CALL_STMT _t51 (NAME _IunparseInt_aii) (TEMP _t50))
    mov [ rbp + -8 ], r10
    callq _IunparseInt_aii ## (CALL_STMT _t51 (NAME _IunparseInt_aii) (TEMP _t50))
    mov r10, [ rbp + -80 ]
    mov r10, rax ## (CALL_STMT _t51 (NAME _IunparseInt_aii) (TEMP _t50))
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -80 ]
    mov r10, r11 ## (MOVE (TEMP _t52) (TEMP _t51))
    mov [ rbp + -80 ], r11
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -96 ]
    mov rdi, r10 ## (CALL_STMT _t53 (NAME _Iprintln_pai) (TEMP _t52))
    mov [ rbp + -96 ], r10
    callq _Iprintln_pai ## (CALL_STMT _t53 (NAME _Iprintln_pai) (TEMP _t52))
    mov r10, [ rbp + -112 ]
    mov r10, rax ## (CALL_STMT _t53 (NAME _Iprintln_pai) (TEMP _t52))
    mov [ rbp + -112 ], r10
    jmp end__Imain_paai ## (RETURN ())
end__Imain_paai:
    addq rsp, 352
    mov rsp, rbp
    popq rbp
    retq
_IclimbStairs_ii:
    pushq rbp
    mov rbp, rsp
    subq rsp, 1712
_l18:
    mov r10, [ rbp + -144 ]
    mov r10, rdi ## (MOVE (TEMP n) (TEMP _ARG0))
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -888 ]
    mov r11, [ rbp + -144 ]
    mov r10, r11 ## (MOVE (TEMP _t54) (TEMP n))
    mov [ rbp + -144 ], r11
    mov [ rbp + -888 ], r10
    mov r10, [ rbp + -608 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -608 ], r10
    mov r10, [ rbp + -888 ]
    mov r11, [ rbp + -608 ]
    cmpq r10, r11 ## (LT (TEMP _t54) (CONST 1))
    mov [ rbp + -608 ], r11
    mov [ rbp + -888 ], r10
    pushq rax
    mov rax, [ rbp + -624 ]
    setl al ## (LT (TEMP _t54) (CONST 1))
    mov [ rbp + -624 ], rax
    popq rax
    mov r10, [ rbp + -616 ]
    pushq rax
    mov rax, [ rbp + -624 ]
    movzx r10, al ## (LT (TEMP _t54) (CONST 1))
    mov [ rbp + -624 ], rax
    popq rax
    mov [ rbp + -616 ], r10
    mov r10, [ rbp + -616 ]
    cmpq r10, 1 ## (CJUMP (LT (TEMP _t54) (CONST 1)) _l0)
    mov [ rbp + -616 ], r10
    je _l0 ## (CJUMP (LT (TEMP _t54) (CONST 1)) _l0)
_l1:
_l2:
    mov r10, [ rbp + -632 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -632 ], r10
    mov r10, [ rbp + -864 ]
    mov r11, [ rbp + -632 ]
    mov r10, r11 ## (MOVE (TEMP _t56) (CONST 8))
    mov [ rbp + -632 ], r11
    mov [ rbp + -864 ], r10
    mov r10, [ rbp + -592 ]
    movabsq r10, 2 ## (CONST 2)
    mov [ rbp + -592 ], r10
    mov r10, [ rbp + -856 ]
    mov r11, [ rbp + -592 ]
    mov r10, r11 ## (MOVE (TEMP _t55) (CONST 2))
    mov [ rbp + -592 ], r11
    mov [ rbp + -856 ], r10
    mov r10, [ rbp + -600 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -600 ], r10
    mov r10, [ rbp + -520 ]
    mov r11, [ rbp + -856 ]
    mov r10, r11 ## (ADD (TEMP _t55) (CONST 1))
    mov [ rbp + -856 ], r11
    mov [ rbp + -520 ], r10
    mov r10, [ rbp + -520 ]
    mov r11, [ rbp + -600 ]
    addq r10, r11 ## (ADD (TEMP _t55) (CONST 1))
    mov [ rbp + -600 ], r11
    mov [ rbp + -520 ], r10
    mov r10, [ rbp + -528 ]
    mov r11, [ rbp + -864 ]
    mov r10, r11 ## (MUL (TEMP _t56) (ADD (TEMP _t55) (CONST 1)))
    mov [ rbp + -864 ], r11
    mov [ rbp + -528 ], r10
    mov r10, [ rbp + -528 ]
    mov r11, [ rbp + -520 ]
    imulq r10, r11 ## (MUL (TEMP _t56) (ADD (TEMP _t55) (CONST 1)))
    mov [ rbp + -520 ], r11
    mov [ rbp + -528 ], r10
    mov r10, [ rbp + -872 ]
    mov r11, [ rbp + -528 ]
    mov r10, r11 ## (MOVE (TEMP _t57) (MUL (TEMP _t56) (ADD (TEMP _t55) (CONST 1))))
    mov [ rbp + -528 ], r11
    mov [ rbp + -872 ], r10
    mov r10, [ rbp + -872 ]
    mov rdi, r10 ## (CALL_STMT _t58 (NAME _xi_alloc) (TEMP _t57))
    mov [ rbp + -872 ], r10
    callq _xi_alloc ## (CALL_STMT _t58 (NAME _xi_alloc) (TEMP _t57))
    mov r10, [ rbp + -880 ]
    mov r10, rax ## (CALL_STMT _t58 (NAME _xi_alloc) (TEMP _t57))
    mov [ rbp + -880 ], r10
    mov r10, [ rbp + -440 ]
    mov r11, [ rbp + -880 ]
    mov r10, r11 ## (MOVE (TEMP _t1) (TEMP _t58))
    mov [ rbp + -880 ], r11
    mov [ rbp + -440 ], r10
    mov r10, [ rbp + -848 ]
    mov r11, [ rbp + -440 ]
    mov r10, r11 ## (MOVE (TEMP _t59) (TEMP _t1))
    mov [ rbp + -440 ], r11
    mov [ rbp + -848 ], r10
    mov r10, [ rbp + -536 ]
    movabsq r10, 2 ## (CONST 2)
    mov [ rbp + -536 ], r10
    mov r10, [ rbp + -848 ]
    mov r11, [ rbp + -536 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t59)) (CONST 2))
    mov [ rbp + -536 ], r11
    mov [ rbp + -848 ], r10
    mov r10, [ rbp + -1232 ]
    mov r11, [ rbp + -440 ]
    mov r10, r11 ## (MOVE (TEMP _t60) (TEMP _t1))
    mov [ rbp + -440 ], r11
    mov [ rbp + -1232 ], r10
    mov r10, [ rbp + -544 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -544 ], r10
    mov r10, [ rbp + -552 ]
    mov r11, [ rbp + -1232 ]
    mov r10, r11 ## (ADD (TEMP _t60) (CONST 8))
    mov [ rbp + -1232 ], r11
    mov [ rbp + -552 ], r10
    mov r10, [ rbp + -552 ]
    mov r11, [ rbp + -544 ]
    addq r10, r11 ## (ADD (TEMP _t60) (CONST 8))
    mov [ rbp + -544 ], r11
    mov [ rbp + -552 ], r10
    mov r10, [ rbp + -464 ]
    mov r11, [ rbp + -552 ]
    mov r10, r11 ## (MOVE (TEMP _t2) (ADD (TEMP _t60) (CONST 8)))
    mov [ rbp + -552 ], r11
    mov [ rbp + -464 ], r10
    mov r10, [ rbp + -1360 ]
    mov r11, [ rbp + -464 ]
    mov r10, r11 ## (MOVE (TEMP _t62) (TEMP _t2))
    mov [ rbp + -464 ], r11
    mov [ rbp + -1360 ], r10
    mov r10, [ rbp + -560 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -560 ], r10
    mov r10, [ rbp + -1240 ]
    mov r11, [ rbp + -560 ]
    mov r10, r11 ## (MOVE (TEMP _t61) (CONST 8))
    mov [ rbp + -560 ], r11
    mov [ rbp + -1240 ], r10
    mov r10, [ rbp + -568 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -568 ], r10
    mov r10, [ rbp + -576 ]
    mov r11, [ rbp + -1240 ]
    mov r10, r11 ## (MUL (TEMP _t61) (CONST 0))
    mov [ rbp + -1240 ], r11
    mov [ rbp + -576 ], r10
    mov r10, [ rbp + -576 ]
    mov r11, [ rbp + -568 ]
    imulq r10, r11 ## (MUL (TEMP _t61) (CONST 0))
    mov [ rbp + -568 ], r11
    mov [ rbp + -576 ], r10
    mov r10, [ rbp + -584 ]
    mov r11, [ rbp + -1360 ]
    mov r10, r11 ## (ADD (TEMP _t62) (MUL (TEMP _t61) (CONST 0)))
    mov [ rbp + -1360 ], r11
    mov [ rbp + -584 ], r10
    mov r10, [ rbp + -584 ]
    mov r11, [ rbp + -576 ]
    addq r10, r11 ## (ADD (TEMP _t62) (MUL (TEMP _t61) (CONST 0)))
    mov [ rbp + -576 ], r11
    mov [ rbp + -584 ], r10
    mov r10, [ rbp + -1368 ]
    mov r11, [ rbp + -584 ]
    mov r10, r11 ## (MOVE (TEMP _t63) (ADD (TEMP _t62) (MUL (TEMP _t61) (CONST 0))))
    mov [ rbp + -584 ], r11
    mov [ rbp + -1368 ], r10
    mov r10, [ rbp + -1664 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -1664 ], r10
    mov r10, [ rbp + -1368 ]
    mov r11, [ rbp + -1664 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t63)) (CONST 1))
    mov [ rbp + -1664 ], r11
    mov [ rbp + -1368 ], r10
    mov r10, [ rbp + -1384 ]
    mov r11, [ rbp + -464 ]
    mov r10, r11 ## (MOVE (TEMP _t65) (TEMP _t2))
    mov [ rbp + -464 ], r11
    mov [ rbp + -1384 ], r10
    mov r10, [ rbp + -1584 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -1584 ], r10
    mov r10, [ rbp + -1376 ]
    mov r11, [ rbp + -1584 ]
    mov r10, r11 ## (MOVE (TEMP _t64) (CONST 8))
    mov [ rbp + -1584 ], r11
    mov [ rbp + -1376 ], r10
    mov r10, [ rbp + -1592 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -1592 ], r10
    mov r10, [ rbp + -1600 ]
    mov r11, [ rbp + -1376 ]
    mov r10, r11 ## (MUL (TEMP _t64) (CONST 1))
    mov [ rbp + -1376 ], r11
    mov [ rbp + -1600 ], r10
    mov r10, [ rbp + -1600 ]
    mov r11, [ rbp + -1592 ]
    imulq r10, r11 ## (MUL (TEMP _t64) (CONST 1))
    mov [ rbp + -1592 ], r11
    mov [ rbp + -1600 ], r10
    mov r10, [ rbp + -1608 ]
    mov r11, [ rbp + -1384 ]
    mov r10, r11 ## (ADD (TEMP _t65) (MUL (TEMP _t64) (CONST 1)))
    mov [ rbp + -1384 ], r11
    mov [ rbp + -1608 ], r10
    mov r10, [ rbp + -1608 ]
    mov r11, [ rbp + -1600 ]
    addq r10, r11 ## (ADD (TEMP _t65) (MUL (TEMP _t64) (CONST 1)))
    mov [ rbp + -1600 ], r11
    mov [ rbp + -1608 ], r10
    mov r10, [ rbp + -1328 ]
    mov r11, [ rbp + -1608 ]
    mov r10, r11 ## (MOVE (TEMP _t66) (ADD (TEMP _t65) (MUL (TEMP _t64) (CONST 1))))
    mov [ rbp + -1608 ], r11
    mov [ rbp + -1328 ], r10
    mov r10, [ rbp + -1616 ]
    movabsq r10, 2 ## (CONST 2)
    mov [ rbp + -1616 ], r10
    mov r10, [ rbp + -1328 ]
    mov r11, [ rbp + -1616 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t66)) (CONST 2))
    mov [ rbp + -1616 ], r11
    mov [ rbp + -1328 ], r10
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -464 ]
    mov r10, r11 ## (MOVE (TEMP arr) (TEMP _t2))
    mov [ rbp + -464 ], r11
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -1624 ]
    movabsq r10, 2 ## (CONST 2)
    mov [ rbp + -1624 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -1624 ]
    mov r10, r11 ## (MOVE (TEMP i) (CONST 2))
    mov [ rbp + -1624 ], r11
    mov [ rbp + -96 ], r10
_l3:
    mov r10, [ rbp + -1336 ]
    mov r11, [ rbp + -96 ]
    mov r10, r11 ## (MOVE (TEMP _t67) (TEMP i))
    mov [ rbp + -96 ], r11
    mov [ rbp + -1336 ], r10
    mov r10, [ rbp + -1336 ]
    mov r11, [ rbp + -144 ]
    cmpq r10, r11 ## (LT (TEMP _t67) (TEMP n))
    mov [ rbp + -144 ], r11
    mov [ rbp + -1336 ], r10
    pushq rax
    mov rax, [ rbp + -1640 ]
    setl al ## (LT (TEMP _t67) (TEMP n))
    mov [ rbp + -1640 ], rax
    popq rax
    mov r10, [ rbp + -1632 ]
    pushq rax
    mov rax, [ rbp + -1640 ]
    movzx r10, al ## (LT (TEMP _t67) (TEMP n))
    mov [ rbp + -1640 ], rax
    popq rax
    mov [ rbp + -1632 ], r10
    mov r10, [ rbp + -1632 ]
    cmpq r10, 1 ## (CJUMP (LT (TEMP _t67) (TEMP n)) _l4)
    mov [ rbp + -1632 ], r10
    je _l4 ## (CJUMP (LT (TEMP _t67) (TEMP n)) _l4)
_l5:
    mov r10, [ rbp + -840 ]
    mov r11, [ rbp + -144 ]
    mov r10, r11 ## (MOVE (TEMP _t119) (TEMP n))
    mov [ rbp + -144 ], r11
    mov [ rbp + -840 ], r10
    mov r10, [ rbp + -1648 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -1648 ], r10
    mov r10, [ rbp + -1656 ]
    mov r11, [ rbp + -840 ]
    mov r10, r11 ## (SUB (TEMP _t119) (CONST 1))
    mov [ rbp + -840 ], r11
    mov [ rbp + -1656 ], r10
    mov r10, [ rbp + -1656 ]
    mov r11, [ rbp + -1648 ]
    subq r10, r11 ## (SUB (TEMP _t119) (CONST 1))
    mov [ rbp + -1648 ], r11
    mov [ rbp + -1656 ], r10
    mov r10, [ rbp + -664 ]
    mov r11, [ rbp + -1656 ]
    mov r10, r11 ## (MOVE (TEMP _t21) (SUB (TEMP _t119) (CONST 1)))
    mov [ rbp + -1656 ], r11
    mov [ rbp + -664 ], r10
    mov r10, [ rbp + -656 ]
    mov r11, [ rbp + -88 ]
    mov r10, r11 ## (MOVE (TEMP _t20) (TEMP arr))
    mov [ rbp + -88 ], r11
    mov [ rbp + -656 ], r10
    mov r10, [ rbp + -680 ]
    mov r11, [ rbp + -656 ]
    mov r10, r11 ## (MOVE (TEMP _t120) (TEMP _t20))
    mov [ rbp + -656 ], r11
    mov [ rbp + -680 ], r10
    mov r10, [ rbp + -1424 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -1424 ], r10
    mov r10, [ rbp + -1432 ]
    mov r11, [ rbp + -680 ]
    mov r10, r11 ## (SUB (TEMP _t120) (CONST 8))
    mov [ rbp + -680 ], r11
    mov [ rbp + -1432 ], r10
    mov r10, [ rbp + -1432 ]
    mov r11, [ rbp + -1424 ]
    subq r10, r11 ## (SUB (TEMP _t120) (CONST 8))
    mov [ rbp + -1424 ], r11
    mov [ rbp + -1432 ], r10
    mov r10, [ rbp + -648 ]
    mov r11, [ rbp + -1432 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t22) (MEM (SUB (TEMP _t120) (CONST 8))))
    mov [ rbp + -1432 ], r11
    mov [ rbp + -648 ], r10
    mov r10, [ rbp + -1440 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -1440 ], r10
    mov r10, [ rbp + -696 ]
    mov r11, [ rbp + -1440 ]
    mov r10, r11 ## (MOVE (TEMP _t121) (CONST 0))
    mov [ rbp + -1440 ], r11
    mov [ rbp + -696 ], r10
    mov r10, [ rbp + -696 ]
    mov r11, [ rbp + -664 ]
    cmpq r10, r11 ## (LEQ (TEMP _t121) (TEMP _t21))
    mov [ rbp + -664 ], r11
    mov [ rbp + -696 ], r10
    pushq rax
    mov rax, [ rbp + -1464 ]
    setle al ## (LEQ (TEMP _t121) (TEMP _t21))
    mov [ rbp + -1464 ], rax
    popq rax
    mov r10, [ rbp + -1456 ]
    pushq rax
    mov rax, [ rbp + -1464 ]
    movzx r10, al ## (LEQ (TEMP _t121) (TEMP _t21))
    mov [ rbp + -1464 ], rax
    popq rax
    mov [ rbp + -1456 ], r10
    mov r10, [ rbp + -728 ]
    mov r11, [ rbp + -1456 ]
    mov r10, r11 ## (MOVE (TEMP _t123) (LEQ (TEMP _t121) (TEMP _t21)))
    mov [ rbp + -1456 ], r11
    mov [ rbp + -728 ], r10
    mov r10, [ rbp + -712 ]
    mov r11, [ rbp + -664 ]
    mov r10, r11 ## (MOVE (TEMP _t122) (TEMP _t21))
    mov [ rbp + -664 ], r11
    mov [ rbp + -712 ], r10
    mov r10, [ rbp + -712 ]
    mov r11, [ rbp + -648 ]
    cmpq r10, r11 ## (LT (TEMP _t122) (TEMP _t22))
    mov [ rbp + -648 ], r11
    mov [ rbp + -712 ], r10
    pushq rax
    mov rax, [ rbp + -1480 ]
    setl al ## (LT (TEMP _t122) (TEMP _t22))
    mov [ rbp + -1480 ], rax
    popq rax
    mov r10, [ rbp + -1472 ]
    pushq rax
    mov rax, [ rbp + -1480 ]
    movzx r10, al ## (LT (TEMP _t122) (TEMP _t22))
    mov [ rbp + -1480 ], rax
    popq rax
    mov [ rbp + -1472 ], r10
    mov r10, [ rbp + -1488 ]
    mov r11, [ rbp + -728 ]
    mov r10, r11 ## (AND (TEMP _t123) (LT (TEMP _t122) (TEMP _t22)))
    mov [ rbp + -728 ], r11
    mov [ rbp + -1488 ], r10
    mov r10, [ rbp + -1488 ]
    mov r11, [ rbp + -1472 ]
    andq r10, r11 ## (AND (TEMP _t123) (LT (TEMP _t122) (TEMP _t22)))
    mov [ rbp + -1472 ], r11
    mov [ rbp + -1488 ], r10
    mov r10, [ rbp + -1488 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t123) (LT (TEMP _t122) (TEMP _t22))) _l15)
    mov [ rbp + -1488 ], r10
    je _l15 ## (CJUMP (AND (TEMP _t123) (LT (TEMP _t122) (TEMP _t22))) _l15)
_l16:
    callq _xi_out_of_bounds ## (CALL_STMT _t124 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -744 ]
    mov r10, rax ## (CALL_STMT _t124 (NAME _xi_out_of_bounds))
    mov [ rbp + -744 ], r10
_l15:
    mov r10, [ rbp + -776 ]
    mov r11, [ rbp + -656 ]
    mov r10, r11 ## (MOVE (TEMP _t126) (TEMP _t20))
    mov [ rbp + -656 ], r11
    mov [ rbp + -776 ], r10
    mov r10, [ rbp + -760 ]
    mov r11, [ rbp + -664 ]
    mov r10, r11 ## (MOVE (TEMP _t125) (TEMP _t21))
    mov [ rbp + -664 ], r11
    mov [ rbp + -760 ], r10
    mov r10, [ rbp + -1496 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -1496 ], r10
    mov r10, [ rbp + -1504 ]
    mov r11, [ rbp + -760 ]
    mov r10, r11 ## (MUL (TEMP _t125) (CONST 8))
    mov [ rbp + -760 ], r11
    mov [ rbp + -1504 ], r10
    mov r10, [ rbp + -1504 ]
    mov r11, [ rbp + -1496 ]
    imulq r10, r11 ## (MUL (TEMP _t125) (CONST 8))
    mov [ rbp + -1496 ], r11
    mov [ rbp + -1504 ], r10
    mov r10, [ rbp + -1248 ]
    mov r11, [ rbp + -776 ]
    mov r10, r11 ## (ADD (TEMP _t126) (MUL (TEMP _t125) (CONST 8)))
    mov [ rbp + -776 ], r11
    mov [ rbp + -1248 ], r10
    mov r10, [ rbp + -1248 ]
    mov r11, [ rbp + -1504 ]
    addq r10, r11 ## (ADD (TEMP _t126) (MUL (TEMP _t125) (CONST 8)))
    mov [ rbp + -1504 ], r11
    mov [ rbp + -1248 ], r10
    mov r10, [ rbp + -360 ]
    mov r11, [ rbp + -1248 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t19) (MEM (ADD (TEMP _t126) (MUL (TEMP _t125) (CONST 8)))))
    mov [ rbp + -1248 ], r11
    mov [ rbp + -360 ], r10
    mov r10, [ rbp + -360 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t19))
    mov [ rbp + -360 ], r10
    jmp end__IclimbStairs_ii ## (RETURN ())
_l0:
    mov r10, [ rbp + -1256 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -1256 ], r10
    mov r10, [ rbp + -448 ]
    mov r11, [ rbp + -1256 ]
    mov r10, r11 ## (MOVE (TEMP _t0) (CONST 0))
    mov [ rbp + -1256 ], r11
    mov [ rbp + -448 ], r10
    mov r10, [ rbp + -448 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t0))
    mov [ rbp + -448 ], r10
    jmp end__IclimbStairs_ii ## (RETURN ())
_l4:
    mov r10, [ rbp + -456 ]
    mov r11, [ rbp + -88 ]
    mov r10, r11 ## (MOVE (TEMP _t3) (TEMP arr))
    mov [ rbp + -88 ], r11
    mov [ rbp + -456 ], r10
    mov r10, [ rbp + -1264 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -1264 ], r10
    mov r10, [ rbp + -1352 ]
    mov r11, [ rbp + -1264 ]
    mov r10, r11 ## (MOVE (TEMP _t69) (CONST 8))
    mov [ rbp + -1264 ], r11
    mov [ rbp + -1352 ], r10
    mov r10, [ rbp + -1272 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -1272 ], r10
    mov r10, [ rbp + -1344 ]
    mov r11, [ rbp + -1272 ]
    mov r10, r11 ## (MOVE (TEMP _t68) (CONST 1))
    mov [ rbp + -1272 ], r11
    mov [ rbp + -1344 ], r10
    mov r10, [ rbp + -1280 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -1280 ], r10
    mov r10, [ rbp + -1288 ]
    mov r11, [ rbp + -1344 ]
    mov r10, r11 ## (ADD (TEMP _t68) (CONST 1))
    mov [ rbp + -1344 ], r11
    mov [ rbp + -1288 ], r10
    mov r10, [ rbp + -1288 ]
    mov r11, [ rbp + -1280 ]
    addq r10, r11 ## (ADD (TEMP _t68) (CONST 1))
    mov [ rbp + -1280 ], r11
    mov [ rbp + -1288 ], r10
    mov r10, [ rbp + -1296 ]
    mov r11, [ rbp + -1352 ]
    mov r10, r11 ## (MUL (TEMP _t69) (ADD (TEMP _t68) (CONST 1)))
    mov [ rbp + -1352 ], r11
    mov [ rbp + -1296 ], r10
    mov r10, [ rbp + -1296 ]
    mov r11, [ rbp + -1288 ]
    imulq r10, r11 ## (MUL (TEMP _t69) (ADD (TEMP _t68) (CONST 1)))
    mov [ rbp + -1288 ], r11
    mov [ rbp + -1296 ], r10
    mov r10, [ rbp + -1072 ]
    mov r11, [ rbp + -1296 ]
    mov r10, r11 ## (MOVE (TEMP _t70) (MUL (TEMP _t69) (ADD (TEMP _t68) (CONST 1))))
    mov [ rbp + -1296 ], r11
    mov [ rbp + -1072 ], r10
    mov r10, [ rbp + -1072 ]
    mov rdi, r10 ## (CALL_STMT _t71 (NAME _xi_alloc) (TEMP _t70))
    mov [ rbp + -1072 ], r10
    callq _xi_alloc ## (CALL_STMT _t71 (NAME _xi_alloc) (TEMP _t70))
    mov r10, [ rbp + -1080 ]
    mov r10, rax ## (CALL_STMT _t71 (NAME _xi_alloc) (TEMP _t70))
    mov [ rbp + -1080 ], r10
    mov r10, [ rbp + -368 ]
    mov r11, [ rbp + -1080 ]
    mov r10, r11 ## (MOVE (TEMP _t15) (TEMP _t71))
    mov [ rbp + -1080 ], r11
    mov [ rbp + -368 ], r10
    mov r10, [ rbp + -1088 ]
    mov r11, [ rbp + -368 ]
    mov r10, r11 ## (MOVE (TEMP _t72) (TEMP _t15))
    mov [ rbp + -368 ], r11
    mov [ rbp + -1088 ], r10
    mov r10, [ rbp + -1304 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -1304 ], r10
    mov r10, [ rbp + -1088 ]
    mov r11, [ rbp + -1304 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t72)) (CONST 1))
    mov [ rbp + -1304 ], r11
    mov [ rbp + -1088 ], r10
    mov r10, [ rbp + -1200 ]
    mov r11, [ rbp + -368 ]
    mov r10, r11 ## (MOVE (TEMP _t73) (TEMP _t15))
    mov [ rbp + -368 ], r11
    mov [ rbp + -1200 ], r10
    mov r10, [ rbp + -1312 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -1312 ], r10
    mov r10, [ rbp + -1320 ]
    mov r11, [ rbp + -1200 ]
    mov r10, r11 ## (ADD (TEMP _t73) (CONST 8))
    mov [ rbp + -1200 ], r11
    mov [ rbp + -1320 ], r10
    mov r10, [ rbp + -1320 ]
    mov r11, [ rbp + -1312 ]
    addq r10, r11 ## (ADD (TEMP _t73) (CONST 8))
    mov [ rbp + -1312 ], r11
    mov [ rbp + -1320 ], r10
    mov r10, [ rbp + -376 ]
    mov r11, [ rbp + -1320 ]
    mov r10, r11 ## (MOVE (TEMP _t16) (ADD (TEMP _t73) (CONST 8)))
    mov [ rbp + -1320 ], r11
    mov [ rbp + -376 ], r10
    mov r10, [ rbp + -1216 ]
    mov r11, [ rbp + -376 ]
    mov r10, r11 ## (MOVE (TEMP _t75) (TEMP _t16))
    mov [ rbp + -376 ], r11
    mov [ rbp + -1216 ], r10
    mov r10, [ rbp + -1096 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -1096 ], r10
    mov r10, [ rbp + -1208 ]
    mov r11, [ rbp + -1096 ]
    mov r10, r11 ## (MOVE (TEMP _t74) (CONST 8))
    mov [ rbp + -1096 ], r11
    mov [ rbp + -1208 ], r10
    mov r10, [ rbp + -1104 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -1104 ], r10
    mov r10, [ rbp + -1112 ]
    mov r11, [ rbp + -1208 ]
    mov r10, r11 ## (MUL (TEMP _t74) (CONST 0))
    mov [ rbp + -1208 ], r11
    mov [ rbp + -1112 ], r10
    mov r10, [ rbp + -1112 ]
    mov r11, [ rbp + -1104 ]
    imulq r10, r11 ## (MUL (TEMP _t74) (CONST 0))
    mov [ rbp + -1104 ], r11
    mov [ rbp + -1112 ], r10
    mov r10, [ rbp + -1120 ]
    mov r11, [ rbp + -1216 ]
    mov r10, r11 ## (ADD (TEMP _t75) (MUL (TEMP _t74) (CONST 0)))
    mov [ rbp + -1216 ], r11
    mov [ rbp + -1120 ], r10
    mov r10, [ rbp + -1120 ]
    mov r11, [ rbp + -1112 ]
    addq r10, r11 ## (ADD (TEMP _t75) (MUL (TEMP _t74) (CONST 0)))
    mov [ rbp + -1112 ], r11
    mov [ rbp + -1120 ], r10
    mov r10, [ rbp + -1408 ]
    mov r11, [ rbp + -1120 ]
    mov r10, r11 ## (MOVE (TEMP _t93) (ADD (TEMP _t75) (MUL (TEMP _t74) (CONST 0))))
    mov [ rbp + -1120 ], r11
    mov [ rbp + -1408 ], r10
    mov r10, [ rbp + -1224 ]
    mov r11, [ rbp + -96 ]
    mov r10, r11 ## (MOVE (TEMP _t76) (TEMP i))
    mov [ rbp + -96 ], r11
    mov [ rbp + -1224 ], r10
    mov r10, [ rbp + -1128 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -1128 ], r10
    mov r10, [ rbp + -1136 ]
    mov r11, [ rbp + -1224 ]
    mov r10, r11 ## (SUB (TEMP _t76) (CONST 1))
    mov [ rbp + -1224 ], r11
    mov [ rbp + -1136 ], r10
    mov r10, [ rbp + -1136 ]
    mov r11, [ rbp + -1128 ]
    subq r10, r11 ## (SUB (TEMP _t76) (CONST 1))
    mov [ rbp + -1128 ], r11
    mov [ rbp + -1136 ], r10
    mov r10, [ rbp + -432 ]
    mov r11, [ rbp + -1136 ]
    mov r10, r11 ## (MOVE (TEMP _t10) (SUB (TEMP _t76) (CONST 1)))
    mov [ rbp + -1136 ], r11
    mov [ rbp + -432 ], r10
    mov r10, [ rbp + -504 ]
    mov r11, [ rbp + -88 ]
    mov r10, r11 ## (MOVE (TEMP _t9) (TEMP arr))
    mov [ rbp + -88 ], r11
    mov [ rbp + -504 ], r10
    mov r10, [ rbp + -1176 ]
    mov r11, [ rbp + -504 ]
    mov r10, r11 ## (MOVE (TEMP _t77) (TEMP _t9))
    mov [ rbp + -504 ], r11
    mov [ rbp + -1176 ], r10
    mov r10, [ rbp + -1144 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -1144 ], r10
    mov r10, [ rbp + -1152 ]
    mov r11, [ rbp + -1176 ]
    mov r10, r11 ## (SUB (TEMP _t77) (CONST 8))
    mov [ rbp + -1176 ], r11
    mov [ rbp + -1152 ], r10
    mov r10, [ rbp + -1152 ]
    mov r11, [ rbp + -1144 ]
    subq r10, r11 ## (SUB (TEMP _t77) (CONST 8))
    mov [ rbp + -1144 ], r11
    mov [ rbp + -1152 ], r10
    mov r10, [ rbp + -400 ]
    mov r11, [ rbp + -1152 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t11) (MEM (SUB (TEMP _t77) (CONST 8))))
    mov [ rbp + -1152 ], r11
    mov [ rbp + -400 ], r10
    mov r10, [ rbp + -1160 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -1160 ], r10
    mov r10, [ rbp + -1184 ]
    mov r11, [ rbp + -1160 ]
    mov r10, r11 ## (MOVE (TEMP _t78) (CONST 0))
    mov [ rbp + -1160 ], r11
    mov [ rbp + -1184 ], r10
    mov r10, [ rbp + -1184 ]
    mov r11, [ rbp + -432 ]
    cmpq r10, r11 ## (LEQ (TEMP _t78) (TEMP _t10))
    mov [ rbp + -432 ], r11
    mov [ rbp + -1184 ], r10
    pushq rax
    mov rax, [ rbp + -8 ]
    setle al ## (LEQ (TEMP _t78) (TEMP _t10))
    mov [ rbp + -8 ], rax
    popq rax
    mov r10, [ rbp + -1168 ]
    pushq rax
    mov rax, [ rbp + -8 ]
    movzx r10, al ## (LEQ (TEMP _t78) (TEMP _t10))
    mov [ rbp + -8 ], rax
    popq rax
    mov [ rbp + -1168 ], r10
    mov r10, [ rbp + -1552 ]
    mov r11, [ rbp + -1168 ]
    mov r10, r11 ## (MOVE (TEMP _t80) (LEQ (TEMP _t78) (TEMP _t10)))
    mov [ rbp + -1168 ], r11
    mov [ rbp + -1552 ], r10
    mov r10, [ rbp + -1192 ]
    mov r11, [ rbp + -432 ]
    mov r10, r11 ## (MOVE (TEMP _t79) (TEMP _t10))
    mov [ rbp + -432 ], r11
    mov [ rbp + -1192 ], r10
    mov r10, [ rbp + -1192 ]
    mov r11, [ rbp + -400 ]
    cmpq r10, r11 ## (LT (TEMP _t79) (TEMP _t11))
    mov [ rbp + -400 ], r11
    mov [ rbp + -1192 ], r10
    pushq rax
    mov rax, [ rbp + -24 ]
    setl al ## (LT (TEMP _t79) (TEMP _t11))
    mov [ rbp + -24 ], rax
    popq rax
    mov r10, [ rbp + -16 ]
    pushq rax
    mov rax, [ rbp + -24 ]
    movzx r10, al ## (LT (TEMP _t79) (TEMP _t11))
    mov [ rbp + -24 ], rax
    popq rax
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -1552 ]
    mov r10, r11 ## (AND (TEMP _t80) (LT (TEMP _t79) (TEMP _t11)))
    mov [ rbp + -1552 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -16 ]
    andq r10, r11 ## (AND (TEMP _t80) (LT (TEMP _t79) (TEMP _t11)))
    mov [ rbp + -16 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -32 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t80) (LT (TEMP _t79) (TEMP _t11))) _l6)
    mov [ rbp + -32 ], r10
    je _l6 ## (CJUMP (AND (TEMP _t80) (LT (TEMP _t79) (TEMP _t11))) _l6)
_l7:
    callq _xi_out_of_bounds ## (CALL_STMT _t81 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -1560 ]
    mov r10, rax ## (CALL_STMT _t81 (NAME _xi_out_of_bounds))
    mov [ rbp + -1560 ], r10
_l6:
    mov r10, [ rbp + -1576 ]
    mov r11, [ rbp + -504 ]
    mov r10, r11 ## (MOVE (TEMP _t83) (TEMP _t9))
    mov [ rbp + -504 ], r11
    mov [ rbp + -1576 ], r10
    mov r10, [ rbp + -1568 ]
    mov r11, [ rbp + -432 ]
    mov r10, r11 ## (MOVE (TEMP _t82) (TEMP _t10))
    mov [ rbp + -432 ], r11
    mov [ rbp + -1568 ], r10
    mov r10, [ rbp + -40 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -1568 ]
    mov r10, r11 ## (MUL (TEMP _t82) (CONST 8))
    mov [ rbp + -1568 ], r11
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -40 ]
    imulq r10, r11 ## (MUL (TEMP _t82) (CONST 8))
    mov [ rbp + -40 ], r11
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -1576 ]
    mov r10, r11 ## (ADD (TEMP _t83) (MUL (TEMP _t82) (CONST 8)))
    mov [ rbp + -1576 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -48 ]
    addq r10, r11 ## (ADD (TEMP _t83) (MUL (TEMP _t82) (CONST 8)))
    mov [ rbp + -48 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -1400 ]
    mov r11, [ rbp + -56 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t92) (MEM (ADD (TEMP _t83) (MUL (TEMP _t82) (CONST 8)))))
    mov [ rbp + -56 ], r11
    mov [ rbp + -1400 ], r10
    mov r10, [ rbp + -1688 ]
    mov r11, [ rbp + -96 ]
    mov r10, r11 ## (MOVE (TEMP _t84) (TEMP i))
    mov [ rbp + -96 ], r11
    mov [ rbp + -1688 ], r10
    mov r10, [ rbp + -64 ]
    movabsq r10, 2 ## (CONST 2)
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -72 ]
    mov r11, [ rbp + -1688 ]
    mov r10, r11 ## (SUB (TEMP _t84) (CONST 2))
    mov [ rbp + -1688 ], r11
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -72 ]
    mov r11, [ rbp + -64 ]
    subq r10, r11 ## (SUB (TEMP _t84) (CONST 2))
    mov [ rbp + -64 ], r11
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -416 ]
    mov r11, [ rbp + -72 ]
    mov r10, r11 ## (MOVE (TEMP _t13) (SUB (TEMP _t84) (CONST 2)))
    mov [ rbp + -72 ], r11
    mov [ rbp + -416 ], r10
    mov r10, [ rbp + -408 ]
    mov r11, [ rbp + -88 ]
    mov r10, r11 ## (MOVE (TEMP _t12) (TEMP arr))
    mov [ rbp + -88 ], r11
    mov [ rbp + -408 ], r10
    mov r10, [ rbp + -1696 ]
    mov r11, [ rbp + -408 ]
    mov r10, r11 ## (MOVE (TEMP _t85) (TEMP _t12))
    mov [ rbp + -408 ], r11
    mov [ rbp + -1696 ], r10
    mov r10, [ rbp + -80 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -328 ]
    mov r11, [ rbp + -1696 ]
    mov r10, r11 ## (SUB (TEMP _t85) (CONST 8))
    mov [ rbp + -1696 ], r11
    mov [ rbp + -328 ], r10
    mov r10, [ rbp + -328 ]
    mov r11, [ rbp + -80 ]
    subq r10, r11 ## (SUB (TEMP _t85) (CONST 8))
    mov [ rbp + -80 ], r11
    mov [ rbp + -328 ], r10
    mov r10, [ rbp + -424 ]
    mov r11, [ rbp + -328 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t14) (MEM (SUB (TEMP _t85) (CONST 8))))
    mov [ rbp + -328 ], r11
    mov [ rbp + -424 ], r10
    mov r10, [ rbp + -336 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -336 ], r10
    mov r10, [ rbp + -1704 ]
    mov r11, [ rbp + -336 ]
    mov r10, r11 ## (MOVE (TEMP _t86) (CONST 0))
    mov [ rbp + -336 ], r11
    mov [ rbp + -1704 ], r10
    mov r10, [ rbp + -1704 ]
    mov r11, [ rbp + -416 ]
    cmpq r10, r11 ## (LEQ (TEMP _t86) (TEMP _t13))
    mov [ rbp + -416 ], r11
    mov [ rbp + -1704 ], r10
    pushq rax
    mov rax, [ rbp + -352 ]
    setle al ## (LEQ (TEMP _t86) (TEMP _t13))
    mov [ rbp + -352 ], rax
    popq rax
    mov r10, [ rbp + -344 ]
    pushq rax
    mov rax, [ rbp + -352 ]
    movzx r10, al ## (LEQ (TEMP _t86) (TEMP _t13))
    mov [ rbp + -352 ], rax
    popq rax
    mov [ rbp + -344 ], r10
    mov r10, [ rbp + -1672 ]
    mov r11, [ rbp + -344 ]
    mov r10, r11 ## (MOVE (TEMP _t88) (LEQ (TEMP _t86) (TEMP _t13)))
    mov [ rbp + -344 ], r11
    mov [ rbp + -1672 ], r10
    mov r10, [ rbp + -1712 ]
    mov r11, [ rbp + -416 ]
    mov r10, r11 ## (MOVE (TEMP _t87) (TEMP _t13))
    mov [ rbp + -416 ], r11
    mov [ rbp + -1712 ], r10
    mov r10, [ rbp + -1712 ]
    mov r11, [ rbp + -424 ]
    cmpq r10, r11 ## (LT (TEMP _t87) (TEMP _t14))
    mov [ rbp + -424 ], r11
    mov [ rbp + -1712 ], r10
    pushq rax
    mov rax, [ rbp + -288 ]
    setl al ## (LT (TEMP _t87) (TEMP _t14))
    mov [ rbp + -288 ], rax
    popq rax
    mov r10, [ rbp + -280 ]
    pushq rax
    mov rax, [ rbp + -288 ]
    movzx r10, al ## (LT (TEMP _t87) (TEMP _t14))
    mov [ rbp + -288 ], rax
    popq rax
    mov [ rbp + -280 ], r10
    mov r10, [ rbp + -296 ]
    mov r11, [ rbp + -1672 ]
    mov r10, r11 ## (AND (TEMP _t88) (LT (TEMP _t87) (TEMP _t14)))
    mov [ rbp + -1672 ], r11
    mov [ rbp + -296 ], r10
    mov r10, [ rbp + -296 ]
    mov r11, [ rbp + -280 ]
    andq r10, r11 ## (AND (TEMP _t88) (LT (TEMP _t87) (TEMP _t14)))
    mov [ rbp + -280 ], r11
    mov [ rbp + -296 ], r10
    mov r10, [ rbp + -296 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t88) (LT (TEMP _t87) (TEMP _t14))) _l8)
    mov [ rbp + -296 ], r10
    je _l8 ## (CJUMP (AND (TEMP _t88) (LT (TEMP _t87) (TEMP _t14))) _l8)
_l9:
    callq _xi_out_of_bounds ## (CALL_STMT _t89 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -1680 ]
    mov r10, rax ## (CALL_STMT _t89 (NAME _xi_out_of_bounds))
    mov [ rbp + -1680 ], r10
_l8:
    mov r10, [ rbp + -1392 ]
    mov r11, [ rbp + -408 ]
    mov r10, r11 ## (MOVE (TEMP _t91) (TEMP _t12))
    mov [ rbp + -408 ], r11
    mov [ rbp + -1392 ], r10
    mov r10, [ rbp + -1448 ]
    mov r11, [ rbp + -416 ]
    mov r10, r11 ## (MOVE (TEMP _t90) (TEMP _t13))
    mov [ rbp + -416 ], r11
    mov [ rbp + -1448 ], r10
    mov r10, [ rbp + -304 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -304 ], r10
    mov r10, [ rbp + -312 ]
    mov r11, [ rbp + -1448 ]
    mov r10, r11 ## (MUL (TEMP _t90) (CONST 8))
    mov [ rbp + -1448 ], r11
    mov [ rbp + -312 ], r10
    mov r10, [ rbp + -312 ]
    mov r11, [ rbp + -304 ]
    imulq r10, r11 ## (MUL (TEMP _t90) (CONST 8))
    mov [ rbp + -304 ], r11
    mov [ rbp + -312 ], r10
    mov r10, [ rbp + -320 ]
    mov r11, [ rbp + -1392 ]
    mov r10, r11 ## (ADD (TEMP _t91) (MUL (TEMP _t90) (CONST 8)))
    mov [ rbp + -1392 ], r11
    mov [ rbp + -320 ], r10
    mov r10, [ rbp + -320 ]
    mov r11, [ rbp + -312 ]
    addq r10, r11 ## (ADD (TEMP _t91) (MUL (TEMP _t90) (CONST 8)))
    mov [ rbp + -312 ], r11
    mov [ rbp + -320 ], r10
    mov r10, [ rbp + -240 ]
    mov r11, [ rbp + -1400 ]
    mov r10, r11 ## (ADD (TEMP _t92) (MEM (ADD (TEMP _t91) (MUL (TEMP _t90) (CONST 8)))))
    mov [ rbp + -1400 ], r11
    mov [ rbp + -240 ], r10
    mov r10, [ rbp + -240 ]
    mov r11, [ rbp + -320 ]
    addq r10, [ r11 ] ## (ADD (TEMP _t92) (MEM (ADD (TEMP _t91) (MUL (TEMP _t90) (CONST 8)))))
    mov [ rbp + -320 ], r11
    mov [ rbp + -240 ], r10
    mov r10, [ rbp + -1408 ]
    mov r11, [ rbp + -240 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t93)) (ADD (TEMP _t92) (MEM (ADD (TEMP _t91) (MUL (TEMP _t90) (CONST 8))))))
    mov [ rbp + -240 ], r11
    mov [ rbp + -1408 ], r10
    mov r10, [ rbp + -472 ]
    mov r11, [ rbp + -376 ]
    mov r10, r11 ## (MOVE (TEMP _t5) (TEMP _t16))
    mov [ rbp + -376 ], r11
    mov [ rbp + -472 ], r10
    mov r10, [ rbp + -1416 ]
    mov r11, [ rbp + -456 ]
    mov r10, r11 ## (MOVE (TEMP _t94) (TEMP _t3))
    mov [ rbp + -456 ], r11
    mov [ rbp + -1416 ], r10
    mov r10, [ rbp + -248 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -248 ], r10
    mov r10, [ rbp + -256 ]
    mov r11, [ rbp + -1416 ]
    mov r10, r11 ## (SUB (TEMP _t94) (CONST 8))
    mov [ rbp + -1416 ], r11
    mov [ rbp + -256 ], r10
    mov r10, [ rbp + -256 ]
    mov r11, [ rbp + -248 ]
    subq r10, r11 ## (SUB (TEMP _t94) (CONST 8))
    mov [ rbp + -248 ], r11
    mov [ rbp + -256 ], r10
    mov r10, [ rbp + -480 ]
    mov r11, [ rbp + -256 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t4) (MEM (SUB (TEMP _t94) (CONST 8))))
    mov [ rbp + -256 ], r11
    mov [ rbp + -480 ], r10
    mov r10, [ rbp + -1520 ]
    mov r11, [ rbp + -472 ]
    mov r10, r11 ## (MOVE (TEMP _t95) (TEMP _t5))
    mov [ rbp + -472 ], r11
    mov [ rbp + -1520 ], r10
    mov r10, [ rbp + -264 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -264 ], r10
    mov r10, [ rbp + -272 ]
    mov r11, [ rbp + -1520 ]
    mov r10, r11 ## (SUB (TEMP _t95) (CONST 8))
    mov [ rbp + -1520 ], r11
    mov [ rbp + -272 ], r10
    mov r10, [ rbp + -272 ]
    mov r11, [ rbp + -264 ]
    subq r10, r11 ## (SUB (TEMP _t95) (CONST 8))
    mov [ rbp + -264 ], r11
    mov [ rbp + -272 ], r10
    mov r10, [ rbp + -496 ]
    mov r11, [ rbp + -272 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t6) (MEM (SUB (TEMP _t95) (CONST 8))))
    mov [ rbp + -272 ], r11
    mov [ rbp + -496 ], r10
    mov r10, [ rbp + -1528 ]
    mov r11, [ rbp + -480 ]
    mov r10, r11 ## (MOVE (TEMP _t96) (TEMP _t4))
    mov [ rbp + -480 ], r11
    mov [ rbp + -1528 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -1528 ]
    mov r10, r11 ## (ADD (TEMP _t96) (TEMP _t6))
    mov [ rbp + -1528 ], r11
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -496 ]
    addq r10, r11 ## (ADD (TEMP _t96) (TEMP _t6))
    mov [ rbp + -496 ], r11
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -488 ]
    mov r11, [ rbp + -200 ]
    mov r10, r11 ## (MOVE (TEMP _t7) (ADD (TEMP _t96) (TEMP _t6)))
    mov [ rbp + -200 ], r11
    mov [ rbp + -488 ], r10
    mov r10, [ rbp + -208 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -1544 ]
    mov r11, [ rbp + -208 ]
    mov r10, r11 ## (MOVE (TEMP _t98) (CONST 8))
    mov [ rbp + -208 ], r11
    mov [ rbp + -1544 ], r10
    mov r10, [ rbp + -1536 ]
    mov r11, [ rbp + -488 ]
    mov r10, r11 ## (MOVE (TEMP _t97) (TEMP _t7))
    mov [ rbp + -488 ], r11
    mov [ rbp + -1536 ], r10
    mov r10, [ rbp + -216 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -216 ], r10
    mov r10, [ rbp + -224 ]
    mov r11, [ rbp + -1536 ]
    mov r10, r11 ## (ADD (TEMP _t97) (CONST 1))
    mov [ rbp + -1536 ], r11
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -224 ]
    mov r11, [ rbp + -216 ]
    addq r10, r11 ## (ADD (TEMP _t97) (CONST 1))
    mov [ rbp + -216 ], r11
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -232 ]
    mov r11, [ rbp + -1544 ]
    mov r10, r11 ## (MUL (TEMP _t98) (ADD (TEMP _t97) (CONST 1)))
    mov [ rbp + -1544 ], r11
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -232 ]
    mov r11, [ rbp + -224 ]
    imulq r10, r11 ## (MUL (TEMP _t98) (ADD (TEMP _t97) (CONST 1)))
    mov [ rbp + -224 ], r11
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -1512 ]
    mov r11, [ rbp + -232 ]
    mov r10, r11 ## (MOVE (TEMP _t99) (MUL (TEMP _t98) (ADD (TEMP _t97) (CONST 1))))
    mov [ rbp + -232 ], r11
    mov [ rbp + -1512 ], r10
    mov r10, [ rbp + -1512 ]
    mov rdi, r10 ## (CALL_STMT _t100 (NAME _xi_alloc) (TEMP _t99))
    mov [ rbp + -1512 ], r10
    callq _xi_alloc ## (CALL_STMT _t100 (NAME _xi_alloc) (TEMP _t99))
    mov r10, [ rbp + -112 ]
    mov r10, rax ## (CALL_STMT _t100 (NAME _xi_alloc) (TEMP _t99))
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -512 ]
    mov r11, [ rbp + -112 ]
    mov r10, r11 ## (MOVE (TEMP _t8) (TEMP _t100))
    mov [ rbp + -112 ], r11
    mov [ rbp + -512 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -512 ]
    mov r10, r11 ## (MOVE (TEMP _t101) (TEMP _t8))
    mov [ rbp + -512 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -488 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t101)) (TEMP _t7))
    mov [ rbp + -488 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -512 ]
    mov r10, r11 ## (MOVE (TEMP _t102) (TEMP _t8))
    mov [ rbp + -512 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -104 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -152 ]
    mov r10, r11 ## (ADD (TEMP _t102) (CONST 8))
    mov [ rbp + -152 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -104 ]
    addq r10, r11 ## (ADD (TEMP _t102) (CONST 8))
    mov [ rbp + -104 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -512 ]
    mov r11, [ rbp + -120 ]
    mov r10, r11 ## (MOVE (TEMP _t8) (ADD (TEMP _t102) (CONST 8)))
    mov [ rbp + -120 ], r11
    mov [ rbp + -512 ], r10
    mov r10, [ rbp + -136 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -384 ]
    mov r11, [ rbp + -136 ]
    mov r10, r11 ## (MOVE (TEMP _t17) (CONST 0))
    mov [ rbp + -136 ], r11
    mov [ rbp + -384 ], r10
    mov r10, [ rbp + -160 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -392 ]
    mov r11, [ rbp + -160 ]
    mov r10, r11 ## (MOVE (TEMP _t18) (CONST 0))
    mov [ rbp + -160 ], r11
    mov [ rbp + -392 ], r10
_l10:
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -384 ]
    mov r10, r11 ## (MOVE (TEMP _t103) (TEMP _t17))
    mov [ rbp + -384 ], r11
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -480 ]
    cmpq r10, r11 ## (LT (TEMP _t103) (TEMP _t4))
    mov [ rbp + -480 ], r11
    mov [ rbp + -168 ], r10
    pushq rax
    mov rax, [ rbp + -184 ]
    setl al ## (LT (TEMP _t103) (TEMP _t4))
    mov [ rbp + -184 ], rax
    popq rax
    mov r10, [ rbp + -176 ]
    pushq rax
    mov rax, [ rbp + -184 ]
    movzx r10, al ## (LT (TEMP _t103) (TEMP _t4))
    mov [ rbp + -184 ], rax
    popq rax
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -176 ]
    cmpq r10, 1 ## (CJUMP (LT (TEMP _t103) (TEMP _t4)) _l11)
    mov [ rbp + -176 ], r10
    je _l11 ## (CJUMP (LT (TEMP _t103) (TEMP _t4)) _l11)
_l12:
    mov r10, [ rbp + -904 ]
    mov r11, [ rbp + -392 ]
    mov r10, r11 ## (MOVE (TEMP _t110) (TEMP _t18))
    mov [ rbp + -392 ], r11
    mov [ rbp + -904 ], r10
    mov r10, [ rbp + -904 ]
    mov r11, [ rbp + -496 ]
    cmpq r10, r11 ## (LT (TEMP _t110) (TEMP _t6))
    mov [ rbp + -496 ], r11
    mov [ rbp + -904 ], r10
    pushq rax
    mov rax, [ rbp + -1016 ]
    setl al ## (LT (TEMP _t110) (TEMP _t6))
    mov [ rbp + -1016 ], rax
    popq rax
    mov r10, [ rbp + -1000 ]
    pushq rax
    mov rax, [ rbp + -1016 ]
    movzx r10, al ## (LT (TEMP _t110) (TEMP _t6))
    mov [ rbp + -1016 ], rax
    popq rax
    mov [ rbp + -1000 ], r10
    mov r10, [ rbp + -1000 ]
    cmpq r10, 1 ## (CJUMP (LT (TEMP _t110) (TEMP _t6)) _l13)
    mov [ rbp + -1000 ], r10
    je _l13 ## (CJUMP (LT (TEMP _t110) (TEMP _t6)) _l13)
_l14:
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -512 ]
    mov r10, r11 ## (MOVE (TEMP arr) (TEMP _t8))
    mov [ rbp + -512 ], r11
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -824 ]
    mov r11, [ rbp + -96 ]
    mov r10, r11 ## (MOVE (TEMP _t118) (TEMP i))
    mov [ rbp + -96 ], r11
    mov [ rbp + -824 ], r10
    mov r10, [ rbp + -1032 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -1032 ], r10
    mov r10, [ rbp + -1048 ]
    mov r11, [ rbp + -824 ]
    mov r10, r11 ## (ADD (TEMP _t118) (CONST 1))
    mov [ rbp + -824 ], r11
    mov [ rbp + -1048 ], r10
    mov r10, [ rbp + -1048 ]
    mov r11, [ rbp + -1032 ]
    addq r10, r11 ## (ADD (TEMP _t118) (CONST 1))
    mov [ rbp + -1032 ], r11
    mov [ rbp + -1048 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -1048 ]
    mov r10, r11 ## (MOVE (TEMP i) (ADD (TEMP _t118) (CONST 1)))
    mov [ rbp + -1048 ], r11
    mov [ rbp + -96 ], r10
    jmp _l3 ## (JUMP (NAME _l3))
_l11:
    mov r10, [ rbp + -1008 ]
    mov r11, [ rbp + -512 ]
    mov r10, r11 ## (MOVE (TEMP _t105) (TEMP _t8))
    mov [ rbp + -512 ], r11
    mov [ rbp + -1008 ], r10
    mov r10, [ rbp + -192 ]
    mov r11, [ rbp + -384 ]
    mov r10, r11 ## (MOVE (TEMP _t104) (TEMP _t17))
    mov [ rbp + -384 ], r11
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -896 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -896 ], r10
    mov r10, [ rbp + -912 ]
    mov r11, [ rbp + -192 ]
    mov r10, r11 ## (MUL (TEMP _t104) (CONST 8))
    mov [ rbp + -192 ], r11
    mov [ rbp + -912 ], r10
    mov r10, [ rbp + -912 ]
    mov r11, [ rbp + -896 ]
    imulq r10, r11 ## (MUL (TEMP _t104) (CONST 8))
    mov [ rbp + -896 ], r11
    mov [ rbp + -912 ], r10
    mov r10, [ rbp + -920 ]
    mov r11, [ rbp + -1008 ]
    mov r10, r11 ## (ADD (TEMP _t105) (MUL (TEMP _t104) (CONST 8)))
    mov [ rbp + -1008 ], r11
    mov [ rbp + -920 ], r10
    mov r10, [ rbp + -920 ]
    mov r11, [ rbp + -912 ]
    addq r10, r11 ## (ADD (TEMP _t105) (MUL (TEMP _t104) (CONST 8)))
    mov [ rbp + -912 ], r11
    mov [ rbp + -920 ], r10
    mov r10, [ rbp + -1056 ]
    mov r11, [ rbp + -920 ]
    mov r10, r11 ## (MOVE (TEMP _t108) (ADD (TEMP _t105) (MUL (TEMP _t104) (CONST 8))))
    mov [ rbp + -920 ], r11
    mov [ rbp + -1056 ], r10
    mov r10, [ rbp + -1040 ]
    mov r11, [ rbp + -456 ]
    mov r10, r11 ## (MOVE (TEMP _t107) (TEMP _t3))
    mov [ rbp + -456 ], r11
    mov [ rbp + -1040 ], r10
    mov r10, [ rbp + -1024 ]
    mov r11, [ rbp + -384 ]
    mov r10, r11 ## (MOVE (TEMP _t106) (TEMP _t17))
    mov [ rbp + -384 ], r11
    mov [ rbp + -1024 ], r10
    mov r10, [ rbp + -936 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -936 ], r10
    mov r10, [ rbp + -952 ]
    mov r11, [ rbp + -1024 ]
    mov r10, r11 ## (MUL (TEMP _t106) (CONST 8))
    mov [ rbp + -1024 ], r11
    mov [ rbp + -952 ], r10
    mov r10, [ rbp + -952 ]
    mov r11, [ rbp + -936 ]
    imulq r10, r11 ## (MUL (TEMP _t106) (CONST 8))
    mov [ rbp + -936 ], r11
    mov [ rbp + -952 ], r10
    mov r10, [ rbp + -968 ]
    mov r11, [ rbp + -1040 ]
    mov r10, r11 ## (ADD (TEMP _t107) (MUL (TEMP _t106) (CONST 8)))
    mov [ rbp + -1040 ], r11
    mov [ rbp + -968 ], r10
    mov r10, [ rbp + -968 ]
    mov r11, [ rbp + -952 ]
    addq r10, r11 ## (ADD (TEMP _t107) (MUL (TEMP _t106) (CONST 8)))
    mov [ rbp + -952 ], r11
    mov [ rbp + -968 ], r10
    mov r10, [ rbp + -984 ]
    mov r11, [ rbp + -968 ]
    mov r10, [ r11 ] ## (MOVE (MEM (TEMP _t108)) (MEM (ADD (TEMP _t107) (MUL (TEMP _t106) (CONST 8)))))
    mov [ rbp + -968 ], r11
    mov [ rbp + -984 ], r10
    mov r10, [ rbp + -1056 ]
    mov r11, [ rbp + -984 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t108)) (MEM (ADD (TEMP _t107) (MUL (TEMP _t106) (CONST 8)))))
    mov [ rbp + -984 ], r11
    mov [ rbp + -1056 ], r10
    mov r10, [ rbp + -1064 ]
    mov r11, [ rbp + -384 ]
    mov r10, r11 ## (MOVE (TEMP _t109) (TEMP _t17))
    mov [ rbp + -384 ], r11
    mov [ rbp + -1064 ], r10
    mov r10, [ rbp + -792 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -792 ], r10
    mov r10, [ rbp + -808 ]
    mov r11, [ rbp + -1064 ]
    mov r10, r11 ## (ADD (TEMP _t109) (CONST 1))
    mov [ rbp + -1064 ], r11
    mov [ rbp + -808 ], r10
    mov r10, [ rbp + -808 ]
    mov r11, [ rbp + -792 ]
    addq r10, r11 ## (ADD (TEMP _t109) (CONST 1))
    mov [ rbp + -792 ], r11
    mov [ rbp + -808 ], r10
    mov r10, [ rbp + -384 ]
    mov r11, [ rbp + -808 ]
    mov r10, r11 ## (MOVE (TEMP _t17) (ADD (TEMP _t109) (CONST 1)))
    mov [ rbp + -808 ], r11
    mov [ rbp + -384 ], r10
    jmp _l10 ## (JUMP (NAME _l10))
_l13:
    mov r10, [ rbp + -960 ]
    mov r11, [ rbp + -512 ]
    mov r10, r11 ## (MOVE (TEMP _t113) (TEMP _t8))
    mov [ rbp + -512 ], r11
    mov [ rbp + -960 ], r10
    mov r10, [ rbp + -928 ]
    mov r11, [ rbp + -392 ]
    mov r10, r11 ## (MOVE (TEMP _t111) (TEMP _t18))
    mov [ rbp + -392 ], r11
    mov [ rbp + -928 ], r10
    mov r10, [ rbp + -832 ]
    mov r11, [ rbp + -928 ]
    mov r10, r11 ## (ADD (TEMP _t111) (TEMP _t4))
    mov [ rbp + -928 ], r11
    mov [ rbp + -832 ], r10
    mov r10, [ rbp + -832 ]
    mov r11, [ rbp + -480 ]
    addq r10, r11 ## (ADD (TEMP _t111) (TEMP _t4))
    mov [ rbp + -480 ], r11
    mov [ rbp + -832 ], r10
    mov r10, [ rbp + -944 ]
    mov r11, [ rbp + -832 ]
    mov r10, r11 ## (MOVE (TEMP _t112) (ADD (TEMP _t111) (TEMP _t4)))
    mov [ rbp + -832 ], r11
    mov [ rbp + -944 ], r10
    mov r10, [ rbp + -672 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -672 ], r10
    mov r10, [ rbp + -688 ]
    mov r11, [ rbp + -944 ]
    mov r10, r11 ## (MUL (TEMP _t112) (CONST 8))
    mov [ rbp + -944 ], r11
    mov [ rbp + -688 ], r10
    mov r10, [ rbp + -688 ]
    mov r11, [ rbp + -672 ]
    imulq r10, r11 ## (MUL (TEMP _t112) (CONST 8))
    mov [ rbp + -672 ], r11
    mov [ rbp + -688 ], r10
    mov r10, [ rbp + -704 ]
    mov r11, [ rbp + -960 ]
    mov r10, r11 ## (ADD (TEMP _t113) (MUL (TEMP _t112) (CONST 8)))
    mov [ rbp + -960 ], r11
    mov [ rbp + -704 ], r10
    mov r10, [ rbp + -704 ]
    mov r11, [ rbp + -688 ]
    addq r10, r11 ## (ADD (TEMP _t113) (MUL (TEMP _t112) (CONST 8)))
    mov [ rbp + -688 ], r11
    mov [ rbp + -704 ], r10
    mov r10, [ rbp + -800 ]
    mov r11, [ rbp + -704 ]
    mov r10, r11 ## (MOVE (TEMP _t116) (ADD (TEMP _t113) (MUL (TEMP _t112) (CONST 8))))
    mov [ rbp + -704 ], r11
    mov [ rbp + -800 ], r10
    mov r10, [ rbp + -992 ]
    mov r11, [ rbp + -472 ]
    mov r10, r11 ## (MOVE (TEMP _t115) (TEMP _t5))
    mov [ rbp + -472 ], r11
    mov [ rbp + -992 ], r10
    mov r10, [ rbp + -976 ]
    mov r11, [ rbp + -392 ]
    mov r10, r11 ## (MOVE (TEMP _t114) (TEMP _t18))
    mov [ rbp + -392 ], r11
    mov [ rbp + -976 ], r10
    mov r10, [ rbp + -720 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -720 ], r10
    mov r10, [ rbp + -736 ]
    mov r11, [ rbp + -976 ]
    mov r10, r11 ## (MUL (TEMP _t114) (CONST 8))
    mov [ rbp + -976 ], r11
    mov [ rbp + -736 ], r10
    mov r10, [ rbp + -736 ]
    mov r11, [ rbp + -720 ]
    imulq r10, r11 ## (MUL (TEMP _t114) (CONST 8))
    mov [ rbp + -720 ], r11
    mov [ rbp + -736 ], r10
    mov r10, [ rbp + -752 ]
    mov r11, [ rbp + -992 ]
    mov r10, r11 ## (ADD (TEMP _t115) (MUL (TEMP _t114) (CONST 8)))
    mov [ rbp + -992 ], r11
    mov [ rbp + -752 ], r10
    mov r10, [ rbp + -752 ]
    mov r11, [ rbp + -736 ]
    addq r10, r11 ## (ADD (TEMP _t115) (MUL (TEMP _t114) (CONST 8)))
    mov [ rbp + -736 ], r11
    mov [ rbp + -752 ], r10
    mov r10, [ rbp + -768 ]
    mov r11, [ rbp + -752 ]
    mov r10, [ r11 ] ## (MOVE (MEM (TEMP _t116)) (MEM (ADD (TEMP _t115) (MUL (TEMP _t114) (CONST 8)))))
    mov [ rbp + -752 ], r11
    mov [ rbp + -768 ], r10
    mov r10, [ rbp + -800 ]
    mov r11, [ rbp + -768 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t116)) (MEM (ADD (TEMP _t115) (MUL (TEMP _t114) (CONST 8)))))
    mov [ rbp + -768 ], r11
    mov [ rbp + -800 ], r10
    mov r10, [ rbp + -816 ]
    mov r11, [ rbp + -392 ]
    mov r10, r11 ## (MOVE (TEMP _t117) (TEMP _t18))
    mov [ rbp + -392 ], r11
    mov [ rbp + -816 ], r10
    mov r10, [ rbp + -784 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -784 ], r10
    mov r10, [ rbp + -640 ]
    mov r11, [ rbp + -816 ]
    mov r10, r11 ## (ADD (TEMP _t117) (CONST 1))
    mov [ rbp + -816 ], r11
    mov [ rbp + -640 ], r10
    mov r10, [ rbp + -640 ]
    mov r11, [ rbp + -784 ]
    addq r10, r11 ## (ADD (TEMP _t117) (CONST 1))
    mov [ rbp + -784 ], r11
    mov [ rbp + -640 ], r10
    mov r10, [ rbp + -392 ]
    mov r11, [ rbp + -640 ]
    mov r10, r11 ## (MOVE (TEMP _t18) (ADD (TEMP _t117) (CONST 1)))
    mov [ rbp + -640 ], r11
    mov [ rbp + -392 ], r10
    jmp _l10 ## (JUMP (NAME _l10))
end__IclimbStairs_ii:
    addq rsp, 1712
    mov rsp, rbp
    popq rbp
    retq
