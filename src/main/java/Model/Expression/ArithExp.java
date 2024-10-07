package Model.Expression;

import Exception.DivisionByZeroException;
import Exception.InvalidTypeException;
import Exception.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IValue;
import Model.Value.IntIValue;

public class ArithExp implements Exp {
    private final Exp e1;
    private final Exp e2;
    private int op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(char operation, Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
        switch (operation) {
            case '+' -> this.op = 1;
            case '-' -> this.op = 2;
            case '*' -> this.op = 3;
            case '/' -> this.op = 4;
        }
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyIHeap<Integer, IValue> hp) throws MyException, DivisionByZeroException, InvalidTypeException {
        IValue v1, v2;
        v1 = e1.eval(tbl, hp);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, hp);
            if (v2.getType().equals(new IntType())) {
                IntIValue i1 = (IntIValue) v1;
                IntIValue i2 = (IntIValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op == 1) return new IntIValue(n1 + n2);
                if (op == 2) return new IntIValue(n1 - n2);
                if (op == 3) return new IntIValue(n1 * n2);
                if (op == 4)
                    if (n2 == 0) throw new DivisionByZeroException("division by zero");
                    else return new IntIValue(n1 / n2);
            } else
                throw new InvalidTypeException("second operand is not an integer");
        } else
            throw new InvalidTypeException("first operand is not an integer");
        return new IntIValue();
    }


    @Override
    public Exp deepCopy() {
        return switch (this.op) {
            case 1 -> new ArithExp('+', this.e1.deepCopy(), this.e2.deepCopy());
            case 2 -> new ArithExp('-', this.e1.deepCopy(), this.e2.deepCopy());
            case 3 -> new ArithExp('*', this.e1.deepCopy(), this.e2.deepCopy());
            case 4 -> new ArithExp('/', this.e1.deepCopy(), this.e2.deepCopy());

            default -> throw new IllegalStateException("Unexpected value: " + this.op);
        };
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else
                throw new MyException("second operand is not an integer");
        } else
            throw new MyException("first operand is not an integer");
    }

    @Override
    public String toString() {
        return switch (this.op) {
            case 1 -> e1.toString() + "+" + e2.toString();
            case 2 -> e1.toString() + "-" + e2.toString();
            case 3 -> e1.toString() + "*" + e2.toString();
            case 4 -> e1.toString() + "/" + e2.toString();
            default -> "";
        };
    }
}