package ru.brichev.runner;

import ru.brichev.runner.implementors.RunnerImplementor;
import ru.brichev.runner.test.implementors.ProcessorImplementor;
import ru.brichev.runner.interfaces.Processor;
import ru.brichev.runner.models.ProcessorException;

import java.util.*;


public class Main {

    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>();
        list1.add("4");
        List<String> list2 = new ArrayList<>();
        list2.add("3");
        list2.add("1");
        list2.add("4");
        List<String> list3 = new ArrayList<>();
        list3.add("4");
        List<String> list4 = new ArrayList<>();

        ProcessorImplementor processorImplementor1 = new ProcessorImplementor("1", list1);

        ProcessorImplementor processorImplementor2 = new ProcessorImplementor("2", list2);

        ProcessorImplementor processorImplementor3 = new ProcessorImplementor("3", list3);

        ProcessorImplementor processorImplementor4 = new ProcessorImplementor("4", list4);

        RunnerImplementor<Integer> runnerImplementor = new RunnerImplementor<>();


        Set<Processor<Integer>> set = new HashSet<>();
        set.add(processorImplementor1);
        set.add(processorImplementor2);
        set.add(processorImplementor3);
        set.add(processorImplementor4);

        Map<String, List<Integer>> results;
        try {
            results = runnerImplementor.runProcessors(set, 4, 5);
            System.out.println(results);
        } catch (ProcessorException e) {
            System.out.println("Run thrown: " + e.getMessage());
        }
    }
}
