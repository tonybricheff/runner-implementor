package ru.brichev.runner.implementors;

import ru.brichev.runner.interfaces.Runner;
import ru.brichev.runner.modules.ProcessorException;

import java.util.*;

public class RunnerImplementor implements Runner {

    @Override
    public Map<String, List> runProcessors(Set processors, int maxThreads, int maxIterations) throws ProcessorException {

        for (int i = 0; i < maxIterations; i++) {

        }

        return null;
    }



}
