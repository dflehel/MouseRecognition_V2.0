/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserModelBuilder;

import Settings.UserModelBuilderSettings;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

/**
 *
 * @author manyi
 */
public class UserModelBuilder {

    private FeatureExtraction extracion;

    public UserModelBuilder(File negativafile, int which) {
        try {
            BufferedReader negativedatafile = null;
            try {
                //datafile = new BufferedReader(new FileReader("chaoshencont_39feat_PC_DD_1000.arff"));
                //datafile = new BufferedReader(new FileReader("javasanmegszurt1.arff"));
                negativedatafile = new BufferedReader(new FileReader(negativafile));

            } catch (FileNotFoundException ex) {
                Logger.getLogger(UserModelBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
            Instances data = null;
            Instances positivedata = null;
            try {
                data = new Instances(negativedatafile);

            } catch (IOException ex) {
                Logger.getLogger(UserModelBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }

            this.extracion = new FeatureExtraction();
            extracion.setData(data);
            extracion.makedataforcreation();
            data.setClassIndex(data.numAttributes() - 1);
            RandomForest classifier = new RandomForest();

            classifier.buildClassifier(data);
            // classifier.buildClassifier(positivedata);

            ObjectOutputStream objectOutputStream = null;
            if (which == 0) {
                objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("mousedataa.model")));
            } else {
                objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("signature.model")));
            }
            objectOutputStream.writeObject(classifier);
            objectOutputStream.flush();
            objectOutputStream.close();
            Evaluation eval = new Evaluation(data);
            eval.crossValidateModel(classifier, data, 10, new Random(1));
            System.out.println(eval.toSummaryString());
            System.out.println("**********");
            for (int i = 0; i < 2; ++i) {
                System.out.println("AUC " + (i) + ": " + eval.areaUnderROC(i));
            }
    

        } catch (Exception ex) {
            Logger.getLogger(UserModelBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static final int NUMUSERS = 21;



}