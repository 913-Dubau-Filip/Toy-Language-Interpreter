package Model.Statements;

import Exception.MyException;
import Exception.VarAlreadyDeclaredException;
import Exception.VarUndeclaredException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IValue;
import Model.Value.StringIValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFileStmt implements IStmt {

    private Exp exp;

    public OpenRFileStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, VarAlreadyDeclaredException, VarUndeclaredException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();
        IValue val = exp.eval(symTable, heap);
        if (val.getType().equals(new StringType())) {
            StringIValue value = (StringIValue) val;
            if (!fileTable.isDefined(value.getValue())) {
                try {
                    BufferedReader buffered = new BufferedReader(new FileReader(value.getValue()));
                    fileTable.add(value.getValue(), buffered);
                } catch (FileNotFoundException e) {
                    throw new MyException(e.getMessage());
                }
            } else
                throw new MyException("Name already exists in FileTable");
        } else
            throw new MyException("Types do not match");
        return null;
    }

    @Override
    public String toString() {
        return "open " + exp.toString();
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFileStmt(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }
}