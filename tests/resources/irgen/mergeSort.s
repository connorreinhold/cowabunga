.intel_syntax noprefix
.globl _Imain_paai
.align 4
_Imain_paai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 1240
_l61:
    mov r10, [ rbp + -1024 ]
    mov r10, rdi ## (MOVE (TEMP args) (TEMP _ARG0))
    mov [ rbp + -1024 ], r10
    mov r10, [ rbp + -16 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -248 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t25) (CONST 0))
    mov [ rbp + -16 ], r11
    mov [ rbp + -248 ], r10
    mov r10, [ rbp + -240 ]
    mov r11, [ rbp + -1024 ]
    mov r10, r11 ## (MOVE (TEMP _t24) (TEMP args))
    mov [ rbp + -1024 ], r11
    mov [ rbp + -240 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -240 ]
    mov r10, r11 ## (MOVE (TEMP _t148) (TEMP _t24))
    mov [ rbp + -240 ], r11
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -1184 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -1184 ], r10
    mov r10, [ rbp + -1200 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (SUB (TEMP _t148) (CONST 8))
    mov [ rbp + -24 ], r11
    mov [ rbp + -1200 ], r10
    mov r10, [ rbp + -1200 ]
    mov r11, [ rbp + -1184 ]
    subq r10, r11 ## (SUB (TEMP _t148) (CONST 8))
    mov [ rbp + -1184 ], r11
    mov [ rbp + -1200 ], r10
    mov r10, [ rbp + -192 ]
    mov r11, [ rbp + -1200 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t26) (MEM (SUB (TEMP _t148) (CONST 8))))
    mov [ rbp + -1200 ], r11
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -1208 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -1208 ], r10
    mov r10, [ rbp + -1192 ]
    mov r11, [ rbp + -1208 ]
    mov r10, r11 ## (MOVE (TEMP _t149) (CONST 0))
    mov [ rbp + -1208 ], r11
    mov [ rbp + -1192 ], r10
    mov r10, [ rbp + -1192 ]
    mov r11, [ rbp + -248 ]
    cmpq r10, r11 ## (LEQ (TEMP _t149) (TEMP _t25))
    mov [ rbp + -248 ], r11
    mov [ rbp + -1192 ], r10
    pushq rax
    mov rax, [ rbp + -1224 ]
    setle al ## (LEQ (TEMP _t149) (TEMP _t25))
    mov [ rbp + -1224 ], rax
    popq rax
    mov r10, [ rbp + -1216 ]
    pushq rax
    mov rax, [ rbp + -1224 ]
    movzx r10, al ## (LEQ (TEMP _t149) (TEMP _t25))
    mov [ rbp + -1224 ], rax
    popq rax
    mov [ rbp + -1216 ], r10
    mov r10, [ rbp + -1080 ]
    mov r11, [ rbp + -1216 ]
    mov r10, r11 ## (MOVE (TEMP _t151) (LEQ (TEMP _t149) (TEMP _t25)))
    mov [ rbp + -1216 ], r11
    mov [ rbp + -1080 ], r10
    mov r10, [ rbp + -1072 ]
    mov r11, [ rbp + -248 ]
    mov r10, r11 ## (MOVE (TEMP _t150) (TEMP _t25))
    mov [ rbp + -248 ], r11
    mov [ rbp + -1072 ], r10
    mov r10, [ rbp + -1072 ]
    mov r11, [ rbp + -192 ]
    cmpq r10, r11 ## (LT (TEMP _t150) (TEMP _t26))
    mov [ rbp + -192 ], r11
    mov [ rbp + -1072 ], r10
    pushq rax
    mov rax, [ rbp + -1240 ]
    setl al ## (LT (TEMP _t150) (TEMP _t26))
    mov [ rbp + -1240 ], rax
    popq rax
    mov r10, [ rbp + -1232 ]
    pushq rax
    mov rax, [ rbp + -1240 ]
    movzx r10, al ## (LT (TEMP _t150) (TEMP _t26))
    mov [ rbp + -1240 ], rax
    popq rax
    mov [ rbp + -1232 ], r10
    mov r10, [ rbp + -1120 ]
    mov r11, [ rbp + -1080 ]
    mov r10, r11 ## (AND (TEMP _t151) (LT (TEMP _t150) (TEMP _t26)))
    mov [ rbp + -1080 ], r11
    mov [ rbp + -1120 ], r10
    mov r10, [ rbp + -1120 ]
    mov r11, [ rbp + -1232 ]
    andq r10, r11 ## (AND (TEMP _t151) (LT (TEMP _t150) (TEMP _t26)))
    mov [ rbp + -1232 ], r11
    mov [ rbp + -1120 ], r10
    mov r10, [ rbp + -1120 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t151) (LT (TEMP _t150) (TEMP _t26))) _l19)
    mov [ rbp + -1120 ], r10
    je _l19 ## (CJUMP (AND (TEMP _t151) (LT (TEMP _t150) (TEMP _t26))) _l19)
_l20:
    callq _xi_out_of_bounds ## (CALL_STMT _t152 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -1088 ]
    mov r10, rax ## (CALL_STMT _t152 (NAME _xi_out_of_bounds))
    mov [ rbp + -1088 ], r10
_l19:
    mov r10, [ rbp + -1104 ]
    mov r11, [ rbp + -240 ]
    mov r10, r11 ## (MOVE (TEMP _t154) (TEMP _t24))
    mov [ rbp + -240 ], r11
    mov [ rbp + -1104 ], r10
    mov r10, [ rbp + -1096 ]
    mov r11, [ rbp + -248 ]
    mov r10, r11 ## (MOVE (TEMP _t153) (TEMP _t25))
    mov [ rbp + -248 ], r11
    mov [ rbp + -1096 ], r10
    mov r10, [ rbp + -1136 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -1136 ], r10
    mov r10, [ rbp + -1152 ]
    mov r11, [ rbp + -1096 ]
    mov r10, r11 ## (MUL (TEMP _t153) (CONST 8))
    mov [ rbp + -1096 ], r11
    mov [ rbp + -1152 ], r10
    mov r10, [ rbp + -1152 ]
    mov r11, [ rbp + -1136 ]
    imulq r10, r11 ## (MUL (TEMP _t153) (CONST 8))
    mov [ rbp + -1136 ], r11
    mov [ rbp + -1152 ], r10
    mov r10, [ rbp + -1168 ]
    mov r11, [ rbp + -1104 ]
    mov r10, r11 ## (ADD (TEMP _t154) (MUL (TEMP _t153) (CONST 8)))
    mov [ rbp + -1104 ], r11
    mov [ rbp + -1168 ], r10
    mov r10, [ rbp + -1168 ]
    mov r11, [ rbp + -1152 ]
    addq r10, r11 ## (ADD (TEMP _t154) (MUL (TEMP _t153) (CONST 8)))
    mov [ rbp + -1152 ], r11
    mov [ rbp + -1168 ], r10
    mov r10, [ rbp + -1112 ]
    mov r11, [ rbp + -1168 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t155) (MEM (ADD (TEMP _t154) (MUL (TEMP _t153) (CONST 8)))))
    mov [ rbp + -1168 ], r11
    mov [ rbp + -1112 ], r10
    mov r10, [ rbp + -1016 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -1016 ], r10
    mov r10, [ rbp + -1032 ]
    mov r11, [ rbp + -1112 ]
    mov r10, r11 ## (SUB (TEMP _t155) (CONST 8))
    mov [ rbp + -1112 ], r11
    mov [ rbp + -1032 ], r10
    mov r10, [ rbp + -1032 ]
    mov r11, [ rbp + -1016 ]
    subq r10, r11 ## (SUB (TEMP _t155) (CONST 8))
    mov [ rbp + -1016 ], r11
    mov [ rbp + -1032 ], r10
    mov r10, [ rbp + -224 ]
    mov r11, [ rbp + -1032 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t22) (MEM (SUB (TEMP _t155) (CONST 8))))
    mov [ rbp + -1032 ], r11
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -1040 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -1040 ], r10
    mov r10, [ rbp + -1144 ]
    mov r11, [ rbp + -1040 ]
    mov r10, r11 ## (MOVE (TEMP _t157) (CONST 8))
    mov [ rbp + -1040 ], r11
    mov [ rbp + -1144 ], r10
    mov r10, [ rbp + -1128 ]
    mov r11, [ rbp + -224 ]
    mov r10, r11 ## (MOVE (TEMP _t156) (TEMP _t22))
    mov [ rbp + -224 ], r11
    mov [ rbp + -1128 ], r10
    mov r10, [ rbp + -1048 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -1048 ], r10
    mov r10, [ rbp + -1056 ]
    mov r11, [ rbp + -1128 ]
    mov r10, r11 ## (ADD (TEMP _t156) (CONST 1))
    mov [ rbp + -1128 ], r11
    mov [ rbp + -1056 ], r10
    mov r10, [ rbp + -1056 ]
    mov r11, [ rbp + -1048 ]
    addq r10, r11 ## (ADD (TEMP _t156) (CONST 1))
    mov [ rbp + -1048 ], r11
    mov [ rbp + -1056 ], r10
    mov r10, [ rbp + -1064 ]
    mov r11, [ rbp + -1144 ]
    mov r10, r11 ## (MUL (TEMP _t157) (ADD (TEMP _t156) (CONST 1)))
    mov [ rbp + -1144 ], r11
    mov [ rbp + -1064 ], r10
    mov r10, [ rbp + -1064 ]
    mov r11, [ rbp + -1056 ]
    imulq r10, r11 ## (MUL (TEMP _t157) (ADD (TEMP _t156) (CONST 1)))
    mov [ rbp + -1056 ], r11
    mov [ rbp + -1064 ], r10
    mov r10, [ rbp + -1160 ]
    mov r11, [ rbp + -1064 ]
    mov r10, r11 ## (MOVE (TEMP _t158) (MUL (TEMP _t157) (ADD (TEMP _t156) (CONST 1))))
    mov [ rbp + -1064 ], r11
    mov [ rbp + -1160 ], r10
    mov r10, [ rbp + -1160 ]
    mov rdi, r10 ## (CALL_STMT _t159 (NAME _xi_alloc) (TEMP _t158))
    mov [ rbp + -1160 ], r10
    callq _xi_alloc ## (CALL_STMT _t159 (NAME _xi_alloc) (TEMP _t158))
    mov r10, [ rbp + -1176 ]
    mov r10, rax ## (CALL_STMT _t159 (NAME _xi_alloc) (TEMP _t158))
    mov [ rbp + -1176 ], r10
    mov r10, [ rbp + -256 ]
    mov r11, [ rbp + -1176 ]
    mov r10, r11 ## (MOVE (TEMP _t21) (TEMP _t159))
    mov [ rbp + -1176 ], r11
    mov [ rbp + -256 ], r10
    mov r10, [ rbp + -864 ]
    mov r11, [ rbp + -256 ]
    mov r10, r11 ## (MOVE (TEMP _t160) (TEMP _t21))
    mov [ rbp + -256 ], r11
    mov [ rbp + -864 ], r10
    mov r10, [ rbp + -864 ]
    mov r11, [ rbp + -224 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t160)) (TEMP _t22))
    mov [ rbp + -224 ], r11
    mov [ rbp + -864 ], r10
    mov r10, [ rbp + -872 ]
    mov r11, [ rbp + -256 ]
    mov r10, r11 ## (MOVE (TEMP _t161) (TEMP _t21))
    mov [ rbp + -256 ], r11
    mov [ rbp + -872 ], r10
    mov r10, [ rbp + -912 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -912 ], r10
    mov r10, [ rbp + -928 ]
    mov r11, [ rbp + -872 ]
    mov r10, r11 ## (ADD (TEMP _t161) (CONST 8))
    mov [ rbp + -872 ], r11
    mov [ rbp + -928 ], r10
    mov r10, [ rbp + -928 ]
    mov r11, [ rbp + -912 ]
    addq r10, r11 ## (ADD (TEMP _t161) (CONST 8))
    mov [ rbp + -912 ], r11
    mov [ rbp + -928 ], r10
    mov r10, [ rbp + -232 ]
    mov r11, [ rbp + -928 ]
    mov r10, r11 ## (MOVE (TEMP _t23) (ADD (TEMP _t161) (CONST 8)))
    mov [ rbp + -928 ], r11
    mov [ rbp + -232 ], r10
_l16:
    mov r10, [ rbp + -880 ]
    mov r11, [ rbp + -224 ]
    mov r10, r11 ## (MOVE (TEMP _t162) (TEMP _t22))
    mov [ rbp + -224 ], r11
    mov [ rbp + -880 ], r10
    mov r10, [ rbp + -952 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -952 ], r10
    mov r10, [ rbp + -880 ]
    mov r11, [ rbp + -952 ]
    cmpq r10, r11 ## (GT (TEMP _t162) (CONST 0))
    mov [ rbp + -952 ], r11
    mov [ rbp + -880 ], r10
    pushq rax
    mov rax, [ rbp + -1000 ]
    setg al ## (GT (TEMP _t162) (CONST 0))
    mov [ rbp + -1000 ], rax
    popq rax
    mov r10, [ rbp + -976 ]
    pushq rax
    mov rax, [ rbp + -1000 ]
    movzx r10, al ## (GT (TEMP _t162) (CONST 0))
    mov [ rbp + -1000 ], rax
    popq rax
    mov [ rbp + -976 ], r10
    mov r10, [ rbp + -976 ]
    cmpq r10, 1 ## (CJUMP (GT (TEMP _t162) (CONST 0)) _l17)
    mov [ rbp + -976 ], r10
    je _l17 ## (CJUMP (GT (TEMP _t162) (CONST 0)) _l17)
_l18:
    mov r10, [ rbp + -520 ]
    mov r11, [ rbp + -232 ]
    mov r10, r11 ## (MOVE (TEMP b) (TEMP _t23))
    mov [ rbp + -232 ], r11
    mov [ rbp + -520 ], r10
    mov r10, [ rbp + -824 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -824 ], r10
    mov r10, [ rbp + -208 ]
    mov r11, [ rbp + -824 ]
    mov r10, r11 ## (MOVE (TEMP _t28) (CONST 0))
    mov [ rbp + -824 ], r11
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -1024 ]
    mov r10, r11 ## (MOVE (TEMP _t27) (TEMP args))
    mov [ rbp + -1024 ], r11
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -936 ]
    mov r11, [ rbp + -200 ]
    mov r10, r11 ## (MOVE (TEMP _t167) (TEMP _t27))
    mov [ rbp + -200 ], r11
    mov [ rbp + -936 ], r10
    mov r10, [ rbp + -832 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -832 ], r10
    mov r10, [ rbp + -840 ]
    mov r11, [ rbp + -936 ]
    mov r10, r11 ## (SUB (TEMP _t167) (CONST 8))
    mov [ rbp + -936 ], r11
    mov [ rbp + -840 ], r10
    mov r10, [ rbp + -840 ]
    mov r11, [ rbp + -832 ]
    subq r10, r11 ## (SUB (TEMP _t167) (CONST 8))
    mov [ rbp + -832 ], r11
    mov [ rbp + -840 ], r10
    mov r10, [ rbp + -216 ]
    mov r11, [ rbp + -840 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t29) (MEM (SUB (TEMP _t167) (CONST 8))))
    mov [ rbp + -840 ], r11
    mov [ rbp + -216 ], r10
    mov r10, [ rbp + -848 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -848 ], r10
    mov r10, [ rbp + -960 ]
    mov r11, [ rbp + -848 ]
    mov r10, r11 ## (MOVE (TEMP _t168) (CONST 0))
    mov [ rbp + -848 ], r11
    mov [ rbp + -960 ], r10
    mov r10, [ rbp + -960 ]
    mov r11, [ rbp + -208 ]
    cmpq r10, r11 ## (LEQ (TEMP _t168) (TEMP _t28))
    mov [ rbp + -208 ], r11
    mov [ rbp + -960 ], r10
    pushq rax
    mov rax, [ rbp + -744 ]
    setle al ## (LEQ (TEMP _t168) (TEMP _t28))
    mov [ rbp + -744 ], rax
    popq rax
    mov r10, [ rbp + -856 ]
    pushq rax
    mov rax, [ rbp + -744 ]
    movzx r10, al ## (LEQ (TEMP _t168) (TEMP _t28))
    mov [ rbp + -744 ], rax
    popq rax
    mov [ rbp + -856 ], r10
    mov r10, [ rbp + -696 ]
    mov r11, [ rbp + -856 ]
    mov r10, r11 ## (MOVE (TEMP _t170) (LEQ (TEMP _t168) (TEMP _t28)))
    mov [ rbp + -856 ], r11
    mov [ rbp + -696 ], r10
    mov r10, [ rbp + -984 ]
    mov r11, [ rbp + -208 ]
    mov r10, r11 ## (MOVE (TEMP _t169) (TEMP _t28))
    mov [ rbp + -208 ], r11
    mov [ rbp + -984 ], r10
    mov r10, [ rbp + -984 ]
    mov r11, [ rbp + -216 ]
    cmpq r10, r11 ## (LT (TEMP _t169) (TEMP _t29))
    mov [ rbp + -216 ], r11
    mov [ rbp + -984 ], r10
    pushq rax
    mov rax, [ rbp + -776 ]
    setl al ## (LT (TEMP _t169) (TEMP _t29))
    mov [ rbp + -776 ], rax
    popq rax
    mov r10, [ rbp + -760 ]
    pushq rax
    mov rax, [ rbp + -776 ]
    movzx r10, al ## (LT (TEMP _t169) (TEMP _t29))
    mov [ rbp + -776 ], rax
    popq rax
    mov [ rbp + -760 ], r10
    mov r10, [ rbp + -792 ]
    mov r11, [ rbp + -696 ]
    mov r10, r11 ## (AND (TEMP _t170) (LT (TEMP _t169) (TEMP _t29)))
    mov [ rbp + -696 ], r11
    mov [ rbp + -792 ], r10
    mov r10, [ rbp + -792 ]
    mov r11, [ rbp + -760 ]
    andq r10, r11 ## (AND (TEMP _t170) (LT (TEMP _t169) (TEMP _t29)))
    mov [ rbp + -760 ], r11
    mov [ rbp + -792 ], r10
    mov r10, [ rbp + -792 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t170) (LT (TEMP _t169) (TEMP _t29))) _l21)
    mov [ rbp + -792 ], r10
    je _l21 ## (CJUMP (AND (TEMP _t170) (LT (TEMP _t169) (TEMP _t29))) _l21)
_l22:
    callq _xi_out_of_bounds ## (CALL_STMT _t171 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -704 ]
    mov r10, rax ## (CALL_STMT _t171 (NAME _xi_out_of_bounds))
    mov [ rbp + -704 ], r10
_l21:
    mov r10, [ rbp + -720 ]
    mov r11, [ rbp + -200 ]
    mov r10, r11 ## (MOVE (TEMP _t173) (TEMP _t27))
    mov [ rbp + -200 ], r11
    mov [ rbp + -720 ], r10
    mov r10, [ rbp + -712 ]
    mov r11, [ rbp + -208 ]
    mov r10, r11 ## (MOVE (TEMP _t172) (TEMP _t28))
    mov [ rbp + -208 ], r11
    mov [ rbp + -712 ], r10
    mov r10, [ rbp + -808 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -808 ], r10
    mov r10, [ rbp + -816 ]
    mov r11, [ rbp + -712 ]
    mov r10, r11 ## (MUL (TEMP _t172) (CONST 8))
    mov [ rbp + -712 ], r11
    mov [ rbp + -816 ], r10
    mov r10, [ rbp + -816 ]
    mov r11, [ rbp + -808 ]
    imulq r10, r11 ## (MUL (TEMP _t172) (CONST 8))
    mov [ rbp + -808 ], r11
    mov [ rbp + -816 ], r10
    mov r10, [ rbp + -664 ]
    mov r11, [ rbp + -720 ]
    mov r10, r11 ## (ADD (TEMP _t173) (MUL (TEMP _t172) (CONST 8)))
    mov [ rbp + -720 ], r11
    mov [ rbp + -664 ], r10
    mov r10, [ rbp + -664 ]
    mov r11, [ rbp + -816 ]
    addq r10, r11 ## (ADD (TEMP _t173) (MUL (TEMP _t172) (CONST 8)))
    mov [ rbp + -816 ], r11
    mov [ rbp + -664 ], r10
    mov r10, [ rbp + -728 ]
    mov r11, [ rbp + -664 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t174) (MEM (ADD (TEMP _t173) (MUL (TEMP _t172) (CONST 8)))))
    mov [ rbp + -664 ], r11
    mov [ rbp + -728 ], r10
    mov r10, [ rbp + -736 ]
    mov r11, [ rbp + -520 ]
    mov r10, r11 ## (MOVE (TEMP _t175) (TEMP b))
    mov [ rbp + -520 ], r11
    mov [ rbp + -736 ], r10
    mov r10, [ rbp + -672 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -672 ], r10
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -672 ]
    mov r10, r11 ## (MOVE (TEMP _t31) (CONST 0))
    mov [ rbp + -672 ], r11
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -1024 ]
    mov r10, r11 ## (MOVE (TEMP _t30) (TEMP args))
    mov [ rbp + -1024 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -752 ]
    mov r11, [ rbp + -80 ]
    mov r10, r11 ## (MOVE (TEMP _t176) (TEMP _t30))
    mov [ rbp + -80 ], r11
    mov [ rbp + -752 ], r10
    mov r10, [ rbp + -680 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -680 ], r10
    mov r10, [ rbp + -688 ]
    mov r11, [ rbp + -752 ]
    mov r10, r11 ## (SUB (TEMP _t176) (CONST 8))
    mov [ rbp + -752 ], r11
    mov [ rbp + -688 ], r10
    mov r10, [ rbp + -688 ]
    mov r11, [ rbp + -680 ]
    subq r10, r11 ## (SUB (TEMP _t176) (CONST 8))
    mov [ rbp + -680 ], r11
    mov [ rbp + -688 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -688 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t32) (MEM (SUB (TEMP _t176) (CONST 8))))
    mov [ rbp + -688 ], r11
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -576 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -576 ], r10
    mov r10, [ rbp + -768 ]
    mov r11, [ rbp + -576 ]
    mov r10, r11 ## (MOVE (TEMP _t177) (CONST 0))
    mov [ rbp + -576 ], r11
    mov [ rbp + -768 ], r10
    mov r10, [ rbp + -768 ]
    mov r11, [ rbp + -88 ]
    cmpq r10, r11 ## (LEQ (TEMP _t177) (TEMP _t31))
    mov [ rbp + -88 ], r11
    mov [ rbp + -768 ], r10
    pushq rax
    mov rax, [ rbp + -608 ]
    setle al ## (LEQ (TEMP _t177) (TEMP _t31))
    mov [ rbp + -608 ], rax
    popq rax
    mov r10, [ rbp + -592 ]
    pushq rax
    mov rax, [ rbp + -608 ]
    movzx r10, al ## (LEQ (TEMP _t177) (TEMP _t31))
    mov [ rbp + -608 ], rax
    popq rax
    mov [ rbp + -592 ], r10
    mov r10, [ rbp + -800 ]
    mov r11, [ rbp + -592 ]
    mov r10, r11 ## (MOVE (TEMP _t179) (LEQ (TEMP _t177) (TEMP _t31)))
    mov [ rbp + -592 ], r11
    mov [ rbp + -800 ], r10
    mov r10, [ rbp + -784 ]
    mov r11, [ rbp + -88 ]
    mov r10, r11 ## (MOVE (TEMP _t178) (TEMP _t31))
    mov [ rbp + -88 ], r11
    mov [ rbp + -784 ], r10
    mov r10, [ rbp + -784 ]
    mov r11, [ rbp + -96 ]
    cmpq r10, r11 ## (LT (TEMP _t178) (TEMP _t32))
    mov [ rbp + -96 ], r11
    mov [ rbp + -784 ], r10
    pushq rax
    mov rax, [ rbp + -640 ]
    setl al ## (LT (TEMP _t178) (TEMP _t32))
    mov [ rbp + -640 ], rax
    popq rax
    mov r10, [ rbp + -624 ]
    pushq rax
    mov rax, [ rbp + -640 ]
    movzx r10, al ## (LT (TEMP _t178) (TEMP _t32))
    mov [ rbp + -640 ], rax
    popq rax
    mov [ rbp + -624 ], r10
    mov r10, [ rbp + -648 ]
    mov r11, [ rbp + -800 ]
    mov r10, r11 ## (AND (TEMP _t179) (LT (TEMP _t178) (TEMP _t32)))
    mov [ rbp + -800 ], r11
    mov [ rbp + -648 ], r10
    mov r10, [ rbp + -648 ]
    mov r11, [ rbp + -624 ]
    andq r10, r11 ## (AND (TEMP _t179) (LT (TEMP _t178) (TEMP _t32)))
    mov [ rbp + -624 ], r11
    mov [ rbp + -648 ], r10
    mov r10, [ rbp + -648 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t179) (LT (TEMP _t178) (TEMP _t32))) _l23)
    mov [ rbp + -648 ], r10
    je _l23 ## (CJUMP (AND (TEMP _t179) (LT (TEMP _t178) (TEMP _t32))) _l23)
