/*
 * Represents a collection of items and their combined utility or fitness
 * Author: Joseph Nunn
 */

import java.util.Random;

public class Candidate {
    private final static int NUMBER_OF_ITEMS = 100;
    private final static int MAX_WEIGHT = 500;
    private final static Random random;
    private double fitness;
    private boolean[] items;

    static {
        random = new Random();
    }

    /**
     * Each item is instantiated with a random boolean. True for taking the item false for not
     * Fitness is calculated
     */
    public Candidate() {
        items = new boolean[NUMBER_OF_ITEMS];
        for (int i = 0; i < NUMBER_OF_ITEMS; i++) {
            items[i] = random.nextBoolean();
        }
        calcFitness();
    }

    /**
     * Creation of a candidate by crossover of two other candidates
     * Each gene is randomly selected from either candidate
     *
     * @param candidate1 the first candidate to crossover
     * @param candidate2 the second candidate to crossover
     */
    public Candidate(Candidate candidate1, Candidate candidate2) {
        items = new boolean[NUMBER_OF_ITEMS];
        for (int i = 0; i < NUMBER_OF_ITEMS; i++) {
            if (random.nextBoolean()) {
                items[i] = candidate1.getItems()[i];
            } else {
                items[i] = candidate2.getItems()[i];
            }
        }
        calcFitness();
    }

    /**
     * Accessor method for the fitness
     * @return the fitness
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * Accessor method for the items
     * @return array of booleans indicating if each item is taken
     */
    public boolean[] getItems() {
        return items;
    }

    /**
     * Calculates the fitness of the candidate
     * The fitness is lowered if over the weight limit
     */
    private void calcFitness() {
        //Provided assessment test to get weight and fitness of items. Weight stored in first position fitness in second
        double[] tmp =(Assess.getTest2(items));
        double weight = tmp[0];
        if (weight > MAX_WEIGHT) {
            fitness = tmp[1] / (weight - MAX_WEIGHT); //Fitness lowered the more it is over the weight limit

        } else {
            fitness = tmp[1];
        }
    }

    /**
     * Mutates the candidate switching whether a random item is taken or not
     */
    public void mutate() {
        int itemToMutate = random.nextInt(NUMBER_OF_ITEMS);
        items[itemToMutate] = !items[itemToMutate];
        calcFitness();
    }

    /**
     * Converts items into a string
     * @return items taken and not taken as as string
     */
    @Override
    public String toString() {
        String stringItems = "";
        for (int i = 0; i < NUMBER_OF_ITEMS - 1; i++) {
            if (items[i]) {
                stringItems += "True, ";
            } else {
                stringItems += "False, ";
            }
        }
        if (items[NUMBER_OF_ITEMS - 1]) {
            stringItems += "True.";
        } else {
            stringItems += "False.";
        }
        return stringItems;
    }
}