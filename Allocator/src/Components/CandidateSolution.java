package Components;

import java.io.File;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

public class CandidateSolution {

        public static final int PENALTY = 1000;
        private Vector<CandidateAssignment> solutionList;
        private Random rand;


        /**
         * @param prefs this prefereance table is used to populate this solution.
         */
        public CandidateSolution(PreferenceTable prefs) {
                solutionList = new Vector<>();
                rand = new Random();

                for (StudentEntry s :prefs.getAllStudentEntries()) {
                        solutionList.add(new CandidateAssignment(s));
                }
        }


        /**
         * @param studentName the student who's assignment you want
         * @return - the student entry of the requested student or null if not exists
         */
        public CandidateAssignment getAssignmentFor(String studentName) {
                for (CandidateAssignment ca : solutionList) {
                        if (ca.getStudentEntry().getName().equals(studentName)) {
                                return ca;
                        }
                }
                return null;
        }

        /**
         * @return - returns a random assignment
         */
        public CandidateAssignment getRandomAssignment() {
                return solutionList.get(rand.nextInt(solutionList.size()));
        }

        /**
         * @return The sum of the energies of the students plus a penalty for each  project assigned more than once
         */
        public int getEnergy() {
                int sumOfStudentEnergies = 0;
                for (CandidateAssignment ca : solutionList) {
                        sumOfStudentEnergies += ca.getEnergy();
                }

                int num_penalties = 0;
                HashMap<String, String> assignedProjects = new HashMap<>();
                for (CandidateAssignment ca : solutionList) {
                        if (assignedProjects.get (ca.getAssignmentFor()) != null) {
                                num_penalties++;
                        } else {
                                assignedProjects.put(ca.getAssignmentFor(), ca.getAssignmentFor());
                        }
                }
                return sumOfStudentEnergies + (num_penalties* PENALTY);
        }

        /**
         * @return the fitness of the solution
         */
        public int getFitness(){
                return -getEnergy();
        }

        @Override
        public String toString() {
                StringBuilder sb = new StringBuilder();
                for (CandidateAssignment ca : solutionList) {
                        sb.append(ca + "\n");
                }
                return sb.toString();
        }

      public static void main(String[] args) {
              CandidateSolution cs = new CandidateSolution(new PreferenceTable("data" + File.separator + "ProjectAllocationData.tsv"));

              System.out.println(cs.getEnergy());
              cs.getRandomAssignment().randomizeAssignment();
              System.out.println(cs.getEnergy());
      }
}
