package Components;


import java.util.Random;

public class AnnealingSolution {

        PreferenceTable table;
        private static final Random RND = new Random(); //for randomizing neighbour
        int energy = 0;
        int newEnergy = 0;

        public AnnealingSolution(PreferenceTable pt){
                //create a solution - leave preassigned
        }

        public void go() {
                //check & set disappointment for initial solution
                //energy = solution.getEnergy();

                //start loop until good solution found:
                //change person using randomize Neighbour
                //check disappointment again
                //newenergy = newsolution.getEnergy

                //use Boltzmann Distribution function

        }


        public void randomizeNeighbour() {
                //solution as parameter
                //Change one element from previous solution
                //check if everyone has unique project
                //if not, fill in blanks
                //return new solution
        }


        public double BoltzmannDistribution(int energy, int newEnergy, double disappointment) {
                if (newEnergy < energy){
                        return 1.0;
                } else {
                        //probability function
                }
                //parameteres of two energy levels and temperature
        }
}
