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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

    @FXML
    private TreeView menu;
    @FXML
    private ProgressBar progressbarforprobs;

    @FXML
    private Pane f;

    /**
     * Initializes the controller class.
     *
     */
    private void creatmenu() {

        TreeItem<String> root = new TreeItem<>("Menu");
        menu.setShowRoot(false);        // itemChild.setExpanded(false);
        //root is the parent of itemChild
        TreeItem<String> menu1 = new TreeItem<>("Sugnature Recognition");
        TreeItem<String> menu2 = new TreeItem<>("Mouse Recogniton");

        TreeItem<String> menu11 = new TreeItem<>("Data Collecting");
        TreeItem<String> menu21 = new TreeItem<>("Data Collecting");
        TreeItem<String> menu12 = new TreeItem<>("Create Model");
        TreeItem<String> menu13 = new TreeItem<>("Test Model");
        TreeItem<String> menu14 = new TreeItem<>("Update Model");
        TreeItem<String> menu22 = new TreeItem<>("Create Model");
        TreeItem<String> menu23 = new TreeItem<>("Test Model");
        TreeItem<String> menu24 = new TreeItem<>("Update Model");

        menu1.getChildren().addAll(menu11, menu12, menu13, menu14);
        menu2.getChildren().addAll(menu21, menu22, menu23);
        root.getChildren().addAll(/*menu1 ,*/menu2);

        this.menu.setRoot(root);
        this.menu.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectednew = (TreeItem<String>) newValue;
                TreeItem<String> selectedold = (TreeItem<String>) oldValue;
                switch (selectednew.getParent().getValue()) {
                    case "Sugnature Recognition":
                        switch (selectednew.getValue()) {
                            case "Data Collecting":
                                MainScreenController.this.startSignatureDatacollecting();
                                break;
                            case "Create Model":
                                MainScreenController.this.startSignatureDataModelBuilder();
                                break;
                            case "Test Model":
                                MainScreenController.this.startSignatureTest();
                                break;
                            case "Update Model":
                                MainScreenController.this.startSignatureupdate();
                                break;
                            default:
                                break;
                        }
                        break;
                    case "Mouse Recogniton":
                        switch (selectednew.getValue()) {
                            case "Data Collecting":
                                MainScreenController.this.startDataCollectingMouseData();
                                break;
                            case "Create Model":
                                MainScreenController.this.startModelBuilderMouseData();
                                break;
                            case "Test Model":
                                MainScreenController.this.startMouseTest();
                                break;
                            case "Update Model":
                                MainScreenController.this.startUpdateMouse();
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
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
        this.creatmenu();
        DataCollectorSettings.loaddata();
    }

}