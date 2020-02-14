package cyr7.parser.ixi;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cyr7.ast.*;
import cyr7.ast.expr.ExprNode;
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

    LinkedList<Optional<ExprNode>> generateEmptyList(int size) {
        LinkedList<Optional<ExprNode>> l = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            l.add(Optional.empty());
        }
        return l;
    }
    
    @Test
    void testEmptyProgram() throws Exception {
        IxiProgramNode expected = new IxiProgramNode(null, new LinkedList<>());
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
        function = new FunctionHeaderDeclNode(null, "main", args, returnTypes);
        functions = new LinkedList<>();
        functions.add(function);
        expected = new IxiProgramNode(null, functions);
        prgm = new StringReader("\nmain()\n");
        parser = new XiParser(new MultiFileLexer(prgm, true), new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);

        function = new FunctionHeaderDeclNode(null, "main", new LinkedList<>(),
                new LinkedList<>());

        String[] expectedNames = new String[]
            { "main", "trial", "run", "halt", "stop", "terminate", "kill" };
        functions = new LinkedList<>();
        for (String n : expectedNames) {
            functions.add(new FunctionHeaderDeclNode(null, n, new LinkedList<>(),
                    new LinkedList<>()));
        }
        expected = new IxiProgramNode(null, functions);
        prgm = new StringReader("\nmain()\ntrial()\nrun()\n"
                + "halt()stop()terminate()kill()");
        parser = new XiParser(new MultiFileLexer(prgm, true),
                new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);
    }

    @Test
    void testProcessWithArguments() throws Exception {
        LinkedList<VarDeclNode> args;
        LinkedList<ITypeExprNode> returnTypes;
        FunctionHeaderDeclNode function;
        LinkedList<FunctionHeaderDeclNode> functions;
        IxiProgramNode expected;
        StringReader prgm;
        XiParser parser;
        Object tree;

        args = new LinkedList<>();
        VarDeclNode[] d = new VarDeclNode[] {
            new VarDeclNode(null, "b",
                    ITypeExprNode.fromDimensionList(
                            new PrimitiveTypeNode(null, PrimitiveEnum.INT),
                            this.generateEmptyList(1)
                        )
                    ),
            new VarDeclNode(null, "c",
                    ITypeExprNode.fromDimensionList(
                            new PrimitiveTypeNode(null, PrimitiveEnum.INT),
                            this.generateEmptyList(3)
                            )
                    ),
            new VarDeclNode(null, "d", 
                    new PrimitiveTypeNode(null, PrimitiveEnum.BOOL)),
        };
        Collections.addAll(args, d);
        returnTypes = new LinkedList<>();
        function = new FunctionHeaderDeclNode(null, "main", args, returnTypes);
        functions = new LinkedList<>();
        functions.add(function);
        expected = new IxiProgramNode(null, functions);
        prgm = new StringReader("\nmain(b: int[], c: int[][][], d: bool)\n");
        parser = new XiParser(new MultiFileLexer(prgm, true), new ComplexSymbolFactory());
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
        returnTypes.add(new PrimitiveTypeNode(null, PrimitiveEnum.INT));
        function = new FunctionHeaderDeclNode(null, "main", args, returnTypes);
        functions = new LinkedList<>();
        functions.add(function);
        expected = new IxiProgramNode(null, functions);
        prgm = new StringReader("\nmain(): int\n");
        MyLexer lex = new MultiFileLexer(prgm, true);
        parser = new XiParser(new MultiFileLexer(prgm, true),
                new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);
        
        args.clear();
        returnTypes.clear();
        ITypeExprNode[] types = new ITypeExprNode[]{
                new PrimitiveTypeNode(null, PrimitiveEnum.INT),
                new PrimitiveTypeNode(null, PrimitiveEnum.BOOL),
                new PrimitiveTypeNode(null, PrimitiveEnum.INT),
                new PrimitiveTypeNode(null, PrimitiveEnum.BOOL),
                new PrimitiveTypeNode(null, PrimitiveEnum.BOOL),
                new PrimitiveTypeNode(null, PrimitiveEnum.BOOL)
        };
        returnTypes.addAll(Arrays.asList(types));
        function = new FunctionHeaderDeclNode(null, "main", args, returnTypes);
        functions = new LinkedList<>();
        functions.add(function);
        expected = new IxiProgramNode(null, functions);
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
    
    
    
    @Test
    void testFunctionWithArguments() throws Exception {
        LinkedList<VarDeclNode> args;
        LinkedList<ITypeExprNode> returnTypes;
        FunctionHeaderDeclNode function;
        LinkedList<FunctionHeaderDeclNode> functions;
        IxiProgramNode expected;
        StringReader prgm;
        XiParser parser;
        Object tree;

        args = new LinkedList<>();
        VarDeclNode[] d = new VarDeclNode[] {
            new VarDeclNode(null, "b",
                    ITypeExprNode.fromDimensionList(
                            new PrimitiveTypeNode(null, PrimitiveEnum.INT),
                            this.generateEmptyList(1)
                        )
                    ),
            new VarDeclNode(null, "c",
                    ITypeExprNode.fromDimensionList(
                            new PrimitiveTypeNode(null, PrimitiveEnum.INT),
                            this.generateEmptyList(3)
                            )
                    ),
            new VarDeclNode(null, "d", 
                    new PrimitiveTypeNode(null, PrimitiveEnum.BOOL)),
        };
        Collections.addAll(args, d);
        
        returnTypes = new LinkedList<>();
        ITypeExprNode[] types = new ITypeExprNode[]{
                ITypeExprNode.fromDimensionList(
                        new PrimitiveTypeNode(null, PrimitiveEnum.INT),
                        this.generateEmptyList(1)
                        ),
                new PrimitiveTypeNode(null, PrimitiveEnum.BOOL),
                ITypeExprNode.fromDimensionList(
                        new PrimitiveTypeNode(null, PrimitiveEnum.INT),
                        this.generateEmptyList(5)
                        ),
                new PrimitiveTypeNode(null, PrimitiveEnum.BOOL),
                new PrimitiveTypeNode(null, PrimitiveEnum.BOOL),
                ITypeExprNode.fromDimensionList(
                        new PrimitiveTypeNode(null, PrimitiveEnum.BOOL),
                        this.generateEmptyList(3)
                        )
        };
        Collections.addAll(returnTypes, types);
        
        
        function = new FunctionHeaderDeclNode(null, "main", args, returnTypes);
        functions = new LinkedList<>();
        functions.add(function);
        expected = new IxiProgramNode(null, functions);
        prgm = new StringReader("\nmain(b: int[], c: int[][][], d: bool): "
                + "int[], bool, int[][][][][], bool, bool, bool[][][]\n");
        parser = new XiParser(new MultiFileLexer(prgm, true), 
                    new ComplexSymbolFactory());
        tree = parser.parse().value;
        assertEquals(tree, expected);
    }
    

}
