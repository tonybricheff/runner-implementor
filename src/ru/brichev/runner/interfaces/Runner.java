package ru.brichev.runner.interfaces;

import ru.brichev.runner.models.ProcessorException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface Runner<T> {

    /**
     * Runs a set of interdependent processors many times until null is produced by any of them
     *
     * @param maxThreads    - maximum number of threads to be used
     * @param maxIterations - maximum number of iterations to run
     * @param processors    - a set of processors
     * @return a map, where the key is a processor id, and the value is a list of its outputs in the order of iterations
     * @throws ProcessorException if a processor throws an exception, loops detected, or some input ids not found
     */
    Map<String, List<T>> runProcessors(Set<Processor<T>> processors, int maxThreads, int maxIterations) throws ProcessorException, InterruptedException, ExecutionException;

}