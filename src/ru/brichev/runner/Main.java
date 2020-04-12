package ru.brichev.runner;

import ru.brichev.runner.implementors.ProcessorImplementor;
import ru.brichev.runner.implementors.RunnerImplementor;
import ru.brichev.runner.models.ProcessorException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {


        List<String> list1 = new ArrayList<>();
        list1.add("4");
        List<String> list2 = new ArrayList<>();
        list2.add("3");
        list2.add("1");
        list2.add("4");
        List<String> list3 = new ArrayList<>();
        list3.add("1");
        list3.add("4");
        list3.add("2");
        List<String> list4 = new ArrayList<>();

        ProcessorImplementor processorImplementor1 = new ProcessorImplementor(1, list1);

        ProcessorImplementor processorImplementor2 = new ProcessorImplementor(2, list2);

        ProcessorImplementor processorImplementor3 = new ProcessorImplementor(3, list3);

        ProcessorImplementor processorImplementor4 = new ProcessorImplementor(4, list4);

        RunnerImplementor runnerImplementor = new RunnerImplementor();


        Set<ProcessorImplementor> set = new HashSet<>();
        set.add(processorImplementor1);
        set.add(processorImplementor2);
        set.add(processorImplementor3);
        set.add(processorImplementor4);
        try {
            runnerImplementor.runProcessors(set, 10, 2);
        }catch (InterruptedException | ProcessorException e){
            System.out.println(e.getMessage());
        }
    }
}
