/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import javafx.scene.input.MouseEvent;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafxmlapplication.JavaFXMLApplication;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 * 
 * @author jiaji
 */
public class inicioSesionController implements Initializable {
    @FXML
    private TextField User;
    @FXML
    private Text errCon;
    @FXML
    private Button inicio;
    @FXML
    private PasswordField password;
    @FXML
    private ToggleButton toggleButton;
    @FXML
    private Label shownPassword;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        shownPassword.setVisible(false);
        //Desabilita boton inicio
        inicio.disableProperty().bind(Bindings.createBooleanBinding(() ->
                User.getText().isEmpty() ||
                password.getText().isEmpty(),
                User.textProperty(),
                password.textProperty()
              
        ));
    }
    
    @FXML
    private void pulsadoIniciar(ActionEvent event) throws IOException, AcountDAOException {
        if (Acount.getInstance().logInUserByCredentials(User.getText(), password.getText())==false){
            errCon.setText("Usuario o contraseña no son correctos");
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/vista/homeScreen.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();
            stage.setTitle("MalGastos");
        }
    }
    
    @FXML
    private void irRegistro(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/Registro.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setTitle("Registrarse");
    }
    
    @FXML
    private void info(ActionEvent event){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText("Información de la aplicación");
        alert.setContentText("MalGastos\n"+"Es una aplicación para gestión de gastos.");
        alert.showAndWait();
    }
    
    @FXML
    private void salir(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    private void passwordFieldKeyTyped(KeyEvent event) {
        shownPassword.textProperty().bind(Bindings.concat(password.getText()));
        
    }

    @FXML
    private void toggleButton(ActionEvent event) {
        if(toggleButton.isSelected()){
            shownPassword.setVisible(true);
            shownPassword.textProperty().bind(Bindings.concat(password.getText()));
        
            toggleButton.setText("Ocultar");
            password.setVisible(false);
        
        }else{
            toggleButton.setText("Ver");
            password.setVisible(true);
         
            shownPassword.setVisible(false);
          
        }
    }
}
