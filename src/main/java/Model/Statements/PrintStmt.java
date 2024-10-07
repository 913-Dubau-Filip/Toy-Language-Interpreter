package Model.Statements;

import Exception.DivisionByZeroException;
import Exception.InvalidTypeException;
import Exception.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.ADT.MyIList;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.IValue;

public class PrintStmt implements IStmt {
    Exp exp;

    public PrintStmt(Exp e) {
        this.exp = e;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, InvalidTypeException, DivisionByZeroException {
        MyIList<IValue> list = state.getOutList();
        MyIDictionary<String, IValue> symTbl = state.getSymTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();
        list.append(exp.eval(symTbl, heap));
        return null;
    }

    @Override
    public String toString() {
        return ("print(" + exp.toString() + ")");
    }
}