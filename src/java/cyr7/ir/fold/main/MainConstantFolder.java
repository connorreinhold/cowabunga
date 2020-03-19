package cyr7.ir.fold.main;

import cyr7.ir.fold.associativity.AssociativePropertyConstFoldVisitor;
import cyr7.ir.fold.basic.DirectConstFoldVisitor;
import cyr7.ir.fold.identity.IdentityConstantFoldVisitor;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.visit.CheckCanonicalIRVisitor;

public class MainConstantFolder {

	private MainConstantFolder() {}
		
	public static IRCompUnit foldCompUnit(IRCompUnit n) {
		
		boolean isCanonical = n.isCanonical(new CheckCanonicalIRVisitor());
		
		DirectConstFoldVisitor direct = new DirectConstFoldVisitor();
		IdentityConstantFoldVisitor identity = new IdentityConstantFoldVisitor(isCanonical);
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
		boolean isCanonical = n.isCanonical(new CheckCanonicalIRVisitor());
		
		DirectConstFoldVisitor direct = new DirectConstFoldVisitor();
		IdentityConstantFoldVisitor identity = new IdentityConstantFoldVisitor(isCanonical);
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