_l24:
    callq _xi_out_of_bounds ## (CALL_STMT _t180 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -528 ]
    mov r10, rax ## (CALL_STMT _t180 (NAME _xi_out_of_bounds))
    mov [ rbp + -528 ], r10
_l23:
    mov r10, [ rbp + -544 ]
    mov r11, [ rbp + -80 ]
    mov r10, r11 ## (MOVE (TEMP _t182) (TEMP _t30))
    mov [ rbp + -80 ], r11
    mov [ rbp + -544 ], r10
    mov r10, [ rbp + -536 ]
    mov r11, [ rbp + -88 ]
    mov r10, r11 ## (MOVE (TEMP _t181) (TEMP _t31))
    mov [ rbp + -88 ], r11
    mov [ rbp + -536 ], r10
    mov r10, [ rbp + -656 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -656 ], r10
    mov r10, [ rbp + -496 ]
    mov r11, [ rbp + -536 ]
    mov r10, r11 ## (MUL (TEMP _t181) (CONST 8))
    mov [ rbp + -536 ], r11
    mov [ rbp + -496 ], r10
    mov r10, [ rbp + -496 ]
    mov r11, [ rbp + -656 ]
    imulq r10, r11 ## (MUL (TEMP _t181) (CONST 8))
    mov [ rbp + -656 ], r11
    mov [ rbp + -496 ], r10
    mov r10, [ rbp + -504 ]
    mov r11, [ rbp + -544 ]
    mov r10, r11 ## (ADD (TEMP _t182) (MUL (TEMP _t181) (CONST 8)))
    mov [ rbp + -544 ], r11
    mov [ rbp + -504 ], r10
    mov r10, [ rbp + -504 ]
    mov r11, [ rbp + -496 ]
    addq r10, r11 ## (ADD (TEMP _t182) (MUL (TEMP _t181) (CONST 8)))
    mov [ rbp + -496 ], r11
    mov [ rbp + -504 ], r10
    mov r10, [ rbp + -552 ]
    mov r11, [ rbp + -504 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t183) (MEM (ADD (TEMP _t182) (MUL (TEMP _t181) (CONST 8)))))
    mov [ rbp + -504 ], r11
    mov [ rbp + -552 ], r10
    mov r10, [ rbp + -512 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -512 ], r10
    mov r10, [ rbp + -400 ]
    mov r11, [ rbp + -552 ]
    mov r10, r11 ## (SUB (TEMP _t183) (CONST 8))
    mov [ rbp + -552 ], r11
    mov [ rbp + -400 ], r10
    mov r10, [ rbp + -400 ]
    mov r11, [ rbp + -512 ]
    subq r10, r11 ## (SUB (TEMP _t183) (CONST 8))
    mov [ rbp + -512 ], r11
    mov [ rbp + -400 ], r10
    mov r10, [ rbp + -560 ]
    mov r11, [ rbp + -400 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t184) (MEM (SUB (TEMP _t183) (CONST 8))))
    mov [ rbp + -400 ], r11
    mov [ rbp + -560 ], r10
    mov r10, [ rbp + -728 ]
    mov rdi, r10 ## (CALL_STMT _t185 (NAME _Isort_pt3aiaii) (TEMP _t174) (TEMP _t175) (TEMP _t184))
    mov [ rbp + -728 ], r10
    mov r10, [ rbp + -736 ]
    mov rsi, r10 ## (CALL_STMT _t185 (NAME _Isort_pt3aiaii) (TEMP _t174) (TEMP _t175) (TEMP _t184))
    mov [ rbp + -736 ], r10
    mov r10, [ rbp + -560 ]
    mov rdx, r10 ## (CALL_STMT _t185 (NAME _Isort_pt3aiaii) (TEMP _t174) (TEMP _t175) (TEMP _t184))
    mov [ rbp + -560 ], r10
    callq _Isort_pt3aiaii ## (CALL_STMT _t185 (NAME _Isort_pt3aiaii) (TEMP _t174) (TEMP _t175) (TEMP _t184))
    mov r10, [ rbp + -568 ]
    mov r10, rax ## (CALL_STMT _t185 (NAME _Isort_pt3aiaii) (TEMP _t174) (TEMP _t175) (TEMP _t184))
    mov [ rbp + -568 ], r10
    mov r10, [ rbp + -416 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -416 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -416 ]
    mov r10, r11 ## (MOVE (TEMP _t34) (CONST 0))
    mov [ rbp + -416 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -1024 ]
    mov r10, r11 ## (MOVE (TEMP _t33) (TEMP args))
    mov [ rbp + -1024 ], r11
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -584 ]
    mov r11, [ rbp + -48 ]
    mov r10, r11 ## (MOVE (TEMP _t186) (TEMP _t33))
    mov [ rbp + -48 ], r11
    mov [ rbp + -584 ], r10
    mov r10, [ rbp + -432 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -432 ], r10
    mov r10, [ rbp + -448 ]
    mov r11, [ rbp + -584 ]
    mov r10, r11 ## (SUB (TEMP _t186) (CONST 8))
    mov [ rbp + -584 ], r11
    mov [ rbp + -448 ], r10
    mov r10, [ rbp + -448 ]
    mov r11, [ rbp + -432 ]
    subq r10, r11 ## (SUB (TEMP _t186) (CONST 8))
    mov [ rbp + -432 ], r11
    mov [ rbp + -448 ], r10
    mov r10, [ rbp + -64 ]
    mov r11, [ rbp + -448 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t35) (MEM (SUB (TEMP _t186) (CONST 8))))
    mov [ rbp + -448 ], r11
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -464 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -464 ], r10
    mov r10, [ rbp + -600 ]
    mov r11, [ rbp + -464 ]
    mov r10, r11 ## (MOVE (TEMP _t187) (CONST 0))
    mov [ rbp + -464 ], r11
    mov [ rbp + -600 ], r10
    mov r10, [ rbp + -600 ]
    mov r11, [ rbp + -56 ]
    cmpq r10, r11 ## (LEQ (TEMP _t187) (TEMP _t34))
    mov [ rbp + -56 ], r11
    mov [ rbp + -600 ], r10
    pushq rax
    mov rax, [ rbp + -480 ]
    setle al ## (LEQ (TEMP _t187) (TEMP _t34))
    mov [ rbp + -480 ], rax
    popq rax
    mov r10, [ rbp + -472 ]
    pushq rax
    mov rax, [ rbp + -480 ]
    movzx r10, al ## (LEQ (TEMP _t187) (TEMP _t34))
    mov [ rbp + -480 ], rax
    popq rax
    mov [ rbp + -472 ], r10
    mov r10, [ rbp + -632 ]
    mov r11, [ rbp + -472 ]
    mov r10, r11 ## (MOVE (TEMP _t189) (LEQ (TEMP _t187) (TEMP _t34)))
    mov [ rbp + -472 ], r11
    mov [ rbp + -632 ], r10
    mov r10, [ rbp + -616 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t188) (TEMP _t34))
    mov [ rbp + -56 ], r11
    mov [ rbp + -616 ], r10
    mov r10, [ rbp + -616 ]
    mov r11, [ rbp + -64 ]
    cmpq r10, r11 ## (LT (TEMP _t188) (TEMP _t35))
    mov [ rbp + -64 ], r11
    mov [ rbp + -616 ], r10
    pushq rax
    mov rax, [ rbp + -336 ]
    setl al ## (LT (TEMP _t188) (TEMP _t35))
    mov [ rbp + -336 ], rax
    popq rax
    mov r10, [ rbp + -488 ]
    pushq rax
    mov rax, [ rbp + -336 ]
    movzx r10, al ## (LT (TEMP _t188) (TEMP _t35))
    mov [ rbp + -336 ], rax
    popq rax
    mov [ rbp + -488 ], r10
    mov r10, [ rbp + -344 ]
    mov r11, [ rbp + -632 ]
    mov r10, r11 ## (AND (TEMP _t189) (LT (TEMP _t188) (TEMP _t35)))
    mov [ rbp + -632 ], r11
    mov [ rbp + -344 ], r10
    mov r10, [ rbp + -344 ]
    mov r11, [ rbp + -488 ]
    andq r10, r11 ## (AND (TEMP _t189) (LT (TEMP _t188) (TEMP _t35)))
    mov [ rbp + -488 ], r11
    mov [ rbp + -344 ], r10
    mov r10, [ rbp + -344 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t189) (LT (TEMP _t188) (TEMP _t35))) _l25)
    mov [ rbp + -344 ], r10
    je _l25 ## (CJUMP (AND (TEMP _t189) (LT (TEMP _t188) (TEMP _t35))) _l25)
_l26:
    callq _xi_out_of_bounds ## (CALL_STMT _t190 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -352 ]
    mov r10, rax ## (CALL_STMT _t190 (NAME _xi_out_of_bounds))
    mov [ rbp + -352 ], r10
_l25:
    mov r10, [ rbp + -368 ]
    mov r11, [ rbp + -48 ]
    mov r10, r11 ## (MOVE (TEMP _t192) (TEMP _t33))
    mov [ rbp + -48 ], r11
    mov [ rbp + -368 ], r10
    mov r10, [ rbp + -360 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t191) (TEMP _t34))
    mov [ rbp + -56 ], r11
    mov [ rbp + -360 ], r10
    mov r10, [ rbp + -264 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -264 ], r10
    mov r10, [ rbp + -272 ]
    mov r11, [ rbp + -360 ]
    mov r10, r11 ## (MUL (TEMP _t191) (CONST 8))
    mov [ rbp + -360 ], r11
    mov [ rbp + -272 ], r10
    mov r10, [ rbp + -272 ]
    mov r11, [ rbp + -264 ]
    imulq r10, r11 ## (MUL (TEMP _t191) (CONST 8))
    mov [ rbp + -264 ], r11
    mov [ rbp + -272 ], r10
    mov r10, [ rbp + -280 ]
    mov r11, [ rbp + -368 ]
    mov r10, r11 ## (ADD (TEMP _t192) (MUL (TEMP _t191) (CONST 8)))
    mov [ rbp + -368 ], r11
    mov [ rbp + -280 ], r10
    mov r10, [ rbp + -280 ]
    mov r11, [ rbp + -272 ]
    addq r10, r11 ## (ADD (TEMP _t192) (MUL (TEMP _t191) (CONST 8)))
    mov [ rbp + -272 ], r11
    mov [ rbp + -280 ], r10
    mov r10, [ rbp + -376 ]
    mov r11, [ rbp + -280 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t193) (MEM (ADD (TEMP _t192) (MUL (TEMP _t191) (CONST 8)))))
    mov [ rbp + -280 ], r11
    mov [ rbp + -376 ], r10
    mov r10, [ rbp + -376 ]
    mov rdi, r10 ## (CALL_STMT _t194 (NAME _Ireverse_pai) (TEMP _t193))
    mov [ rbp + -376 ], r10
    callq _Ireverse_pai ## (CALL_STMT _t194 (NAME _Ireverse_pai) (TEMP _t193))
    mov r10, [ rbp + -384 ]
    mov r10, rax ## (CALL_STMT _t194 (NAME _Ireverse_pai) (TEMP _t193))
    mov [ rbp + -384 ], r10
    mov r10, [ rbp + -288 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -288 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -288 ]
    mov r10, r11 ## (MOVE (TEMP _t37) (CONST 0))
    mov [ rbp + -288 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -72 ]
    mov r11, [ rbp + -1024 ]
    mov r10, r11 ## (MOVE (TEMP _t36) (TEMP args))
    mov [ rbp + -1024 ], r11
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -392 ]
    mov r11, [ rbp + -72 ]
    mov r10, r11 ## (MOVE (TEMP _t195) (TEMP _t36))
    mov [ rbp + -72 ], r11
    mov [ rbp + -392 ], r10
    mov r10, [ rbp + -296 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -296 ], r10
    mov r10, [ rbp + -304 ]
    mov r11, [ rbp + -392 ]
    mov r10, r11 ## (SUB (TEMP _t195) (CONST 8))
    mov [ rbp + -392 ], r11
    mov [ rbp + -304 ], r10
    mov r10, [ rbp + -304 ]
    mov r11, [ rbp + -296 ]
    subq r10, r11 ## (SUB (TEMP _t195) (CONST 8))
    mov [ rbp + -296 ], r11
    mov [ rbp + -304 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -304 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t38) (MEM (SUB (TEMP _t195) (CONST 8))))
    mov [ rbp + -304 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -312 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -312 ], r10
    mov r10, [ rbp + -408 ]
    mov r11, [ rbp + -312 ]
    mov r10, r11 ## (MOVE (TEMP _t196) (CONST 0))
    mov [ rbp + -312 ], r11
    mov [ rbp + -408 ], r10
    mov r10, [ rbp + -408 ]
    mov r11, [ rbp + -32 ]
    cmpq r10, r11 ## (LEQ (TEMP _t196) (TEMP _t37))
    mov [ rbp + -32 ], r11
    mov [ rbp + -408 ], r10
    pushq rax
    mov rax, [ rbp + -328 ]
    setle al ## (LEQ (TEMP _t196) (TEMP _t37))
    mov [ rbp + -328 ], rax
    popq rax
    mov r10, [ rbp + -320 ]
    pushq rax
    mov rax, [ rbp + -328 ]
    movzx r10, al ## (LEQ (TEMP _t196) (TEMP _t37))
    mov [ rbp + -328 ], rax
    popq rax
    mov [ rbp + -320 ], r10
    mov r10, [ rbp + -440 ]
    mov r11, [ rbp + -320 ]
    mov r10, r11 ## (MOVE (TEMP _t198) (LEQ (TEMP _t196) (TEMP _t37)))
    mov [ rbp + -320 ], r11
    mov [ rbp + -440 ], r10
    mov r10, [ rbp + -424 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (MOVE (TEMP _t197) (TEMP _t37))
    mov [ rbp + -32 ], r11
    mov [ rbp + -424 ], r10
    mov r10, [ rbp + -424 ]
    mov r11, [ rbp + -40 ]
    cmpq r10, r11 ## (LT (TEMP _t197) (TEMP _t38))
    mov [ rbp + -40 ], r11
    mov [ rbp + -424 ], r10
    pushq rax
    mov rax, [ rbp + -104 ]
    setl al ## (LT (TEMP _t197) (TEMP _t38))
    mov [ rbp + -104 ], rax
    popq rax
    mov r10, [ rbp + -184 ]
    pushq rax
    mov rax, [ rbp + -104 ]
    movzx r10, al ## (LT (TEMP _t197) (TEMP _t38))
    mov [ rbp + -104 ], rax
    popq rax
    mov [ rbp + -184 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -440 ]
    mov r10, r11 ## (AND (TEMP _t198) (LT (TEMP _t197) (TEMP _t38)))
    mov [ rbp + -440 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -184 ]
    andq r10, r11 ## (AND (TEMP _t198) (LT (TEMP _t197) (TEMP _t38)))
    mov [ rbp + -184 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -112 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t198) (LT (TEMP _t197) (TEMP _t38))) _l27)
    mov [ rbp + -112 ], r10
    je _l27 ## (CJUMP (AND (TEMP _t198) (LT (TEMP _t197) (TEMP _t38))) _l27)
_l28:
    callq _xi_out_of_bounds ## (CALL_STMT _t199 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -456 ]
    mov r10, rax ## (CALL_STMT _t199 (NAME _xi_out_of_bounds))
    mov [ rbp + -456 ], r10
_l27:
    mov r10, [ rbp + -968 ]
    mov r11, [ rbp + -72 ]
    mov r10, r11 ## (MOVE (TEMP _t201) (TEMP _t36))
    mov [ rbp + -72 ], r11
    mov [ rbp + -968 ], r10
    mov r10, [ rbp + -944 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (MOVE (TEMP _t200) (TEMP _t37))
    mov [ rbp + -32 ], r11
    mov [ rbp + -944 ], r10
    mov r10, [ rbp + -120 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -944 ]
    mov r10, r11 ## (MUL (TEMP _t200) (CONST 8))
    mov [ rbp + -944 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -120 ]
    imulq r10, r11 ## (MUL (TEMP _t200) (CONST 8))
    mov [ rbp + -120 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -968 ]
    mov r10, r11 ## (ADD (TEMP _t201) (MUL (TEMP _t200) (CONST 8)))
    mov [ rbp + -968 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -128 ]
    addq r10, r11 ## (ADD (TEMP _t201) (MUL (TEMP _t200) (CONST 8)))
    mov [ rbp + -128 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -992 ]
    mov r11, [ rbp + -136 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t202) (MEM (ADD (TEMP _t201) (MUL (TEMP _t200) (CONST 8)))))
    mov [ rbp + -136 ], r11
    mov [ rbp + -992 ], r10
    mov r10, [ rbp + -992 ]
    mov rdi, r10 ## (CALL_STMT _t203 (NAME _IprintArray_pai) (TEMP _t202))
    mov [ rbp + -992 ], r10
    callq _IprintArray_pai ## (CALL_STMT _t203 (NAME _IprintArray_pai) (TEMP _t202))
    mov r10, [ rbp + -1008 ]
    mov r10, rax ## (CALL_STMT _t203 (NAME _IprintArray_pai) (TEMP _t202))
    mov [ rbp + -1008 ], r10
    jmp end__Imain_paai ## (RETURN ())
_l17:
    mov r10, [ rbp + -888 ]
    mov r11, [ rbp + -224 ]
    mov r10, r11 ## (MOVE (TEMP _t163) (TEMP _t22))
    mov [ rbp + -224 ], r11
    mov [ rbp + -888 ], r10
    mov r10, [ rbp + -144 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -888 ]
    mov r10, r11 ## (SUB (TEMP _t163) (CONST 1))
    mov [ rbp + -888 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -144 ]
    subq r10, r11 ## (SUB (TEMP _t163) (CONST 1))
    mov [ rbp + -144 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -224 ]
    mov r11, [ rbp + -152 ]
    mov r10, r11 ## (MOVE (TEMP _t22) (SUB (TEMP _t163) (CONST 1)))
    mov [ rbp + -152 ], r11
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -904 ]
    mov r11, [ rbp + -232 ]
    mov r10, r11 ## (MOVE (TEMP _t165) (TEMP _t23))
    mov [ rbp + -232 ], r11
    mov [ rbp + -904 ], r10
    mov r10, [ rbp + -160 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -896 ]
    mov r11, [ rbp + -160 ]
    mov r10, r11 ## (MOVE (TEMP _t164) (CONST 8))
    mov [ rbp + -160 ], r11
    mov [ rbp + -896 ], r10
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -896 ]
    mov r10, r11 ## (MUL (TEMP _t164) (TEMP _t22))
    mov [ rbp + -896 ], r11
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -224 ]
    imulq r10, r11 ## (MUL (TEMP _t164) (TEMP _t22))
    mov [ rbp + -224 ], r11
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -904 ]
    mov r10, r11 ## (ADD (TEMP _t165) (MUL (TEMP _t164) (TEMP _t22)))
    mov [ rbp + -904 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -168 ]
    addq r10, r11 ## (ADD (TEMP _t165) (MUL (TEMP _t164) (TEMP _t22)))
    mov [ rbp + -168 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -920 ]
    mov r11, [ rbp + -176 ]
    mov r10, r11 ## (MOVE (TEMP _t166) (ADD (TEMP _t165) (MUL (TEMP _t164) (TEMP _t22))))
    mov [ rbp + -176 ], r11
    mov [ rbp + -920 ], r10
    mov r10, [ rbp + -8 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -920 ]
    mov r11, [ rbp + -8 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t166)) (CONST 0))
    mov [ rbp + -8 ], r11
    mov [ rbp + -920 ], r10
    jmp _l16 ## (JUMP (NAME _l16))
end__Imain_paai:
    addq rsp, 1240
    mov rsp, rbp
    popq rbp
    retq
_ItopDownMerge_pt5aiiiiai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 1288
_l64:
    mov r10, [ rbp + -712 ]
    mov r10, rdi ## (MOVE (TEMP a) (TEMP _ARG0))
    mov [ rbp + -712 ], r10
    mov r10, [ rbp + -264 ]
    mov r10, rsi ## (MOVE (TEMP iBegin) (TEMP _ARG1))
    mov [ rbp + -264 ], r10
    mov r10, [ rbp + -904 ]
    mov r10, rdx ## (MOVE (TEMP iMiddle) (TEMP _ARG2))
    mov [ rbp + -904 ], r10
    mov r10, [ rbp + -1040 ]
    mov r10, rcx ## (MOVE (TEMP iEnd) (TEMP _ARG3))
    mov [ rbp + -1040 ], r10
    mov r10, [ rbp + -720 ]
    mov r10, r8 ## (MOVE (TEMP b) (TEMP _ARG4))
    mov [ rbp + -720 ], r10
    mov r10, [ rbp + -728 ]
    mov r11, [ rbp + -264 ]
    mov r10, r11 ## (MOVE (TEMP i) (TEMP iBegin))
    mov [ rbp + -264 ], r11
    mov [ rbp + -728 ], r10
    mov r10, [ rbp + -744 ]
    mov r11, [ rbp + -904 ]
    mov r10, r11 ## (MOVE (TEMP j) (TEMP iMiddle))
    mov [ rbp + -904 ], r11
    mov [ rbp + -744 ], r10
    mov r10, [ rbp + -760 ]
    mov r11, [ rbp + -264 ]
    mov r10, r11 ## (MOVE (TEMP k) (TEMP iBegin))
    mov [ rbp + -264 ], r11
    mov [ rbp + -760 ], r10
_l32:
    mov r10, [ rbp + -608 ]
    mov r11, [ rbp + -760 ]
    mov r10, r11 ## (MOVE (TEMP _t234) (TEMP k))
    mov [ rbp + -760 ], r11
    mov [ rbp + -608 ], r10
    mov r10, [ rbp + -608 ]
    mov r11, [ rbp + -1040 ]
    cmpq r10, r11 ## (LT (TEMP _t234) (TEMP iEnd))
    mov [ rbp + -1040 ], r11
    mov [ rbp + -608 ], r10
    pushq rax
    mov rax, [ rbp + -24 ]
    setl al ## (LT (TEMP _t234) (TEMP iEnd))
    mov [ rbp + -24 ], rax
    popq rax
    mov r10, [ rbp + -8 ]
    pushq rax
    mov rax, [ rbp + -24 ]
    movzx r10, al ## (LT (TEMP _t234) (TEMP iEnd))
    mov [ rbp + -24 ], rax
    popq rax
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -8 ]
    cmpq r10, 1 ## (CJUMP (LT (TEMP _t234) (TEMP iEnd)) _l33)
    mov [ rbp + -8 ], r10
    je _l33 ## (CJUMP (LT (TEMP _t234) (TEMP iEnd)) _l33)
_l34:
    jmp end__ItopDownMerge_pt5aiiiiai ## (RETURN ())
_l33:
    mov r10, [ rbp + -616 ]
    mov r11, [ rbp + -728 ]
    mov r10, r11 ## (MOVE (TEMP _t235) (TEMP i))
    mov [ rbp + -728 ], r11
    mov [ rbp + -616 ], r10
    mov r10, [ rbp + -616 ]
    mov r11, [ rbp + -904 ]
    cmpq r10, r11 ## (LT (TEMP _t235) (TEMP iMiddle))
    mov [ rbp + -904 ], r11
    mov [ rbp + -616 ], r10
    pushq rax
    mov rax, [ rbp + -56 ]
    setl al ## (LT (TEMP _t235) (TEMP iMiddle))
    mov [ rbp + -56 ], rax
    popq rax
    mov r10, [ rbp + -40 ]
    pushq rax
    mov rax, [ rbp + -56 ]
    movzx r10, al ## (LT (TEMP _t235) (TEMP iMiddle))
    mov [ rbp + -56 ], rax
    popq rax
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -40 ]
    cmpq r10, 1 ## (CJUMP (LT (TEMP _t235) (TEMP iMiddle)) _l38)
    mov [ rbp + -40 ], r10
    je _l38 ## (CJUMP (LT (TEMP _t235) (TEMP iMiddle)) _l38)
