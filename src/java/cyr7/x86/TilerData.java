package cyr7.x86;

import cyr7.ir.nodes.IRTemp;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMInstr;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class TilerData {

    /**
     * The cost of the optimal tile formed with {@code this} node as the root of
     * an IR tree.
     */
    public final int tileCost;

    /**
     * The optimal list of instructions representing the optimal tile formed
     * with {@code this} node as the root of an IR tree.
     * <p>
     * The instructions are in order from lowest index to highest index. This
     * means the first instruction of the list is the first instruction executed
     * in assembly.
     */
    public final List<ASMInstr> optimalInstructions;

    public final Optional<ASMArg> result;

    TilerData(int tileCost, List<ASMInstr> optimalInstructions, Optional<ASMArg> result) {
        this.tileCost = tileCost;
        this.optimalInstructions = optimalInstructions;
        this.result = result;
    }

}
