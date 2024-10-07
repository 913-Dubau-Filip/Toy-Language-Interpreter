package Model.Expression;

import Exception.DivisionByZeroException;
import Exception.InvalidTypeException;
import Exception.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.IValue;
import Model.Value.RefIValue;

public class rH implements Exp {
    Exp e;

    public rH(Exp exp) {
        this.e = exp;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyIHeap<Integer, IValue> hp) throws MyException, DivisionByZeroException, InvalidTypeException {
        IValue value = e.eval(tbl, hp);
        if (value instanceof RefIValue refValue) {
            int a = refValue.getAddress();
            if (hp.isDefined(a)) {
                return hp.lookup(a);
            } else
                throw new MyException("Address not found in heap");
        } else
            throw new MyException("Exp must  be evaluated to a Reference Value");
    }

    @Override
    public Exp deepCopy() {
        return new rH(this.e.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ = e.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft = (RefType) typ;
            return reft.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }

    @Override
    public String toString() {
        return "rH( " + e.toString() + " )";
    }
}