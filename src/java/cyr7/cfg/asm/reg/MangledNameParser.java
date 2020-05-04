package cyr7.cfg.asm.reg;

import cyr7.semantics.types.ArrayType;
import cyr7.semantics.types.ExpandedType;
import cyr7.semantics.types.FunctionType;
import cyr7.semantics.types.OrdinaryType;
import cyr7.semantics.types.PrimitiveType;
import cyr7.util.Pair;
import cyr7.x86.ASMConstants;
import cyr7.x86.asm.ASMReg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Utility class for parsing mangled names
 */
public enum MangledNameParser {
    ;

    public static Optional<FunctionType> parseFunctionType(String mangledName) {
        int lastUnderscoreIndex = mangledName.lastIndexOf('_');
        if (lastUnderscoreIndex == -1) {
            return Optional.empty();
        }

        try {
            String encodedType = mangledName.substring(lastUnderscoreIndex + 1);

            // The cursor points to the next character in encodedType
            int cursor = 0;

            // The number of return values
            int numReturnValues;
            switch (encodedType.charAt(cursor)) {
                case 'p':
                    cursor++;
                    numReturnValues = 0;
                    break;
                case 't':
                    cursor++;
                    while ('0' <= encodedType.charAt(cursor) && encodedType.charAt(cursor) <= '9') {
                        cursor++;
                    }
                    numReturnValues =
                        Integer.parseInt(encodedType.substring(1, cursor + 1));
                    cursor++;
                    break;
                default:
                    cursor++;
                    numReturnValues = 1;
            }

            // Parse return types

            List<OrdinaryType> returnTypes = new ArrayList<>(numReturnValues);
            for (int i = 0; i < numReturnValues; i++) {
                Pair<Integer, OrdinaryType> cursorAndType
                    = parseOrdinaryType(encodedType, cursor);

                cursor = cursorAndType.left;
                returnTypes.add(cursorAndType.right);
            }

            // Parse argument types

            List<OrdinaryType> argumentTypes = new ArrayList<>(1);
            while (cursor < encodedType.length()) {
                Pair<Integer, OrdinaryType> cursorAndType
                    = parseOrdinaryType(encodedType, cursor);

                cursor = cursorAndType.left;
                argumentTypes.add(cursorAndType.right);
            }

            return Optional.of(
                new FunctionType(
                    new ExpandedType(argumentTypes),
                    new ExpandedType(returnTypes)));

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static Pair<Integer, OrdinaryType> parseOrdinaryType(
        String encodedType,
        int cursor) throws IllegalArgumentException {
        if (cursor >= encodedType.length()) {
            throw new IllegalArgumentException();
        }

        switch (encodedType.charAt(cursor)) {
            case 'i':
                return new Pair<>(cursor + 1, PrimitiveType.intDefault);
            case 'b':
                return new Pair<>(cursor + 1, PrimitiveType.boolDefault);
            case 'a':
                Pair<Integer, OrdinaryType> subtype
                    = parseOrdinaryType(encodedType, cursor + 1);
                return new Pair<>(subtype.left, new ArrayType(subtype.right));
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Return registers for the mangled name.
     */
    public static Set<ASMReg> returnRegisters(String mangledName) {
        Optional<FunctionType> optFunctionType = parseFunctionType(mangledName);
        if (optFunctionType.isEmpty()) {
            return Collections.emptySet();
        }

        FunctionType functionType = optFunctionType.get();

        // the number of return registers is the number of return values,
        // capped at the number of available return registers
        int numRetRegs = Math.min(
            ASMConstants.RETURN_REGISTERS.length,
            functionType.output.getTypes().size());

        return new HashSet<>(Arrays
                .asList(ASMConstants.RETURN_REGISTERS)
                .subList(0, numRetRegs));
    }

    /**
     * The argument registers for a call to a function with the specified
     * mangled name, including the extra argument to store return values
     * beyond the 6th.
     */
    public static Set<ASMReg> argRegisters(String mangledName) {
        Optional<FunctionType> optFunctionType = parseFunctionType(mangledName);
        if (optFunctionType.isEmpty()) {
            return Collections.emptySet();
        }

        FunctionType functionType = optFunctionType.get();

        // the number of argument registers is the number of arguments
        // (plus one if the number of return values is more than 2)
        // capped at the number of available argument registers
        int numArgRegs = Math.min(
            ASMConstants.ARGUMENT_REGISTERS.length,
            functionType.input.getTypes().size() +
                (functionType.output.getTypes().size() > 2 ? 1 : 0));

        return new HashSet<>(Arrays
            .asList(ASMConstants.ARGUMENT_REGISTERS)
            .subList(0, numArgRegs));
    }

}
