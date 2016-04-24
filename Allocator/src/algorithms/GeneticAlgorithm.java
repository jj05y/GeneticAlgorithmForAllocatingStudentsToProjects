package algorithms;

import interfaces.Gui;
import listeners.CallBackListener;
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

        private static final int INITIAL_TEMPERATURE = 3000;
        private static final double DECREMENT_AMOUNT = 0.5;
        private ArrayList<CandidateSolution> population;
        private CallBackListener callBackListener;
        private int numGenerations;
        private int populationSize;
        private int topN;

        public GeneticAlgorithm(PreferenceTable preferenceTable, CallBackListener cbl,
                                int numGen, int top, int popSize, Boolean annealedPop){
                callBackListener = cbl;
                populationSize = popSize;
                numGenerations = numGen;
                topN = top;

                population = new ArrayList<CandidateSolution>();
                generatePopulation(preferenceTable, annealedPop);
        }

        private void generatePopulation(PreferenceTable preferenceTable, Boolean annealedPop) {
                int updateEvery = populationSize / Gui.PROG_BAR_SIZE;
                AnnealingAlgorithm aa;

                callBackListener.setStatus("Creating Population");

                for (int i = 0;i < populationSize;i++) {
                        if(annealedPop){
                                aa = new AnnealingAlgorithm(preferenceTable, INITIAL_TEMPERATURE, DECREMENT_AMOUNT, callBackListener, true);
                                population.add(aa.go());
                        }else {
                                population.add(new CandidateSolution(preferenceTable));
                        }
                        if (i%updateEvery==0 && i/ updateEvery < Gui.PROG_BAR_SIZE) {
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

        public CandidateSolution runAlgorithm(){

                ArrayList<CandidateSolution> children;

                //used to know when to update progress bar
                int updateEveryN = numGenerations / Gui.PROG_BAR_SIZE;
                callBackListener.setStatus("Let the genetics Begin");

                for(int i = 0; i< numGenerations; i++) {

                        //getkids
                        children = mate(fittestGroup());
                        //removetoomany
                        for(int j =0; j<children.size(); j++) population.remove(0);
                        //addkids
                        population.addAll(children);

                        if (i%updateEveryN==0 && i/updateEveryN < Gui.PROG_BAR_SIZE) {
                                callBackListener.updateProgressBar(i / updateEveryN);
                        }
                }

                Collections.sort(population);
                return population.get(population.size()-1);
        }
}