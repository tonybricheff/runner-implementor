package ru.brichev.runner.models;

import ru.brichev.runner.interfaces.Processor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ProcessorThread<T> implements Callable<T> {
    private final DataStore<T> dataStore;
    private final List<Processor<T>> processors;
    private final List<T> input;
    private int currentIteration;
    private final int maxIterations;

    public ProcessorThread(List<Processor<T>> processors, DataStore<T> dataStore, int maxIterations) {
        this.processors = processors;
        this.dataStore = dataStore;
        this.input = new ArrayList<>();
        this.currentIteration = 0;
        this.maxIterations = maxIterations;
    }

    private boolean getStatus(Processor<T> processor) {
        input.clear();
        for (String id : processor.getInputIds()) {
            T idOut = dataStore.get(id, currentIteration);
            if (idOut == null) {
                return false;
            }
        }
        return true;
    }

    private void getInput(Processor<T> processor) {
        input.clear();
        for (String id : processor.getInputIds()) {
            T idOut = dataStore.get(id, currentIteration);
            input.add(idOut);
        }
    }


    @Override
    public T call() throws ProcessorException {
        for (currentIteration = 0; currentIteration < maxIterations; currentIteration++) {
            for (Processor<T> processor : processors) {
                T result;
                while (!getStatus(processor)) {
                    Thread.yield();
                }
                System.out.println(Thread.currentThread().toString() + " processor: " + processor.getId() + " iteration: " + (currentIteration + 1));
                getInput(processor);
                input.add((T) processor.getLast());
                result = (T) processor.process(input);
                dataStore.add(processor.getId(), currentIteration, result);
                processor.setLast(processor.process(input));
            }
        }
        return null;
    }
}
