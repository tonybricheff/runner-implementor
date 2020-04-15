package ru.brichev.runner.models;

//Implementation of Processor Exception
public class ProcessorException extends Exception {

    public ProcessorException(String message, String processorId) {
        super(message + " " + processorId);
    }
}

