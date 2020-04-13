package ru.brichev.runner.models;

import ru.brichev.runner.interfaces.Processor;

import java.util.ArrayList;
import java.util.List;

public class ProcessorThread<T> extends Thread {
    private final DataStore<T> dataStore;
    private final List<Processor<T>> processors;
    private final List<T> input;

    public ProcessorThread(List<Processor<T>> processors, DataStore<T> dataStore) {
        this.processors = processors;
        this.dataStore = dataStore;
        this.input = new ArrayList<>();
    }

    private boolean getStatus(Processor<T> processor) {
        input.clear();
        for (String id : processor.getInputIds()) {
            T idOut = dataStore.get(id);
            if (idOut == null) {
                return false;
            } else {
                input.add(idOut);
            }
        }
        return true;
    }

    private void getInput(Processor<T> processor) {
        input.clear();
        for (String id : processor.getInputIds()) {
            T idOut = dataStore.get(id);
            input.add(idOut);
        }

    }


    @Override
    public void run() {
        for(Processor<T> processor : processors) {
            while (!getStatus(processor)) {
                Thread.yield();
            }
            // getInput();
            System.out.println(getName() + " " + processor.getId() + " " + input + " " + processor.getLast());
            try {
                if (input == null) {
                    dataStore.add(processor.getId(), (T) processor.process(new ArrayList<>()));
                    processor.setLast(1);
                } else {
                    getInput(processor);
                    input.add((T) processor.getLast());
                    dataStore.add(processor.getId(), (T) processor.process(input));
                    processor.setLast(processor.process(input));
                }
            } catch (ProcessorException e) {
                e.printStackTrace();
            }
        }
    }

}
