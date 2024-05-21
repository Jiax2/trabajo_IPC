/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import static javafxmlapplication.JavaFXMLApplication.setRoot;

/**
 * FXML Controller class
 *
 * @author jiaji
 */
public class AddGastosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cancelarGasto(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/homeScreen.fxml")); 
        Parent root = loader.load(); 
        setRoot(root);
    }

    @FXML
    private void aceptarGasto(ActionEvent event) {
        
    }
    
}
