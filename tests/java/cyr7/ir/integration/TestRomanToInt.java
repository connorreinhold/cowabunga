package cyr7.ir.integration;

import java.util.Arrays;
import java.util.stream.Collectors;

import cyr7.ir.integration.Run.RunConfiguration;

public class TestRomanToInt extends TestProgram {

    private long[] parseString(String s) {
        Character[] charObjectArray = s.chars().mapToObj(c -> (char) c)
                .toArray(Character[]::new);
        Long[] longArray = Arrays.asList(charObjectArray).stream().map(c -> {
            return Long.valueOf(c.charValue());
        }).collect(Collectors.toList()).toArray(new Long[] {});

        long[] primArr = new long[longArray.length];
        for (int i = 0; i < longArray.length; i++) {
            primArr[i] = longArray[i].longValue();
        }
        return primArr;
    }

    @Override
    protected String filename() {
        return "romanToInt";
    }

    @Override
    protected String expected() {
        return "1994\n" + "4\n" + "5\n" + "3\n";
    }

    @Override
    protected RunConfiguration configuration() {
       return new RunConfiguration().args(
            "MCMXCIV",
            "IV",
            "V",
            "III"
        );
    }
}
