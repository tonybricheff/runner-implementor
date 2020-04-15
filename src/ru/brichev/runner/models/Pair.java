package ru.brichev.runner.models;

//Pair of id and number of iteration which is used as a key in Hash Map
public class Pair {
    private final String id;
    private final Integer iteration;

    public Pair(String id, Integer iteration) {
        this.id = id;
        this.iteration = iteration;
    }

    //Override of equals and hashcode to use Pair as a key in Hash map
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair pair = (Pair) o;
        boolean equals = id.equals(pair.id);
        if (iteration != null) {
            equals = equals && iteration.equals(pair.iteration);
        } else {
            equals = equals && pair.iteration == null;
        }
        return equals;
    }

    @Override
    public int hashCode() {
        int hashcode = Integer.parseInt(id);
        hashcode = 31 * hashcode + iteration.hashCode();
        return hashcode;
    }
}