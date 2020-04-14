package ru.brichev.runner.models;

import java.util.*;

public class DataStore<T> {
    private final Map<Pair, T> data;
    private final Map<String, List<T>> results;

    public DataStore(Map<String, List<T>> results) {
        this.results = results;
        this.data = new HashMap<>();
    }

    public synchronized void add(String pid, Integer iteration, T value) {
        data.put(new Pair(pid, iteration), value);
        if (!results.containsKey(pid)) {
            results.put(pid, new ArrayList<>());
        }
        results.get(pid).add(value);
    }

    public synchronized T get(String pid, Integer iteration) {
        return data.get(new Pair(pid, iteration));
    }
}