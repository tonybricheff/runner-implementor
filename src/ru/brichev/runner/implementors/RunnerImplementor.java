package ru.brichev.runner.implementors;

import ru.brichev.runner.interfaces.Processor;
import ru.brichev.runner.interfaces.Runner;
import ru.brichev.runner.models.DependencyGraph;
import ru.brichev.runner.models.ProcessorException;

import java.util.*;

public class RunnerImplementor implements Runner {

    @Override
    public Map<String, List> runProcessors(Set processors, int maxThreads, int maxIterations) throws ProcessorException, InterruptedException {

        Map<String, List> results = new HashMap<>();
        DependencyGraph dependencyGraph = new DependencyGraph(processors);
        dependencyGraph.printGraph();
        try {
            System.out.println(dependencyGraph.getProcessorsOrder());
        }catch (ProcessorException e){
            System.out.println("Exception: " + e.getMessage());
        }


        /*for (int i = 0; i < maxIterations; i++) {
            parallelWork(maxThreads, processors, results);
        }
         */

        return null;
    }

    private void  parallelWork(int threads, Set<Processor> processorsSet, Map<String, List> resultsMap) throws InterruptedException {

        if (threads == 0) {
            throw new IllegalArgumentException("There are 0 threads");
        }

        List<Processor> listOfProcessors = new ArrayList<>(processorsSet);
        listOfProcessors.sort(Comparator.comparingInt(processor -> processor.getInputIds().size()));

        int threadsCounter = Math.max(1, Math.min(listOfProcessors.size(), threads));
        int partSize = listOfProcessors.size() / threadsCounter;
        int restCounter = listOfProcessors.size() % threadsCounter;

        List<Thread> workers = new ArrayList<>();
        final List results = new ArrayList<>(Collections.nCopies(threadsCounter, null));
        for (int i = 0; i < threadsCounter; i++) {
            final int index = i;
            workers.add(new Thread(() -> System.out.println(listOfProcessors.get(index).getId())));
            workers.get(i).start();
        }

        for (int i = 0; i < threadsCounter; i++) {
            workers.get(i).join();
        }


    }

}
