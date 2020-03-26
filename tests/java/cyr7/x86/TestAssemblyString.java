package cyr7.x86;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import cyr7.x86.asm.ASMReg;

public class TestAssemblyString {

    /**
     * A simple test case checking that the assembly form of registers are correct.
     */
    @Test
    void testRegArgs() {
        var registers = ASMReg.values();
        for (ASMReg r : registers) {
            String intelSyntaxString = r.getIntelArg();
            switch (r) {
                case R10:
                    assertEquals("r10", intelSyntaxString);
                    break;
                case R11:
                    assertEquals("r11", intelSyntaxString);
                    break;
                case R12:
                    assertEquals("r12", intelSyntaxString);
                    break;
                case R13:
                    assertEquals("r13", intelSyntaxString);
                    break;
                case R14:
                    assertEquals("r14", intelSyntaxString);
                    break;
                case R15:
                    assertEquals("r15", intelSyntaxString);
                    break;
                case R8:
                    assertEquals("r8", intelSyntaxString);
                    break;
                case R9:
                    assertEquals("r9", intelSyntaxString);
                    break;
                case RAX:
                    assertEquals("rax", intelSyntaxString);
                    break;
                case RBP:
                    assertEquals("rbp", intelSyntaxString);
                    break;
                case RBX:
                    assertEquals("rbx", intelSyntaxString);
                    break;
                case RCX:
                    assertEquals("rcx", intelSyntaxString);
                    break;
                case RDI:
                    assertEquals("rdi", intelSyntaxString);
                    break;
                case RDX:
                    assertEquals("rdx", intelSyntaxString);
                    break;
                case RSI:
                    assertEquals("rsi", intelSyntaxString);
                    break;
                case RSP:
                    assertEquals("rsp", intelSyntaxString);
                    break;
                default:
                    break;
            }
        }
    }

}