import SimulatedAnnealing.SimulatedAnnealing;
import distanceMatrix.DistanceMatrix;

public class Main {

    public static void main(String[] args) {
        DistanceMatrix map = new DistanceMatrix("/home/manuelp/Documents/university/IA/simulated-annealing/exMatrix.txt");

        System.out.println(map.getMaxDistance());

        SimulatedAnnealing sa = new SimulatedAnnealing(map);

        sa.setInitTempDMax();
    }
}
