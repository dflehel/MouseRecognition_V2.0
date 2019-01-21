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
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import mousedatacollector.MouseDataCollertor;
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
    
    private MouseDataCollertor collector;

    
     @FXML
    public void pressStartButton(ActionEvent event)  {               
            Image image = new Image("Pictures/activepicture.png");
            this.im2.setImage(image);
            DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss");
                Date date = new Date();
               
                String s = dateFormat.format(date);
            try{
                FileWriter files = new FileWriter("Collected Data/"+s+".CSV");
                files.append("client timestamp,button,state,x,y\n");
            
               try {
                GlobalScreen.registerNativeHook();
                Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
                logger.setLevel(Level.OFF);

                // Don't forget to disable the parent handlers.
                logger.setUseParentHandlers(false);
                }
                catch (NativeHookException ex) {
                System.err.println("There was a problem registering the native hook.");
                System.err.println(ex.getMessage());
                
                System.exit(1);
                }
                
                // Construct the example object.
                this.collector = new MouseDataCollertor();
                this.collector.setWrite(files);
                // Add the appropriate listeners.
                GlobalScreen.addNativeMouseListener( this.collector);
                GlobalScreen.addNativeMouseMotionListener( this.collector);
                GlobalScreen.addNativeMouseWheelListener( this.collector);
                this.listfiles();
                }

        catch (IOException ex) {
                Logger.getLogger(MouseDataCollertor.class.getName()).log(Level.SEVERE, null, ex);
                } 
    }

     @FXML
    public void pressStopButton(ActionEvent event)  {               
            Image image = new Image("Pictures/inactivepicture.png");
            this.im2.setImage(image);
            this.collector.stop();
    }
    
    
    private void listfiles(){
         File folder = new File("Collected Data");
        File[] listOfFiles = folder.listFiles();
        this.listfiles.setEditable(false);
        this.listfiles.getItems().clear();
        for (int i = 0; i < listOfFiles.length; i++) {
           this.listfiles.getItems().add(listOfFiles[i]);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image("Pictures/inactivepicture.png");
        this.im2.setImage(image);
        this.listfiles();
        // TODO

    }

}
