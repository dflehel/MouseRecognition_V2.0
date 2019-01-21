/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScreenContolers;

import UserModelBuilder.UserModelBuilder;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import mouserecognition.DFLRandomForestClassifier;
import mouserecognition.Extraction;
import mouserecognition.IClassifier;

/**
 * FXML Controller class
 *
 * @author Denes
 */
public class MouseDataUserModelBulderScreenController implements Initializable {

    @FXML
    private CheckBox box;

    @FXML
    private Label progrreslabel;

    @FXML
    private ProgressIndicator progressindicator;

    private File file;

    /**
     * Initializes the controller class.
     */
    public void pressCreatButton(ActionEvent event) {

        if (this.box.isSelected()) {

        } else {
            this.progressindicator.setVisible(true);
            this.progrreslabel.setVisible(true);
            Thread modelmakethread = new Thread("Model make") {
                public void run() {
                    new UserModelBuilder(file, progressindicator, progrreslabel,0);
                    Platform.runLater(() -> {
                        Stage dialog = new Stage();
                        dialog.initStyle(StageStyle.UTILITY);
                        Scene scene = new Scene(new Group(new Text(25, 25, "All is done!")));
                        dialog.setScene(scene);
                        dialog.showAndWait();
                    });

                }
            };
            modelmakethread.start();

        }

    }

    public void pressChoseButton(ActionEvent event) {
        FileChooser filechooser = new FileChooser();
        this.file = filechooser.showOpenDialog(null);
        if (this.file == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Hiba");
            alert.setHeaderText("Nem valasztot ki semmit");
            alert.setContentText("Sajnos igy nem tudok dolgozni :) !!");

            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

}