_l36:
    mov r10, [ rbp + -664 ]
    mov r11, [ rbp + -760 ]
    mov r10, r11 ## (MOVE (TEMP _t46) (TEMP k))
    mov [ rbp + -760 ], r11
    mov [ rbp + -664 ], r10
    mov r10, [ rbp + -656 ]
    mov r11, [ rbp + -720 ]
    mov r10, r11 ## (MOVE (TEMP _t45) (TEMP b))
    mov [ rbp + -720 ], r11
    mov [ rbp + -656 ], r10
    mov r10, [ rbp + -288 ]
    mov r11, [ rbp + -656 ]
    mov r10, r11 ## (MOVE (TEMP _t252) (TEMP _t45))
    mov [ rbp + -656 ], r11
    mov [ rbp + -288 ], r10
    mov r10, [ rbp + -72 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -288 ]
    mov r10, r11 ## (SUB (TEMP _t252) (CONST 8))
    mov [ rbp + -288 ], r11
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -72 ]
    subq r10, r11 ## (SUB (TEMP _t252) (CONST 8))
    mov [ rbp + -72 ], r11
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -672 ]
    mov r11, [ rbp + -88 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t47) (MEM (SUB (TEMP _t252) (CONST 8))))
    mov [ rbp + -88 ], r11
    mov [ rbp + -672 ], r10
    mov r10, [ rbp + -104 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -296 ]
    mov r11, [ rbp + -104 ]
    mov r10, r11 ## (MOVE (TEMP _t253) (CONST 0))
    mov [ rbp + -104 ], r11
    mov [ rbp + -296 ], r10
    mov r10, [ rbp + -296 ]
    mov r11, [ rbp + -664 ]
    cmpq r10, r11 ## (LEQ (TEMP _t253) (TEMP _t46))
    mov [ rbp + -664 ], r11
    mov [ rbp + -296 ], r10
    pushq rax
    mov rax, [ rbp + -152 ]
    setle al ## (LEQ (TEMP _t253) (TEMP _t46))
    mov [ rbp + -152 ], rax
    popq rax
    mov r10, [ rbp + -128 ]
    pushq rax
    mov rax, [ rbp + -152 ]
    movzx r10, al ## (LEQ (TEMP _t253) (TEMP _t46))
    mov [ rbp + -152 ], rax
    popq rax
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -312 ]
    mov r11, [ rbp + -128 ]
    mov r10, r11 ## (MOVE (TEMP _t255) (LEQ (TEMP _t253) (TEMP _t46)))
    mov [ rbp + -128 ], r11
    mov [ rbp + -312 ], r10
    mov r10, [ rbp + -304 ]
    mov r11, [ rbp + -664 ]
    mov r10, r11 ## (MOVE (TEMP _t254) (TEMP _t46))
    mov [ rbp + -664 ], r11
    mov [ rbp + -304 ], r10
    mov r10, [ rbp + -304 ]
    mov r11, [ rbp + -672 ]
    cmpq r10, r11 ## (LT (TEMP _t254) (TEMP _t47))
    mov [ rbp + -672 ], r11
    mov [ rbp + -304 ], r10
    pushq rax
    mov rax, [ rbp + -1056 ]
    setl al ## (LT (TEMP _t254) (TEMP _t47))
    mov [ rbp + -1056 ], rax
    popq rax
    mov r10, [ rbp + -1048 ]
    pushq rax
    mov rax, [ rbp + -1056 ]
    movzx r10, al ## (LT (TEMP _t254) (TEMP _t47))
    mov [ rbp + -1056 ], rax
    popq rax
    mov [ rbp + -1048 ], r10
    mov r10, [ rbp + -1072 ]
    mov r11, [ rbp + -312 ]
    mov r10, r11 ## (AND (TEMP _t255) (LT (TEMP _t254) (TEMP _t47)))
    mov [ rbp + -312 ], r11
    mov [ rbp + -1072 ], r10
    mov r10, [ rbp + -1072 ]
    mov r11, [ rbp + -1048 ]
    andq r10, r11 ## (AND (TEMP _t255) (LT (TEMP _t254) (TEMP _t47)))
    mov [ rbp + -1048 ], r11
    mov [ rbp + -1072 ], r10
    mov r10, [ rbp + -1072 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t255) (LT (TEMP _t254) (TEMP _t47))) _l44)
    mov [ rbp + -1072 ], r10
    je _l44 ## (CJUMP (AND (TEMP _t255) (LT (TEMP _t254) (TEMP _t47))) _l44)
_l45:
    callq _xi_out_of_bounds ## (CALL_STMT _t256 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -320 ]
    mov r10, rax ## (CALL_STMT _t256 (NAME _xi_out_of_bounds))
    mov [ rbp + -320 ], r10
_l44:
    mov r10, [ rbp + -352 ]
    mov r11, [ rbp + -656 ]
    mov r10, r11 ## (MOVE (TEMP _t258) (TEMP _t45))
    mov [ rbp + -656 ], r11
    mov [ rbp + -352 ], r10
    mov r10, [ rbp + -336 ]
    mov r11, [ rbp + -664 ]
    mov r10, r11 ## (MOVE (TEMP _t257) (TEMP _t46))
    mov [ rbp + -664 ], r11
    mov [ rbp + -336 ], r10
    mov r10, [ rbp + -1088 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -1088 ], r10
    mov r10, [ rbp + -1104 ]
    mov r11, [ rbp + -336 ]
    mov r10, r11 ## (MUL (TEMP _t257) (CONST 8))
    mov [ rbp + -336 ], r11
    mov [ rbp + -1104 ], r10
    mov r10, [ rbp + -1104 ]
    mov r11, [ rbp + -1088 ]
    imulq r10, r11 ## (MUL (TEMP _t257) (CONST 8))
    mov [ rbp + -1088 ], r11
    mov [ rbp + -1104 ], r10
    mov r10, [ rbp + -1120 ]
    mov r11, [ rbp + -352 ]
    mov r10, r11 ## (ADD (TEMP _t258) (MUL (TEMP _t257) (CONST 8)))
    mov [ rbp + -352 ], r11
    mov [ rbp + -1120 ], r10
    mov r10, [ rbp + -1120 ]
    mov r11, [ rbp + -1104 ]
    addq r10, r11 ## (ADD (TEMP _t258) (MUL (TEMP _t257) (CONST 8)))
    mov [ rbp + -1104 ], r11
    mov [ rbp + -1120 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -1120 ]
    mov r10, r11 ## (MOVE (TEMP _t266) (ADD (TEMP _t258) (MUL (TEMP _t257) (CONST 8))))
    mov [ rbp + -1120 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -640 ]
    mov r11, [ rbp + -744 ]
    mov r10, r11 ## (MOVE (TEMP _t49) (TEMP j))
    mov [ rbp + -744 ], r11
    mov [ rbp + -640 ], r10
    mov r10, [ rbp + -632 ]
    mov r11, [ rbp + -712 ]
    mov r10, r11 ## (MOVE (TEMP _t48) (TEMP a))
    mov [ rbp + -712 ], r11
    mov [ rbp + -632 ], r10
    mov r10, [ rbp + -192 ]
    mov r11, [ rbp + -632 ]
    mov r10, r11 ## (MOVE (TEMP _t259) (TEMP _t48))
    mov [ rbp + -632 ], r11
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -1136 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -1136 ], r10
    mov r10, [ rbp + -1152 ]
    mov r11, [ rbp + -192 ]
    mov r10, r11 ## (SUB (TEMP _t259) (CONST 8))
    mov [ rbp + -192 ], r11
    mov [ rbp + -1152 ], r10
    mov r10, [ rbp + -1152 ]
    mov r11, [ rbp + -1136 ]
    subq r10, r11 ## (SUB (TEMP _t259) (CONST 8))
    mov [ rbp + -1136 ], r11
    mov [ rbp + -1152 ], r10
    mov r10, [ rbp + -448 ]
    mov r11, [ rbp + -1152 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t50) (MEM (SUB (TEMP _t259) (CONST 8))))
    mov [ rbp + -1152 ], r11
    mov [ rbp + -448 ], r10
    mov r10, [ rbp + -1176 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -1176 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -1176 ]
    mov r10, r11 ## (MOVE (TEMP _t260) (CONST 0))
    mov [ rbp + -1176 ], r11
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -640 ]
    cmpq r10, r11 ## (LEQ (TEMP _t260) (TEMP _t49))
    mov [ rbp + -640 ], r11
    mov [ rbp + -16 ], r10
    pushq rax
    mov rax, [ rbp + -504 ]
    setle al ## (LEQ (TEMP _t260) (TEMP _t49))
    mov [ rbp + -504 ], rax
    popq rax
    mov r10, [ rbp + -1200 ]
    pushq rax
    mov rax, [ rbp + -504 ]
    movzx r10, al ## (LEQ (TEMP _t260) (TEMP _t49))
    mov [ rbp + -504 ], rax
    popq rax
    mov [ rbp + -1200 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -1200 ]
    mov r10, r11 ## (MOVE (TEMP _t262) (LEQ (TEMP _t260) (TEMP _t49)))
    mov [ rbp + -1200 ], r11
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -640 ]
    mov r10, r11 ## (MOVE (TEMP _t261) (TEMP _t49))
    mov [ rbp + -640 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -448 ]
    cmpq r10, r11 ## (LT (TEMP _t261) (TEMP _t50))
    mov [ rbp + -448 ], r11
    mov [ rbp + -32 ], r10
    pushq rax
    mov rax, [ rbp + -368 ]
    setl al ## (LT (TEMP _t261) (TEMP _t50))
    mov [ rbp + -368 ], rax
    popq rax
    mov r10, [ rbp + -520 ]
    pushq rax
    mov rax, [ rbp + -368 ]
    movzx r10, al ## (LT (TEMP _t261) (TEMP _t50))
    mov [ rbp + -368 ], rax
    popq rax
    mov [ rbp + -520 ], r10
    mov r10, [ rbp + -384 ]
    mov r11, [ rbp + -48 ]
    mov r10, r11 ## (AND (TEMP _t262) (LT (TEMP _t261) (TEMP _t50)))
    mov [ rbp + -48 ], r11
    mov [ rbp + -384 ], r10
    mov r10, [ rbp + -384 ]
    mov r11, [ rbp + -520 ]
    andq r10, r11 ## (AND (TEMP _t262) (LT (TEMP _t261) (TEMP _t50)))
    mov [ rbp + -520 ], r11
    mov [ rbp + -384 ], r10
    mov r10, [ rbp + -384 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t262) (LT (TEMP _t261) (TEMP _t50))) _l46)
    mov [ rbp + -384 ], r10
    je _l46 ## (CJUMP (AND (TEMP _t262) (LT (TEMP _t261) (TEMP _t50))) _l46)
_l47:
    callq _xi_out_of_bounds ## (CALL_STMT _t263 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -64 ]
    mov r10, rax ## (CALL_STMT _t263 (NAME _xi_out_of_bounds))
    mov [ rbp + -64 ], r10
_l46:
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -632 ]
    mov r10, r11 ## (MOVE (TEMP _t265) (TEMP _t48))
    mov [ rbp + -632 ], r11
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -640 ]
    mov r10, r11 ## (MOVE (TEMP _t264) (TEMP _t49))
    mov [ rbp + -640 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -400 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -400 ], r10
    mov r10, [ rbp + -408 ]
    mov r11, [ rbp + -80 ]
    mov r10, r11 ## (MUL (TEMP _t264) (CONST 8))
    mov [ rbp + -80 ], r11
    mov [ rbp + -408 ], r10
    mov r10, [ rbp + -408 ]
    mov r11, [ rbp + -400 ]
    imulq r10, r11 ## (MUL (TEMP _t264) (CONST 8))
    mov [ rbp + -400 ], r11
    mov [ rbp + -408 ], r10
    mov r10, [ rbp + -416 ]
    mov r11, [ rbp + -96 ]
    mov r10, r11 ## (ADD (TEMP _t265) (MUL (TEMP _t264) (CONST 8)))
    mov [ rbp + -96 ], r11
    mov [ rbp + -416 ], r10
    mov r10, [ rbp + -416 ]
    mov r11, [ rbp + -408 ]
    addq r10, r11 ## (ADD (TEMP _t265) (MUL (TEMP _t264) (CONST 8)))
    mov [ rbp + -408 ], r11
    mov [ rbp + -416 ], r10
    mov r10, [ rbp + -424 ]
    mov r11, [ rbp + -416 ]
    mov r10, [ r11 ] ## (MOVE (MEM (TEMP _t266)) (MEM (ADD (TEMP _t265) (MUL (TEMP _t264) (CONST 8)))))
    mov [ rbp + -416 ], r11
    mov [ rbp + -424 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -424 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t266)) (MEM (ADD (TEMP _t265) (MUL (TEMP _t264) (CONST 8)))))
    mov [ rbp + -424 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -744 ]
    mov r10, r11 ## (MOVE (TEMP _t267) (TEMP j))
    mov [ rbp + -744 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -432 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -432 ], r10
    mov r10, [ rbp + -440 ]
    mov r11, [ rbp + -136 ]
    mov r10, r11 ## (ADD (TEMP _t267) (CONST 1))
    mov [ rbp + -136 ], r11
    mov [ rbp + -440 ], r10
    mov r10, [ rbp + -440 ]
    mov r11, [ rbp + -432 ]
    addq r10, r11 ## (ADD (TEMP _t267) (CONST 1))
    mov [ rbp + -432 ], r11
    mov [ rbp + -440 ], r10
    mov r10, [ rbp + -744 ]
    mov r11, [ rbp + -440 ]
    mov r10, r11 ## (MOVE (TEMP j) (ADD (TEMP _t267) (CONST 1)))
    mov [ rbp + -440 ], r11
    mov [ rbp + -744 ], r10
_l37:
    mov r10, [ rbp + -944 ]
    mov r11, [ rbp + -760 ]
    mov r10, r11 ## (MOVE (TEMP _t284) (TEMP k))
    mov [ rbp + -760 ], r11
    mov [ rbp + -944 ], r10
    mov r10, [ rbp + -328 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -328 ], r10
    mov r10, [ rbp + -344 ]
    mov r11, [ rbp + -944 ]
    mov r10, r11 ## (ADD (TEMP _t284) (CONST 1))
    mov [ rbp + -944 ], r11
    mov [ rbp + -344 ], r10
    mov r10, [ rbp + -344 ]
    mov r11, [ rbp + -328 ]
    addq r10, r11 ## (ADD (TEMP _t284) (CONST 1))
    mov [ rbp + -328 ], r11
    mov [ rbp + -344 ], r10
    mov r10, [ rbp + -760 ]
    mov r11, [ rbp + -344 ]
    mov r10, r11 ## (MOVE (TEMP k) (ADD (TEMP _t284) (CONST 1)))
    mov [ rbp + -344 ], r11
    mov [ rbp + -760 ], r10
    jmp _l32 ## (JUMP (NAME _l32))
_l38:
    mov r10, [ rbp + -624 ]
    mov r11, [ rbp + -744 ]
    mov r10, r11 ## (MOVE (TEMP _t236) (TEMP j))
    mov [ rbp + -744 ], r11
    mov [ rbp + -624 ], r10
    mov r10, [ rbp + -624 ]
    mov r11, [ rbp + -1040 ]
    cmpq r10, r11 ## (GEQ (TEMP _t236) (TEMP iEnd))
    mov [ rbp + -1040 ], r11
    mov [ rbp + -624 ], r10
    pushq rax
    mov rax, [ rbp + -200 ]
    setge al ## (GEQ (TEMP _t236) (TEMP iEnd))
    mov [ rbp + -200 ], rax
    popq rax
    mov r10, [ rbp + -360 ]
    pushq rax
    mov rax, [ rbp + -200 ]
    movzx r10, al ## (GEQ (TEMP _t236) (TEMP iEnd))
    mov [ rbp + -200 ], rax
    popq rax
    mov [ rbp + -360 ], r10
    mov r10, [ rbp + -360 ]
    cmpq r10, 1 ## (CJUMP (GEQ (TEMP _t236) (TEMP iEnd)) _l35)
    mov [ rbp + -360 ], r10
    je _l35 ## (CJUMP (GEQ (TEMP _t236) (TEMP iEnd)) _l35)
_l39:
    mov r10, [ rbp + -680 ]
    mov r11, [ rbp + -728 ]
    mov r10, r11 ## (MOVE (TEMP _t40) (TEMP i))
    mov [ rbp + -728 ], r11
    mov [ rbp + -680 ], r10
    mov r10, [ rbp + -248 ]
    mov r11, [ rbp + -712 ]
    mov r10, r11 ## (MOVE (TEMP _t39) (TEMP a))
    mov [ rbp + -712 ], r11
    mov [ rbp + -248 ], r10
    mov r10, [ rbp + -536 ]
    mov r11, [ rbp + -248 ]
    mov r10, r11 ## (MOVE (TEMP _t237) (TEMP _t39))
    mov [ rbp + -248 ], r11
    mov [ rbp + -536 ], r10
    mov r10, [ rbp + -208 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -216 ]
    mov r11, [ rbp + -536 ]
    mov r10, r11 ## (SUB (TEMP _t237) (CONST 8))
    mov [ rbp + -536 ], r11
    mov [ rbp + -216 ], r10
    mov r10, [ rbp + -216 ]
    mov r11, [ rbp + -208 ]
    subq r10, r11 ## (SUB (TEMP _t237) (CONST 8))
    mov [ rbp + -208 ], r11
    mov [ rbp + -216 ], r10
    mov r10, [ rbp + -688 ]
    mov r11, [ rbp + -216 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t41) (MEM (SUB (TEMP _t237) (CONST 8))))
    mov [ rbp + -216 ], r11
    mov [ rbp + -688 ], r10
    mov r10, [ rbp + -224 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -544 ]
    mov r11, [ rbp + -224 ]
    mov r10, r11 ## (MOVE (TEMP _t238) (CONST 0))
    mov [ rbp + -224 ], r11
    mov [ rbp + -544 ], r10
    mov r10, [ rbp + -544 ]
    mov r11, [ rbp + -680 ]
    cmpq r10, r11 ## (LEQ (TEMP _t238) (TEMP _t40))
    mov [ rbp + -680 ], r11
    mov [ rbp + -544 ], r10
    pushq rax
    mov rax, [ rbp + -240 ]
    setle al ## (LEQ (TEMP _t238) (TEMP _t40))
    mov [ rbp + -240 ], rax
    popq rax
    mov r10, [ rbp + -232 ]
    pushq rax
    mov rax, [ rbp + -240 ]
    movzx r10, al ## (LEQ (TEMP _t238) (TEMP _t40))
    mov [ rbp + -240 ], rax
    popq rax
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -456 ]
    mov r11, [ rbp + -232 ]
    mov r10, r11 ## (MOVE (TEMP _t240) (LEQ (TEMP _t238) (TEMP _t40)))
    mov [ rbp + -232 ], r11
    mov [ rbp + -456 ], r10
    mov r10, [ rbp + -552 ]
    mov r11, [ rbp + -680 ]
    mov r10, r11 ## (MOVE (TEMP _t239) (TEMP _t40))
    mov [ rbp + -680 ], r11
    mov [ rbp + -552 ], r10
    mov r10, [ rbp + -552 ]
    mov r11, [ rbp + -688 ]
    cmpq r10, r11 ## (LT (TEMP _t239) (TEMP _t41))
    mov [ rbp + -688 ], r11
    mov [ rbp + -552 ], r10
    pushq rax
    mov rax, [ rbp + -120 ]
    setl al ## (LT (TEMP _t239) (TEMP _t41))
    mov [ rbp + -120 ], rax
    popq rax
    mov r10, [ rbp + -256 ]
    pushq rax
    mov rax, [ rbp + -120 ]
    movzx r10, al ## (LT (TEMP _t239) (TEMP _t41))
    mov [ rbp + -120 ], rax
    popq rax
    mov [ rbp + -256 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -456 ]
    mov r10, r11 ## (AND (TEMP _t240) (LT (TEMP _t239) (TEMP _t41)))
    mov [ rbp + -456 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -256 ]
    andq r10, r11 ## (AND (TEMP _t240) (LT (TEMP _t239) (TEMP _t41)))
    mov [ rbp + -256 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -144 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t240) (LT (TEMP _t239) (TEMP _t41))) _l40)
    mov [ rbp + -144 ], r10
    je _l40 ## (CJUMP (AND (TEMP _t240) (LT (TEMP _t239) (TEMP _t41))) _l40)
_l41:
    callq _xi_out_of_bounds ## (CALL_STMT _t241 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -464 ]
    mov r10, rax ## (CALL_STMT _t241 (NAME _xi_out_of_bounds))
    mov [ rbp + -464 ], r10
