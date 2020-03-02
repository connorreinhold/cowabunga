package cyr7.typecheck;

import cyr7.ast.Node;
import cyr7.ast.VarDeclNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.access.ArrayAccessExprNode;
import cyr7.ast.expr.access.VariableAccessExprNode;
import cyr7.ast.expr.literalexpr.LiteralArrayExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.expr.literalexpr.LiteralStringExprNode;
import cyr7.ast.stmt.*;
import cyr7.ast.type.PrimitiveEnum;
import cyr7.ast.type.PrimitiveTypeNode;
import cyr7.ast.type.TypeExprArrayNode;
import cyr7.exceptions.semantics.SemanticException;
import cyr7.semantics.context.Context;
import cyr7.semantics.types.ExpandedType;
import cyr7.semantics.types.FunctionType;
import cyr7.semantics.context.HashMapStackContext;
import cyr7.semantics.types.PrimitiveType;
import cyr7.semantics.types.ResultType;
import cyr7.util.OneOfThree;
import java_cup.runtime.ComplexSymbolFactory.Location;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestTypeCheckVisitorStmt {

    Context context;
    TypeCheckVisitor visitor;
    Node node;
    OneOfThree<ExpandedType, ResultType, Optional<Void>> result;
    Location loc = new Location(0, 0);
    
    @Test
    void testExprStmtNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        
        visitor.context.addFn("bool_of_int", new FunctionType(ExpandedType.intType, 
                                                ExpandedType.boolType));
        node = new ExprStmtNode(loc, 
                new FunctionCallExprNode(loc, "bool_of_int", 
                        List.of(new LiteralIntExprNode(loc, "1000"))));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        
        
        visitor.context.addFn("sleep", new FunctionType(ExpandedType.intType, 
                ExpandedType.unitExpandedType));
        node = new ExprStmtNode(loc, 
                new FunctionCallExprNode(loc, "sleep", 
                        List.of(new LiteralIntExprNode(loc, "1000"))));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        visitor.context.addFn("factorize", new FunctionType(ExpandedType.intType, 
                new ExpandedType(
                        List.of(PrimitiveType.intDefault, PrimitiveType.intDefault))));
        node = new ExprStmtNode(loc, 
                new FunctionCallExprNode(loc, "factorize", 
                        List.of(new LiteralIntExprNode(loc, "1000"))));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

    }
    
    
    @Test
    void testVarInit() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        
        node = new VarInitStmtNode(loc, 
                new VarDeclNode(loc, "alias", 
                        new PrimitiveTypeNode(loc, PrimitiveEnum.INT)), 
                new LiteralIntExprNode(loc, "123"));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        
        
        // Assigning Mismatching types
        node = new VarInitStmtNode(loc, 
                new VarDeclNode(loc, "alias2", 
                        new PrimitiveTypeNode(loc, PrimitiveEnum.INT)), 
                new LiteralBoolExprNode(loc, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        // Redeclaring variable names
        node = new VarInitStmtNode(loc, 
                new VarDeclNode(loc, "alias", 
                        new PrimitiveTypeNode(loc, PrimitiveEnum.BOOL)),
                new LiteralBoolExprNode(loc, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
    }
    
    
    @Test
    void testAssignmentStmtNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addVar("cash", PrimitiveType.intDefault);
        
        node = new AssignmentStmtNode(loc, 
                new VariableAccessExprNode(loc, "cash"),
                new LiteralIntExprNode(loc, "123"));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        
        
        // Mismatched types
        node = new AssignmentStmtNode(loc, 
                new VariableAccessExprNode(loc, "cash"),
                new LiteralBoolExprNode(loc, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));


        
        // Attempting to assign to undeclared variable.
        node = new AssignmentStmtNode(loc, 
                new VariableAccessExprNode(loc, "undeclaredValue"),
                new LiteralStringExprNode(loc, "Anything"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }
    
    
    
    
    @Test
    void testMultiAssignmentStmtNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addFn("generate", new FunctionType(
                ExpandedType.unitExpandedType, 
                new ExpandedType(
                        List.of(PrimitiveType.intDefault, PrimitiveType.boolDefault))));

        
        node = new MultiAssignStmtNode(loc,
                List.of(Optional.of(new VarDeclNode(loc, "apple", 
                        new PrimitiveTypeNode(loc, PrimitiveEnum.INT))),
                        Optional.of(new VarDeclNode(loc, "oranges", 
                                new PrimitiveTypeNode(loc, PrimitiveEnum.BOOL))
                        )),
                new FunctionCallExprNode(loc, "generate", List.of())
                );
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        
        
        
        
        node = new MultiAssignStmtNode(loc,
                List.of(Optional.of(new VarDeclNode(loc, "banana", 
                        new PrimitiveTypeNode(loc, PrimitiveEnum.INT)))
                        , Optional.empty()),
                new FunctionCallExprNode(loc, "generate", List.of())
                );
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());

        
        node = new MultiAssignStmtNode(loc,
                List.of(Optional.empty(), Optional.empty()),
                new FunctionCallExprNode(loc, "generate", List.of())
                );
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());

        
        
        
        // Duplicate Assignment
        node = new MultiAssignStmtNode(loc,
                List.of(Optional.of(new VarDeclNode(loc, "apple", 
                        new PrimitiveTypeNode(loc, PrimitiveEnum.INT))),
                        Optional.of(new VarDeclNode(loc, "apple", 
                                new PrimitiveTypeNode(loc, PrimitiveEnum.BOOL))
                        )),
                new FunctionCallExprNode(loc, "generate", List.of())
                );
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        // Mismatched types
        node = new MultiAssignStmtNode(loc,
                List.of(Optional.of(new VarDeclNode(loc, "gorilla", 
                        new PrimitiveTypeNode(loc, PrimitiveEnum.INT))),
                        Optional.empty(),
                        Optional.empty()),
                new FunctionCallExprNode(loc, "generate", List.of())
                );
        assertThrows(SemanticException.class, () -> node.accept(visitor));
                
    }
    
    
    @Test
    void testVarDeclStmtNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        
        node = new VarDeclStmtNode(loc, 
                new VarDeclNode(loc, "testVar", 
                        new PrimitiveTypeNode(loc, PrimitiveEnum.INT)));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        
        
        
        // Redeclare Variables
        node = new VarDeclStmtNode(loc, 
                new VarDeclNode(loc, "testVar", 
                        new PrimitiveTypeNode(loc, PrimitiveEnum.BOOL)));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new VarDeclStmtNode(loc, 
                new VarDeclNode(loc, "testVar", 
                        new PrimitiveTypeNode(loc, PrimitiveEnum.INT)));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
    }

    
    
    @Test
    void testArrayDeclStmtNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        
        node = new ArrayDeclStmtNode(loc, "arr", 
                new TypeExprArrayNode(loc, 
                        new PrimitiveTypeNode(loc, PrimitiveEnum.INT), 
                        Optional.empty()));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        
        node = new ArrayDeclStmtNode(loc, "arrSized", 
                new TypeExprArrayNode(loc, 
                        new PrimitiveTypeNode(loc, PrimitiveEnum.INT), 
                        Optional.of(new LiteralIntExprNode(loc, "12"))));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        

        
        // Using Strings as array size.
        node = new ArrayDeclStmtNode(loc, "arrSized", 
                new TypeExprArrayNode(loc, 
                        new PrimitiveTypeNode(loc, PrimitiveEnum.INT), 
                        Optional.of(new LiteralStringExprNode(loc, "num"))));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        // Redeclare Variables
        node = new ArrayDeclStmtNode(loc, "arr", 
                new TypeExprArrayNode(loc, 
                        new PrimitiveTypeNode(loc, PrimitiveEnum.INT), 
                        Optional.empty()));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new VarDeclStmtNode(loc, 
                new VarDeclNode(loc, "arr", 
                        new PrimitiveTypeNode(loc, PrimitiveEnum.BOOL)));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }
    
    
    @Test
    void testProcedureCall() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addFn("foo", new FunctionType(ExpandedType.unitExpandedType,
                ExpandedType.unitExpandedType));

        node = new ProcedureStmtNode(loc, 
                new FunctionCallExprNode(loc, "foo", List.of()));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        

        
        visitor.context.addFn("bar", new FunctionType(ExpandedType.unitExpandedType,
                ExpandedType.intType));
        node = new ProcedureStmtNode(loc, 
                new FunctionCallExprNode(loc, "bar", List.of()));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        visitor.context.addFn("python", new FunctionType(ExpandedType.unitExpandedType,
                new ExpandedType(List.of(PrimitiveType.intDefault, 
                        PrimitiveType.boolDefault))));
        node = new ProcedureStmtNode(loc, 
                new FunctionCallExprNode(loc, "python", List.of()));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }
    
    
    @Test
    void testReturnStmt() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addRet(ExpandedType.unitExpandedType);
        node = new ReturnStmtNode(loc, List.of());
        result = node.accept(visitor);
        assertEquals(ResultType.VOID, result.assertSecond());
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addRet(ExpandedType.intType);
        node = new ReturnStmtNode(loc, 
                List.of(new LiteralIntExprNode(loc, "0")));
        result = node.accept(visitor);        
        assertEquals(ResultType.VOID, result.assertSecond());
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addRet(new ExpandedType(List.of(
                PrimitiveType.intDefault, PrimitiveType.boolDefault
                )));
        node = new ReturnStmtNode(loc, 
                List.of(
                        new LiteralIntExprNode(loc, "0"),
                        new LiteralBoolExprNode(loc, false)));
        result = node.accept(visitor);        
        assertEquals(ResultType.VOID, result.assertSecond());
        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addRet(ExpandedType.unitExpandedType);
        node = new ReturnStmtNode(loc, List.of(
                new LiteralIntExprNode(loc, "0")));
        assertThrows(SemanticException.class, () -> node.accept(visitor));        
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addRet(ExpandedType.intType);
        node = new ReturnStmtNode(loc, List.of());
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addRet(new ExpandedType(List.of(
                PrimitiveType.intDefault, PrimitiveType.boolDefault
                )));
        node = new ReturnStmtNode(loc, 
                List.of(new LiteralIntExprNode(loc, "0")));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }
    
    
    @Test
    void testIfElseStmtNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        
        
        
        /**
         * if (true)
         *    apple: int;
         * else
         *    apple: bool;
         */
        node = new IfElseStmtNode(loc, new LiteralBoolExprNode(loc, true), 
                new VarDeclStmtNode(loc, 
                        new VarDeclNode(loc, "apple", 
                                new PrimitiveTypeNode(loc, 
                                        PrimitiveEnum.INT))), 
                Optional.of(new VarDeclStmtNode(loc, 
                        new VarDeclNode(loc, "apple", 
                                new PrimitiveTypeNode(loc, 
                                        PrimitiveEnum.BOOL)))));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());

        
        
        /**
         * if (true) {
         *    return;
         * } else
         *    apple: bool;
         */
        visitor.context.addRet(ExpandedType.unitExpandedType);
        node = new IfElseStmtNode(loc, new LiteralBoolExprNode(loc, true), 
                new BlockStmtNode(loc, List.of(
                        new ReturnStmtNode(loc, List.of())
                )),
                Optional.of(new VarDeclStmtNode(loc, 
                        new VarDeclNode(loc, "apple", 
                                new PrimitiveTypeNode(loc, 
                                        PrimitiveEnum.BOOL)))));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());


        /**
         * if (true)
         *    apple: int;
         */
        visitor.context.addRet(ExpandedType.unitExpandedType);
        node = new IfElseStmtNode(loc, new LiteralBoolExprNode(loc, true), 
                new VarDeclStmtNode(loc, 
                        new VarDeclNode(loc, "apple", 
                                new PrimitiveTypeNode(loc, 
                                        PrimitiveEnum.INT))),  
                Optional.empty());
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());


        /**
         * if (true) {
         *    return;
         * } else {
         *    return;
         * }
         */
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addRet(ExpandedType.unitExpandedType);
        node = new IfElseStmtNode(loc, new LiteralBoolExprNode(loc, true), 
                new BlockStmtNode(loc, List.of(
                        new ReturnStmtNode(loc, List.of())
                )), 
                Optional.of(new BlockStmtNode(loc, List.of(
                        new ReturnStmtNode(loc, List.of())
                ))));
        result = node.accept(visitor);
        assertEquals(ResultType.VOID, result.assertSecond());
        

        /**
         * if (true) {
         *    return;
         * }
         */
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addRet(ExpandedType.unitExpandedType);
        node = new IfElseStmtNode(loc, new LiteralBoolExprNode(loc, false), 
                new BlockStmtNode(loc, List.of(
                        new ReturnStmtNode(loc, List.of())
                )),
                Optional.empty());
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        
        
        
        
        
        
        /**
         * if ({}) {
         *    return 123;
         * } else {
         *    return;
         * }
         */
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addRet(ExpandedType.unitExpandedType);
        node = new IfElseStmtNode(loc, new LiteralArrayExprNode(loc, List.of()),
                new BlockStmtNode(loc, List.of(
                        new ReturnStmtNode(loc, List.of(
                                new LiteralIntExprNode(loc, "123")))
                )),
                Optional.of(
                        new BlockStmtNode(loc, List.of(
                                new ReturnStmtNode(loc, List.of())
                        ))));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        /**
         * if ({}[0]) {
         *    return 123;
         * } else {
         *    return;
         * }
         */
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addRet(ExpandedType.unitExpandedType);
        node = new IfElseStmtNode(loc, 
                new ArrayAccessExprNode(loc,
                        new LiteralArrayExprNode(loc, List.of()),
                        new LiteralIntExprNode(loc, "0")), 
                new BlockStmtNode(loc, List.of(
                        new ReturnStmtNode(loc, List.of())
                )),
                Optional.of(
                        new BlockStmtNode(loc, List.of(
                                new ReturnStmtNode(loc, List.of())
                        ))));
        result = node.accept(visitor);
        assertEquals(ResultType.VOID, result.assertSecond());
        
        
        
        
        
        
        /**
         * if ({}[0]) {
         *    return 123;
         * }         
         */
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addRet(ExpandedType.intType);
        node = new IfElseStmtNode(loc, 
                new ArrayAccessExprNode(loc,
                        new LiteralArrayExprNode(loc, List.of()),
                        new LiteralIntExprNode(loc, "0")), 
                new BlockStmtNode(loc, List.of(
                        new ReturnStmtNode(loc, List.of(
                                new LiteralIntExprNode(loc, "123")))
                )),
                Optional.empty());
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        
        
        
        
        
        
        
        
        /**
         * if (121)
         *    apple: int;
         * else
         *    apple: bool;
         */
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new IfElseStmtNode(loc, new LiteralIntExprNode(loc, "121"), 
                new VarDeclStmtNode(loc, 
                        new VarDeclNode(loc, "apple", 
                                new PrimitiveTypeNode(loc, 
                                        PrimitiveEnum.INT))), 
                Optional.of(new VarDeclStmtNode(loc, 
                        new VarDeclNode(loc, "apple", 
                                new PrimitiveTypeNode(loc, 
                                        PrimitiveEnum.BOOL)))));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        /**
         * if (true) {
         *    return 123;
         * } else {
         *    return;
         * }
         */
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addRet(ExpandedType.unitExpandedType);
        node = new IfElseStmtNode(loc, new LiteralBoolExprNode(loc, true), 
                new BlockStmtNode(loc, List.of(
                        new ReturnStmtNode(loc, List.of(
                                new LiteralIntExprNode(loc, "123")))
                )),
                Optional.of(new BlockStmtNode(loc, List.of(
                        new ReturnStmtNode(loc, List.of())))));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        
        
    }
    
    @Test
    void testWhileStmtNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        
        
        
        /**
         * while (true)
         *    apple: int;
         */
        node = new WhileStmtNode(loc, new LiteralBoolExprNode(loc, true), 
                new VarDeclStmtNode(loc, 
                        new VarDeclNode(loc, "apple", 
                                new PrimitiveTypeNode(loc, 
                                        PrimitiveEnum.INT))));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());

        
        
        /**
         * while (true) {
         *    return;
         * }
         */
        visitor.context.addRet(ExpandedType.unitExpandedType);
        node = new WhileStmtNode(loc, new LiteralBoolExprNode(loc, true), 
                new BlockStmtNode(loc, List.of(
                        new ReturnStmtNode(loc, List.of())
                )));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());


        /**
         * while (true)
         *    apple: int;
         */
        visitor.context.addRet(ExpandedType.unitExpandedType);
        node = new WhileStmtNode(loc, new LiteralBoolExprNode(loc, true), 
                new VarDeclStmtNode(loc, 
                        new VarDeclNode(loc, "apple", 
                                new PrimitiveTypeNode(loc, 
                                        PrimitiveEnum.INT))));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());


        
        
        /**
         * while ({}) {
         *    return 123;
         * }
         */
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addRet(ExpandedType.unitExpandedType);
        node = new WhileStmtNode(loc, new LiteralArrayExprNode(loc, List.of()),
                new BlockStmtNode(loc, List.of(
                        new ReturnStmtNode(loc, List.of(
                                new LiteralIntExprNode(loc, "123")))
                )));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        
        /**
         * while (121)
         *    apple: int;
         */
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        node = new WhileStmtNode(loc, new LiteralIntExprNode(loc, "121"), 
                new VarDeclStmtNode(loc, 
                        new VarDeclNode(loc, "apple", 
                                new PrimitiveTypeNode(loc, 
                                        PrimitiveEnum.INT))));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        /**
         * while (true) {
         *    return 123;
         * }
         */
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        visitor.context.addRet(ExpandedType.unitExpandedType);
        node = new WhileStmtNode(loc, new LiteralBoolExprNode(loc, true), 
                new BlockStmtNode(loc, List.of(
                        new ReturnStmtNode(loc, List.of(
                                new LiteralIntExprNode(loc, "123")))
                )));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
       

    }
    
    
    @Test
    void testBlockStmt() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(null);
        
        node = new BlockStmtNode(loc, List.of());
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        

        node = new BlockStmtNode(loc, List.of(
                new VarDeclStmtNode(loc, 
                        new VarDeclNode(loc, "ringo", 
                                new PrimitiveTypeNode(loc, PrimitiveEnum.INT)))
                ));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        
        
        visitor.context.addRet(ExpandedType.unitExpandedType);
        node = new BlockStmtNode(loc, List.of(
                new ReturnStmtNode(loc, List.of())
                ));
        result = node.accept(visitor);
        assertEquals(ResultType.VOID, result.assertSecond());
        
        
        visitor.context.addRet(ExpandedType.unitExpandedType);
        node = new BlockStmtNode(loc, List.of(
                new ReturnStmtNode(loc, List.of()),
                new VarDeclStmtNode(loc, 
                        new VarDeclNode(loc, "ringo", 
                                new PrimitiveTypeNode(loc, PrimitiveEnum.INT)))
                ));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }
    
    
}
