package Model.Statements;

import Exception.DivisionByZeroException;
import Exception.InvalidTypeException;
import Exception.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.ADT.MyIStack;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolIValue;
import Model.Value.IValue;

public class IfStmt implements IStmt {
    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el) {
        exp = e;
        thenS = t;
        elseS = el;
    }

    public Exp getExp() {
        return this.exp;
    }

    public IStmt getThenS() {
        return this.thenS;
    }

    public IStmt getElseS() {
        return this.elseS;
    }

    @Override
    public String toString() {
        return "(IF(" + exp.toString() + ") THEN(" + thenS.toString() + ")ELSE(" + elseS.toString() + "))";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, InvalidTypeException, DivisionByZeroException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, IValue> symTbl = state.getSymTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();
        IValue cond = exp.eval(symTbl, heap);
        BoolIValue bCond = (BoolIValue) cond;
        if (!cond.getType().equals(new BoolType())) {
            throw new InvalidTypeException("Conditional expression is not boolean");
        } else if (bCond.getValue()) {
            stk.push(thenS);
        } else {
            stk.push(elseS);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typeCheck(typeEnv.deepcopy());
            elseS.typeCheck(typeEnv.deepcopy());
            return typeEnv;
        } else
            throw new MyException("The condition of IF has not the type bool");
    }
}