import Map.DistanceMatrix;

public class Main {
    public static void main(String[] args) {
        DistanceMatrix dm = new DistanceMatrix("/home/manuel-pereira/Documents/university/IA/simulated-annealing/exMatrix.txt");

        dm.showCities();
    }    
}
