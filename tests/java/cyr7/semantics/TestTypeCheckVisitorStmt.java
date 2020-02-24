package cyr7.semantics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import cyr7.ast.ArrayVariableAccessNode;
import cyr7.ast.Node;
import cyr7.ast.VarDeclNode;
import cyr7.ast.VariableAccessNode;
import cyr7.ast.expr.ArrayLiteralAccessExprNode;
import cyr7.ast.expr.ArrayLiteralExprNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
import cyr7.ast.expr.literalexpr.LiteralStringExprNode;
import cyr7.ast.stmt.ArrayDeclStmtNode;
import cyr7.ast.stmt.AssignmentStmtNode;
import cyr7.ast.stmt.BlockStmtNode;
import cyr7.ast.stmt.ExprStmtNode;
import cyr7.ast.stmt.IfElseStmtNode;
import cyr7.ast.stmt.MultiAssignStmtNode;
import cyr7.ast.stmt.ProcedureStmtNode;
import cyr7.ast.stmt.ReturnStmtNode;
import cyr7.ast.stmt.StmtNode;
import cyr7.ast.stmt.VarDeclStmtNode;
import cyr7.ast.stmt.VarInitStmtNode;
import cyr7.ast.stmt.WhileStmtNode;
import cyr7.ast.type.PrimitiveEnum;
import cyr7.ast.type.PrimitiveTypeNode;
import cyr7.ast.type.TypeExprArrayNode;
import cyr7.ast.type.TypeExprNode;
import cyr7.exceptions.SemanticException;
import cyr7.typecheck.TypeCheckVisitor;
import cyr7.util.OneOfTwo;

class TestTypeCheckVisitorStmt {

    Context context;
    TypeCheckVisitor visitor;
    Node node;
    OneOfTwo<ExpandedType, ResultType> result;
    
    @Test
    void testExprStmtNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        
        context.addFn("bool_of_int", new FunctionType(ExpandedType.intType, 
                                                ExpandedType.boolType));
        node = new ExprStmtNode(null, 
                new FunctionCallExprNode(null, "bool_of_int", 
                        List.of(new LiteralIntExprNode(null, "1000"))));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        
        
