package Model.Expression;

import Exception.DivisionByZeroException;
import Exception.InvalidTypeException;
import Exception.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Type.Type;
import Model.Value.IValue;

public interface Exp {

    IValue eval(MyIDictionary<String, IValue> tbl, MyIHeap<Integer, IValue> hp) throws MyException, DivisionByZeroException, InvalidTypeException;

    public Exp deepCopy();

    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}