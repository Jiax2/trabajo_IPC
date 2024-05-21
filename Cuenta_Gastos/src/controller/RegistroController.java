/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafxmlapplication.JavaFXMLApplication;
import model.Acount;
import model.AcountDAOException;


/**
 * FXML Controller class
 *
 * @author jiaji
 */
public class RegistroController implements Initializable {
    
    @FXML
    private TextField nombre;
    @FXML
    private TextField usuario;
    @FXML
    private PasswordField pass1;
    @FXML
    private PasswordField pass2;
    @FXML
    private TextField mail;
    @FXML
    private Text error;
    @FXML
    private Button imagen;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void irInicioSesion(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/inicioSesion.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Iniciar sesión");
    }
    
    @FXML
    private void pulsadoRegistro(ActionEvent event) throws IOException, AcountDAOException {
        if (nombre.getText().isEmpty() || usuario.getText().isEmpty() || pass1.getText().isEmpty() || pass2.getText().isEmpty() || mail.getText().isEmpty()){
            error.setText("Faltan campos por completar");
        }else if (Acount.getInstance().logInUserByCredentials(usuario.getText(), pass1.getText())==true){
            error.setText("Ya existe este usuario");
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/vista/homeScreen.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setTitle("MalGastos");
        }
    }
}
