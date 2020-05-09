package cyr7.cfg.ir.dfa.loops.inductionvars;

public class DefinedInductionVariable implements InductionVariable {
        private String basicRef;
        private long factor;
        private long offset;
        public DefinedInductionVariable(String basicRef, long factor, long offset) {
            this.basicRef = basicRef;
            this.factor = factor;
            this.offset = offset;
        }
        @Override
        public String basicRef() {
            return basicRef;
        }
        @Override
        public long getFactor() {
            return factor;
        }
        @Override
        public long getOffset() {
            return offset;
        }
        
        @Override
        public boolean equals(Object o) {
            if (o instanceof DefinedInductionVariable) {
                DefinedInductionVariable comp = (DefinedInductionVariable) o;
                return this.basicRef.equals(comp.basicRef) 
                        && this.factor == comp.factor && this.offset == comp.offset;
            }
            return false;
            
        }
        
        @Override
        public String toString() {
            return String.format("<%s, %d, %d>", basicRef, factor, offset);
        }
    }
    