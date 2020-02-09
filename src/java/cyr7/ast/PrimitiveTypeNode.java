package cyr7.ast;

import edu.cornell.cs.cs4120.util.SExpPrinter;

public class PrimitiveTypeNode implements TypeNode {
	final PrimitiveEnum type;

	public PrimitiveTypeNode(PrimitiveEnum type) {
		this.type = type;
	}

	@Override
	public int getDimensions() {
		return 0;
	}

	@Override
	public void prettyPrint(SExpPrinter printer) {
		printer.printAtom(this.type.toString().toLowerCase());
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof PrimitiveTypeNode) {
			return this.type == ((PrimitiveTypeNode) o).type;
		}
		return false;
	}
}
