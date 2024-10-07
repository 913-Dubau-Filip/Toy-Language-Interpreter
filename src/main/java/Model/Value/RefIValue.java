package Model.Value;

import Model.Type.RefType;
import Model.Type.Type;

import java.util.Objects;

public class RefIValue implements IValue {
    int address;
    Type locationType;

    public RefIValue(int a, Type l) {
        this.address = a;
        this.locationType = l;
    }

    public int getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefIValue refIValue = (RefIValue) o;
        return address == refIValue.address && Objects.equals(locationType, refIValue.locationType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, locationType);
    }

    public int getAddr() {
        return address;
    }

    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public IValue deepCopy() {
        return new RefIValue(address, locationType.deepCopy());
    }

    @Override
    public String toString() {
        return "( " + address + ", " + locationType + " )";
    }
}