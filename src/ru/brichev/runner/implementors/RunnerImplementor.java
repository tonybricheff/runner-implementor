package ru.brichev.runner.implementors;

import ru.brichev.runner.interfaces.Processor;
import ru.brichev.runner.interfaces.Runner;
import ru.brichev.runner.models.DataStore;
import ru.brichev.runner.models.DependencyGraph;
import ru.brichev.runner.models.ProcessorException;
import ru.brichev.runner.models.ProcessorThread;

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



        int partSize = runOrder.size() / threadsCounter;
        int restCounter = runOrder.size() % threadsCounter;

        final List<List<Processor<T>>> partitions = new ArrayList<>();
        for (int i = 0, l = 0; i < threadsCounter; i++) {
            int r = l + partSize + (restCounter-- >= 1 ? 1 : 0);
            partitions.add(runOrder.subList(l, r));
            l = r;
        }

        DataStore<String> dataStore = new DataStore<>();
        List<ProcessorThread> workers = new ArrayList<>();
        final List<T> results = new ArrayList<>(Collections.nCopies(threadsCounter, null));
        for (int i = 0; i < threadsCounter; i++) {
            final int index = i;
            workers.add(new ProcessorThread(partitions.get(index).get(0).getId(),partitions.get(index).get(0).getInputIds(), dataStore)); //решить вопрос с потоками(если меньше)
            workers.get(i).start();
        }

        for (int i = 0; i < threadsCounter; i++) {
            workers.get(i).join();
        }


    }

}
