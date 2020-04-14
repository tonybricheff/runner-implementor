package ru.brichev.runner.implementors;

import ru.brichev.runner.interfaces.Processor;
import ru.brichev.runner.interfaces.Runner;
import ru.brichev.runner.models.DataStore;
import ru.brichev.runner.models.DependencyGraph;
import ru.brichev.runner.models.ProcessorException;
import ru.brichev.runner.models.ProcessorThread;

import java.util.*;
import java.util.concurrent.ExecutorService;


public class RunnerImplementor<T> implements Runner<T> {


    @Override
    public Map<String, List<T>> runProcessors(Set<Processor<T>> processors, int maxThreads, int maxIterations) throws ProcessorException, InterruptedException {

        Map<String, List<T>> results = new HashMap<>();
        DependencyGraph<T> dependencyGraph = new DependencyGraph<>(processors);
        List<Processor<T>> runOrder = dependencyGraph.getProcessorsOrder();
        DataStore<T> dataStore = new DataStore<>(results);


        for (Processor<T> processor : runOrder)
            System.out.print(processor.getId() + " ");
        System.out.println();

        if (maxThreads == 0) {
            throw new IllegalArgumentException("There are 0 threads");
        }

        List<ProcessorThread<T>> workers = new ArrayList<>();

        ExecutorService executorService;

        int threadsCounter = Math.max(1, Math.min(runOrder.size(), maxThreads));
        int partSize = runOrder.size() / threadsCounter;
        int restCounter = runOrder.size() % threadsCounter;

        final List<List<Processor<T>>> partitions = new ArrayList<>();
        for (int i = 0, l = 0; i < threadsCounter; i++) {
            int r = l + partSize + (restCounter-- >= 1 ? 1 : 0);
            partitions.add(runOrder.subList(l, r));
            l = r;
        }
        for (int i = 0; i < threadsCounter; i++) {
            workers.add(new ProcessorThread<>(partitions.get(i), dataStore, maxIterations));
        }

        //dataStore.setProcessorThreads(workers);

        for (int i = 0; i < threadsCounter; i++) {
            workers.get(i).start();
        }


        for (int i = 0; i < threadsCounter; i++) {
            workers.get(i).join();
        }

        return results;
    }


}