/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScreenContolers;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mousedatacollector.MouseDataCollertor;
import mouserecognition.DFLRandomForestClassifier;
import mouserecognition.DataCollector;
import mouserecognition.Display;
import mouserecognition.Extraction;
import mouserecognition.IClassifier;
import mouserecognition.IEvent;
import mouserecognition.IFeature;
import mouserecognition.MouseRecognition;
import mouserecognition.Settings;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

/**
 * FXML Controller class
 *
 * @author Denes
 */
public class MouseDataTestingScreenController implements Initializable {

    private ToggleGroup group;
    @FXML
    private RadioButton modelradiobutton;
    @FXML
    private ImageView im2;
    @FXML
    private RadioButton fileradiobutton;
    private Thread featureExtractionThread;
    private Thread dataCollectorThread;

    @FXML
    private ProgressBar progressbar;
    
    @FXML
    private ListView listview;
    
    @FXML
    private Label scorrelabel;
    
    /**
     * Initializes the controller class.
     */
    private void creatradiogroup() {
        group = new ToggleGroup();
        this.modelradiobutton.setToggleGroup(group);
        this.fileradiobutton.setToggleGroup(group);
        this.modelradiobutton.setSelected(true);

    }

    @FXML
    public void pressStartButton(ActionEvent event) {
        Image image = new Image("Pictures/activepicture.png");
        this.im2.setImage(image);
         DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss");
        Date date = new Date();

        String ss = dateFormat.format(date);
        Queue<ArrayList<IEvent>> events = new LinkedList<ArrayList<IEvent>>();
        FileWriter fileWriter;
        Display d;
    //    d = new Display();
    //    d.setVisible(true);
        try {
            fileWriter = new FileWriter("kimenet_" + ss + ".csv");
            fileWriter.append(Settings.FILE_HEADER.toString());
            Queue<IFeature> moves = new LinkedList<IFeature>();
            
          /*  Thread classificationThread = new Thread("Classifier") {
                public void run() {
                    System.out.println("run by: " + getName());
                    IClassifier classifier = new DFLRandomForestClassifier(moves, fileWriter, d);
                      //   classifier.setFile(fileWriter);
                     //    classifier.setMoves(moves);
                     //   classifier.setDisplay(d);
                    classifier.classify();
                }
            };*/

            this.featureExtractionThread = new Thread("Feature extractor") {
                public void run() {
                    System.out.println("run by: " + getName());
                    Extraction extraction = new Extraction();
                    extraction.setEventslist(events);
                    extraction.setMoves(moves);
                    IClassifier classifier = null;
                    if(fileradiobutton.isSelected()){
                            classifier = new DFLRandomForestClassifier(moves, fileWriter,progressbar,scorrelabel,listview,0);
                    }
                    else{
                        classifier = new DFLRandomForestClassifier(moves, fileWriter,progressbar,scorrelabel,listview,1);
                    }
                   // classifier.setFile(fileWriter);
                  //  classifier.setMoves(moves);
                  //  classifier.setDisplay(d);
                    extraction.setClassifier(classifier);
                    extraction.featuresextraction();
                }
            };

            this.dataCollectorThread = new Thread("Data collector") {
                public void run() {
                    try {
                        GlobalScreen.registerNativeHook();
                        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
                        logger.setLevel(Level.OFF);

                        // Don't forget to disable the parent handlers.
                        logger.setUseParentHandlers(false);
                    } catch (NativeHookException ex) {
                        System.err.println("There was a problem registering the native hook.");
                        System.err.println(ex.getMessage());
                        System.exit(1);
                    }

                    DataCollector datacollector = new DataCollector();
                    datacollector.setEvents(events);
                    datacollector.setT(featureExtractionThread);
                    GlobalScreen.addNativeMouseListener(datacollector);
                    GlobalScreen.addNativeMouseMotionListener(datacollector);
                    GlobalScreen.addNativeMouseWheelListener(datacollector);
                    System.out.println("run by: " + getName());
                }
            };
        //    classificationThread.start();
         this.dataCollectorThread.start();
          this.featureExtractionThread.start();
        //    classificationThread.join();
   
        }  catch (IOException ex) {
            Logger.getLogger(MouseRecognition.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void pressStopButton(ActionEvent event) {
        Image image = new Image("Pictures/inactivepicture.png");
        this.im2.setImage(image);
        this.dataCollectorThread.stop();
        this.featureExtractionThread.stop();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.creatradiogroup();
    }

}
