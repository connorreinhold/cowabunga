//package cyr7.x86.pattern;
//
//import java.util.Optional;
//
//import cyr7.ir.nodes.IRBinOp;
//import cyr7.ir.nodes.IRExpr;
//import cyr7.x86.asm.ASMAddrExpr;
//import cyr7.x86.asm.ASMArg;
//import cyr7.x86.asm.ASMMemArg;
//import cyr7.x86.asm.ASMTempRegArg;
//import cyr7.x86.tiler.ComplexTiler;
//import cyr7.x86.tiler.TilerData;
//
//public class TempPlusConstantTimesTempChecker {
//
//    public static Optional<TilerData> match(IRExpr n, ComplexTiler tiler) {
//        if (n instanceof IRBinOp) {
//            IRBinOp binop = (IRBinOp) n;
//            IRExpr lhs = binop.left();
//            IRExpr rhs = binop.right();
//
//            TilerData lhsTile = lhs.accept(tiler);
//            Optional<TilerData> rhsArgTile = ConstantTimesTempChecker.match(rhs,
//                    tiler);
//            // tile1 = t_left + constant * t_right
//            // tile1 = (t1 + t2) + constant * t_right
//            // t_left = t1 + t2
//
//            TilerData rhsTile = rhs.accept(tiler);
//            Optional<TilerData> lhsArgTile = ConstantTimesTempChecker.match(lhs,
//                    tiler);
//
//            if (rhsArgTile.isEmpty() && lhsArgTile.isEmpty()) {
//                return Optional.empty();
//            } else if (rhsArgTile.isEmpty()) {
//                // rhs has a constant times a temp
//                ASMAddrExpr memAddr = ((ASMMemArg) rhsArgTile.get().result.get()).address;
//                var scale = memAddr.scale;
//                ASMMemArg mem = new ASMMemArg(new ASMAddrExpr(Optional.of(
//                        (ASMTempRegArg) lhsTile.result.get()), scale,
//                        memAddr.index, 0));
//            }
//
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
//                    ASMTempArg lhsTemp = new ASMTempArg(((IRTemp) lhs).name(),
//                            Size.QWORD);
//                    ASMMemArg memArgs = rhsArg.get();
//                    ASMMemArg addressArg = new ASMMemArg(new ASMAddrExpr(
//                            Optional.of(lhsTemp), memArgs.address.scale,
//                            memArgs.address.index, 0));
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
//}