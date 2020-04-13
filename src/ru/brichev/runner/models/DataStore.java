package ru.brichev.runner.models;

import java.util.HashMap;
import java.util.Map;

public class DataStore<T> {
    private final Map<String, T> data;

    public DataStore(){
        data = new HashMap<>();
    }

    public synchronized void add(String  pid, T value){
        data.put(pid, value);
    }

    public synchronized T get(String pid){
        return data.get(pid);
    }

}
