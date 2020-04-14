package cyr7.x86.pattern;

import java.util.ArrayList;
import java.util.List;

public class NestedPatternBuilder implements Pattern {

    private final List<Pattern> subpatterns;

    public NestedPatternBuilder() {
        this.subpatterns = new ArrayList<>();
    }

    public static NestedPatternBuilder create() {
        return new NestedPatternBuilder();
    }

    public NestedPatternBuilder add(Pattern pattern) {
        subpatterns.add(pattern);
        return this;
    }

    @Override
    public boolean matches(Object[] objs) {
        if (objs.length != subpatterns.size()) {
            return false;
        }

        if (!(objs instanceof  Object[][])) {
            return false;
        }

        Object[][] arrays = (Object[][]) objs;

        for (int i = 0; i < arrays.length; i++) {
            if (!subpatterns.get(i).matches(arrays[i])) {
                return false;
            }
        }

        return true;
    }

}
