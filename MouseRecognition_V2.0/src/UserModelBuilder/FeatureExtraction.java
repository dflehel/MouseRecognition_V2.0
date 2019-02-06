/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserModelBuilder;

import Settings.ClassifierSettings;
import Settings.UserModelBuilderSettings;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import mouserecognition.IEvent;
import mouserecognition.IFeature;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import mouserecognition.DFLDatasetEvent;
import mouserecognition.LehelFeature;
import mouserecognition.TeacherFeature;
import weka.core.Instance;

/**
 *
 * @author Denes
 */
public class FeatureExtraction {

    private RandomForest model;
    private Instances data;
    private ArrayList<IEvent> events = new ArrayList<>();

    public FeatureExtraction(RandomForest model, Instances data) {
        this.model = model;
        this.data = data;
    }

    public Instances getData() {
        return data;
    }

    public void setData(Instances data) {
        this.data = data;
    }

    public FeatureExtraction() {
    }

    public RandomForest getModel() {
        return model;
    }

    public void setModel(RandomForest model) {
        this.model = model;
    }

    public void makedataforcreation() {

        //Creat model
        if (UserModelBuilderSettings.whicmod == 0) {
            this.usnewdata();
        } else {
            this.useolddata();
            this.usnewdata();
        }
    }

    private void usnewdata() {
        IFeature feature = null;
        if (UserModelBuilderSettings.whichfeature == 0) {
            feature = new LehelFeature();
        } else {
            feature = new TeacherFeature();
        }
        File dir = new File("Collected Data/New Data");
        File dircopy = new File("/Collected Data/Old Data");
        File[] files = dir.listFiles();
        for (File file : files) {
            Scanner scanner;
            try {
                scanner = new Scanner(file);
                scanner.nextLine();
                while (scanner.hasNextLine()) {
                    String[] line = scanner.nextLine().split(",");
                    IEvent event = new DFLDatasetEvent();
                    event.setTime(Long.parseLong(line[0]));
                    event.setButtonype(line[1]);
                    event.setActiontype(line[2]);
                    event.setX(Integer.parseInt(line[3]));
                    event.setY(Integer.parseInt(line[4]));
                    events.add(event);
                    if (event.getActiontype().equalsIgnoreCase("Pressed")) {
                        if (this.events.size() > ClassifierSettings.NUM_EVENTS) {
                            feature.ExtractFeatures(events);
                            Instance ins = feature.getInstance();
                            ins.setValue(23, 1.0);
                            this.data.add(ins);
                            this.events = new ArrayList<>();
                        }
                    }
                    if (event.getActiontype().equalsIgnoreCase("Released")) {
                        if (this.events.size() > ClassifierSettings.NUM_EVENTS) {
                            feature.ExtractFeatures(events);
                            Instance ins = feature.getInstance();
                            ins.setValue(23, 1.0);
                            this.data.add(ins);

                            this.events = new ArrayList<>();
                        }
                    }
                    if (event.getActiontype().equalsIgnoreCase("Move")) {
                        if (this.events.size() > ClassifierSettings.NUM_EVENTS) {
                            if ((this.events.get(this.events.size() - 2).getTime() - event.getTime()) > ClassifierSettings.TIME_THRESHOLD) {
                                feature.ExtractFeatures(events);
                                Instance ins = feature.getInstance();
                                ins.setValue(23, 1.0);
                                this.data.add(ins);
                                this.events = new ArrayList<>();
                            }
                        }
                    }

                }
                scanner.close();
                Files.move(Paths.get(file.getAbsolutePath()), Paths.get(file.getParentFile().getParentFile().getAbsoluteFile() + "/Old Data/" + file.getName()), StandardCopyOption.REPLACE_EXISTING);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FeatureExtraction.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FeatureExtraction.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void useolddata() {
        IFeature feature = null;
        if (UserModelBuilderSettings.whichfeature == 0) {
            feature = new LehelFeature();
        } else {
            feature = new TeacherFeature();
        }
        File dir = new File("Collected Data/Old Data");
        File[] files = dir.listFiles();
        for (File file : files) {
            Scanner scanner;
            try {
                scanner = new Scanner(file);
                scanner.nextLine();
                while (scanner.hasNextLine()) {
                    String[] line = scanner.nextLine().split(",");
                    IEvent event = new DFLDatasetEvent();
                    event.setTime(Long.parseLong(line[0]));
                    event.setButtonype(line[1]);
                    event.setActiontype(line[2]);
                    event.setX(Integer.parseInt(line[3]));
                    event.setY(Integer.parseInt(line[4]));
                    events.add(event);
                    if (event.getActiontype().equalsIgnoreCase("Pressed")) {
                        if (this.events.size() > ClassifierSettings.NUM_EVENTS) {
                            feature.ExtractFeatures(events);
                            Instance ins = feature.getInstance();
                            ins.setValue(23, 1.0);
                            this.data.add(ins);
                            this.events = new ArrayList<>();
                        }
                    }
                    if (event.getActiontype().equalsIgnoreCase("Released")) {
                        if (this.events.size() > ClassifierSettings.NUM_EVENTS) {
                            feature.ExtractFeatures(events);
                            Instance ins = feature.getInstance();
                            ins.setValue(23, 1.0);
                            this.data.add(ins);
                            this.events = new ArrayList<>();
                        }
                    }
                    if (event.getActiontype().equalsIgnoreCase("Move")) {
                        if (this.events.size() > ClassifierSettings.NUM_EVENTS) {
                            if ((this.events.get(this.events.size() - 2).getTime() - event.getTime()) > ClassifierSettings.TIME_THRESHOLD) {
                                feature.ExtractFeatures(events);
                                Instance ins = feature.getInstance();
                                ins.setValue(23, 1.0);
                                this.data.add(ins);
                                this.events = new ArrayList<>();
                            }
                        }
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FeatureExtraction.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}