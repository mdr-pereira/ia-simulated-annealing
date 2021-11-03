import SimulatedAnnealing.*;
import distanceMatrix.DistanceMatrix;

public class Main {

    public static void main(String[] args) {
        DistanceMatrix map = new DistanceMatrix("/home/manuelp/Documents/university/IA/simulated-annealing/exMatrix.txt");

        // System.out.println(map.getMaxDistance());

        SimulatedAnnealing sa = new SimulatedAnnealing(map);

        sa.runAlgorithm();
        Solution best = sa.getBestSolution();
        Solution worst = sa.getWorstSolution();
        Solution initial = sa.getInitialSolution();
        Solution last = sa.getLastSolution();
        long runtime = sa.getRunTime();
        int totalIt = sa.getTotalIt();


        System.out.println("RESULTS:");
        System.out.println();

        System.out.println("Algorithm run stats:");
        System.out.println("- Runtime: " + runtime);
        System.out.println("- Total iterations: " + totalIt);
        System.out.println();

        System.out.println("Initial Solution:");
        System.out.println("- Iterations number: " + best.getNIteration());
        System.out.println("- Temperature: " + best.getTemp());
        System.out.println("- Solution: ");
        // TODO ITERATION

        //TODO REST
        
        //sa.setInitTempDMax();
    }
}
