package Model.Statements;


import Exception.*;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.ADT.MyIStack;
import Model.Expression.Exp;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.IValue;

public class AssignStmt implements IStmt {
    String id;
    Exp exp;

    public AssignStmt(String id, Exp ex) {
        this.exp = ex;
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public Exp getExp() {
        return this.exp;
    }

    @Override
    public String toString() {
        return id + "=" + exp.toString();
    } //x=4

    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, InvalidTypeException, DivisionByZeroException, VarUndeclaredException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, IValue> symTbl = state.getSymTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();
        if (symTbl.isDefined(id)) {
            IValue val = exp.eval(symTbl, heap);
            Type typId = (symTbl.lookup(id)).getType();
            if (val.getType().equals(typId))
                symTbl.update(id, val);
            else
                throw new InvalidTypeException("declared type of variable " + id + " " + typId.toString() + " and type of the assigned expression " + val.getType().toString() + " do not match");
        } else throw new VarUndeclaredException("the used variable " + id + " was not declared before");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(id);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }
}