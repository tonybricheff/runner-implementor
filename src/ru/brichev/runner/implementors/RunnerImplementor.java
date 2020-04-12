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
        DependencyGraph dependencyGraph = new DependencyGraph(processors);
        dependencyGraph.printGraph();
        System.out.println(dependencyGraph.getProcessorsOrder());

        /*for (int i = 0; i < maxIterations; i++) {
            parallelWork(maxThreads, processors, results);
        }
         */

        return null;
    }

    private void parallelWork(int threads, Set<Processor> processorsSet, Map<String, List> resultsMap) throws InterruptedException {

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
