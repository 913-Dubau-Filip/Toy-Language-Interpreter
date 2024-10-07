package Model.Statements;

import Exception.*;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IValue;
import Model.Value.StringIValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt {

    Exp exp;

    public CloseRFileStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, VarAlreadyDeclaredException, InvalidTypeException, DivisionByZeroException, VarUndeclaredException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();
        IValue val = exp.eval(symTable, heap);
        if (val.getType().equals(new StringType())) {
            StringIValue value = (StringIValue) val;
            if (fileTable.isDefined(value.getValue())) {
                try {
                    BufferedReader bufferedReader = fileTable.lookup(value.getValue());
                    bufferedReader.close();
                    fileTable.remove(value.getValue());
                } catch (IOException e) {
                    throw new MyException(e.getMessage());
                }
            } else
                throw new MyException("Name does not exist in FileTable");
        } else
            throw new MyException("Types do not match");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFileStmt(this.exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "close " + exp.toString();
    }
}