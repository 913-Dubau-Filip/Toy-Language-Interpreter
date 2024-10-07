package Model.ADT;

import java.util.ArrayList;

public class MyList<T> implements MyIList<T> {

    private ArrayList<T> list;

    public MyList() {
        list = new ArrayList<>();
    }

    public ArrayList<T> getList() {
        return list;
    }

    @Override
    public boolean append(T obj) {
        return list.add(obj);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public T get(int i) {
        return list.get(i);
    }

    @Override
    public String toString() {
        String rez = "";
        int i;
        for (i = 0; i < list.size(); i++) {
            rez = rez + list.get(i);
            rez = rez + "\n";
        }
        return rez;
    }

    @Override
    public ArrayList<T> getall() {
        return this.list;
    }
}