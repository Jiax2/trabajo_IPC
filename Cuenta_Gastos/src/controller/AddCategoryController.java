/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Acount; 
import model.AcountDAOException;
/**
 * FXML Controller class
 *
 * @author jiaji
 */
public class AddCategoryController implements Initializable {
    
    @FXML
    private TextField catName;
    @FXML
    private TextField catDes;
    @FXML
    private Button add;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       add.disableProperty().bind(Bindings.createBooleanBinding(() ->
            catName.getText().isEmpty(),
             catName.textProperty()));
    }
    
    @FXML
    private void cancelarCategoria(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/homeScreen.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Malgastos");
    }

    @FXML
    private void aceptarCategoria(ActionEvent event) throws AcountDAOException, IOException {
         try{    
            Acount.getInstance().registerCategory(catName.getText(), catDes.getText());
            Parent root = FXMLLoader.load(getClass().getResource("/vista/homeScreen.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setTitle("Malgastos");
            }catch( model.AcountDAOException a){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error en la creación de categoría");
                alert.setHeaderText("Nombre de la categoría ya en uso");
                alert.setContentText("Cambia o edita el nombre de la categoría");
                alert.showAndWait();
            }
    }
}