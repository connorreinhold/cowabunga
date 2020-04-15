package cyr7.x86.tiler;

import cyr7.cli.CLI;
import cyr7.ir.IdGenerator;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRCJump;
import cyr7.ir.nodes.IRCall;
import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRESeq;
import cyr7.ir.nodes.IRExp;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRJump;
import cyr7.ir.nodes.IRLabel;
import cyr7.ir.nodes.IRMem;
import cyr7.ir.nodes.IRMove;
import cyr7.ir.nodes.IRName;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRSeq;
import cyr7.ir.nodes.IRTemp;
import cyr7.x86.asm.ASMAddrExpr.ScaleValues;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.asm.ASMTempArg.Size;
import cyr7.x86.pattern.BiPatternBuilder;
import cyr7.x86.pattern.PatternGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ComplexTiler extends BasicTiler {

    private static final Comparator<TilerData> byCost
        = Comparator.comparingInt(lhs -> lhs.tileCost);

    public ComplexTiler(IdGenerator generator, int numRetValues,
                        String returnLbl,
                        Optional<ASMTempArg> additionalRetValAddress,
                        boolean stack16ByteAligned) {
        super(generator, numRetValues, returnLbl, additionalRetValAddress,
            stack16ByteAligned);

        disableBasicTilerMemoizeResults();
        PatternGenerator.instantiate(this);
    }

    @Override
    public TilerData visit(IRBinOp n) {
        ASMLineFactory make = new ASMLineFactory(n);
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        List<TilerData> possibleTilings = new ArrayList<>();

        switch (n.opType()) {

            case MUL: {
                

                
            
            case ADD: {     
                var tempPlusTempPattern = BiPatternBuilder
                .left()
                .instOf(ASMTempArg.class)
                .right()
                .instOf(ASMTempArg.class)
                .finish()
                .mappingLeft(IRExpr.class,
                    (Function<IRExpr, ASMArg>)
                        node -> node.accept(this).result.get())
                .mappingRight(IRExpr.class,
                    (Function<IRExpr, ASMArg>)
                        node -> node.accept(this).result.get());
                
               
                
                
                
                
                
                

                var constTemp = BiPatternBuilder
                    .left()
                    .instOf(IRConst.class)
                    .and(x -> x.constant() == 1 || x.constant() == 2 || x.constant() == 4 || x.constant() == 8)
                    .right()
                    .instOf(ASMTempArg.class)
                    .finish()
                    .mappingRight(IRExpr.class,
                        (Function<IRExpr, ASMArg>)
                            node -> node.accept(this).result.get())
                    .enableCommutes();

                var constTempPlusN = BiPatternBuilder
                    .left()
                    .instOf(IRBinOp.class)
                    .and(x -> x.opType() == OpType.MUL)
                    .and(x -> constTemp.matches(new Object[] { x.left(), x.right() }))
                    .right()
                    .instOf(IRConst.class)
                    .finish()
                    .enableCommutes();

                if (constTempPlusN.matches(new Object[]{n.left(), n.right()})) {
                    IRConst constArg = constTemp.leftObj();
                    ASMTempArg tempArg = constTemp.rightObj();
                    IRConst nArg = constTempPlusN.rightObj();

                    ASMTempArg resultTemp = arg.temp(generator.newTemp(), Size.QWORD);
                    List<ASMLine> insns = new ArrayList<>();
                    insns.addAll(constTemp.preMapRight().getOptimalTiling().optimalInstructions);
>>>>>>> 5a4ab1483c0110d97068401c87da0a0774233ad5

                    ASMLine line = make.Lea(
                        resultTemp,
                        arg.mem(arg.addr(
<<<<<<< HEAD
                            Optional.of(arg.temp(lhs.name, Size.QWORD)),
                            ScaleValues.ONE,
                            Optional.of(arg.temp(rhs.name, Size.QWORD)),
                            0
=======
                            Optional.empty(),
                            ScaleValues.fromConst(constArg.constant()).get(),
                            Optional.of(arg.temp(tempArg.name, Size.QWORD)),
                            nArg.constant()
>>>>>>> 5a4ab1483c0110d97068401c87da0a0774233ad5
                        ))
                    );
                    insns.add(line);

<<<<<<< HEAD
                   possibleTilings.add(
=======
                    possibleTilings.add(
>>>>>>> 5a4ab1483c0110d97068401c87da0a0774233ad5
                        new TilerData(1,
                            insns,
                            Optional.of(resultTemp)
                        ));
<<<<<<< HEAD
                } else if (false) {
                    
                }
                
                
                
                
                
                
            
=======
                }
            }
>>>>>>> 5a4ab1483c0110d97068401c87da0a0774233ad5
        }

        possibleTilings.add(super.visit(n));

        TilerData optimal = possibleTilings.stream().min(byCost).get();
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRCall n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRConst n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRESeq n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRMem n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRName n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRTemp n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRCallStmt n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRCJump n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRCompUnit n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRExp n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRFuncDecl n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRJump n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRLabel n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRMove n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRReturn n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

    @Override
    public TilerData visit(IRSeq n) {
        if (n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }

        TilerData optimal = super.visit(n);
        n.setOptimalTilingOnce(optimal);
        return optimal;
    }

}
