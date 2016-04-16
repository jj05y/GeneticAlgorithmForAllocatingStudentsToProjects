package Links;


import Algorithms.AnnealingAlgorithm;
import Algorithms.GeneticAlgorithm;
import CallBackListeners.LinkCallBackListener;
import MiscComponents.CandidateAssignment;
import MiscComponents.CandidateSolution;
import MiscComponents.PreferenceTable;

import java.io.*;

public class Link {

        private AnnealingAlgorithm annealingAlgorithm;
        private GeneticAlgorithm geneticAlgorithm;
        private PreferenceTable prefs;
        private CandidateSolution solution;
        private LinkCallBackListener callBackListener;

        public Link(String filename, LinkCallBackListener cbl) {
                callBackListener = cbl;
                prefs = new PreferenceTable(filename);

        }

        public boolean createOutputFile(File file){
                if (solution == null) {
                        //TODO error handling
                      return false;
                }
                try {
                        FileWriter fw = new FileWriter(file);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write("Name\tAssignment\tRanking\tEnergy\n");

                        for (CandidateAssignment ca : solution.getSolutionList())
                        {
                                //TODO outputstring method in CANDIDATE ASSIGNMENT
                                bw.write(ca.getStudentEntry().getName()+"\t"+ca.getAssignment()+"\t"+ca.getStudentEntry().getRanking(ca.getAssignment())+"\t"+ca.getAssignmentEnergy()+"\n");
                        }

                        bw.write("Energy of this solution: " + solution.getEnergy());

                        bw.flush();
                        bw.close();
                        fw.close();
                } catch (IOException e) {
                        System.out.println("File Not Found error");
                }
                solution = null;
                return true;
        }


        public void createGeneticSolution() {
                callBackListener.setStatus("TICK TICK");
                geneticAlgorithm = new GeneticAlgorithm(prefs, callBackListener);
                solution = geneticAlgorithm.runAlgorithm();
                callBackListener.setStatus("FINSIHED GENETIC " + solution.getEnergy());
        }
}