_l40:
    mov r10, [ rbp + -480 ]
    mov r11, [ rbp + -248 ]
    mov r10, r11 ## (MOVE (TEMP _t243) (TEMP _t39))
    mov [ rbp + -248 ], r11
    mov [ rbp + -480 ], r10
    mov r10, [ rbp + -472 ]
    mov r11, [ rbp + -680 ]
    mov r10, r11 ## (MOVE (TEMP _t242) (TEMP _t40))
    mov [ rbp + -680 ], r11
    mov [ rbp + -472 ], r10
    mov r10, [ rbp + -160 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -472 ]
    mov r10, r11 ## (MUL (TEMP _t242) (CONST 8))
    mov [ rbp + -472 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -160 ]
    imulq r10, r11 ## (MUL (TEMP _t242) (CONST 8))
    mov [ rbp + -160 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -1248 ]
    mov r11, [ rbp + -480 ]
    mov r10, r11 ## (ADD (TEMP _t243) (MUL (TEMP _t242) (CONST 8)))
    mov [ rbp + -480 ], r11
    mov [ rbp + -1248 ], r10
    mov r10, [ rbp + -1248 ]
    mov r11, [ rbp + -176 ]
    addq r10, r11 ## (ADD (TEMP _t243) (MUL (TEMP _t242) (CONST 8)))
    mov [ rbp + -176 ], r11
    mov [ rbp + -1248 ], r10
    mov r10, [ rbp + -280 ]
    mov r11, [ rbp + -1248 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t251) (MEM (ADD (TEMP _t243) (MUL (TEMP _t242) (CONST 8)))))
    mov [ rbp + -1248 ], r11
    mov [ rbp + -280 ], r10
    mov r10, [ rbp + -704 ]
    mov r11, [ rbp + -744 ]
    mov r10, r11 ## (MOVE (TEMP _t43) (TEMP j))
    mov [ rbp + -744 ], r11
    mov [ rbp + -704 ], r10
    mov r10, [ rbp + -696 ]
    mov r11, [ rbp + -712 ]
    mov r10, r11 ## (MOVE (TEMP _t42) (TEMP a))
    mov [ rbp + -712 ], r11
    mov [ rbp + -696 ], r10
    mov r10, [ rbp + -488 ]
    mov r11, [ rbp + -696 ]
    mov r10, r11 ## (MOVE (TEMP _t244) (TEMP _t42))
    mov [ rbp + -696 ], r11
    mov [ rbp + -488 ], r10
    mov r10, [ rbp + -1256 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -1256 ], r10
    mov r10, [ rbp + -1264 ]
    mov r11, [ rbp + -488 ]
    mov r10, r11 ## (SUB (TEMP _t244) (CONST 8))
    mov [ rbp + -488 ], r11
    mov [ rbp + -1264 ], r10
    mov r10, [ rbp + -1264 ]
    mov r11, [ rbp + -1256 ]
    subq r10, r11 ## (SUB (TEMP _t244) (CONST 8))
    mov [ rbp + -1256 ], r11
    mov [ rbp + -1264 ], r10
    mov r10, [ rbp + -648 ]
    mov r11, [ rbp + -1264 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t44) (MEM (SUB (TEMP _t244) (CONST 8))))
    mov [ rbp + -1264 ], r11
    mov [ rbp + -648 ], r10
    mov r10, [ rbp + -1272 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -1272 ], r10
    mov r10, [ rbp + -496 ]
    mov r11, [ rbp + -1272 ]
    mov r10, r11 ## (MOVE (TEMP _t245) (CONST 0))
    mov [ rbp + -1272 ], r11
    mov [ rbp + -496 ], r10
    mov r10, [ rbp + -496 ]
    mov r11, [ rbp + -704 ]
    cmpq r10, r11 ## (LEQ (TEMP _t245) (TEMP _t43))
    mov [ rbp + -704 ], r11
    mov [ rbp + -496 ], r10
    pushq rax
    mov rax, [ rbp + -1288 ]
    setle al ## (LEQ (TEMP _t245) (TEMP _t43))
    mov [ rbp + -1288 ], rax
    popq rax
    mov r10, [ rbp + -1280 ]
    pushq rax
    mov rax, [ rbp + -1288 ]
    movzx r10, al ## (LEQ (TEMP _t245) (TEMP _t43))
    mov [ rbp + -1288 ], rax
    popq rax
    mov [ rbp + -1280 ], r10
    mov r10, [ rbp + -528 ]
    mov r11, [ rbp + -1280 ]
    mov r10, r11 ## (MOVE (TEMP _t247) (LEQ (TEMP _t245) (TEMP _t43)))
    mov [ rbp + -1280 ], r11
    mov [ rbp + -528 ], r10
    mov r10, [ rbp + -512 ]
    mov r11, [ rbp + -704 ]
    mov r10, r11 ## (MOVE (TEMP _t246) (TEMP _t43))
    mov [ rbp + -704 ], r11
    mov [ rbp + -512 ], r10
    mov r10, [ rbp + -512 ]
    mov r11, [ rbp + -648 ]
    cmpq r10, r11 ## (LT (TEMP _t246) (TEMP _t44))
    mov [ rbp + -648 ], r11
    mov [ rbp + -512 ], r10
    pushq rax
    mov rax, [ rbp + -1184 ]
    setl al ## (LT (TEMP _t246) (TEMP _t44))
    mov [ rbp + -1184 ], rax
    popq rax
    mov r10, [ rbp + -1160 ]
    pushq rax
    mov rax, [ rbp + -1184 ]
    movzx r10, al ## (LT (TEMP _t246) (TEMP _t44))
    mov [ rbp + -1184 ], rax
    popq rax
    mov [ rbp + -1160 ], r10
    mov r10, [ rbp + -1208 ]
    mov r11, [ rbp + -528 ]
    mov r10, r11 ## (AND (TEMP _t247) (LT (TEMP _t246) (TEMP _t44)))
    mov [ rbp + -528 ], r11
    mov [ rbp + -1208 ], r10
    mov r10, [ rbp + -1208 ]
    mov r11, [ rbp + -1160 ]
    andq r10, r11 ## (AND (TEMP _t247) (LT (TEMP _t246) (TEMP _t44)))
    mov [ rbp + -1160 ], r11
    mov [ rbp + -1208 ], r10
    mov r10, [ rbp + -1208 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t247) (LT (TEMP _t246) (TEMP _t44))) _l42)
    mov [ rbp + -1208 ], r10
    je _l42 ## (CJUMP (AND (TEMP _t247) (LT (TEMP _t246) (TEMP _t44))) _l42)
_l43:
    callq _xi_out_of_bounds ## (CALL_STMT _t248 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -376 ]
    mov r10, rax ## (CALL_STMT _t248 (NAME _xi_out_of_bounds))
    mov [ rbp + -376 ], r10
_l42:
    mov r10, [ rbp + -272 ]
    mov r11, [ rbp + -696 ]
    mov r10, r11 ## (MOVE (TEMP _t250) (TEMP _t42))
    mov [ rbp + -696 ], r11
    mov [ rbp + -272 ], r10
    mov r10, [ rbp + -392 ]
    mov r11, [ rbp + -704 ]
    mov r10, r11 ## (MOVE (TEMP _t249) (TEMP _t43))
    mov [ rbp + -704 ], r11
    mov [ rbp + -392 ], r10
    mov r10, [ rbp + -1224 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -1224 ], r10
    mov r10, [ rbp + -1240 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -1240 ], r10
    mov r10, [ rbp + -1000 ]
    mov r11, [ rbp + -392 ]
    mov r10, r11 ## (MUL (TEMP _t249) (CONST 8))
    mov [ rbp + -392 ], r11
    mov [ rbp + -1000 ], r10
    mov r10, [ rbp + -1000 ]
    mov r11, [ rbp + -1240 ]
    imulq r10, r11 ## (MUL (TEMP _t249) (CONST 8))
    mov [ rbp + -1240 ], r11
    mov [ rbp + -1000 ], r10
    mov r10, [ rbp + -1008 ]
    mov r11, [ rbp + -272 ]
    mov r10, r11 ## (ADD (TEMP _t250) (MUL (TEMP _t249) (CONST 8)))
    mov [ rbp + -272 ], r11
    mov [ rbp + -1008 ], r10
    mov r10, [ rbp + -1008 ]
    mov r11, [ rbp + -1000 ]
    addq r10, r11 ## (ADD (TEMP _t250) (MUL (TEMP _t249) (CONST 8)))
    mov [ rbp + -1000 ], r11
    mov [ rbp + -1008 ], r10
    mov r10, [ rbp + -280 ]
    mov r11, [ rbp + -1008 ]
    cmpq r10, [ r11 ] ## (GEQ (TEMP _t251) (MEM (ADD (TEMP _t250) (MUL (TEMP _t249) (CONST 8)))))
    mov [ rbp + -1008 ], r11
    mov [ rbp + -280 ], r10
    pushq rax
    mov rax, [ rbp + -1024 ]
    setge al ## (GEQ (TEMP _t251) (MEM (ADD (TEMP _t250) (MUL (TEMP _t249) (CONST 8)))))
    mov [ rbp + -1024 ], rax
    popq rax
    mov r10, [ rbp + -1016 ]
    pushq rax
    mov rax, [ rbp + -1024 ]
    movzx r10, al ## (GEQ (TEMP _t251) (MEM (ADD (TEMP _t250) (MUL (TEMP _t249) (CONST 8)))))
    mov [ rbp + -1024 ], rax
    popq rax
    mov [ rbp + -1016 ], r10
    mov r10, [ rbp + -1032 ]
    mov r11, [ rbp + -1224 ]
    mov r10, r11 ## (XOR (CONST 1) (GEQ (TEMP _t251) (MEM (ADD (TEMP _t250) (MUL (TEMP _t249) (CONST 8))))))
    mov [ rbp + -1224 ], r11
    mov [ rbp + -1032 ], r10
    mov r10, [ rbp + -1032 ]
    mov r11, [ rbp + -1016 ]
    xorq r10, r11 ## (XOR (CONST 1) (GEQ (TEMP _t251) (MEM (ADD (TEMP _t250) (MUL (TEMP _t249) (CONST 8))))))
    mov [ rbp + -1016 ], r11
    mov [ rbp + -1032 ], r10
    mov r10, [ rbp + -1032 ]
    cmpq r10, 1 ## (CJUMP (XOR (CONST 1)  (GEQ (TEMP _t251) (MEM (ADD (TEMP _t250) (MUL (TEMP _t249) (CONST 8)))))) _l36)
    mov [ rbp + -1032 ], r10
    je _l36 ## (CJUMP (XOR (CONST 1)  (GEQ (TEMP _t251) (MEM (ADD (TEMP _t250) (MUL (TEMP _t249) (CONST 8)))))) _l36)
_l35:
    mov r10, [ rbp + -584 ]
    mov r11, [ rbp + -760 ]
    mov r10, r11 ## (MOVE (TEMP _t52) (TEMP k))
    mov [ rbp + -760 ], r11
    mov [ rbp + -584 ], r10
    mov r10, [ rbp + -576 ]
    mov r11, [ rbp + -720 ]
    mov r10, r11 ## (MOVE (TEMP _t51) (TEMP b))
    mov [ rbp + -720 ], r11
    mov [ rbp + -576 ], r10
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -576 ]
    mov r10, r11 ## (MOVE (TEMP _t268) (TEMP _t51))
    mov [ rbp + -576 ], r11
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -952 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -952 ], r10
    mov r10, [ rbp + -960 ]
    mov r11, [ rbp + -168 ]
    mov r10, r11 ## (SUB (TEMP _t268) (CONST 8))
    mov [ rbp + -168 ], r11
    mov [ rbp + -960 ], r10
    mov r10, [ rbp + -960 ]
    mov r11, [ rbp + -952 ]
    subq r10, r11 ## (SUB (TEMP _t268) (CONST 8))
    mov [ rbp + -952 ], r11
    mov [ rbp + -960 ], r10
    mov r10, [ rbp + -592 ]
    mov r11, [ rbp + -960 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t53) (MEM (SUB (TEMP _t268) (CONST 8))))
    mov [ rbp + -960 ], r11
    mov [ rbp + -592 ], r10
    mov r10, [ rbp + -968 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -968 ], r10
    mov r10, [ rbp + -184 ]
    mov r11, [ rbp + -968 ]
    mov r10, r11 ## (MOVE (TEMP _t269) (CONST 0))
    mov [ rbp + -968 ], r11
    mov [ rbp + -184 ], r10
    mov r10, [ rbp + -184 ]
    mov r11, [ rbp + -584 ]
    cmpq r10, r11 ## (LEQ (TEMP _t269) (TEMP _t52))
    mov [ rbp + -584 ], r11
    mov [ rbp + -184 ], r10
    pushq rax
    mov rax, [ rbp + -984 ]
    setle al ## (LEQ (TEMP _t269) (TEMP _t52))
    mov [ rbp + -984 ], rax
    popq rax
    mov r10, [ rbp + -976 ]
    pushq rax
    mov rax, [ rbp + -984 ]
    movzx r10, al ## (LEQ (TEMP _t269) (TEMP _t52))
    mov [ rbp + -984 ], rax
    popq rax
    mov [ rbp + -976 ], r10
    mov r10, [ rbp + -1080 ]
    mov r11, [ rbp + -976 ]
    mov r10, r11 ## (MOVE (TEMP _t271) (LEQ (TEMP _t269) (TEMP _t52)))
    mov [ rbp + -976 ], r11
    mov [ rbp + -1080 ], r10
    mov r10, [ rbp + -1064 ]
    mov r11, [ rbp + -584 ]
    mov r10, r11 ## (MOVE (TEMP _t270) (TEMP _t52))
    mov [ rbp + -584 ], r11
    mov [ rbp + -1064 ], r10
    mov r10, [ rbp + -1064 ]
    mov r11, [ rbp + -592 ]
    cmpq r10, r11 ## (LT (TEMP _t270) (TEMP _t53))
    mov [ rbp + -592 ], r11
    mov [ rbp + -1064 ], r10
    pushq rax
    mov rax, [ rbp + -872 ]
    setl al ## (LT (TEMP _t270) (TEMP _t53))
    mov [ rbp + -872 ], rax
    popq rax
    mov r10, [ rbp + -992 ]
    pushq rax
    mov rax, [ rbp + -872 ]
    movzx r10, al ## (LT (TEMP _t270) (TEMP _t53))
    mov [ rbp + -872 ], rax
    popq rax
    mov [ rbp + -992 ], r10
    mov r10, [ rbp + -880 ]
    mov r11, [ rbp + -1080 ]
    mov r10, r11 ## (AND (TEMP _t271) (LT (TEMP _t270) (TEMP _t53)))
    mov [ rbp + -1080 ], r11
    mov [ rbp + -880 ], r10
    mov r10, [ rbp + -880 ]
    mov r11, [ rbp + -992 ]
    andq r10, r11 ## (AND (TEMP _t271) (LT (TEMP _t270) (TEMP _t53)))
    mov [ rbp + -992 ], r11
    mov [ rbp + -880 ], r10
    mov r10, [ rbp + -880 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t271) (LT (TEMP _t270) (TEMP _t53))) _l48)
    mov [ rbp + -880 ], r10
    je _l48 ## (CJUMP (AND (TEMP _t271) (LT (TEMP _t270) (TEMP _t53))) _l48)
_l49:
    callq _xi_out_of_bounds ## (CALL_STMT _t272 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -1096 ]
    mov r10, rax ## (CALL_STMT _t272 (NAME _xi_out_of_bounds))
    mov [ rbp + -1096 ], r10
_l48:
    mov r10, [ rbp + -1128 ]
    mov r11, [ rbp + -576 ]
    mov r10, r11 ## (MOVE (TEMP _t274) (TEMP _t51))
    mov [ rbp + -576 ], r11
    mov [ rbp + -1128 ], r10
    mov r10, [ rbp + -1112 ]
    mov r11, [ rbp + -584 ]
    mov r10, r11 ## (MOVE (TEMP _t273) (TEMP _t52))
    mov [ rbp + -584 ], r11
    mov [ rbp + -1112 ], r10
    mov r10, [ rbp + -888 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -888 ], r10
    mov r10, [ rbp + -896 ]
    mov r11, [ rbp + -1112 ]
    mov r10, r11 ## (MUL (TEMP _t273) (CONST 8))
    mov [ rbp + -1112 ], r11
    mov [ rbp + -896 ], r10
    mov r10, [ rbp + -896 ]
    mov r11, [ rbp + -888 ]
    imulq r10, r11 ## (MUL (TEMP _t273) (CONST 8))
    mov [ rbp + -888 ], r11
    mov [ rbp + -896 ], r10
    mov r10, [ rbp + -816 ]
    mov r11, [ rbp + -1128 ]
    mov r10, r11 ## (ADD (TEMP _t274) (MUL (TEMP _t273) (CONST 8)))
    mov [ rbp + -1128 ], r11
    mov [ rbp + -816 ], r10
    mov r10, [ rbp + -816 ]
    mov r11, [ rbp + -896 ]
    addq r10, r11 ## (ADD (TEMP _t274) (MUL (TEMP _t273) (CONST 8)))
    mov [ rbp + -896 ], r11
    mov [ rbp + -816 ], r10
    mov r10, [ rbp + -928 ]
    mov r11, [ rbp + -816 ]
    mov r10, r11 ## (MOVE (TEMP _t282) (ADD (TEMP _t274) (MUL (TEMP _t273) (CONST 8))))
    mov [ rbp + -816 ], r11
    mov [ rbp + -928 ], r10
    mov r10, [ rbp + -560 ]
    mov r11, [ rbp + -728 ]
    mov r10, r11 ## (MOVE (TEMP _t55) (TEMP i))
    mov [ rbp + -728 ], r11
    mov [ rbp + -560 ], r10
    mov r10, [ rbp + -600 ]
    mov r11, [ rbp + -712 ]
    mov r10, r11 ## (MOVE (TEMP _t54) (TEMP a))
    mov [ rbp + -712 ], r11
    mov [ rbp + -600 ], r10
    mov r10, [ rbp + -1144 ]
    mov r11, [ rbp + -600 ]
    mov r10, r11 ## (MOVE (TEMP _t275) (TEMP _t54))
    mov [ rbp + -600 ], r11
    mov [ rbp + -1144 ], r10
    mov r10, [ rbp + -824 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -824 ], r10
    mov r10, [ rbp + -832 ]
    mov r11, [ rbp + -1144 ]
    mov r10, r11 ## (SUB (TEMP _t275) (CONST 8))
    mov [ rbp + -1144 ], r11
    mov [ rbp + -832 ], r10
    mov r10, [ rbp + -832 ]
    mov r11, [ rbp + -824 ]
    subq r10, r11 ## (SUB (TEMP _t275) (CONST 8))
    mov [ rbp + -824 ], r11
    mov [ rbp + -832 ], r10
    mov r10, [ rbp + -568 ]
    mov r11, [ rbp + -832 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t56) (MEM (SUB (TEMP _t275) (CONST 8))))
    mov [ rbp + -832 ], r11
    mov [ rbp + -568 ], r10
    mov r10, [ rbp + -840 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -840 ], r10
    mov r10, [ rbp + -1168 ]
    mov r11, [ rbp + -840 ]
    mov r10, r11 ## (MOVE (TEMP _t276) (CONST 0))
    mov [ rbp + -840 ], r11
    mov [ rbp + -1168 ], r10
    mov r10, [ rbp + -1168 ]
    mov r11, [ rbp + -560 ]
    cmpq r10, r11 ## (LEQ (TEMP _t276) (TEMP _t55))
    mov [ rbp + -560 ], r11
    mov [ rbp + -1168 ], r10
    pushq rax
    mov rax, [ rbp + -856 ]
    setle al ## (LEQ (TEMP _t276) (TEMP _t55))
    mov [ rbp + -856 ], rax
    popq rax
    mov r10, [ rbp + -848 ]
    pushq rax
    mov rax, [ rbp + -856 ]
    movzx r10, al ## (LEQ (TEMP _t276) (TEMP _t55))
    mov [ rbp + -856 ], rax
    popq rax
    mov [ rbp + -848 ], r10
    mov r10, [ rbp + -1216 ]
    mov r11, [ rbp + -848 ]
    mov r10, r11 ## (MOVE (TEMP _t278) (LEQ (TEMP _t276) (TEMP _t55)))
    mov [ rbp + -848 ], r11
    mov [ rbp + -1216 ], r10
    mov r10, [ rbp + -1192 ]
    mov r11, [ rbp + -560 ]
    mov r10, r11 ## (MOVE (TEMP _t277) (TEMP _t55))
    mov [ rbp + -560 ], r11
    mov [ rbp + -1192 ], r10
    mov r10, [ rbp + -1192 ]
    mov r11, [ rbp + -568 ]
    cmpq r10, r11 ## (LT (TEMP _t277) (TEMP _t56))
    mov [ rbp + -568 ], r11
    mov [ rbp + -1192 ], r10
    pushq rax
    mov rax, [ rbp + -792 ]
    setl al ## (LT (TEMP _t277) (TEMP _t56))
    mov [ rbp + -792 ], rax
    popq rax
    mov r10, [ rbp + -864 ]
    pushq rax
    mov rax, [ rbp + -792 ]
    movzx r10, al ## (LT (TEMP _t277) (TEMP _t56))
    mov [ rbp + -792 ], rax
    popq rax
    mov [ rbp + -864 ], r10
    mov r10, [ rbp + -800 ]
    mov r11, [ rbp + -1216 ]
    mov r10, r11 ## (AND (TEMP _t278) (LT (TEMP _t277) (TEMP _t56)))
    mov [ rbp + -1216 ], r11
    mov [ rbp + -800 ], r10
    mov r10, [ rbp + -800 ]
    mov r11, [ rbp + -864 ]
    andq r10, r11 ## (AND (TEMP _t278) (LT (TEMP _t277) (TEMP _t56)))
    mov [ rbp + -864 ], r11
    mov [ rbp + -800 ], r10
    mov r10, [ rbp + -800 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t278) (LT (TEMP _t277) (TEMP _t56))) _l50)
    mov [ rbp + -800 ], r10
    je _l50 ## (CJUMP (AND (TEMP _t278) (LT (TEMP _t277) (TEMP _t56))) _l50)
_l51:
    callq _xi_out_of_bounds ## (CALL_STMT _t279 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -1232 ]
    mov r10, rax ## (CALL_STMT _t279 (NAME _xi_out_of_bounds))
    mov [ rbp + -1232 ], r10
_l50:
    mov r10, [ rbp + -920 ]
    mov r11, [ rbp + -600 ]
    mov r10, r11 ## (MOVE (TEMP _t281) (TEMP _t54))
    mov [ rbp + -600 ], r11
    mov [ rbp + -920 ], r10
    mov r10, [ rbp + -912 ]
    mov r11, [ rbp + -560 ]
    mov r10, r11 ## (MOVE (TEMP _t280) (TEMP _t55))
    mov [ rbp + -560 ], r11
    mov [ rbp + -912 ], r10
    mov r10, [ rbp + -808 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -808 ], r10
    mov r10, [ rbp + -736 ]
    mov r11, [ rbp + -912 ]
    mov r10, r11 ## (MUL (TEMP _t280) (CONST 8))
    mov [ rbp + -912 ], r11
    mov [ rbp + -736 ], r10
    mov r10, [ rbp + -736 ]
    mov r11, [ rbp + -808 ]
    imulq r10, r11 ## (MUL (TEMP _t280) (CONST 8))
    mov [ rbp + -808 ], r11
    mov [ rbp + -736 ], r10
    mov r10, [ rbp + -752 ]
    mov r11, [ rbp + -920 ]
    mov r10, r11 ## (ADD (TEMP _t281) (MUL (TEMP _t280) (CONST 8)))
    mov [ rbp + -920 ], r11
    mov [ rbp + -752 ], r10
    mov r10, [ rbp + -752 ]
    mov r11, [ rbp + -736 ]
    addq r10, r11 ## (ADD (TEMP _t281) (MUL (TEMP _t280) (CONST 8)))
    mov [ rbp + -736 ], r11
    mov [ rbp + -752 ], r10
    mov r10, [ rbp + -768 ]
    mov r11, [ rbp + -752 ]
    mov r10, [ r11 ] ## (MOVE (MEM (TEMP _t282)) (MEM (ADD (TEMP _t281) (MUL (TEMP _t280) (CONST 8)))))
    mov [ rbp + -752 ], r11
    mov [ rbp + -768 ], r10
    mov r10, [ rbp + -928 ]
    mov r11, [ rbp + -768 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t282)) (MEM (ADD (TEMP _t281) (MUL (TEMP _t280) (CONST 8)))))
    mov [ rbp + -768 ], r11
    mov [ rbp + -928 ], r10
    mov r10, [ rbp + -936 ]
    mov r11, [ rbp + -728 ]
    mov r10, r11 ## (MOVE (TEMP _t283) (TEMP i))
    mov [ rbp + -728 ], r11
    mov [ rbp + -936 ], r10
    mov r10, [ rbp + -776 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -776 ], r10
    mov r10, [ rbp + -784 ]
    mov r11, [ rbp + -936 ]
    mov r10, r11 ## (ADD (TEMP _t283) (CONST 1))
    mov [ rbp + -936 ], r11
    mov [ rbp + -784 ], r10
    mov r10, [ rbp + -784 ]
    mov r11, [ rbp + -776 ]
    addq r10, r11 ## (ADD (TEMP _t283) (CONST 1))
    mov [ rbp + -776 ], r11
    mov [ rbp + -784 ], r10
    mov r10, [ rbp + -728 ]
    mov r11, [ rbp + -784 ]
    mov r10, r11 ## (MOVE (TEMP i) (ADD (TEMP _t283) (CONST 1)))
    mov [ rbp + -784 ], r11
    mov [ rbp + -728 ], r10
    jmp _l37 ## (JUMP (NAME _l37))
end__ItopDownMerge_pt5aiiiiai:
    addq rsp, 1288
    mov rsp, rbp
    popq rbp
    retq
_Icopy_pt4aiiiai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 448
_l65:
    mov r10, [ rbp + -8 ]
    mov r10, rdi ## (MOVE (TEMP a) (TEMP _ARG0))
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -72 ]
    mov r10, rsi ## (MOVE (TEMP iBegin) (TEMP _ARG1))
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -360 ]
    mov r10, rdx ## (MOVE (TEMP iEnd) (TEMP _ARG2))
    mov [ rbp + -360 ], r10
    mov r10, [ rbp + -16 ]
    mov r10, rcx ## (MOVE (TEMP b) (TEMP _ARG3))
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -32 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (MOVE (TEMP k) (CONST 0))
    mov [ rbp + -32 ], r11
    mov [ rbp + -24 ], r10
_l52:
    mov r10, [ rbp + -232 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (MOVE (TEMP _t285) (TEMP k))
    mov [ rbp + -24 ], r11
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -232 ]
    mov r11, [ rbp + -360 ]
    cmpq r10, r11 ## (LT (TEMP _t285) (TEMP iEnd))
    mov [ rbp + -360 ], r11
    mov [ rbp + -232 ], r10
    pushq rax
    mov rax, [ rbp + -64 ]
    setl al ## (LT (TEMP _t285) (TEMP iEnd))
    mov [ rbp + -64 ], rax
    popq rax
    mov r10, [ rbp + -48 ]
    pushq rax
    mov rax, [ rbp + -64 ]
    movzx r10, al ## (LT (TEMP _t285) (TEMP iEnd))
    mov [ rbp + -64 ], rax
    popq rax
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -48 ]
    cmpq r10, 1 ## (CJUMP (LT (TEMP _t285) (TEMP iEnd)) _l53)
    mov [ rbp + -48 ], r10
    je _l53 ## (CJUMP (LT (TEMP _t285) (TEMP iEnd)) _l53)
