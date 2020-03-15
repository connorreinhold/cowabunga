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
