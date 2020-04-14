package ru.brichev.runner.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStore<T> {
    private final Map<String, T> data;
    private final Map<String, List<T>> results;
    private List<ProcessorThread<T>> processorThreads;

    public DataStore(Map<String, List<T>> results) {
        this.results = results;
        this.data = new HashMap<>();
        this.processorThreads = new ArrayList<>();
    }

    public void setProcessorThreads(List<ProcessorThread<T>> processorThreads){
        this.processorThreads = processorThreads;
    }

    public List<ProcessorThread<T>> getProcessorThreads() {
        return processorThreads;
    }

    public synchronized void add(String pid, T value) {
        data.put(pid, value);
        if (!results.containsKey(pid)) {
            results.put(pid, new ArrayList<>());
        }
        results.get(pid).add(value);
    }

    public synchronized T get(String pid) {
        return data.get(pid);
    }
}
