package Interfaces;


import Algorithms.GeneticAlgorithm;
import MiscComponents.PreferenceTable;

import java.io.File;

public class GeneticCli {

        public GeneticCli(String filename) {
                PreferenceTable prefs = new PreferenceTable(filename);
                GeneticAlgorithm ga = new GeneticAlgorithm(prefs, null);
              ga.runAlgorithm();

        }

        public static void main(String[] args) {


                String filepath = "data" + File.separator;
                String defaultFile = "ProjectAllocationData.tsv";

                new GeneticCli(filepath + defaultFile);
        }

}
