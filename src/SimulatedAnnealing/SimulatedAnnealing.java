package SimulatedAnnealing;
import distanceMatrix.*;

public class SimulatedAnnealing {

    private static final double CERTAINTY = 0.95;
    private static final double ALPHA = 0.9; // Uses high values [0.8 - 0.99] as proven effective through practical testing. [NOT CHECKED]
    private static final double BETA = 0.01; // small enough value [NOT CHECKED]
    private static final double ACCEPTED_MOVES_FLOOR = 0.10; // % of moves accepted [NOT CHECKED]
    private static final double K = 1.05; // iterations per temperature *K variation [NOT CHECKED]
    private static final int N = 5; // iterations per temperature *K variation [NOT CHECKED]

    private DistanceMatrix map;
    private double initialTemperature;
    private double currentTemperature;
    private int totalIter; // total num of iterations
    private int varNumIter; // variable num of iterations per temperature
    private int maxIter; // max iterations allowed
    private Solution bestSolution;
    private Solution worstSolution;
    private Solution initialSolution;
    private Solution currentSolution;
    private long runTime; // exec time for algorithm
    private long bestTime; // time needed to find best solution
    private double deltaE;

    // ALGORITHM

    public SimulatedAnnealing(DistanceMatrix map) { // TODO != constructors ?
        this.map = map;
        initialTemperature = initTempDMax(); //initTempProportional();
        currentTemperature = initialTemperature;
        totalIter = 0;
        varNumIter = initIterNum();
        maxIter = setMaxIter();
        initialSolution = initialSolution(); // TODO here?
        currentSolution = initialSolution;
        bestSolution = initialSolution;
        worstSolution = initialSolution;
        runTime = 0;
        deltaE = 0; // TODO needed?
    }

    // ALGORITHM

    // TODO
    private Solution initialSolution() {
        return null;
    }

    public void runAlgorithm() {
        //start timer
        long startAlg = System.nanoTime();

        //get new solution
        while(stopCriteriaMaxIt() && currentTemperature > 0) { // TODO !stopCriteriaProb(prob not calc)

            // get current solution and time
            currentSolution = getSolution();
            long lastStop = System.nanoTime();

            // check for new stats
            if (currentSolution.getCost() > worstSolution.getCost())
                worstSolution = currentSolution;
            if (currentSolution.getCost() < bestSolution.getCost()) {
                bestSolution = currentSolution;
                bestTime = lastStop;
            }

            // get new temp and varNumIter
            varNumIter = itPerTemperatureK(varNumIter); //itPerTemperatureN(varNumIter)
            currentTemperature = geometricDecay(currentTemperature); //slowDecay(currentTemperature)
        }

        // stop timer and calc runtime
        long endAlg = System.nanoTime();
        runTime = endAlg - startAlg;
    }

    // TODO
    private Solution getSolution() {
        return null;
    }

    // ACCEPTING PROB

    private boolean acceptingProbability(double deltaE) {
        return Math.exp(deltaE/currentTemperature) > ACCEPTED_MOVES_FLOOR;
    }


    // TODO calc initial num of iterations
    private int initIterNum() {
        return 0;
    }

    /**
     * past num iterations times K constant
     * @param nIter past num iterations
     * @return new num iterations for new temp
     */
    private int itPerTemperatureK(int nIter) {
        return (int) (nIter*K);
    }

    /**
     * past num iterations plus N constant
     * @param nIter past num iterations
     * @return new num iterations for new temp
     */
    private int itPerTemperatureN(int nIter) {
        return nIter+N;
    }


    // INITIAL TEMP CALC

    /**
     * Init temp set around max distance between cities
     * @return initial temperature
     */
    private double initTempDMax() {
        return (- map.getMaxDistance() / Math.log(CERTAINTY));
    }

    /**
     * TODO
     * start with a low enough temperature
     * then gradually increase
     * until neighbour acceptance is good enough
     */
    private void initTempProportional() {

    }

    // TEMP VARIATION OPTIONS

    /**
     * Geometric variation of temperature
     * 
     * @param temperature T(k-1)
     * @return new temperature
     */
    private double geometricDecay(double temperature) {
        return ALPHA*temperature;
    }

    /**
     * Slow and gradual decay of temperature
     * 
     * @param temperature T(k-1)
     * @return new temperature
     */
    private double slowDecay(double temperature) {
        return temperature/(1+BETA*temperature);
    }

    // STOP CONDITIONS

    /**
     * if accepted proportion falls under ACCEPTED_MOVES_FLOOR
     * algorithm should stop
     * @param prob  n possible/n currently possible
     * @return true to stop it
     */
    private boolean stopCriteriaProb(double prob) {
        // TODO prob, proportion of accepted moves
        return prob <= ACCEPTED_MOVES_FLOOR;
    }

    /**
     * Stops algorithm if max num of iterations
     * 
     * @return true if reached
     */
    private boolean stopCriteriaMaxIt() {
        // TODO maxIter not calculated yet
        return totalIter >= maxIter;
    }

    // TODO
    private int setMaxIter() {
        return 0;
    }

    // RESULTS

    /**
     * Used to then get solution stats (it where calc, temp exec, cities list)
     * @return initialSolution
     */
    public Solution getInitialSolution() {
        return initialSolution;
    }

    /**
     * Used to then get solution stats (it where calc, temp exec, cities list)
     * @return currentSolution
     */
    public Solution getLastSolution() {
        return currentSolution;
    }

    /**
     * Used to then get solution stats (it where calc, temp exec, cities list)
     * @return bestSolution
     */
    public Solution getBestSolution() {
        return bestSolution;
    }

    /**
     * Used to then get solution stats (it where calc, temp exec, cities list)
     * @return worstSolution
     */
    public Solution getWorstSolution() {
        return worstSolution;
    }

    /**
     * @return runTime,  alg runTime
     */
    public long getRunTime() {
        return runTime;
    }

    /**
     * @return totalIter, total iterations
     */
    public int getTotalIt() {
        return totalIter;
    }
}
