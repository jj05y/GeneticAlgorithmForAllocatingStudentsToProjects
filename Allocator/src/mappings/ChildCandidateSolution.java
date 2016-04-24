package mappings;

import java.util.Vector;

public class ChildCandidateSolution extends CandidateSolution {

        public int energyWith(CandidateAssignment ca) {
                CandidateSolution temp = new CandidateSolution(new Vector<>(solutionList));
                temp.getSolutionList().add(ca);
                return temp.getEnergy();

        }
        public void addAssignment(CandidateAssignment ca) {
                solutionList.add(ca);
        }

}
