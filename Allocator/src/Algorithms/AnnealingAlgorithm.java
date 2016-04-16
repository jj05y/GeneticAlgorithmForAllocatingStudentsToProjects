package Algorithms;


import MiscComponents.CandidateAssignment;
import MiscComponents.CandidateSolution;
import MiscComponents.PreferenceTable;

import java.util.Random;

import static java.lang.Math.pow;

public class AnnealingAlgorithm {

        CandidateSolution currentSolution;
        CandidateAssignment temp;
        private static final Random RND = new Random(); //for randomizing neighbour
        private int energy = 0;
        private int newEnergy = 0;
        private double temperature;
        private double coolAmount;
        public static final double E = 2.71828;

        public AnnealingAlgorithm(PreferenceTable pt, int initialTemperature, double decrementAmount){
                temperature = initialTemperature;
                coolAmount = decrementAmount;
                currentSolution = new CandidateSolution(pt);
        }

        public CandidateSolution go() {
                energy = currentSolution.getEnergy();
              //  System.out.print("Original Energy: ");
           //     System.out.println(currentSolution.getEnergy());
                while (temperature > 0){        //while good solution hasn't been found
                        randomizeNeighbour();
                        double probability = BoltzmannDistribution(energy, newEnergy, temperature);
                        double chance = RND.nextDouble();
                        if (chance > probability){
                            temp.undoChange();
                        }else {
                            energy = currentSolution.getEnergy();
                        }
                        temperature-= 1; //decrease temperature with each iteration
                }
            //    System.out.print("New energy: ");
             //   System.out.println(currentSolution.getEnergy());
                return currentSolution;
        }


        public void randomizeNeighbour() {
                temp = currentSolution.getRandomAssignment();
                temp.randomizeAssignment();     //current solution with one students project changed
                newEnergy = currentSolution.getEnergy();
        }


        public double BoltzmannDistribution(int energy, int newEnergy, double temperature) {
                if (newEnergy < energy){
                    return 1.0;            //always accept change
                } else {
                     return 1 / (pow(E,(double)newEnergy - energy / temperature)); // probability of accepting change or not
                }
        }
}
