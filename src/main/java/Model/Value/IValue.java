package Model.Value;

import Model.Type.Type;

public interface IValue {
    Type getType();

    public IValue deepCopy();
}