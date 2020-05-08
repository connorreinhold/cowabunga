package cyr7.cfg.asm.reg;

import cyr7.x86.asm.ASMTempRegArg;

import java.util.LinkedList;

final class Worklists {

    public final LinkedList<ASMTempRegArg> initial = new LinkedList<>();
    public final LinkedList<ASMTempRegArg> simplify = new LinkedList<>();
    public final LinkedList<Integer> moves = new LinkedList<>();
    public final LinkedList<ASMTempRegArg> freeze = new LinkedList<>();
    public final LinkedList<ASMTempRegArg> spill = new LinkedList<>();

}
