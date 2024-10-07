package Model.ADT;

import Exception.ExecutionStackEmpty;
import Exception.MyException;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {

    private Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<>();
    }

    @Override
    public T pop() throws MyException {
        if (stack.isEmpty())
            throw new ExecutionStackEmpty("Stack is empty.");
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.empty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    public Stack<T> getStack() {
        return stack;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = stack.size() - 1; i >= 0; i--) {
            sb.append(stack.get(i));
            if (i != 0)
                sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public Stack<T> getContent() {
        return this.stack;
    }
}