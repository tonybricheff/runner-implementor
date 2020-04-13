package ru.brichev.runner.models;

import ru.brichev.runner.interfaces.Processor;

import java.util.ArrayList;
import java.util.List;

public class ProcessorThread<T> extends Thread {
    private final List<String> inputIds;
    private final DataStore<T> dataStore;
    private final Processor<T> processor;
    private List<T> input;

    public ProcessorThread(Processor<T> processor, List<String> inputIds, DataStore<T> dataStore) {
        this.processor = processor;
        this.inputIds = inputIds;
        this.dataStore = dataStore;
        this.input = new ArrayList<>();

    }

    private boolean getStatus() {
        input.clear();
        for (String id : inputIds) {
            T idOut = dataStore.get(id);
            if (idOut == null) {
                return false;
            } else {
                input.add(idOut);
            }
        }
        return true;
    }

    private void getInput() {
        input.clear();
        for (String id : inputIds) {
            T idOut = dataStore.get(id);
            input.add(idOut);
        }

    }


    @Override
    public void run() {
        while (!getStatus()) {
            Thread.yield();
        }
        getInput();
        System.out.println(getName() + " " + processor.getId() + " " + input + " " + processor.getLast());
        try {
            if (input == null) {
                dataStore.add(processor.getId(), (T) processor.process(new ArrayList<>()));
                processor.setLast(1);
            } else {
                getInput();
                input.add((T) processor.getLast());
                dataStore.add(processor.getId(), (T) processor.process(input));
                processor.setLast(processor.process(input));
            }
        } catch (ProcessorException e) {
            e.printStackTrace();
        }
    }

}
