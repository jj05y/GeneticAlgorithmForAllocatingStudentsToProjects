package interfaces;


import links.Link;
import listeners.CallBackListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Cli implements CallBackListener {

        private Link link;


        public Cli(String filename) {
                link = new Link(filename, this);
        }


        private void runPerformanceAnalytics(String filename) {
                try {
                        PrintWriter pw = new PrintWriter(filename+"SARESULTS", "UTF-8");
                        int energy;

                        pw.println("START");
                        //sim annealing
                        pw.println("SIMULATED ANNEALING");
                        pw.println("initialTemp\tcoolAmount\tenergy");
                        for (int intialT = 1000; intialT <= 6000; intialT += 500) {
                                for (double decAmount = 0.25; decAmount < 5; decAmount += 0.25) {
                                        energy = 0;
                                        for (int avg = 0; avg < 10; avg++) {
                                                energy += link.createSimulatedSoution(intialT, decAmount, false);
                                        }
                                        energy /= 10;
                                        pw.println(intialT + "\t" + decAmount + "\t" + energy );
                                }
                        }
                        pw.close();
                        System.out.println("sa done");
                        pw = new PrintWriter(filename+"GARESULTS", "UTF-8");

                        //genetic
                        pw.println("\n\n\nGENETIC");
                        pw.println("Population Size\tTop N to mate\n Number of Generations\tEnergy");
                        for (int popSize = 500; popSize <= 2000; popSize += 500) {
                                for (int topN = 50; topN <= popSize/2; topN += 200) {
                                        for (int numGen = 60; numGen <= 150; numGen += 30) {
                                                energy = 0;
                                                for (int avg = 0; avg < 3; avg++) {
                                                        energy += link.createGeneticSolution(numGen, topN, popSize, false);
                                                }
                                                energy /= 3;
                                                pw.println(popSize + "\t" + topN + "\t" + numGen + "\t" + energy);
                                        }
                                }
                        }

                        pw.flush();
                        pw.close();
                } catch (IOException e) {
                        System.out.println("invalid file");
                        e.printStackTrace();
                }
        }


        @Override
        public void setStatus(String status) {
                System.out.println(status);
        }

        @Override
        public void updateProgressBar(int i) {
                System.out.print(".");
        }

        @Override
        public void resetProgressBar() {
                System.out.println();
        }

        public static void main(String[] args) {
                String helpString = " Useage: \n" +
                        "<infilename> -sa INITIAL_TEMP COOL_AMOUNT <outfilename>\n" +
                        "<infilename> -ga NUM_GEN TOP_N POP_SIZE <outfilename\n" +
                        "<infilename> -test\n" +
                        "-help";
                try {
                Cli cli = new Cli(args[0]);
                if (args[1].equals("-sa")) {
                        cli.link.createSimulatedSoution(Integer.parseInt(args[2]), Integer.parseInt(args[3]), false);
                        cli.link.createOutputFile(new File(args[4]));
                } else if (args[1].equals("-ga")) {
                        cli.link.createGeneticSolution(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), false);
                        cli.link.createOutputFile(new File(args[5]));
                } else if (args[1].equals("-test")) {
                        cli.runPerformanceAnalytics(args[2]);
                }  else if (args[0].equals("-help")) {
                        System.out.println(helpString);
                }
                } catch (Exception e) {
                        System.out.println(helpString);
                }

        }
}
