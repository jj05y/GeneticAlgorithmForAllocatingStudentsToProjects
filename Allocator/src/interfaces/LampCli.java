package interfaces;


import links.Link;
import listeners.InterfaceCallBackListener;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class LampCli implements InterfaceCallBackListener {

        private Link link;


        public LampCli(String filename) {
                link = new Link(filename, this);
        }


        private void runPerformanceAnalytics() {
                try {
                        String outFilename = "perftest";
                        PrintWriter pw = new PrintWriter(outFilename + "SARESULTS.tsv", "UTF-8");
                        int energy;

                        pw.println("START");
                        //sim annealing
                        pw.println("SIMULATED ANNEALING");
                        pw.println("cool amount scaled up by a factor of 100 for graphing purposes");
                        pw.println("initialTemp\tcoolAmount\tenergy");
                        for (int intialT = 1000; intialT <= 6000; intialT += 500) {
                                for (double decAmount = 0.25; decAmount < 5; decAmount += 0.25) {
                                        energy = 0;
                                        for (int avg = 0; avg < 10; avg++) {
                                                energy += link.createSimulatedSoution(intialT, decAmount, false).getEnergy();
                                        }
                                        energy /= 10;
                                        pw.println(intialT + "\t" + (decAmount*100) + "\t" + energy);
                                }
                        }
                        pw.close();
                        System.out.println("Simulated Annealing Done");
                        pw = new PrintWriter(outFilename + "GARESULTS.tsv", "UTF-8");

                        //genetic
                        pw.println("\n\n\nGENETIC");
                        pw.println("Population Size\tTop N to mate\t Number of Generations\tEnergy");
                        for (int popSize = 500; popSize <= 2000; popSize += 500) {
                                for (int topN = 50; topN <= 2*(popSize / 3); topN += 200) {
                                        for (int numGen = 60; numGen <= 150; numGen += 30) {
                                                energy = 0;
                                                for (int avg = 0; avg < 3; avg++) {
                                                        energy += link.createGeneticSolution(numGen, topN, popSize).getEnergy();
                                                }
                                                energy /= 3;
                                                pw.println(popSize + "\t" + topN + "\t" + numGen + "\t" + energy);
                                        }
                                }
                        }

                        pw.close();
                        System.out.println("Genetic Algorithm Done");
                        pw = new PrintWriter(outFilename + "HYBRID.tsv", "UTF-8");

                        //hybrid
                        pw.println("\n\n\nHybrid");
                        pw.println("Population Size\tTop N to mate\tNumber of Generations\tEnergy\tInitial Temperature\tDecrement Amount");
                        for (int popSize = 500; popSize <= 2000; popSize += 500) {
                                for (int topN = 250; topN <= 2*(popSize / 3); topN += 200) {
                                        for (int numGen = 90; numGen <= 150; numGen += 30) {
                                                for (int intialT = 2000; intialT <= 6000; intialT += 1000) {
                                                        for (double decAmount = 0.5; decAmount < 3; decAmount += 0.5) {
                                                                energy = 0;
                                                                for (int avg = 0; avg < 3; avg++) {
                                                                        energy += link.createHybridGeneticSolution(numGen, topN, popSize, intialT, decAmount).getEnergy();
                                                                }
                                                                energy /= 3;
                                                                pw.println(popSize + "\t" + topN + "\t" + numGen + "\t" + energy + "\t" + intialT + "\t" + (decAmount*100));
                                                                System.out.println(popSize + "\t" + topN + "\t" + numGen + "\t" + energy + "\t" + intialT + "\t" + (decAmount*100));
                                                        }
                                                }
                                        }
                                }
                        }

                        pw.close();
                } catch (IOException e) {
                        System.out.println("error: invalid output file");
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
                String helpString = " Usage: \n" +
                        "<infilename> -sa INITIAL_TEMP COOL_AMOUNT <outfilename>\n" +
                        "<infilename> -ga NUM_GEN TOP_N POP_SIZE <outfilename\n" +
                        "<infilename> -test <baseoutfilename>\n" +
                        "-help";
                LampCli cli;
                try {
                        if (args[0].equals("-help")) {
                                System.out.println(helpString);
                        } else if (args[1].equals("-sa")) {
                                cli = new LampCli(args[0]);
                                cli.link.createSimulatedSoution(Integer.parseInt(args[2]), Double.parseDouble(args[3]), false);
                                cli.link.createOutputFile(new File(args[4]));
                        } else if (args[1].equals("-ga")) {
                                cli = new LampCli(args[0]);
                                cli.link.createGeneticSolution(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                                cli.link.createOutputFile(new File(args[5]));
                        } else if (args[1].equals("-test")) {
                                cli = new LampCli(args[0]);
                                cli.runPerformanceAnalytics();
                        }
                } catch (Exception e) {
                        System.out.println(helpString);
                        e.printStackTrace();
                }

        }
}
