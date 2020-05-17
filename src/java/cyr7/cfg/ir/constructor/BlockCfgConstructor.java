package cyr7.cfg.ir.constructor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import cyr7.cfg.ir.nodes.CFGBlockNode;
import cyr7.cfg.ir.nodes.CFGCallNode;
import cyr7.cfg.ir.nodes.CFGIfNode;
import cyr7.cfg.ir.nodes.CFGMemAssignNode;
import cyr7.cfg.ir.nodes.CFGNode;
import cyr7.cfg.ir.nodes.CFGReturnNode;
import cyr7.cfg.ir.nodes.CFGSelfLoopNode;
import cyr7.cfg.ir.nodes.CFGStartNode;
import cyr7.cfg.ir.nodes.CFGStubNode;
import cyr7.cfg.ir.nodes.CFGVarAssignNode;
import cyr7.ir.block.BasicBlock;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRCJump;
import cyr7.ir.nodes.IRCall;
import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRESeq;
import cyr7.ir.nodes.IRExp;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRJump;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRMem;
import cyr7.ir.nodes.IRMove;
import cyr7.ir.nodes.IRName;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRStmt;
import cyr7.ir.nodes.IRTemp;
import cyr7.visitor.MyIRVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;
import polyglot.util.Pair;

public class BlockCfgConstructor {

    private BlockCfgConstructor() {}

    public static CFGStartNode construct(List<List<BasicBlock>> blocks) {
        final Map<String, CFGNode> labelToCFG = new HashMap<>();
        final Queue<Pair<CFGStubNode, String>> jumpTargetFromCFG = new ArrayDeque<>();
        Optional<CFGNode> successor = Optional.empty();
        for (int i = blocks.size() - 1; i >= 1; i--) {
            successor = buildCfgComponent(blocks.get(i),
                    labelToCFG, jumpTargetFromCFG, successor);
        }
        final Optional<CFGNode> topNode =
                buildCfgComponent(blocks.get(0), labelToCFG,
                                  jumpTargetFromCFG, successor);
        final CFGStartNode startNode;
        if (topNode.isPresent()) {
            startNode = new CFGStartNode(new Location(-1, -1), topNode.get());
        } else {
            throw new AssertionError("Content of program is empty. "
                                    + "No return node found");
        }

        /**
         * Join the unconnected jump nodes.
         */
        while (!jumpTargetFromCFG.isEmpty()) {
            final var nextPair = jumpTargetFromCFG.poll();
            final CFGStubNode stub = nextPair.part1();
            final String target = nextPair.part2();

            if (labelToCFG.containsKey(target)) {
                CFGNode targetNode = labelToCFG.get(target);
                for (CFGNode incoming: stub.in()) {
                    // Target node may be itself, which
                    // indicates an empty loop coming from the parent node.
                    if (targetNode == stub) {
                        // Infinite loop...
                        var selfLoop = new CFGSelfLoopNode();
                        incoming.replaceOutEdge(stub, selfLoop);
                    } else {
                        incoming.replaceOutEdge(stub, targetNode);
                    }
                }
            } else {
                throw new UnsupportedOperationException(
                        "Target label was never found in the program.");
            }
        }
        return startNode;
    }

    /**
     * Condenses a sequence of basic blocks into CFG nodes.
     * @param sequence
     * @param labelToCFG
     * @param jumpTargetFromCFG
     * @param successor
     * @return
     */
    private static Optional<CFGNode> buildCfgComponent(List<BasicBlock> sequence,
            Map<String, CFGNode> labelToCFG,
            Queue<Pair<CFGStubNode, String>> jumpTargetFromCFG,
            Optional<CFGNode> successor) {
        for (int j = sequence.size() - 1; j >= 0; j--) {
            final var visitor = new BlockCfgConstructorVisitor();
            final BasicBlock block = sequence.get(j);
            final var result = visitor.execute(block.stmts,
                                        labelToCFG,
                                        jumpTargetFromCFG,
                                        successor);
            successor = Optional.of(result.part2());
            result.part1().forEach(label ->
                labelToCFG.put(label, result.part2())
            );
        }
        return successor;
    }

