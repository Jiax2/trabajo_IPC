/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 * Clase controller para inicioSesion.fxml
 * @author jiaji
 */
public class PrincipalController implements Initializable {

    @FXML
    private HBox hBox;
    @FXML
    private TextField User;
    @FXML
    private PasswordField Pass;
    @FXML
    private Text errCon;
    @FXML
    private Text Registro;
    @FXML
    private Button inicio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
