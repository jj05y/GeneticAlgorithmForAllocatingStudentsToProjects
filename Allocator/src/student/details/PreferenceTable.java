package student.details;

import java.io.*;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

public class PreferenceTable {


        private HashMap<String, StudentEntry> studentLookup;
        private Vector<Vector<String>> fileContents;
        private static final Random rand = new Random(); //woo



        public PreferenceTable(String filename) {
                fileContents =  loadContentFromFile(filename);
                studentLookup = buildStudentLookup(fileContents);
        }


        // this method loads the vector of vectors into a hashmap, this could have been done when initially
        // reading the file. It's done here merely as an exercise.
        private HashMap<String, StudentEntry> buildStudentLookup(Vector<Vector<String>> fileContents) {
                HashMap<String, StudentEntry> map = new HashMap<>();

                for (Vector<String> v: fileContents.subList(1, fileContents.size())) { //the first vector is ignored
                        String name = v.remove(0);
                        StudentEntry tempStudent = new StudentEntry(name);
                        String preassigned = v.remove(0);
                        if (preassigned.equals("Yes")) {
                                tempStudent.setPreAssignedProject(v.remove(0));
                        } else {
                                for (String project : v) {
                                        tempStudent.addProject(project);
                                }
                        }
                        tempStudent.setInitialNumPrefs();
                        map.put(name,tempStudent);
                }
                return map;
        }


        private Vector<Vector<String>> loadContentFromFile(String filename) {
                Vector<Vector<String>> fileContents = new Vector<>(); //the vector to hold all vectors is created
                try {
                        FileInputStream fis = new FileInputStream(filename);
                        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

                        String line;    //the line to parse

                        StringTokenizer tokenizer;  //this tokenizer will be used to split up the line into tokens
                        while ((line = br.readLine()) != null) {
                                tokenizer = new StringTokenizer(line, "\t");
                                fileContents.add(new Vector<>()); // a vector is created for each line
                                while (tokenizer.hasMoreTokens()) {
                                        String project = tokenizer.nextToken().intern(); //internalise each string
                                        fileContents.get(fileContents.size() - 1).add(project); //the created vector is grabbed and tokens are added one by one
                                }
                        }

                        br.close(); // the reader is closed
                        fis.close(); // the file input stream is also closed

                } catch (IOException e) { //any file IO exceptions must be caught and handled
                        e.printStackTrace(); //not doing a good job of handling them, yet
                }

                return fileContents;    //after the file has been read, the main vector is returned
        }

        public Vector<StudentEntry> getAllStudentEntries() {
                return new Vector<>(studentLookup.values());
        }

        public StudentEntry getEntryFor(String sname) {
                return studentLookup.get(sname);
        }

        public StudentEntry getRandomStudent() {
                return getAllStudentEntries().get(rand.nextInt(getAllStudentEntries().size()));
        }

        public String getRandomAvailablePreference() {
                StudentEntry student = getRandomStudent();
                while (student.hasPreAssignedProject()) { //when getting a random project, need to be sure it's not a preassigned project
                        student =getRandomStudent();
                }
                return student.getRandomPreference();
        }

        //using methods implemented above for choosing a random project, the each
        public void fillPreferencesOfAll(int maxPrefs) {
                for (StudentEntry s : studentLookup.values()) {
                        if (!s.hasPreAssignedProject()) {
                                while (s.getOrderedPreferences().size() < maxPrefs) {
                                        s.addProject(getRandomAvailablePreference());
                                }
                        }
                }
        }


   /*     public static void main(String[] args) {

                //Load a file in,
                String bigFileName = "ProjectAllocationData.tsv";
                String smallFileName = "inputFile.txt";
                PreferenceTable pt = new PreferenceTable("data" + File.separator + bigFileName);

                //Print some random things
                System.out.println("Random Project: " + pt.getRandomAvailablePreference());
                System.out.println("Random Project: " + pt.getRandomAvailablePreference());
                System.out.println("Random Project: " + pt.getRandomAvailablePreference());
                System.out.println("Random Project: " + pt.getRandomAvailablePreference());
                System.out.println("Random Project: " + pt.getRandomAvailablePreference());
                System.out.println();
                System.out.println("Random Student: " + pt.getRandomStudent().getName());
                System.out.println("Random Student: " + pt.getRandomStudent().getName());
                System.out.println("Random Student: " + pt.getRandomStudent().getName());
                System.out.println("Random Student: " + pt.getRandomStudent().getName());
                System.out.println("Random Student: " + pt.getRandomStudent().getName());
                System.out.println();

                System.out.println("Student without 10 stated prefs after filling:");
                StudentEntry foo = pt.getEntryFor("Russell Brand");
                pt.fillPreferencesOfAll(10);
                System.out.println(foo.getName() + " had stated " + foo.getNumberOfStatedPreferences() + " prefs but now has " + foo.getOrderedPreferences().size() + " prefs.");
                System.out.println("\nReprinting all of same student:");
                System.out.println(pt.getEntryFor("Russell Brand"));
                System.out.println("\nPrinting Student with preassigned project to make sure no issues:");
                System.out.println(pt.getEntryFor("Monica Lewinsky"));






        }*/

}
