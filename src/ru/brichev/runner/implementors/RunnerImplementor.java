package ru.brichev.runner.implementors;

import ru.brichev.runner.interfaces.Processor;
import ru.brichev.runner.interfaces.Runner;
import ru.brichev.runner.models.DataStore;
import ru.brichev.runner.models.DependencyGraph;
import ru.brichev.runner.models.ProcessorException;
import ru.brichev.runner.models.ProcessorThread;

import java.util.*;
import java.util.concurrent.*;


public class RunnerImplementor<T> implements Runner<T> {


    @Override
    public Map<String, List<T>> runProcessors(Set<Processor<T>> processors, int maxThreads, int maxIterations) throws
            ProcessorException {

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

        int threadsCounter = Math.max(1, Math.min(runOrder.size(), maxThreads));
        int partSize = runOrder.size() / threadsCounter;
        int restCounter = runOrder.size() % threadsCounter;

        final List<List<Processor<T>>> partitions = new ArrayList<>();
        for (int i = 0, l = 0; i < threadsCounter; i++) {
            int r = l + partSize + (restCounter-- >= 1 ? 1 : 0);
            partitions.add(runOrder.subList(l, r));
            l = r;
        }

        ExecutorService executorService = Executors.newFixedThreadPool(threadsCounter);

        List<Future<T>> futures = new ArrayList<>();

        try {
            for (int i = 0; i < threadsCounter; i++) {
                futures.add(executorService.submit(new ProcessorThread<>(partitions.get(i), dataStore, maxIterations)));
            }

            executorService.shutdown();

            try {
                for (Future<T> future : futures) {
                    future.get();
                }
            } catch (InterruptedException ignored) {
            }

            return validateResult(results, maxIterations);
        } catch (ExecutionException e) {
            System.out.println(executorService.isShutdown());
            throw new ProcessorException("Processor Exception", "");
        }
    }


    private Map<String, List<T>> validateResult(Map<String, List<T>> results, Integer iterations) {

        Map<String, List<T>> resultOfValidation = new HashMap<>();
        boolean isNull = false;

        for (int currentIteration = 0; currentIteration < iterations; currentIteration++) {
            for (Map.Entry<String, List<T>> entry : results.entrySet()) {
                T processorResult = entry.getValue().get(currentIteration);

                if (processorResult == null) {
                    if (currentIteration != 0) {
                        isNull = true;
                    } else {
                        return new HashMap<>();
                    }
                }
            }

            if (!isNull) {
                for (Map.Entry<String, List<T>> entry : results.entrySet()) {
                    T processorResult = entry.getValue().get(currentIteration);
                    if (!resultOfValidation.containsKey(entry.getKey())) {
                        resultOfValidation.put(entry.getKey(), new ArrayList<>());
                    }
                    resultOfValidation.get(entry.getKey()).add(processorResult);
                }
            }

        }
        return resultOfValidation;
    }

}


