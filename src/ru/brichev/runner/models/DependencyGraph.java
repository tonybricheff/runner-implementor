package ru.brichev.runner.models;

import ru.brichev.runner.interfaces.Processor;

import java.util.*;

public class DependencyGraph<T> {
    private final int numVertices;
    private LinkedList<Integer>[] adjLists;
    private boolean cycle;
    private final int[] used;
    private final List<Processor<T>> topsorted;
    private Map<String, Processor<T>> processorIds;

    public DependencyGraph(Set<Processor<T>> processors) throws ProcessorException {
        this.numVertices = processors.size();
        this.used = new int[numVertices];
        this.topsorted = new ArrayList<>();
        resize(numVertices);
        for (Processor<T> processor : processors) {
            processorIds.put(processor.getId(), processor);
            for (String inputId : processor.getInputIds()) {
                if (Integer.parseInt(inputId) > numVertices || Integer.parseInt(inputId) < 1) {
                    throw new ProcessorException("Invalid inputId detected: " + inputId, Integer.parseInt(inputId));
                } else {
                    addEdge(Integer.parseInt(inputId) - 1, Integer.parseInt(processor.getId()) - 1);
                }
            }
        }
    }

    private void resize(int n) {
        adjLists = new LinkedList[n];
        processorIds = new HashMap<>(n);
        for (int i = 0; i < numVertices; i++)
            adjLists[i] = new LinkedList<>();
    }

    private void addEdge(Integer from, Integer to) {
        adjLists[from].add(to);
    }

    private void dfs(int v) {
        used[v] = 1;
        for (int i = 0; i < adjLists[v].size(); i++) {
            int next = adjLists[v].get(i);
            if (used[next] == 0)
                dfs(next);
            if (used[next] == 1)
                cycle = true;
        }
        used[v] = 2;
        topsorted.add(processorIds.get(String.valueOf(v + 1)));
    }

    private void topsort() {
        Arrays.fill(used, 0);
        topsorted.clear();
        for (int i = 0; i < numVertices; i++)
            if (used[i] == 0)
                dfs(i);
        Collections.reverse(topsorted);

    }

    public List<Processor<T>> getProcessorsOrder() throws ProcessorException {
        topsort();
        if (cycle) {
            throw new ProcessorException("Cycle detected", 0);
        }

        return topsorted;
    }

    public int size(int v) {
        return adjLists[v].size();
    }

    public Integer get(int from, int to) {
        return adjLists[from].get(to);
    }

    public void printGraph() {
        for (int i = 0; i < numVertices; i++) {
            System.out.print(i + 1 + " : ");
            for (int j = 0; j < adjLists[i].size(); j++) {
                System.out.print(adjLists[i].get(j) + 1 + " ");
            }
            System.out.println();
        }
    }

}
