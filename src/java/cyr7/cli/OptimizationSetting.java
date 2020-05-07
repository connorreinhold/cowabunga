package cyr7.cli;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class OptimizationSetting {

    private boolean enableOptimizations;

    private EnumMap<Optimization, Boolean> settings;

    public OptimizationSetting() {
        this.enableOptimizations = true;

        final Map<Optimization, Boolean> enumToBoolHashMap = new HashMap<>();
        enumToBoolHashMap.put(Optimization.REG, true);
        // EnumMap requires at least one mapping to be instantiated.
        this.settings = new EnumMap<>(enumToBoolHashMap);

        for (Optimization opt: Optimization.values()) {
            this.settings.put(opt, true);
        }
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

    public boolean get(Optimization type) {
        switch (type) {
        case ALG: return settings.get(type) && this.enableOptimizations;
        case CF: return settings.get(type) && this.enableOptimizations;
        case COPY: return settings.get(type) && this.enableOptimizations;
        case CP: return settings.get(type) && this.enableOptimizations;
        case CSE: throw new UnsupportedOperationException("No support for cse");
        case DCE: return settings.get(type) && this.enableOptimizations;
        case INL: throw new UnsupportedOperationException("No support for inl");
        case LICM: throw new UnsupportedOperationException("No support for licm");
        case LU: throw new UnsupportedOperationException("No support for lu");
        case MC: throw new UnsupportedOperationException("No support for mc");
        case PRE: throw new UnsupportedOperationException("No support for pre");
        case REG: return settings.get(type) && this.enableOptimizations;
        case SR: throw new UnsupportedOperationException("No support for sr");
        case UNSUPPORTED: throw new UnsupportedOperationException("Unuspported option");
        case VN: throw new UnsupportedOperationException("No support for vn");
        default: throw new UnsupportedOperationException("No matching optimization found");
        }
    }

    public String description() {
        StringBuffer description = new StringBuffer();
        this.settings.forEach((opt, set) -> {
            description.append(opt.toString() + " enabled: " + set + "\n");
        });
        return description.toString();
    }


    public void setOptimization(Optimization optType, boolean setting) {
        this.settings.put(optType, setting);
    }

}
