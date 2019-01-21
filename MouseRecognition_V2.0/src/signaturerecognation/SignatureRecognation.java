/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signaturerecognation;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

/**
 *
 * @author Denes
 */
public class SignatureRecognation extends Application {
    

    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        
        
        stage.setScene(scene);
        stage.show();
       // new User_Signature_Model_Builder();
    }

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        launch(args);
//    }
    
}
