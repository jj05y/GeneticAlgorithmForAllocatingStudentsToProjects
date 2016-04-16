package MiscComponents;

public class CandidateAssignment {

        private StudentEntry student;
        private String assignment;
        private String previousAssignment;

        /**
         *
         * @param student - The student with which to create the assignment
         */
        public CandidateAssignment(StudentEntry student) {
                this.student = student;
                randomizeAssignment();
        }

        /**
         * assigns a random preference unless the student has a preassigned project.
         */
        public void randomizeAssignment() {
                previousAssignment = assignment;
                if (!student.hasPreAssignedProject()) {
                        assignment = student.getRandomPreference();
                } else {
                        assignment = student.getPreAssignedProject();
                }
        }

        /**
         * used to revert the assigment back to the previous assignment
         */
        public void undoChange() {
                assignment = previousAssignment;
        }


        /*
         * @return returns this assignments StudentEntry
         */
        public StudentEntry getStudentEntry() {
                return student;
        }


        /**
         * @return returns the assignment for this student
         */
        public String getAssignment() {
                return assignment;
        }

        /**
         * @return the energy of the current assignment,,, or constant 1 if the student has a preassigned project
         */
        public int getAssignmentEnergy() {
                if (student.hasPreAssignedProject()) {
                        return 1;
                } else {
                        int ranking = student.getRanking(assignment);
                        ranking = (ranking == -1? 99:ranking); //boost!
                        return (int) (Math.pow(ranking+1,2));
                }

        }

        @Override
        public String toString() {
                return "CandidateAssignment{" +
                        "student=" + student.getName() +
                        ", assignment='" + assignment + '\'' +
                        ", previousAssignment='" + previousAssignment + '\'' +
                        '}';
        }

    /*    public static void main(String[] args) {
                CandidateAssignment ca = new CandidateAssignment(new PreferenceTable("data" + File.separator + "ProjectAllocationData.tsv").getRandomStudent());

                        System.out.print("Energy before randomization: ");
                        System.out.println(ca.getAssignmentEnergy());
                        ca.randomizeAssignment();
                        System.out.print("Energy after randomization: ");
                        System.out.println(ca.getAssignmentEnergy());
                        System.out.println();

        }*/
}
