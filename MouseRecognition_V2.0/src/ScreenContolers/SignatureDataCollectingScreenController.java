/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScreenContolers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import signaturerecognation.User_Signature_Model_Builder;

/**
 * FXML Controller class
 *
 * @author Denes
 */
public class SignatureDataCollectingScreenController implements Initializable {

    
     @FXML
    private Label number;
    @FXML
    private Canvas signature;
    
    private GraphicsContext graphicsContext;
    private WritableImage wim;
    private StringBuilder title= new StringBuilder("kep");
    private User_Signature_Model_Builder teszt;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
       signature.snapshot(null, wim);
        File file = new File("Collected Pictures/"+title+number.getText().split("/")[1]+".png");
        Integer num = Integer.parseInt(number.getText().split("/")[1]) + 1;
        number.setText(number.getText().split("/")[0]+"/"+num.toString());
         try {
            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
          
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


        
        


    }    
      
    
}
