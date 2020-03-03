package cyr7.ir;

import cyr7.ir.nodes.IRLabel;

public final class LabelGenerator {

    private static int counter = 0;

    public static IRLabel nextLabel() {
        return new IRLabel(String.valueOf(counter));
    }

    private LabelGenerator() { }

}
