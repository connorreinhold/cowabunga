package cyr7.parser.util;

import cyr7.ast.XiProgramNode;
import cyr7.ast.expr.ExprNode;
import cyr7.ast.stmt.StmtNode;
import cyr7.ast.stmt.VarDeclStmtNode;
import java_cup.runtime.ComplexSymbolFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import cyr7.exceptions.LexerException;
import cyr7.lexer.MultiFileLexer;
import cyr7.lexer.MyLexer;
import cyr7.parser.XiParser;
public class ParserFactory {

    static public XiParser make(String contents, boolean isIxi)
            throws Exception {
        MyLexer lexer = new MultiFileLexer(new StringReader(contents), isIxi);
        XiParser parser = new XiParser(lexer, new ComplexSymbolFactory());
        return parser;
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
        return ((VarDeclStmtNode) node.functions.get(0).block.statements.get(0)).initializer.get();
    }

    private ParserFactory() { }

}
