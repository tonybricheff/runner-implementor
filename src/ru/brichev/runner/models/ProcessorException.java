package ru.brichev.runner.models;

public class ProcessorException extends Exception {

    public ProcessorException(String message, String processorId) {
        super(message + " " + processorId);
    }
}

