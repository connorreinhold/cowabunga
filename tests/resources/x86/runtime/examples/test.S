#include "defs.h"
.intel_syntax noprefix
.text
.globl FUNC(Imain_paai)
.align 4

FUNC(Imain_paai):
pushq rbp
movq rbp, rsp
subq rbp, 120
_l3:
movq r10, [ rbp + 56 ]
movq r10, rdi
movq [ rbp + 56 ], r10
movq r10, [ rbp + 80 ]
movq r11, [ rbp + 56 ]
movq r10, r11
movq [ rbp + 80 ], r10
movq [ rbp + 56 ], r11
movq r10, [ rbp + 112 ]
movabsq r10, 8
movq [ rbp + 112 ], r10
movq r10, [ rbp + 88 ]
movq r11, [ rbp + 80 ]
movq r10, r11
movq [ rbp + 88 ], r10
movq [ rbp + 80 ], r11
movq r10, [ rbp + 88 ]
movq r11, [ rbp + 112 ]
subq r10, r11
movq [ rbp + 88 ], r10
movq [ rbp + 112 ], r11
pushq rax
movq r10, [ rbp + 88 ]
movq rax, r10
movq [ rbp + 88 ], r10
movq r10, [ rbp + 104 ]
movq r10, [ rax ]
movq [ rbp + 104 ], r10
popq rax
movq r10, [ rbp + 72 ]
movq r11, [ rbp + 104 ]
movq r10, r11
movq [ rbp + 72 ], r10
movq [ rbp + 104 ], r11
movq r10, [ rbp + 8 ]
movabsq r10, 2
movq [ rbp + 8 ], r10
movq r10, [ rbp + 72 ]
movq r11, [ rbp + 8 ]
cmpq r10, r11
movq [ rbp + 72 ], r10
movq [ rbp + 8 ], r11
movq r10, [ rbp + 16 ]
setle r10l
movq [ rbp + 16 ], r10
movq r10, [ rbp + 16 ]
xorq r10, 1
movq [ rbp + 16 ], r10
movq r10, [ rbp + 16 ]
cmpq r10, 1
movq [ rbp + 16 ], r10
je _l0
_l1:
_l2:
movq r10, [ rbp + 32 ]
movabsq r10, 10
movq [ rbp + 32 ], r10
movq r10, [ rbp + 40 ]
movq r11, [ rbp + 32 ]
movq r10, r11
movq [ rbp + 40 ], r10
movq [ rbp + 32 ], r11
movq r10, [ rbp + 24 ]
movabsq r10, 42
movq [ rbp + 24 ], r10
movq r10, [ rbp + 48 ]
movq r11, [ rbp + 24 ]
movq r10, r11
movq [ rbp + 48 ], r10
movq [ rbp + 24 ], r11
movq r10, [ rbp + 96 ]
movq r11, [ rbp + 40 ]
movq r10, r11
movq [ rbp + 96 ], r10
movq [ rbp + 40 ], r11
movq r10, [ rbp + 120 ]
movq r11, [ rbp + 96 ]
movq r10, r11
movq [ rbp + 120 ], r10
movq [ rbp + 96 ], r11
movq r10, [ rbp + 120 ]
movq r11, [ rbp + 48 ]
addq r10, r11
movq [ rbp + 120 ], r10
movq [ rbp + 48 ], r11
movq r10, [ rbp + 64 ]
movq r11, [ rbp + 120 ]
movq r10, r11
movq [ rbp + 64 ], r10
movq [ rbp + 120 ], r11
jmp end__Imain_paai
_l0:
jmp end__Imain_paai
end__Imain_paai:
addq rsp, 120
movq rsp, rbp
popq rbp
retq
