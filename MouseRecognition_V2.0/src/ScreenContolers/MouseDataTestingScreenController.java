/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScreenContolers;

import Settings.GlobalSettings;
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
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mousedatacollector.MouseDataCollector;
import mouserecognition.DFLRandomForestClassifier;
import mouserecognition.DataCollector;
import mouserecognition.Extraction;
import mouserecognition.IClassifier;
import mouserecognition.IEvent;
import mouserecognition.IFeature;
import Settings.ClassifierSettings;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.Notifications;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

/**
 * FXML Controller class
 *
 * @author Denes
 */
public class MouseDataTestingScreenController implements Initializable {

    @FXML
    private ImageView activationstateimag;
    private Thread featureExtractionThread;
    private Thread dataCollectorThread;

    private MaskerPane masker = new MaskerPane();

    @FXML
    private AnchorPane root;

    @FXML
    private Label onoff;
    @FXML
    private ProgressBar progressbar;

    @FXML
    private ListView listview;

    @FXML
    private Label scorrelabel;

    @FXML
    private LineChart chart;

    @FXML
    private Button start;

    @FXML
    private Button stop;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void pressStartButton(ActionEvent event) {
        Image image = new Image("Pictures/activepicture.png");
        this.masker.setVisible(true);
        this.start.setDisable(true);
        this.stop.setDisable(false);
        this.onoff.setText("Active");
        this.activationstateimag.setImage(image);
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss");
        Date date = new Date();

        String ss = dateFormat.format(date);
        Queue<ArrayList<IEvent>> events = new LinkedList<ArrayList<IEvent>>();
        FileWriter fileWriter;

        try {
            fileWriter = new FileWriter("kimenet_" + ss + ".csv");
            fileWriter.append(ClassifierSettings.FILE_HEADER.toString());
            Queue<IFeature> moves = new LinkedList<IFeature>();

         
            this.featureExtractionThread = new Thread("Feature extractor") {
                public void run() {
                    System.out.println("run by: " + getName());
                    Extraction extraction = new Extraction();
                    extraction.setEventslist(events);
                    extraction.setMoves(moves);
                    IClassifier classifier = null;

                    classifier = new DFLRandomForestClassifier(moves, fileWriter, progressbar, scorrelabel, 1);

                    extraction.setClassifier(classifier);
                    Platform.runLater(() -> {
                        masker.setVisible(false);

                        Notifications.create()
                                .title("Load finished")
                                .text("Your model was successfully loaded!")
                                .position(Pos.CENTER)
                                .showInformation();
                    });
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
                    System.out.println("run by: " + getName());
                }
            };
            //    classificationThread.start();
            this.dataCollectorThread.start();
            this.featureExtractionThread.start();
            //    classificationThread.join();

        } catch (IOException ex) {
            Logger.getLogger(MouseDataTestingScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void pressStopButton(ActionEvent event) {
        Image image = new Image("Pictures/inactivepicture.png");
        this.activationstateimag.setImage(image);
        this.onoff.setText("Inactive");
        this.dataCollectorThread.stop();
        this.featureExtractionThread.stop();
        this.start.setDisable(false);
        this.stop.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.chart.getData().add(GlobalSettings.series);
        this.masker.setVisible(false);
        Image image = new Image("Pictures/inactivepicture.png");
        this.activationstateimag.setImage(image);
        this.masker.setProgress(-1);
        this.masker.setText("Loading your model");
        this.masker.setMinWidth(633);
        this.masker.setMinHeight(395);
        this.root.getChildren().add(this.masker);
    }

}
