/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScreenContolers;

import Settings.GlobalSettings;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.CheckBox;

/**
 * FXML Controller class
 *
 * @author Denes
 */
public class SignatureUpdateScreenController implements Initializable {

   @FXML
    private LineChart chart;
    
    @FXML
    private CheckBox isupdating;
    
    
    @FXML
    public void bospressed(ActionEvent event) {
        GlobalSettings.updatingsignature = this.isupdating.isSelected();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
  
       
        this.chart.getData().add(GlobalSettings.seriessignature);
        System.out.println(GlobalSettings.seriessignature.getData().toString());
    }    
}
