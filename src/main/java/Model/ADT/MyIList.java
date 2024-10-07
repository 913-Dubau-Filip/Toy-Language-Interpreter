package Model.ADT;

import java.util.ArrayList;

public interface MyIList<T> {
    boolean append(T obj);

    boolean isEmpty();

    int size();

    void clear();

    T get(int i); // index

    String toString();

    ArrayList<T> getall();
}