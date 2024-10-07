package Model.Statements;

import Exception.*;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.ADT.MyIStack;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolIValue;
import Model.Value.IValue;

public class WhileStmt implements IStmt {
    private Exp e;
    private IStmt s;

    public WhileStmt(Exp exp, IStmt stmt) {
        this.e = exp;
        this.s = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, VarAlreadyDeclaredException, InvalidTypeException, DivisionByZeroException, VarUndeclaredException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIStack<IStmt> stk = state.getStk();
        MyIHeap<Integer, IValue> heap = state.getHeap();
        IValue condition = e.eval(symTable, heap);
        if (!(condition.getType() instanceof BoolType)) {
            throw new InvalidTypeException("The expression in the while should be a boolean");
        }
        BoolIValue check = (BoolIValue) e.eval(symTable, heap);
        if (check.getValue()) {
            stk.push(new WhileStmt(e, s));
            stk.push(s);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(e.deepCopy(), s.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expresioType = e.typecheck(typeEnv);
        if (!expresioType.equals(new BoolType())) {
            throw new InvalidTypeException("expresion must be avaluated to boolean");
        }
        s.typeCheck(typeEnv.deepcopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "while ( " + this.e.toString() + " )  " + this.s.toString();
    }
}