_l54:
    jmp end__Icopy_pt4aiiiai ## (RETURN ())
_l53:
    mov r10, [ rbp + -352 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (MOVE (TEMP _t58) (TEMP k))
    mov [ rbp + -24 ], r11
    mov [ rbp + -352 ], r10
    mov r10, [ rbp + -344 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t57) (TEMP b))
    mov [ rbp + -16 ], r11
    mov [ rbp + -344 ], r10
    mov r10, [ rbp + -248 ]
    mov r11, [ rbp + -344 ]
    mov r10, r11 ## (MOVE (TEMP _t286) (TEMP _t57))
    mov [ rbp + -344 ], r11
    mov [ rbp + -248 ], r10
    mov r10, [ rbp + -440 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -440 ], r10
    mov r10, [ rbp + -448 ]
    mov r11, [ rbp + -248 ]
    mov r10, r11 ## (SUB (TEMP _t286) (CONST 8))
    mov [ rbp + -248 ], r11
    mov [ rbp + -448 ], r10
    mov r10, [ rbp + -448 ]
    mov r11, [ rbp + -440 ]
    subq r10, r11 ## (SUB (TEMP _t286) (CONST 8))
    mov [ rbp + -440 ], r11
    mov [ rbp + -448 ], r10
    mov r10, [ rbp + -336 ]
    mov r11, [ rbp + -448 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t59) (MEM (SUB (TEMP _t286) (CONST 8))))
    mov [ rbp + -448 ], r11
    mov [ rbp + -336 ], r10
    mov r10, [ rbp + -368 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -368 ], r10
    mov r10, [ rbp + -264 ]
    mov r11, [ rbp + -368 ]
    mov r10, r11 ## (MOVE (TEMP _t287) (CONST 0))
    mov [ rbp + -368 ], r11
    mov [ rbp + -264 ], r10
    mov r10, [ rbp + -264 ]
    mov r11, [ rbp + -352 ]
    cmpq r10, r11 ## (LEQ (TEMP _t287) (TEMP _t58))
    mov [ rbp + -352 ], r11
    mov [ rbp + -264 ], r10
    pushq rax
    mov rax, [ rbp + -384 ]
    setle al ## (LEQ (TEMP _t287) (TEMP _t58))
    mov [ rbp + -384 ], rax
    popq rax
    mov r10, [ rbp + -376 ]
    pushq rax
    mov rax, [ rbp + -384 ]
    movzx r10, al ## (LEQ (TEMP _t287) (TEMP _t58))
    mov [ rbp + -384 ], rax
    popq rax
    mov [ rbp + -376 ], r10
    mov r10, [ rbp + -296 ]
    mov r11, [ rbp + -376 ]
    mov r10, r11 ## (MOVE (TEMP _t289) (LEQ (TEMP _t287) (TEMP _t58)))
    mov [ rbp + -376 ], r11
    mov [ rbp + -296 ], r10
    mov r10, [ rbp + -280 ]
    mov r11, [ rbp + -352 ]
    mov r10, r11 ## (MOVE (TEMP _t288) (TEMP _t58))
    mov [ rbp + -352 ], r11
    mov [ rbp + -280 ], r10
    mov r10, [ rbp + -280 ]
    mov r11, [ rbp + -336 ]
    cmpq r10, r11 ## (LT (TEMP _t288) (TEMP _t59))
    mov [ rbp + -336 ], r11
    mov [ rbp + -280 ], r10
    pushq rax
    mov rax, [ rbp + -400 ]
    setl al ## (LT (TEMP _t288) (TEMP _t59))
    mov [ rbp + -400 ], rax
    popq rax
    mov r10, [ rbp + -392 ]
    pushq rax
    mov rax, [ rbp + -400 ]
    movzx r10, al ## (LT (TEMP _t288) (TEMP _t59))
    mov [ rbp + -400 ], rax
    popq rax
    mov [ rbp + -392 ], r10
    mov r10, [ rbp + -408 ]
    mov r11, [ rbp + -296 ]
    mov r10, r11 ## (AND (TEMP _t289) (LT (TEMP _t288) (TEMP _t59)))
    mov [ rbp + -296 ], r11
    mov [ rbp + -408 ], r10
    mov r10, [ rbp + -408 ]
    mov r11, [ rbp + -392 ]
    andq r10, r11 ## (AND (TEMP _t289) (LT (TEMP _t288) (TEMP _t59)))
    mov [ rbp + -392 ], r11
    mov [ rbp + -408 ], r10
    mov r10, [ rbp + -408 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t289) (LT (TEMP _t288) (TEMP _t59))) _l55)
    mov [ rbp + -408 ], r10
    je _l55 ## (CJUMP (AND (TEMP _t289) (LT (TEMP _t288) (TEMP _t59))) _l55)
_l56:
    callq _xi_out_of_bounds ## (CALL_STMT _t290 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -88 ]
    mov r10, rax ## (CALL_STMT _t290 (NAME _xi_out_of_bounds))
    mov [ rbp + -88 ], r10
_l55:
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -344 ]
    mov r10, r11 ## (MOVE (TEMP _t292) (TEMP _t57))
    mov [ rbp + -344 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -104 ]
    mov r11, [ rbp + -352 ]
    mov r10, r11 ## (MOVE (TEMP _t291) (TEMP _t58))
    mov [ rbp + -352 ], r11
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -416 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -416 ], r10
    mov r10, [ rbp + -424 ]
    mov r11, [ rbp + -104 ]
    mov r10, r11 ## (MUL (TEMP _t291) (CONST 8))
    mov [ rbp + -104 ], r11
    mov [ rbp + -424 ], r10
    mov r10, [ rbp + -424 ]
    mov r11, [ rbp + -416 ]
    imulq r10, r11 ## (MUL (TEMP _t291) (CONST 8))
    mov [ rbp + -416 ], r11
    mov [ rbp + -424 ], r10
    mov r10, [ rbp + -432 ]
    mov r11, [ rbp + -112 ]
    mov r10, r11 ## (ADD (TEMP _t292) (MUL (TEMP _t291) (CONST 8)))
    mov [ rbp + -112 ], r11
    mov [ rbp + -432 ], r10
    mov r10, [ rbp + -432 ]
    mov r11, [ rbp + -424 ]
    addq r10, r11 ## (ADD (TEMP _t292) (MUL (TEMP _t291) (CONST 8)))
    mov [ rbp + -424 ], r11
    mov [ rbp + -432 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -432 ]
    mov r10, r11 ## (MOVE (TEMP _t300) (ADD (TEMP _t292) (MUL (TEMP _t291) (CONST 8))))
    mov [ rbp + -432 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (MOVE (TEMP _t61) (TEMP k))
    mov [ rbp + -24 ], r11
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t60) (TEMP a))
    mov [ rbp + -8 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -80 ]
    mov r10, r11 ## (MOVE (TEMP _t293) (TEMP _t60))
    mov [ rbp + -80 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -328 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -328 ], r10
    mov r10, [ rbp + -208 ]
    mov r11, [ rbp + -128 ]
    mov r10, r11 ## (SUB (TEMP _t293) (CONST 8))
    mov [ rbp + -128 ], r11
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -208 ]
    mov r11, [ rbp + -328 ]
    subq r10, r11 ## (SUB (TEMP _t293) (CONST 8))
    mov [ rbp + -328 ], r11
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -208 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t62) (MEM (SUB (TEMP _t293) (CONST 8))))
    mov [ rbp + -208 ], r11
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -216 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -216 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -216 ]
    mov r10, r11 ## (MOVE (TEMP _t294) (CONST 0))
    mov [ rbp + -216 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -96 ]
    cmpq r10, r11 ## (LEQ (TEMP _t294) (TEMP _t61))
    mov [ rbp + -96 ], r11
    mov [ rbp + -144 ], r10
    pushq rax
    mov rax, [ rbp + -240 ]
    setle al ## (LEQ (TEMP _t294) (TEMP _t61))
    mov [ rbp + -240 ], rax
    popq rax
    mov r10, [ rbp + -224 ]
    pushq rax
    mov rax, [ rbp + -240 ]
    movzx r10, al ## (LEQ (TEMP _t294) (TEMP _t61))
    mov [ rbp + -240 ], rax
    popq rax
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -224 ]
    mov r10, r11 ## (MOVE (TEMP _t296) (LEQ (TEMP _t294) (TEMP _t61)))
    mov [ rbp + -224 ], r11
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -160 ]
    mov r11, [ rbp + -96 ]
    mov r10, r11 ## (MOVE (TEMP _t295) (TEMP _t61))
    mov [ rbp + -96 ], r11
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -160 ]
    mov r11, [ rbp + -200 ]
    cmpq r10, r11 ## (LT (TEMP _t295) (TEMP _t62))
    mov [ rbp + -200 ], r11
    mov [ rbp + -160 ], r10
    pushq rax
    mov rax, [ rbp + -272 ]
    setl al ## (LT (TEMP _t295) (TEMP _t62))
    mov [ rbp + -272 ], rax
    popq rax
    mov r10, [ rbp + -256 ]
    pushq rax
    mov rax, [ rbp + -272 ]
    movzx r10, al ## (LT (TEMP _t295) (TEMP _t62))
    mov [ rbp + -272 ], rax
    popq rax
    mov [ rbp + -256 ], r10
    mov r10, [ rbp + -288 ]
    mov r11, [ rbp + -168 ]
    mov r10, r11 ## (AND (TEMP _t296) (LT (TEMP _t295) (TEMP _t62)))
    mov [ rbp + -168 ], r11
    mov [ rbp + -288 ], r10
    mov r10, [ rbp + -288 ]
    mov r11, [ rbp + -256 ]
    andq r10, r11 ## (AND (TEMP _t296) (LT (TEMP _t295) (TEMP _t62)))
    mov [ rbp + -256 ], r11
    mov [ rbp + -288 ], r10
    mov r10, [ rbp + -288 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t296) (LT (TEMP _t295) (TEMP _t62))) _l57)
    mov [ rbp + -288 ], r10
    je _l57 ## (CJUMP (AND (TEMP _t296) (LT (TEMP _t295) (TEMP _t62))) _l57)
_l58:
    callq _xi_out_of_bounds ## (CALL_STMT _t297 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -176 ]
    mov r10, rax ## (CALL_STMT _t297 (NAME _xi_out_of_bounds))
    mov [ rbp + -176 ], r10
_l57:
    mov r10, [ rbp + -192 ]
    mov r11, [ rbp + -80 ]
    mov r10, r11 ## (MOVE (TEMP _t299) (TEMP _t60))
    mov [ rbp + -80 ], r11
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -184 ]
    mov r11, [ rbp + -96 ]
    mov r10, r11 ## (MOVE (TEMP _t298) (TEMP _t61))
    mov [ rbp + -96 ], r11
    mov [ rbp + -184 ], r10
    mov r10, [ rbp + -304 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -304 ], r10
    mov r10, [ rbp + -312 ]
    mov r11, [ rbp + -184 ]
    mov r10, r11 ## (MUL (TEMP _t298) (CONST 8))
    mov [ rbp + -184 ], r11
    mov [ rbp + -312 ], r10
    mov r10, [ rbp + -312 ]
    mov r11, [ rbp + -304 ]
    imulq r10, r11 ## (MUL (TEMP _t298) (CONST 8))
    mov [ rbp + -304 ], r11
    mov [ rbp + -312 ], r10
    mov r10, [ rbp + -320 ]
    mov r11, [ rbp + -192 ]
    mov r10, r11 ## (ADD (TEMP _t299) (MUL (TEMP _t298) (CONST 8)))
    mov [ rbp + -192 ], r11
    mov [ rbp + -320 ], r10
    mov r10, [ rbp + -320 ]
    mov r11, [ rbp + -312 ]
    addq r10, r11 ## (ADD (TEMP _t299) (MUL (TEMP _t298) (CONST 8)))
    mov [ rbp + -312 ], r11
    mov [ rbp + -320 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -320 ]
    mov r10, [ r11 ] ## (MOVE (MEM (TEMP _t300)) (MEM (ADD (TEMP _t299) (MUL (TEMP _t298) (CONST 8)))))
    mov [ rbp + -320 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -120 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t300)) (MEM (ADD (TEMP _t299) (MUL (TEMP _t298) (CONST 8)))))
    mov [ rbp + -120 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (MOVE (TEMP _t301) (TEMP k))
    mov [ rbp + -24 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -136 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (ADD (TEMP _t301) (CONST 1))
    mov [ rbp + -56 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -136 ]
    addq r10, r11 ## (ADD (TEMP _t301) (CONST 1))
    mov [ rbp + -136 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -152 ]
    mov r10, r11 ## (MOVE (TEMP k) (ADD (TEMP _t301) (CONST 1)))
    mov [ rbp + -152 ], r11
    mov [ rbp + -24 ], r10
    jmp _l52 ## (JUMP (NAME _l52))
end__Icopy_pt4aiiiai:
    addq rsp, 448
    mov rsp, rbp
    popq rbp
    retq
_Isort_pt3aiaii:
    pushq rbp
    mov rbp, rsp
    subq rsp, 120
_l62:
    mov r10, [ rbp + -8 ]
    mov r10, rdi ## (MOVE (TEMP a) (TEMP _ARG0))
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -16 ]
    mov r10, rsi ## (MOVE (TEMP b) (TEMP _ARG1))
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -56 ]
    mov r10, rdx ## (MOVE (TEMP n) (TEMP _ARG2))
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t204) (TEMP a))
    mov [ rbp + -8 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -24 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (MOVE (TEMP _t205) (CONST 0))
    mov [ rbp + -24 ], r11
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t206) (TEMP n))
    mov [ rbp + -56 ], r11
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -104 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t207) (TEMP b))
    mov [ rbp + -16 ], r11
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -80 ]
    mov rdi, r10 ## (CALL_STMT _t208 (NAME _Icopy_pt4aiiiai) (TEMP _t204) (TEMP _t205) (TEMP _t206) (TEMP _t207))
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -88 ]
    mov rsi, r10 ## (CALL_STMT _t208 (NAME _Icopy_pt4aiiiai) (TEMP _t204) (TEMP _t205) (TEMP _t206) (TEMP _t207))
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -96 ]
    mov rdx, r10 ## (CALL_STMT _t208 (NAME _Icopy_pt4aiiiai) (TEMP _t204) (TEMP _t205) (TEMP _t206) (TEMP _t207))
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -104 ]
    mov rcx, r10 ## (CALL_STMT _t208 (NAME _Icopy_pt4aiiiai) (TEMP _t204) (TEMP _t205) (TEMP _t206) (TEMP _t207))
    mov [ rbp + -104 ], r10
    callq _Icopy_pt4aiiiai ## (CALL_STMT _t208 (NAME _Icopy_pt4aiiiai) (TEMP _t204) (TEMP _t205) (TEMP _t206) (TEMP _t207))
    mov r10, [ rbp + -112 ]
    mov r10, rax ## (CALL_STMT _t208 (NAME _Icopy_pt4aiiiai) (TEMP _t204) (TEMP _t205) (TEMP _t206) (TEMP _t207))
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t209) (TEMP b))
    mov [ rbp + -16 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -32 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -40 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (MOVE (TEMP _t210) (CONST 0))
    mov [ rbp + -32 ], r11
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (MOVE (TEMP _t211) (TEMP n))
    mov [ rbp + -56 ], r11
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -64 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t212) (TEMP a))
    mov [ rbp + -8 ], r11
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -120 ]
    mov rdi, r10 ## (CALL_STMT _t213 (NAME _ItopDownSplitMerge_pt4aiiiai) (TEMP _t209) (TEMP _t210) (TEMP _t211) (TEMP _t212))
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -40 ]
    mov rsi, r10 ## (CALL_STMT _t213 (NAME _ItopDownSplitMerge_pt4aiiiai) (TEMP _t209) (TEMP _t210) (TEMP _t211) (TEMP _t212))
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -48 ]
    mov rdx, r10 ## (CALL_STMT _t213 (NAME _ItopDownSplitMerge_pt4aiiiai) (TEMP _t209) (TEMP _t210) (TEMP _t211) (TEMP _t212))
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -64 ]
    mov rcx, r10 ## (CALL_STMT _t213 (NAME _ItopDownSplitMerge_pt4aiiiai) (TEMP _t209) (TEMP _t210) (TEMP _t211) (TEMP _t212))
    mov [ rbp + -64 ], r10
    callq _ItopDownSplitMerge_pt4aiiiai ## (CALL_STMT _t213 (NAME _ItopDownSplitMerge_pt4aiiiai) (TEMP _t209) (TEMP _t210) (TEMP _t211) (TEMP _t212))
    mov r10, [ rbp + -72 ]
    mov r10, rax ## (CALL_STMT _t213 (NAME _ItopDownSplitMerge_pt4aiiiai) (TEMP _t209) (TEMP _t210) (TEMP _t211) (TEMP _t212))
    mov [ rbp + -72 ], r10
    jmp end__Isort_pt3aiaii ## (RETURN ())
end__Isort_pt3aiaii:
    addq rsp, 120
    mov rsp, rbp
    popq rbp
    retq
_ItopDownSplitMerge_pt4aiiiai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 256
_l63:
    mov r10, [ rbp + -16 ]
    mov r10, rdi ## (MOVE (TEMP b) (TEMP _ARG0))
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -120 ]
    mov r10, rsi ## (MOVE (TEMP iBegin) (TEMP _ARG1))
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -128 ]
    mov r10, rdx ## (MOVE (TEMP iEnd) (TEMP _ARG2))
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -8 ]
    mov r10, rcx ## (MOVE (TEMP a) (TEMP _ARG3))
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -208 ]
    mov r11, [ rbp + -128 ]
    mov r10, r11 ## (MOVE (TEMP _t214) (TEMP iEnd))
    mov [ rbp + -128 ], r11
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -208 ]
    mov r10, r11 ## (SUB (TEMP _t214) (TEMP iBegin))
    mov [ rbp + -208 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -120 ]
    subq r10, r11 ## (SUB (TEMP _t214) (TEMP iBegin))
    mov [ rbp + -120 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -144 ]
    mov r10, r11 ## (MOVE (TEMP _t215) (SUB (TEMP _t214) (TEMP iBegin)))
    mov [ rbp + -144 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -160 ]
    movabsq r10, 2 ## (CONST 2)
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -160 ]
    cmpq r10, r11 ## (LT (TEMP _t215) (CONST 2))
    mov [ rbp + -160 ], r11
    mov [ rbp + -80 ], r10
    pushq rax
    mov rax, [ rbp + -192 ]
    setl al ## (LT (TEMP _t215) (CONST 2))
    mov [ rbp + -192 ], rax
    popq rax
    mov r10, [ rbp + -176 ]
    pushq rax
    mov rax, [ rbp + -192 ]
    movzx r10, al ## (LT (TEMP _t215) (CONST 2))
    mov [ rbp + -192 ], rax
    popq rax
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -176 ]
    cmpq r10, 1 ## (CJUMP (LT (TEMP _t215) (CONST 2)) _l29)
    mov [ rbp + -176 ], r10
    je _l29 ## (CJUMP (LT (TEMP _t215) (CONST 2)) _l29)
_l30:
_l31:
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -128 ]
    mov r10, r11 ## (MOVE (TEMP _t216) (TEMP iEnd))
    mov [ rbp + -128 ], r11
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -88 ]
    mov r10, r11 ## (ADD (TEMP _t216) (TEMP iBegin))
    mov [ rbp + -88 ], r11
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -120 ]
    addq r10, r11 ## (ADD (TEMP _t216) (TEMP iBegin))
    mov [ rbp + -120 ], r11
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -200 ]
    mov r10, r11 ## (MOVE (TEMP _t217) (ADD (TEMP _t216) (TEMP iBegin)))
    mov [ rbp + -200 ], r11
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -64 ]
    movabsq r10, 2 ## (CONST 2)
    mov [ rbp + -64 ], r10
    pushq rax ## (DIV (TEMP _t217) (CONST 2))
    pushq rdx ## (DIV (TEMP _t217) (CONST 2))
    mov r10, [ rbp + -96 ]
    mov rax, r10 ## (DIV (TEMP _t217) (CONST 2))
    mov [ rbp + -96 ], r10
    cqo ## (DIV (TEMP _t217) (CONST 2))
    mov r10, [ rbp + -64 ]
    idivq r10 ## (DIV (TEMP _t217) (CONST 2))
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -216 ]
    mov r10, rax ## (DIV (TEMP _t217) (CONST 2))
    mov [ rbp + -216 ], r10
    popq rdx ## (DIV (TEMP _t217) (CONST 2))
    popq rax ## (DIV (TEMP _t217) (CONST 2))
    mov r10, [ rbp + -256 ]
    mov r11, [ rbp + -216 ]
    mov r10, r11 ## (MOVE (TEMP iMiddle) (DIV (TEMP _t217) (CONST 2)))
    mov [ rbp + -216 ], r11
    mov [ rbp + -256 ], r10
    mov r10, [ rbp + -104 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t218) (TEMP a))
    mov [ rbp + -8 ], r11
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -120 ]
    mov r10, r11 ## (MOVE (TEMP _t219) (TEMP iBegin))
    mov [ rbp + -120 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -256 ]
    mov r10, r11 ## (MOVE (TEMP _t220) (TEMP iMiddle))
    mov [ rbp + -256 ], r11
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t221) (TEMP b))
    mov [ rbp + -16 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -104 ]
    mov rdi, r10 ## (CALL_STMT _t222 (NAME _ItopDownSplitMerge_pt4aiiiai) (TEMP _t218) (TEMP _t219) (TEMP _t220) (TEMP _t221))
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -112 ]
    mov rsi, r10 ## (CALL_STMT _t222 (NAME _ItopDownSplitMerge_pt4aiiiai) (TEMP _t218) (TEMP _t219) (TEMP _t220) (TEMP _t221))
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -24 ]
    mov rdx, r10 ## (CALL_STMT _t222 (NAME _ItopDownSplitMerge_pt4aiiiai) (TEMP _t218) (TEMP _t219) (TEMP _t220) (TEMP _t221))
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -32 ]
    mov rcx, r10 ## (CALL_STMT _t222 (NAME _ItopDownSplitMerge_pt4aiiiai) (TEMP _t218) (TEMP _t219) (TEMP _t220) (TEMP _t221))
    mov [ rbp + -32 ], r10
    callq _ItopDownSplitMerge_pt4aiiiai ## (CALL_STMT _t222 (NAME _ItopDownSplitMerge_pt4aiiiai) (TEMP _t218) (TEMP _t219) (TEMP _t220) (TEMP _t221))
    mov r10, [ rbp + -40 ]
    mov r10, rax ## (CALL_STMT _t222 (NAME _ItopDownSplitMerge_pt4aiiiai) (TEMP _t218) (TEMP _t219) (TEMP _t220) (TEMP _t221))
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t223) (TEMP a))
    mov [ rbp + -8 ], r11
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -256 ]
    mov r10, r11 ## (MOVE (TEMP _t224) (TEMP iMiddle))
    mov [ rbp + -256 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -72 ]
    mov r11, [ rbp + -128 ]
    mov r10, r11 ## (MOVE (TEMP _t225) (TEMP iEnd))
    mov [ rbp + -128 ], r11
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -224 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t226) (TEMP b))
    mov [ rbp + -16 ], r11
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -48 ]
    mov rdi, r10 ## (CALL_STMT _t227 (NAME _ItopDownSplitMerge_pt4aiiiai) (TEMP _t223) (TEMP _t224) (TEMP _t225) (TEMP _t226))
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -56 ]
    mov rsi, r10 ## (CALL_STMT _t227 (NAME _ItopDownSplitMerge_pt4aiiiai) (TEMP _t223) (TEMP _t224) (TEMP _t225) (TEMP _t226))
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -72 ]
    mov rdx, r10 ## (CALL_STMT _t227 (NAME _ItopDownSplitMerge_pt4aiiiai) (TEMP _t223) (TEMP _t224) (TEMP _t225) (TEMP _t226))
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -224 ]
    mov rcx, r10 ## (CALL_STMT _t227 (NAME _ItopDownSplitMerge_pt4aiiiai) (TEMP _t223) (TEMP _t224) (TEMP _t225) (TEMP _t226))
    mov [ rbp + -224 ], r10
    callq _ItopDownSplitMerge_pt4aiiiai ## (CALL_STMT _t227 (NAME _ItopDownSplitMerge_pt4aiiiai) (TEMP _t223) (TEMP _t224) (TEMP _t225) (TEMP _t226))
    mov r10, [ rbp + -232 ]
    mov r10, rax ## (CALL_STMT _t227 (NAME _ItopDownSplitMerge_pt4aiiiai) (TEMP _t223) (TEMP _t224) (TEMP _t225) (TEMP _t226))
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -240 ]
    mov r11, [ rbp + -16 ]
    mov r10, r11 ## (MOVE (TEMP _t228) (TEMP b))
    mov [ rbp + -16 ], r11
    mov [ rbp + -240 ], r10
    mov r10, [ rbp + -248 ]
    mov r11, [ rbp + -120 ]
    mov r10, r11 ## (MOVE (TEMP _t229) (TEMP iBegin))
    mov [ rbp + -120 ], r11
    mov [ rbp + -248 ], r10
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -256 ]
    mov r10, r11 ## (MOVE (TEMP _t230) (TEMP iMiddle))
    mov [ rbp + -256 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -128 ]
    mov r10, r11 ## (MOVE (TEMP _t231) (TEMP iEnd))
    mov [ rbp + -128 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t232) (TEMP a))
    mov [ rbp + -8 ], r11
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -240 ]
    mov rdi, r10 ## (CALL_STMT _t233 (NAME _ItopDownMerge_pt5aiiiiai) (TEMP _t228) (TEMP _t229) (TEMP _t230) (TEMP _t231) (TEMP _t232))
    mov [ rbp + -240 ], r10
    mov r10, [ rbp + -248 ]
    mov rsi, r10 ## (CALL_STMT _t233 (NAME _ItopDownMerge_pt5aiiiiai) (TEMP _t228) (TEMP _t229) (TEMP _t230) (TEMP _t231) (TEMP _t232))
    mov [ rbp + -248 ], r10
    mov r10, [ rbp + -136 ]
    mov rdx, r10 ## (CALL_STMT _t233 (NAME _ItopDownMerge_pt5aiiiiai) (TEMP _t228) (TEMP _t229) (TEMP _t230) (TEMP _t231) (TEMP _t232))
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -152 ]
    mov rcx, r10 ## (CALL_STMT _t233 (NAME _ItopDownMerge_pt5aiiiiai) (TEMP _t228) (TEMP _t229) (TEMP _t230) (TEMP _t231) (TEMP _t232))
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -168 ]
    mov r8, r10 ## (CALL_STMT _t233 (NAME _ItopDownMerge_pt5aiiiiai) (TEMP _t228) (TEMP _t229) (TEMP _t230) (TEMP _t231) (TEMP _t232))
    mov [ rbp + -168 ], r10
    callq _ItopDownMerge_pt5aiiiiai ## (CALL_STMT _t233 (NAME _ItopDownMerge_pt5aiiiiai) (TEMP _t228) (TEMP _t229) (TEMP _t230) (TEMP _t231) (TEMP _t232))
    mov r10, [ rbp + -184 ]
    mov r10, rax ## (CALL_STMT _t233 (NAME _ItopDownMerge_pt5aiiiiai) (TEMP _t228) (TEMP _t229) (TEMP _t230) (TEMP _t231) (TEMP _t232))
    mov [ rbp + -184 ], r10
    jmp end__ItopDownSplitMerge_pt4aiiiai ## (RETURN ())
