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
import Model.Value.IntIValue;

public class RelationalExp implements Exp {
    private final Exp e1;
    private final Exp e2;
    private String op; //1-<, 2-<=, 3-==, 4-!=, 5 => , 6 - >=

    public RelationalExp(Exp e1, Exp e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
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
                return switch (op) {
                    case "<" -> new BoolIValue(n1 < n2);
                    case "<=" -> new BoolIValue(n1 <= n2);
                    case "==" -> new BoolIValue(n1 == n2);
                    case "!=" -> new BoolIValue(n1 != n2);
                    case ">" -> new BoolIValue(n1 > n2);
                    case ">=" -> new BoolIValue(n1 >= n2);
                    default -> throw new MyException("type is not int");
                };
            } else throw new InvalidTypeException("operand 2 is not an integer");
        } else throw new InvalidTypeException("Operator 1 is no an integer");
    }

    @Override
    public Exp deepCopy() {
        return new RelationalExp(this.e1.deepCopy(), this.e2.deepCopy(), this.op);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            } else
                throw new MyException("second operand is not an integer");
        } else
            throw new MyException("first operand is not an integer");
    }

    @Override
    public String toString() {
        return e1.toString() + this.op + e2.toString();
    }
}