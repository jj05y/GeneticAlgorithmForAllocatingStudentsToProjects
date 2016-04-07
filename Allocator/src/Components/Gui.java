package Components;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Gui {

        JLabel label;
        JButton b;
        JTextArea textArea;

        JFrame frame;

        JButton chooseFile;
        JButton chooseSa;
        JButton chooseGen;

        JPanel topArea;
        JPanel settings;
        JPanel outputArea;

        final JFileChooser fc = new JFileChooser();


        public Gui() {
                frame = new JFrame("LAMP");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setLayout(new BorderLayout());





                topArea = makeTopArea();
                settings = makeGenSettings();
                outputArea = makeOutputArea();




                frame.add(topArea, BorderLayout.NORTH);
                frame.add(settings, BorderLayout.CENTER);
                frame.add(outputArea, BorderLayout.SOUTH);
                frame.pack();

                frame.setVisible(true);
        }

        private JPanel makeTopArea() {
                JPanel p = new JPanel();
                chooseFile = new JButton("Load File");
                chooseFile.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                int returnVal = fc.showOpenDialog(frame);

                                if (returnVal == JFileChooser.APPROVE_OPTION) {
                                        File file = fc.getSelectedFile();
                                        //This is where a real application would open the file.
                                        System.out.println(file.getName() +"opened");
                                } else {
                                        System.out.println("Fail");
                                }
                        }
                });

                chooseSa = new JButton("Simulated Annealling");
                chooseSa.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                System.out.println("SIMULATED ANNEALING");
                                frame.remove(settings);
                                frame.add(makeSaSettings(),BorderLayout.CENTER);
                                frame.validate();
                        }
                });

                chooseGen = new JButton("Genetic Algorithm");
                chooseGen.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                System.out.println("GENETIC ALGORITHMS");
                                frame.remove(settings);
                                frame.add(makeGenSettings(), BorderLayout.CENTER);
                                frame.validate();
                        }
                });
                p.setLayout(new FlowLayout());
                p.add(chooseFile);
                p.add(chooseSa);
                p.add(chooseGen);

                return p;


        }

        private JPanel makeGenSettings() {
                JPanel panel = new JPanel();
                panel.setLayout(new FlowLayout());
                panel.add(new JLabel("Gentic Settings"));
                return panel;

        }


        private JPanel makeSaSettings() {
                JPanel panel = new JPanel();
                panel.setLayout(new FlowLayout());
                panel.add(new JLabel("Simulated Annealin Settings"));
                return panel;

        }

        private JPanel makeOutputArea() {
                JPanel panel = new JPanel();
                panel.setLayout(new FlowLayout());
                JButton button = new JButton("Save");
                panel.add(button);
                return panel;
        }


        public static void main(String[] args) {
                new Gui();
        }

}
