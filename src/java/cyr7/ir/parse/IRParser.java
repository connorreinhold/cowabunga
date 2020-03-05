

package cyr7.ir.parse;

import cyr7.ir.nodes.*;
import java_cup.runtime.ComplexSymbolFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/** CUP v0.11b 20150326 generated parser.
  */
public class IRParser
 extends java_cup.runtime.lr_parser {

  @Override
  public final Class<?> getSymbolContainer() {
    return IRSym.class;
  }

  /** Default constructor. */
  @Deprecated
  public IRParser() {super();}

  /** Constructor which sets the default scanner. */
  @Deprecated
  public IRParser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner and a SymbolFactory. */
  public IRParser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\067\000\002\002\004\000\002\002\007\000\002\003" +
    "\003\000\002\003\004\000\002\004\007\000\002\014\003" +
    "\000\002\010\005\000\002\011\005\000\002\011\006\000" +
    "\002\011\004\000\002\011\004\000\002\011\004\000\002" +
    "\011\006\000\002\011\005\000\002\011\004\000\002\011" +
    "\003\000\002\020\006\000\002\020\006\000\002\006\002" +
    "\000\002\006\003\000\002\007\003\000\002\007\004\000" +
    "\002\015\005\000\002\016\004\000\002\016\004\000\002" +
    "\016\003\000\002\016\004\000\002\016\005\000\002\016" +
    "\004\000\002\016\005\000\002\021\003\000\002\017\005" +
    "\000\002\017\005\000\002\017\005\000\002\017\005\000" +
    "\002\017\005\000\002\017\005\000\002\017\005\000\002" +
    "\017\005\000\002\017\005\000\002\017\005\000\002\017" +
    "\005\000\002\017\005\000\002\017\005\000\002\017\005" +
    "\000\002\017\005\000\002\017\005\000\002\017\005\000" +
    "\002\017\005\000\002\005\003\000\002\005\004\000\002" +
    "\012\002\000\002\012\003\000\002\013\003\000\002\013" +
    "\004" });

  /** Access to production table. */
  @Override
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\177\000\004\004\004\001\002\000\004\047\007\001" +
    "\002\000\004\002\006\001\002\000\004\002\001\001\002" +
    "\000\004\050\011\001\002\000\004\004\013\001\002\000" +
    "\010\004\ufffc\005\ufffc\050\ufffc\001\002\000\006\004\uffff" +
    "\005\uffff\001\002\000\004\046\017\001\002\000\006\004" +
    "\013\005\016\001\002\000\006\004\ufffe\005\ufffe\001\002" +
    "\000\004\002\000\001\002\000\004\050\011\001\002\000" +
    "\004\004\021\001\002\000\022\006\027\007\030\010\032" +
    "\011\034\012\033\013\026\014\031\015\025\001\002\000" +
    "\004\005\023\001\002\000\006\004\ufffd\005\ufffd\001\002" +
    "\000\004\005\201\001\002\000\004\005\ufff2\001\002\000" +
    "\004\004\040\001\002\000\004\004\165\001\002\000\006" +
    "\004\uffef\050\011\001\002\000\004\050\011\001\002\000" +
    "\004\004\040\001\002\000\004\004\040\001\002\000\004" +
    "\004\021\001\002\000\006\004\021\005\ufff7\001\002\000" +
    "\006\004\uffd0\005\uffd0\001\002\000\006\004\uffcf\005\uffcf" +
    "\001\002\000\062\016\046\017\063\020\064\021\042\022" +
    "\057\023\070\024\043\025\050\026\053\027\073\030\061" +
    "\031\051\032\066\033\052\034\071\035\065\036\060\037" +
    "\044\040\055\041\072\042\045\043\056\044\067\045\062" +
    "\001\002\000\004\005\ufff6\001\002\000\004\004\040\001" +
    "\002\000\004\004\040\001\002\000\004\004\040\001\002" +
    "\000\004\004\040\001\002\000\004\051\140\001\002\000" +
    "\004\005\uffe8\001\002\000\004\004\040\001\002\000\004" +
    "\004\040\001\002\000\004\004\040\001\002\000\004\004" +
    "\040\001\002\000\004\005\127\001\002\000\004\004\040" +
    "\001\002\000\004\004\040\001\002\000\004\050\011\001" +
    "\002\000\004\004\040\001\002\000\004\004\040\001\002" +
    "\000\004\004\040\001\002\000\004\050\011\001\002\000" +
    "\004\004\040\001\002\000\004\004\040\001\002\000\004" +
    "\004\040\001\002\000\004\004\040\001\002\000\004\004" +
    "\021\001\002\000\004\004\040\001\002\000\004\004\040" +
    "\001\002\000\004\004\040\001\002\000\004\004\040\001" +
    "\002\000\004\005\uffdf\001\002\000\004\004\040\001\002" +
    "\000\004\005\uffd5\001\002\000\004\004\040\001\002\000" +
    "\004\005\uffda\001\002\000\004\004\040\001\002\000\004" +
    "\005\uffe4\001\002\000\004\004\040\001\002\000\004\005" +
    "\uffd2\001\002\000\004\004\040\001\002\000\004\005\uffdc" +
    "\001\002\000\004\004\040\001\002\000\004\005\uffd9\001" +
    "\002\000\004\005\uffe7\001\002\000\004\005\uffe9\001\002" +
    "\000\004\004\040\001\002\000\004\005\uffd1\001\002\000" +
    "\004\004\040\001\002\000\004\005\uffde\001\002\000\004" +
    "\004\040\001\002\000\004\005\uffd8\001\002\000\004\005" +
    "\uffe5\001\002\000\004\004\040\001\002\000\004\005\uffd3" +
    "\001\002\000\004\004\040\001\002\000\004\005\uffd6\001" +
    "\002\000\010\004\uffeb\005\uffeb\050\uffeb\001\002\000\004" +
    "\004\040\001\002\000\004\005\uffe0\001\002\000\004\004" +
    "\040\001\002\000\004\005\uffdb\001\002\000\004\004\040" +
    "\001\002\000\004\005\uffdd\001\002\000\004\004\040\001" +
    "\002\000\004\005\uffe1\001\002\000\004\005\uffe3\001\002" +
    "\000\004\005\uffea\001\002\000\004\004\040\001\002\000" +
    "\004\005\uffd4\001\002\000\004\004\040\001\002\000\004" +
    "\005\uffd7\001\002\000\004\004\040\001\002\000\004\005" +
    "\uffe2\001\002\000\006\004\040\005\uffce\001\002\000\004" +
    "\005\uffe6\001\002\000\006\004\040\005\uffcd\001\002\000" +
    "\006\004\uffcc\005\uffcc\001\002\000\006\004\uffcb\005\uffcb" +
    "\001\002\000\004\005\ufff8\001\002\000\004\005\ufff3\001" +
    "\002\000\004\004\040\001\002\000\006\004\uffee\050\011" +
    "\001\002\000\006\004\uffed\050\uffed\001\002\000\006\004" +
    "\uffec\050\uffec\001\002\000\006\004\040\005\uffce\001\002" +
    "\000\004\005\ufff9\001\002\000\006\017\170\020\171\001" +
    "\002\000\004\004\040\001\002\000\004\005\ufffa\001\002" +
    "\000\004\050\011\001\002\000\004\004\040\001\002\000" +
    "\004\005\173\001\002\000\004\004\ufff0\001\002\000\004" +
    "\005\175\001\002\000\004\004\ufff1\001\002\000\004\050" +
    "\011\001\002\000\006\005\ufff4\050\011\001\002\000\004" +
    "\005\ufff5\001\002\000\006\004\ufffb\005\ufffb\001\002" });

  /** Access to parse-action table. */
  @Override
  public short[][] action_table() {return _action_table;}

  /** {@code reduce_goto} table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\177\000\004\002\004\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\004\014\007\001\001" +
    "\000\006\003\013\004\011\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\004\004\014\001\001" +
    "\000\002\001\001\000\002\001\001\000\004\014\017\001" +
    "\001\000\004\010\021\001\001\000\004\011\023\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\004\015\175\001\001\000\004\020\165" +
    "\001\001\000\010\006\156\007\157\014\160\001\001\000" +
    "\004\014\155\001\001\000\004\015\154\001\001\000\004" +
    "\015\040\001\001\000\006\005\034\010\035\001\001\000" +
    "\004\010\036\001\001\000\002\001\001\000\002\001\001" +
    "\000\006\016\053\017\046\001\001\000\002\001\001\000" +
    "\004\015\147\001\001\000\004\015\145\001\001\000\004" +
    "\015\143\001\001\000\004\015\141\001\001\000\004\021" +
    "\140\001\001\000\002\001\001\000\004\015\135\001\001" +
    "\000\004\015\133\001\001\000\004\015\131\001\001\000" +
    "\004\015\127\001\001\000\002\001\001\000\004\015\124" +
    "\001\001\000\004\015\122\001\001\000\004\014\121\001" +
    "\001\000\004\015\117\001\001\000\004\015\115\001\001" +
    "\000\004\015\113\001\001\000\004\014\112\001\001\000" +
    "\004\015\111\001\001\000\004\015\107\001\001\000\004" +
    "\015\105\001\001\000\004\015\103\001\001\000\004\010" +
    "\101\001\001\000\004\015\077\001\001\000\004\015\075" +
    "\001\001\000\004\015\073\001\001\000\004\015\074\001" +
    "\001\000\002\001\001\000\004\015\076\001\001\000\002" +
    "\001\001\000\004\015\100\001\001\000\002\001\001\000" +
    "\004\015\102\001\001\000\002\001\001\000\004\015\104" +
    "\001\001\000\002\001\001\000\004\015\106\001\001\000" +
    "\002\001\001\000\004\015\110\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\004\015\114\001" +
    "\001\000\002\001\001\000\004\015\116\001\001\000\002" +
    "\001\001\000\004\015\120\001\001\000\002\001\001\000" +
    "\002\001\001\000\004\015\123\001\001\000\002\001\001" +
    "\000\004\015\125\001\001\000\002\001\001\000\002\001" +
    "\001\000\004\015\130\001\001\000\002\001\001\000\004" +
    "\015\132\001\001\000\002\001\001\000\004\015\134\001" +
    "\001\000\002\001\001\000\004\015\136\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\004\015" +
    "\142\001\001\000\002\001\001\000\004\015\144\001\001" +
    "\000\002\001\001\000\004\015\146\001\001\000\002\001" +
    "\001\000\010\012\150\013\151\015\152\001\001\000\002" +
    "\001\001\000\004\015\153\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\004" +
    "\015\162\001\001\000\004\014\161\001\001\000\002\001" +
    "\001\000\002\001\001\000\010\012\163\013\151\015\152" +
    "\001\001\000\002\001\001\000\002\001\001\000\004\015" +
    "\166\001\001\000\002\001\001\000\004\014\173\001\001" +
    "\000\004\015\171\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\004\014\176" +
    "\001\001\000\004\014\177\001\001\000\002\001\001\000" +
    "\002\001\001" });

  /** Access to {@code reduce_goto} table. */
  @Override
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$IRParser$actions action_obj;

  /** Action encapsulation object initializer. */
  @Override
  protected void init_actions()
    {
      action_obj = new CUP$IRParser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  @Override
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack<java_cup.runtime.Symbol> stack,
    int                        top)
    throws Exception
  {
    /* call code in generated class */
    return action_obj.CUP$IRParser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  @Override
  public int start_state() {return 0;}
  /** Indicates start production. */
  @Override
  public int start_production() {return 0;}

  /** {@code EOF} Symbol index. */
  @Override
  public int EOF_sym() {return 0;}

  /** {@code error} Symbol index. */
  @Override
  public int error_sym() {return 1;}




    protected IRNodeFactory nf;

    public IRParser(IRLexer lexer, IRNodeFactory nf) {
        super(lexer, new ComplexSymbolFactory());
        this.nf = nf;
    }


/** Cup generated class to encapsulate user supplied action code.*/
class CUP$IRParser$actions {
    private final IRParser parser;

    /** Constructor */
    CUP$IRParser$actions(IRParser parser) {
        this.parser = parser;
    }

    /** Method with the actual generated action code for actions 0 to 54. */
    public final java_cup.runtime.Symbol CUP$IRParser$do_action_part00000000(
            int                        CUP$IRParser$act_num,
            java_cup.runtime.lr_parser CUP$IRParser$parser,
            java.util.Stack<java_cup.runtime.Symbol> CUP$IRParser$stack,
            int                        CUP$IRParser$top)
            throws Exception {
            /* Symbol object for return from actions */
            java_cup.runtime.Symbol CUP$IRParser$result;

        /* select the action based on the action number */
        switch (CUP$IRParser$act_num) {
        /*. . . . . . . . . . . . . . . . . . . .*/
        case 0: // $START ::= compunit EOF 
            {
                Object RESULT = null;
                IRCompUnit start_val = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRCompUnit> value();
                RESULT = start_val;
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("$START",0, RESULT);
            }
            /* ACCEPT */
            CUP$IRParser$parser.done_parsing();
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 1: // compunit ::= LPAREN COMPUNIT name funcdecls RPAREN 
            {
                IRCompUnit RESULT = null;
                String n = CUP$IRParser$stack.elementAt(CUP$IRParser$top-2).<String> value();
                List<IRFuncDecl> f = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<List<IRFuncDecl>> value();
                
        RESULT = parser.nf.IRCompUnit(n);
        for (IRFuncDecl fd : f)
             RESULT.appendFunc(fd);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("compunit",0, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 2: // funcdecls ::= funcdecl 
            {
                List<IRFuncDecl> RESULT = null;
                IRFuncDecl fd = CUP$IRParser$stack.peek().<IRFuncDecl> value();
                
        RESULT = new ArrayList<>();
        RESULT.add(fd);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("funcdecls",1, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 3: // funcdecls ::= funcdecls funcdecl 
            {
                List<IRFuncDecl> RESULT = null;
                List<IRFuncDecl> f = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<List<IRFuncDecl>> value();
                IRFuncDecl fd = CUP$IRParser$stack.peek().<IRFuncDecl> value();
                
        RESULT = f;
        RESULT.add(fd);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("funcdecls",1, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 4: // funcdecl ::= LPAREN FUNC name stmt RPAREN 
            {
                IRFuncDecl RESULT = null;
                String n = CUP$IRParser$stack.elementAt(CUP$IRParser$top-2).<String> value();
                IRStmt s = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRStmt> value();
                
        RESULT = parser.nf.IRFuncDecl(n, s);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("funcdecl",2, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 5: // name ::= ATOM 
            {
                String RESULT = null;
                String a = CUP$IRParser$stack.peek().<String> value();
                
        RESULT = a;
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("name",10, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 6: // stmt ::= LPAREN bare_stmt RPAREN 
            {
                IRStmt RESULT = null;
                IRStmt s = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRStmt> value();
                
        RESULT = s;
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("stmt",6, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 7: // bare_stmt ::= MOVE dest expr 
            {
                IRStmt RESULT = null;
                IRExpr dest = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                IRExpr e = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRMove(dest, e);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("bare_stmt",7, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 8: // bare_stmt ::= CALL_STMT collector_list_opt expr exprs_opt 
            {
                IRStmt RESULT = null;
                List<String> collectors = CUP$IRParser$stack.elementAt(CUP$IRParser$top-2).<List<String>> value();
                IRExpr target = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                List<IRExpr> args = CUP$IRParser$stack.peek().<List<IRExpr>> value();
                
        RESULT = parser.nf.IRCallStmt(collectors, target, args);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("bare_stmt",7, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 9: // bare_stmt ::= EXP expr 
            {
                IRStmt RESULT = null;
                IRExpr e = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRExp(e);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("bare_stmt",7, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 10: // bare_stmt ::= SEQ stmts 
            {
                IRStmt RESULT = null;
                List<IRStmt> l = CUP$IRParser$stack.peek().<List<IRStmt>> value();
                
        RESULT = parser.nf.IRSeq(l);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("bare_stmt",7, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 11: // bare_stmt ::= JUMP expr 
            {
                IRStmt RESULT = null;
                IRExpr e = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRJump(e);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("bare_stmt",7, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 12: // bare_stmt ::= CJUMP expr name name 
            {
                IRStmt RESULT = null;
                IRExpr e = CUP$IRParser$stack.elementAt(CUP$IRParser$top-2).<IRExpr> value();
                String trueLabel = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<String> value();
                String falseLabel = CUP$IRParser$stack.peek().<String> value();
                
        RESULT = parser.nf.IRCJump(e, trueLabel, falseLabel);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("bare_stmt",7, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 13: // bare_stmt ::= CJUMP expr name 
            {
                IRStmt RESULT = null;
                IRExpr e = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                String trueLabel = CUP$IRParser$stack.peek().<String> value();
                
        RESULT = parser.nf.IRCJump(e, trueLabel);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("bare_stmt",7, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 14: // bare_stmt ::= LABEL name 
            {
                IRStmt RESULT = null;
                String n = CUP$IRParser$stack.peek().<String> value();
                
        RESULT = parser.nf.IRLabel(n);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("bare_stmt",7, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 15: // bare_stmt ::= RETURN 
            {
                IRStmt RESULT = null;
                
        RESULT = parser.nf.IRReturn();
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("bare_stmt",7, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 16: // dest ::= LPAREN TEMP name RPAREN 
            {
                IRExpr RESULT = null;
                String n = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<String> value();
                
        RESULT = parser.nf.IRTemp(n);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("dest",14, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 17: // dest ::= LPAREN MEM expr RPAREN 
            {
                IRExpr RESULT = null;
                IRExpr e = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                
        RESULT = parser.nf.IRMem(e);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("dest",14, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 18: // collector_list_opt ::= 
            {
                List<String> RESULT = null;
                
        RESULT = Collections.emptyList();
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("collector_list_opt",4, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 19: // collector_list_opt ::= collector_list 
            {
                List<String> RESULT = null;
                List<String> l = CUP$IRParser$stack.peek().<List<String>> value();
                
        RESULT = l;
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("collector_list_opt",4, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 20: // collector_list ::= name 
            {
                List<String> RESULT = null;
                String n = CUP$IRParser$stack.peek().<String> value();
                
        RESULT = new LinkedList<>();
        RESULT.add(n);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("collector_list",5, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 21: // collector_list ::= collector_list name 
            {
                List<String> RESULT = null;
                List<String> l = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<List<String>> value();
                String n = CUP$IRParser$stack.peek().<String> value();
                
        RESULT = l;
        RESULT.add(n);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("collector_list",5, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 22: // expr ::= LPAREN bare_expr RPAREN 
            {
                IRExpr RESULT = null;
                IRExpr e = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                
        RESULT = e;
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("expr",11, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 23: // bare_expr ::= CONST num 
            {
                IRExpr RESULT = null;
                Long n = CUP$IRParser$stack.peek().<Long> value();
                
        RESULT = parser.nf.IRConst(n);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("bare_expr",12, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 24: // bare_expr ::= TEMP name 
            {
                IRExpr RESULT = null;
                String n = CUP$IRParser$stack.peek().<String> value();
                
        RESULT = parser.nf.IRTemp(n);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("bare_expr",12, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 25: // bare_expr ::= op 
            {
                IRExpr RESULT = null;
                IRExpr o = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = o;
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("bare_expr",12, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 26: // bare_expr ::= MEM expr 
            {
                IRExpr RESULT = null;
                IRExpr e = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRMem(e);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("bare_expr",12, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 27: // bare_expr ::= CALL expr exprs_opt 
            {
                IRExpr RESULT = null;
                IRExpr target = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                List<IRExpr> args = CUP$IRParser$stack.peek().<List<IRExpr>> value();
                
        RESULT = parser.nf.IRCall(target, args);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("bare_expr",12, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 28: // bare_expr ::= NAME name 
            {
                IRExpr RESULT = null;
                String n = CUP$IRParser$stack.peek().<String> value();
                
        RESULT = parser.nf.IRName(n);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("bare_expr",12, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 29: // bare_expr ::= ESEQ stmt expr 
            {
                IRExpr RESULT = null;
                IRStmt s = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRStmt> value();
                IRExpr e = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRESeq(s, e);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("bare_expr",12, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 30: // num ::= NUMBER 
            {
                Long RESULT = null;
                Long n = CUP$IRParser$stack.peek().<Long> value();
                
        RESULT = n;
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("num",15, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 31: // op ::= ADD expr expr 
            {
                IRExpr RESULT = null;
                IRExpr x = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                IRExpr y = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRBinOp(IRBinOp.OpType.ADD, x,y);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("op",13, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 32: // op ::= SUB expr expr 
            {
                IRExpr RESULT = null;
                IRExpr x = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                IRExpr y = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRBinOp(IRBinOp.OpType.SUB, x,y);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("op",13, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 33: // op ::= MUL expr expr 
            {
                IRExpr RESULT = null;
                IRExpr x = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                IRExpr y = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRBinOp(IRBinOp.OpType.MUL, x,y);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("op",13, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 34: // op ::= HMUL expr expr 
            {
                IRExpr RESULT = null;
                IRExpr x = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                IRExpr y = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRBinOp(IRBinOp.OpType.HMUL, x,y);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("op",13, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 35: // op ::= DIV expr expr 
            {
                IRExpr RESULT = null;
                IRExpr x = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                IRExpr y = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRBinOp(IRBinOp.OpType.DIV, x,y);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("op",13, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 36: // op ::= MOD expr expr 
            {
                IRExpr RESULT = null;
                IRExpr x = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                IRExpr y = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRBinOp(IRBinOp.OpType.MOD, x,y);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("op",13, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 37: // op ::= AND expr expr 
            {
                IRExpr RESULT = null;
                IRExpr x = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                IRExpr y = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRBinOp(IRBinOp.OpType.AND, x,y);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("op",13, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 38: // op ::= OR expr expr 
            {
                IRExpr RESULT = null;
                IRExpr x = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                IRExpr y = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRBinOp(IRBinOp.OpType.OR, x,y);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("op",13, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 39: // op ::= XOR expr expr 
            {
                IRExpr RESULT = null;
                IRExpr x = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                IRExpr y = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRBinOp(IRBinOp.OpType.XOR, x,y);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("op",13, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 40: // op ::= LSHIFT expr expr 
            {
                IRExpr RESULT = null;
                IRExpr x = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                IRExpr y = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRBinOp(IRBinOp.OpType.LSHIFT, x,y);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("op",13, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 41: // op ::= RSHIFT expr expr 
            {
                IRExpr RESULT = null;
                IRExpr x = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                IRExpr y = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRBinOp(IRBinOp.OpType.RSHIFT, x,y);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("op",13, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 42: // op ::= ARSHIFT expr expr 
            {
                IRExpr RESULT = null;
                IRExpr x = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                IRExpr y = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRBinOp(IRBinOp.OpType.ARSHIFT, x,y);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("op",13, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 43: // op ::= EQ expr expr 
            {
                IRExpr RESULT = null;
                IRExpr x = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                IRExpr y = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRBinOp(IRBinOp.OpType.EQ, x,y);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("op",13, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 44: // op ::= NEQ expr expr 
            {
                IRExpr RESULT = null;
                IRExpr x = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                IRExpr y = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRBinOp(IRBinOp.OpType.NEQ, x,y);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("op",13, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 45: // op ::= LT expr expr 
            {
                IRExpr RESULT = null;
                IRExpr x = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                IRExpr y = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRBinOp(IRBinOp.OpType.LT, x,y);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("op",13, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 46: // op ::= GT expr expr 
            {
                IRExpr RESULT = null;
                IRExpr x = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                IRExpr y = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRBinOp(IRBinOp.OpType.GT, x,y);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("op",13, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 47: // op ::= LEQ expr expr 
            {
                IRExpr RESULT = null;
                IRExpr x = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                IRExpr y = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRBinOp(IRBinOp.OpType.LEQ, x,y);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("op",13, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 48: // op ::= GEQ expr expr 
            {
                IRExpr RESULT = null;
                IRExpr x = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<IRExpr> value();
                IRExpr y = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = parser.nf.IRBinOp(IRBinOp.OpType.GEQ, x,y);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("op",13, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 49: // stmts ::= stmt 
            {
                List<IRStmt> RESULT = null;
                IRStmt s = CUP$IRParser$stack.peek().<IRStmt> value();
                
        RESULT = new LinkedList<>();
        RESULT.add(s);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("stmts",3, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 50: // stmts ::= stmts stmt 
            {
                List<IRStmt> RESULT = null;
                List<IRStmt> l = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<List<IRStmt>> value();
                IRStmt s = CUP$IRParser$stack.peek().<IRStmt> value();
                
        RESULT = l;
        RESULT.add(s);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("stmts",3, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 51: // exprs_opt ::= 
            {
                List<IRExpr> RESULT = null;
                
        RESULT = Collections.emptyList();
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("exprs_opt",8, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 52: // exprs_opt ::= exprs 
            {
                List<IRExpr> RESULT = null;
                List<IRExpr> l = CUP$IRParser$stack.peek().<List<IRExpr>> value();
                
        RESULT = l;
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("exprs_opt",8, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 53: // exprs ::= expr 
            {
                List<IRExpr> RESULT = null;
                IRExpr e = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = new LinkedList<>();
        RESULT.add(e);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("exprs",9, RESULT);
            }
            return CUP$IRParser$result;

        /*. . . . . . . . . . . . . . . . . . . .*/
        case 54: // exprs ::= exprs expr 
            {
                List<IRExpr> RESULT = null;
                List<IRExpr> l = CUP$IRParser$stack.elementAt(CUP$IRParser$top-1).<List<IRExpr>> value();
                IRExpr e = CUP$IRParser$stack.peek().<IRExpr> value();
                
        RESULT = l;
        RESULT.add(e);
    
                CUP$IRParser$result = parser.getSymbolFactory().newSymbol("exprs",9, RESULT);
            }
            return CUP$IRParser$result;

        /* . . . . . .*/
        default:
            throw new Exception(
                  "Invalid action number " + CUP$IRParser$act_num + " found in internal parse table");

        }
    } /* end of method */

    /** Method splitting the generated action code into several parts. */
    public final java_cup.runtime.Symbol CUP$IRParser$do_action(
            int                        CUP$IRParser$act_num,
            java_cup.runtime.lr_parser CUP$IRParser$parser,
            java.util.Stack<java_cup.runtime.Symbol> CUP$IRParser$stack,
            int                        CUP$IRParser$top)
            throws Exception {
            return CUP$IRParser$do_action_part00000000(
                           CUP$IRParser$act_num,
                           CUP$IRParser$parser,
                           CUP$IRParser$stack,
                           CUP$IRParser$top);
    }
}

}
