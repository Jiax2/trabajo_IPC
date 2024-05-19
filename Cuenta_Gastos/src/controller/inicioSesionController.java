/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafxmlapplication.JavaFXMLApplication; 
import model.Acount.*;
/**
 * FXML Controller class
 *
 * @author jiaji
 */
public class inicioSesionController implements Initializable {

    @FXML
    private HBox hBox;
    @FXML
    private TextField User;
    @FXML
    private PasswordField Password;
    @FXML
    private Text errCon;
    @FXML
    private Text Registro;
    @FXML
    private Button iniciar;

    /**
     * Initializes the controller class.
     */
    
    private void pulsadoIniciar(ActionEvent event) throws IOException {
        if(User.getText()== null || Password.getText()==null){
            errCon.setText("Faltan campos por completar");
        }else if(logInUserByCredentials(User.getText(),Password.getText())==false){
            errCon.setText("No existe el usuario");
        }else{
            FXMLLoader loader =new FXMLLoader(getClass().getResource("homeScreen.fxml"));
            Parent root=loader.load();
            iniciar.getScene().getWindow().hide();
            Scene scene= new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        }
    }
    
    private void irRegistro(MouseEvent event) throws IOException{
        FXMLLoader loader =new FXMLLoader(getClass().getResource("Registro.fxml"));
        Parent root=loader.load();
        Registro.getScene().getWindow().hide();
        Scene scene= new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
}
