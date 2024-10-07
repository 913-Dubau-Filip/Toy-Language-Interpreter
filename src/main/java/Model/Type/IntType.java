package Model.Type;

import Model.Value.IValue;
import Model.Value.IntIValue;

public class IntType implements Type {
    public IValue defaultValue() {
        return new IntIValue(0);
    }

    @Override
    public Type deepCopy() {
        return new IntType();
    }

    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }
}
