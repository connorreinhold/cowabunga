.intel_syntax noprefix
.globl _Imain_paai
.align 4
_Imain_paai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 1208
_l11:
    mov r10, [ rbp + -608 ]
    mov r10, rdi ## (MOVE (TEMP arg) (TEMP _ARG0))
    mov [ rbp + -608 ], r10
_l3:
    callq _Ieof_b ## (CALL_STMT _t11 (NAME _Ieof_b))
    mov r10, [ rbp + -992 ]
    mov r10, rax ## (CALL_STMT _t11 (NAME _Ieof_b))
    mov [ rbp + -992 ], r10
    mov r10, [ rbp + -992 ]
    cmpq r10, 1 ## (CJUMP (TEMP _t11) _l5)
    mov [ rbp + -992 ], r10
    je _l5 ## (CJUMP (TEMP _t11) _l5)
_l4:
    mov r10, [ rbp + -768 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -768 ], r10
    mov r10, [ rbp + -1016 ]
    mov r11, [ rbp + -768 ]
    mov r10, r11 ## (MOVE (TEMP _t13) (CONST 8))
    mov [ rbp + -768 ], r11
    mov [ rbp + -1016 ], r10
    mov r10, [ rbp + -776 ]
    movabsq r10, 7 ## (CONST 7)
    mov [ rbp + -776 ], r10
    mov r10, [ rbp + -1008 ]
    mov r11, [ rbp + -776 ]
    mov r10, r11 ## (MOVE (TEMP _t12) (CONST 7))
    mov [ rbp + -776 ], r11
    mov [ rbp + -1008 ], r10
    mov r10, [ rbp + -784 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -784 ], r10
    mov r10, [ rbp + -1064 ]
    mov r11, [ rbp + -1008 ]
    mov r10, r11 ## (ADD (TEMP _t12) (CONST 1))
    mov [ rbp + -1008 ], r11
    mov [ rbp + -1064 ], r10
    mov r10, [ rbp + -1064 ]
    mov r11, [ rbp + -784 ]
    addq r10, r11 ## (ADD (TEMP _t12) (CONST 1))
    mov [ rbp + -784 ], r11
    mov [ rbp + -1064 ], r10
    mov r10, [ rbp + -1072 ]
    mov r11, [ rbp + -1016 ]
    mov r10, r11 ## (MUL (TEMP _t13) (ADD (TEMP _t12) (CONST 1)))
    mov [ rbp + -1016 ], r11
    mov [ rbp + -1072 ], r10
    mov r10, [ rbp + -1072 ]
    mov r11, [ rbp + -1064 ]
    imulq r10, r11 ## (MUL (TEMP _t13) (ADD (TEMP _t12) (CONST 1)))
    mov [ rbp + -1064 ], r11
    mov [ rbp + -1072 ], r10
    mov r10, [ rbp + -1024 ]
    mov r11, [ rbp + -1072 ]
    mov r10, r11 ## (MOVE (TEMP _t14) (MUL (TEMP _t13) (ADD (TEMP _t12) (CONST 1))))
    mov [ rbp + -1072 ], r11
    mov [ rbp + -1024 ], r10
    mov r10, [ rbp + -1024 ]
    mov rdi, r10 ## (CALL_STMT _t15 (NAME _xi_alloc) (TEMP _t14))
    mov [ rbp + -1024 ], r10
    callq _xi_alloc ## (CALL_STMT _t15 (NAME _xi_alloc) (TEMP _t14))
    mov r10, [ rbp + -960 ]
    mov r10, rax ## (CALL_STMT _t15 (NAME _xi_alloc) (TEMP _t14))
    mov [ rbp + -960 ], r10
    mov r10, [ rbp + -1168 ]
    mov r11, [ rbp + -960 ]
    mov r10, r11 ## (MOVE (TEMP _t2) (TEMP _t15))
    mov [ rbp + -960 ], r11
    mov [ rbp + -1168 ], r10
    mov r10, [ rbp + -968 ]
    mov r11, [ rbp + -1168 ]
    mov r10, r11 ## (MOVE (TEMP _t16) (TEMP _t2))
    mov [ rbp + -1168 ], r11
    mov [ rbp + -968 ], r10
    mov r10, [ rbp + -1080 ]
    movabsq r10, 7 ## (CONST 7)
    mov [ rbp + -1080 ], r10
    mov r10, [ rbp + -968 ]
    mov r11, [ rbp + -1080 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t16)) (CONST 7))
    mov [ rbp + -1080 ], r11
    mov [ rbp + -968 ], r10
    mov r10, [ rbp + -976 ]
    mov r11, [ rbp + -1168 ]
    mov r10, r11 ## (MOVE (TEMP _t17) (TEMP _t2))
    mov [ rbp + -1168 ], r11
    mov [ rbp + -976 ], r10
    mov r10, [ rbp + -1088 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -1088 ], r10
    mov r10, [ rbp + -1152 ]
    mov r11, [ rbp + -976 ]
    mov r10, r11 ## (ADD (TEMP _t17) (CONST 8))
    mov [ rbp + -976 ], r11
    mov [ rbp + -1152 ], r10
    mov r10, [ rbp + -1152 ]
    mov r11, [ rbp + -1088 ]
    addq r10, r11 ## (ADD (TEMP _t17) (CONST 8))
    mov [ rbp + -1088 ], r11
    mov [ rbp + -1152 ], r10
    mov r10, [ rbp + -1160 ]
    mov r11, [ rbp + -1152 ]
    mov r10, r11 ## (MOVE (TEMP _t3) (ADD (TEMP _t17) (CONST 8)))
    mov [ rbp + -1152 ], r11
    mov [ rbp + -1160 ], r10
    mov r10, [ rbp + -952 ]
    mov r11, [ rbp + -1160 ]
    mov r10, r11 ## (MOVE (TEMP _t19) (TEMP _t3))
    mov [ rbp + -1160 ], r11
    mov [ rbp + -952 ], r10
    mov r10, [ rbp + -1176 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -1176 ], r10
    mov r10, [ rbp + -984 ]
    mov r11, [ rbp + -1176 ]
    mov r10, r11 ## (MOVE (TEMP _t18) (CONST 8))
    mov [ rbp + -1176 ], r11
    mov [ rbp + -984 ], r10
    mov r10, [ rbp + -1184 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -1184 ], r10
    mov r10, [ rbp + -1200 ]
    mov r11, [ rbp + -984 ]
    mov r10, r11 ## (MUL (TEMP _t18) (CONST 0))
    mov [ rbp + -984 ], r11
    mov [ rbp + -1200 ], r10
    mov r10, [ rbp + -1200 ]
    mov r11, [ rbp + -1184 ]
    imulq r10, r11 ## (MUL (TEMP _t18) (CONST 0))
    mov [ rbp + -1184 ], r11
    mov [ rbp + -1200 ], r10
    mov r10, [ rbp + -1128 ]
    mov r11, [ rbp + -952 ]
    mov r10, r11 ## (ADD (TEMP _t19) (MUL (TEMP _t18) (CONST 0)))
    mov [ rbp + -952 ], r11
    mov [ rbp + -1128 ], r10
    mov r10, [ rbp + -1128 ]
    mov r11, [ rbp + -1200 ]
    addq r10, r11 ## (ADD (TEMP _t19) (MUL (TEMP _t18) (CONST 0)))
    mov [ rbp + -1200 ], r11
    mov [ rbp + -1128 ], r10
    mov r10, [ rbp + -336 ]
    mov r11, [ rbp + -1128 ]
    mov r10, r11 ## (MOVE (TEMP _t20) (ADD (TEMP _t19) (MUL (TEMP _t18) (CONST 0))))
    mov [ rbp + -1128 ], r11
    mov [ rbp + -336 ], r10
    mov r10, [ rbp + -1136 ]
    movabsq r10, 78 ## (CONST 78)
    mov [ rbp + -1136 ], r10
    mov r10, [ rbp + -336 ]
    mov r11, [ rbp + -1136 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t20)) (CONST 78))
    mov [ rbp + -1136 ], r11
    mov [ rbp + -336 ], r10
    mov r10, [ rbp + -304 ]
    mov r11, [ rbp + -1160 ]
    mov r10, r11 ## (MOVE (TEMP _t22) (TEMP _t3))
    mov [ rbp + -1160 ], r11
    mov [ rbp + -304 ], r10
    mov r10, [ rbp + -944 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -944 ], r10
    mov r10, [ rbp + -344 ]
    mov r11, [ rbp + -944 ]
    mov r10, r11 ## (MOVE (TEMP _t21) (CONST 8))
    mov [ rbp + -944 ], r11
    mov [ rbp + -344 ], r10
    mov r10, [ rbp + -912 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -912 ], r10
    mov r10, [ rbp + -920 ]
    mov r11, [ rbp + -344 ]
    mov r10, r11 ## (MUL (TEMP _t21) (CONST 1))
    mov [ rbp + -344 ], r11
    mov [ rbp + -920 ], r10
    mov r10, [ rbp + -920 ]
    mov r11, [ rbp + -912 ]
    imulq r10, r11 ## (MUL (TEMP _t21) (CONST 1))
    mov [ rbp + -912 ], r11
    mov [ rbp + -920 ], r10
    mov r10, [ rbp + -928 ]
    mov r11, [ rbp + -304 ]
    mov r10, r11 ## (ADD (TEMP _t22) (MUL (TEMP _t21) (CONST 1)))
    mov [ rbp + -304 ], r11
    mov [ rbp + -928 ], r10
    mov r10, [ rbp + -928 ]
    mov r11, [ rbp + -920 ]
    addq r10, r11 ## (ADD (TEMP _t22) (MUL (TEMP _t21) (CONST 1)))
    mov [ rbp + -920 ], r11
    mov [ rbp + -928 ], r10
    mov r10, [ rbp + -312 ]
    mov r11, [ rbp + -928 ]
    mov r10, r11 ## (MOVE (TEMP _t23) (ADD (TEMP _t22) (MUL (TEMP _t21) (CONST 1))))
    mov [ rbp + -928 ], r11
    mov [ rbp + -312 ], r10
    mov r10, [ rbp + -936 ]
    movabsq r10, 117 ## (CONST 117)
    mov [ rbp + -936 ], r10
    mov r10, [ rbp + -312 ]
    mov r11, [ rbp + -936 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t23)) (CONST 117))
    mov [ rbp + -936 ], r11
    mov [ rbp + -312 ], r10
    mov r10, [ rbp + -328 ]
    mov r11, [ rbp + -1160 ]
    mov r10, r11 ## (MOVE (TEMP _t25) (TEMP _t3))
    mov [ rbp + -1160 ], r11
    mov [ rbp + -328 ], r10
    mov r10, [ rbp + -1032 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -1032 ], r10
    mov r10, [ rbp + -320 ]
    mov r11, [ rbp + -1032 ]
    mov r10, r11 ## (MOVE (TEMP _t24) (CONST 8))
    mov [ rbp + -1032 ], r11
    mov [ rbp + -320 ], r10
    mov r10, [ rbp + -1040 ]
    movabsq r10, 2 ## (CONST 2)
    mov [ rbp + -1040 ], r10
    mov r10, [ rbp + -1048 ]
    mov r11, [ rbp + -320 ]
    mov r10, r11 ## (MUL (TEMP _t24) (CONST 2))
    mov [ rbp + -320 ], r11
    mov [ rbp + -1048 ], r10
    mov r10, [ rbp + -1048 ]
    mov r11, [ rbp + -1040 ]
    imulq r10, r11 ## (MUL (TEMP _t24) (CONST 2))
    mov [ rbp + -1040 ], r11
    mov [ rbp + -1048 ], r10
    mov r10, [ rbp + -1056 ]
    mov r11, [ rbp + -328 ]
    mov r10, r11 ## (ADD (TEMP _t25) (MUL (TEMP _t24) (CONST 2)))
    mov [ rbp + -328 ], r11
    mov [ rbp + -1056 ], r10
    mov r10, [ rbp + -1056 ]
    mov r11, [ rbp + -1048 ]
    addq r10, r11 ## (ADD (TEMP _t25) (MUL (TEMP _t24) (CONST 2)))
    mov [ rbp + -1048 ], r11
    mov [ rbp + -1056 ], r10
    mov r10, [ rbp + -272 ]
    mov r11, [ rbp + -1056 ]
    mov r10, r11 ## (MOVE (TEMP _t26) (ADD (TEMP _t25) (MUL (TEMP _t24) (CONST 2))))
    mov [ rbp + -1056 ], r11
    mov [ rbp + -272 ], r10
    mov r10, [ rbp + -1000 ]
    movabsq r10, 109 ## (CONST 109)
    mov [ rbp + -1000 ], r10
    mov r10, [ rbp + -272 ]
    mov r11, [ rbp + -1000 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t26)) (CONST 109))
    mov [ rbp + -1000 ], r11
    mov [ rbp + -272 ], r10
    mov r10, [ rbp + -288 ]
    mov r11, [ rbp + -1160 ]
    mov r10, r11 ## (MOVE (TEMP _t28) (TEMP _t3))
    mov [ rbp + -1160 ], r11
    mov [ rbp + -288 ], r10
    mov r10, [ rbp + -728 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -728 ], r10
    mov r10, [ rbp + -280 ]
    mov r11, [ rbp + -728 ]
    mov r10, r11 ## (MOVE (TEMP _t27) (CONST 8))
    mov [ rbp + -728 ], r11
    mov [ rbp + -280 ], r10
    mov r10, [ rbp + -736 ]
    movabsq r10, 3 ## (CONST 3)
    mov [ rbp + -736 ], r10
    mov r10, [ rbp + -744 ]
    mov r11, [ rbp + -280 ]
    mov r10, r11 ## (MUL (TEMP _t27) (CONST 3))
    mov [ rbp + -280 ], r11
    mov [ rbp + -744 ], r10
    mov r10, [ rbp + -744 ]
    mov r11, [ rbp + -736 ]
    imulq r10, r11 ## (MUL (TEMP _t27) (CONST 3))
    mov [ rbp + -736 ], r11
    mov [ rbp + -744 ], r10
    mov r10, [ rbp + -752 ]
    mov r11, [ rbp + -288 ]
    mov r10, r11 ## (ADD (TEMP _t28) (MUL (TEMP _t27) (CONST 3)))
    mov [ rbp + -288 ], r11
    mov [ rbp + -752 ], r10
    mov r10, [ rbp + -752 ]
    mov r11, [ rbp + -744 ]
    addq r10, r11 ## (ADD (TEMP _t28) (MUL (TEMP _t27) (CONST 3)))
    mov [ rbp + -744 ], r11
    mov [ rbp + -752 ], r10
    mov r10, [ rbp + -296 ]
    mov r11, [ rbp + -752 ]
    mov r10, r11 ## (MOVE (TEMP _t29) (ADD (TEMP _t28) (MUL (TEMP _t27) (CONST 3))))
    mov [ rbp + -752 ], r11
    mov [ rbp + -296 ], r10
    mov r10, [ rbp + -760 ]
    movabsq r10, 98 ## (CONST 98)
    mov [ rbp + -760 ], r10
    mov r10, [ rbp + -296 ]
    mov r11, [ rbp + -760 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t29)) (CONST 98))
    mov [ rbp + -760 ], r11
    mov [ rbp + -296 ], r10
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -1160 ]
    mov r10, r11 ## (MOVE (TEMP _t31) (TEMP _t3))
    mov [ rbp + -1160 ], r11
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -576 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -576 ], r10
    mov r10, [ rbp + -160 ]
    mov r11, [ rbp + -576 ]
    mov r10, r11 ## (MOVE (TEMP _t30) (CONST 8))
    mov [ rbp + -576 ], r11
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -584 ]
    movabsq r10, 4 ## (CONST 4)
    mov [ rbp + -584 ], r10
    mov r10, [ rbp + -592 ]
    mov r11, [ rbp + -160 ]
    mov r10, r11 ## (MUL (TEMP _t30) (CONST 4))
    mov [ rbp + -160 ], r11
    mov [ rbp + -592 ], r10
    mov r10, [ rbp + -592 ]
    mov r11, [ rbp + -584 ]
    imulq r10, r11 ## (MUL (TEMP _t30) (CONST 4))
    mov [ rbp + -584 ], r11
    mov [ rbp + -592 ], r10
    mov r10, [ rbp + -600 ]
    mov r11, [ rbp + -168 ]
    mov r10, r11 ## (ADD (TEMP _t31) (MUL (TEMP _t30) (CONST 4)))
    mov [ rbp + -168 ], r11
    mov [ rbp + -600 ], r10
    mov r10, [ rbp + -600 ]
    mov r11, [ rbp + -592 ]
    addq r10, r11 ## (ADD (TEMP _t31) (MUL (TEMP _t30) (CONST 4)))
    mov [ rbp + -592 ], r11
    mov [ rbp + -600 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -600 ]
    mov r10, r11 ## (MOVE (TEMP _t32) (ADD (TEMP _t31) (MUL (TEMP _t30) (CONST 4))))
    mov [ rbp + -600 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -616 ]
    movabsq r10, 101 ## (CONST 101)
    mov [ rbp + -616 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -616 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t32)) (CONST 101))
    mov [ rbp + -616 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -1160 ]
    mov r10, r11 ## (MOVE (TEMP _t34) (TEMP _t3))
    mov [ rbp + -1160 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -528 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -528 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -528 ]
    mov r10, r11 ## (MOVE (TEMP _t33) (CONST 8))
    mov [ rbp + -528 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -536 ]
    movabsq r10, 5 ## (CONST 5)
    mov [ rbp + -536 ], r10
    mov r10, [ rbp + -544 ]
    mov r11, [ rbp + -128 ]
    mov r10, r11 ## (MUL (TEMP _t33) (CONST 5))
    mov [ rbp + -128 ], r11
    mov [ rbp + -544 ], r10
    mov r10, [ rbp + -544 ]
    mov r11, [ rbp + -536 ]
    imulq r10, r11 ## (MUL (TEMP _t33) (CONST 5))
    mov [ rbp + -536 ], r11
    mov [ rbp + -544 ], r10
    mov r10, [ rbp + -552 ]
    mov r11, [ rbp + -136 ]
    mov r10, r11 ## (ADD (TEMP _t34) (MUL (TEMP _t33) (CONST 5)))
    mov [ rbp + -136 ], r11
    mov [ rbp + -552 ], r10
    mov r10, [ rbp + -552 ]
    mov r11, [ rbp + -544 ]
    addq r10, r11 ## (ADD (TEMP _t34) (MUL (TEMP _t33) (CONST 5)))
    mov [ rbp + -544 ], r11
    mov [ rbp + -552 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -552 ]
    mov r10, r11 ## (MOVE (TEMP _t35) (ADD (TEMP _t34) (MUL (TEMP _t33) (CONST 5))))
    mov [ rbp + -552 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -560 ]
    movabsq r10, 114 ## (CONST 114)
    mov [ rbp + -560 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -560 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t35)) (CONST 114))
    mov [ rbp + -560 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -104 ]
    mov r11, [ rbp + -1160 ]
    mov r10, r11 ## (MOVE (TEMP _t37) (TEMP _t3))
    mov [ rbp + -1160 ], r11
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -568 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -568 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -568 ]
    mov r10, r11 ## (MOVE (TEMP _t36) (CONST 8))
    mov [ rbp + -568 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -424 ]
    movabsq r10, 6 ## (CONST 6)
    mov [ rbp + -424 ], r10
    mov r10, [ rbp + -432 ]
    mov r11, [ rbp + -152 ]
    mov r10, r11 ## (MUL (TEMP _t36) (CONST 6))
    mov [ rbp + -152 ], r11
    mov [ rbp + -432 ], r10
    mov r10, [ rbp + -432 ]
    mov r11, [ rbp + -424 ]
    imulq r10, r11 ## (MUL (TEMP _t36) (CONST 6))
    mov [ rbp + -424 ], r11
    mov [ rbp + -432 ], r10
    mov r10, [ rbp + -440 ]
    mov r11, [ rbp + -104 ]
    mov r10, r11 ## (ADD (TEMP _t37) (MUL (TEMP _t36) (CONST 6)))
    mov [ rbp + -104 ], r11
    mov [ rbp + -440 ], r10
    mov r10, [ rbp + -440 ]
    mov r11, [ rbp + -432 ]
    addq r10, r11 ## (ADD (TEMP _t37) (MUL (TEMP _t36) (CONST 6)))
    mov [ rbp + -432 ], r11
    mov [ rbp + -440 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -440 ]
    mov r10, r11 ## (MOVE (TEMP _t38) (ADD (TEMP _t37) (MUL (TEMP _t36) (CONST 6))))
    mov [ rbp + -440 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -448 ]
    movabsq r10, 63 ## (CONST 63)
    mov [ rbp + -448 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -448 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t38)) (CONST 63))
    mov [ rbp + -448 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -1160 ]
    mov r10, r11 ## (MOVE (TEMP _t39) (TEMP _t3))
    mov [ rbp + -1160 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -120 ]
    mov rdi, r10 ## (CALL_STMT _t40 (NAME _Iprint_pai) (TEMP _t39))
    mov [ rbp + -120 ], r10
    callq _Iprint_pai ## (CALL_STMT _t40 (NAME _Iprint_pai) (TEMP _t39))
    mov r10, [ rbp + -672 ]
    mov r10, rax ## (CALL_STMT _t40 (NAME _Iprint_pai) (TEMP _t39))
    mov [ rbp + -672 ], r10
    callq _Ireadln_ai ## (CALL_STMT _t41 (NAME _Ireadln_ai))
    mov r10, [ rbp + -680 ]
    mov r10, rax ## (CALL_STMT _t41 (NAME _Ireadln_ai))
    mov [ rbp + -680 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -680 ]
    mov r10, r11 ## (MOVE (TEMP input) (TEMP _t41))
    mov [ rbp + -680 ], r11
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -688 ]
    mov r11, [ rbp + -96 ]
    mov r10, r11 ## (MOVE (TEMP _t42) (TEMP input))
    mov [ rbp + -96 ], r11
    mov [ rbp + -688 ], r10
    mov r10, [ rbp + -688 ]
    mov rdi, r10 ## (CALL_STMT _t43 (NAME _IparseInt_t2ibai) (TEMP _t42))
    mov [ rbp + -688 ], r10
    callq _IparseInt_t2ibai ## (CALL_STMT _t43 (NAME _IparseInt_t2ibai) (TEMP _t42))
    mov r10, [ rbp + -696 ]
    mov r10, rax ## (CALL_STMT _t43 (NAME _IparseInt_t2ibai) (TEMP _t42))
    mov [ rbp + -696 ], r10
    mov r10, [ rbp + -840 ]
    mov r10, rax ## (MOVE (TEMP num) (TEMP _RET0))
    mov [ rbp + -840 ], r10
    mov r10, [ rbp + -1144 ]
    mov r10, rdx ## (MOVE (TEMP ok) (TEMP _RET1))
    mov [ rbp + -1144 ], r10
    mov r10, [ rbp + -1144 ]
    cmpq r10, 1 ## (CJUMP (TEMP ok) _l6)
    mov [ rbp + -1144 ], r10
    je _l6 ## (CJUMP (TEMP ok) _l6)
