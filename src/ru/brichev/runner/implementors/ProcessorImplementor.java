package ru.brichev.runner.implementors;

import ru.brichev.runner.interfaces.Processor;
import ru.brichev.runner.models.ProcessorException;

import java.util.List;



public class ProcessorImplementor<T> implements Processor<T> {

    private final int pid;
    private final List<String> inputIds;

    public ProcessorImplementor(int pid, List<String> inputIds) {
        this.pid = pid;
        this.inputIds = inputIds;
    }

    @Override
    public int getId() {
        return pid;
    }

    @Override
    public List<String> getInputIds() {
        return inputIds;
    }

    @Override
    public T process(List<T> input) throws ProcessorException {
        if(input.isEmpty()) {
            return null;
        }
        T resultOfTreatment = null;

        return resultOfTreatment;
    }

    public int process() throws ProcessorException {

        return pid;
    }

    @Override
    public String toString() {
        return pid + " ";
    }
}
