/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class JavaFXMLApplication extends Application {
    private Stage stage; 
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage; 
        loadScene("homeScreen.fxml"); 
        stage.show(); 
    }
    
    //==========================================================================
    //carga una nueva escena
    public void loadScene(String s) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/"+ s)); 
        Parent root = loader.load(); 
        Scene scene = new Scene(root); 
        stage.setScene(scene);
    }
    
    public void cambiarInicio(){
        try { 
            loadScene("Registro.fxml");
        } catch (IOException ex) {
            Logger.getLogger(JavaFXMLApplication.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error loading scene: " + "incioSesion");
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