_l7:
_l8:
    jmp _l3 ## (JUMP (NAME _l3))
_l6:
    mov r10, [ rbp + -640 ]
    mov r11, [ rbp + -840 ]
    mov r10, r11 ## (MOVE (TEMP _t44) (TEMP num))
    mov [ rbp + -840 ], r11
    mov [ rbp + -640 ], r10
    mov r10, [ rbp + -640 ]
    mov rdi, r10 ## (CALL_STMT _t45 (NAME _Ifactorial_ii) (TEMP _t44))
    mov [ rbp + -640 ], r10
    callq _Ifactorial_ii ## (CALL_STMT _t45 (NAME _Ifactorial_ii) (TEMP _t44))
    mov r10, [ rbp + -648 ]
    mov r10, rax ## (CALL_STMT _t45 (NAME _Ifactorial_ii) (TEMP _t44))
    mov [ rbp + -648 ], r10
    mov r10, [ rbp + -352 ]
    mov r11, [ rbp + -648 ]
    mov r10, r11 ## (MOVE (TEMP val) (TEMP _t45))
    mov [ rbp + -648 ], r11
    mov [ rbp + -352 ], r10
    mov r10, [ rbp + -656 ]
    mov r11, [ rbp + -840 ]
    mov r10, r11 ## (MOVE (TEMP _t46) (TEMP num))
    mov [ rbp + -840 ], r11
    mov [ rbp + -656 ], r10
    mov r10, [ rbp + -656 ]
    mov rdi, r10 ## (CALL_STMT _t47 (NAME _IunparseInt_aii) (TEMP _t46))
    mov [ rbp + -656 ], r10
    callq _IunparseInt_aii ## (CALL_STMT _t47 (NAME _IunparseInt_aii) (TEMP _t46))
    mov r10, [ rbp + -664 ]
    mov r10, rax ## (CALL_STMT _t47 (NAME _IunparseInt_aii) (TEMP _t46))
    mov [ rbp + -664 ], r10
    mov r10, [ rbp + -624 ]
    mov r11, [ rbp + -664 ]
    mov r10, r11 ## (MOVE (TEMP _t48) (TEMP _t47))
    mov [ rbp + -664 ], r11
    mov [ rbp + -624 ], r10
    mov r10, [ rbp + -624 ]
    mov rdi, r10 ## (CALL_STMT _t49 (NAME _Iprint_pai) (TEMP _t48))
    mov [ rbp + -624 ], r10
    callq _Iprint_pai ## (CALL_STMT _t49 (NAME _Iprint_pai) (TEMP _t48))
    mov r10, [ rbp + -632 ]
    mov r10, rax ## (CALL_STMT _t49 (NAME _Iprint_pai) (TEMP _t48))
    mov [ rbp + -632 ], r10
    mov r10, [ rbp + -368 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -368 ], r10
    mov r10, [ rbp + -496 ]
    mov r11, [ rbp + -368 ]
    mov r10, r11 ## (MOVE (TEMP _t51) (CONST 8))
    mov [ rbp + -368 ], r11
    mov [ rbp + -496 ], r10
    mov r10, [ rbp + -376 ]
    movabsq r10, 5 ## (CONST 5)
    mov [ rbp + -376 ], r10
    mov r10, [ rbp + -360 ]
    mov r11, [ rbp + -376 ]
    mov r10, r11 ## (MOVE (TEMP _t50) (CONST 5))
    mov [ rbp + -376 ], r11
    mov [ rbp + -360 ], r10
    mov r10, [ rbp + -384 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -384 ], r10
    mov r10, [ rbp + -392 ]
    mov r11, [ rbp + -360 ]
    mov r10, r11 ## (ADD (TEMP _t50) (CONST 1))
    mov [ rbp + -360 ], r11
    mov [ rbp + -392 ], r10
    mov r10, [ rbp + -392 ]
    mov r11, [ rbp + -384 ]
    addq r10, r11 ## (ADD (TEMP _t50) (CONST 1))
    mov [ rbp + -384 ], r11
    mov [ rbp + -392 ], r10
    mov r10, [ rbp + -400 ]
    mov r11, [ rbp + -496 ]
    mov r10, r11 ## (MUL (TEMP _t51) (ADD (TEMP _t50) (CONST 1)))
    mov [ rbp + -496 ], r11
    mov [ rbp + -400 ], r10
    mov r10, [ rbp + -400 ]
    mov r11, [ rbp + -392 ]
    imulq r10, r11 ## (MUL (TEMP _t51) (ADD (TEMP _t50) (CONST 1)))
    mov [ rbp + -392 ], r11
    mov [ rbp + -400 ], r10
    mov r10, [ rbp + -504 ]
    mov r11, [ rbp + -400 ]
    mov r10, r11 ## (MOVE (TEMP _t52) (MUL (TEMP _t51) (ADD (TEMP _t50) (CONST 1))))
    mov [ rbp + -400 ], r11
    mov [ rbp + -504 ], r10
    mov r10, [ rbp + -504 ]
    mov rdi, r10 ## (CALL_STMT _t53 (NAME _xi_alloc) (TEMP _t52))
    mov [ rbp + -504 ], r10
    callq _xi_alloc ## (CALL_STMT _t53 (NAME _xi_alloc) (TEMP _t52))
    mov r10, [ rbp + -512 ]
    mov r10, rax ## (CALL_STMT _t53 (NAME _xi_alloc) (TEMP _t52))
    mov [ rbp + -512 ], r10
    mov r10, [ rbp + -1208 ]
    mov r11, [ rbp + -512 ]
    mov r10, r11 ## (MOVE (TEMP _t4) (TEMP _t53))
    mov [ rbp + -512 ], r11
    mov [ rbp + -1208 ], r10
    mov r10, [ rbp + -520 ]
    mov r11, [ rbp + -1208 ]
    mov r10, r11 ## (MOVE (TEMP _t54) (TEMP _t4))
    mov [ rbp + -1208 ], r11
    mov [ rbp + -520 ], r10
    mov r10, [ rbp + -408 ]
    movabsq r10, 5 ## (CONST 5)
    mov [ rbp + -408 ], r10
    mov r10, [ rbp + -520 ]
    mov r11, [ rbp + -408 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t54)) (CONST 5))
    mov [ rbp + -408 ], r11
    mov [ rbp + -520 ], r10
    mov r10, [ rbp + -464 ]
    mov r11, [ rbp + -1208 ]
    mov r10, r11 ## (MOVE (TEMP _t55) (TEMP _t4))
    mov [ rbp + -1208 ], r11
    mov [ rbp + -464 ], r10
    mov r10, [ rbp + -416 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -416 ], r10
    mov r10, [ rbp + -248 ]
    mov r11, [ rbp + -464 ]
    mov r10, r11 ## (ADD (TEMP _t55) (CONST 8))
    mov [ rbp + -464 ], r11
    mov [ rbp + -248 ], r10
    mov r10, [ rbp + -248 ]
    mov r11, [ rbp + -416 ]
    addq r10, r11 ## (ADD (TEMP _t55) (CONST 8))
    mov [ rbp + -416 ], r11
    mov [ rbp + -248 ], r10
    mov r10, [ rbp + -1192 ]
    mov r11, [ rbp + -248 ]
    mov r10, r11 ## (MOVE (TEMP _t5) (ADD (TEMP _t55) (CONST 8)))
    mov [ rbp + -248 ], r11
    mov [ rbp + -1192 ], r10
    mov r10, [ rbp + -480 ]
    mov r11, [ rbp + -1192 ]
    mov r10, r11 ## (MOVE (TEMP _t57) (TEMP _t5))
    mov [ rbp + -1192 ], r11
    mov [ rbp + -480 ], r10
    mov r10, [ rbp + -256 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -256 ], r10
    mov r10, [ rbp + -472 ]
    mov r11, [ rbp + -256 ]
    mov r10, r11 ## (MOVE (TEMP _t56) (CONST 8))
    mov [ rbp + -256 ], r11
    mov [ rbp + -472 ], r10
    mov r10, [ rbp + -264 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -264 ], r10
    mov r10, [ rbp + -184 ]
    mov r11, [ rbp + -472 ]
    mov r10, r11 ## (MUL (TEMP _t56) (CONST 0))
    mov [ rbp + -472 ], r11
    mov [ rbp + -184 ], r10
    mov r10, [ rbp + -184 ]
    mov r11, [ rbp + -264 ]
    imulq r10, r11 ## (MUL (TEMP _t56) (CONST 0))
    mov [ rbp + -264 ], r11
    mov [ rbp + -184 ], r10
    mov r10, [ rbp + -192 ]
    mov r11, [ rbp + -480 ]
    mov r10, r11 ## (ADD (TEMP _t57) (MUL (TEMP _t56) (CONST 0)))
    mov [ rbp + -480 ], r11
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -192 ]
    mov r11, [ rbp + -184 ]
    addq r10, r11 ## (ADD (TEMP _t57) (MUL (TEMP _t56) (CONST 0)))
    mov [ rbp + -184 ], r11
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -488 ]
    mov r11, [ rbp + -192 ]
    mov r10, r11 ## (MOVE (TEMP _t58) (ADD (TEMP _t57) (MUL (TEMP _t56) (CONST 0))))
    mov [ rbp + -192 ], r11
    mov [ rbp + -488 ], r10
    mov r10, [ rbp + -200 ]
    movabsq r10, 33 ## (CONST 33)
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -488 ]
    mov r11, [ rbp + -200 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t58)) (CONST 33))
    mov [ rbp + -200 ], r11
    mov [ rbp + -488 ], r10
    mov r10, [ rbp + -824 ]
    mov r11, [ rbp + -1192 ]
    mov r10, r11 ## (MOVE (TEMP _t60) (TEMP _t5))
    mov [ rbp + -1192 ], r11
    mov [ rbp + -824 ], r10
    mov r10, [ rbp + -208 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -456 ]
    mov r11, [ rbp + -208 ]
    mov r10, r11 ## (MOVE (TEMP _t59) (CONST 8))
    mov [ rbp + -208 ], r11
    mov [ rbp + -456 ], r10
    mov r10, [ rbp + -216 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -216 ], r10
    mov r10, [ rbp + -224 ]
    mov r11, [ rbp + -456 ]
    mov r10, r11 ## (MUL (TEMP _t59) (CONST 1))
    mov [ rbp + -456 ], r11
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -224 ]
    mov r11, [ rbp + -216 ]
    imulq r10, r11 ## (MUL (TEMP _t59) (CONST 1))
    mov [ rbp + -216 ], r11
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -232 ]
    mov r11, [ rbp + -824 ]
    mov r10, r11 ## (ADD (TEMP _t60) (MUL (TEMP _t59) (CONST 1)))
    mov [ rbp + -824 ], r11
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -232 ]
    mov r11, [ rbp + -224 ]
    addq r10, r11 ## (ADD (TEMP _t60) (MUL (TEMP _t59) (CONST 1)))
    mov [ rbp + -224 ], r11
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -832 ]
    mov r11, [ rbp + -232 ]
    mov r10, r11 ## (MOVE (TEMP _t61) (ADD (TEMP _t60) (MUL (TEMP _t59) (CONST 1))))
    mov [ rbp + -232 ], r11
    mov [ rbp + -832 ], r10
    mov r10, [ rbp + -240 ]
    movabsq r10, 32 ## (CONST 32)
    mov [ rbp + -240 ], r10
    mov r10, [ rbp + -832 ]
    mov r11, [ rbp + -240 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t61)) (CONST 32))
    mov [ rbp + -240 ], r11
    mov [ rbp + -832 ], r10
    mov r10, [ rbp + -888 ]
    mov r11, [ rbp + -1192 ]
    mov r10, r11 ## (MOVE (TEMP _t63) (TEMP _t5))
    mov [ rbp + -1192 ], r11
    mov [ rbp + -888 ], r10
    mov r10, [ rbp + -80 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -880 ]
    mov r11, [ rbp + -80 ]
    mov r10, r11 ## (MOVE (TEMP _t62) (CONST 8))
    mov [ rbp + -80 ], r11
    mov [ rbp + -880 ], r10
    mov r10, [ rbp + -88 ]
    movabsq r10, 2 ## (CONST 2)
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -8 ]
    mov r11, [ rbp + -880 ]
    mov r10, r11 ## (MUL (TEMP _t62) (CONST 2))
    mov [ rbp + -880 ], r11
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -8 ]
    mov r11, [ rbp + -88 ]
    imulq r10, r11 ## (MUL (TEMP _t62) (CONST 2))
    mov [ rbp + -88 ], r11
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -888 ]
    mov r10, r11 ## (ADD (TEMP _t63) (MUL (TEMP _t62) (CONST 2)))
    mov [ rbp + -888 ], r11
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -8 ]
    addq r10, r11 ## (ADD (TEMP _t63) (MUL (TEMP _t62) (CONST 2)))
    mov [ rbp + -8 ], r11
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -896 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t64) (ADD (TEMP _t63) (MUL (TEMP _t62) (CONST 2))))
    mov [ rbp + -16 ], r11
    mov [ rbp + -896 ], r10
    mov r10, [ rbp + -24 ]
    movabsq r10, 105 ## (CONST 105)
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -896 ]
    mov r11, [ rbp + -24 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t64)) (CONST 105))
    mov [ rbp + -24 ], r11
    mov [ rbp + -896 ], r10
    mov r10, [ rbp + -848 ]
    mov r11, [ rbp + -1192 ]
    mov r10, r11 ## (MOVE (TEMP _t66) (TEMP _t5))
    mov [ rbp + -1192 ], r11
    mov [ rbp + -848 ], r10
    mov r10, [ rbp + -32 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -904 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (MOVE (TEMP _t65) (CONST 8))
    mov [ rbp + -32 ], r11
    mov [ rbp + -904 ], r10
    mov r10, [ rbp + -40 ]
    movabsq r10, 3 ## (CONST 3)
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -904 ]
    mov r10, r11 ## (MUL (TEMP _t65) (CONST 3))
    mov [ rbp + -904 ], r11
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -40 ]
    imulq r10, r11 ## (MUL (TEMP _t65) (CONST 3))
    mov [ rbp + -40 ], r11
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -848 ]
    mov r10, r11 ## (ADD (TEMP _t66) (MUL (TEMP _t65) (CONST 3)))
    mov [ rbp + -848 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -48 ]
    addq r10, r11 ## (ADD (TEMP _t66) (MUL (TEMP _t65) (CONST 3)))
    mov [ rbp + -48 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -856 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t67) (ADD (TEMP _t66) (MUL (TEMP _t65) (CONST 3))))
    mov [ rbp + -56 ], r11
    mov [ rbp + -856 ], r10
    mov r10, [ rbp + -64 ]
    movabsq r10, 115 ## (CONST 115)
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -856 ]
    mov r11, [ rbp + -64 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t67)) (CONST 115))
    mov [ rbp + -64 ], r11
    mov [ rbp + -856 ], r10
    mov r10, [ rbp + -872 ]
    mov r11, [ rbp + -1192 ]
    mov r10, r11 ## (MOVE (TEMP _t69) (TEMP _t5))
    mov [ rbp + -1192 ], r11
    mov [ rbp + -872 ], r10
    mov r10, [ rbp + -72 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -864 ]
    mov r11, [ rbp + -72 ]
    mov r10, r11 ## (MOVE (TEMP _t68) (CONST 8))
    mov [ rbp + -72 ], r11
    mov [ rbp + -864 ], r10
    mov r10, [ rbp + -1120 ]
    movabsq r10, 4 ## (CONST 4)
    mov [ rbp + -1120 ], r10
    mov r10, [ rbp + -1096 ]
    mov r11, [ rbp + -864 ]
    mov r10, r11 ## (MUL (TEMP _t68) (CONST 4))
    mov [ rbp + -864 ], r11
    mov [ rbp + -1096 ], r10
    mov r10, [ rbp + -1096 ]
    mov r11, [ rbp + -1120 ]
    imulq r10, r11 ## (MUL (TEMP _t68) (CONST 4))
    mov [ rbp + -1120 ], r11
    mov [ rbp + -1096 ], r10
    mov r10, [ rbp + -1104 ]
    mov r11, [ rbp + -872 ]
    mov r10, r11 ## (ADD (TEMP _t69) (MUL (TEMP _t68) (CONST 4)))
    mov [ rbp + -872 ], r11
    mov [ rbp + -1104 ], r10
    mov r10, [ rbp + -1104 ]
    mov r11, [ rbp + -1096 ]
    addq r10, r11 ## (ADD (TEMP _t69) (MUL (TEMP _t68) (CONST 4)))
    mov [ rbp + -1096 ], r11
    mov [ rbp + -1104 ], r10
    mov r10, [ rbp + -704 ]
    mov r11, [ rbp + -1104 ]
    mov r10, r11 ## (MOVE (TEMP _t70) (ADD (TEMP _t69) (MUL (TEMP _t68) (CONST 4))))
    mov [ rbp + -1104 ], r11
    mov [ rbp + -704 ], r10
    mov r10, [ rbp + -1112 ]
    movabsq r10, 32 ## (CONST 32)
    mov [ rbp + -1112 ], r10
    mov r10, [ rbp + -704 ]
    mov r11, [ rbp + -1112 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t70)) (CONST 32))
    mov [ rbp + -1112 ], r11
    mov [ rbp + -704 ], r10
    mov r10, [ rbp + -712 ]
    mov r11, [ rbp + -1192 ]
    mov r10, r11 ## (MOVE (TEMP _t71) (TEMP _t5))
    mov [ rbp + -1192 ], r11
    mov [ rbp + -712 ], r10
    mov r10, [ rbp + -712 ]
    mov rdi, r10 ## (CALL_STMT _t72 (NAME _Iprint_pai) (TEMP _t71))
    mov [ rbp + -712 ], r10
    callq _Iprint_pai ## (CALL_STMT _t72 (NAME _Iprint_pai) (TEMP _t71))
    mov r10, [ rbp + -720 ]
    mov r10, rax ## (CALL_STMT _t72 (NAME _Iprint_pai) (TEMP _t71))
    mov [ rbp + -720 ], r10
    mov r10, [ rbp + -792 ]
    mov r11, [ rbp + -352 ]
    mov r10, r11 ## (MOVE (TEMP _t73) (TEMP val))
    mov [ rbp + -352 ], r11
    mov [ rbp + -792 ], r10
    mov r10, [ rbp + -792 ]
    mov rdi, r10 ## (CALL_STMT _t74 (NAME _IunparseInt_aii) (TEMP _t73))
    mov [ rbp + -792 ], r10
    callq _IunparseInt_aii ## (CALL_STMT _t74 (NAME _IunparseInt_aii) (TEMP _t73))
    mov r10, [ rbp + -800 ]
    mov r10, rax ## (CALL_STMT _t74 (NAME _IunparseInt_aii) (TEMP _t73))
    mov [ rbp + -800 ], r10
    mov r10, [ rbp + -808 ]
    mov r11, [ rbp + -800 ]
    mov r10, r11 ## (MOVE (TEMP _t75) (TEMP _t74))
    mov [ rbp + -800 ], r11
    mov [ rbp + -808 ], r10
    mov r10, [ rbp + -808 ]
    mov rdi, r10 ## (CALL_STMT _t76 (NAME _Iprintln_pai) (TEMP _t75))
    mov [ rbp + -808 ], r10
    callq _Iprintln_pai ## (CALL_STMT _t76 (NAME _Iprintln_pai) (TEMP _t75))
    mov r10, [ rbp + -816 ]
    mov r10, rax ## (CALL_STMT _t76 (NAME _Iprintln_pai) (TEMP _t75))
    mov [ rbp + -816 ], r10
    jmp _l8 ## (JUMP (NAME _l8))
