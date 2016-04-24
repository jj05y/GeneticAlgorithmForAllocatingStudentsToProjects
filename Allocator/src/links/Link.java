package links;


import algorithms.AnnealingAlgorithm;
import algorithms.GeneticAlgorithm;
import listeners.CallBackListener;
import mappings.CandidateAssignment;
import mappings.CandidateSolution;
import student.details.PreferenceTable;

import java.io.*;

public class Link {

        public static final int NO_SOLUTION = -1;
        public static final int SUCCESS = 1;
        public static final int NO_FILE = -2;
        private AnnealingAlgorithm annealingAlgorithm;
        private GeneticAlgorithm geneticAlgorithm;
        private PreferenceTable prefs;
        private CandidateSolution solution;
        private CallBackListener callBackListener;

        public Link(String filename, CallBackListener cbl) {
                callBackListener = cbl;
                System.out.println(filename);
                prefs = new PreferenceTable(filename);

        }

        public int createOutputFile(File file){

                try {
                        FileWriter fw = new FileWriter(file);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write("Name\tAssignment\tRanking\tEnergy\n");

                        for (CandidateAssignment ca : solution.getSolutionList())
                        {
                                bw.write(ca.getStudentEntry().getName()+"\t"+ca.getAssignment()+"\t" +
                                        +ca.getStudentEntry().getRanking(ca.getAssignment())+"\t"+
                                        ca.getAssignmentEnergy()+"\n");
                        }

                        bw.write("Energy of this solution: " + solution.getEnergy());

                        bw.flush();
                        bw.close();
                        fw.close();
                } catch (IOException e) {
                        System.out.println("File Not Found error");
                        return NO_FILE;
                }
                solution = null;
                return SUCCESS;
        }


        public int createGeneticSolution(int numGen, int topN, int popSize, boolean annealedPop) {
                callBackListener.setStatus("TICK TICK");
                geneticAlgorithm = new GeneticAlgorithm(prefs, callBackListener,numGen,topN,popSize, annealedPop);
                solution = geneticAlgorithm.runAlgorithm();
                callBackListener.setStatus("FINSIHED GENETIC " + solution.getEnergy());
                return solution.getEnergy();
        }

        public int createSimulatedSoution(int intialTemp, double decrementAmount, boolean statusSuppressed) {
                annealingAlgorithm = new AnnealingAlgorithm(prefs,intialTemp, decrementAmount, callBackListener, statusSuppressed);
                solution = annealingAlgorithm.go();
                return solution.getEnergy();
        }

        public boolean hasSolution() {
                return solution!=null;
        }
}
