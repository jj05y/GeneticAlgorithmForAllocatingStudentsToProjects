package Components;

import java.util.ArrayList;
// this will be a client of candidate solution
/**
 * Created by edwinkeville on 07/04/16.
 */

//so how does this work. we create our candidate solution.
// select the top group
// mate them and add the children back into the fray
// remove the lowest members now. (cull. )
// begin again.
public class GeneticAlgorithm {

        private static final int RUNTIMES = 1000;
        private static final int SELECTTOP = 20;

        ArrayList<CandidateSolution> population = new ArrayList<CandidateSolution>();
        ArrayList<CandidateSolution> fittestGroup = new ArrayList<CandidateSolution>();

        public GeneticAlgorithm(PreferenceTable preferenceTable){
                generatePopulation(preferenceTable);
                runAlgorithm();
        }

        private void generatePopulation(PreferenceTable preferenceTable) {
                //augment array population using preference table.
        }

        public void findBestCandidates(){
        }

        public void mateBestCandidates(){

        }

        public void cullLowest(){
        }

        public CandidateSolution runAlgorithm(){
                CandidateSolution solution = null;
                int i = 0;
                while(i<RUNTIMES) {
                        findBestCandidates();
                        mateBestCandidates();
                        cullLowest();
                }
                return solution;
        }
}
