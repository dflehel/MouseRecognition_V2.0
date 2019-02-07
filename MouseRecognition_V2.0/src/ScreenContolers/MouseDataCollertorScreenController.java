/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScreenContolers;

import Settings.DataCollectorSettings;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import mousedatacollector.MouseDataCollector;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

/**
 * FXML Controller class
 *
 * @author Denes
 */
public class MouseDataCollertorScreenController implements Initializable {

    @FXML
    private ImageView im2;
    @FXML
    private ListView listfiles;

    private MouseDataCollector collector;
    @FXML
    private Label actionsnum;

    @FXML
    private Label onoff;

    private Thread dataCollecting;

    @FXML
    private Button start;

    @FXML
    private Button stop;

    private DataCollectorSettings d = new DataCollectorSettings();

    @FXML
    public void pressStartButton(ActionEvent event) {
        Image image = new Image("Pictures/activepicture.png");
         this.start.setDisable(true);
        this.stop.setDisable(false);
        this.im2.setImage(image);
        this.onoff.setText("Active");
        this.dataCollecting = new Thread("DataCollector") {
            public void run() {
                DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss");
                Date date = new Date();

                String s = dateFormat.format(date);

                try {
                    FileWriter files = new FileWriter("Collected Data/New Data/" + s + ".CSV");
                    files.append("client timestamp,button,state,x,y\n");

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

                    // Construct the example object.
                    collector = new MouseDataCollector();
                    collector.setWrite(files);
                    collector.setActionsnum(actionsnum);
                    // Add the appropriate listeners.
                    GlobalScreen.addNativeMouseListener(collector);
                    GlobalScreen.addNativeMouseMotionListener(collector);
                    GlobalScreen.addNativeMouseWheelListener(collector);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // if you change the UI, do it here !
                            listfiles();
                        }
                    });
                } catch (Exception e) {
                    Logger.getLogger(MouseDataCollector.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        };
        this.dataCollecting.start();

    }

    @FXML
    public void pressStopButton(ActionEvent event) {
        Image image = new Image("Pictures/inactivepicture.png");
        this.im2.setImage(image);
        GlobalScreen.removeNativeMouseListener(collector);
        GlobalScreen.removeNativeMouseMotionListener(collector);
        GlobalScreen.removeNativeMouseWheelListener(collector);
        this.collector.stop();
        this.dataCollecting.stop();
        DataCollectorSettings.savedata();
         this.start.setDisable(false);
        this.stop.setDisable(true);
        this.onoff.setText("Inactive");
    }

    private void listfiles() {
        File folder = new File("Collected Data/New Data");
        File[] listOfFiles = folder.listFiles();
        this.listfiles.setEditable(false);
        this.listfiles.getItems().clear();
        for (int i = 0; i < listOfFiles.length; i++) {
            this.listfiles.getItems().add(listOfFiles[i]);
        }
        DataCollectorSettings.loaddata();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image("Pictures/inactivepicture.png");
        this.im2.setImage(image);
        this.listfiles();
        this.actionsnum.setText(DataCollectorSettings.numberofactions + " / " + DataCollectorSettings.minimumofmouseactions);
        // TODO

    }

}
