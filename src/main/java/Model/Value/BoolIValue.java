package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

import java.util.Objects;

public class BoolIValue implements IValue {
    private boolean value;

    public BoolIValue() {
        this.value = false;
    }

    public BoolIValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }

    public void setValue(boolean nv) {
        this.value = nv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoolIValue boolValue = (BoolIValue) o;
        return value == boolValue.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public IValue deepCopy() {
        return new BoolIValue(value);
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}