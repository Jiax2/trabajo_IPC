/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import com.sun.javafx.logging.PlatformLogger.Level;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafxmlapplication.JavaFXMLApplication;
import model.*; 
import controller.RegistroController.*;
import java.lang.System.Logger;
import javafx.scene.layout.BorderPane;
import model.Acount;
import model.AcountDAOException;
/**
 * FXML Controller class
 *
 * @author jiaji
 */

    

public class CategoriasController implements Initializable {
    
    @FXML
    private Button catAdd;
    @FXML
    private Button catDel;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    //Soy retrasado
    @FXML
    private void addCategoria(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/addCategory.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Añadir Categoria"); 
    }

    @FXML
    private void deleteCategoria(ActionEvent event) throws IOException {
       
    }
    
}
