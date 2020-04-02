#include "defs.h"
.intel_syntax noprefix
.text
.globl FUNC(Imain_paai)
.align 4

FUNC(Imain_paai):
pushq rbp
movq rbp, rsp
subq rsp, 48
_l0:
movq r10, [ rbp + 8 ]
movq r10, rdi
movq [ rbp + 8 ], r10
movq r10, [ rbp + 24 ]
movabsq r10, 1
movq [ rbp + 24 ], r10
movq r10, [ rbp + 32 ]
movq r11, [ rbp + 24 ]
movq r10, r11
movq [ rbp + 32 ], r10
movq [ rbp + 24 ], r11
movq r10, [ rbp + 40 ]
movabsq r10, 1
movq [ rbp + 40 ], r10
movq r10, [ rbp + 48 ]
movq r11, [ rbp + 32 ]
movq r10, r11
movq [ rbp + 48 ], r10
movq [ rbp + 32 ], r11
movq r10, [ rbp + 48 ]
movq r11, [ rbp + 40 ]
addq r10, r11
movq [ rbp + 48 ], r10
movq [ rbp + 40 ], r11
movq r10, [ rbp + 16 ]
movq r11, [ rbp + 48 ]
movq r10, r11
movq [ rbp + 16 ], r10
movq [ rbp + 48 ], r11
jmp end__Imain_paai
end__Imain_paai:
addq rsp, 48
movq rsp, rbp
popq rbp
retq