_l5:
    jmp end__Imain_paai ## (RETURN ())
end__Imain_paai:
    addq rsp, 1208
    mov rsp, rbp
    popq rbp
    retq
_Ifactorial_ii:
    pushq rbp
    mov rbp, rsp
    subq rsp, 120
_l9:
    mov r10, [ rbp + -40 ]
    mov r10, rdi ## (MOVE (TEMP i) (TEMP _ARG0))
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -40 ]
    mov r10, r11 ## (MOVE (TEMP _t6) (TEMP i))
    mov [ rbp + -40 ], r11
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -48 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -48 ]
    cmpq r10, r11 ## (LEQ (TEMP _t6) (CONST 1))
    mov [ rbp + -48 ], r11
    mov [ rbp + -16 ], r10
    pushq rax
    mov rax, [ rbp + -64 ]
    setle al ## (LEQ (TEMP _t6) (CONST 1))
    mov [ rbp + -64 ], rax
    popq rax
    mov r10, [ rbp + -56 ]
    pushq rax
    mov rax, [ rbp + -64 ]
    movzx r10, al ## (LEQ (TEMP _t6) (CONST 1))
    mov [ rbp + -64 ], rax
    popq rax
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -56 ]
    cmpq r10, 1 ## (CJUMP (LEQ (TEMP _t6) (CONST 1)) _l0)
    mov [ rbp + -56 ], r10
    je _l0 ## (CJUMP (LEQ (TEMP _t6) (CONST 1)) _l0)
