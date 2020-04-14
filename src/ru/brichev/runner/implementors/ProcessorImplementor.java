package ru.brichev.runner.implementors;

import ru.brichev.runner.interfaces.Processor;
import ru.brichev.runner.models.ProcessorException;

import java.util.List;



public class ProcessorImplementor<T extends Integer> implements Processor<T> {

    private final String pid;
    private final List<String> inputIds;
    private Integer lastInput;

    public ProcessorImplementor(String pid, List<String> inputIds) {
        this.pid = pid;
        this.inputIds = inputIds;
        this.lastInput = 0;
    }

    @Override
    public Integer getLast() {
        return lastInput;
    }

    @Override
    public void setLast(Integer last) {
            this.lastInput = last;
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
    public Integer process(List<T> input) throws ProcessorException {

        int result = 1;
        for(T out : input){
            result += out.intValue();
        }
        if(result == 18)
            throw new ProcessorException("Тест", pid);
        //if(result == 19)
          //  return null;
        return result;
    }


    @Override
    public String toString() {
        return pid + " ";
    }

    public Integer getLastInput() {
        return lastInput;
    }

}
