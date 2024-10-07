package Model.Statements;

import Exception.MyException;
import Model.ADT.*;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.IValue;

import java.io.BufferedReader;


public class ForkStmt implements IStmt {
    IStmt s;

    public ForkStmt(IStmt e) {
        this.s = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> newstack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = state.getSymTable().deepcopy();
        MyIDictionary<String, BufferedReader> FileTable = state.getFileTable();
        MyIHeap<Integer, IValue> Heap = state.getHeap();
        MyIList<IValue> out = state.getOutList();
        int id = state.getId() * 10 + state.getnrkids();
        state.incnrkids();
        return new PrgState(newstack, symTable, out, FileTable, (MyHeap<Integer, IValue>) Heap, s, id);
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(s.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return s.typeCheck(typeEnv);
    }

    @Override
    public String toString() {
        return "fork(" + this.s.toString() + "   )";
    }
}