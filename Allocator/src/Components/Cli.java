package Components;


import java.io.File;

public class Cli {

        public Cli(String filename) {
                PreferenceTable prefs = new PreferenceTable(filename);
                SimulatedAnnealingAlgorithm as = new SimulatedAnnealingAlgorithm(prefs);
                GeneticAlgorithm gs = new GeneticAlgorithm(prefs);
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
