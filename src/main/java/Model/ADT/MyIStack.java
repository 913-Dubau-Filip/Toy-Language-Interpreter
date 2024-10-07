package Model.ADT;

import Exception.MyException;

import java.util.Stack;

public interface MyIStack<T> {

    T pop() throws MyException;

    void push(T v);

    boolean isEmpty();

    int size();

    String toString();

    public Stack<T> getContent();
}