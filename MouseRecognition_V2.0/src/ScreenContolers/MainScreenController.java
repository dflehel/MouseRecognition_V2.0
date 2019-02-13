/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScreenContolers;

import Settings.DataCollectorSettings;
import Settings.GlobalSettings;
import java.awt.Panel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Denes
 */
public class MainScreenController implements Initializable {

    @FXML
    private AnchorPane displaypanel;

    // @FXML
    //  private TreeView menu;
    @FXML
    private ProgressBar progressbarforprobs;

    @FXML
    private Pane f;

    @FXML
    private Button datacollector;

    @FXML
    private Button buildmodel;

    @FXML
    private Button testmodel;

    /**
     * Initializes the controller class.
     *
     */
    private void setbuttons() {
        this.datacollector.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                startDataCollectingMouseData();
            }
        });

        this.buildmodel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                startModelBuilderMouseData();
            }
        });

        this.testmodel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                startMouseTest();
            }
        });
    }

    private void startSignatureupdate() {
        try {
            Pane anchorpane = FXMLLoader.load(getClass().getResource("/Screen/SignatureUpdateScreen.fxml"));
            this.f.getChildren().setAll(anchorpane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startSignatureTest() {
        try {
            Pane anchorpane = FXMLLoader.load(getClass().getResource("/Screen/SignatureRecognitionTestScreen.fxml"));
            this.f.getChildren().setAll(anchorpane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startSignatureDataModelBuilder() {
        try {
            Pane anchorpane = FXMLLoader.load(getClass().getResource("/Screen/SignatureMakeModelScreen.fxml"));
            this.f.getChildren().setAll(anchorpane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startSignatureDatacollecting() {
        try {
            Pane anchorpane = FXMLLoader.load(getClass().getResource("/Screen/SignatureDataCollectingScreen.fxml"));
            this.f.getChildren().setAll(anchorpane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startUpdateMouse() {
        try {
            Pane anchorpane = FXMLLoader.load(getClass().getResource("/Screen/UpdateModelMouseData.fxml"));

            this.f.getChildren().setAll(anchorpane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startMouseTest() {
        try {
            Pane anchorpane = FXMLLoader.load(getClass().getResource("/Screen/MouseDataTestingScreen.fxml"));
            boolean firsttime = true;

            for (Object o : this.f.getChildren().toArray()) {
                AnchorPane a = (AnchorPane) o;
                if (a.getId().equalsIgnoreCase("mdt")) {
                    firsttime = false;
                    a.setVisible(true);
                } else {
                    a.setVisible(false);
                }
            }
            if (firsttime == true) {
                this.f.getChildren().addAll(anchorpane);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startDataCollectingMouseData() {
        try {
            Pane anchorpane = FXMLLoader.load(getClass().getResource("/Screen/MouseDataCollertorScreen.fxml"));
            boolean firsttime = true;

            for (Object o : this.f.getChildren().toArray()) {
                AnchorPane a = (AnchorPane) o;
                if (a.getId().equalsIgnoreCase("mdc")) {
                    firsttime = false;
                    a.setVisible(true);
                } else {
                    a.setVisible(false);
                }
            }
            if (firsttime == true) {
                this.f.getChildren().addAll(anchorpane);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startModelBuilderMouseData() {

        try {
            System.out.println("1");
            Pane anchorpane = FXMLLoader.load(getClass().getResource("/Screen/MouseDataUserModelBulderScreen.fxml"));
            System.out.println("2");
            boolean firsttime = true;

            for (Object o : this.f.getChildren().toArray()) {
                AnchorPane a = (AnchorPane) o;
                if (a.getId().equalsIgnoreCase("mdmb")) {
                    firsttime = false;
                    a.setVisible(true);
                } else {
                    a.setVisible(false);
                }
            }
            if (firsttime == true) {
                this.f.getChildren().addAll(anchorpane);
            }
            System.out.println("3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.setbuttons();
        DataCollectorSettings.loaddata();
    }

}
