package Model.Statements;

import Exception.*;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.IValue;
import Model.Value.RefIValue;

public class newStmt implements IStmt {
    String var_name;
    Exp exp;

    public newStmt(String s, Exp e) {
        var_name = s;
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, VarAlreadyDeclaredException, InvalidTypeException, DivisionByZeroException, VarUndeclaredException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();
        if (symTable.isDefined(var_name)) {
            IValue value = (symTable.lookup(var_name));
            if (value.getType() instanceof RefType) {
                RefType refType = (RefType) value.getType();
                IValue val = exp.eval(symTable, heap);
                if (val.getType().equals(refType.getInner())) {
                    int k = heap.nextFreeAdress();
                    heap.add(k, val);
                    RefIValue refV = (RefIValue) value;
                    symTable.update(var_name, new RefIValue(k, refV.getLocationType()));
                } else {
                    throw new MyException("The type of the expression and locationType are not the same");
                }
            } else
                throw new MyException(var_name + " is not Reference Type");
        } else
            throw new MyException("The used variable " + var_name + " was not declared before");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new newStmt(var_name, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(var_name);
        Type typExp = exp.typecheck(typeEnv);
        if (typeVar.equals(new RefType(typExp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return "new(" + var_name + ", " + exp + ")";
    }
}