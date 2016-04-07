package Components;



public class Cli {

        public Cli(String filename) {
                PreferenceTable prefs = new PreferenceTable(filename);
                AnnealingSolution as = new AnnealingSolution(prefs);
                GeneticSolution gs = new GeneticSolution(prefs);
                as.go();
                gs.go();
        }

        public static void main(String[] args) {

                System.out.println(args);


        }


}
