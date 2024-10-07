package Model.Statements;

import Exception.ADTException;
import Exception.MyException;
import Exception.VarAlreadyDeclaredException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIStack;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.IValue;

public class VarDeclStmt implements IStmt {
    String name;
    Type type;

    public VarDeclStmt(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, VarAlreadyDeclaredException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, IValue> symTbl = state.getSymTable();
        if (symTbl.isDefined(name))
            throw new VarAlreadyDeclaredException("Variable is already declared");
        else
            symTbl.add(name, type.defaultValue());
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, type.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.add(name, type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return this.type.toString() + " " + this.name;
    }
}