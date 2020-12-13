/*
 * Using a genetic algorithm determines a collection of items that provides the most utility while being under a
 * weight limit of 500kg from 100 items defined in the compiled class Problem2Test provided to me by my University
 * Lectures.
 *
 * @Author Joseph Nunn
 */


public class KnapsackSolver {
    private Population population;

    /**
     * Creates a population of candidates
     */
    public KnapsackSolver() {
        population = new Population();
    }

    /**
     * Evolves the population until the most fit solution hasn't improved for 10 evolutions
     * Prints the most fit solution. True is an item taken false is an item not taken
     */
    public void getSolution() {
        Candidate mostFit = null;
        double bestFitness = -1;
        int noImprovement = 0;

        while (noImprovement < 10) {
            population.evolve();
            mostFit = population.getMostFitCandidate();
            if (mostFit.getFitness() <= bestFitness) {
                noImprovement++;
            } else {
                bestFitness = mostFit.getFitness();
                noImprovement = 0;
            }
        }
        System.out.println("Most Fit Solution:");
        System.out.println(mostFit.toString());
    }

    public static void main(String[] args) {
        KnapsackSolver solver = new KnapsackSolver();
        solver.getSolution();
    }
}