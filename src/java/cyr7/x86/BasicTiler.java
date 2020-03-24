package cyr7.x86;

import cyr7.ir.nodes.*;
import cyr7.visitor.MyIRVisitor;
import cyr7.x86.asm.ASMArg;
import cyr7.x86.asm.ASMFactory;
import cyr7.x86.asm.ASMInstr;
import cyr7.x86.asm.ASMTempArg;
import cyr7.x86.TilerData;
import cyr7.ir.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BasicTiler implements MyIRVisitor<TilerData> {
    
    private final IdGenerator generator;
    
    public BasicTiler(IdGenerator generator) {
        this.generator = generator;
    }
    
    @Override
    public TilerData visit(IRBinOp n) {
        if(n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        List<ASMInstr> insns = new ArrayList<ASMInstr>();
        ASMArg ret = new ASMTempArg(generator.newTemp());
        
        TilerData left = n.left().accept(this);
        TilerData right = n.right().accept(this);
        
        insns.addAll(left.optimalInstructions);
        insns.addAll(right.optimalInstructions);
        
        insns.add(ASMFactory.Mov(ret, left.result.get()));
        
        switch(n.opType()) {
        case ADD:
            insns.add(ASMFactory.Add(ret, right.result.get()));
            break;
        case AND:
            insns.add(ASMFactory.And(ret, right.result.get()));
            break;
        case ARSHIFT:
            insns.add(ASMFactory.(ret, right.result.get()));
            break;
        case DIV:
            break;
        case EQ:
            break;
        case GEQ:
            break;
        case GT:
            break;
        case HMUL:
            break;
        case LEQ:
            break;
        case LSHIFT:
            break;
        case LT:
            break;
        case MOD:
            break;
        case MUL:
            break;
        case NEQ:
            break;
        case OR:
            break;
        case RSHIFT:
            break;
        case SUB:
            break;
        case XOR:
            break;
        default:
            break;
            
        }
        
        TilerData result = new TilerData(1 + left.tileCost + right.tileCost, insns, Optional.of(ret));
        n.setOptimalTilingOnce(result);
        return result;
    }

    @Override
    public TilerData visit(IRCall n) {
        if(n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return null;
    }

    @Override
    public TilerData visit(IRConst n) {
        if(n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        
        return null;
    }

    @Override
    public TilerData visit(IRESeq n) {
        if(n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return null;
    }

    @Override
    public TilerData visit(IRMem n) {
        if(n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return null;
    }

    @Override
    public TilerData visit(IRName n) {
        if(n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return null;
    }

    @Override
    public TilerData visit(IRTemp n) {
        if(n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return null;
    }

    @Override
    public TilerData visit(IRCallStmt n) {
        if(n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return null;
    }

    @Override
    public TilerData visit(IRCJump n) {
        if(n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return null;
    }

    @Override
    public TilerData visit(IRCompUnit n) {
        if(n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return null;
    }

    @Override
    public TilerData visit(IRExp n) {
        if(n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return null;
    }

    @Override
    public TilerData visit(IRFuncDecl n) {
        if(n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return null;
    }

    @Override
    public TilerData visit(IRJump n) {
        if(n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return null;
    }

    @Override
    public TilerData visit(IRLabel n) {
        if(n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return null;
    }

    @Override
    public TilerData visit(IRMove n) {
        if(n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return null;
    }

    @Override
    public TilerData visit(IRReturn n) {
        if(n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return null;
    }

    @Override
    public TilerData visit(IRSeq n) {
        if(n.hasOptimalTiling()) {
            return n.getOptimalTiling();
        }
        return null;
    }
}
