package ru.brichev.runner.models;

import ru.brichev.runner.implementors.ProcessorImplementor;

import java.util.*;

public class DependencyGraph {
    private final int numVertices;
    private LinkedList<Integer>[] adjLists;
    private boolean cycle;
    private final int[] used;
    private final List<Integer> topsorted;

    public DependencyGraph(Set<ProcessorImplementor> processors) throws ProcessorException {
        this.numVertices = processors.size();
        this.used = new int[numVertices];
        this.topsorted = new ArrayList<>();
        resize(numVertices);
        for (ProcessorImplementor processor : processors) {
            for (String inputId : processor.getInputIds()) {
                if (Integer.parseInt(inputId) > numVertices || Integer.parseInt(inputId) < 1) {
                    throw new ProcessorException("Invalid inputId detected: " + inputId, Integer.parseInt(inputId));
                } else {
                    addEdge(Integer.parseInt(inputId) - 1, processor.getId() - 1);
                }
            }
        }
    }

    public void resize(int n) {
        adjLists = new LinkedList[n];
        for (int i = 0; i < numVertices; i++)
            adjLists[i] = new LinkedList<>();
    }

    public void addEdge(Integer from, Integer to) {
        adjLists[from].add(to);
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
        topsorted.add(v + 1);
    }

    private void topsort() {
        Arrays.fill(used, 0);
        topsorted.clear();
        for (int i = 0; i < numVertices; i++)
            if (used[i] == 0)
                dfs(i);
        Collections.reverse(topsorted);

    }

    public List<Integer> getProcessorsOrder() throws ProcessorException {
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

}
