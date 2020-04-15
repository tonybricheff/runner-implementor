package ru.brichev.runner.implementors;

import ru.brichev.runner.interfaces.Processor;
import ru.brichev.runner.models.ProcessorException;

import java.util.List;


public class ProcessorImplementor implements Processor<java.lang.Integer> {

    private final String pid;
    private final List<String> inputIds;
    private int lastInput;


    public ProcessorImplementor(String pid, List<String> inputIds) {
        this.pid = pid;
        this.inputIds = inputIds;
        this.lastInput = 0;
    }


    @Override
    public String getId() {
        return pid;
    }

    @Override
    public List<String> getInputIds() {
        return inputIds;
    }


    @Override
    public java.lang.Integer process(List<java.lang.Integer> input) throws ProcessorException {

        int resultOfProcessing = 1;


        for (java.lang.Integer out : input) {
            resultOfProcessing += out;
        }

        resultOfProcessing += lastInput;
        lastInput = resultOfProcessing;

        //if(result == 14)
        //  throw new ProcessorException("ban", pid);

        /*if(result == 19)
            return null;
         */

        return resultOfProcessing;
    }


    @Override
    public String toString() {
        return pid + " ";
    }

}
