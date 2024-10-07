package Model.Type;

import Model.Value.IValue;
import Model.Value.RefIValue;

public class RefType implements Type {
    Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof RefType)
            return inner.equals(((RefType) another).getInner());
        else
            return false;
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }

    public IValue defaultValue() {
        return new RefIValue(0, inner);
    }

    @Override
    public Type deepCopy() {
        return null;
    }
}