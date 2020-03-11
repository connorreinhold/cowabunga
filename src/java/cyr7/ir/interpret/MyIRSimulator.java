package cyr7.ir.interpret;

import cyr7.ir.nodes.IRBinOp;
import cyr7.ir.nodes.IRCJump;
import cyr7.ir.nodes.IRCall;
import cyr7.ir.nodes.IRCallStmt;
import cyr7.ir.nodes.IRCompUnit;
import cyr7.ir.nodes.IRConst;
import cyr7.ir.nodes.IRExp;
import cyr7.ir.nodes.IRExpr;
import cyr7.ir.nodes.IRFuncDecl;
import cyr7.ir.nodes.IRJump;
import cyr7.ir.nodes.IRMem;
import cyr7.ir.nodes.IRMove;
import cyr7.ir.nodes.IRName;
import cyr7.ir.nodes.IRNode;
import cyr7.ir.nodes.IRNodeFactory;
import cyr7.ir.nodes.IRNodeFactory_c;
import cyr7.ir.nodes.IRReturn;
import cyr7.ir.nodes.IRTemp;
import edu.cornell.cs.cs4120.util.InternalCompilerError;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MyIRSimulator extends IRSimulator {

    public static final int DEFAULT_HEAP_SIZE = IRSimulator.DEFAULT_HEAP_SIZE;
    public static final int BIG_HEAP_SIZE = 64 * IRSimulator.DEFAULT_HEAP_SIZE;

    public MyIRSimulator(IRCompUnit compUnit) {
        super(compUnit);
    }

    public MyIRSimulator(IRCompUnit compUnit, PrintStream stdout) {
        super(compUnit, stdout);
    }

    public MyIRSimulator(IRCompUnit compUnit, int heapSize, PrintStream stdout) {
        super(compUnit, heapSize, stdout);
    }

    @Override
    protected void interpret(ExecutionFrame frame, IRNode insn) {
        /*
         * Why is this method duplicated from IRSimulator?
         *
         * If we were to use a call to super.interpret that would double the
         * size of the Java call stack, causing a stack overflow for moderately
         * sized programs.
         *
         * The only difference between this method and the one in IRSimulator
         * is that this method handles IRCallStmt differently to support the
         * Spring 2020 version of the Canonical IR.
         */
        if (insn instanceof IRConst)
            exprStack.pushValue(((IRConst) insn).value());
        else if (insn instanceof IRTemp) {
            String tempName = ((IRTemp) insn).name();
            exprStack.pushTemp(frame.get(tempName), tempName);
        }
        else if (insn instanceof IRBinOp) {
            long r = exprStack.popValue();
            long l = exprStack.popValue();
            long result;
            switch (((IRBinOp) insn).opType()) {
                case ADD:
                    result = l + r;
                    break;
                case SUB:
                    result = l - r;
                    break;
                case MUL:
                    result = l * r;
                    break;
                case HMUL:
                    result = BigInteger.valueOf(l)
                        .multiply(BigInteger.valueOf(r))
                        .shiftRight(64)
                        .longValue();
                    break;
                case DIV:
                    if (r == 0) throw new Trap("Division by zero!");
                    result = l / r;
                    break;
                case MOD:
                    if (r == 0) throw new Trap("Division by zero!");
                    result = l % r;
                    break;
                case AND:
                    result = l & r;
                    break;
                case OR:
                    result = l | r;
                    break;
                case XOR:
                    result = l ^ r;
                    break;
                case LSHIFT:
                    result = l << r;
                    break;
                case RSHIFT:
                    result = l >>> r;
                    break;
                case ARSHIFT:
                    result = l >> r;
                    break;
                case EQ:
                    result = l == r ? 1 : 0;
                    break;
                case NEQ:
                    result = l != r ? 1 : 0;
                    break;
                case LT:
                    result = l < r ? 1 : 0;
                    break;
                case GT:
                    result = l > r ? 1 : 0;
                    break;
                case LEQ:
                    result = l <= r ? 1 : 0;
                    break;
                case GEQ:
                    result = l >= r ? 1 : 0;
                    break;
                default:
                    throw new InternalCompilerError("Invalid binary operation");
            }
            exprStack.pushValue(result);
        }
        else if (insn instanceof IRMem) {
            long addr = exprStack.popValue();
            exprStack.pushAddr(read(addr), addr);
        }
        else if (insn instanceof IRCall) {
            int argsCount = ((IRCall) insn).args().size();
            long args[] = new long[argsCount];
            for (int i = argsCount - 1; i >= 0; --i)
                args[i] = exprStack.popValue();
            if (debugLevel > 2) {
                System.out.println("Arguments: " + Arrays.toString(args));
            }
            StackItem target = exprStack.pop();
            String targetName;
            if (target.type == StackItem.Kind.NAME)
                targetName = target.name;
            else if (indexToInsn.containsKey(target.value)) {
                IRNode node = indexToInsn.get(target.value);
                if (node instanceof IRFuncDecl)
                    targetName = ((IRFuncDecl) node).name();
                else throw new InternalCompilerError("Call to a non-function instruction!");
            }
            else throw new InternalCompilerError("Invalid function call '"
                    + insn + "' (target '" + target.value + "' is unknown)!");

            long retVal = call(frame, targetName, args);
            if (debugLevel > 2) {
                System.err.println("Return value: " + retVal);
            }
            exprStack.pushValue(retVal);
        }
        else if (insn instanceof IRName) {
            String name = ((IRName) insn).name();
            exprStack.pushName(libraryFunctions.contains(name)
                ? -1 : findLabel(name), name);
        }
        else if (insn instanceof IRMove) {
            long r = exprStack.popValue();
            StackItem stackItem = exprStack.pop();
            switch (stackItem.type) {
                case MEM:
                    if (debugLevel > 0)
                        System.out.println("mem[" + stackItem.addr + "]=" + r);
                    store(stackItem.addr, r);
                    break;
                case TEMP:
                    if (debugLevel > 0)
                        System.out.println("temp[" + stackItem.temp + "]=" + r);
                    frame.put(stackItem.temp, r);
                    break;
                default:
                    throw new InternalCompilerError("Invalid MOVE!");
            }
        }
        else if (insn instanceof IRCallStmt) {
            IRNodeFactory make = new IRNodeFactory_c(insn.location());

            // Get arguments (all args, let's hope this works)
            List<IRExpr> args = new ArrayList<>();
            for (int i = 0;
                 frame.regs().containsKey(Configuration.ABSTRACT_ARG_PREFIX + i);
                 i++) {

                IRTemp arg = make.IRTemp(Configuration.ABSTRACT_ARG_PREFIX + i);
                args.add(arg);
                interpret(frame, arg);
            }

            // Make the call
            IRCallStmt callStmt = (IRCallStmt) insn;
            IRCall syntheticCall = make.IRCall(callStmt.target(), args);
            interpret(frame, syntheticCall);

            // Remove the return value that was pushed onto the stack by IRCall
            exprStack.popValue();
        } else if (insn instanceof IRExp) {
            // Discard result.
            exprStack.pop();
        }
        else if (insn instanceof IRJump)
            frame.setIP(exprStack.popValue());
        else if (insn instanceof IRCJump) {
            IRCJump irCJump = (IRCJump) insn;
            long top = exprStack.popValue();
            String label;
            if (top == 0)
                label = irCJump.falseLabel();
            else if (top == 1)
                label = irCJump.trueLabel();
            else throw new InternalCompilerError("Invalid value in CJUMP - expected 0/1, got "
                    + top);
            if (label != null) frame.setIP(findLabel(label));
        }
        else if (insn instanceof IRReturn) {
            frame.setIP(-1);
        }
    }

}
