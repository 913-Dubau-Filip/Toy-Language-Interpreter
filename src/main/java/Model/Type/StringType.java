package Model.Type;

import Model.Value.IValue;
import Model.Value.StringIValue;

public class StringType implements Type {
    @Override
    public IValue defaultValue() {
        return new StringIValue();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringType;
    }

    @Override
    public Type deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "String";
    }
}