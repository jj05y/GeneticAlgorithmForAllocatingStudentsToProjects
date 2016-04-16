package Interfaces;


import Algorithms.AnnealingAlgorithm;
import Algorithms.GeneticAlgorithm;
import MiscComponents.PreferenceTable;

import java.io.File;

public class Cli {

        public Cli(String filename) {
                PreferenceTable prefs = new PreferenceTable(filename);
                AnnealingAlgorithm as = new AnnealingAlgorithm(prefs,1000,0.1);
                GeneticAlgorithm gs = new GeneticAlgorithm(prefs, null);
                as.go();

        }

        public static void main(String[] args) {

                String filepath = "data" + File.separator;
                String defaultFile = "ProjectAllocationData.tsv";
                if (args.length == 0) {
                        new Cli(filepath + defaultFile);
                } else {
                        new Cli(filepath + args[0]);
                }
        }

}
