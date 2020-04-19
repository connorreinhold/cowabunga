package cyr7.x86.asm;

import java.util.List;

public interface ASMInstrTypeI {

    List<Integer> defs();
    List<Integer> uses();

}
