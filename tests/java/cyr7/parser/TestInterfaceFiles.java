package cyr7.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import cyr7.ast.ArrayTypeNode;
import cyr7.ast.FunctionArgNode;
import cyr7.ast.FunctionDeclNode;
import cyr7.ast.IxiProgramNode;
import cyr7.ast.PrimitiveEnum;
import cyr7.ast.PrimitiveTypeNode;
import cyr7.ast.TypeNode;
import cyr7.lexer.MyLexer;
import cyr7.parser.XiParser;
import java_cup.runtime.ComplexSymbolFactory;

class TestInterfaceFiles {

    @Test
    void testEmptyProgram() throws Exception {
        IxiProgramNode expected = new IxiProgramNode(new LinkedList<>());
        StringReader prgm = new StringReader("");
        XiParser parser = new XiParser(new MyLexer(prgm, true),
                new ComplexSymbolFactory());
        Object tree = parser.parse().value;
        assertEquals(tree, expected);
    }

    @Test
    void testNoArgsProcess() throws Exception {
        LinkedList<FunctionArgNode> args;
        LinkedList<TypeNode> returnTypes;
        FunctionDeclNode function;
        LinkedList<FunctionDeclNode> functions;
        IxiProgramNode expected;
        StringReader prgm;
        XiParser parser;
        Object tree;

        args = new LinkedList<>();
        returnTypes = new LinkedList<>();
        function = new FunctionDeclNode("main", args, returnTypes);
        functions = new LinkedList<>();
        functions.add(function);
        expected = new IxiProgramNode(functions);
        prgm = new StringReader("\nmain()\n");
        parser = new XiParser(new MyLexer(prgm, true), new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);

        function = new FunctionDeclNode("main", new LinkedList<>(),
                new LinkedList<>());

        String[] expectedNames = new String[]
            { "main", "trial", "run", "halt", "stop", "terminate", "kill" };
        functions = new LinkedList<>();
        for (String n : expectedNames) {
            functions.add(new FunctionDeclNode(n, new LinkedList<>(),
                    new LinkedList<>()));
        }
        expected = new IxiProgramNode(functions);
        prgm = new StringReader("\nmain()\ntrial()\nrun()\n"
                + "halt()stop()terminate()kill()");
        parser = new XiParser(new MyLexer(prgm, true), 
                new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);
    }

    @Test
    void testNoArgsFunction() throws Exception {
        
        LinkedList<FunctionArgNode> args;
        LinkedList<TypeNode> returnTypes;
        LinkedList<FunctionDeclNode> functions;
        IxiProgramNode expected;
        StringReader prgm;
        XiParser parser;
        Object tree;
        FunctionDeclNode function;
        
        args = new LinkedList<>();
        returnTypes = new LinkedList<>();
        returnTypes.add(new PrimitiveTypeNode(PrimitiveEnum.INT));
        function = new FunctionDeclNode(
                "main", args, returnTypes);  
        functions = new LinkedList<>();
        functions.add(function);
        expected = new IxiProgramNode(functions);
        prgm = new StringReader("\nmain(): int\n");
        MyLexer lex = new MyLexer(prgm, true);
        parser = new XiParser(new MyLexer(prgm, true),
                new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);
        
        
        
        args.clear();
        returnTypes.clear();
        TypeNode[] types = new TypeNode[]{
            new PrimitiveTypeNode(PrimitiveEnum.INT), 
            new PrimitiveTypeNode(PrimitiveEnum.BOOL), 
            new PrimitiveTypeNode(PrimitiveEnum.INT),
            new PrimitiveTypeNode(PrimitiveEnum.BOOL),
            new PrimitiveTypeNode(PrimitiveEnum.BOOL),
            new PrimitiveTypeNode(PrimitiveEnum.BOOL),
        };
        for (TypeNode t: types) {
            returnTypes.add(t);
        }
        function = new FunctionDeclNode("main", args, returnTypes);
        functions = new LinkedList<>();
        functions.add(function);
        expected = new IxiProgramNode(functions);
        prgm = new StringReader("\nmain(): int, bool, int, bool, bool, bool\n");
        parser = new XiParser(new MyLexer(prgm, true),
                new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);
        
        
        
        args.clear();
        returnTypes.clear();
        Integer[] arrayDimensions = new Integer[]{
                Integer.MAX_VALUE >>> 20,
                Integer.MAX_VALUE >>> 19,
                Integer.MAX_VALUE >>> 18,
        };

        for (int d: arrayDimensions) {
            TypeNode type = new PrimitiveTypeNode(PrimitiveEnum.INT);
            for (int i = 0; i < d; i++) {
                type = new ArrayTypeNode(type);
            }
            returnTypes.add(type);
        }
        
        StringBuffer prgmString = new StringBuffer("main():");
        prgmString.append(Arrays.asList(arrayDimensions).stream()
            .map(d -> {
                StringBuffer typeString = new StringBuffer("int");
                for (int i = 0; i < d; i++) {
                    typeString.append("[]");
                }
                return typeString.toString();
            }).collect(Collectors.joining(", ")));
        
        function = new FunctionDeclNode("main", args, returnTypes);
        functions = new LinkedList<>();
        functions.add(function);
        expected = new IxiProgramNode(functions);
        prgm = new StringReader(prgmString.toString());
        parser = new XiParser(new MyLexer(prgm, true),
                new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);
        
    }

}
