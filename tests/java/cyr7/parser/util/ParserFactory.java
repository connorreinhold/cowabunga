package cyr7.parser.util;

import cyr7.ast.expr.ExprNode;
import cyr7.ast.stmt.StmtNode;
import cyr7.ast.stmt.VarInitStmtNode;
import cyr7.ast.toplevel.XiProgramNode;
import cyr7.lexer.MultiFileLexer;
import cyr7.lexer.MyLexer;
import cyr7.parser.XiParser;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;

import java.io.StringReader;
import java.util.List;
public class ParserFactory {

    public static final Location LOC = new Location(1, 1);

    static public XiParser make(String contents, boolean isIxi)
            throws Exception {
        MyLexer lexer = new MultiFileLexer(new StringReader(contents), "", isIxi);
        return new XiParser(lexer, new ComplexSymbolFactory());
    }

    public static List<StmtNode> parseStatement(String stmt) throws Exception {
        String program = "f() {\n" + stmt + "\n}";
        XiParser parser = ParserFactory.make(program, false);
        XiProgramNode node = (XiProgramNode) parser.parse().value;
        return node.functions.get(0).block.statements;
    }

    public static ExprNode parseExpr(String expr) throws Exception {
        String program = "f() { i: int = " + expr + " }";
        XiParser parser = ParserFactory.make(program, false);
        XiProgramNode node = (XiProgramNode) parser.parse().value;
        return ((VarInitStmtNode) node.functions.get(0).block.statements.get(0)).initializer;
    }

    private ParserFactory() { }

}