_l1:
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -40 ]
    mov r10, r11 ## (MOVE (TEMP _t10) (TEMP i))
    mov [ rbp + -40 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -8 ]
    mov r11, [ rbp + -40 ]
    mov r10, r11 ## (MOVE (TEMP _t7) (TEMP i))
    mov [ rbp + -40 ], r11
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -72 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (SUB (TEMP _t7) (CONST 1))
    mov [ rbp + -8 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -72 ]
    subq r10, r11 ## (SUB (TEMP _t7) (CONST 1))
    mov [ rbp + -72 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -80 ]
    mov r10, r11 ## (MOVE (TEMP _t8) (SUB (TEMP _t7) (CONST 1)))
    mov [ rbp + -80 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -32 ]
    mov rdi, r10 ## (CALL_STMT _t9 (NAME _Ifactorial_ii) (TEMP _t8))
    mov [ rbp + -32 ], r10
    callq _Ifactorial_ii ## (CALL_STMT _t9 (NAME _Ifactorial_ii) (TEMP _t8))
    mov r10, [ rbp + -24 ]
    mov r10, rax ## (CALL_STMT _t9 (NAME _Ifactorial_ii) (TEMP _t8))
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -120 ]
    mov r10, r11 ## (MUL (TEMP _t10) (TEMP _t9))
    mov [ rbp + -120 ], r11
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -24 ]
    imulq r10, r11 ## (MUL (TEMP _t10) (TEMP _t9))
    mov [ rbp + -24 ], r11
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -88 ]
    mov r10, r11 ## (MOVE (TEMP _t0) (MUL (TEMP _t10) (TEMP _t9)))
    mov [ rbp + -88 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -112 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t0))
    mov [ rbp + -112 ], r10
    jmp end__Ifactorial_ii ## (RETURN ())
_l10:
_l2:
    jmp end__Ifactorial_ii ## (RETURN ())
_l0:
    mov r10, [ rbp + -96 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -104 ]
    mov r11, [ rbp + -96 ]
    mov r10, r11 ## (MOVE (TEMP _t1) (CONST 1))
    mov [ rbp + -96 ], r11
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -104 ]
    mov rax, r10 ## (MOVE (TEMP _RET0) (TEMP _t1))
    mov [ rbp + -104 ], r10
    jmp end__Ifactorial_ii ## (RETURN ())
end__Ifactorial_ii:
    addq rsp, 120
    mov rsp, rbp
    popq rbp
    retq
