package links;


import algorithms.AnnealingAlgorithm;
import algorithms.GeneticAlgorithm;
import listeners.InterfaceCallBackListener;
import mappings.CandidateAssignment;
import mappings.CandidateSolution;
import student.details.PreferenceTable;

import java.io.*;

public class Link {

        public static final int SUCCESS = 1;
        public static final int NO_FILE = -1;
        private AnnealingAlgorithm annealingAlgorithm;
        private GeneticAlgorithm geneticAlgorithm;
        private PreferenceTable prefs;
        private CandidateSolution solution;
        private InterfaceCallBackListener callBackListener;

        public Link(String filename, InterfaceCallBackListener cbl) {
                callBackListener = cbl;
                callBackListener.setStatus("Opened "+ filename);
                prefs = new PreferenceTable(filename);
        }

        public int createOutputFile(File file){

                try {
                        FileWriter fw = new FileWriter(file);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write("Name\tAssignment\tRanking\n");

                        for (CandidateAssignment ca : solution.getSolutionList())
                        {
                                bw.write(ca.getStudentEntry().getName()+"\t"+ca.getAssignment()+"\t" +
                                        +(ca.getStudentEntry().getRanking(ca.getAssignment())+1)+"\n");
                        }

                        bw.write("Energy of this solution: " + solution.getEnergy());

                        bw.flush();
                        bw.close();
                        fw.close();
                        callBackListener.setStatus("Solution saved in " + file.getName());
                } catch (IOException e) {
                        System.out.println("File Not Found error");
                        return NO_FILE;
                }
                solution = null;
                return SUCCESS;
        }


        public CandidateSolution createGeneticSolution(int numGen, int topN, int popSize) {
                geneticAlgorithm = new GeneticAlgorithm(callBackListener,numGen,topN,popSize);
                solution = geneticAlgorithm.runAlgorithmWithRandomPopulation(prefs);
                return solution;
        }


        public CandidateSolution createHybridGeneticSolution(int numGen, int topN, int popSize, int intialTemp, double decrementAmount) {
                geneticAlgorithm = new GeneticAlgorithm(callBackListener,numGen,topN,popSize);
                solution = geneticAlgorithm.runAlgorithmWithAnnealedPopulation(prefs,intialTemp,decrementAmount);
                return solution;
        }

        public CandidateSolution createSimulatedSoution(int intialTemp, double decrementAmount, boolean statusSuppressed) {
                annealingAlgorithm = new AnnealingAlgorithm(prefs,intialTemp, decrementAmount, callBackListener, statusSuppressed);
                solution = annealingAlgorithm.go();
                return solution;
        }

        public boolean hasSolution() {
                return solution!=null;
        }


}
