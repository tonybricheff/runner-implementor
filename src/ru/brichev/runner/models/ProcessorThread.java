package ru.brichev.runner.models;

import java.util.List;

public class ProcessorThread extends Thread{
    private final List<String> inputIds;

    public ProcessorThread(List<String> inputIds) {
        this.inputIds = inputIds;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().toString() + " " + inputIds);
    }

}
