package MiscComponents;

import java.util.Random;
import java.util.Vector;

// this class has a few fields with getters and setters, aswell as small amounts of logic to determine if the project
// has been preassigned.
// there is also a to_string method implemented
public class StudentEntry {

        private String name;
        private Vector<String> orderedPreferences;
        private String preAssignedProject;
        private int numberOfStatedPreferences;
        private static final Random rand = new Random();


        public StudentEntry(String name) {
                this.name = name;
                orderedPreferences = new Vector<>();
        }


        public void addProject(String name) {
                if (!hasPreference(name)) { //projects must be unique
                        orderedPreferences.add(name);
                }
        }

        public String getName() {
                return name;
        }

        public Vector<String> getOrderedPreferences() {
                return orderedPreferences;
        }

        public int getNumberOfStatedPreferences(){
                return numberOfStatedPreferences;
        }

        public String getPreAssignedProject() {
                return preAssignedProject;
        }

        public boolean hasPreAssignedProject() {
                return preAssignedProject != null;
        }

        //this is called only when finished initially packing orderedPreferences
        public void setInitialNumPrefs() {
                if (!hasPreAssignedProject()) {
                        numberOfStatedPreferences = orderedPreferences.size();
                } else {
                        numberOfStatedPreferences = 1;
                }
        }

        public void setPreAssignedProject(String project){
                preAssignedProject = project;
        }

        @Override
        public String toString() {
                return "name='" + name + '\'' +
                        "\npreAssignedProject='" + preAssignedProject + '\'' +
                        "\nnumberOfStatedPreferences=" + numberOfStatedPreferences +
                        "\norderedPreferences=" + orderedPreferences;
        }

        public String getRandomPreference() {
                return orderedPreferences.get(rand.nextInt(orderedPreferences.size()));
        }

        public boolean hasPreference(String pref) {
                return orderedPreferences.contains(pref);
        }

        /**
         * @param proj - the project for which a ranking is required
         * @return - the ranking of the given project
         */
        public int getRanking(String proj) {
                if (hasPreAssignedProject()) {
                        return 1;
                } else {
                        return orderedPreferences.indexOf(proj);
                }
        }
}
