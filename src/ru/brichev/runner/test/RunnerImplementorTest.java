package ru.brichev.runner.test;

import org.junit.Assert;
import org.junit.Test;
import ru.brichev.runner.implementors.RunnerImplementor;
import ru.brichev.runner.interfaces.Processor;
import ru.brichev.runner.models.ProcessorException;
import ru.brichev.runner.test.implementors.ProcessorImplementor;

import java.util.*;


public class RunnerImplementorTest {

    @Test
    public void threadsTest() {

        Map<String, List<Integer>> expected = new HashMap<>();
        for (int i = 1; i < 5; i++) {
            expected.put(String.valueOf(i), new ArrayList<>());
        }
        expected.get("1").add(2);
        expected.get("1").add(5);
        expected.get("1").add(9);
        expected.get("1").add(14);
        expected.get("1").add(20);

        expected.get("2").add(6);
        expected.get("2").add(19);
        expected.get("2").add(41);
        expected.get("2").add(74);
        expected.get("2").add(120);

        expected.get("3").add(2);
        expected.get("3").add(5);
        expected.get("3").add(9);
        expected.get("3").add(14);
        expected.get("3").add(20);

        expected.get("4").add(1);
        expected.get("4").add(2);
        expected.get("4").add(3);
        expected.get("4").add(4);
        expected.get("4").add(5);

        Map<String, List<Integer>> results;
        try {

            for (int i = 1; i < 20; i++) {

                List<String> list1 = new ArrayList<>();
                list1.add("4");
                List<String> list2 = new ArrayList<>();
                list2.add("3");
                list2.add("1");
                list2.add("4");
                List<String> list3 = new ArrayList<>();
                list3.add("4");
                List<String> list4 = new ArrayList<>();

                ProcessorImplementor processorImplementor1 = new ProcessorImplementor("1", list1);

                ProcessorImplementor processorImplementor2 = new ProcessorImplementor("2", list2);

                ProcessorImplementor processorImplementor3 = new ProcessorImplementor("3", list3);

                ProcessorImplementor processorImplementor4 = new ProcessorImplementor("4", list4);

                RunnerImplementor<Integer> runnerImplementor = new RunnerImplementor<>();


                Set<Processor<Integer>> set = new HashSet<>();
                set.add(processorImplementor1);
                set.add(processorImplementor2);
                set.add(processorImplementor3);
                set.add(processorImplementor4);

                results = runnerImplementor.runProcessors(set, i, 5);

                Assert.assertEquals(expected, results);

            }
        } catch (ProcessorException e) {
            System.out.println("Run thrown: " + e.getMessage());
        }
    }

    @Test
    public void cycleTest() {

        try {

            for (int i = 1; i < 20; i++) {
                List<String> list1 = new ArrayList<>();
                list1.add("4");
                List<String> list2 = new ArrayList<>();
                list2.add("3");
                list2.add("1");
                list2.add("4");
                List<String> list3 = new ArrayList<>();
                list3.add("2");
                List<String> list4 = new ArrayList<>();

                ProcessorImplementor processorImplementor1 = new ProcessorImplementor("1", list1);

                ProcessorImplementor processorImplementor2 = new ProcessorImplementor("2", list2);

                ProcessorImplementor processorImplementor3 = new ProcessorImplementor("3", list3);

                ProcessorImplementor processorImplementor4 = new ProcessorImplementor("4", list4);

                RunnerImplementor<Integer> runnerImplementor = new RunnerImplementor<>();


                Set<Processor<Integer>> set = new HashSet<>();
                set.add(processorImplementor1);
                set.add(processorImplementor2);
                set.add(processorImplementor3);
                set.add(processorImplementor4);

                runnerImplementor.runProcessors(set, i, 5);

            }
        } catch (ProcessorException e) {
            System.out.println("Run thrown: " + e.getMessage());
        }
    }

