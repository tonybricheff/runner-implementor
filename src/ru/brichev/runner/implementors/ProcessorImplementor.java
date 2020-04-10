package ru.brichev.runner.implementors;

import ru.brichev.runner.interfaces.Processor;
import ru.brichev.runner.modules.ProcessorException;

import java.util.List;


public class ProcessorImplementor implements Processor {

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
    public Object process(List input) throws ProcessorException {
        if(input.isEmpty()) {
            return null;
        }
        return null;
    }

}
