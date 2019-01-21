/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signaturerecognation;

import Settings.GlobalSettings;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javax.imageio.ImageIO;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.instance.imagefilter.EdgeHistogramFilter;

/**
 *
 * @author Denes
 */
public class SignatureClassifier {

    private double[] values;
    private RandomForest randomforest;
    private ArrayList<Double> output = new ArrayList<>();
    private ArrayList<Attribute> att = new ArrayList<>();
    private Instances instances;
    private Instances toclassify;
    private ProgressBar bar;
    private Label lab;
    private static int counter = 0;

    public SignatureClassifier(ProgressBar bar, Label lab) {
        this.bar = bar;
        this.lab = lab;
    }

    public void loadodel() {
        try {
            ObjectInputStream objectInputStream
                    = new ObjectInputStream(new FileInputStream("betoltes.model"));
            this.randomforest = (RandomForest) objectInputStream.readObject();
            this.randomforest.setNumFeatures(23);
            this.randomforest.setMaxDepth(10);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(User_Signature_Model_Builder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(User_Signature_Model_Builder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(User_Signature_Model_Builder.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            this.instances = new Instances(new BufferedReader(new FileReader("h2.arff")));
            this.instances.setClassIndex(this.instances.numAttributes() - 1);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(User_Signature_Model_Builder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(User_Signature_Model_Builder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void testsignature(String file) {
        try {
            EdgeHistogramFilter histo = new EdgeHistogramFilter();
            BufferedImage img = null;
            img = ImageIO.read(new File(file));
            a A = new a();
            this.values = A.getFeatures(img);
            double movesProbs[] = new double[2];
            Instance instance = new DenseInstance(1.0, this.values);

            instance.setDataset(this.instances);
            double[] probs = this.randomforest.distributionForInstance(instance);
            movesProbs[1] += probs[1];
            System.out.println(this.randomforest.classifyInstance(instance));
            System.out.println(". Time: " + Calendar.getInstance().getTime() + "\tProbability: " + probs[1]);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // if you change the UI, do it here !
                    bar.setProgress(probs[1]);
                    lab.setText(probs[1]*100 + "%");
                
                }
            });
             GlobalSettings.seriessignature.getData().add(new XYChart.Data<>(counter+"",probs[1]*100 ));
             counter =counter+1;
        } catch (IOException ex) {
            Logger.getLogger(User_Signature_Model_Builder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(User_Signature_Model_Builder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
