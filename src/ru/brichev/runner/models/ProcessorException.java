package ru.brichev.runner.models;

public class ProcessorException extends Exception {

    private String processorId;

    public ProcessorException(String message, String processorId) {
        super(message);
        this.processorId = processorId;
    }
}

