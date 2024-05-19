/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import javafx.scene.input.MouseEvent;
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
import javafx.scene.control.Label;
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
    private Stage primaryStage;
    @FXML
    private HBox hBox;
    @FXML
    private TextField User;
    @FXML
    private PasswordField Password;
    @FXML
    private Text errCon;
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
        } else if (Acount.getInstance().logInUserByCredentials(User.getText(), Password.getText())==false){
            errCon.setText("No existe el usuario");
        } else {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/vista/homeScreen.fxml"));
        HBox root = (HBox) myLoader.load();
        homeScreenController win1Controller = myLoader.<homeScreenController>getController();

        win1Controller.initWin1(primaryStage);
        //We create the scene foe win1

        Scene scene = new Scene(root);
        //we asign new scene to current stage/window
        primaryStage.setScene(scene);
        primaryStage.setTitle("MalGastos");
        primaryStage.show();
        }
    }
    
    @FXML
    private void irRegistro(MouseEvent event) throws IOException {
    FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/vista/Registro.fxml"));
        HBox root = (HBox) myLoader.load();
        RegistroController win2Controller = myLoader.<RegistroController>getController();
    
        win2Controller.initWin2(primaryStage);
        //We create the scene foe win1
    
        Scene scene = new Scene(root);
        //we asign new scene to current stage/window
        primaryStage.setScene(scene);
        primaryStage.setTitle("Crear Cuenta");
        primaryStage.show();
    }
    
}
