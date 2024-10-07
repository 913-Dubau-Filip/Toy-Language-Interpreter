package Model;

import Exception.*;
import Model.ADT.*;
import Model.Statements.IStmt;
import Model.Value.IValue;

import java.io.BufferedReader;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, IValue> symTable;
    MyIDictionary<String, BufferedReader> FileTable;
    MyIHeap<Integer, IValue> Heap;
    MyIList<IValue> out;
    IStmt originalProgram;
    private int id;
    public int nrkids = 0;

    public synchronized void incnrkids() {
        this.nrkids++;
    }

    public synchronized int getnrkids() {
        return nrkids;
    }

    synchronized void setId(int id) {
        this.id = id;
    }

    public synchronized int getId() {
        return id;
    }

    public int getIID() {
        return id;
    }

    public Boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException, ProgramStateIsEmptyException, VarAlreadyDeclaredException, ADTException, InvalidTypeException, DivisionByZeroException, VarUndeclaredException {

        if (exeStack.isEmpty())
            throw new ProgramStateIsEmptyException("PrgState stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, IValue> symtbl, MyIList<IValue> ot, MyIDictionary<String, BufferedReader> filetabl, MyHeap<Integer, IValue> heap, IStmt prg, int id) {
        setId(id);
        exeStack = stk;       //Execution Stack (ExeStack): a stack of statements to execute the current program
        symTable = symtbl;    //Table of Symbols (SymTable): a table which keeps the variables values (int v=5; dict: v:4)
        out = ot;           //Output (Out): that keeps all the messages printed by the toy program
        originalProgram = prg.deepCopy();  //recreate the entire original prg
        this.FileTable = filetabl;
        this.Heap = heap;
        stk.push(prg);
    }

    @Override
    public String toString() {

        return "ProgramStateID " + getId() + "\n" +
                "ExeStack:\n" + exeStack.toString() +
                "\nSymTable:\n" + symTable.toString() +
                "\nOut:\n" + out.toString() +
                "\nFilTable:\n" + FileTable.toString() +
                "\nHeap:\n" + Heap.toString() + "\n" +
                "--------------------------------------------------------------------------------";
    }

    public MyIStack<IStmt> getStk() {
        return exeStack;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public MyIList<IValue> getOutList() {
        return out;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return this.FileTable;
    }

    public MyIHeap<Integer, IValue> getHeap() {
        return this.Heap;
    }
}