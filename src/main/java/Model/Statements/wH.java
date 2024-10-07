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

public class wH implements IStmt {
    Exp e;
    String var_Name;

    public wH(String Name, Exp exp) {
        this.e = exp;
        this.var_Name = Name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, VarAlreadyDeclaredException, InvalidTypeException, DivisionByZeroException, VarUndeclaredException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();
        /*
        first we check if var_name is a variable defined in SymTable, if its type is a Ref type and if
        the address from the RefValue associated in SymTable is a key in Heap. If not, the execution
        is stopped with an appropriate error message.
         */
        if (symTable.isDefined(var_Name)) {
            IValue value = (symTable.lookup(var_Name));
            if (value.getType() instanceof RefType) {

                RefIValue refV = (RefIValue) value;
                int adr = refV.getAddress();
                if (heap.isDefined(adr)) {

                    //Second the expression is evaluated and the result must have its type equal to the
                    //locationType of the var_name type. If not, the execution is stopped with an appropriate
                    //message
                    RefType refType = (RefType) value.getType();
                    IValue val = e.eval(symTable,heap);
                    if (val.getType().equals(refType.getInner())) {

                        //Third we access the Heap using the address from var_name and that Heap entry is updated
                        //to the result of the expression evaluation
                        heap.put(refV.getAddress(), val);
                        symTable.put(var_Name, new RefIValue(refV.getAddress(), refV.getLocationType()));
                    }else
                        throw new InvalidTypeException("locationType of the var_name types are not the same");
                } else
                    throw new MyException("the address from the RefValue associated in SymTable is not a key in Heap");
            } else
                throw new InvalidTypeException(var_Name + " is not Reference Type");
        } else
            throw new MyException("The used variable " + var_Name + " is not declared");

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new wH(var_Name,e.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(var_Name);
        Type typExp = e.typecheck(typeEnv);
        if (typeVar.equals(new RefType(typExp)))
            return typeEnv;
        else
            throw new MyException("wH stmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return "wH( " + var_Name + ", " + this.e.toString() + " )";
    }
}