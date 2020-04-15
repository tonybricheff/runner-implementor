package ru.brichev.runner.models;

import java.util.*;


public class DataStorage<T> {

    //Storage: Pair(id, iteration), output of Processor::process
    private final Map<Pair, T> data;

    //Map with results of each processor iteration
    private final Map<String, List<T>> results;

    //Status parameter
    private boolean isAlive;

    public DataStorage(Map<String, List<T>> results) {
        this.results = results;
        this.data = new HashMap<>();
        this.isAlive = true;
    }

    public synchronized void setStatus(boolean alive) {
        isAlive = alive;
    }

    public synchronized boolean getStatus() {
        return isAlive;
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