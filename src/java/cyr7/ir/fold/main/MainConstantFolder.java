package cyr7.ir.fold.main;

import cyr7.ir.fold.associativity.AssociativePropertyConstFoldVisitor;
import cyr7.ir.fold.basic.DirectConstFoldVisitor;
import cyr7.ir.fold.identity.IdentityConstantFoldVisitor;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.visit.CheckCanonicalIRVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class MainConstantFolder {

	private MainConstantFolder() {}
	
	public static void main(String[] args) {
        IRNodeFactory make = new IRNodeFactory_c(new Location(-1, -1));

        IRExpr e = make.IRBinOp(OpType.ADD,
                make.IRBinOp(OpType.ADD, make.IRConst(12), make.IRTemp("t")),
                make.IRBinOp(OpType.ADD, make.IRConst(-3), make.IRTemp("a")));
        System.out.println(e);
        var result = MainConstantFolder.foldExpr(e);        
        System.out.println(result);
        System.out.println();

        e = make.IRBinOp(OpType.XOR, make.IRConst(1),
                make.IRBinOp(OpType.XOR, make.IRConst(1),
                make.IRBinOp(OpType.LEQ,
                        make.IRTemp("d"), make.IRConst(3))));
        result = MainConstantFolder.foldExpr(e);        
        System.out.println(e);
        result = MainConstantFolder.foldExpr(e);        
        System.out.println(result);
        System.out.println();
        
        e = make.IRBinOp(OpType.OR,
                make.IRBinOp(OpType.XOR, make.IRConst(1), make.IRTemp("a")),
                make.IRBinOp(OpType.XOR, make.IRConst(1), make.IRTemp("b")));

        result = MainConstantFolder.foldExpr(e);        
        System.out.println(e);
        result = MainConstantFolder.foldExpr(e);        
        System.out.println(result);
        System.out.println();
	}
	
	public static IRCompUnit foldCompUnit(IRCompUnit n) {
		if (n.isCanonical(new CheckCanonicalIRVisitor())) {
			// Different constant fold settings
		} else {
			
		}
		DirectConstFoldVisitor direct = new DirectConstFoldVisitor();
		IdentityConstantFoldVisitor identity = new IdentityConstantFoldVisitor();
		AssociativePropertyConstFoldVisitor associativity = 
				new AssociativePropertyConstFoldVisitor();	
		return (IRCompUnit)n.accept(direct)
							.assertSecond()
							.accept(identity)
							.assertSecond()
							.accept(associativity)
							.assertSecond()
							.accept(direct)
							.assertSecond()
							.accept(identity)
							.assertSecond();
	}

	public static IRExpr foldExpr(IRExpr n) {
		if (n.isCanonical(new CheckCanonicalIRVisitor())) {
			// Different constant fold settings
		} else {}
		
		DirectConstFoldVisitor direct = new DirectConstFoldVisitor();
		IdentityConstantFoldVisitor identity = new IdentityConstantFoldVisitor();
		AssociativePropertyConstFoldVisitor associativity = 
				new AssociativePropertyConstFoldVisitor();	
		return n.accept(direct)
				.assertFirst()
				.accept(identity)
				.assertFirst()
				.accept(associativity)
				.assertFirst()
				.accept(direct)
				.assertFirst()
				.accept(identity)
				.assertFirst();
	}
	
}
