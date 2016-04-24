package algorithms;


import interfaces.Gui;
import listeners.CallBackListener;
import mappings.CandidateAssignment;
import mappings.CandidateSolution;
import student.details.PreferenceTable;

import java.util.Random;

import static java.lang.Math.pow;

public class AnnealingAlgorithm {

        private CandidateSolution currentSolution;
        private CandidateAssignment temp;
        private CallBackListener callBackListener;
        private double temperature;
        private double coolAmount;
        private boolean statusSuppressed;

        public AnnealingAlgorithm(PreferenceTable pt, int initialTemperature, double decrementAmount, CallBackListener cbl, boolean suppressed){
                temperature = initialTemperature;
                coolAmount = decrementAmount;
                callBackListener = cbl;
                statusSuppressed = suppressed;
                currentSolution = new CandidateSolution(pt);
        }

        public CandidateSolution go() {
                if (!statusSuppressed) {
                        callBackListener.setStatus("Simulated Annealing starting.");
                }
                int energy = currentSolution.getEnergy();
                int newEnergy;
                int update = ((int) (temperature/coolAmount)) / Gui.PROG_BAR_SIZE;
                int noOfCycles = 0;
                Random rnd = new Random();

                while (temperature > 0){        //while good solution hasn't been found
                        noOfCycles++;
                        temp = currentSolution.getRandomAssignment();
                        temp.randomizeAssignment();     //current solution with one student's project changed
                        newEnergy = currentSolution.getEnergy();
                        double probability = probabilityOfAccepting(energy, newEnergy, temperature);
                        double chance = rnd.nextDouble();

                        if (chance > probability){
                            temp.undoChange();
                        }else {
                            energy = currentSolution.getEnergy();
                        }

                        temperature -= coolAmount; //decrease temperature with each iteration
                        if (!statusSuppressed){
                                if (noOfCycles%update==0 && noOfCycles/ update < Gui.PROG_BAR_SIZE) {
                                        callBackListener.updateProgressBar(noOfCycles / update);
                                }
                        }
                }

                if (!statusSuppressed){
                        callBackListener.resetProgressBar();
                        callBackListener.setStatus("Result obtained with energy of " +currentSolution.getEnergy());
                }
                return currentSolution;
        }


        public double probabilityOfAccepting(int energy, int newEnergy, double temperature) {
                if (newEnergy < energy){
                    return 1.0;            //always accept change
                } else {
                     return 1 / Math.exp((double)newEnergy - (double)energy / temperature); // probability of accepting change or not
                }
        }
}
