package Algorithms;

import CallBackListeners.LinkCallBackListener;
import Interfaces.SimpleGui;
import MiscComponents.CandidateAssignment;
import MiscComponents.CandidateSolution;
import MiscComponents.InterimCandidateSolution;
import MiscComponents.PreferenceTable;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
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

        private static final int NUM_GENERATIONS = 150;
        private static final int SELECTTOP = 500;
        private static final int POPULATIONSIZE = 1000;
        public static final double PROB_OF_MUTATION = 0.0001;
        private AnnealingAlgorithm aa;

        ArrayList<CandidateSolution> population = new ArrayList<CandidateSolution>();
        ArrayList<CandidateSolution> fittestGroup = new ArrayList<CandidateSolution>();
        ArrayList<CandidateSolution> children = new ArrayList<CandidateSolution>();

        private LinkCallBackListener callBackListener;

        public GeneticAlgorithm(PreferenceTable preferenceTable, LinkCallBackListener callBackListener){
                this.callBackListener = callBackListener;
                generatePopulation(preferenceTable);
                callBackListener.resetProgressBar();

        }

        private void generatePopulation(PreferenceTable preferenceTable) {
                callBackListener.setStatus("Creating Population");
                int updateEvery = POPULATIONSIZE/SimpleGui.PROG_BAR_SIZE;
                for (int i = 0;i < POPULATIONSIZE;i++) {
                     //   population.add(new CandidateSolution(preferenceTable));
                        aa = new AnnealingAlgorithm(preferenceTable, 1000,0.01);

                        population.add(aa.go());
                        if (i%updateEvery==0 && i/ updateEvery < SimpleGui.PROG_BAR_SIZE) {
                                callBackListener.updateProgressBar(i/updateEvery);
                        }
                }
                System.out.println("pop made");

        }

        public void findFittestGroup(){
                fittestGroup.clear();
                Collections.sort(population);
//                for(CandidateSolution s:population ) {
//                        System.out.println(s.getFitness());
//                }
                for (int i=0; i<SELECTTOP; i++) {
                        fittestGroup.add(population.get(population.size() - i - 1));
                }
        }



        /// the algorithm!!! ok so take the best 10. how to mate?? top 5 with bottom? tops in pairs? experiment.
        /// just choosing arbitrary ones for now.
        public void mateBestCandidates(){
                Collections.shuffle(fittestGroup);
                children.clear();

                for(int i =0; i<SELECTTOP; i += 2){

                        CandidateSolution parent1 = fittestGroup.get(i);
                        CandidateSolution parent2 = fittestGroup.get(i+1);

                        //System.out.println("parent1 fitness is: " + parent1.getFitness());
                        //System.out.println("parent2 fitness is: " + parent2.getFitness());


                        InterimCandidateSolution child = new InterimCandidateSolution();

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
                                //System.out.println("resulting child Fitness: " + children.get(children.size()-1).getFitness());
                        }else{
                              //  System.out.println("DIE DIE!!!");
                        }


                }
        }




        public void cullLowest(){
                //get rid of the lowest.
                System.out.println("*********************" + children.size());
                for(int i =0; i<children.size(); i++) {
                       // System.out.println("Fitness of removed element ******"  + population.get(0).getFitness());
                        population.remove(0);
                }
                //add in the children that we made earlier
                        population.addAll(children);
        }

        public CandidateSolution runAlgorithm(){
                int updateEvery = NUM_GENERATIONS/SimpleGui.PROG_BAR_SIZE;
                callBackListener.setStatus("Let the genetics Begin");
                for(int i = 0; i< NUM_GENERATIONS; i++) {
                        System.out.print(i + ": ");
                        findFittestGroup();

                        mateBestCandidates();

                        cullLowest();
                        if (i%updateEvery==0 && i/updateEvery < SimpleGui.PROG_BAR_SIZE) {
                                callBackListener.updateProgressBar(i/updateEvery);
                        }
                        Collections.sort(population);
                        System.out.println("fittest: " +population.get(population.size()-1).getFitness());

                }
                Collections.sort(population);
                return population.get(population.size()-1);
        }
}