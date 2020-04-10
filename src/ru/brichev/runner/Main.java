package ru.brichev.runner;

import ru.brichev.runner.implementors.ProcessorImplementor;
import ru.brichev.runner.implementors.RunnerImplementor;
import ru.brichev.runner.modules.ProcessorException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {
        ProcessorImplementor processorImplementor = new ProcessorImplementor(5, new ArrayList<>());
        RunnerImplementor runnerImplementor = new RunnerImplementor();


        Set set = new HashSet();
        set.add(processorImplementor);
        try {
            runnerImplementor.runProcessors(set, 10, 2);
        }catch (InterruptedException | ProcessorException e){
            System.out.println(e.getMessage());
        }
    }
}
