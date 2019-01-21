/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScreenContolers;

import Settings.GlobalSettings;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;

/**
 * FXML Controller class
 *
 * @author Denes
 */
public class UpdateModelMouseDataController implements Initializable {

    
    @FXML
    private LineChart chart;
    
    @FXML
    private CheckBox isupdating;
    
    
    @FXML
    public void bospressed(ActionEvent event) {
        GlobalSettings.updating = this.isupdating.isSelected();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
  
       
        this.chart.getData().add(GlobalSettings.series);
    }    
    
}