_l29:
    jmp end__ItopDownSplitMerge_pt4aiiiai ## (RETURN ())
end__ItopDownSplitMerge_pt4aiiiai:
    addq rsp, 256
    mov rsp, rbp
    popq rbp
    retq
_IprintArray_pai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 928
_l59:
    mov r10, [ rbp + -424 ]
    mov r10, rdi ## (MOVE (TEMP a) (TEMP _ARG0))
    mov [ rbp + -424 ], r10
    mov r10, [ rbp + -328 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -328 ], r10
    mov r10, [ rbp + -456 ]
    mov r11, [ rbp + -328 ]
    mov r10, r11 ## (MOVE (TEMP i) (CONST 0))
    mov [ rbp + -328 ], r11
    mov [ rbp + -456 ], r10
    mov r10, [ rbp + -344 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -344 ], r10
    mov r10, [ rbp + -600 ]
    mov r11, [ rbp + -344 ]
    mov r10, r11 ## (MOVE (TEMP _t64) (CONST 8))
    mov [ rbp + -344 ], r11
    mov [ rbp + -600 ], r10
    mov r10, [ rbp + -360 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -360 ], r10
    mov r10, [ rbp + -592 ]
    mov r11, [ rbp + -360 ]
    mov r10, r11 ## (MOVE (TEMP _t63) (CONST 1))
    mov [ rbp + -360 ], r11
    mov [ rbp + -592 ], r10
    mov r10, [ rbp + -376 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -376 ], r10
    mov r10, [ rbp + -392 ]
    mov r11, [ rbp + -592 ]
    mov r10, r11 ## (ADD (TEMP _t63) (CONST 1))
    mov [ rbp + -592 ], r11
    mov [ rbp + -392 ], r10
    mov r10, [ rbp + -392 ]
    mov r11, [ rbp + -376 ]
    addq r10, r11 ## (ADD (TEMP _t63) (CONST 1))
    mov [ rbp + -376 ], r11
    mov [ rbp + -392 ], r10
    mov r10, [ rbp + -400 ]
    mov r11, [ rbp + -600 ]
    mov r10, r11 ## (MUL (TEMP _t64) (ADD (TEMP _t63) (CONST 1)))
    mov [ rbp + -600 ], r11
    mov [ rbp + -400 ], r10
    mov r10, [ rbp + -400 ]
    mov r11, [ rbp + -392 ]
    imulq r10, r11 ## (MUL (TEMP _t64) (ADD (TEMP _t63) (CONST 1)))
    mov [ rbp + -392 ], r11
    mov [ rbp + -400 ], r10
    mov r10, [ rbp + -608 ]
    mov r11, [ rbp + -400 ]
    mov r10, r11 ## (MOVE (TEMP _t65) (MUL (TEMP _t64) (ADD (TEMP _t63) (CONST 1))))
    mov [ rbp + -400 ], r11
    mov [ rbp + -608 ], r10
    mov r10, [ rbp + -608 ]
    mov rdi, r10 ## (CALL_STMT _t66 (NAME _xi_alloc) (TEMP _t65))
    mov [ rbp + -608 ], r10
    callq _xi_alloc ## (CALL_STMT _t66 (NAME _xi_alloc) (TEMP _t65))
    mov r10, [ rbp + -560 ]
    mov r10, rax ## (CALL_STMT _t66 (NAME _xi_alloc) (TEMP _t65))
    mov [ rbp + -560 ], r10
    mov r10, [ rbp + -864 ]
    mov r11, [ rbp + -560 ]
    mov r10, r11 ## (MOVE (TEMP _t0) (TEMP _t66))
    mov [ rbp + -560 ], r11
    mov [ rbp + -864 ], r10
    mov r10, [ rbp + -568 ]
    mov r11, [ rbp + -864 ]
    mov r10, r11 ## (MOVE (TEMP _t67) (TEMP _t0))
    mov [ rbp + -864 ], r11
    mov [ rbp + -568 ], r10
    mov r10, [ rbp + -408 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -408 ], r10
    mov r10, [ rbp + -568 ]
    mov r11, [ rbp + -408 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t67)) (CONST 1))
    mov [ rbp + -408 ], r11
    mov [ rbp + -568 ], r10
    mov r10, [ rbp + -576 ]
    mov r11, [ rbp + -864 ]
    mov r10, r11 ## (MOVE (TEMP _t68) (TEMP _t0))
    mov [ rbp + -864 ], r11
    mov [ rbp + -576 ], r10
    mov r10, [ rbp + -416 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -416 ], r10
    mov r10, [ rbp + -304 ]
    mov r11, [ rbp + -576 ]
    mov r10, r11 ## (ADD (TEMP _t68) (CONST 8))
    mov [ rbp + -576 ], r11
    mov [ rbp + -304 ], r10
    mov r10, [ rbp + -304 ]
    mov r11, [ rbp + -416 ]
    addq r10, r11 ## (ADD (TEMP _t68) (CONST 8))
    mov [ rbp + -416 ], r11
    mov [ rbp + -304 ], r10
    mov r10, [ rbp + -856 ]
    mov r11, [ rbp + -304 ]
    mov r10, r11 ## (MOVE (TEMP _t1) (ADD (TEMP _t68) (CONST 8)))
    mov [ rbp + -304 ], r11
    mov [ rbp + -856 ], r10
    mov r10, [ rbp + -432 ]
    mov r11, [ rbp + -856 ]
    mov r10, r11 ## (MOVE (TEMP _t70) (TEMP _t1))
    mov [ rbp + -856 ], r11
    mov [ rbp + -432 ], r10
    mov r10, [ rbp + -312 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -312 ], r10
    mov r10, [ rbp + -584 ]
    mov r11, [ rbp + -312 ]
    mov r10, r11 ## (MOVE (TEMP _t69) (CONST 8))
    mov [ rbp + -312 ], r11
    mov [ rbp + -584 ], r10
    mov r10, [ rbp + -232 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -232 ], r10
    mov r10, [ rbp + -240 ]
    mov r11, [ rbp + -584 ]
    mov r10, r11 ## (MUL (TEMP _t69) (CONST 0))
    mov [ rbp + -584 ], r11
    mov [ rbp + -240 ], r10
    mov r10, [ rbp + -240 ]
    mov r11, [ rbp + -232 ]
    imulq r10, r11 ## (MUL (TEMP _t69) (CONST 0))
    mov [ rbp + -232 ], r11
    mov [ rbp + -240 ], r10
    mov r10, [ rbp + -248 ]
    mov r11, [ rbp + -432 ]
    mov r10, r11 ## (ADD (TEMP _t70) (MUL (TEMP _t69) (CONST 0)))
    mov [ rbp + -432 ], r11
    mov [ rbp + -248 ], r10
    mov r10, [ rbp + -248 ]
    mov r11, [ rbp + -240 ]
    addq r10, r11 ## (ADD (TEMP _t70) (MUL (TEMP _t69) (CONST 0)))
    mov [ rbp + -240 ], r11
    mov [ rbp + -248 ], r10
    mov r10, [ rbp + -440 ]
    mov r11, [ rbp + -248 ]
    mov r10, r11 ## (MOVE (TEMP _t71) (ADD (TEMP _t70) (MUL (TEMP _t69) (CONST 0))))
    mov [ rbp + -248 ], r11
    mov [ rbp + -440 ], r10
    mov r10, [ rbp + -256 ]
    movabsq r10, 44 ## (CONST 44)
    mov [ rbp + -256 ], r10
    mov r10, [ rbp + -440 ]
    mov r11, [ rbp + -256 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t71)) (CONST 44))
    mov [ rbp + -256 ], r11
    mov [ rbp + -440 ], r10
    mov r10, [ rbp + -264 ]
    mov r11, [ rbp + -856 ]
    mov r10, r11 ## (MOVE (TEMP comma) (TEMP _t1))
    mov [ rbp + -856 ], r11
    mov [ rbp + -264 ], r10
    mov r10, [ rbp + -272 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -272 ], r10
    mov r10, [ rbp + -528 ]
    mov r11, [ rbp + -272 ]
    mov r10, r11 ## (MOVE (TEMP _t73) (CONST 8))
    mov [ rbp + -272 ], r11
    mov [ rbp + -528 ], r10
    mov r10, [ rbp + -280 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -280 ], r10
    mov r10, [ rbp + -448 ]
    mov r11, [ rbp + -280 ]
    mov r10, r11 ## (MOVE (TEMP _t72) (CONST 1))
    mov [ rbp + -280 ], r11
    mov [ rbp + -448 ], r10
    mov r10, [ rbp + -288 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -288 ], r10
    mov r10, [ rbp + -296 ]
    mov r11, [ rbp + -448 ]
    mov r10, r11 ## (ADD (TEMP _t72) (CONST 1))
    mov [ rbp + -448 ], r11
    mov [ rbp + -296 ], r10
    mov r10, [ rbp + -296 ]
    mov r11, [ rbp + -288 ]
    addq r10, r11 ## (ADD (TEMP _t72) (CONST 1))
    mov [ rbp + -288 ], r11
    mov [ rbp + -296 ], r10
    mov r10, [ rbp + -208 ]
    mov r11, [ rbp + -528 ]
    mov r10, r11 ## (MUL (TEMP _t73) (ADD (TEMP _t72) (CONST 1)))
    mov [ rbp + -528 ], r11
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -208 ]
    mov r11, [ rbp + -296 ]
    imulq r10, r11 ## (MUL (TEMP _t73) (ADD (TEMP _t72) (CONST 1)))
    mov [ rbp + -296 ], r11
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -536 ]
    mov r11, [ rbp + -208 ]
    mov r10, r11 ## (MOVE (TEMP _t74) (MUL (TEMP _t73) (ADD (TEMP _t72) (CONST 1))))
    mov [ rbp + -208 ], r11
    mov [ rbp + -536 ], r10
    mov r10, [ rbp + -536 ]
    mov rdi, r10 ## (CALL_STMT _t75 (NAME _xi_alloc) (TEMP _t74))
    mov [ rbp + -536 ], r10
    callq _xi_alloc ## (CALL_STMT _t75 (NAME _xi_alloc) (TEMP _t74))
    mov r10, [ rbp + -544 ]
    mov r10, rax ## (CALL_STMT _t75 (NAME _xi_alloc) (TEMP _t74))
    mov [ rbp + -544 ], r10
    mov r10, [ rbp + -888 ]
    mov r11, [ rbp + -544 ]
    mov r10, r11 ## (MOVE (TEMP _t2) (TEMP _t75))
    mov [ rbp + -544 ], r11
    mov [ rbp + -888 ], r10
    mov r10, [ rbp + -552 ]
    mov r11, [ rbp + -888 ]
    mov r10, r11 ## (MOVE (TEMP _t76) (TEMP _t2))
    mov [ rbp + -888 ], r11
    mov [ rbp + -552 ], r10
    mov r10, [ rbp + -216 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -216 ], r10
    mov r10, [ rbp + -552 ]
    mov r11, [ rbp + -216 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t76)) (CONST 1))
    mov [ rbp + -216 ], r11
    mov [ rbp + -552 ], r10
    mov r10, [ rbp + -504 ]
    mov r11, [ rbp + -888 ]
    mov r10, r11 ## (MOVE (TEMP _t77) (TEMP _t2))
    mov [ rbp + -888 ], r11
    mov [ rbp + -504 ], r10
    mov r10, [ rbp + -224 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -504 ]
    mov r10, r11 ## (ADD (TEMP _t77) (CONST 8))
    mov [ rbp + -504 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -224 ]
    addq r10, r11 ## (ADD (TEMP _t77) (CONST 8))
    mov [ rbp + -224 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -872 ]
    mov r11, [ rbp + -152 ]
    mov r10, r11 ## (MOVE (TEMP _t3) (ADD (TEMP _t77) (CONST 8)))
    mov [ rbp + -152 ], r11
    mov [ rbp + -872 ], r10
    mov r10, [ rbp + -520 ]
    mov r11, [ rbp + -872 ]
    mov r10, r11 ## (MOVE (TEMP _t79) (TEMP _t3))
    mov [ rbp + -872 ], r11
    mov [ rbp + -520 ], r10
    mov r10, [ rbp + -160 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -512 ]
    mov r11, [ rbp + -160 ]
    mov r10, r11 ## (MOVE (TEMP _t78) (CONST 8))
    mov [ rbp + -160 ], r11
    mov [ rbp + -512 ], r10
    mov r10, [ rbp + -168 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -512 ]
    mov r10, r11 ## (MUL (TEMP _t78) (CONST 0))
    mov [ rbp + -512 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -168 ]
    imulq r10, r11 ## (MUL (TEMP _t78) (CONST 0))
    mov [ rbp + -168 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -184 ]
    mov r11, [ rbp + -520 ]
    mov r10, r11 ## (ADD (TEMP _t79) (MUL (TEMP _t78) (CONST 0)))
    mov [ rbp + -520 ], r11
    mov [ rbp + -184 ], r10
    mov r10, [ rbp + -184 ]
    mov r11, [ rbp + -176 ]
    addq r10, r11 ## (ADD (TEMP _t79) (MUL (TEMP _t78) (CONST 0)))
    mov [ rbp + -176 ], r11
    mov [ rbp + -184 ], r10
    mov r10, [ rbp + -720 ]
    mov r11, [ rbp + -184 ]
    mov r10, r11 ## (MOVE (TEMP _t80) (ADD (TEMP _t79) (MUL (TEMP _t78) (CONST 0))))
    mov [ rbp + -184 ], r11
    mov [ rbp + -720 ], r10
    mov r10, [ rbp + -192 ]
    movabsq r10, 123 ## (CONST 123)
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -720 ]
    mov r11, [ rbp + -192 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t80)) (CONST 123))
    mov [ rbp + -192 ], r11
    mov [ rbp + -720 ], r10
    mov r10, [ rbp + -728 ]
    mov r11, [ rbp + -872 ]
    mov r10, r11 ## (MOVE (TEMP _t81) (TEMP _t3))
    mov [ rbp + -872 ], r11
    mov [ rbp + -728 ], r10
    mov r10, [ rbp + -728 ]
    mov rdi, r10 ## (CALL_STMT _t82 (NAME _Iprint_pai) (TEMP _t81))
    mov [ rbp + -728 ], r10
    callq _Iprint_pai ## (CALL_STMT _t82 (NAME _Iprint_pai) (TEMP _t81))
    mov r10, [ rbp + -736 ]
    mov r10, rax ## (CALL_STMT _t82 (NAME _Iprint_pai) (TEMP _t81))
    mov [ rbp + -736 ], r10
_l0:
    mov r10, [ rbp + -880 ]
    mov r11, [ rbp + -456 ]
    mov r10, r11 ## (MOVE (TEMP _t84) (TEMP i))
    mov [ rbp + -456 ], r11
    mov [ rbp + -880 ], r10
    mov r10, [ rbp + -744 ]
    mov r11, [ rbp + -424 ]
    mov r10, r11 ## (MOVE (TEMP _t83) (TEMP a))
    mov [ rbp + -424 ], r11
    mov [ rbp + -744 ], r10
    mov r10, [ rbp + -200 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -744 ]
    mov r10, r11 ## (SUB (TEMP _t83) (CONST 8))
    mov [ rbp + -744 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -120 ]
    mov r11, [ rbp + -200 ]
    subq r10, r11 ## (SUB (TEMP _t83) (CONST 8))
    mov [ rbp + -200 ], r11
    mov [ rbp + -120 ], r10
    mov r10, [ rbp + -880 ]
    mov r11, [ rbp + -120 ]
    cmpq r10, [ r11 ] ## (LT (TEMP _t84) (MEM (SUB (TEMP _t83) (CONST 8))))
    mov [ rbp + -120 ], r11
    mov [ rbp + -880 ], r10
    pushq rax
    mov rax, [ rbp + -136 ]
    setl al ## (LT (TEMP _t84) (MEM (SUB (TEMP _t83) (CONST 8))))
    mov [ rbp + -136 ], rax
    popq rax
    mov r10, [ rbp + -128 ]
    pushq rax
    mov rax, [ rbp + -136 ]
    movzx r10, al ## (LT (TEMP _t84) (MEM (SUB (TEMP _t83) (CONST 8))))
    mov [ rbp + -136 ], rax
    popq rax
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -128 ]
    cmpq r10, 1 ## (CJUMP (LT (TEMP _t84) (MEM (SUB (TEMP _t83) (CONST 8)))) _l1)
    mov [ rbp + -128 ], r10
    je _l1 ## (CJUMP (LT (TEMP _t84) (MEM (SUB (TEMP _t83) (CONST 8)))) _l1)
_l2:
    mov r10, [ rbp + -144 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -464 ]
    mov r11, [ rbp + -144 ]
    mov r10, r11 ## (MOVE (TEMP _t100) (CONST 8))
    mov [ rbp + -144 ], r11
    mov [ rbp + -464 ], r10
    mov r10, [ rbp + -72 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -680 ]
    mov r11, [ rbp + -72 ]
    mov r10, r11 ## (MOVE (TEMP _t99) (CONST 1))
    mov [ rbp + -72 ], r11
    mov [ rbp + -680 ], r10
    mov r10, [ rbp + -80 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -680 ]
    mov r10, r11 ## (ADD (TEMP _t99) (CONST 1))
    mov [ rbp + -680 ], r11
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -80 ]
    addq r10, r11 ## (ADD (TEMP _t99) (CONST 1))
    mov [ rbp + -80 ], r11
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -464 ]
    mov r10, r11 ## (MUL (TEMP _t100) (ADD (TEMP _t99) (CONST 1)))
    mov [ rbp + -464 ], r11
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -88 ]
    imulq r10, r11 ## (MUL (TEMP _t100) (ADD (TEMP _t99) (CONST 1)))
    mov [ rbp + -88 ], r11
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -472 ]
    mov r11, [ rbp + -96 ]
    mov r10, r11 ## (MOVE (TEMP _t101) (MUL (TEMP _t100) (ADD (TEMP _t99) (CONST 1))))
    mov [ rbp + -96 ], r11
    mov [ rbp + -472 ], r10
    mov r10, [ rbp + -472 ]
    mov rdi, r10 ## (CALL_STMT _t102 (NAME _xi_alloc) (TEMP _t101))
    mov [ rbp + -472 ], r10
    callq _xi_alloc ## (CALL_STMT _t102 (NAME _xi_alloc) (TEMP _t101))
    mov r10, [ rbp + -480 ]
    mov r10, rax ## (CALL_STMT _t102 (NAME _xi_alloc) (TEMP _t101))
    mov [ rbp + -480 ], r10
    mov r10, [ rbp + -8 ]
    mov r11, [ rbp + -480 ]
    mov r10, r11 ## (MOVE (TEMP _t7) (TEMP _t102))
    mov [ rbp + -480 ], r11
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -488 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t103) (TEMP _t7))
    mov [ rbp + -8 ], r11
    mov [ rbp + -488 ], r10
    mov r10, [ rbp + -104 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -488 ]
    mov r11, [ rbp + -104 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t103)) (CONST 1))
    mov [ rbp + -104 ], r11
    mov [ rbp + -488 ], r10
    mov r10, [ rbp + -496 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t104) (TEMP _t7))
    mov [ rbp + -8 ], r11
    mov [ rbp + -496 ], r10
    mov r10, [ rbp + -112 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -496 ]
    mov r10, r11 ## (ADD (TEMP _t104) (CONST 8))
    mov [ rbp + -496 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -112 ]
    addq r10, r11 ## (ADD (TEMP _t104) (CONST 8))
    mov [ rbp + -112 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (MOVE (TEMP _t8) (ADD (TEMP _t104) (CONST 8)))
    mov [ rbp + -32 ], r11
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -336 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (MOVE (TEMP _t106) (TEMP _t8))
    mov [ rbp + -24 ], r11
    mov [ rbp + -336 ], r10
    mov r10, [ rbp + -40 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -40 ], r10
    mov r10, [ rbp + -320 ]
    mov r11, [ rbp + -40 ]
    mov r10, r11 ## (MOVE (TEMP _t105) (CONST 8))
    mov [ rbp + -40 ], r11
    mov [ rbp + -320 ], r10
    mov r10, [ rbp + -48 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -320 ]
    mov r10, r11 ## (MUL (TEMP _t105) (CONST 0))
    mov [ rbp + -320 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -48 ]
    imulq r10, r11 ## (MUL (TEMP _t105) (CONST 0))
    mov [ rbp + -48 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -64 ]
    mov r11, [ rbp + -336 ]
    mov r10, r11 ## (ADD (TEMP _t106) (MUL (TEMP _t105) (CONST 0)))
    mov [ rbp + -336 ], r11
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -64 ]
    mov r11, [ rbp + -56 ]
    addq r10, r11 ## (ADD (TEMP _t106) (MUL (TEMP _t105) (CONST 0)))
    mov [ rbp + -56 ], r11
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -352 ]
    mov r11, [ rbp + -64 ]
    mov r10, r11 ## (MOVE (TEMP _t107) (ADD (TEMP _t106) (MUL (TEMP _t105) (CONST 0))))
    mov [ rbp + -64 ], r11
    mov [ rbp + -352 ], r10
    mov r10, [ rbp + -800 ]
    movabsq r10, 125 ## (CONST 125)
    mov [ rbp + -800 ], r10
    mov r10, [ rbp + -352 ]
    mov r11, [ rbp + -800 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t107)) (CONST 125))
    mov [ rbp + -800 ], r11
    mov [ rbp + -352 ], r10
    mov r10, [ rbp + -368 ]
    mov r11, [ rbp + -24 ]
    mov r10, r11 ## (MOVE (TEMP _t108) (TEMP _t8))
    mov [ rbp + -24 ], r11
    mov [ rbp + -368 ], r10
    mov r10, [ rbp + -368 ]
    mov rdi, r10 ## (CALL_STMT _t109 (NAME _Iprintln_pai) (TEMP _t108))
    mov [ rbp + -368 ], r10
    callq _Iprintln_pai ## (CALL_STMT _t109 (NAME _Iprintln_pai) (TEMP _t108))
    mov r10, [ rbp + -384 ]
    mov r10, rax ## (CALL_STMT _t109 (NAME _Iprintln_pai) (TEMP _t108))
    mov [ rbp + -384 ], r10
    jmp end__IprintArray_pai ## (RETURN ())
