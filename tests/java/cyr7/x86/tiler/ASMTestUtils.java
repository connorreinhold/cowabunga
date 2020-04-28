package cyr7.x86.tiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;

import cyr7.C;
import cyr7.ir.DefaultIdGenerator;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.x86.asm.ASMArgFactory;
import cyr7.x86.asm.ASMLine;
import cyr7.x86.asm.ASMLineFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ASMTestUtils {

    public static final IRNodeFactory irNodeFactory = new IRNodeFactory_c(C.LOC);
    public static final ASMLineFactory lineFactory = new ASMLineFactory(irNodeFactory.IRSeq());
    public static final ASMArgFactory arg = ASMArgFactory.instance;

    public static <T> T makeIR(Function<IRNodeFactory, T> block) {
        return block.apply(irNodeFactory);
    }

    public static <T> T makeASM(Function<ASMLineFactory, T> block) {
        return block.apply(lineFactory);
    }

    public static ComplexTiler makeTiler() {
        return new ComplexTiler(
            new DefaultIdGenerator(),
            0,
            "no",
            Optional.empty(),
            false);
    }

    public static void assertEqualsTiled(IRNode node, String... assembly) {
        ComplexTiler tiler = makeTiler();
        node.accept(tiler);
        assertEquals(node.getOptimalTiling().optimalInstructions.size(), assembly.length,
            "\nTiled Instructions:\n"
                + node
                .getOptimalTiling()
                .optimalInstructions
                .stream()
                .map(ASMLine::getIntelAssembly)
                .collect(Collectors.joining("\n"))
                + "\nExpected Instructions:\n"
                + Arrays.toString(assembly)
                + "\n");

        var actual = node
            .getOptimalTiling()
            .optimalInstructions
            .stream()
            .map(l -> l.getIntelAssembly(false, false))
            .toArray(String[]::new);
        replaceWithOrderedTemps(actual);

        for (int i = 0; i < assembly.length; i++) {
            System.out.println(actual[i]);
            assertEquals(assembly[i], actual[i]);
        }
    }

    public static void replaceWithOrderedTemps(String[] insns) {
        int orderedTempIndex = 0;
        Map<Integer, Integer> insnTempToOrderedTemps = new HashMap<>();

        for (int i = 0; i < insns.length; i++) {
            String insn = insns[i];
            List<Integer> tempLocations = tempLocations(insn);
            Collections.reverse(tempLocations);
            String orderedString = insn;
            for (Integer location : tempLocations) {
                int endIndex = location + 2;
                while (endIndex < insn.length()
                    && '0' <= insn.charAt(endIndex)
                    && insn.charAt(endIndex) <= '9') {
                    endIndex++;
                }
                int insnTemp = Integer.parseInt(insn.substring(location + 2, endIndex));
                
                int orderedTemp = 0;
                if (insnTempToOrderedTemps.containsKey(insnTemp)) {
                    orderedTemp = insnTempToOrderedTemps.get(insnTemp);
                } else {
                    orderedTemp = orderedTempIndex++;
                }

                insnTempToOrderedTemps.put(insnTemp, orderedTemp);
                orderedString = replaceSubstring(orderedString, location + 2, endIndex, Integer.toString(orderedTemp));
            }
            insns[i] = orderedString;
        }
    }

    private static List<Integer> tempLocations(String insn) {
        List<Integer> locations = new ArrayList<>();
        int cursor = 0;
        while ((cursor = insn.indexOf("_t", cursor)) != -1) {
            locations.add(cursor);
            cursor++;
        }
        return locations;
    }

    private static String replaceSubstring(String original, int start, int end, String replacement) {
        return original.substring(0, start) + replacement + original.substring(end);
    }

}
