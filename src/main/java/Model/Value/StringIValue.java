package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

import java.util.Objects;

public class StringIValue implements IValue {

    private final String value;


    public StringIValue() {
        this.value = " ";
    }

    public StringIValue(String value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringIValue that = (StringIValue) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public IValue deepCopy() {
        return new StringIValue(value);
    }

    public String getValue() {
        return this.value;
    }
}