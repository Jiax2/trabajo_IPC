/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
import model.Acount;
import model.AcountDAOException;

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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic if needed
    }

    @FXML
    private void pulsadoIniciar(ActionEvent event) throws IOException, AcountDAOException {
        if (User.getText().isEmpty() || Password.getText().isEmpty()) {
            errCon.setText("Faltan campos por completar");
        } else if (!Acount.getInstance().logInUserByCredentials(User.getText(), Password.getText())) {
            errCon.setText("No existe el usuario");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/homeScreen.fxml"));
            Parent root = loader.load();
            iniciar.getScene().getWindow().hide();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Stage) iniciar.getScene().getWindow()).close();
        }
    }

    @FXML
    private void irRegistro(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/Registro.fxml"));
        Parent root = loader.load();
        Registro.getScene().getWindow().hide();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        ((Stage) Registro.getScene().getWindow()).close();
    }
}
