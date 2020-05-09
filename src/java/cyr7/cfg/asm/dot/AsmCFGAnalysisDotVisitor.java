package cyr7.cfg.asm.dot;

import cyr7.cfg.asm.nodes.AsmCFGIfNode;
import cyr7.cfg.asm.nodes.AsmCFGNode;
import cyr7.cfg.asm.nodes.AsmCFGOpNode;
import cyr7.cfg.asm.nodes.AsmCFGReturnNode;
import cyr7.cfg.asm.nodes.AsmCFGStartNode;
import cyr7.cfg.asm.visitor.AsmCFGVisitor;
import polyglot.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AsmCFGAnalysisDotVisitor extends AsmCFGDotVisitor {

    private final Function<AsmCFGNode, String> analysis;

    public AsmCFGAnalysisDotVisitor(Function<AsmCFGNode, String> analysis) {
        this.analysis = analysis;
    }

    @Override
    protected String toString(AsmCFGNode n) {
        return super.toString(n) + "\\n" + analysis.apply(n);
    }

}