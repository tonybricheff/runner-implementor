package ru.brichev.runner.modules;

public class ProcessorException extends Exception {

    private int processorId;

    public ProcessorException(String message, int processorId) {
        super(message);
        this.processorId = processorId;
    }
}

