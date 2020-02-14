package cyr7.parser.xi;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import cyr7.ast.FunctionHeaderDeclNode;
import cyr7.ast.UseNode;
import cyr7.ast.XiProgramNode;
import cyr7.ast.expr.literalexpr.LiteralBoolExprNode;
import cyr7.ast.stmt.IfElseStmtNode;
import cyr7.ast.stmt.ReturnStmtNode;
import cyr7.ast.stmt.VarDeclNode;
import cyr7.ast.type.ITypeExprNode;
import cyr7.ast.type.PrimitiveEnum;
import cyr7.parser.XiParser;
import cyr7.parser.util.GenerateEmptyTree;
import cyr7.parser.util.ParserFactory;

class TestIf {

    XiParser parser;
    String program;
    
    @Test
    void test() throws Exception {
        
        // Normal if-else statement
        program = "main() { if true return 1 else return 4 }";        
        parser = ParserFactory.make(program, false);
        assertDoesNotThrow(parser::parse);
        
        // Without blocks, the next statements after if/else 
        // is one statement long
        program = "main() { if true return 1 else return 4 return 0 }";        
        parser = ParserFactory.make(program, false);
        assertDoesNotThrow(parser::parse);

        // Dangling else
        program = "main() { if true return 1 if false return 3 else return 4 }";        
        parser = ParserFactory.make(program, false);
        assertDoesNotThrow(parser::parse);
        
    }

}
