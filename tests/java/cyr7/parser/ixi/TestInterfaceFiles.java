package cyr7.parser.ixi;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

import cyr7.ast.*;
import cyr7.ast.stmt.VarDeclNode;
import cyr7.ast.type.ITypeExprNode;
import cyr7.ast.type.PrimitiveEnum;
import cyr7.ast.type.PrimitiveTypeNode;
import cyr7.ast.type.TypeExprArrayNode;
import cyr7.lexer.MultiFileLexer;
import cyr7.parser.XiParser;
import org.junit.jupiter.api.Test;

import cyr7.lexer.MyLexer;
import java_cup.runtime.ComplexSymbolFactory;

class TestInterfaceFiles {

    @Test
    void testEmptyProgram() throws Exception {
        IxiProgramNode expected = new IxiProgramNode(new LinkedList<>());
        StringReader prgm = new StringReader("");
        XiParser parser = new XiParser(new MultiFileLexer(prgm, true),
                new ComplexSymbolFactory());
        Object tree = parser.parse().value;
        assertEquals(tree, expected);
    }

    @Test
    void testNoArgsProcess() throws Exception {
        LinkedList<VarDeclNode> args;
        LinkedList<ITypeExprNode> returnTypes;
        FunctionHeaderDeclNode function;
        LinkedList<FunctionHeaderDeclNode> functions;
        IxiProgramNode expected;
        StringReader prgm;
        XiParser parser;
        Object tree;

        args = new LinkedList<>();
        returnTypes = new LinkedList<>();
        function = new FunctionHeaderDeclNode("main", args, returnTypes);
        functions = new LinkedList<>();
        functions.add(function);
        expected = new IxiProgramNode(functions);
        prgm = new StringReader("\nmain()\n");
        parser = new XiParser(new MultiFileLexer(prgm, true), new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);

        function = new FunctionHeaderDeclNode("main", new LinkedList<>(),
                new LinkedList<>());

        String[] expectedNames = new String[]
            { "main", "trial", "run", "halt", "stop", "terminate", "kill" };
        functions = new LinkedList<>();
        for (String n : expectedNames) {
            functions.add(new FunctionHeaderDeclNode(n, new LinkedList<>(),
                    new LinkedList<>()));
        }
        expected = new IxiProgramNode(functions);
        prgm = new StringReader("\nmain()\ntrial()\nrun()\n"
                + "halt()stop()terminate()kill()");
        parser = new XiParser(new MultiFileLexer(prgm, true),
                new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);
    }

    @Test
    void testNoArgsFunction() throws Exception {
        
        LinkedList<VarDeclNode> args;
        LinkedList<ITypeExprNode> returnTypes;
        LinkedList<FunctionHeaderDeclNode> functions;
        IxiProgramNode expected;
        StringReader prgm;
        XiParser parser;
        Object tree;
        FunctionHeaderDeclNode function;
        
        args = new LinkedList<>();
        returnTypes = new LinkedList<>();
        returnTypes.add(PrimitiveEnumNode.INT);
        function = new FunctionHeaderDeclNode(
                "main", args, returnTypes);  
        functions = new LinkedList<>();
        functions.add(function);
        expected = new IxiProgramNode(functions);
        prgm = new StringReader("\nmain(): int\n");
        MyLexer lex = new MultiFileLexer(prgm, true);
        parser = new XiParser(new MultiFileLexer(prgm, true),
                new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);
        
        
        
        args.clear();
        returnTypes.clear();
        ITypeExprNode[] types = new ITypeExprNode[]{
            PrimitiveEnumNode.INT,
            PrimitiveEnumNode.BOOL,
            PrimitiveEnumNode.INT,
            PrimitiveEnumNode.BOOL,
            PrimitiveEnumNode.BOOL,
            PrimitiveEnumNode.BOOL,
        };
        for (ITypeExprNode t: types) {
            returnTypes.add(t);
        }
        function = new FunctionHeaderDeclNode(null, "main", args, returnTypes);
        functions = new LinkedList<>();
        functions.add(function);
        expected = new IxiProgramNode(functions);
        prgm = new StringReader("\nmain(): int, bool, int, bool, bool, bool\n");
        parser = new XiParser(new MultiFileLexer(prgm, true),
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
            ITypeExprNode type = new PrimitiveTypeNode(null, PrimitiveEnum.INT);
            for (int i = 0; i < d; i++) {
                type = new TypeExprArrayNode(null, type, Optional.empty());
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
        
        function = new FunctionHeaderDeclNode(null, "main", args, returnTypes);
        functions = new LinkedList<>();
        functions.add(function);
        expected = new IxiProgramNode(null, functions);
        prgm = new StringReader(prgmString.toString());
        parser = new XiParser(new MultiFileLexer(prgm, true),
                new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);
        
    }

}
