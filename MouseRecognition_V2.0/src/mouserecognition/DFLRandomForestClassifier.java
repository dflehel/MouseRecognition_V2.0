/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mouserecognition;

import Settings.ClassifierSettings;
import Settings.GlobalSettings;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import static Settings.ClassifierSettings.LOAD_MODEL;
import static Settings.ClassifierSettings.NUM_ACTIONS;
import static Settings.ClassifierSettings.NUM_TO_CLASSIFY;
import javafx.scene.paint.Color;
import weka.classifiers.UpdateableClassifier;
import weka.core.Instances;
import weka.core.Attribute;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;

/**
 *
 * @author Denes
 */
public class DFLRandomForestClassifier implements IClassifier {

    private RandomForest randomforest;
    private ArrayList<Double> output = new ArrayList<>();
    private ArrayList<Attribute> att = new ArrayList<>();
    private Instances instances;
    private Instances toclassify;
    private FileWriter file;
    private int counter = 0;

    private ProgressBar bar;
    private ListView list;
    private Label lab;
    private Queue<IFeature> moves = new LinkedList<IFeature>();

    private Queue<Double> probabilities = new LinkedBlockingDeque<>(NUM_TO_CLASSIFY);



    public DFLRandomForestClassifier(Queue<IFeature> moves, FileWriter file, ProgressBar bar, Label lab, int which) {
        this.file = file;
        this.moves = moves;
        this.bar = bar;
        this.lab = lab;
        if (which == 0) {
            BufferedReader datafile = null;
            try {
                datafile = new BufferedReader(new FileReader("training.arff"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
            }
            Instances data = null;
            try {
                data = new Instances(datafile);
            } catch (IOException ex) {
                Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
            }
            data.setClassIndex(data.numAttributes() - 1);
            this.randomforest = new RandomForest();
            try {
                this.randomforest.buildClassifier(data);
            } catch (Exception ex) {
                Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                ObjectInputStream objectInputStream
                        = new ObjectInputStream(new FileInputStream("betoltes.model"));
                this.randomforest = (RandomForest) objectInputStream.readObject();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            String f = "f";
            this.instances = new Instances(new BufferedReader(new FileReader("Headers/lehelfh.arff")));
            this.instances.setClassIndex(this.instances.numAttributes() - 1);
            this.toclassify = new Instances(new BufferedReader(new FileReader("Headers/lehelfh.arff")));
            this.toclassify.setClassIndex(this.instances.numAttributes() - 1);
            for (Integer i = 1; i < ClassifierSettings.DFL_NUM_FEATURES; ++i) {
                this.att.add(new Attribute(new String(f + i.toString() + " numeric")));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }




    public FileWriter getFile() {
        return file;
    }

    public void setFile(FileWriter file) {
        this.file = file;
    }

    public Queue<IFeature> getMoves() {
        return moves;
    }

    public void setMoves(Queue<IFeature> moves) {
        this.moves = moves;
    }

    public void classify() {
        while (true) {
            if (this.moves.size() < NUM_ACTIONS) {
                try {
                    synchronized (this.moves) {
                        System.out.println("[WaitingThread]: Waiting for another thread" + "to notify me...");
                        this.moves.wait();
                        System.out.println("[WaitingThread]: Successfully notified!");
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Extraction.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                double movesProbs[] = new double[2];
                int SIZE = this.moves.size();

                System.out.println("MOVES #actions: " + this.moves.size());
                for (IFeature f : this.moves) {
                    Instance instance = f.getInstance();
                    instance.setDataset(this.instances);
                    System.out.println(f.toString());
                    TeacherFeature tf = (TeacherFeature) f;

                    printEventData(tf);
                    try {
                        double[] probs = this.randomforest.distributionForInstance(instance);
                        movesProbs[1] += probs[1];
                        System.out.println((counter) + ". Time: " + Calendar.getInstance().getTime() + "\tProbability: " + probs[1]);
                    } catch (Exception ex) {
                        Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                TeacherFeature tf = null;
                if (ClassifierSettings.WHICH_FEATURES == 1) {
                    tf = (TeacherFeature) this.moves.element();
                }

                try {
                    double res = (movesProbs[1] / SIZE);
                    if (probabilities.size() < ClassifierSettings.NUM_TO_CLASSIFY) {
                        probabilities.add(res);
                    } else {
                        probabilities.poll();
                        probabilities.add(res);
                    }
                    double mean = computeMean(probabilities);
                    if (ClassifierSettings.WHICH_FEATURES == 1) {
                        this.file.append(tf.getAction_type() + ", " + res + "\n");
                    } else {
                        this.file.append(res + "\n");
                    }
                    Integer i = new Integer((int) (mean * 100));
                   this.file.flush();
                } catch (IOException ex) {
                    Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
                }

                ++counter;
                this.toclassify.clear();

                synchronized (this.moves) {
                    this.moves.poll();
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Classifier{" + "output=" + output + '}';
    }

    public DFLRandomForestClassifier() {
        //train classifier
        if (!LOAD_MODEL) {
            BufferedReader datafile = null;
            try {
                datafile = new BufferedReader(new FileReader("training.arff"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
            }
            Instances data = null;
            try {
                data = new Instances(datafile);
            } catch (IOException ex) {
                Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
            }
            data.setClassIndex(data.numAttributes() - 1);
            this.randomforest = new RandomForest();
            this.randomforest.setNumFeatures(ClassifierSettings.DFL_NUM_FEATURES);
            this.randomforest.setMaxDepth(10);
            try {
                this.randomforest.buildClassifier(data);
            } catch (Exception ex) {
                Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                ObjectInputStream objectInputStream
                        = new ObjectInputStream(new FileInputStream("betoltes.model"));
                this.randomforest = (RandomForest) objectInputStream.readObject();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            String f = "f";
            this.instances = new Instances(new BufferedReader(new FileReader("header.arff")));
            this.instances.setClassIndex(this.instances.numAttributes() - 1);
            this.toclassify = new Instances(new BufferedReader(new FileReader("header.arff")));
            this.toclassify.setClassIndex(this.instances.numAttributes() - 1);
            for (Integer i = 1; i < ClassifierSettings.DFL_NUM_FEATURES; ++i) {
                this.att.add(new Attribute(new String(f + i.toString() + " numeric")));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void printEventData(TeacherFeature f) {
        System.out.print("BEGIN*** ");
        for (int i = 0; i < f.getEvents().size(); ++i) {
            System.out.print(f.getEvents().get(i).getActiontype() + " ");

        }

        System.out.println("END***");
    }

    private double computeMean(Queue<Double> probabilities) {
        if (probabilities.isEmpty()) {
            return 0;
        }
        double avg = 0;
        for (double d : probabilities) {
            avg += d;
        }
        return avg / probabilities.size();
    }

    @Override
    public void classify(Queue<IFeature> moves) {
        double movesProbs[] = new double[2];
        int SIZE = this.moves.size();

        System.out.println("MOVES #actions: " + this.moves.size());
        for (IFeature f : this.moves) {
            Instance instance = f.getInstance();
            instance.setDataset(this.instances);
            this.instances.add(instance);
            System.out.println(f.toString());
            //  TeacherFeature tf = (TeacherFeature) f;

            //  printEventData(tf);
            try {
                double[] probs = this.randomforest.distributionForInstance(instance);
                movesProbs[1] += probs[1];
                if (GlobalSettings.updating) {

                    this.randomforest.buildClassifier(this.instances);
                    this.instances.delete();
                }
                System.out.println((counter) + ". Time: " + Calendar.getInstance().getTime() + "\tProbability: " + probs[1]);
            } catch (Exception ex) {
                Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        TeacherFeature tf = null;
        if (ClassifierSettings.WHICH_FEATURES == 1) {
            tf = (TeacherFeature) this.moves.element();
        }

        try {
            double res = (movesProbs[1] / SIZE);
            if (probabilities.size() < ClassifierSettings.NUM_TO_CLASSIFY) {
                probabilities.add(res);
            } else {
                probabilities.poll();
                probabilities.add(res);
            }
            double mean = computeMean(probabilities);
            if (ClassifierSettings.WHICH_FEATURES == 1) {
                this.file.append(tf.getAction_type() + ", " + res + "\n");
            } else {
                this.file.append(res + "\n");
            }
            Integer i = new Integer((int) (mean * 100));
            //this.display.setscore((counter) + ". Time: " + Calendar.getInstance().getTime() + "\tProbability: " + String.format("%.4f", mean), i)

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // if you change the UI, do it here !
                    bar.setProgress(mean);
                    if (i >= 50){
                      ClassifierSettings.setBarStyleClass(bar, ClassifierSettings.GREEN_BAR);
                    }
                    else{
                        ClassifierSettings.setBarStyleClass(bar, ClassifierSettings.RED_BAR);
                  }
                    lab.setText(i + "%");
                    GlobalSettings.series.getData().add(new XYChart.Data<>(counter + "", i));
                }
            });
            this.file.flush();
        } catch (IOException ex) {
            Logger.getLogger(DFLRandomForestClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }

        ++counter;
        this.toclassify.clear();

        synchronized (this.moves) {
            this.moves.poll();
        }
    }

}