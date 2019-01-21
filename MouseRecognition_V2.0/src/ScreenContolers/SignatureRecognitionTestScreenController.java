/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScreenContolers;

import java.io.File;
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
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
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
import signaturerecognation.SignatureClassifier;

import signaturerecognation.User_Signature_Model_Builder;

/**
 * FXML Controller class
 *
 * @author Denes
 */
public class SignatureRecognitionTestScreenController implements Initializable {
 private ToggleGroup group;
    @FXML
    private RadioButton modelradiobutton;
    @FXML
    private RadioButton fileradiobutton;

    
    private SignatureClassifier classifier;

    @FXML
    private Canvas signature;
    @FXML
    private ProgressBar bar;
    @FXML
    private Label score;
       
    private GraphicsContext graphicsContext;
    private WritableImage wim;
    private StringBuilder title= new StringBuilder("kep");
    private User_Signature_Model_Builder teszt;
    
    
     private void creatradiogroup() {
        group = new ToggleGroup();
        this.modelradiobutton.setToggleGroup(group);
        this.fileradiobutton.setToggleGroup(group);
        this.modelradiobutton.setSelected(true);

    }
     @FXML
    public void pressStartButton(ActionEvent event) {
       // this.teszt = new User_Signature_Model_Builder(new double[2]) ;
        this.classifier = new SignatureClassifier(this.bar,this.score);
        this.classifier.loadodel();// this.teszt.loadodel();
        System.out.println("dfdsffd");
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
       signature.snapshot(null, wim);
        File file = new File("test.png");
         try {
            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
            this.classifier.testsignature("test.png");
        } catch (Exception s) {
        }
      System.out.println("dfbjlfwfhwle");
      this.graphicsContext.clearRect(this.signature.getScaleX(),this.signature.getScaleY(), 500, 300);
      this.graphicsContext.setFill(Color.YELLOW);
      this.graphicsContext.fillRect(this.signature.getScaleX(),this.signature.getScaleY(), 500, 300);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
           this.graphicsContext = this.signature.getGraphicsContext2D();
        this.graphicsContext.setFill(Color.YELLOW);
        this.graphicsContext.fillRect(this.signature.getScaleX(),this.signature.getScaleY(), 500, 300);
         this.wim = new WritableImage(500,300);
        

        
        this.signature.addEventHandler(MouseEvent.MOUSE_PRESSED, 
        new EventHandler<MouseEvent>(){
    @Override
    public void handle(MouseEvent event) {
        graphicsContext.beginPath();
        graphicsContext.moveTo(event.getX(), event.getY());
        graphicsContext.stroke();

    }
});

        this.signature.addEventHandler(MouseEvent.MOUSE_DRAGGED, 
        new EventHandler<MouseEvent>(){
    @Override
    public void handle(MouseEvent event) {
        graphicsContext.lineTo(event.getX(), event.getY());
        graphicsContext.stroke();
        graphicsContext.closePath();
        graphicsContext.beginPath();
        graphicsContext.moveTo(event.getX(), event.getY());
    }
});

        
        this.signature.addEventHandler(MouseEvent.MOUSE_RELEASED, 
        new EventHandler<MouseEvent>(){
    @Override
    public void handle(MouseEvent event) {
        System.out.println("VEGE");
        graphicsContext.lineTo(event.getX(), event.getY());
        graphicsContext.stroke();
        graphicsContext.closePath();
       

    }
});

       this.creatradiogroup();
        
        


    }     
    
}
