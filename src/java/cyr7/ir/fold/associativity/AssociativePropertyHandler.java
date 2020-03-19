package cyr7.ir.fold.associativity;

import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRBinOp.OpType;

public class AssociativePropertyHandler {
	
	private AssociativePropertyHandler() {}
	
	private static AdditionAssociativePropertyVisitor addition = 
			new AdditionAssociativePropertyVisitor();
	private static MultiplicationAssociativePropertyVisitor mult = 
			new MultiplicationAssociativePropertyVisitor();
	
    public static IRExpr valueOf(IRBinOp root) {
        IRNodeFactory make = new IRNodeFactory_c(root.location());
        switch (root.opType()) {
        case ADD:
        case SUB: {
        	var res = root.accept(addition);
            var constant = res.part2();
            var lhs = res.part1();
            if (constant == 0) {
                return lhs;
            } else if (constant < 0) {
                return make.IRBinOp(OpType.SUB, lhs, make.IRConst(Math.abs(constant)));
            } else {
                return make.IRBinOp(OpType.ADD, lhs, make.IRConst(constant));
            }
        }
        case MUL: {
        	var res = root.accept(mult);
            var constant = res.part2();
            var lhs = res.part1();
            if (constant == 1) {
                return lhs;
            } else {
                return make.IRBinOp(OpType.MUL, lhs, make.IRConst(constant));
            }
        }
        default: 
        	return root;
        }
    }
}