_l1:
    mov r10, [ rbp + -904 ]
    mov r11, [ rbp + -456 ]
    mov r10, r11 ## (MOVE (TEMP _t5) (TEMP i))
    mov [ rbp + -456 ], r11
    mov [ rbp + -904 ], r10
    mov r10, [ rbp + -920 ]
    mov r11, [ rbp + -424 ]
    mov r10, r11 ## (MOVE (TEMP _t4) (TEMP a))
    mov [ rbp + -424 ], r11
    mov [ rbp + -920 ], r10
    mov r10, [ rbp + -896 ]
    mov r11, [ rbp + -920 ]
    mov r10, r11 ## (MOVE (TEMP _t85) (TEMP _t4))
    mov [ rbp + -920 ], r11
    mov [ rbp + -896 ], r10
    mov r10, [ rbp + -808 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -808 ], r10
    mov r10, [ rbp + -816 ]
    mov r11, [ rbp + -896 ]
    mov r10, r11 ## (SUB (TEMP _t85) (CONST 8))
    mov [ rbp + -896 ], r11
    mov [ rbp + -816 ], r10
    mov r10, [ rbp + -816 ]
    mov r11, [ rbp + -808 ]
    subq r10, r11 ## (SUB (TEMP _t85) (CONST 8))
    mov [ rbp + -808 ], r11
    mov [ rbp + -816 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -816 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t6) (MEM (SUB (TEMP _t85) (CONST 8))))
    mov [ rbp + -816 ], r11
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -824 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -824 ], r10
    mov r10, [ rbp + -912 ]
    mov r11, [ rbp + -824 ]
    mov r10, r11 ## (MOVE (TEMP _t86) (CONST 0))
    mov [ rbp + -824 ], r11
    mov [ rbp + -912 ], r10
    mov r10, [ rbp + -912 ]
    mov r11, [ rbp + -904 ]
    cmpq r10, r11 ## (LEQ (TEMP _t86) (TEMP _t5))
    mov [ rbp + -904 ], r11
    mov [ rbp + -912 ], r10
    pushq rax
    mov rax, [ rbp + -752 ]
    setle al ## (LEQ (TEMP _t86) (TEMP _t5))
    mov [ rbp + -752 ], rax
    popq rax
    mov r10, [ rbp + -832 ]
    pushq rax
    mov rax, [ rbp + -752 ]
    movzx r10, al ## (LEQ (TEMP _t86) (TEMP _t5))
    mov [ rbp + -752 ], rax
    popq rax
    mov [ rbp + -832 ], r10
    mov r10, [ rbp + -840 ]
    mov r11, [ rbp + -832 ]
    mov r10, r11 ## (MOVE (TEMP _t88) (LEQ (TEMP _t86) (TEMP _t5)))
    mov [ rbp + -832 ], r11
    mov [ rbp + -840 ], r10
    mov r10, [ rbp + -928 ]
    mov r11, [ rbp + -904 ]
    mov r10, r11 ## (MOVE (TEMP _t87) (TEMP _t5))
    mov [ rbp + -904 ], r11
    mov [ rbp + -928 ], r10
    mov r10, [ rbp + -928 ]
    mov r11, [ rbp + -16 ]
    cmpq r10, r11 ## (LT (TEMP _t87) (TEMP _t6))
    mov [ rbp + -16 ], r11
    mov [ rbp + -928 ], r10
    pushq rax
    mov rax, [ rbp + -768 ]
    setl al ## (LT (TEMP _t87) (TEMP _t6))
    mov [ rbp + -768 ], rax
    popq rax
    mov r10, [ rbp + -760 ]
    pushq rax
    mov rax, [ rbp + -768 ]
    movzx r10, al ## (LT (TEMP _t87) (TEMP _t6))
    mov [ rbp + -768 ], rax
    popq rax
    mov [ rbp + -760 ], r10
    mov r10, [ rbp + -776 ]
    mov r11, [ rbp + -840 ]
    mov r10, r11 ## (AND (TEMP _t88) (LT (TEMP _t87) (TEMP _t6)))
    mov [ rbp + -840 ], r11
    mov [ rbp + -776 ], r10
    mov r10, [ rbp + -776 ]
    mov r11, [ rbp + -760 ]
    andq r10, r11 ## (AND (TEMP _t88) (LT (TEMP _t87) (TEMP _t6)))
    mov [ rbp + -760 ], r11
    mov [ rbp + -776 ], r10
    mov r10, [ rbp + -776 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t88) (LT (TEMP _t87) (TEMP _t6))) _l3)
    mov [ rbp + -776 ], r10
    je _l3 ## (CJUMP (AND (TEMP _t88) (LT (TEMP _t87) (TEMP _t6))) _l3)
_l4:
    callq _xi_out_of_bounds ## (CALL_STMT _t89 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -848 ]
    mov r10, rax ## (CALL_STMT _t89 (NAME _xi_out_of_bounds))
    mov [ rbp + -848 ], r10
_l3:
    mov r10, [ rbp + -616 ]
    mov r11, [ rbp + -920 ]
    mov r10, r11 ## (MOVE (TEMP _t91) (TEMP _t4))
    mov [ rbp + -920 ], r11
    mov [ rbp + -616 ], r10
    mov r10, [ rbp + -648 ]
    mov r11, [ rbp + -904 ]
    mov r10, r11 ## (MOVE (TEMP _t90) (TEMP _t5))
    mov [ rbp + -904 ], r11
    mov [ rbp + -648 ], r10
    mov r10, [ rbp + -784 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -784 ], r10
    mov r10, [ rbp + -792 ]
    mov r11, [ rbp + -648 ]
    mov r10, r11 ## (MUL (TEMP _t90) (CONST 8))
    mov [ rbp + -648 ], r11
    mov [ rbp + -792 ], r10
    mov r10, [ rbp + -792 ]
    mov r11, [ rbp + -784 ]
    imulq r10, r11 ## (MUL (TEMP _t90) (CONST 8))
    mov [ rbp + -784 ], r11
    mov [ rbp + -792 ], r10
    mov r10, [ rbp + -656 ]
    mov r11, [ rbp + -616 ]
    mov r10, r11 ## (ADD (TEMP _t91) (MUL (TEMP _t90) (CONST 8)))
    mov [ rbp + -616 ], r11
    mov [ rbp + -656 ], r10
    mov r10, [ rbp + -656 ]
    mov r11, [ rbp + -792 ]
    addq r10, r11 ## (ADD (TEMP _t91) (MUL (TEMP _t90) (CONST 8)))
    mov [ rbp + -792 ], r11
    mov [ rbp + -656 ], r10
    mov r10, [ rbp + -624 ]
    mov r11, [ rbp + -656 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t92) (MEM (ADD (TEMP _t91) (MUL (TEMP _t90) (CONST 8)))))
    mov [ rbp + -656 ], r11
    mov [ rbp + -624 ], r10
    mov r10, [ rbp + -624 ]
    mov rdi, r10 ## (CALL_STMT _t93 (NAME _IunparseInt_aii) (TEMP _t92))
    mov [ rbp + -624 ], r10
    callq _IunparseInt_aii ## (CALL_STMT _t93 (NAME _IunparseInt_aii) (TEMP _t92))
    mov r10, [ rbp + -632 ]
    mov r10, rax ## (CALL_STMT _t93 (NAME _IunparseInt_aii) (TEMP _t92))
    mov [ rbp + -632 ], r10
    mov r10, [ rbp + -640 ]
    mov r11, [ rbp + -632 ]
    mov r10, r11 ## (MOVE (TEMP _t94) (TEMP _t93))
    mov [ rbp + -632 ], r11
    mov [ rbp + -640 ], r10
    mov r10, [ rbp + -640 ]
    mov rdi, r10 ## (CALL_STMT _t95 (NAME _Iprint_pai) (TEMP _t94))
    mov [ rbp + -640 ], r10
    callq _Iprint_pai ## (CALL_STMT _t95 (NAME _Iprint_pai) (TEMP _t94))
    mov r10, [ rbp + -688 ]
    mov r10, rax ## (CALL_STMT _t95 (NAME _Iprint_pai) (TEMP _t94))
    mov [ rbp + -688 ], r10
    mov r10, [ rbp + -696 ]
    mov r11, [ rbp + -264 ]
    mov r10, r11 ## (MOVE (TEMP _t96) (TEMP comma))
    mov [ rbp + -264 ], r11
    mov [ rbp + -696 ], r10
    mov r10, [ rbp + -696 ]
    mov rdi, r10 ## (CALL_STMT _t97 (NAME _Iprint_pai) (TEMP _t96))
    mov [ rbp + -696 ], r10
    callq _Iprint_pai ## (CALL_STMT _t97 (NAME _Iprint_pai) (TEMP _t96))
    mov r10, [ rbp + -704 ]
    mov r10, rax ## (CALL_STMT _t97 (NAME _Iprint_pai) (TEMP _t96))
    mov [ rbp + -704 ], r10
    mov r10, [ rbp + -712 ]
    mov r11, [ rbp + -456 ]
    mov r10, r11 ## (MOVE (TEMP _t98) (TEMP i))
    mov [ rbp + -456 ], r11
    mov [ rbp + -712 ], r10
    mov r10, [ rbp + -664 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -664 ], r10
    mov r10, [ rbp + -672 ]
    mov r11, [ rbp + -712 ]
    mov r10, r11 ## (ADD (TEMP _t98) (CONST 1))
    mov [ rbp + -712 ], r11
    mov [ rbp + -672 ], r10
    mov r10, [ rbp + -672 ]
    mov r11, [ rbp + -664 ]
    addq r10, r11 ## (ADD (TEMP _t98) (CONST 1))
    mov [ rbp + -664 ], r11
    mov [ rbp + -672 ], r10
    mov r10, [ rbp + -456 ]
    mov r11, [ rbp + -672 ]
    mov r10, r11 ## (MOVE (TEMP i) (ADD (TEMP _t98) (CONST 1)))
    mov [ rbp + -672 ], r11
    mov [ rbp + -456 ], r10
    jmp _l0 ## (JUMP (NAME _l0))
end__IprintArray_pai:
    addq rsp, 928
    mov rsp, rbp
    popq rbp
    retq
_Ireverse_pai:
    pushq rbp
    mov rbp, rsp
    subq rsp, 912
_l60:
    mov r10, [ rbp + -424 ]
    mov r10, rdi ## (MOVE (TEMP a) (TEMP _ARG0))
    mov [ rbp + -424 ], r10
    mov r10, [ rbp + -840 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -840 ], r10
    mov r10, [ rbp + -456 ]
    mov r11, [ rbp + -840 ]
    mov r10, r11 ## (MOVE (TEMP k) (CONST 0))
    mov [ rbp + -840 ], r11
    mov [ rbp + -456 ], r10
    mov r10, [ rbp + -320 ]
    mov r11, [ rbp + -424 ]
    mov r10, r11 ## (MOVE (TEMP _t110) (TEMP a))
    mov [ rbp + -424 ], r11
    mov [ rbp + -320 ], r10
    mov r10, [ rbp + -776 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -776 ], r10
    mov r10, [ rbp + -784 ]
    mov r11, [ rbp + -320 ]
    mov r10, r11 ## (SUB (TEMP _t110) (CONST 8))
    mov [ rbp + -320 ], r11
    mov [ rbp + -784 ], r10
    mov r10, [ rbp + -784 ]
    mov r11, [ rbp + -776 ]
    subq r10, r11 ## (SUB (TEMP _t110) (CONST 8))
    mov [ rbp + -776 ], r11
    mov [ rbp + -784 ], r10
    mov r10, [ rbp + -184 ]
    mov r11, [ rbp + -784 ]
    mov r10, [ r11 ] ## (MOVE (TEMP len) (MEM (SUB (TEMP _t110) (CONST 8))))
    mov [ rbp + -784 ], r11
    mov [ rbp + -184 ], r10
_l5:
    mov r10, [ rbp + -352 ]
    mov r11, [ rbp + -456 ]
    mov r10, r11 ## (MOVE (TEMP _t112) (TEMP k))
    mov [ rbp + -456 ], r11
    mov [ rbp + -352 ], r10
    mov r10, [ rbp + -336 ]
    mov r11, [ rbp + -184 ]
    mov r10, r11 ## (MOVE (TEMP _t111) (TEMP len))
    mov [ rbp + -184 ], r11
    mov [ rbp + -336 ], r10
    mov r10, [ rbp + -792 ]
    movabsq r10, 2 ## (CONST 2)
    mov [ rbp + -792 ], r10
    pushq rax ## (DIV (TEMP _t111) (CONST 2))
    pushq rdx ## (DIV (TEMP _t111) (CONST 2))
    mov r10, [ rbp + -336 ]
    mov rax, r10 ## (DIV (TEMP _t111) (CONST 2))
    mov [ rbp + -336 ], r10
    cqo ## (DIV (TEMP _t111) (CONST 2))
    mov r10, [ rbp + -792 ]
    idivq r10 ## (DIV (TEMP _t111) (CONST 2))
    mov [ rbp + -792 ], r10
    mov r10, [ rbp + -800 ]
    mov r10, rax ## (DIV (TEMP _t111) (CONST 2))
    mov [ rbp + -800 ], r10
    popq rdx ## (DIV (TEMP _t111) (CONST 2))
    popq rax ## (DIV (TEMP _t111) (CONST 2))
    mov r10, [ rbp + -352 ]
    mov r11, [ rbp + -800 ]
    cmpq r10, r11 ## (LT (TEMP _t112) (DIV (TEMP _t111) (CONST 2)))
    mov [ rbp + -800 ], r11
    mov [ rbp + -352 ], r10
    pushq rax
    mov rax, [ rbp + -816 ]
    setl al ## (LT (TEMP _t112) (DIV (TEMP _t111) (CONST 2)))
    mov [ rbp + -816 ], rax
    popq rax
    mov r10, [ rbp + -808 ]
    pushq rax
    mov rax, [ rbp + -816 ]
    movzx r10, al ## (LT (TEMP _t112) (DIV (TEMP _t111) (CONST 2)))
    mov [ rbp + -816 ], rax
    popq rax
    mov [ rbp + -808 ], r10
    mov r10, [ rbp + -808 ]
    cmpq r10, 1 ## (CJUMP (LT (TEMP _t112) (DIV (TEMP _t111) (CONST 2))) _l6)
    mov [ rbp + -808 ], r10
    je _l6 ## (CJUMP (LT (TEMP _t112) (DIV (TEMP _t111) (CONST 2))) _l6)
_l7:
    jmp end__Ireverse_pai ## (RETURN ())
_l6:
    mov r10, [ rbp + -912 ]
    mov r11, [ rbp + -456 ]
    mov r10, r11 ## (MOVE (TEMP _t10) (TEMP k))
    mov [ rbp + -456 ], r11
    mov [ rbp + -912 ], r10
    mov r10, [ rbp + -8 ]
    mov r11, [ rbp + -424 ]
    mov r10, r11 ## (MOVE (TEMP _t9) (TEMP a))
    mov [ rbp + -424 ], r11
    mov [ rbp + -8 ], r10
    mov r10, [ rbp + -368 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t113) (TEMP _t9))
    mov [ rbp + -8 ], r11
    mov [ rbp + -368 ], r10
    mov r10, [ rbp + -824 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -824 ], r10
    mov r10, [ rbp + -664 ]
    mov r11, [ rbp + -368 ]
    mov r10, r11 ## (SUB (TEMP _t113) (CONST 8))
    mov [ rbp + -368 ], r11
    mov [ rbp + -664 ], r10
    mov r10, [ rbp + -664 ]
    mov r11, [ rbp + -824 ]
    subq r10, r11 ## (SUB (TEMP _t113) (CONST 8))
    mov [ rbp + -824 ], r11
    mov [ rbp + -664 ], r10
    mov r10, [ rbp + -880 ]
    mov r11, [ rbp + -664 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t11) (MEM (SUB (TEMP _t113) (CONST 8))))
    mov [ rbp + -664 ], r11
    mov [ rbp + -880 ], r10
    mov r10, [ rbp + -680 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -680 ], r10
    mov r10, [ rbp + -384 ]
    mov r11, [ rbp + -680 ]
    mov r10, r11 ## (MOVE (TEMP _t114) (CONST 0))
    mov [ rbp + -680 ], r11
    mov [ rbp + -384 ], r10
    mov r10, [ rbp + -384 ]
    mov r11, [ rbp + -912 ]
    cmpq r10, r11 ## (LEQ (TEMP _t114) (TEMP _t10))
    mov [ rbp + -912 ], r11
    mov [ rbp + -384 ], r10
    pushq rax
    mov rax, [ rbp + -592 ]
    setle al ## (LEQ (TEMP _t114) (TEMP _t10))
    mov [ rbp + -592 ], rax
    popq rax
    mov r10, [ rbp + -696 ]
    pushq rax
    mov rax, [ rbp + -592 ]
    movzx r10, al ## (LEQ (TEMP _t114) (TEMP _t10))
    mov [ rbp + -592 ], rax
    popq rax
    mov [ rbp + -696 ], r10
    mov r10, [ rbp + -256 ]
    mov r11, [ rbp + -696 ]
    mov r10, r11 ## (MOVE (TEMP _t116) (LEQ (TEMP _t114) (TEMP _t10)))
    mov [ rbp + -696 ], r11
    mov [ rbp + -256 ], r10
    mov r10, [ rbp + -400 ]
    mov r11, [ rbp + -912 ]
    mov r10, r11 ## (MOVE (TEMP _t115) (TEMP _t10))
    mov [ rbp + -912 ], r11
    mov [ rbp + -400 ], r10
    mov r10, [ rbp + -400 ]
    mov r11, [ rbp + -880 ]
    cmpq r10, r11 ## (LT (TEMP _t115) (TEMP _t11))
    mov [ rbp + -880 ], r11
    mov [ rbp + -400 ], r10
    pushq rax
    mov rax, [ rbp + -608 ]
    setl al ## (LT (TEMP _t115) (TEMP _t11))
    mov [ rbp + -608 ], rax
    popq rax
    mov r10, [ rbp + -600 ]
    pushq rax
    mov rax, [ rbp + -608 ]
    movzx r10, al ## (LT (TEMP _t115) (TEMP _t11))
    mov [ rbp + -608 ], rax
    popq rax
    mov [ rbp + -600 ], r10
    mov r10, [ rbp + -616 ]
    mov r11, [ rbp + -256 ]
    mov r10, r11 ## (AND (TEMP _t116) (LT (TEMP _t115) (TEMP _t11)))
    mov [ rbp + -256 ], r11
    mov [ rbp + -616 ], r10
    mov r10, [ rbp + -616 ]
    mov r11, [ rbp + -600 ]
    andq r10, r11 ## (AND (TEMP _t116) (LT (TEMP _t115) (TEMP _t11)))
    mov [ rbp + -600 ], r11
    mov [ rbp + -616 ], r10
    mov r10, [ rbp + -616 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t116) (LT (TEMP _t115) (TEMP _t11))) _l8)
    mov [ rbp + -616 ], r10
    je _l8 ## (CJUMP (AND (TEMP _t116) (LT (TEMP _t115) (TEMP _t11))) _l8)
_l9:
    callq _xi_out_of_bounds ## (CALL_STMT _t117 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -264 ]
    mov r10, rax ## (CALL_STMT _t117 (NAME _xi_out_of_bounds))
    mov [ rbp + -264 ], r10
_l8:
    mov r10, [ rbp + -280 ]
    mov r11, [ rbp + -8 ]
    mov r10, r11 ## (MOVE (TEMP _t119) (TEMP _t9))
    mov [ rbp + -8 ], r11
    mov [ rbp + -280 ], r10
    mov r10, [ rbp + -272 ]
    mov r11, [ rbp + -912 ]
    mov r10, r11 ## (MOVE (TEMP _t118) (TEMP _t10))
    mov [ rbp + -912 ], r11
    mov [ rbp + -272 ], r10
    mov r10, [ rbp + -624 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -624 ], r10
    mov r10, [ rbp + -632 ]
    mov r11, [ rbp + -272 ]
    mov r10, r11 ## (MUL (TEMP _t118) (CONST 8))
    mov [ rbp + -272 ], r11
    mov [ rbp + -632 ], r10
    mov r10, [ rbp + -632 ]
    mov r11, [ rbp + -624 ]
    imulq r10, r11 ## (MUL (TEMP _t118) (CONST 8))
    mov [ rbp + -624 ], r11
    mov [ rbp + -632 ], r10
    mov r10, [ rbp + -640 ]
    mov r11, [ rbp + -280 ]
    mov r10, r11 ## (ADD (TEMP _t119) (MUL (TEMP _t118) (CONST 8)))
    mov [ rbp + -280 ], r11
    mov [ rbp + -640 ], r10
    mov r10, [ rbp + -640 ]
    mov r11, [ rbp + -632 ]
    addq r10, r11 ## (ADD (TEMP _t119) (MUL (TEMP _t118) (CONST 8)))
    mov [ rbp + -632 ], r11
    mov [ rbp + -640 ], r10
    mov r10, [ rbp + -768 ]
    mov r11, [ rbp + -640 ]
    mov r10, [ r11 ] ## (MOVE (TEMP temp) (MEM (ADD (TEMP _t119) (MUL (TEMP _t118) (CONST 8)))))
    mov [ rbp + -640 ], r11
    mov [ rbp + -768 ], r10
    mov r10, [ rbp + -896 ]
    mov r11, [ rbp + -456 ]
    mov r10, r11 ## (MOVE (TEMP _t13) (TEMP k))
    mov [ rbp + -456 ], r11
    mov [ rbp + -896 ], r10
    mov r10, [ rbp + -888 ]
    mov r11, [ rbp + -424 ]
    mov r10, r11 ## (MOVE (TEMP _t12) (TEMP a))
    mov [ rbp + -424 ], r11
    mov [ rbp + -888 ], r10
    mov r10, [ rbp + -200 ]
    mov r11, [ rbp + -888 ]
    mov r10, r11 ## (MOVE (TEMP _t120) (TEMP _t12))
    mov [ rbp + -888 ], r11
    mov [ rbp + -200 ], r10
    mov r10, [ rbp + -648 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -648 ], r10
    mov r10, [ rbp + -512 ]
    mov r11, [ rbp + -200 ]
    mov r10, r11 ## (SUB (TEMP _t120) (CONST 8))
    mov [ rbp + -200 ], r11
    mov [ rbp + -512 ], r10
    mov r10, [ rbp + -512 ]
    mov r11, [ rbp + -648 ]
    subq r10, r11 ## (SUB (TEMP _t120) (CONST 8))
    mov [ rbp + -648 ], r11
    mov [ rbp + -512 ], r10
    mov r10, [ rbp + -904 ]
    mov r11, [ rbp + -512 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t14) (MEM (SUB (TEMP _t120) (CONST 8))))
    mov [ rbp + -512 ], r11
    mov [ rbp + -904 ], r10
    mov r10, [ rbp + -528 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -528 ], r10
    mov r10, [ rbp + -208 ]
    mov r11, [ rbp + -528 ]
    mov r10, r11 ## (MOVE (TEMP _t121) (CONST 0))
    mov [ rbp + -528 ], r11
    mov [ rbp + -208 ], r10
    mov r10, [ rbp + -208 ]
    mov r11, [ rbp + -896 ]
    cmpq r10, r11 ## (LEQ (TEMP _t121) (TEMP _t13))
    mov [ rbp + -896 ], r11
    mov [ rbp + -208 ], r10
    pushq rax
    mov rax, [ rbp + -440 ]
    setle al ## (LEQ (TEMP _t121) (TEMP _t13))
    mov [ rbp + -440 ], rax
    popq rax
    mov r10, [ rbp + -432 ]
    pushq rax
    mov rax, [ rbp + -440 ]
    movzx r10, al ## (LEQ (TEMP _t121) (TEMP _t13))
    mov [ rbp + -440 ], rax
    popq rax
    mov [ rbp + -432 ], r10
    mov r10, [ rbp + -224 ]
    mov r11, [ rbp + -432 ]
    mov r10, r11 ## (MOVE (TEMP _t123) (LEQ (TEMP _t121) (TEMP _t13)))
    mov [ rbp + -432 ], r11
    mov [ rbp + -224 ], r10
    mov r10, [ rbp + -216 ]
    mov r11, [ rbp + -896 ]
    mov r10, r11 ## (MOVE (TEMP _t122) (TEMP _t13))
    mov [ rbp + -896 ], r11
    mov [ rbp + -216 ], r10
    mov r10, [ rbp + -216 ]
    mov r11, [ rbp + -904 ]
    cmpq r10, r11 ## (LT (TEMP _t122) (TEMP _t14))
    mov [ rbp + -904 ], r11
    mov [ rbp + -216 ], r10
    pushq rax
    mov rax, [ rbp + -464 ]
    setl al ## (LT (TEMP _t122) (TEMP _t14))
    mov [ rbp + -464 ], rax
    popq rax
    mov r10, [ rbp + -448 ]
    pushq rax
    mov rax, [ rbp + -464 ]
    movzx r10, al ## (LT (TEMP _t122) (TEMP _t14))
    mov [ rbp + -464 ], rax
    popq rax
    mov [ rbp + -448 ], r10
    mov r10, [ rbp + -472 ]
    mov r11, [ rbp + -224 ]
    mov r10, r11 ## (AND (TEMP _t123) (LT (TEMP _t122) (TEMP _t14)))
    mov [ rbp + -224 ], r11
    mov [ rbp + -472 ], r10
    mov r10, [ rbp + -472 ]
    mov r11, [ rbp + -448 ]
    andq r10, r11 ## (AND (TEMP _t123) (LT (TEMP _t122) (TEMP _t14)))
    mov [ rbp + -448 ], r11
    mov [ rbp + -472 ], r10
    mov r10, [ rbp + -472 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t123) (LT (TEMP _t122) (TEMP _t14))) _l10)
    mov [ rbp + -472 ], r10
    je _l10 ## (CJUMP (AND (TEMP _t123) (LT (TEMP _t122) (TEMP _t14))) _l10)
