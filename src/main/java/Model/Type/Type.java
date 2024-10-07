package Model.Type;

import Model.Value.IValue;

public interface Type {
    IValue defaultValue();

    public Type deepCopy();
}
