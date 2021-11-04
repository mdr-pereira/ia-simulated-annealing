import java.util.ArrayList;
import java.util.HashMap;

import SimulatedAnnealing.*;
import distanceMatrix.DistanceMatrix;

public class Main {

    public static void main(String[] args) {
        DistanceMatrix map = new DistanceMatrix("/home/manuel-pereira/Documents/university/IA/simulated-annealing/exMatrix.txt");

        SimulatedAnnealingClass alg = new SimulatedAnnealingClass(map);

        ArrayList<String> E1 = new ArrayList<>();
        E1.add("Atroeira");
        E1.add("Douro");
        E1.add("Pinhal");
        E1.add("Teixoso");
        E1.add("Ulgueira");
        E1.add("Vilar");

        alg.runAlgorithm(E1);

        HashMap<String, Solution> solutions = new HashMap<>();
        solutions.put("initial", alg.getInitialSolution());
        solutions.put("best", alg.getBestSolution());
        solutions.put("worst", alg.getWorstSolution());
        solutions.put("last", alg.getLastSolution());
        
        //=====================================================================

        System.out.println("\nAlgorithm run stats:");
        System.out.println("- Runtime: " +  alg.getRunTime());
        System.out.println("- Total iterations: " + alg.getTotalIt());
        System.out.println();

        for(String solutionName: solutions.keySet()) {
            Solution solution = solutions.get(solutionName);

            System.out.println();
            System.out.println(solutionName.toUpperCase());
            System.out.println("\n- Iterations number: " + solution.getNIteration());
            System.out.println("- Temperature: " + solution.getTemp());
            System.out.println("- Solution: " + solution.getPath());
            System.out.println("- Cost " + solution.getCost());

        }
    }
}
