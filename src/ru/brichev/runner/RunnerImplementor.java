package ru.brichev.runner;

import ru.brichev.runner.interfaces.Runner;
import ru.brichev.runner.modules.ProcessorException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RunnerImplementor implements Runner {

    @Override
    public Map<String, List> runProcessors(Set set, int maxThreads, int maxIterations) throws ProcessorException {
        return null;
    }


}
