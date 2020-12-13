/*
 * Represents the population of candidates
 * Methods to create new evolutions of the candidates using crossover and mutation
 * Author: Joseph Nunn
 */

import java.util.Random;
import java.util.ArrayList;

public class Population {
    private final static int POPULATION_SIZE = 3000;
    private final static int TOURNAMENT_SIZE = 2;
    private final static double MUTATION_CHANCE = 0.01;
    private final static double CROSSOVER_CHANCE = 0.8;
    private ArrayList<Candidate> candidates;
    private final Random random;

    /**
     * Creates candidates to fill the population
     */
    public Population() {
        random = new Random();
        candidates = new ArrayList<>(POPULATION_SIZE);
        for (int i = 0; i < POPULATION_SIZE; i++) {
            candidates.add(new Candidate());
        }
    }

    /**
     * Returns the most fit candidate in the population
     * @return most fit candidate
     */
    public Candidate getMostFitCandidate() {
        return getMostFitCandidate(candidates);
    }

    /**
     * Returns the most fit candidate in an ArrayList
     * @param candidates ArrayList of candidates to find the most fit from
     * @return The most fit candidate
     */
    private Candidate getMostFitCandidate(ArrayList<Candidate> candidates) {
        Candidate mostFit = candidates.get(0);
        for (int i = 1; i < candidates.size(); i++) {
            if ((mostFit.getFitness() - candidates.get(i).getFitness()) < 0) {
                mostFit = candidates.get(i);
            }
        }
        return mostFit;
    }

    /**
     * Repopulates the population with new and old candidates
     * Amount of old candidates and candidates created through crossover defined by CROSSOVER_CHANCE
     * The best candidate is always in the new population
     * A number of candidates defined by MUTATION_CHANCE are mutated. The most fit candidate from the last evolution
     * will not get mutated.
     */
    public void evolve() {
        ArrayList<Candidate> children = new ArrayList<>(POPULATION_SIZE);
        Candidate mostFit = getMostFitCandidate(candidates);
        children.add(mostFit); //Elitism. Make sure best candidate survives
        while (children.size() < POPULATION_SIZE) {
            children.add(breed(candidates));
        }
        for (int i = 1; i < children.size(); i++) {
            if (random.nextDouble() < MUTATION_CHANCE) {
                children.get(i).mutate();
            }
        }
        candidates = children;
    }

    /**
     * Returns a candidate to be used in a new population
     * Either a candidate chosen through selection or a candidate from crossover of two selected candidates
     * Chance that it is a candidate created through crossover defined by CROSSOVER_CHANCE
     * @param candidates candidates to select from
     * @return a candidate chosen through selection or a candidate from crossover of two selected candidates
     */
    private Candidate breed(ArrayList<Candidate> candidates) {
        Candidate parent1 = selection(candidates);
        if (random.nextDouble() < CROSSOVER_CHANCE) {
            Candidate parent2 = selection(candidates);
            return new Candidate(parent1, parent2);
        } else {
            return parent1;
        }
    }

    /**
     * Tournament selection of candidates
     * Randomly selects a number of candidates defined by TOURNAMENT_SIZE
     * Chooses the most fit from that selection
     * @param candidates candidates to select from
     * @return The most fit candidate from the tournament
     */
    private Candidate selection(ArrayList<Candidate> candidates ) {
        ArrayList<Candidate> competitors = new ArrayList<>(TOURNAMENT_SIZE);
        while (competitors.size() < TOURNAMENT_SIZE) {
            competitors.add(candidates.get(random.nextInt(candidates.size())));
        }
        return getMostFitCandidate(competitors);
    }
}