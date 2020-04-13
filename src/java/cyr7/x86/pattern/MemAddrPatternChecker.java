//package cyr7.x86.pattern;
//
//import java.lang.StackWalker.Option;
//import java.util.Optional;
//
//import cyr7.ir.nodes.IRBinOp;
//import cyr7.ir.nodes.IRExpr;
//import cyr7.ir.nodes.IRTemp;
//import cyr7.visitor.MyIRVisitor;
//import cyr7.x86.asm.ASMAddrExpr;
//import cyr7.x86.asm.ASMMemArg;
//import cyr7.x86.asm.ASMTempArg;
//import cyr7.x86.asm.ASMTempArg.Size;
//import cyr7.x86.tiler.ComplexTiler;
//import cyr7.x86.tiler.TilerData;
//import cyr7.x86.tiler.TilerFactory;
//
//public class MemAddrPatternChecker
//        implements MyIRVisitor<Optional<TilerData>> {
//
////    public static instance
//
//    private ComplexTiler tiler;
//
//    public MemAddrPatternChecker(ComplexTiler tiler) {
//        this.tiler = tiler;
//    }
//
//    @Override
//    public Optional<TilerData> visit(IRBinOp n) {
//
//        /**
//         *
//         * instructions = []
//         * result = constantTimesTemp();
//         * if (result.isPresent()) {
//         *      arg = getArgument()
//         *      instructions.add(lea t_result, arg)
//         * }
//         *
//         *
//         */
//
//        /*
//         * Check if LHS is temp and if so, check that RHS is valid binop
//         *
//         * Valid Binop:
//         *      - Run constantTimesTemp()
//         *      - Run checkTempPlusConstantTimesTemp()
//         *      - Run checkTempPlus_ConstantTimesTemp_PlusTemp()
//         *      - Run checkTempPlusTemp()
//         *
//         */
//    }
//
//    private Optional<TilerData> constantTimesTemp(IRExpr n) {
//
//        return Optional.of(new ASMMemArg(new ASMAddrExpr(null))).get();
//        // Checks if LHS is a constant and RHS is a temp
//        // Checks if LHS is a temp and RHS is a temp
//    }
//
//    private Optional<TilerData> checkTempPlusConstantTimesTemp(IRExpr n) {
//        if (n instanceof IRBinOp) {
//            IRBinOp binop = (IRBinOp)n;
//            IRExpr lhs = binop.left();
//            IRExpr rhs = binop.right();
//
//            TilerData lhsTile = lhs.accept(tiler);
//            Optional<TilerData> rhsArgTile = this.constantTimesTemp(rhs);
//            // tile1 = t_left + constant * t_right
//            // tile1 = (t1 + t2) + constant * t_right
//            // t_left = t1 + t2
//
//            TilerData rhsTile = rhs.accept(tiler);
//            Optional<TilerData> lhsArgTile = this.constantTimesTemp(lhs);
//            // tile2 = constant * t_left + t_right
//
//            // Compare tile1 vs tile2 for lower cost?
//            // Return the tile with lower cost.
//            // If both are empty, return empty
//
//            if (lhs instanceof IRTemp) {
//                // Constant and temporary
//                Optional<ASMMemArg> rhsArg = this.constantTimesTemp(rhs);
//                if (rhsArg.isEmpty()) {
//                    return Optional.empty();
//                } else {
//                    ASMTempArg lhsTemp = new ASMTempArg(((IRTemp)lhs).name(), Size.QWORD);
//                    ASMMemArg memArgs = rhsArg.get();
//                    ASMMemArg addressArg = new ASMMemArg(new ASMAddrExpr(Optional.of(lhsTemp), memArgs.address.scale, memArgs.address.index, 0));
//                    return Optional.of(addressArg);
//                }
//            } else if (rhs instanceof IRTemp) {
//                Optional<ASMMemArg> lhsArg = this.constantTimesTemp(lhs);
//            } else {
//                return Optional.empty();
//            }
//        }
//        return Optional.empty();
//        // Checks LHS is temp and for RHS run consantTimesTemp()
//        // Checks for LHS run constantTimesTemp() and RHS is temp
//    }
//
//    private Optional<ASMMemArg> checkTempPlusTemp() {
//        // Check LHS is temp and RHS is temp
//    }
//
//}
