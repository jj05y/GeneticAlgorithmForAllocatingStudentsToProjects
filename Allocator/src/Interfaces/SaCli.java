package Interfaces;


import Algorithms.AnnealingAlgorithm;
import MiscComponents.PreferenceTable;

import java.io.File;

public class SaCli {

        public SaCli(String filename) {
                PreferenceTable prefs = new PreferenceTable(filename);
                AnnealingAlgorithm aa = new AnnealingAlgorithm(prefs, 10000, 0.01);
                aa.go();

        }

        public static void main(String[] args) {

                String filepath = "data" + File.separator;
                String defaultFile = "ProjectAllocationData.tsv";

                for (int i = 0; i < 100; i++) {
                        new SaCli(filepath + defaultFile);
                }
        }

}
