package Model.ADT;

import Exception.ADTException;

import java.util.Iterator;
import java.util.Map;

public interface MyIHeap<K, V> {
    void add(K key, V value) throws ADTException;

    void remove(K key) throws ADTException;

    void update(K key, V value) throws ADTException;

    V lookup(K key);

    int size();

    int nextFreeAdress();

    boolean isEmpty();

    Map<K, V> getContent();

    String toString();

    boolean isDefined(K id);

    void setContent(Map map);

    void put(K key, V el);

    Iterator<K> getIterator();
}