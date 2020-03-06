package cyr7.ir.interpret;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import cyr7.ir.nodes.IRBinOp.OpType;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRStmt;
import cyr7.ir.parse.IRLexer;
import cyr7.ir.parse.IRParser;
import cyr7.ir.visit.CheckCanonicalIRVisitor;
import cyr7.ir.visit.CheckConstFoldedIRVisitor;
import edu.cornell.cs.cs4120.util.CodeWriterSExpPrinter;
import edu.cornell.cs.cs4120.util.SExpPrinter;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class Main {

    public static void main(String[] args) {
        // Runs a simple program in the interpreter

        // IR roughly corresponds to the following:
        //     a(i:int, j:int): int, int {
        //         return i, (2 * j);
        //     }
        //     b(i:int, j:int): int {
        //         x:int, y:int = a(i, j);
        //         return x + 5 * y;
        //     }
        IRNodeFactory make = new IRNodeFactory_c(new Location(-1, -1));
        {

        String arg0 = Configuration.ABSTRACT_ARG_PREFIX + 0;
        String arg1 = Configuration.ABSTRACT_ARG_PREFIX + 1;
        String ret0 = Configuration.ABSTRACT_RET_PREFIX + 0;
        String ret1 = Configuration.ABSTRACT_RET_PREFIX + 1;

        IRStmt aBody = make
                .IRSeq(make.IRMove(make.IRTemp("i"), make.IRTemp(arg0)),
                        make.IRMove(make.IRTemp("j"), make.IRTemp(arg1)),
                        make.IRMove(make.IRTemp(ret0), make.IRTemp("i")),
                        make.IRMove(make.IRTemp(ret1), make.IRBinOp(OpType.MUL,
                                make.IRConst(2), make.IRTemp("j"))),
                        make.IRReturn());
        IRFuncDecl aFunc = make.IRFuncDecl("a", aBody);

        IRStmt bBody =
                    make.IRSeq(
                            make.IRCallStmt(Arrays.asList("x", "y"),
                                    make.IRName("a"),
                                    List.of(make.IRTemp("i"),
                                            make.IRTemp("j"))),

                        make.IRMove(make.IRTemp(ret0), make.IRBinOp(OpType.ADD,
                                make.IRTemp("x"), make.IRBinOp(OpType.MUL,
                                        make.IRConst(5), make.IRTemp("y")))),
                        make.IRReturn());
        IRFuncDecl bFunc = make.IRFuncDecl("b", bBody);

        IRCompUnit compUnit = make.IRCompUnit("test");
        compUnit.appendFunc(aFunc);
        compUnit.appendFunc(bFunc);

        // IR pretty-printer demo
        System.out.println("Code:");
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw);
             SExpPrinter sp = new CodeWriterSExpPrinter(pw)) {
            compUnit.printSExp(sp);
        }
        System.out.println(sw);

        // IR interpreter demo
        {
            IRSimulator sim = new IRSimulator(compUnit);
            long result = sim.call("b", 2, 1);
            System.out.println("b(2,1) == " + result);
        }

        // IR canonical checker demo
        {
            CheckCanonicalIRVisitor cv = new CheckCanonicalIRVisitor();
            System.out.print("Canonical?: ");
            System.out.println(cv.visit(compUnit));
        }

        // IR constant-folding checker demo
        {
            CheckConstFoldedIRVisitor cv = new CheckConstFoldedIRVisitor();
            System.out.print("Constant-folded?: ");
            System.out.println(cv.visit(compUnit));
        }

        // IR parser demo
        String prog = sw.toString();
        try (StringReader r = new StringReader(prog)) {
            IRParser parser =
                        new IRParser(new IRLexer(r), make);
            IRCompUnit compUnit_ = null;
            try {
                compUnit_ = parser.parse().<IRCompUnit> value();
            }
            catch (RuntimeException e) {
                throw e;
            }
            catch (Exception e) {
                // Used by CUP to indicate an unrecoverable error.
                String msg = e.getMessage();
                if (msg != null) System.err.println("Syntax error: " + msg);
            }

            if (compUnit_ != null) {
                IRSimulator sim = new IRSimulator(compUnit_);
                long result = sim.call("b", 2, 1);
                System.out.println("b(2,1) == " + result);
            }
        }


        }














        // Runs a simple program in the interpreter

        // IR roughly corresponds to the following:
        //     a(i:int, j:int): int, int {
        //         return i, (2 * j);
        //     }
        //     b(i:int, j:int): int {
        //         x:int, y:int = a(i, j);
        //         return x + 5 * y;
        //     }
        {
        String arg0 = Configuration.ABSTRACT_ARG_PREFIX + 0;
        String arg1 = Configuration.ABSTRACT_ARG_PREFIX + 1;
        String ret0 = Configuration.ABSTRACT_RET_PREFIX + 0;
        String ret1 = Configuration.ABSTRACT_RET_PREFIX + 1;

        IRStmt aBody = make
                .IRSeq(make.IRMove(make.IRTemp("i"), make.IRTemp(arg0)),
                        make.IRMove(make.IRTemp("j"), make.IRTemp(arg1)),
                        make.IRMove(make.IRTemp(ret0), make.IRTemp("i")),
                        make.IRMove(make.IRTemp(ret1), make.IRBinOp(OpType.MUL,
                                make.IRConst(2), make.IRTemp("j"))),
                        make.IRReturn());
        IRFuncDecl aFunc = make.IRFuncDecl("a", aBody);

        IRStmt bBody =
                    make.IRSeq(
                            make.IRCallStmt(Arrays.asList("x", "y"),
                                    make.IRName("a"),
                                    List.of(make.IRTemp(arg0),
                                            make.IRTemp(arg1))),

                        make.IRMove(make.IRTemp(ret0), make.IRBinOp(OpType.ADD,
                                make.IRTemp("x"), make.IRBinOp(OpType.MUL,
                                        make.IRConst(5), make.IRTemp("y")))),
                        make.IRReturn());

        IRFuncDecl bFunc = make.IRFuncDecl("b", bBody);

        IRCompUnit compUnit = make.IRCompUnit("test");
        compUnit.appendFunc(aFunc);
        compUnit.appendFunc(bFunc);

        // IR pretty-printer demo
        System.out.println("Code:");
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw);
             SExpPrinter sp = new CodeWriterSExpPrinter(pw)) {
            compUnit.printSExp(sp);
        }
        System.out.println(sw);

        // IR interpreter demo
        {
                IRSimulator sim = new IRSimulator(compUnit);
            long result = sim.call("b", 2, 1);
            System.out.println("b(2,1) == " + result);
        }

        // IR canonical checker demo
        {
            CheckCanonicalIRVisitor cv = new CheckCanonicalIRVisitor();
            System.out.print("Canonical?: ");
            System.out.println(cv.visit(compUnit));
        }

        // IR constant-folding checker demo
        {
            CheckConstFoldedIRVisitor cv = new CheckConstFoldedIRVisitor();
            System.out.print("Constant-folded?: ");
            System.out.println(cv.visit(compUnit));
        }

        // IR parser demo
        String prog = sw.toString();
        try (StringReader r = new StringReader(prog)) {
                IRParser parser = new IRParser(new IRLexer(r), make);
            IRCompUnit compUnit_ = null;
            try {
                compUnit_ = parser.parse().<IRCompUnit> value();
            }
            catch (RuntimeException e) {
                throw e;
            }
            catch (Exception e) {
                // Used by CUP to indicate an unrecoverable error.
                String msg = e.getMessage();
                if (msg != null) System.err.println("Syntax error: " + msg);
            }

            if (compUnit_ != null) {
                IRSimulator sim = new IRSimulator(compUnit_);
                long result = sim.call("b", 2, 1);
                System.out.println("b(2,1) == " + result);
            }
        }

        }


    }
}
