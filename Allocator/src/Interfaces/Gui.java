/*
package Interfaces;



import CallBackListeners.LinkCallBackListener;
import Links.Link;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Gui implements LinkCallBackListener {

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

        JLabel statusLabel;


        private JFileChooser fc;
        private boolean isRunning;

        private Link link;


        public Gui() {
                fc = new JFileChooser();
                fc.setCurrentDirectory(new File("\\data"));
                frame = new JFrame("LAMP");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setLayout(new BorderLayout());

                topArea = makeTopArea();
                settings = makeSaSettings();
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
                LinkCallBackListener cbl = this;
                chooseFile.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                int returnVal = fc.showOpenDialog(frame);

                                if (returnVal == JFileChooser.APPROVE_OPTION) {
                                        File file = fc.getSelectedFile();
                                        //This is where a real application would open the file.
                                        link = new Link(file.getAbsolutePath(), cbl);
                                        System.out.println(file.getAbsolutePath() +" link created");

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
                                settings = makeSaSettings();
                                frame.add(settings,BorderLayout.CENTER);
                                frame.pack();
                                frame.validate();
                        }
                });

                chooseGen = new JButton("Genetic Algorithm");
                chooseGen.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                System.out.println("GENETIC ALGORITHMS");
                                frame.remove(settings);
                                settings = makeGenSettings();
                                frame.add(settings, BorderLayout.CENTER);
                                frame.pack();
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
                panel.setLayout(new BorderLayout());

                JPanel topBit = new JPanel();
                topBit.setLayout(new FlowLayout());
                topBit.add(new JLabel("Genetic Settings"));

                panel.add(topBit, BorderLayout.NORTH);

                JPanel importantBits = new JPanel();
                importantBits.setLayout(new FlowLayout());

                JPanel slidersArea = new JPanel();
                slidersArea.setLayout(new BoxLayout(slidersArea, BoxLayout.Y_AXIS));


                JPanel popArea = new JPanel();
                popArea.setLayout(new FlowLayout());

                JLabel popVal = new JLabel("500");


                JSlider sliderPop = new JSlider(JSlider.HORIZONTAL, 1000, 500);
                sliderPop.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                                popVal.setText(sliderPop.getValue()+"");
                        }
                });

                popArea.add(new JLabel("Population Size"));
                popArea.add(sliderPop);
                popArea.add(popVal);


                JPanel itterationsArea = new JPanel();
                itterationsArea.setLayout(new FlowLayout());
                JLabel itterationVal = new JLabel("500");

                JSlider sliderItteration = new JSlider(JSlider.HORIZONTAL, 1000, 500);
                sliderItteration.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                                itterationVal.setText(sliderItteration.getValue()+"");
                        }
                });


                itterationsArea.add(new JLabel("#Itterations"));
                itterationsArea.add(sliderItteration);
                itterationsArea.add(itterationVal);


                JPanel mateArea = new JPanel();
                itterationsArea.setLayout(new FlowLayout());

                JLabel mateVal = new JLabel("500");

                JSlider sliderMate = new JSlider(JSlider.HORIZONTAL, 1000, 500);
                sliderItteration.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                                mateVal.setText(sliderMate.getValue()+"");
                        }
                });


                mateArea.add(new JLabel("# to Mate"));
                mateArea.add(sliderMate);
                mateArea.add(mateVal);

                slidersArea.add(itterationsArea);
                slidersArea.add(popArea);
                slidersArea.add(mateArea);


                JPanel buttons = new JPanel();
                buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
               //buttons.getLayout().

                JButton reset = new JButton("Reset");
                reset.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                sliderPop.setValue(500);
                                sliderItteration.setValue(500);
                        }
                });
                JButton start = new JButton("Start");
                JButton button3 = new JButton("Cancel");
                buttons.add(reset);

                buttons.add(start);
                start.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                if (link == null) {
                                        JOptionPane.showMessageDialog(null, "Load a file first");
                                } else {
                                        link.createGeneticSolution();
                                }
                        }
                });
                buttons.add(button3);


                importantBits.add(slidersArea);
                importantBits.add(buttons);

                panel.add(importantBits);
                panel.validate();

                return panel;

        }


        private JPanel makeSaSettings() {
                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());

                JPanel topBit = new JPanel();
                topBit.setLayout(new FlowLayout());
                topBit.add(new JLabel("Simulated Annealing Settings"));

                panel.add(topBit, BorderLayout.NORTH);

                JPanel slidersArea = new JPanel();
                slidersArea.setLayout(new BoxLayout(slidersArea, BoxLayout.Y_AXIS));


                JPanel temperatureArea = new JPanel();
                temperatureArea.setLayout(new FlowLayout());

                JLabel temperatureVal = new JLabel("500");

                JSlider sliderTemperature = new JSlider(JSlider.HORIZONTAL, 1000, 500);
                sliderTemperature.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                                temperatureVal.setText(sliderTemperature.getValue()+"");
                        }
                });


                temperatureArea.add(new JLabel("Initial Temperature"));
                temperatureArea.add(sliderTemperature);
                temperatureArea.add(temperatureVal);


                JPanel decrementArea = new JPanel();
                temperatureArea.setLayout(new FlowLayout());

                JLabel decrementVal = new JLabel("1.0");

                JSlider sliderDecrement = new JSlider(JSlider.HORIZONTAL, 2000, 500);
                sliderDecrement.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                                decrementVal.setText(((double)sliderDecrement.getValue()/1000)+"");
                        }
                });


                decrementArea.add(new JLabel("Temp Decrement Amount"));
                decrementArea.add(sliderDecrement);
                decrementArea.add(decrementVal);

                slidersArea.add(temperatureArea);
                slidersArea.add(decrementArea);

                JPanel buttons = new JPanel();
                buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

                JButton reset = new JButton("Reset");
                reset.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                sliderTemperature.setValue(500);
                        }
                });

                JButton start = new JButton("Start");
                start.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                if (link==null) {
                                        JOptionPane.showMessageDialog(null, "Load a file first");
                                } else {
                                        int temp = Integer.parseInt(temperatureVal.getText());
                                        double dec = Double.parseDouble(decrementVal.getText());
                                        link.createAnnealedSolution(temp, dec);
                                }
                        }
                });
                JButton button3 = new JButton("Cancel");

                buttons.add(reset);
                buttons.add(start);
                buttons.add(button3);




                panel.add(slidersArea, BorderLayout.WEST);
                panel.add(buttons, BorderLayout.EAST);
                return panel;

        }

        private JPanel makeOutputArea() {
                JPanel panel = new JPanel();
                panel.setLayout(new FlowLayout());
                JButton buttonSave = new JButton("Save");
                statusLabel = new JLabel("Not Running");

                buttonSave.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                int returnVal = fc.showSaveDialog(frame);

                                if (returnVal == JFileChooser.APPROVE_OPTION) {
                                        File file = fc.getSelectedFile();
                                        //This is where a real application would open the file.
                                        if (!link.createOutputFile(file)) {
                                                JOptionPane.showMessageDialog(null, "Create A Solution First");
                                        } else {
                                                System.out.println(file.getName() +" choosen for saving");
                                        }
                                } else {
                                        System.out.println("Fail");
                                }

                        }
                });

                JButton buttonQuit = new JButton("Quit");

                buttonQuit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                if (isRunning) {
                                        JOptionPane.showMessageDialog(null,"Please wait until algorithm finished or cancel it.");
                                } else {
                                        frame.dispose();
                                }
                        }
                });
                panel.add(statusLabel);
                panel.add(buttonSave);
                panel.add(buttonQuit);
                return panel;
        }


        public static void main(String[] args) {
                new Gui();
        }

        @Override
        public void setStatus(String status) {
                statusLabel.setText(status);
                frame.pack();
                frame.validate();
        }

        @Override
        public void updateProgressBar(int i) {

        }

        @Override
        public void resetProgressBar() {

        }
}
*/
