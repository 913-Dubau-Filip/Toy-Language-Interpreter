package Model.Type;

import Model.Value.BoolIValue;
import Model.Value.IValue;

public class BoolType implements Type {

    @Override
    public IValue defaultValue() {
        return new BoolIValue(false);
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }


    @Override
    public String toString() {
        return "bool";
    }
}