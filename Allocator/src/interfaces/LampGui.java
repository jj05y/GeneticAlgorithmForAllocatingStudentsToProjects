package interfaces;



import listeners.InterfaceCallBackListener;
import links.Link;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LampGui implements InterfaceCallBackListener {


        public static final int PROG_BAR_SIZE = 20;
        private static final int OPTIMAL_POP_SIZE = 2000;
        private static final int OPTIMAL_TOP_N = 650;
        private static final int OPTIMAL_NUM_GEN = 120;
        private static final int OPTIMAL_INITIAL_TEMP = 3000;
        private static final double OPTIMAL_DECREMENT_AMOUNT = 0.5;


        private JFrame frame;
        private JLabel statusLabel;
        private JFileChooser fc;
        private Link link;
        private JPanel[] progressBar;
        private Thread worker;


        public LampGui() {
                InterfaceCallBackListener cbl = this;
                fc = new JFileChooser();
                progressBar = new JPanel[PROG_BAR_SIZE];
                worker= new Thread();

                statusLabel = new JLabel("NOTHING IS HAPPENING");

                frame = new JFrame("LAMP");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setLayout(new BorderLayout());

                JPanel north = new JPanel();
                north.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
                JButton load = new JButton("Load Preference File");
                load.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                int returnVal = fc.showOpenDialog(frame);

                                if (returnVal == JFileChooser.APPROVE_OPTION) {
                                        File file = fc.getSelectedFile();
                                        link = new Link(file.getAbsolutePath(), cbl);

                                }
                        }
                });
                JButton go = new JButton("    Create Solution    ");
                go.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                if (link == null) {
                                        JOptionPane.showMessageDialog(null, "Load a file first");
                                } else if (!worker.isAlive()) {
                                        resetProgressBar();
                                        worker = new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                        link.createHybridGeneticSolution(
                                                                OPTIMAL_NUM_GEN,
                                                                OPTIMAL_TOP_N,
                                                                OPTIMAL_POP_SIZE,
                                                                OPTIMAL_INITIAL_TEMP,
                                                                OPTIMAL_DECREMENT_AMOUNT);
                                                }
                                        });
                                        worker.start();

                                } else {
                                        JOptionPane.showMessageDialog(null, "Please wait...");
                                }
                        }
                });
                JButton save = new JButton("Save Solution");
                save.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                if (!worker.isAlive()) {
                                        if (link != null && link.hasSolution()) {
                                                fc.setSelectedFile(new File("Student Mappings.tsv"));
                                                int returnVal = fc.showSaveDialog(frame);
                                                if (returnVal == JFileChooser.APPROVE_OPTION) {
                                                        File file = fc.getSelectedFile();
                                                        int result = link.createOutputFile(file);
                                                        if (result == Link.NO_FILE) {
                                                                JOptionPane.showMessageDialog(null, "File not opened");
                                                        } else if (result == Link.SUCCESS) {
                                                                JOptionPane.showMessageDialog(null,file.getName()+" saved");
                                                        }
                                                }
                                        } else {
                                                JOptionPane.showMessageDialog(null, "Create a solution first");
                                        }
                                } else {
                                        JOptionPane.showMessageDialog(null, "Please wait...");
                                }
                        }
                });

                north.add(load);
                north.add(go);
                north.add(save);


                JPanel pBar = new JPanel();
                pBar.setLayout(new FlowLayout(FlowLayout.CENTER,0,1));
                for (int i =0 ; i<progressBar.length;i++) {
                        progressBar[i] = new JPanel();
                        progressBar[i].setPreferredSize(new Dimension(400/PROG_BAR_SIZE,25));
                        progressBar[i].setBackground(Color.WHITE);
                        pBar.add(progressBar[i]);
                }

                JPanel centre = new JPanel();
                centre.setLayout(new FlowLayout(FlowLayout.CENTER));
                centre.add(statusLabel);

                frame.add(north, BorderLayout.NORTH);
                frame.add(centre, BorderLayout.CENTER);
                frame.add(pBar, BorderLayout.SOUTH);

                frame.pack();

                frame.setVisible(true);
        }


        public static void main(String[] args) {
                new LampGui();
        }

        @Override
        public void setStatus(String status) {
                statusLabel.setText(status);
                frame.pack();
                frame.validate();
        }

        public void updateProgressBar(int i) {
                progressBar[i].setBackground(Color.GREEN);

        }
        public void resetProgressBar() {
                for (JPanel p : progressBar) {
                        p.setBackground(Color.WHITE);
                }
        }
}
