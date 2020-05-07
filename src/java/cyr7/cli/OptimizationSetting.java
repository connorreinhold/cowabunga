package cyr7.cli;

public class OptimizationSetting {

    private boolean enableOptimizations;

    private boolean cFoldEnabled;
    private boolean algebraOptEnabled;
    private boolean registerAllocEnabled;
    private boolean copyPropEnabled;
    private boolean deadCodeElimEnabled;
    private boolean constPropEnabled;

    public OptimizationSetting() {
        enableOptimizations = true;

        cFoldEnabled = true;
        algebraOptEnabled = true;
        registerAllocEnabled = true;
        copyPropEnabled = true;
        deadCodeElimEnabled = true;
        constPropEnabled = true;
    }

    public boolean noOptimizationsEnabled() {
        return !this.enableOptimizations;
    }

    public void disableAllOptimizations() {
        this.enableOptimizations = false;
        for (Optimization v: Optimization.values()) {
            this.setOptimization(v, false);
        }
    }

    public boolean getOptimizationSetting(Optimization type) {
        switch (type) {
        case ALG: return this.algebraOptEnabled && this.enableOptimizations;
        case CF: return this.cFoldEnabled && this.enableOptimizations;
        case COPY: return this.copyPropEnabled && this.enableOptimizations;
        case CP: return this.constPropEnabled && this.enableOptimizations;
        case CSE: throw new UnsupportedOperationException("No support for cse");
        case DCE: return this.deadCodeElimEnabled && this.enableOptimizations;
        case INL: throw new UnsupportedOperationException("No support for inl");
        case LICM: throw new UnsupportedOperationException("No support for licm");
        case LU: throw new UnsupportedOperationException("No support for lu");
        case MC: throw new UnsupportedOperationException("No support for mc");
        case PRE: throw new UnsupportedOperationException("No support for pre");
        case REG: return this.registerAllocEnabled && this.enableOptimizations;
        case SR: throw new UnsupportedOperationException("No support for sr");
        case UNSUPPORTED: throw new UnsupportedOperationException("Unuspported option");
        case VN: throw new UnsupportedOperationException("No support for vn");
        default: throw new UnsupportedOperationException("No matching optimization found");
        }
    }


    public void setOptimization(Optimization optType, boolean setting) {
        switch (optType) {
        case ALG:
            this.algebraOptEnabled = setting && this.enableOptimizations;
            break;
        case CF:
            this.cFoldEnabled = setting && this.enableOptimizations;
            break;
        case COPY:
            this.copyPropEnabled = setting && this.enableOptimizations;
            break;
        case CP:
            this.constPropEnabled = setting && this.enableOptimizations;
            break;
        case CSE:
            break;
        case DCE:
            this.deadCodeElimEnabled = setting && this.enableOptimizations;
            break;
        case INL:
            break;
        case LICM:
            break;
        case LU:
            break;
        case MC:
            this.registerAllocEnabled = setting && this.enableOptimizations;
            break;
        case PRE:
            break;
        case REG:
            this.registerAllocEnabled = setting && this.enableOptimizations;
            break;
        case SR:
            break;
        case VN:
            break;
        case UNSUPPORTED:
            break;
        default:
            break;
        }
    }


}