_l11:
    callq _xi_out_of_bounds ## (CALL_STMT _t124 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -232 ]
    mov r10, rax ## (CALL_STMT _t124 (NAME _xi_out_of_bounds))
    mov [ rbp + -232 ], r10
_l10:
    mov r10, [ rbp + -248 ]
    mov r11, [ rbp + -888 ]
    mov r10, r11 ## (MOVE (TEMP _t126) (TEMP _t12))
    mov [ rbp + -888 ], r11
    mov [ rbp + -248 ], r10
    mov r10, [ rbp + -240 ]
    mov r11, [ rbp + -896 ]
    mov r10, r11 ## (MOVE (TEMP _t125) (TEMP _t13))
    mov [ rbp + -896 ], r11
    mov [ rbp + -240 ], r10
    mov r10, [ rbp + -480 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -480 ], r10
    mov r10, [ rbp + -488 ]
    mov r11, [ rbp + -240 ]
    mov r10, r11 ## (MUL (TEMP _t125) (CONST 8))
    mov [ rbp + -240 ], r11
    mov [ rbp + -488 ], r10
    mov r10, [ rbp + -488 ]
    mov r11, [ rbp + -480 ]
    imulq r10, r11 ## (MUL (TEMP _t125) (CONST 8))
    mov [ rbp + -480 ], r11
    mov [ rbp + -488 ], r10
    mov r10, [ rbp + -496 ]
    mov r11, [ rbp + -248 ]
    mov r10, r11 ## (ADD (TEMP _t126) (MUL (TEMP _t125) (CONST 8)))
    mov [ rbp + -248 ], r11
    mov [ rbp + -496 ], r10
    mov r10, [ rbp + -496 ]
    mov r11, [ rbp + -488 ]
    addq r10, r11 ## (ADD (TEMP _t126) (MUL (TEMP _t125) (CONST 8)))
    mov [ rbp + -488 ], r11
    mov [ rbp + -496 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -496 ]
    mov r10, r11 ## (MOVE (TEMP _t136) (ADD (TEMP _t126) (MUL (TEMP _t125) (CONST 8))))
    mov [ rbp + -496 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -160 ]
    mov r11, [ rbp + -184 ]
    mov r10, r11 ## (MOVE (TEMP _t127) (TEMP len))
    mov [ rbp + -184 ], r11
    mov [ rbp + -160 ], r10
    mov r10, [ rbp + -504 ]
    mov r11, [ rbp + -160 ]
    mov r10, r11 ## (SUB (TEMP _t127) (TEMP k))
    mov [ rbp + -160 ], r11
    mov [ rbp + -504 ], r10
    mov r10, [ rbp + -504 ]
    mov r11, [ rbp + -456 ]
    subq r10, r11 ## (SUB (TEMP _t127) (TEMP k))
    mov [ rbp + -456 ], r11
    mov [ rbp + -504 ], r10
    mov r10, [ rbp + -168 ]
    mov r11, [ rbp + -504 ]
    mov r10, r11 ## (MOVE (TEMP _t128) (SUB (TEMP _t127) (TEMP k)))
    mov [ rbp + -504 ], r11
    mov [ rbp + -168 ], r10
    mov r10, [ rbp + -416 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -416 ], r10
    mov r10, [ rbp + -288 ]
    mov r11, [ rbp + -168 ]
    mov r10, r11 ## (SUB (TEMP _t128) (CONST 1))
    mov [ rbp + -168 ], r11
    mov [ rbp + -288 ], r10
    mov r10, [ rbp + -288 ]
    mov r11, [ rbp + -416 ]
    subq r10, r11 ## (SUB (TEMP _t128) (CONST 1))
    mov [ rbp + -416 ], r11
    mov [ rbp + -288 ], r10
    mov r10, [ rbp + -856 ]
    mov r11, [ rbp + -288 ]
    mov r10, r11 ## (MOVE (TEMP _t16) (SUB (TEMP _t128) (CONST 1)))
    mov [ rbp + -288 ], r11
    mov [ rbp + -856 ], r10
    mov r10, [ rbp + -848 ]
    mov r11, [ rbp + -424 ]
    mov r10, r11 ## (MOVE (TEMP _t15) (TEMP a))
    mov [ rbp + -424 ], r11
    mov [ rbp + -848 ], r10
    mov r10, [ rbp + -176 ]
    mov r11, [ rbp + -848 ]
    mov r10, r11 ## (MOVE (TEMP _t129) (TEMP _t15))
    mov [ rbp + -848 ], r11
    mov [ rbp + -176 ], r10
    mov r10, [ rbp + -296 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -296 ], r10
    mov r10, [ rbp + -304 ]
    mov r11, [ rbp + -176 ]
    mov r10, r11 ## (SUB (TEMP _t129) (CONST 8))
    mov [ rbp + -176 ], r11
    mov [ rbp + -304 ], r10
    mov r10, [ rbp + -304 ]
    mov r11, [ rbp + -296 ]
    subq r10, r11 ## (SUB (TEMP _t129) (CONST 8))
    mov [ rbp + -296 ], r11
    mov [ rbp + -304 ], r10
    mov r10, [ rbp + -864 ]
    mov r11, [ rbp + -304 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t17) (MEM (SUB (TEMP _t129) (CONST 8))))
    mov [ rbp + -304 ], r11
    mov [ rbp + -864 ], r10
    mov r10, [ rbp + -312 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -312 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -312 ]
    mov r10, r11 ## (MOVE (TEMP _t130) (CONST 0))
    mov [ rbp + -312 ], r11
    mov [ rbp + -96 ], r10
    mov r10, [ rbp + -96 ]
    mov r11, [ rbp + -856 ]
    cmpq r10, r11 ## (LEQ (TEMP _t130) (TEMP _t16))
    mov [ rbp + -856 ], r11
    mov [ rbp + -96 ], r10
    pushq rax
    mov rax, [ rbp + -344 ]
    setle al ## (LEQ (TEMP _t130) (TEMP _t16))
    mov [ rbp + -344 ], rax
    popq rax
    mov r10, [ rbp + -328 ]
    pushq rax
    mov rax, [ rbp + -344 ]
    movzx r10, al ## (LEQ (TEMP _t130) (TEMP _t16))
    mov [ rbp + -344 ], rax
    popq rax
    mov [ rbp + -328 ], r10
    mov r10, [ rbp + -112 ]
    mov r11, [ rbp + -328 ]
    mov r10, r11 ## (MOVE (TEMP _t132) (LEQ (TEMP _t130) (TEMP _t16)))
    mov [ rbp + -328 ], r11
    mov [ rbp + -112 ], r10
    mov r10, [ rbp + -104 ]
    mov r11, [ rbp + -856 ]
    mov r10, r11 ## (MOVE (TEMP _t131) (TEMP _t16))
    mov [ rbp + -856 ], r11
    mov [ rbp + -104 ], r10
    mov r10, [ rbp + -104 ]
    mov r11, [ rbp + -864 ]
    cmpq r10, r11 ## (LT (TEMP _t131) (TEMP _t17))
    mov [ rbp + -864 ], r11
    mov [ rbp + -104 ], r10
    pushq rax
    mov rax, [ rbp + -376 ]
    setl al ## (LT (TEMP _t131) (TEMP _t17))
    mov [ rbp + -376 ], rax
    popq rax
    mov r10, [ rbp + -360 ]
    pushq rax
    mov rax, [ rbp + -376 ]
    movzx r10, al ## (LT (TEMP _t131) (TEMP _t17))
    mov [ rbp + -376 ], rax
    popq rax
    mov [ rbp + -360 ], r10
    mov r10, [ rbp + -392 ]
    mov r11, [ rbp + -112 ]
    mov r10, r11 ## (AND (TEMP _t132) (LT (TEMP _t131) (TEMP _t17)))
    mov [ rbp + -112 ], r11
    mov [ rbp + -392 ], r10
    mov r10, [ rbp + -392 ]
    mov r11, [ rbp + -360 ]
    andq r10, r11 ## (AND (TEMP _t132) (LT (TEMP _t131) (TEMP _t17)))
    mov [ rbp + -360 ], r11
    mov [ rbp + -392 ], r10
    mov r10, [ rbp + -392 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t132) (LT (TEMP _t131) (TEMP _t17))) _l12)
    mov [ rbp + -392 ], r10
    je _l12 ## (CJUMP (AND (TEMP _t132) (LT (TEMP _t131) (TEMP _t17))) _l12)
_l13:
    callq _xi_out_of_bounds ## (CALL_STMT _t133 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -120 ]
    mov r10, rax ## (CALL_STMT _t133 (NAME _xi_out_of_bounds))
    mov [ rbp + -120 ], r10
_l12:
    mov r10, [ rbp + -136 ]
    mov r11, [ rbp + -848 ]
    mov r10, r11 ## (MOVE (TEMP _t135) (TEMP _t15))
    mov [ rbp + -848 ], r11
    mov [ rbp + -136 ], r10
    mov r10, [ rbp + -128 ]
    mov r11, [ rbp + -856 ]
    mov r10, r11 ## (MOVE (TEMP _t134) (TEMP _t16))
    mov [ rbp + -856 ], r11
    mov [ rbp + -128 ], r10
    mov r10, [ rbp + -408 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -408 ], r10
    mov r10, [ rbp + -672 ]
    mov r11, [ rbp + -128 ]
    mov r10, r11 ## (MUL (TEMP _t134) (CONST 8))
    mov [ rbp + -128 ], r11
    mov [ rbp + -672 ], r10
    mov r10, [ rbp + -672 ]
    mov r11, [ rbp + -408 ]
    imulq r10, r11 ## (MUL (TEMP _t134) (CONST 8))
    mov [ rbp + -408 ], r11
    mov [ rbp + -672 ], r10
    mov r10, [ rbp + -688 ]
    mov r11, [ rbp + -136 ]
    mov r10, r11 ## (ADD (TEMP _t135) (MUL (TEMP _t134) (CONST 8)))
    mov [ rbp + -136 ], r11
    mov [ rbp + -688 ], r10
    mov r10, [ rbp + -688 ]
    mov r11, [ rbp + -672 ]
    addq r10, r11 ## (ADD (TEMP _t135) (MUL (TEMP _t134) (CONST 8)))
    mov [ rbp + -672 ], r11
    mov [ rbp + -688 ], r10
    mov r10, [ rbp + -704 ]
    mov r11, [ rbp + -688 ]
    mov r10, [ r11 ] ## (MOVE (MEM (TEMP _t136)) (MEM (ADD (TEMP _t135) (MUL (TEMP _t134) (CONST 8)))))
    mov [ rbp + -688 ], r11
    mov [ rbp + -704 ], r10
    mov r10, [ rbp + -144 ]
    mov r11, [ rbp + -704 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t136)) (MEM (ADD (TEMP _t135) (MUL (TEMP _t134) (CONST 8)))))
    mov [ rbp + -704 ], r11
    mov [ rbp + -144 ], r10
    mov r10, [ rbp + -152 ]
    mov r11, [ rbp + -184 ]
    mov r10, r11 ## (MOVE (TEMP _t137) (TEMP len))
    mov [ rbp + -184 ], r11
    mov [ rbp + -152 ], r10
    mov r10, [ rbp + -712 ]
    mov r11, [ rbp + -152 ]
    mov r10, r11 ## (SUB (TEMP _t137) (TEMP k))
    mov [ rbp + -152 ], r11
    mov [ rbp + -712 ], r10
    mov r10, [ rbp + -712 ]
    mov r11, [ rbp + -456 ]
    subq r10, r11 ## (SUB (TEMP _t137) (TEMP k))
    mov [ rbp + -456 ], r11
    mov [ rbp + -712 ], r10
    mov r10, [ rbp + -80 ]
    mov r11, [ rbp + -712 ]
    mov r10, r11 ## (MOVE (TEMP _t138) (SUB (TEMP _t137) (TEMP k)))
    mov [ rbp + -712 ], r11
    mov [ rbp + -80 ], r10
    mov r10, [ rbp + -720 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -720 ], r10
    mov r10, [ rbp + -728 ]
    mov r11, [ rbp + -80 ]
    mov r10, r11 ## (SUB (TEMP _t138) (CONST 1))
    mov [ rbp + -80 ], r11
    mov [ rbp + -728 ], r10
    mov r10, [ rbp + -728 ]
    mov r11, [ rbp + -720 ]
    subq r10, r11 ## (SUB (TEMP _t138) (CONST 1))
    mov [ rbp + -720 ], r11
    mov [ rbp + -728 ], r10
    mov r10, [ rbp + -832 ]
    mov r11, [ rbp + -728 ]
    mov r10, r11 ## (MOVE (TEMP _t19) (SUB (TEMP _t138) (CONST 1)))
    mov [ rbp + -728 ], r11
    mov [ rbp + -832 ], r10
    mov r10, [ rbp + -872 ]
    mov r11, [ rbp + -424 ]
    mov r10, r11 ## (MOVE (TEMP _t18) (TEMP a))
    mov [ rbp + -424 ], r11
    mov [ rbp + -872 ], r10
    mov r10, [ rbp + -88 ]
    mov r11, [ rbp + -872 ]
    mov r10, r11 ## (MOVE (TEMP _t139) (TEMP _t18))
    mov [ rbp + -872 ], r11
    mov [ rbp + -88 ], r10
    mov r10, [ rbp + -736 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -736 ], r10
    mov r10, [ rbp + -744 ]
    mov r11, [ rbp + -88 ]
    mov r10, r11 ## (SUB (TEMP _t139) (CONST 8))
    mov [ rbp + -88 ], r11
    mov [ rbp + -744 ], r10
    mov r10, [ rbp + -744 ]
    mov r11, [ rbp + -736 ]
    subq r10, r11 ## (SUB (TEMP _t139) (CONST 8))
    mov [ rbp + -736 ], r11
    mov [ rbp + -744 ], r10
    mov r10, [ rbp + -192 ]
    mov r11, [ rbp + -744 ]
    mov r10, [ r11 ] ## (MOVE (TEMP _t20) (MEM (SUB (TEMP _t139) (CONST 8))))
    mov [ rbp + -744 ], r11
    mov [ rbp + -192 ], r10
    mov r10, [ rbp + -752 ]
    movabsq r10, 0 ## (CONST 0)
    mov [ rbp + -752 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -752 ]
    mov r10, r11 ## (MOVE (TEMP _t140) (CONST 0))
    mov [ rbp + -752 ], r11
    mov [ rbp + -16 ], r10
    mov r10, [ rbp + -16 ]
    mov r11, [ rbp + -832 ]
    cmpq r10, r11 ## (LEQ (TEMP _t140) (TEMP _t19))
    mov [ rbp + -832 ], r11
    mov [ rbp + -16 ], r10
    pushq rax
    mov rax, [ rbp + -656 ]
    setle al ## (LEQ (TEMP _t140) (TEMP _t19))
    mov [ rbp + -656 ], rax
    popq rax
    mov r10, [ rbp + -760 ]
    pushq rax
    mov rax, [ rbp + -656 ]
    movzx r10, al ## (LEQ (TEMP _t140) (TEMP _t19))
    mov [ rbp + -656 ], rax
    popq rax
    mov [ rbp + -760 ], r10
    mov r10, [ rbp + -32 ]
    mov r11, [ rbp + -760 ]
    mov r10, r11 ## (MOVE (TEMP _t142) (LEQ (TEMP _t140) (TEMP _t19)))
    mov [ rbp + -760 ], r11
    mov [ rbp + -32 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -832 ]
    mov r10, r11 ## (MOVE (TEMP _t141) (TEMP _t19))
    mov [ rbp + -832 ], r11
    mov [ rbp + -24 ], r10
    mov r10, [ rbp + -24 ]
    mov r11, [ rbp + -192 ]
    cmpq r10, r11 ## (LT (TEMP _t141) (TEMP _t20))
    mov [ rbp + -192 ], r11
    mov [ rbp + -24 ], r10
    pushq rax
    mov rax, [ rbp + -536 ]
    setl al ## (LT (TEMP _t141) (TEMP _t20))
    mov [ rbp + -536 ], rax
    popq rax
    mov r10, [ rbp + -520 ]
    pushq rax
    mov rax, [ rbp + -536 ]
    movzx r10, al ## (LT (TEMP _t141) (TEMP _t20))
    mov [ rbp + -536 ], rax
    popq rax
    mov [ rbp + -520 ], r10
    mov r10, [ rbp + -544 ]
    mov r11, [ rbp + -32 ]
    mov r10, r11 ## (AND (TEMP _t142) (LT (TEMP _t141) (TEMP _t20)))
    mov [ rbp + -32 ], r11
    mov [ rbp + -544 ], r10
    mov r10, [ rbp + -544 ]
    mov r11, [ rbp + -520 ]
    andq r10, r11 ## (AND (TEMP _t142) (LT (TEMP _t141) (TEMP _t20)))
    mov [ rbp + -520 ], r11
    mov [ rbp + -544 ], r10
    mov r10, [ rbp + -544 ]
    cmpq r10, 1 ## (CJUMP (AND (TEMP _t142) (LT (TEMP _t141) (TEMP _t20))) _l14)
    mov [ rbp + -544 ], r10
    je _l14 ## (CJUMP (AND (TEMP _t142) (LT (TEMP _t141) (TEMP _t20))) _l14)
_l15:
    callq _xi_out_of_bounds ## (CALL_STMT _t143 (NAME _xi_out_of_bounds))
    mov r10, [ rbp + -40 ]
    mov r10, rax ## (CALL_STMT _t143 (NAME _xi_out_of_bounds))
    mov [ rbp + -40 ], r10
_l14:
    mov r10, [ rbp + -56 ]
    mov r11, [ rbp + -872 ]
    mov r10, r11 ## (MOVE (TEMP _t145) (TEMP _t18))
    mov [ rbp + -872 ], r11
    mov [ rbp + -56 ], r10
    mov r10, [ rbp + -48 ]
    mov r11, [ rbp + -832 ]
    mov r10, r11 ## (MOVE (TEMP _t144) (TEMP _t19))
    mov [ rbp + -832 ], r11
    mov [ rbp + -48 ], r10
    mov r10, [ rbp + -552 ]
    movabsq r10, 8 ## (CONST 8)
    mov [ rbp + -552 ], r10
    mov r10, [ rbp + -560 ]
    mov r11, [ rbp + -48 ]
    mov r10, r11 ## (MUL (TEMP _t144) (CONST 8))
    mov [ rbp + -48 ], r11
    mov [ rbp + -560 ], r10
    mov r10, [ rbp + -560 ]
    mov r11, [ rbp + -552 ]
    imulq r10, r11 ## (MUL (TEMP _t144) (CONST 8))
    mov [ rbp + -552 ], r11
    mov [ rbp + -560 ], r10
    mov r10, [ rbp + -568 ]
    mov r11, [ rbp + -56 ]
    mov r10, r11 ## (ADD (TEMP _t145) (MUL (TEMP _t144) (CONST 8)))
    mov [ rbp + -56 ], r11
    mov [ rbp + -568 ], r10
    mov r10, [ rbp + -568 ]
    mov r11, [ rbp + -560 ]
    addq r10, r11 ## (ADD (TEMP _t145) (MUL (TEMP _t144) (CONST 8)))
    mov [ rbp + -560 ], r11
    mov [ rbp + -568 ], r10
    mov r10, [ rbp + -64 ]
    mov r11, [ rbp + -568 ]
    mov r10, r11 ## (MOVE (TEMP _t146) (ADD (TEMP _t145) (MUL (TEMP _t144) (CONST 8))))
    mov [ rbp + -568 ], r11
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -64 ]
    mov r11, [ rbp + -768 ]
    mov [ r10 ], r11 ## (MOVE (MEM (TEMP _t146)) (TEMP temp))
    mov [ rbp + -768 ], r11
    mov [ rbp + -64 ], r10
    mov r10, [ rbp + -72 ]
    mov r11, [ rbp + -456 ]
    mov r10, r11 ## (MOVE (TEMP _t147) (TEMP k))
    mov [ rbp + -456 ], r11
    mov [ rbp + -72 ], r10
    mov r10, [ rbp + -576 ]
    movabsq r10, 1 ## (CONST 1)
    mov [ rbp + -576 ], r10
    mov r10, [ rbp + -584 ]
    mov r11, [ rbp + -72 ]
    mov r10, r11 ## (ADD (TEMP _t147) (CONST 1))
    mov [ rbp + -72 ], r11
    mov [ rbp + -584 ], r10
    mov r10, [ rbp + -584 ]
    mov r11, [ rbp + -576 ]
    addq r10, r11 ## (ADD (TEMP _t147) (CONST 1))
    mov [ rbp + -576 ], r11
    mov [ rbp + -584 ], r10
    mov r10, [ rbp + -456 ]
    mov r11, [ rbp + -584 ]
    mov r10, r11 ## (MOVE (TEMP k) (ADD (TEMP _t147) (CONST 1)))
    mov [ rbp + -584 ], r11
    mov [ rbp + -456 ], r10
    jmp _l5 ## (JUMP (NAME _l5))
end__Ireverse_pai:
    addq rsp, 912
    mov rsp, rbp
    popq rbp
    retq
