package cyr7.semantics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import cyr7.ast.Node;
import cyr7.ast.VarDeclNode;
import cyr7.ast.VariableAccessNode;
import cyr7.ast.expr.FunctionCallExprNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.expr.literalexpr.LiteralIntExprNode;
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
import cyr7.exceptions.SemanticException;
import cyr7.typecheck.TypeCheckVisitor;
import cyr7.util.OneOfTwo;

class TypeCheckVisitorStmtTests {

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
        assertEquals(result.assertSecond(), ResultType.UNIT);
        
        
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
    void testAssignmentStmtNode() {
        context = new HashMapStackContext();
        context.addVar("cash", OrdinaryType.intType);
        visitor = new TypeCheckVisitor(context);
        
        node = new AssignmentStmtNode(null, 
                new VariableAccessNode(null, "cash"), 
                new LiteralIntExprNode(null, "123"));
        result = node.accept(visitor);
        assertEquals(result.assertSecond(), ResultType.UNIT);
        
    }
    
    @Test
    void testWhileStmtNode() {
        context = new HashMapStackContext();
        visitor = new TypeCheckVisitor(context);
        
//        TODO: Test other statement nodes first
        node = new WhileStmtNode(null, 
                new LiteralBoolExprNode(null, true), 
                null);
        fail("");
    }

    
/*
 * 
 *     @Override
    public OneOfTwo<ExpandedType, ResultType> visit(ArrayDeclStmtNode n) {
        if (context.contains(n.identifier)) {
            throw new SemanticException("Duplicate variablee declaration");
        }
        
        ExpandedType expectedArray = n.type.accept(this).assertFirst();
        if (!expectedArray.isArray()) {
            throw new SemanticException("Expected an array, but found a "
                    + "non-array type instead");
        }
        if (expectedArray.isOrdinary()) {
            context.addVar(n.identifier, expectedArray.getOrdinaryType());
            return OneOfTwo.ofSecond(ResultType.UNIT);
        } else {
            throw new SemanticException("Expected an array or primitive, but "
                    + "found a tuple");
        }
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(AssignmentStmtNode n) {
        ExpandedType lhsType = n.access.accept(this).assertFirst();
        ExpandedType rhsType = n.value.accept(this).assertFirst();
        
        if (rhsType.isASubtypeOf(lhsType)) {
            return OneOfTwo.ofSecond(ResultType.UNIT);
        }
        throw new SemanticException("Inequivalent type assignment");
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(BlockStmtNode n) {
        context.push();
        Iterator<StmtNode> stmtIterator = n.statements.iterator();
        while (stmtIterator.hasNext()) {
            StmtNode stmt = stmtIterator.next();
            ResultType type = stmt.accept(this).assertSecond();
            if (stmtIterator.hasNext() && type == ResultType.VOID) {
                throw new SemanticException("Early void statement");
            } else {
                context.pop();
                return OneOfTwo.ofSecond(type);
            }
        }
        context.pop();
        return OneOfTwo.ofSecond(ResultType.UNIT);
    }


    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(IfElseStmtNode n) {
        context.push();
        ExpandedType guardType = n.guard.accept(this).assertFirst();
        context.pop();

        if (!guardType.isSubtypeOfBool()) {
            throw new SemanticException(
                    "Guard expression does not evaluate to bool");
        }

        context.push();
        ResultType ifType = n.ifBlock.accept(this).assertSecond();
        context.pop();

        if (n.elseBlock.isPresent()) {
            context.push();
            ResultType elseType = n.elseBlock.get().accept(this).assertSecond();
            context.pop();
            return OneOfTwo.ofSecond(
                        ResultType.leastUpperBound(ifType, elseType));
        } else {
            return OneOfTwo.ofSecond(ifType);
        }
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(MultiAssignStmtNode n) {
        ExpandedType types = n.initializer.accept(this).assertFirst();
        if (types.isUnit()) {
            throw new SemanticException("Expected function call, but found a "
                    + "procedure instead.");
        }
        List<OrdinaryType> typeList = types.getTypes();
        List<Optional<VarDeclNode>> varDecls = n.varDecls;
        if (typeList.size() != varDecls.size()) {
            throw new SemanticException("Number of variable declarations and "
                    + "function return values do not match");
        }
        Iterator<OrdinaryType> typeIterator = typeList.iterator();
        Iterator<Optional<VarDeclNode>> declIterator = varDecls.iterator();
        while(typeIterator.hasNext() && declIterator.hasNext()) {
            OrdinaryType returnedType = typeIterator.next();
            Optional<VarDeclNode> maybeVarDecl = declIterator.next();
            if (maybeVarDecl.isPresent()) {
                VarDeclNode varDecl = maybeVarDecl.get();
                ExpandedType varDeclType = varDecl.accept(this).assertFirst();
                assert(varDeclType.isOrdinary());
                OrdinaryType varDeclTrueType = varDeclType.getOrdinaryType();
                if (returnedType.isSubtypeOf(varDeclTrueType)) {
                    context.addVar(varDecl.identifier, varDeclTrueType);
                } else {
                    throw new SemanticException("Mismatched types");
                }
            } // else is a wildcard and returned value is thrown away.
        }
        return OneOfTwo.ofSecond(ResultType.UNIT);
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(ProcedureStmtNode n) {
        ExpandedType type = n.procedureCall.accept(this).assertFirst();
        if (type.isUnit()) {
            return OneOfTwo.ofSecond(ResultType.UNIT);
        } else {
            throw new SemanticException("Expected a procedure but found a "
                    + "function instead.");
        }
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(ReturnStmtNode n) {
        int numOfValues = n.exprs.size();
        Optional<ExpandedType> maybeTypes = context.getRet();
        assert(maybeTypes.isPresent());
        ExpandedType expected = maybeTypes.get();
        ExpandedType exprType;
        switch (numOfValues) {
        case 0:
            if (expected.isUnit()) {
                return OneOfTwo.ofSecond(ResultType.VOID);
            }
            break;
        case 1:
            if (expected.isOrdinary()) {
                exprType = n.exprs.get(0).accept(this).assertFirst();
                if (exprType.isASubtypeOf(expected)) {
                    return OneOfTwo.ofSecond(ResultType.VOID);
                }
            }
            break;
        default:
            exprType = new ExpandedType(n.exprs.stream()
                    .map(e -> {
                        ExpandedType t = e.accept(this).assertFirst();
                        assert(t.isOrdinary());
                        return t.getOrdinaryType();
                    }).collect(Collectors.toList()));
            if (exprType.isASubtypeOf(expected)) {
                return OneOfTwo.ofSecond(ResultType.VOID);
            }
            break;
        }
        throw new SemanticException("Return types do not match the function's "
                + "expected return types");
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(VarDeclStmtNode n) {
        ExpandedType type = n.varDecl.accept(this).assertFirst();
        if (type.isOrdinary()) {
            context.addVar(n.varDecl.identifier, type.getOrdinaryType());
            return OneOfTwo.ofSecond(ResultType.UNIT);
        } else {
            throw new SemanticException();
        }
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(VarInitStmtNode n) {
        VarDeclNode varDecl = n.varDecl;
        ExpandedType varDeclType = varDecl.accept(this).assertFirst();
        ExpandedType initializedType = n.initializer.accept(this).assertFirst();
        
        if (initializedType.isASubtypeOf(varDeclType)) {
            assert(varDeclType.isOrdinary() && initializedType.isOrdinary());
            context.addVar(varDecl.identifier, varDeclType.getOrdinaryType());
            return OneOfTwo.ofSecond(ResultType.UNIT);
        } else {
            throw new SemanticException("Mismatched types");
        }
    }

    @Override
    public OneOfTwo<ExpandedType, ResultType> visit(WhileStmtNode n) {
        ExpandedType guardType = n.guard.accept(this).assertFirst();
        if (!guardType.isSubtypeOfBool()) {
            throw new SemanticException("Guard expression does "
                    + "not evaluate to bool");
        }

        context.push();
        n.block.accept(this);
        context.pop();

        return OneOfTwo.ofSecond(ResultType.UNIT);
    }

 */
    
}
