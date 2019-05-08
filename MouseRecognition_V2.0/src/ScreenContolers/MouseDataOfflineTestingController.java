/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScreenContolers;

import Settings.GlobalSettings;
import java.awt.Component;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.swing.JFileChooser;
import mouserecognition.DFLRandomForestClassifier;
import mouserecognition.IClassifier;
import offlinetesting.OfflineTessting;


/**
 * FXML Controller class
 *
 * @author Denes
 */
public class MouseDataOfflineTestingController implements Initializable {

    /**
     * Initializes the controller class.
     */
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
    
    @FXML
    private TextField number;
    
    @FXML
    public void pressClrear(ActionEvent event){
        this.chart.getData().clear();
    }
    
    @FXML
    public void pressStartButton(ActionEvent event) {
          JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        Component parent = null;
        GlobalSettings.seriesoffline = new XYChart.Series<>();
        this.chart.getData().add(GlobalSettings.seriesoffline);
        int returnVal = chooser.showOpenDialog(parent);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("safds");
            OfflineTessting testing = new OfflineTessting(chooser.getSelectedFile().getAbsolutePath());
             IClassifier classifier = null;
             System.out.println("safds");
                    classifier = new DFLRandomForestClassifier(null, null, null, null, 1);
             classifier.classify(testing.getMoves(), Integer.parseInt(number.getText()));
               System.out.println("safds");
        }
         
        }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.chart.getData().add(GlobalSettings.seriesoffline);
    }    
    
}