        context.addFn("sleep", new FunctionType(ExpandedType.intType, 
                ExpandedType.unitExpandedType));
        node = new ExprStmtNode(null, 
                new FunctionCallExprNode(null, "sleep", 
                        List.of(new LiteralIntExprNode(null, "1000"))));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context.addFn("factorize", new FunctionType(ExpandedType.intType, 
                new ExpandedType(
                        List.of(OrdinaryType.intType, OrdinaryType.intType))));
        node = new ExprStmtNode(null, 
                new FunctionCallExprNode(null, "factorize", 
                        List.of(new LiteralIntExprNode(null, "1000"))));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

    }
    
    
    @Test
    void testVarInit() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        
        node = new VarInitStmtNode(null, 
                new VarDeclNode(null, "alias", 
                        new PrimitiveTypeNode(null, PrimitiveEnum.INT)), 
                new LiteralIntExprNode(null, "123"));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        
        
        // Assigning Mismatching types
        node = new VarInitStmtNode(null, 
                new VarDeclNode(null, "alias2", 
                        new PrimitiveTypeNode(null, PrimitiveEnum.INT)), 
                new LiteralBoolExprNode(null, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        // Redeclaring variable names
        node = new VarInitStmtNode(null, 
                new VarDeclNode(null, "alias", 
                        new PrimitiveTypeNode(null, PrimitiveEnum.BOOL)),
                new LiteralBoolExprNode(null, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
    }
    
    
    @Test
    void testAssignmentStmtNode() {
        context = new HashMapStackContext();
        context.addVar("cash", OrdinaryType.intType);
        visitor = new TypeCheckVisitor(context);
        
        node = new AssignmentStmtNode(null, 
                new VariableAccessNode(null, "cash"), 
                new LiteralIntExprNode(null, "123"));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        
        
        // Mismatched types
        node = new AssignmentStmtNode(null, 
                new VariableAccessNode(null, "cash"), 
                new LiteralBoolExprNode(null, false));
        assertThrows(SemanticException.class, () -> node.accept(visitor));


        
        // Attempting to assign to undeclared variable.
        node = new AssignmentStmtNode(null, 
                new VariableAccessNode(null, "undeclaredValue"), 
                new LiteralStringExprNode(null, "Anything"));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }
    
    
    
    
    @Test
    void testMultiAssignmentStmtNode() {
        context = new HashMapStackContext();
        context.addFn("generate", new FunctionType(
                ExpandedType.unitExpandedType, 
                new ExpandedType(
                        List.of(OrdinaryType.intType, OrdinaryType.boolType))));
        visitor = new TypeCheckVisitor(context);

        
        node = new MultiAssignStmtNode(null,
                List.of(Optional.of(new VarDeclNode(null, "apple", 
                        new PrimitiveTypeNode(null, PrimitiveEnum.INT))),
                        Optional.of(new VarDeclNode(null, "oranges", 
                                new PrimitiveTypeNode(null, PrimitiveEnum.BOOL))
                        )),
                new FunctionCallExprNode(null, "generate", List.of())
                );
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        
        
        
        
        node = new MultiAssignStmtNode(null,
                List.of(Optional.of(new VarDeclNode(null, "banana", 
                        new PrimitiveTypeNode(null, PrimitiveEnum.INT)))
                        , Optional.empty()),
                new FunctionCallExprNode(null, "generate", List.of())
                );
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());

        
        node = new MultiAssignStmtNode(null,
                List.of(Optional.empty(), Optional.empty()),
                new FunctionCallExprNode(null, "generate", List.of())
                );
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());

        
        
        
        // Duplicate Assignment
        node = new MultiAssignStmtNode(null,
                List.of(Optional.of(new VarDeclNode(null, "apple", 
                        new PrimitiveTypeNode(null, PrimitiveEnum.INT))),
                        Optional.of(new VarDeclNode(null, "oranges", 
                                new PrimitiveTypeNode(null, PrimitiveEnum.BOOL))
                        )),
                new FunctionCallExprNode(null, "generate", List.of())
                );
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        
        // Mismatched types
        node = new MultiAssignStmtNode(null,
                List.of(Optional.of(new VarDeclNode(null, "gorilla", 
                        new PrimitiveTypeNode(null, PrimitiveEnum.INT))),
                        Optional.empty(),
                        Optional.empty()),
                new FunctionCallExprNode(null, "generate", List.of())
                );
        assertThrows(SemanticException.class, () -> node.accept(visitor));
                
    }
    
    
    @Test
    void testVarDeclStmtNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        
        node = new VarDeclStmtNode(null, 
                new VarDeclNode(null, "testVar", 
                        new PrimitiveTypeNode(null, PrimitiveEnum.INT)));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        
        
        
        // Redeclare Variables
        node = new VarDeclStmtNode(null, 
                new VarDeclNode(null, "testVar", 
                        new PrimitiveTypeNode(null, PrimitiveEnum.BOOL)));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new VarDeclStmtNode(null, 
                new VarDeclNode(null, "testVar", 
                        new PrimitiveTypeNode(null, PrimitiveEnum.INT)));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
    }

    
    
    @Test
    void testArrayDeclStmtNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        
        node = new ArrayDeclStmtNode(null, "arr", 
                new TypeExprArrayNode(null, 
                        new PrimitiveTypeNode(null, PrimitiveEnum.INT), 
                        Optional.empty()));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        
        node = new ArrayDeclStmtNode(null, "arrSized", 
                new TypeExprArrayNode(null, 
                        new PrimitiveTypeNode(null, PrimitiveEnum.INT), 
                        Optional.of(new LiteralIntExprNode(null, "12"))));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        

        
        // Using Strings as array size.
        node = new ArrayDeclStmtNode(null, "arrSized", 
                new TypeExprArrayNode(null, 
                        new PrimitiveTypeNode(null, PrimitiveEnum.INT), 
                        Optional.of(new LiteralStringExprNode(null, "num"))));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        // Redeclare Variables
        node = new ArrayDeclStmtNode(null, "arr", 
                new TypeExprArrayNode(null, 
                        new PrimitiveTypeNode(null, PrimitiveEnum.INT), 
                        Optional.empty()));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        node = new VarDeclStmtNode(null, 
                new VarDeclNode(null, "arr", 
                        new PrimitiveTypeNode(null, PrimitiveEnum.BOOL)));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }
    
    
    @Test
    void testProcedureCall() {
        context = new HashMapStackContext();
        context.addFn("foo", new FunctionType(ExpandedType.unitExpandedType,
                ExpandedType.unitExpandedType));
        visitor = new TypeCheckVisitor(context);

        node = new ProcedureStmtNode(null, 
                new FunctionCallExprNode(null, "foo", List.of()));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        

        
        context.addFn("bar", new FunctionType(ExpandedType.unitExpandedType,
                ExpandedType.intType));
        node = new ProcedureStmtNode(null, 
                new FunctionCallExprNode(null, "bar", List.of()));
        assertThrows(SemanticException.class, () -> node.accept(visitor));

        
        context.addFn("python", new FunctionType(ExpandedType.unitExpandedType,
                new ExpandedType(List.of(OrdinaryType.intType, 
                        OrdinaryType.boolType))));
        node = new ProcedureStmtNode(null, 
                new FunctionCallExprNode(null, "python", List.of()));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }
    
    
    @Test
    void testReturnStmt() {
        context = new HashMapStackContext();
        context.addRet(ExpandedType.unitExpandedType);
        visitor = new TypeCheckVisitor(context);
        node = new ReturnStmtNode(null, List.of());
        result = node.accept(visitor);
        assertEquals(ResultType.VOID, result.assertSecond());
        
        
        context = new HashMapStackContext();
        context.addRet(ExpandedType.intType);
        visitor = new TypeCheckVisitor(context);
        node = new ReturnStmtNode(null, 
                List.of(new LiteralIntExprNode(null, "0")));
        result = node.accept(visitor);        
        assertEquals(ResultType.VOID, result.assertSecond());
        
        
        context = new HashMapStackContext();
        context.addRet(new ExpandedType(List.of(
                OrdinaryType.intType, OrdinaryType.boolType
                )));
        visitor = new TypeCheckVisitor(context);
        node = new ReturnStmtNode(null, 
                List.of(
                        new LiteralIntExprNode(null, "0"),
                        new LiteralBoolExprNode(null, false)));
        result = node.accept(visitor);        
        assertEquals(ResultType.VOID, result.assertSecond());
        
        
        
        context = new HashMapStackContext();
        context.addRet(ExpandedType.unitExpandedType);
        visitor = new TypeCheckVisitor(context);
        node = new ReturnStmtNode(null, List.of(
                new LiteralIntExprNode(null, "0")));
        assertThrows(SemanticException.class, () -> node.accept(visitor));        
        
        
        context = new HashMapStackContext();
        context.addRet(ExpandedType.intType);
        visitor = new TypeCheckVisitor(context);
        node = new ReturnStmtNode(null, List.of());
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        context = new HashMapStackContext();
        context.addRet(new ExpandedType(List.of(
                OrdinaryType.intType, OrdinaryType.boolType
                )));
        visitor = new TypeCheckVisitor(context);
        node = new ReturnStmtNode(null, 
                List.of(new LiteralIntExprNode(null, "0")));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }
    
    
    @Test
    void testIfElseStmtNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        
        
        
        /**
         * if (true)
         *    apple: int;
         * else
         *    apple: bool;
         */
        node = new IfElseStmtNode(null, new LiteralBoolExprNode(null, true), 
                new VarDeclStmtNode(null, 
                        new VarDeclNode(null, "apple", 
                                new PrimitiveTypeNode(null, 
                                        PrimitiveEnum.INT))), 
                Optional.of(new VarDeclStmtNode(null, 
                        new VarDeclNode(null, "apple", 
                                new PrimitiveTypeNode(null, 
                                        PrimitiveEnum.BOOL)))));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());

        
        
        /**
         * if (true) {
         *    return;
         * } else
         *    apple: bool;
         */
        context.addRet(ExpandedType.unitExpandedType);
        node = new IfElseStmtNode(null, new LiteralBoolExprNode(null, true), 
                new BlockStmtNode(null, List.of(
                        new ReturnStmtNode(null, List.of())
                )),
                Optional.of(new VarDeclStmtNode(null, 
                        new VarDeclNode(null, "apple", 
                                new PrimitiveTypeNode(null, 
                                        PrimitiveEnum.BOOL)))));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());


        /**
         * if (true)
         *    apple: int;
         */
        context.addRet(ExpandedType.unitExpandedType);
        node = new IfElseStmtNode(null, new LiteralBoolExprNode(null, true), 
                new VarDeclStmtNode(null, 
                        new VarDeclNode(null, "apple", 
                                new PrimitiveTypeNode(null, 
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
        context.addRet(ExpandedType.unitExpandedType);
        visitor = new TypeCheckVisitor(context);
        node = new IfElseStmtNode(null, new LiteralBoolExprNode(null, true), 
                new BlockStmtNode(null, List.of(
                        new ReturnStmtNode(null, List.of())
                )), 
                Optional.of(new BlockStmtNode(null, List.of(
                        new ReturnStmtNode(null, List.of())
                ))));
        result = node.accept(visitor);
        assertEquals(ResultType.VOID, result.assertSecond());
        

        /**
         * if (true) {
         *    return;
         * }
         */
        context = new HashMapStackContext();
        context.addRet(ExpandedType.unitExpandedType);
        visitor = new TypeCheckVisitor(context);
        node = new IfElseStmtNode(null, new LiteralBoolExprNode(null, false), 
                new BlockStmtNode(null, List.of(
                        new ReturnStmtNode(null, List.of())
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
        context.addRet(ExpandedType.unitExpandedType);
        visitor = new TypeCheckVisitor(context);
        node = new IfElseStmtNode(null, new ArrayLiteralExprNode(null, List.of()), 
                new BlockStmtNode(null, List.of(
                        new ReturnStmtNode(null, List.of(
                                new LiteralIntExprNode(null, "123")))
                )),
                Optional.of(
                        new BlockStmtNode(null, List.of(
                                new ReturnStmtNode(null, List.of())
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
        context.addRet(ExpandedType.unitExpandedType);
        visitor = new TypeCheckVisitor(context);
        node = new IfElseStmtNode(null, 
                new ArrayLiteralAccessExprNode(null, 
                        new ArrayLiteralExprNode(null, List.of()),
                        new LiteralIntExprNode(null, "0")), 
                new BlockStmtNode(null, List.of(
                        new ReturnStmtNode(null, List.of())
                )),
                Optional.of(
                        new BlockStmtNode(null, List.of(
                                new ReturnStmtNode(null, List.of())
                        ))));
        result = node.accept(visitor);
        assertEquals(ResultType.VOID, result.assertSecond());
        
        
        
        
        
        
        /**
         * if ({}[0]) {
         *    return 123;
         * }         
         */
        context = new HashMapStackContext();
        context.addRet(ExpandedType.intType);
        visitor = new TypeCheckVisitor(context);
        node = new IfElseStmtNode(null, 
                new ArrayLiteralAccessExprNode(null, 
                        new ArrayLiteralExprNode(null, List.of()),
                        new LiteralIntExprNode(null, "0")), 
                new BlockStmtNode(null, List.of(
                        new ReturnStmtNode(null, List.of(
                                new LiteralIntExprNode(null, "123")))
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
        visitor = new TypeCheckVisitor(context);
        node = new IfElseStmtNode(null, new LiteralIntExprNode(null, "121"), 
                new VarDeclStmtNode(null, 
                        new VarDeclNode(null, "apple", 
                                new PrimitiveTypeNode(null, 
                                        PrimitiveEnum.INT))), 
                Optional.of(new VarDeclStmtNode(null, 
                        new VarDeclNode(null, "apple", 
                                new PrimitiveTypeNode(null, 
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
        context.addRet(ExpandedType.unitExpandedType);
        visitor = new TypeCheckVisitor(context);
        node = new IfElseStmtNode(null, new LiteralBoolExprNode(null, true), 
                new BlockStmtNode(null, List.of(
                        new ReturnStmtNode(null, List.of(
                                new LiteralIntExprNode(null, "123")))
                )),
                Optional.of(new BlockStmtNode(null, List.of(
                        new ReturnStmtNode(null, List.of())))));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        
        
    }
    
    @Test
    void testWhileStmtNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        
        
        
        /**
         * while (true)
         *    apple: int;
         */
        node = new WhileStmtNode(null, new LiteralBoolExprNode(null, true), 
                new VarDeclStmtNode(null, 
                        new VarDeclNode(null, "apple", 
                                new PrimitiveTypeNode(null, 
                                        PrimitiveEnum.INT))));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());

        
        
        /**
         * while (true) {
         *    return;
         * }
         */
        context.addRet(ExpandedType.unitExpandedType);
        node = new WhileStmtNode(null, new LiteralBoolExprNode(null, true), 
                new BlockStmtNode(null, List.of(
                        new ReturnStmtNode(null, List.of())
                )));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());


        /**
         * while (true)
         *    apple: int;
         */
        context.addRet(ExpandedType.unitExpandedType);
        node = new WhileStmtNode(null, new LiteralBoolExprNode(null, true), 
                new VarDeclStmtNode(null, 
                        new VarDeclNode(null, "apple", 
                                new PrimitiveTypeNode(null, 
                                        PrimitiveEnum.INT))));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());


        
        
        /**
         * while ({}) {
         *    return 123;
         * }
         */
        context = new HashMapStackContext();
        context.addRet(ExpandedType.unitExpandedType);
        visitor = new TypeCheckVisitor(context);
        node = new WhileStmtNode(null, new ArrayLiteralExprNode(null, List.of()), 
                new BlockStmtNode(null, List.of(
                        new ReturnStmtNode(null, List.of(
                                new LiteralIntExprNode(null, "123")))
                )));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        
        /**
         * while (121)
         *    apple: int;
         */
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        node = new WhileStmtNode(null, new LiteralIntExprNode(null, "121"), 
                new VarDeclStmtNode(null, 
                        new VarDeclNode(null, "apple", 
                                new PrimitiveTypeNode(null, 
                                        PrimitiveEnum.INT))));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
        
        
        /**
         * while (true) {
         *    return 123;
         * }
         */
        context = new HashMapStackContext();
        context.addRet(ExpandedType.unitExpandedType);
        visitor = new TypeCheckVisitor(context);
        node = new WhileStmtNode(null, new LiteralBoolExprNode(null, true), 
                new BlockStmtNode(null, List.of(
                        new ReturnStmtNode(null, List.of(
                                new LiteralIntExprNode(null, "123")))
                )));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
       

    }
    
    
    @Test
    void testBlockStmt() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        
        node = new BlockStmtNode(null, List.of());
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        

        node = new BlockStmtNode(null, List.of(
                new VarDeclStmtNode(null, 
                        new VarDeclNode(null, "ringo", 
                                new PrimitiveTypeNode(null, PrimitiveEnum.INT)))
                ));
        result = node.accept(visitor);
        assertEquals(ResultType.UNIT, result.assertSecond());
        
        
        context.addRet(ExpandedType.unitExpandedType);
        node = new BlockStmtNode(null, List.of(
                new ReturnStmtNode(null, List.of())
                ));
        result = node.accept(visitor);
        assertEquals(ResultType.VOID, result.assertSecond());
        
        
        context.addRet(ExpandedType.unitExpandedType);
        node = new BlockStmtNode(null, List.of(
                new ReturnStmtNode(null, List.of()),
                new VarDeclStmtNode(null, 
                        new VarDeclNode(null, "ringo", 
                                new PrimitiveTypeNode(null, PrimitiveEnum.INT)))
                ));
        assertThrows(SemanticException.class, () -> node.accept(visitor));
    }
    
    
}
