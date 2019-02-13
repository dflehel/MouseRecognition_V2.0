/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScreenContolers;

import Settings.UserModelBuilderSettings;
import UserModelBuilder.UserModelBuilder;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import mouserecognition.DFLRandomForestClassifier;
import mouserecognition.Extraction;
import mouserecognition.IClassifier;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Denes
 */
public class MouseDataUserModelBulderScreenController implements Initializable {

    @FXML
    private CheckBox box;
    
    private MaskerPane masker = new MaskerPane();
    
    @FXML
    private AnchorPane root;




    private File file;

    @FXML
    private ComboBox selector;

    @FXML
    private RadioButton creat;

    @FXML
    private RadioButton update;
    
    @FXML
    private Button doit;

    private ToggleGroup group;

    /**
     * Initializes the controller class.
     */
    public void pressRadioButton(ActionEvent event) {
        if (this.creat.isSelected()) {
            UserModelBuilderSettings.whicmod = 0;
        } else {
            UserModelBuilderSettings.whicmod = 1;
        }
    }

    public void pressCreatButton(ActionEvent event) {

        this.masker.setVisible(true);
        if (UserModelBuilderSettings.whichfeature == 0) {
            this.file = new File("Negative Data/lehelf.arff");
        } else {

        }
        this.doit.setDisable(true);
        Thread modelmakethread = new Thread("Model make") {
            public void run() {
                new UserModelBuilder(file, 0);
                Platform.runLater(() -> {
                    doit.setDisable(false);
            
                                     Notifications.create()
              .title("Working finished")
              .text("Your model was successfully created!")
              .position(Pos.CENTER)
              .showInformation();
                });
                masker.setVisible(false);
            }
        };
        modelmakethread.start();

    }

    private void createselector() {
        this.selector.getItems().addAll("Lehel feature", "Teacher feature");
        this.selector.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (observable.getValue().toString().equalsIgnoreCase("Lehel feature")) {
                    UserModelBuilderSettings.whichfeature = 0;
                } else {
                    UserModelBuilderSettings.whichfeature = 1;
                }
            }

        });
    }

    private void creatradiogroup() {
        group = new ToggleGroup();
        this.creat.setToggleGroup(group);
        this.update.setToggleGroup(group);
        this.creat.setSelected(true);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.createselector();
        this.creatradiogroup();
        this.masker.setVisible(false);
        this.masker.setProgress(-1);
        this.masker.setText("Working");
        this.masker.setMinWidth(633);
        this.masker.setMinHeight(395);
        this.root.getChildren().add(this.masker);
        
    }

}