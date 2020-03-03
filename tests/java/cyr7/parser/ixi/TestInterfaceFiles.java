package cyr7.parser.ixi;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

import cyr7.ast.*;
import cyr7.ast.expr.ExprNode;
import cyr7.ast.toplevel.FunctionHeaderDeclNode;
import cyr7.ast.toplevel.IxiProgramNode;
import cyr7.ast.type.TypeExprNode;
import cyr7.ast.type.PrimitiveEnum;
import cyr7.ast.type.PrimitiveTypeNode;
import cyr7.ast.type.TypeExprArrayNode;
import cyr7.exceptions.parser.ParserException;
import cyr7.lexer.MultiFileLexer;
import cyr7.parser.XiParser;
import cyr7.parser.util.ParserFactory;
import org.junit.jupiter.api.Test;

import cyr7.lexer.MyLexer;
import java_cup.runtime.ComplexSymbolFactory;

import static cyr7.parser.util.ParserFactory.LOC;

class TestInterfaceFiles {

    LinkedList<Optional<ExprNode>> generateEmptyList(int size) {
        LinkedList<Optional<ExprNode>> l = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            l.add(Optional.empty());
        }
        return l;
    }
    
    @Test
    void testEmptyProgram() throws Exception {
        // Section 8 of Xi Spec:
        // Interface files contain a _nonempty_ set of procedure and function...
        assertThrows(ParserException.class, () ->
            ParserFactory.make("", true).parse()
        );

        assertThrows(ParserException.class, () ->
            ParserFactory.make(";", true).parse()
        );

        assertThrows(ParserException.class, () ->
            ParserFactory.make(";;;;", true).parse()
        );
    }

    @Test
    void testNoArgsProcess() throws Exception {
        LinkedList<VarDeclNode> args;
        LinkedList<TypeExprNode> returnTypes;
        FunctionHeaderDeclNode function;
        LinkedList<FunctionHeaderDeclNode> functions;
        IxiProgramNode expected;
        StringReader prgm;
        XiParser parser;
        Object tree;

        args = new LinkedList<>();
        returnTypes = new LinkedList<>();
        function = new FunctionHeaderDeclNode(LOC, "main", args, returnTypes);
        functions = new LinkedList<>();
        functions.add(function);
        expected = new IxiProgramNode(LOC, functions);
        prgm = new StringReader("\nmain()\n");
        parser = new XiParser(new MultiFileLexer(prgm, "", true), new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);

        function = new FunctionHeaderDeclNode(LOC, "main", new LinkedList<>(),
                new LinkedList<>());

        String[] expectedNames = new String[]
            { "main", "trial", "run", "halt", "stop", "terminate", "kill" };
        functions = new LinkedList<>();
        for (String n : expectedNames) {
            functions.add(new FunctionHeaderDeclNode(LOC, n, new LinkedList<>(),
                    new LinkedList<>()));
        }
        expected = new IxiProgramNode(LOC, functions);
        prgm = new StringReader("\nmain()\ntrial()\nrun()\n"
                + "halt()stop()terminate()kill()");
        parser = new XiParser(new MultiFileLexer(prgm, "", true),
                new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);
    }

    @Test
    void testProcessWithArguments() throws Exception {
        LinkedList<VarDeclNode> args;
        LinkedList<TypeExprNode> returnTypes;
        FunctionHeaderDeclNode function;
        LinkedList<FunctionHeaderDeclNode> functions;
        IxiProgramNode expected;
        StringReader prgm;
        XiParser parser;
        Object tree;

        args = new LinkedList<>();
        VarDeclNode[] d = new VarDeclNode[] {
            new VarDeclNode(LOC, "b",
                    TypeExprNode.fromDimensionList(
                            new PrimitiveTypeNode(LOC, PrimitiveEnum.INT),
                            this.generateEmptyList(1)
                        )
                    ),
            new VarDeclNode(LOC, "c",
                    TypeExprNode.fromDimensionList(
                            new PrimitiveTypeNode(LOC, PrimitiveEnum.INT),
                            this.generateEmptyList(3)
                            )
                    ),
            new VarDeclNode(LOC, "d",
                    new PrimitiveTypeNode(LOC, PrimitiveEnum.BOOL)),
        };
        Collections.addAll(args, d);
        returnTypes = new LinkedList<>();
        function = new FunctionHeaderDeclNode(LOC, "main", args, returnTypes);
        functions = new LinkedList<>();
        functions.add(function);
        expected = new IxiProgramNode(LOC, functions);
        prgm = new StringReader("\nmain(b: int[], c: int[][][], d: bool)\n");
        parser = new XiParser(new MultiFileLexer(prgm, "", true), new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);
    }

    
    @Test
    void testNoArgsFunction() throws Exception {
        
        LinkedList<VarDeclNode> args;
        LinkedList<TypeExprNode> returnTypes;
        LinkedList<FunctionHeaderDeclNode> functions;
        IxiProgramNode expected;
        StringReader prgm;
        XiParser parser;
        Object tree;
        FunctionHeaderDeclNode function;
        
        args = new LinkedList<>();
        returnTypes = new LinkedList<>();
        returnTypes.add(new PrimitiveTypeNode(LOC, PrimitiveEnum.INT));
        function = new FunctionHeaderDeclNode(LOC, "main", args, returnTypes);
        functions = new LinkedList<>();
        functions.add(function);
        expected = new IxiProgramNode(LOC, functions);
        prgm = new StringReader("\nmain(): int\n");
        MyLexer lex = new MultiFileLexer(prgm, "", true);
        parser = new XiParser(new MultiFileLexer(prgm, "", true),
                new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);
        
        args.clear();
        returnTypes.clear();
        TypeExprNode[] types = new TypeExprNode[]{
                new PrimitiveTypeNode(LOC, PrimitiveEnum.INT),
                new PrimitiveTypeNode(LOC, PrimitiveEnum.BOOL),
                new PrimitiveTypeNode(LOC, PrimitiveEnum.INT),
                new PrimitiveTypeNode(LOC, PrimitiveEnum.BOOL),
                new PrimitiveTypeNode(LOC, PrimitiveEnum.BOOL),
                new PrimitiveTypeNode(LOC, PrimitiveEnum.BOOL)
        };
        returnTypes.addAll(Arrays.asList(types));
        function = new FunctionHeaderDeclNode(LOC, "main", args, returnTypes);
        functions = new LinkedList<>();
        functions.add(function);
        expected = new IxiProgramNode(LOC, functions);
        prgm = new StringReader("\nmain(): int, bool, int, bool, bool, bool\n");
        parser = new XiParser(new MultiFileLexer(prgm, "", true),
                new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);
    
        args.clear();
        returnTypes.clear();
        Integer[] arrayDimensions = new Integer[]{
                Integer.MAX_VALUE >>> 20,
                Integer.MAX_VALUE >>> 21,
                Integer.MAX_VALUE >>> 22,
        };

        for (int d: arrayDimensions) {
            TypeExprNode type = new PrimitiveTypeNode(LOC, PrimitiveEnum.INT);
            for (int i = 0; i < d; i++) {
                type = new TypeExprArrayNode(LOC, type, Optional.empty());
            }
            returnTypes.add(type);
        }

        function = new FunctionHeaderDeclNode(LOC, "main", args, returnTypes);
        functions = new LinkedList<>();
        functions.add(function);
        expected = new IxiProgramNode(LOC, functions);
        String prgmString = "main():" + Arrays.stream(arrayDimensions)
            .map(d -> "int" + "[]".repeat(Math.max(0, d)))
            .collect(Collectors.joining(", "));
        prgm = new StringReader(prgmString);
        parser = new XiParser(new MultiFileLexer(prgm, "", true),
                new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);
    }
    
    
    
    @Test
    void testFunctionWithArguments() throws Exception {
        LinkedList<VarDeclNode> args;
        LinkedList<TypeExprNode> returnTypes;
        FunctionHeaderDeclNode function;
        LinkedList<FunctionHeaderDeclNode> functions;
        IxiProgramNode expected;
        StringReader prgm;
        XiParser parser;
        Object tree;

        args = new LinkedList<>();
        VarDeclNode[] d = new VarDeclNode[] {
            new VarDeclNode(LOC, "b",
                    TypeExprNode.fromDimensionList(
                            new PrimitiveTypeNode(LOC, PrimitiveEnum.INT),
                            this.generateEmptyList(1)
                        )
                    ),
            new VarDeclNode(LOC, "c",
                    TypeExprNode.fromDimensionList(
                            new PrimitiveTypeNode(LOC, PrimitiveEnum.INT),
                            this.generateEmptyList(3)
                            )
                    ),
            new VarDeclNode(LOC, "d",
                    new PrimitiveTypeNode(LOC, PrimitiveEnum.BOOL)),
        };
        Collections.addAll(args, d);
        
        returnTypes = new LinkedList<>();
        TypeExprNode[] types = new TypeExprNode[]{
                TypeExprNode.fromDimensionList(
                        new PrimitiveTypeNode(LOC, PrimitiveEnum.INT),
                        this.generateEmptyList(1)
                        ),
                new PrimitiveTypeNode(LOC, PrimitiveEnum.BOOL),
                TypeExprNode.fromDimensionList(
                        new PrimitiveTypeNode(LOC, PrimitiveEnum.INT),
                        this.generateEmptyList(5)
                        ),
                new PrimitiveTypeNode(LOC, PrimitiveEnum.BOOL),
                new PrimitiveTypeNode(LOC, PrimitiveEnum.BOOL),
                TypeExprNode.fromDimensionList(
                        new PrimitiveTypeNode(LOC, PrimitiveEnum.BOOL),
                        this.generateEmptyList(3)
                        )
        };
        Collections.addAll(returnTypes, types);
        
        
        function = new FunctionHeaderDeclNode(LOC, "main", args, returnTypes);
        functions = new LinkedList<>();
        functions.add(function);
        expected = new IxiProgramNode(LOC, functions);
        prgm = new StringReader("\nmain(b: int[], c: int[][][], d: bool): "
                + "int[], bool, int[][][][][], bool, bool, bool[][][]\n");
        parser = new XiParser(new MultiFileLexer(prgm, "", true), 
                    new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);
    }
    

}
