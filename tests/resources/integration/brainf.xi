use io
use conv

main(args: int[][]) {
    interpret(args[1])
}

interpret(prog: int[]) {
    mem: int[] = mem_init(1024)

    mem_ptr: int = 0
    insn_ptr: int = 0
    while insn_ptr < length(prog) {
        if prog[insn_ptr] == '>' {
            mem_ptr = mem_ptr + 1
        } else if prog[insn_ptr] == '<' {
            mem_ptr = mem_ptr - 1
        } else if prog[insn_ptr] == '+' {
            incr(mem, mem_ptr)
        } else if prog[insn_ptr] == '-' {
            decr(mem, mem_ptr)
        } else if prog[insn_ptr] == '.' {
            print({ mem[mem_ptr] })
        } else if prog[insn_ptr] == ',' {
            c: int = getchar()
            if c < 0 {
                return
            }
            mem[mem_ptr] = c
        } else if prog[insn_ptr] == '[' {
            if mem[mem_ptr] == 0
                insn_ptr = seek_forward(prog, insn_ptr)
        } else if prog[insn_ptr] == ']' {
            if mem[mem_ptr] != 0
                insn_ptr = seek_backward(prog, insn_ptr)
        }

        insn_ptr = insn_ptr + 1
    }
}

mem_init(size: int): int[] {
    mem: int[size]
    i: int = 0
    while i < length(mem) {
        mem[i] = 0
        i = i + 1
    }
    return mem
}

incr(mem: int[], index: int) {
    mem[index] = mem[index] + 1
    if mem[index] >= 256 mem[index] = 0
}

decr(mem: int[], index: int) {
    mem[index] = mem[index] - 1
    if mem[index] < 0 mem[index] = 255
}

seek_forward(prog: int[], insn_ptr: int): int {
    stack: int = 1
    while stack > 0 {
        insn_ptr = insn_ptr + 1

        if prog[insn_ptr] == '['
            stack = stack + 1
        else if prog[insn_ptr] == ']'
            stack = stack - 1
    }
    return insn_ptr
}

seek_backward(prog: int[], insn_ptr: int): int {
    stack: int = 1
    while stack > 0 {
        insn_ptr = insn_ptr - 1

        if prog[insn_ptr] == ']'
            stack = stack + 1
        else if prog[insn_ptr] == '['
            stack = stack - 1
    }
    return insn_ptr
}
