package cyr7.cfg.asm.reg;

import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.ASMTempRegArg;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

final class SpillMemAllocator {

    // temp t is stored at memory location [RBP + 8(i + 1)]
    private final HashMap<String, Integer> tempToIndex;

    private final HashSet<ASMTempRegArg> newTemps;

    public SpillMemAllocator() {
        this.tempToIndex = new HashMap<>();
        this.newTemps = new HashSet<>();
    }

    public Integer indexOf(ASMTempArg temp) {
        if (tempToIndex.containsKey(temp.name)) {
            return tempToIndex.get(temp.name);
        } else {
            int index = tempToIndex.size();
            tempToIndex.put(temp.name, index);
            return index;
        }
    }

    public int allocatedTemps() {
        return tempToIndex.size();
    }

    public void clearNewTemps() {
        newTemps.clear();
    }

    public Set<ASMTempRegArg> newTemps() {
        return Collections.unmodifiableSet(newTemps);
    }

}
