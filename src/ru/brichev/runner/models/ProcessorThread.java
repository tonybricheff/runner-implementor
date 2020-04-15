package ru.brichev.runner.models;

import ru.brichev.runner.interfaces.Processor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

//Implementation of processor thread

public class ProcessorThread<T> implements Callable<T> {

    //link to data storage
    private final DataStorage<T> dataStorage;

    //list of thread's processors
    private final List<Processor<T>> processors;

    //output of inputIds of current processor
    private final List<T> input;

    //number of currentIteration in thread
    private int currentIteration;

    //number of iteration in thread
    private final int maxIterations;

    public ProcessorThread(List<Processor<T>> processors, DataStorage<T> dataStorage, int maxIterations) {
        this.processors = processors;
        this.dataStorage = dataStorage;
        this.input = new ArrayList<>();
        this.currentIteration = 0;
        this.maxIterations = maxIterations;
    }


    /*
       checks if inputIds of processor have already processed
       if data storage is not alive throw ProcessorException
     */
    private boolean getStatus(Processor<T> processor) throws ProcessorException {
        input.clear();
        if (!dataStorage.getStatus()) {
            throw new ProcessorException("", "");
        }
        for (String id : processor.getInputIds()) {
            T idOut = dataStorage.get(id, currentIteration);
            if (idOut == null) {
                return false;
            }
        }
        return true;
    }


    /*
        fills list input using results of processor inputIds process method
    */
    private void getInput(Processor<T> processor) {
        input.clear();
        for (String id : processor.getInputIds()) {
            T idOut = dataStorage.get(id, currentIteration);
            input.add(idOut);
        }
    }


    /*
        Override of call method
        run iteration for list of processor
        Processor is waiting until his inputIds add their result to data storage
        Processor gets their inputIds and try to process them
        Adds the result of process to data storage

        if any processor throw Processor Exception, call catch it, kills the data storage
        and throws Processor Exception to the run method
     */
    @Override
    public T call() throws ProcessorException {
        for (currentIteration = 0; currentIteration < maxIterations; currentIteration++) {
            for (Processor<T> processor : processors) {
                T result;
                try {
                    while (!getStatus(processor)) {
                        Thread.yield();
                    }

                    //Uncomment to check the order of iteration in parallel threads
                    // System.out.println(Thread.currentThread().toString() + " processor: " + processor.getId() + " iteration: " + (currentIteration + 1));

                    getInput(processor);
                    result = processor.process(input);
                    dataStorage.add(processor.getId(), currentIteration, result);
                } catch (ProcessorException e) {
                    dataStorage.setStatus(false);
                    throw new ProcessorException(e.getMessage(), "");
                }
            }
        }
        return null;
    }
}
