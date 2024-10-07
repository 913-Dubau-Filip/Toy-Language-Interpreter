package Model.Statements;

import Exception.*;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IValue;
import Model.Value.IntIValue;
import Model.Value.StringIValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt {
    Exp exp;
    String id;

    public ReadFileStmt(Exp exp, String s) {
        this.exp = exp;
        this.id = s;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, VarAlreadyDeclaredException, InvalidTypeException, DivisionByZeroException, VarUndeclaredException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();
        if (symTable.isDefined(id)) {
            Type typId = (symTable.lookup(id)).getType();
            if (typId.equals(new IntType())) {
                IValue val = exp.eval(symTable, heap);
                StringIValue value = (StringIValue) val;
                if (val.getType().equals(new StringType())) {
                    if (fileTable.isDefined(value.getValue())) {
                        BufferedReader br = fileTable.lookup(value.getValue());
                        String line;
                        try {
                            line = br.readLine();
                            if (line == null) {
                                if (!symTable.isDefined(id))
                                    symTable.add(id, new IntIValue(0));
                                else
                                    symTable.update(id, new IntIValue(0));
                            } else {
                                int nr = Integer.parseInt(line);
                                if (!symTable.isDefined(id))
                                    symTable.add(id, new IntIValue(nr));
                                else
                                    symTable.update(id, new IntIValue(nr));
                            }
                        } catch (IOException e) {
                            throw new MyException(e.getMessage());
                        }
                    } else {
                        throw new MyException("Name does not exist in FileTable");
                    }
                } else
                    throw new MyException("Types do not match");
            } else
                throw new MyException("Declared type of variable " + id + " is not int");
        } else
            throw new MyException("The used variable " + id + " was not declared before");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(exp.deepCopy(), id);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "read " + id;
    }
}