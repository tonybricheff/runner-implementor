package ru.brichev.runner.models;

import java.util.List;

public class ProcessorThread<T> extends Thread{
    private final List<String> inputIds;
    private DataStore<T> dataStore;
    private Integer pid;


    public ProcessorThread(Integer pid, List<String> inputIds, DataStore<T> dataStore) {
        this.pid = pid;
        this.inputIds = inputIds;
        this.dataStore = dataStore;
    }

    private boolean getStatus(){
        for(String id : inputIds){
            if(dataStore.get(id) == null){
                return false;
            }
        }
        return true;
    }


    @Override
    public void run() {
        while (!getStatus()){
            Thread.yield();
        }
        System.out.println(getName() + " " + pid);
        dataStore.add(pid.toString(), (T) "a");
    }

}
