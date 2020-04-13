package ru.brichev.runner.implementors;

import ru.brichev.runner.interfaces.Processor;
import ru.brichev.runner.interfaces.Runner;
import ru.brichev.runner.models.DependencyGraph;
import ru.brichev.runner.models.ProcessorException;

import java.util.*;

public class RunnerImplementor<T> implements Runner<T> {

    @Override
    public Map<String, List<T>> runProcessors(Set<Processor<T>> processors, int maxThreads, int maxIterations) throws ProcessorException, InterruptedException {

        Map<String, List<T>> results = new HashMap<>();
        DependencyGraph<T> dependencyGraph = new DependencyGraph<>(processors);
        List<Processor<T>> runOrder = dependencyGraph.getProcessorsOrder();
        
        /*
        for(Processor<T> processor : runOrder)
            System.out.print(processor.getId() + " ");

         */

        for (int i = 0; i < maxIterations; i++) {
            parallelWork(maxThreads, runOrder, results);
        }


        return null;
    }

    private void parallelWork(int threads, List<Processor<T>> runOrder, Map<String, List<T>> resultsMap) throws InterruptedException {

        if (threads == 0) {
            throw new IllegalArgumentException("There are 0 threads");
        }

        int threadsCounter = Math.max(1, Math.min(runOrder.size(), threads));

        List<Thread> workers = new ArrayList<>();
        final List<T> results = new ArrayList<>(Collections.nCopies(threadsCounter, null));
        for (int i = 0; i < threadsCounter; i++) {
            final int index = i;
            workers.add(new Thread(() -> System.out.println(Thread.currentThread().toString() + " " + runOrder.get(index).getId())));
            workers.get(i).start();
        }

        for (int i = 0; i < threadsCounter; i++) {
            workers.get(i).join();
        }


    }

}
