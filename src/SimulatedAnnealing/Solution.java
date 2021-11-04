package SimulatedAnnealing;

import java.util.ArrayList;
import java.util.Random;

import distanceMatrix.DistanceMatrix;

public class Solution {

    private ArrayList<String> path;
    private int cost;
    private int nIteration;
    private double temp;

    public Solution(ArrayList<String> path, int cost, int nIteration, double temp) {
        this.path = path;
        this.cost = cost;
        this.nIteration = nIteration;
        this.temp = temp;
    }

    public Solution(Solution solution) {
        this(solution.path, solution.cost, solution.nIteration, solution.temp);
    }

    //=========================================================================

    public ArrayList<String> getPath() {
        return path;
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

    //=========================================================================

    protected void nextSolution(DistanceMatrix map, double temp) {
        int[] indices = randIndices();

        ArrayList<String> aux = new ArrayList<>();

        int i;
        for(i = 0; i <= indices[0]; i++) {
            aux.add(path.get(i));
        }

        for(i = 0; i < (indices[1] - indices[0]); i++) {
            aux.add(path.get(indices[1] - i));
        }

        for(i = 0; i < (path.size() - indices[1] - 1); i++) {
            aux.add(path.get(indices[1] + i + 1));
        }

        this.path = aux;
        this.cost = this.cost + calculateDelta(map, indices[0], indices[1]);
        this.nIteration++;
        this.temp = temp; 
    }

    //=========================================================================

    protected void setSolution(Solution solution) {
        this.path = solution.path;
        this.cost = solution.cost;
        this.nIteration = solution.nIteration;
        this.temp = solution.temp;
    }

    //=========================================================================

    private int[] randIndices() {
        Random rand = new Random();

        int i1 = rand.nextInt(path.size()),
            i2 = rand.nextInt(path.size());

        while(i1 == i2) {
            i2 = rand.nextInt(path.size());
        }

        if(i1 < i2)
            return new int[] {i1, i2};
        else
            return new int[] {i2, i1};
    }

    private int calculateDelta(DistanceMatrix map, int index1, int index2) {
        String i = path.get(index1),
                j = path.get(index2),
                ip = path.get(index1 + 1),
                jp;

        if(index2 == path.size() - 1)
            jp = path.get(0);
        else
            jp = path.get(index2 + 1);

        return (map.distance(i, j) +
                map.distance(ip, jp) -
                map.distance(i, ip) -
                map.distance(j, jp));
    }
}
