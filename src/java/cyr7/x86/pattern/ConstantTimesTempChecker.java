//package cyr7.x86.pattern;
//
//import java.util.Optional;
//
//import cyr7.ir.nodes.IRBinOp;
//import cyr7.ir.nodes.IRConst;
//import cyr7.ir.nodes.IRExpr;
//import cyr7.ir.nodes.IRBinOp.OpType;
//import cyr7.x86.tiler.ComplexTiler;
//import cyr7.x86.tiler.TilerData;
//
//public class ConstantTimesTempChecker {
//
//    public static Optional<TilerData> match(IRExpr n, ComplexTiler tiler) {
//        if (n instanceof IRBinOp) {
//            IRBinOp binop = (IRBinOp) n;
//            if (binop.opType() != OpType.MUL) {
//                return Optional.empty();
//            }
//
//            IRExpr lhs = binop.left();
//            IRExpr rhs = binop.right();
//
//            if (rhs instanceof IRConst) {
//                TilerData lhsTile = lhs.accept(tiler);
//            }
//            if (lhs instanceof IRConst) {
//                TilerData rhsTile = rhs.accept(tiler);
//            }
//
//            return Optional.empty();
//
//        }
//    }
//
//}