    @Test
    public void invalidInputIdTest() {

        try {

            for (int i = 1; i < 20; i++) {
                List<String> list1 = new ArrayList<>();
                list1.add("4");
                List<String> list2 = new ArrayList<>();
                list2.add("3");
                list2.add("1");
                list2.add("4");
                List<String> list3 = new ArrayList<>();
                list3.add("99999");
                List<String> list4 = new ArrayList<>();

                ProcessorImplementor processorImplementor1 = new ProcessorImplementor("1", list1);

                ProcessorImplementor processorImplementor2 = new ProcessorImplementor("2", list2);

                ProcessorImplementor processorImplementor3 = new ProcessorImplementor("3", list3);

                ProcessorImplementor processorImplementor4 = new ProcessorImplementor("4", list4);

                RunnerImplementor<Integer> runnerImplementor = new RunnerImplementor<>();


                Set<Processor<Integer>> set = new HashSet<>();
                set.add(processorImplementor1);
                set.add(processorImplementor2);
                set.add(processorImplementor3);
                set.add(processorImplementor4);

                runnerImplementor.runProcessors(set, i, 5);

            }
        } catch (ProcessorException e) {
            System.out.println("Run thrown: " + e.getMessage());
        }
    }

    @Test
    public void nullTest() {

        Map<String, List<Integer>> expected = new HashMap<>();
        for (int i = 1; i < 5; i++) {
            expected.put(String.valueOf(i), new ArrayList<>());
        }
        expected.get("1").add(2);
        expected.get("1").add(5);
        expected.get("1").add(9);


        expected.get("2").add(5);
        expected.get("2").add(15);
        expected.get("2").add(31);


        expected.get("3").add(1);
        expected.get("3").add(2);
        expected.get("3").add(3);


        expected.get("4").add(1);
        expected.get("4").add(2);
        expected.get("4").add(3);


        Map<String, List<Integer>> results;

        try {

            for (int i = 1; i < 20; i++) {
                List<String> list1 = new ArrayList<>();
                list1.add("4");
                List<String> list2 = new ArrayList<>();
                list2.add("3");
                list2.add("1");
                list2.add("4");
                List<String> list3 = new ArrayList<>();
                List<String> list4 = new ArrayList<>();

                ProcessorImplementor processorImplementor1 = new ProcessorImplementor("1", list1);

                ProcessorImplementor processorImplementor2 = new ProcessorImplementor("2", list2);

                ProcessorImplementor processorImplementor3 = new ProcessorImplementor("3", list3);

                ProcessorImplementor processorImplementor4 = new ProcessorImplementor("4", list4);

                RunnerImplementor<Integer> runnerImplementor = new RunnerImplementor<>();


                Set<Processor<Integer>> set = new HashSet<>();
                set.add(processorImplementor1);
                set.add(processorImplementor2);
                set.add(processorImplementor3);
                set.add(processorImplementor4);

                results = runnerImplementor.runProcessors(set, i, 5);
                Assert.assertEquals(expected, results);
            }
        } catch (ProcessorException e) {
            System.out.println("Run thrown: " + e.getMessage());
        }
    }

    @Test
    public void processorExceptionTest() {

        Map<String, List<Integer>> expected = new HashMap<>();
        for (int i = 1; i < 5; i++) {
            expected.put(String.valueOf(i), new ArrayList<>());
        }
        expected.get("1").add(2);
        expected.get("1").add(5);
        expected.get("1").add(9);


        expected.get("2").add(5);
        expected.get("2").add(15);
        expected.get("2").add(31);


        expected.get("3").add(1);
        expected.get("3").add(2);
        expected.get("3").add(3);


        expected.get("4").add(1);
        expected.get("4").add(2);
        expected.get("4").add(3);


        Map<String, List<Integer>> results;

        try {

            for (int i = 1; i < 20; i++) {
                List<String> list1 = new ArrayList<>();
                list1.add("4");
                List<String> list2 = new ArrayList<>();
                list2.add("3");
                list2.add("4");
                List<String> list3 = new ArrayList<>();
                list3.add("1");
                List<String> list4 = new ArrayList<>();

                ProcessorImplementor processorImplementor1 = new ProcessorImplementor("1", list1);

                ProcessorImplementor processorImplementor2 = new ProcessorImplementor("2", list2);

                ProcessorImplementor processorImplementor3 = new ProcessorImplementor("3", list3);

                ProcessorImplementor processorImplementor4 = new ProcessorImplementor("4", list4);

                RunnerImplementor<Integer> runnerImplementor = new RunnerImplementor<>();


                Set<Processor<Integer>> set = new HashSet<>();
                set.add(processorImplementor1);
                set.add(processorImplementor2);
                set.add(processorImplementor3);
                set.add(processorImplementor4);

                results = runnerImplementor.runProcessors(set, i, 5);

                Assert.assertEquals(expected, results);

            }
        } catch (ProcessorException e) {
            System.out.println("Run thrown: " + e.getMessage());
        }
    }


}