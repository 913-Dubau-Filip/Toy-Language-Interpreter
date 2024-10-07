package Model.Value;

import Model.Type.IntType;
import Model.Type.Type;

import java.util.Objects;

public class IntIValue implements IValue {
    int val;

    public IntIValue() {
        this.val = 0;
    }

    public IntIValue(int v) {
        val = v;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int nv) {
        this.val = nv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntIValue intValue = (IntIValue) o;
        return val == intValue.val;
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }

    public Type getType() {
        return new IntType();
    }

    @Override
    public IValue deepCopy() {
        return new IntIValue(val);
    }
}