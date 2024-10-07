package Model.ADT;

import Exception.ADTException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyHeap<K, V> implements MyIHeap<K, V> {
    private Map<K, V> map;
    private AtomicInteger index = new AtomicInteger(1);

    public MyHeap() {
        map = new HashMap<>();
        index = new AtomicInteger(1);
    }

    public void setContent(Map map) {
        this.map = map;
    }

    @Override
    public void put(K key, V el) {
        if (!this.isDefined(key))
            index.incrementAndGet();
        map.put(key, el);
    }

    @Override
    public Iterator<K> getIterator() {
        return map.keySet().iterator();
    }

    @Override
    public void add(K key, V value) throws ADTException {
        if (map.containsKey(key))
            throw new ADTException("Element already exists");
        index.incrementAndGet();
        map.put(key, value);
    }

    @Override
    public void remove(K key) throws ADTException {
        if (!map.containsKey(key))
            throw new ADTException("Element doesn't exists.");
        map.remove(key);
    }

    @Override
    public void update(K key, V value) throws ADTException {
        if (!map.containsKey(key))
            throw new ADTException("Element does not exist.");
        map.replace(key, value);
    }

    @Override
    public V lookup(K key) {
        return map.get(key);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public int nextFreeAdress() {
        return index.get();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Map<K, V> getContent() {
        return map;
    }

    @Override
    public boolean isDefined(K id) {
        return map.containsKey(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (K s : map.keySet()) {
            if (!first) {
                sb.append("\n");
            }
            if (first) {
                first = false;
            }
            sb.append(s.toString()).append("->").append(map.get(s).toString());
        }
        return sb.toString();
    }
}