    private static class BlockCfgConstructorVisitor
                            implements MyIRVisitor<CFGNode>{

        private Map<String, CFGNode> labelToCFG;
        private Queue<Pair<CFGStubNode, String>> jumpTargetFromCFG;

        /**
         * The out node of the CFG Block, which is to be set as the out node
         * when the CFG Block node is instantiated.
         * <p>
         * If the out node is unknown i.e. a block with no jump statements,
         * do not create a block node and return the successor.
         */
        private Optional<CFGNode> outNode;

        /**
         * An accumulator CFG pointer.
         */
        private CFGNode successor;

        /**
         * The starting CFG Node of previously computed CFG-component.
         */
        private CFGNode nodeOfNextBlock;

        /**
         * The set of variables that are defined inside a block.
         */
        private Set<String> variablesDefined;

        /**
         * The set of labels placed at the beginnings of basic blocks.
         */
        private Set<String> labelSets;

        /**
         * The out node of the last node in the block of nodes.
         */
        private CFGStubNode finalStub;

        /**
         * Number of linear CFG nodes.
         */
        private long depth;

        protected BlockCfgConstructorVisitor() {
            this.labelToCFG = new HashMap<>();
            this.jumpTargetFromCFG = new LinkedList<>();
            this.outNode = Optional.empty();
            this.labelSets = new HashSet<>();
        }

        private CFGStubNode createStubNode() {
            return new CFGStubNode();
        }

        /**
         * Creates a linear CFG node tree, associated with the label, i.e. the
         * first statement in the list of statements.
         *
         * @param stmts
         * @param labelToCFG A mapping from labels to CFG trees.
         * @param jumpTargetFromCFG A queue of stub nodes and a jump target pairs
         *                          where the jump target is the CFG node that
         *                          will replace the stub node.
         * @param successor The CFG node that would come immediately after
         *                  this linear CFG tree.
         */
        protected Pair<Set<String>, CFGNode> execute(List<IRStmt> stmts,
                Map<String, CFGNode> labelToCFG,
                Queue<Pair<CFGStubNode, String>> jumpTargetFromCFG,
                Optional<CFGNode> successor) {

            this.depth = 0;
            this.labelToCFG = labelToCFG;
            this.jumpTargetFromCFG = jumpTargetFromCFG;
            this.variablesDefined = new HashSet<>();
            this.nodeOfNextBlock = successor.map(s -> s)
                                .orElse(new CFGReturnNode(new Location(-1, -1)));

            final var stmtArray = new ArrayList<>(stmts);
            this.finalStub = this.createStubNode();
            this.successor = finalStub;
            for (int i = stmtArray.size() - 1; i >= 0; i--) {
                var stmt = stmtArray.get(i);
                this.successor = stmt.accept(this);
            }
            if (this.successor == finalStub && this.outNode.isEmpty()) {
                // No blocks or instructions were made
                return new Pair<>(labelSets, this.nodeOfNextBlock);
            } else if (this.successor == finalStub && this.outNode.isPresent()) {
                // There is only a CJump or a Return node in this block.
                return new Pair<>(labelSets, this.outNode.get());
            } else if (this.depth == 1 && this.outNode.isEmpty()) {
                // There is a single instruction. Easier use to specific CFG
                // node than to create a block. No CJump or Return node.
                this.successor.replaceOutEdge(finalStub, this.nodeOfNextBlock);
                return new Pair<>(labelSets, this.successor);
            } else if (this.depth == 1 && this.outNode.isPresent()) {
                // There is a single instruction. Easier use to specific CFG
                // node than to create a block. There is a CJump or Return node.
                this.successor.replaceOutEdge(finalStub, this.outNode.get());
                return new Pair<>(labelSets, this.successor);
            } else if (this.outNode.isEmpty()) {
                // Create a block. There is no CJump or Return node.
                final var block = new CFGBlockNode(new Location(-1, -1),
                                         this.successor,
                                         this.nodeOfNextBlock);
                return new Pair<>(labelSets, block);
            } else {
             // Create a block. There is a CJump or Return node at the end.
                final var block = new CFGBlockNode(new Location(-1, -1),
                        this.successor,
                        this.outNode.get());
                return new Pair<>(labelSets, block);
            }
        }

        /**
         * May return the stub node.
         */
        @Override
        public CFGNode visit(IRLabel n) {
            this.labelSets.add(n.name());
            return successor;
        }

        @Override
        public CFGNode visit(IRMove n) {
            this.depth++;
            if (n.target() instanceof IRTemp) {
                IRTemp temp = (IRTemp) n.target();
                if (this.variablesDefined.contains(temp.name())) {
                    if (this.successor == finalStub) {
                        throw new AssertionError("This is not possible. No other variable"
                                + " assign nodes have been traversed in this block.");
                    } else if (this.depth == 2 && this.outNode.isEmpty()) {
                        // There is one definition node only, i.e. the successor.
                        this.successor.replaceOutEdge(finalStub, this.nodeOfNextBlock);
                        this.nodeOfNextBlock = this.successor;
                        this.outNode = Optional.empty();
                        this.finalStub = this.createStubNode();
                        this.depth = 1;
                        this.variablesDefined.clear();
                        this.variablesDefined.add(temp.name());
                        return new CFGVarAssignNode(n.location(),
                                temp.name(), n.source(), this.finalStub);
                    } else if (this.depth == 2 && this.outNode.isPresent()) {
                        // There is a definition and an out node.
                        this.successor.replaceOutEdge(finalStub, this.outNode.get());
                        this.nodeOfNextBlock = this.successor;
                        this.outNode = Optional.empty();
                        this.finalStub = this.createStubNode();
                        this.depth = 1;
                        this.variablesDefined.clear();
                        this.variablesDefined.add(temp.name());
                        return new CFGVarAssignNode(n.location(),
                                temp.name(), n.source(), this.finalStub);
                    } else if (this.outNode.isEmpty()) {
                        // Create a block
                        final var block = new CFGBlockNode(new Location(-1, -1),
                                                 this.successor,
                                                 this.nodeOfNextBlock);
                        this.nodeOfNextBlock = block;
                        this.outNode = Optional.empty();
                        this.finalStub = this.createStubNode();
                        this.depth = 1;
                        this.variablesDefined.clear();
                        this.variablesDefined.add(temp.name());
                        return new CFGVarAssignNode(n.location(),
                                temp.name(), n.source(), this.finalStub);
                    } else {
                        final var block = new CFGBlockNode(new Location(-1, -1),
                                                this.successor,
                                                this.outNode.get());
                        this.nodeOfNextBlock = block;
                        this.outNode = Optional.empty();
                        this.finalStub = this.createStubNode();
                        this.depth = 1;
                        this.variablesDefined.clear();
                        this.variablesDefined.add(temp.name());
                        return new CFGVarAssignNode(n.location(),
                                temp.name(), n.source(), this.finalStub);
                    }
                } else {
                    this.variablesDefined.add(temp.name());
                    return new CFGVarAssignNode(n.location(),
                            temp.name(), n.source(), successor);
                }
            } else {
                return new CFGMemAssignNode(n.location(),
                            n.target(), n.source(), successor);
            }
        }

        @Override
        public CFGNode visit(IRCallStmt n) {
            this.depth++;
            if (this.variablesDefined.stream().anyMatch(def -> {
                return n.collectors().contains(def) && !def.equals("_");
            })) {
                if (this.successor == finalStub) {
                    throw new AssertionError("This is not possible. No other variable"
                            + " assign nodes have been traversed in this block.");
                } else if (this.depth == 2 && this.outNode.isEmpty()) {
                    // There is one definition node only, i.e. the successor.
                    this.successor.replaceOutEdge(finalStub, this.nodeOfNextBlock);
                    this.nodeOfNextBlock = this.successor;
                    this.outNode = Optional.empty();
                    this.finalStub = this.createStubNode();
                    this.depth = 1;
                    this.variablesDefined.clear();
                    this.variablesDefined.addAll(n.collectors());
                    return new CFGCallNode(n.location(), n, this.finalStub);
                } else if (this.depth == 2 && this.outNode.isPresent()) {
                    // There is a definition and an out node.
                    this.successor.replaceOutEdge(finalStub, this.outNode.get());
                    this.nodeOfNextBlock = this.successor;
                    this.outNode = Optional.empty();
                    this.finalStub = this.createStubNode();
                    this.depth = 1;
                    this.variablesDefined.clear();
                    this.variablesDefined.addAll(n.collectors());
                    return new CFGCallNode(n.location(), n, this.finalStub);
                } else if (this.outNode.isEmpty()) {
                    // Create a block
                    final var block = new CFGBlockNode(new Location(-1, -1),
                                             this.successor,
                                             this.nodeOfNextBlock);
                    this.nodeOfNextBlock = block;
                    this.outNode = Optional.empty();
                    this.finalStub = this.createStubNode();
                    this.depth = 1;
                    this.variablesDefined.clear();
                    this.variablesDefined.addAll(n.collectors());
                    return new CFGCallNode(n.location(), n, this.finalStub);
                } else {
                    final var block = new CFGBlockNode(new Location(-1, -1),
                            this.successor,
                            this.outNode.get());
                    this.nodeOfNextBlock = block;
                    this.outNode = Optional.empty();
                    this.finalStub = this.createStubNode();
                    this.depth = 1;
                    this.variablesDefined.clear();
                    this.variablesDefined.addAll(n.collectors());
                    return new CFGCallNode(n.location(), n, this.finalStub);
                }
            } else {
                return new CFGCallNode(n.location(), n, successor);
            }
        }

        @Override
        public CFGNode visit(IRCJump n) {
            String trueBranch = n.trueLabel();
            if (this.labelToCFG.containsKey(trueBranch)) {
                outNode = Optional.of(new CFGIfNode(n.location(),
                        this.labelToCFG.get(trueBranch),
                        this.nodeOfNextBlock, n.cond()));
                return successor;
            } else {
                // Create stub node, and connect target to the stub node.
                CFGStubNode stub = this.createStubNode();
                outNode = Optional.of(new CFGIfNode(n.location(),
                        stub, this.nodeOfNextBlock, n.cond()));
                this.jumpTargetFromCFG.add(new Pair<>(stub, trueBranch));
                return successor;
            }
        }

        @Override
        public CFGNode visit(IRJump n) {
            if (n.target() instanceof IRName) {
                String target = ((IRName) n.target()).name();
                if (this.labelToCFG.containsKey(target)) {
                    // Make successor the target node.
                    outNode = Optional.of(this.labelToCFG.get(target));
                    return successor;
                } else {
                    // Create a stub node for later computation
                    CFGStubNode stub = this.createStubNode();
                    this.jumpTargetFromCFG.add(new Pair<>(stub, target));
                    outNode = Optional.of(stub);
                    return successor;
                }
            } else {
                throw new UnsupportedOperationException(
                        "IRJump contains a non-name target.");
            }
        }

        @Override
        public CFGNode visit(IRReturn n) {
            outNode = Optional.of(new CFGReturnNode(n.location()));
            return successor;
        }

        @Override
        public CFGNode visit(IRCompUnit n) {
            throw new UnsupportedOperationException(
                    "Cannot use IRCompUnit in this visitor.");
        }

        @Override
        public CFGNode visit(IRExp n) {
            throw new UnsupportedOperationException("Cannot use IRExp in LIR.");
        }

        @Override
        public CFGNode visit(IRFuncDecl n) {
            throw new UnsupportedOperationException(
                    "Cannot use IRFuncDecl in this visitor.");
        }

        @Override
        public CFGNode visit(IRBinOp n) {
            throw new UnsupportedOperationException(
                    "Cannot use IR expressions in this visitor.");
        }

        @Override
        public CFGNode visit(IRCall n) {
            throw new UnsupportedOperationException("Cannot use IRCall in LIR.");
        }

        @Override
        public CFGNode visit(IRConst n) {
            throw new UnsupportedOperationException(
                    "Cannot use IR expressions in this visitor.");
        }

        @Override
        public CFGNode visit(IRESeq n) {
            throw new UnsupportedOperationException("Cannot use IRESeq in LIR.");
        }

        @Override
        public CFGNode visit(IRMem n) {
            throw new UnsupportedOperationException(
                    "Cannot use IR expressions in this visitor.");
        }

        @Override
        public CFGNode visit(IRName n) {
            throw new UnsupportedOperationException(
                    "There are no reasons to use IRName other than it being inside of IRJump or IRCallStmt.");
        }

        @Override
        public CFGNode visit(IRTemp n) {
            throw new UnsupportedOperationException(
                    "Cannot use IR expressions in this visitor.");
        }

        @Override
        public CFGNode visit(IRSeq n) {
            throw new UnsupportedOperationException(
                    "Cannot enter the IRSeq visitor.");
        }

    }

}
