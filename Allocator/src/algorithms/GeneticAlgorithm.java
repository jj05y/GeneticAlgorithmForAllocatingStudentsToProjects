package algorithms;

import interfaces.LampGui;
import listeners.InterfaceCallBackListener;
import mappings.CandidateAssignment;
import mappings.CandidateSolution;
import mappings.ChildCandidateSolution;
import student.details.PreferenceTable;

import java.util.ArrayList;
import java.util.Collections;
// this will be a client of candidate solution

/**
 * Created by edwinkeville on 07/04/16.
 */


public class GeneticAlgorithm {


        private ArrayList<CandidateSolution> population;
        private InterfaceCallBackListener callBackListener;
        private int numGenerations;
        private int populationSize;
        private int topN;

        public GeneticAlgorithm(InterfaceCallBackListener cbl,
                                int numGen, int top, int popSize){
                callBackListener = cbl;
                populationSize = popSize;
                numGenerations = numGen;
                topN = top;
        }

        private void generatePopulation(PreferenceTable preferenceTable, Boolean annealedPop, int initialTemp, double decrementAmount) {
                population = new ArrayList<CandidateSolution>();
                int updateEvery = populationSize / LampGui.PROG_BAR_SIZE;
                AnnealingAlgorithm aa;

                for (int i = 0;i < populationSize;i++) {
                        if(annealedPop){
                                aa = new AnnealingAlgorithm(preferenceTable, initialTemp, decrementAmount, callBackListener, true);
                                population.add(aa.go());
                        }else {
                                population.add(new CandidateSolution(preferenceTable));
                        }
                        if (i%updateEvery==0 && i/ updateEvery < LampGui.PROG_BAR_SIZE) {
                                callBackListener.updateProgressBar(i / updateEvery);
                        }
                }

                callBackListener.resetProgressBar();
        }

        private ArrayList<CandidateSolution> fittestGroup(){
                ArrayList<CandidateSolution> fittestGroup = new ArrayList<>();

                Collections.sort(population);

                for (int i=0; i< topN; i++) {
                        fittestGroup.add(population.get(population.size() - i - 1));
                }
                Collections.shuffle(fittestGroup);
                return fittestGroup;
        }

        private ArrayList<CandidateSolution> mate(ArrayList<CandidateSolution> group){

                ArrayList<CandidateSolution> children = new ArrayList<>();

                for(int i =0; i< topN; i += 2){

                        CandidateSolution parent1 = group.get(i);
                        CandidateSolution parent2 = group.get(i+1);

                        ChildCandidateSolution child = new ChildCandidateSolution();

                        for(int j = 0; j<parent1.getSolutionList().size(); j++){

                                CandidateAssignment fromP1 = parent1.getSolutionList().get(j);
                                CandidateAssignment fromP2 = parent2.getSolutionList().get(j);

                                if(child.energyWith(fromP1) < child.energyWith(fromP2)){
                                        child.addAssignment(fromP1);
                                }else{
                                        child.addAssignment(fromP2);
                                }
                        }

                        if(child.getFitness()>parent1.getFitness() && child.getFitness()>parent2.getFitness() ){
                                children.add(child);
                        }
                }
                return children;
        }

        public CandidateSolution runAlgorithmWithAnnealedPopulation(PreferenceTable prefs, int initialTemp, double decrementAmount){

                callBackListener.setStatus("Creating Population");
                generatePopulation(prefs, true, initialTemp, decrementAmount);

                return runAlgorithm();
        }
        public CandidateSolution runAlgorithmWithRandomPopulation(PreferenceTable prefs) {

                callBackListener.setStatus("Creating Population");
                generatePopulation(prefs, false, 0, 0);

                return runAlgorithm();
        }

        private CandidateSolution runAlgorithm(){

                ArrayList<CandidateSolution> children;

                //used to know when to update progress bar
                int updateEveryN = numGenerations / LampGui.PROG_BAR_SIZE;



                callBackListener.setStatus("Genetic Algorithm Running");
                for(int i = 0; i< numGenerations; i++) {

                        //getkids
                        children = mate(fittestGroup());
                        //removetoomany
                        for(int j =0; j<children.size(); j++) population.remove(0);
                        //addkids
                        population.addAll(children);

                        if (i%updateEveryN==0 && i/updateEveryN < LampGui.PROG_BAR_SIZE) {
                                callBackListener.updateProgressBar(i / updateEveryN);
                        }
                }
                Collections.sort(population);
                CandidateSolution bestSolution = population.get(populationSize-1);
                callBackListener.resetProgressBar();
                callBackListener.setStatus("Solution created with energy of " + bestSolution.getEnergy());
                return bestSolution;
        }
}