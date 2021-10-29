package SimulatedAnnealing;
import distanceMatrix.*;

public class SimulatedAnnealing {

    private static final double CERTAINTY = 0.95;

    private static final double ALPHA = 0.9; // Uses high values [0.8 - 0.99] as proven effective through practical testing.
    private static final double BETA = 0.01; // small enough value
    private static final double ACCEPTED_MOVES_FLOOR = 0.10; // % of moves accepted [NOT CHECKED]

    private DistanceMatrix map;
    private double initialTemperature; // need to be calculated
    private int numIter; // total num of iterations
    private int varNumIter; // variable num of iterations per temperature
    private int maxIter; // max iterations allowed TODO not sure best way to set number

    // TODO num iter per temp (n_iter*k, k slightly bigger than 1 || fixed num)1

    public SimulatedAnnealing(DistanceMatrix map) {
        this.map = map;
    }

    public Solution runAlgorithm() {
        return null;
    }

    // INITIAL TEMP CALC

    /**
     * TODO (calc in matrix)
     * Init temp = max distance between cities
     */
    public void setInitTempDMax() {
        this.initialTemperature = - map.getMaxDistance() / Math.log(CERTAINTY);

        System.out.println(initialTemperature);
    }

    /**
     * TODO
     * start with a low enough temperature
     * then gradually increase
     * until neighbour acceptance is good enough
     */
    public void initTempProportional() {

    }

    // TEMP VARIATION OPTIONS

    /**
     * Geometric variation of temperature
     * 
     * @param temperature T(k-1)
     * @return new temperature
     */
    public double geometricDecay(double temperature) {
        return ALPHA*temperature;
    }

    /**
     * Slow and gradual decay of temperature
     * 
     * @param temperature T(k-1)
     * @return new temperature
     */
    public double slowDecay(double temperature) {
        return temperature/(1+BETA*temperature);
    }

    // STOP CONDITIONS

    /**
     * if accepted proportion falls under ACCEPTED_MOVES_FLOOR
     * algorithm should stop
     * @return true to stop it
     */
    public boolean stopCriteriaProp(double prop) {
        // TODO prop, n possible/n currently possible
        return prop <= ACCEPTED_MOVES_FLOOR;
    }

    /**
     * Stops algorithm if max num of iterations
     * 
     * @return true if reached
     */
    public boolean stopCriteriaMaxIt() {
        // TODO maxIter not calculated yet
        return numIter >= maxIter;
    }


}
