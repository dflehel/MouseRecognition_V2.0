/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signaturerecognation;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
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
public class User_Signature_Model_Builder {
    
     private double[] values;
         private RandomForest randomforest;
    private ArrayList<Double> output = new ArrayList<>();
    private ArrayList<Attribute> att = new ArrayList<>();
    private Instances instances;
    private Instances toclassify;

    public User_Signature_Model_Builder(double[] values) {
        this.values = values;
        this.loadodel();
    }
    
    

    public User_Signature_Model_Builder() throws Exception {
          BufferedReader datafile = null;
        try {
            //datafile = new BufferedReader(new FileReader("chaoshencont_39feat_PC_DD_1000.arff"));
            //datafile = new BufferedReader(new FileReader("javasanmegszurt1.arff"));
            datafile = new BufferedReader(new FileReader("ttrain.arff"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(User_Signature_Model_Builder.class.getName()).log(Level.SEVERE, null, ex);
        }

        Instances data = null;
        try {
            data = new Instances(datafile);
        } catch (IOException ex) {
            Logger.getLogger(User_Signature_Model_Builder.class.getName()).log(Level.SEVERE, null, ex);
        }
        data.setClassIndex(data.numAttributes() - 1);

       FilteredClassifier f = new FilteredClassifier();
        RandomForest  classifier = new RandomForest();
        classifier.setNumFeatures(23);
        classifier.setMaxDepth(10);
        classifier.buildClassifier(data);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("betoltes.model")));
        objectOutputStream.writeObject(classifier);
        objectOutputStream.flush();
        objectOutputStream.close();
     
        
        Evaluation eval = new Evaluation(data);
        eval.crossValidateModel(classifier, data, 10, new Random(1));
        System.out.println(eval.toSummaryString());
        System.out.println("**********");

        for( int i=0; i<2; ++i){
            System.out.println("AUC "+(i)+": " + eval.areaUnderROC(i));
        }
    }
    
    public void loadodel(){
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
           this.values =   A.getFeatures(img);
           double movesProbs[] = new double[2];
           Instance instance = new DenseInstance(1.0,this.values);
         
          instance.setDataset(this.instances);
          double[] probs = this.randomforest.distributionForInstance(instance);
                        movesProbs[1] += probs[1];
                        System.out.println(this.randomforest.classifyInstance(instance));
                        System.out.println(". Time: " + Calendar.getInstance().getTime() + "\tProbability: " + probs[1]);
         } catch (IOException ex) {
             Logger.getLogger(User_Signature_Model_Builder.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(User_Signature_Model_Builder.class.getName()).log(Level.SEVERE, null, ex);
         }


    }

    @Override
    public String toString() {
        return "User_Signature_Model_Builder{" + "values=" + values + '}';
    }
    
    
}
