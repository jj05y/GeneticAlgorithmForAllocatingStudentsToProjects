package Interfaces;



import CallBackListeners.LinkCallBackListener;
import Links.Link;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SimpleGui implements LinkCallBackListener {


        public static final int PROG_BAR_SIZE = 10;
        private JFrame frame;
        private JLabel statusLabel;
        private JFileChooser fc;
        private Link link;
        private JPanel[] progressBar;
        int amountComplete;
        Thread worker;


        public SimpleGui() {
                LinkCallBackListener cbl = this;
                fc = new JFileChooser();
                //TODO CONSTNATS and tie into genetic
                progressBar = new JPanel[PROG_BAR_SIZE];
                amountComplete = 0;
                worker= new Thread();

                statusLabel = new JLabel("NOTHING IS HAPPENING");

                frame = new JFrame("LAMP");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setLayout(new BorderLayout());


                JPanel north = new JPanel();
                north.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
                JButton load = new JButton("Load");
                load.addActionListener(new ActionListener() {
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
                JButton go = new JButton("     GO     ");
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
                                                        link.createGeneticSolution();
                                                }
                                        });
                                        worker.start();

                                }
                        }
                });
                JButton save = new JButton("Save");
                save.addActionListener(new ActionListener() {
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


                north.add(load);
                north.add(go);
                north.add(save);





                JPanel pBar = new JPanel();
                pBar.setLayout(new FlowLayout(FlowLayout.CENTER,0,1));
                for (int i =0 ; i<progressBar.length;i++) {
                        progressBar[i] = new JPanel();
                        progressBar[i].setPreferredSize(new Dimension(300/PROG_BAR_SIZE,25));
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
                new SimpleGui();
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
