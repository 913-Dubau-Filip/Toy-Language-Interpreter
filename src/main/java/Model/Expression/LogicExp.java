package Model.Expression;

import Exception.DivisionByZeroException;
import Exception.InvalidTypeException;
import Exception.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolIValue;
import Model.Value.IValue;

public class LogicExp implements Exp {
    Exp e1;
    Exp e2;
    String op; // & |

    //constructor, getters
    LogicExp(Exp e1, Exp e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    public Exp getE1() {
        return this.e1;
    }

    public Exp getE2() {
        return this.e2;
    }

    public String getOp() {
        return this.op;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyIHeap<Integer, IValue> hp) throws MyException, DivisionByZeroException, InvalidTypeException {
        IValue nr1 = e1.eval(tbl, hp);
        if (nr1.getType().equals(new BoolType())) {
            IValue nr2 = e2.eval(tbl, hp);
            if (nr2.getType().equals(new BoolType())) {
                BoolIValue n1 = (BoolIValue) nr1;
                BoolIValue n2 = (BoolIValue) nr2;
                if (op.equals("&")) {
                    return new BoolIValue(n1.getValue() && n2.getValue());
                } else if (op.equals("|"))
                    return new BoolIValue(n1.getValue() || n2.getValue());
                else
                    throw new InvalidTypeException("Invalid boolean operation!");
            } else
                throw new InvalidTypeException("Operand2 is not boolean");
        } else
            throw new InvalidTypeException("Operand1 is not boolean");
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(this.e1.deepCopy(), this.e2.deepCopy(), this.op);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new IntType();
            } else
                throw new MyException("second operand is not an Boolean");
        } else
            throw new MyException("first operand is not an Boolean");
    }

    @Override
    public String toString() {
        return this.e1.toString() + this.op + this.e2.toString();
    }
}