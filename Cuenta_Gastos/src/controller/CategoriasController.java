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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import static javafxmlapplication.JavaFXMLApplication.setRoot;

/**
 * FXML Controller class
 *
 * @author jiaji
 */
public class CategoriasController implements Initializable {

    @FXML
    private BorderPane pantallaCategorias;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addCategoria(ActionEvent event) throws IOException { 
        //addCategoria(event);
    }

    @FXML
    private void deleteCategoria(ActionEvent event) throws IOException {
       
    }
    
}
