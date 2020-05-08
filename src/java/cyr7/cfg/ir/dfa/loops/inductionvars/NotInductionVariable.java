package cyr7.cfg.ir.dfa.loops.inductionvars;

// bottom element
    public enum NotInductionVariable implements InductionVariable {
        INSTANCE;

        @Override
        public String basicRef() {
            return "N/A";
        }

        @Override
        public long getFactor() {
            return 0;
        }

        @Override
        public long getOffset() {
            return 0;
        }
        
        @Override
        public String toString() {
            return String.format("<Not IV>");
        }
    }