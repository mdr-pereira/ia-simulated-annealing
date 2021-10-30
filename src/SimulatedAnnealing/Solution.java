package SimulatedAnnealing;

import java.util.LinkedList;

public class Solution {

    private LinkedList<String> solution;
    private int cost;
    private int nIteration;
    private double temp;
    // TODO should receive "valor da função" (?)

    public Solution(LinkedList<String> solution, int cost, int nIteration, double temp) {
        this.solution = solution;
        this.cost = cost;
        this.nIteration = nIteration;
        this.temp = temp;
    }

    public LinkedList<String> getSolution() {
        return solution;
    }

    public int getCost() {
        return cost;
    }

    public int getNIteration() {
        return nIteration;
    }

    public double getTemp() {
        return temp;
    }
}
