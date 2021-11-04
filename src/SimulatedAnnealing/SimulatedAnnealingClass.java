package SimulatedAnnealing;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import distanceMatrix.*;

public class SimulatedAnnealingClass implements SimulatedAnnealing {

    private static final double CERTAINTY = 0.90;
    private static final double ALPHA = 0.80; // Uses high values [0.8 - 0.99] as proven effective through practical testing.
    private static final double BETA = 0.01; // small enough value [NOT CHECKED]
    private static final double ACCEPTED_MOVES_FLOOR = 0.05; // % of moves accepted [NOT CHECKED]
    private static final double K = 1.01; // iterations per temperature *K variation [NOT CHECKED]
    private static final int N = 5; // iterations per temperature *K variation [NOT CHECKED]

    private DistanceMatrix map;

    private Solution initialSolution;
    private Solution finalSolution;
    private Solution bestSolution;
    private Solution worstSolution;

    private double initialTemp;

    private int totalIter; // total num of iterations
    private int maxIter; // max iterations allowed

    private long runTime; // exec time for algorithm

    public SimulatedAnnealingClass(DistanceMatrix map) {
        this(map, 1000000);
    }

    public SimulatedAnnealingClass(DistanceMatrix map, int maxIter) {
        this.map = map;
        initialTemp = initTempDMax(); //initTempProportional();
        totalIter = 0;
        runTime = 0;

        this.maxIter = maxIter;
    }

    //=========================================================================
    // ALGORITHM

    /**
     * Creates an initial solution through random shuffling.
     * 
     * @return - randomized initial solution
     */
    private Solution initialSolution(ArrayList<String> cities) {
        ArrayList<String> aux = new ArrayList<>(cities);
        
        Collections.shuffle(aux);

        int size = aux.size(),
            cost = 0;
        for(int i = 0; i < size - 1; i++) {
            cost += map.distance(aux.get(i), aux.get(i + 1));
        }   

        //Calculate distance between the last and first index, simbolizing a closed loop.
        cost += map.distance(aux.get(0), aux.get(size - 1));

        return new Solution(aux, cost, totalIter, initTempDMax());
    }

    public void runAlgorithm() {
        runAlgorithm(map.getCities());
    }

    public void runAlgorithm(ArrayList<String> cities) {
        Solution initial = initialSolution(cities),
                current = new Solution(initial),
                next = new Solution(initial),
                best = new Solution(initial),
                worst = new Solution(initial);

        int iterations = 0,
            maxIterationsAtTemp = initIterNum(),
            iterationsAtTemp = 0;

        double currentTemp = initialTemp;

        //Start timer
        long startAlg = System.nanoTime(),
                endAlg = 0l; 

        int acceptedMoves = 0;

        //Get new solution
        while(!stopCriteriaMaxIt(iterations) && currentTemp > 0) { // TODO !stopCriteriaProb(prob not calc)
            
            //Get current solution and time
            next.nextSolution(map, currentTemp);

            //Set new metrics, and check for solution viability
            if(next.getCost() < current.getCost()) {

                if (next.getCost() < best.getCost()) {
                    best.setSolution(next);
                }

                current.setSolution(next);
                acceptedMoves++;
            }
            else {
                if (next.getCost() > worst.getCost())
                    worst.setSolution(next);

                if(acceptingProbability(currentTemp, next.getCost() - current.getCost())) {
                    current.setSolution(next);
                    acceptedMoves++;
                }
            }

            iterations++;
            iterationsAtTemp++;

            if(!stopCriteriaProb(iterations, acceptedMoves))
                break;

            if(iterationsAtTemp == maxIterationsAtTemp) {
                iterationsAtTemp = 0;
                // get new temp and varNumIter
                maxIterationsAtTemp = itPerTemperatureK(iterationsAtTemp); //itPerTemperatureN(varNumIter)
                currentTemp = geometricDecay(currentTemp); //slowDecay(currentTemperature)
            }
        }

        // stop timer and calc runtime
        endAlg = System.nanoTime();
        runTime = endAlg - startAlg;

        this.totalIter += iterations;

        this.initialSolution = initial;
        this.finalSolution = next;
        this.bestSolution = best;
        this.worstSolution = worst;
    }

    //=========================================================================
    // ACCEPTING PROB

    private boolean acceptingProbability(double currentTemp, double deltaE) {
        return Math.exp(-deltaE / currentTemp) >= new Random().nextDouble();
    }


    // TODO calc initial num of iterations
    private int initIterNum() {
        int size = map.getCities().size();
        return  (size - 1)/ 2 ;
    }

    /**
     * past num iterations times K constant
     * @param nIter past num iterations
     * @return new num iterations for new temp
     */
    private int itPerTemperatureK(int nIter) {
        return (int) (nIter * K);
    }

    /**
     * past num iterations plus N constant
     * @param nIter past num iterations
     * @return new num iterations for new temp
     */
    private int itPerTemperatureN(int nIter) {
        return nIter + N;
    }

    //=========================================================================
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

    //=========================================================================
    // TEMP VARIATION OPTIONS

    /**
     * Geometric variation of temperature
     * 
     * @param temperature T(k-1)
     * @return new temperature
     */
    private double geometricDecay(double temperature) {
        return ALPHA * temperature;
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

    //=========================================================================
    // STOP CONDITIONS

    /**
     * if accepted proportion falls under ACCEPTED_MOVES_FLOOR
     * algorithm should stop
     * @param prob  n possible/n currently possible
     * @return true to stop it
     */
    private boolean stopCriteriaProb(int iterations, int acceptedMoves) {
        return ((double) acceptedMoves / (double) iterations) >= ACCEPTED_MOVES_FLOOR;
    }

    /**
     * Stops algorithm if max num of iterations
     * 
     * @return true if reached
     */
    private boolean stopCriteriaMaxIt(int numIter) {
        return numIter >= maxIter;
    }

    //=========================================================================
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
        return finalSolution;
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
