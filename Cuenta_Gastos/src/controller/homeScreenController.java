package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafxmlapplication.JavaFXMLApplication;

/**
 * FXML Controller class
 * Clase Controller para homeScreen.fxml
 * @author jiaji
 */
public class homeScreenController extends JavaFXMLApplication implements Initializable {

    private JavaFXMLApplication main; 
    @FXML
    private Button gastos;
    @FXML
    private Button categorias;
    @FXML
    private Button cuenta;
    @FXML
    private Button salir;
    @FXML
    private VBox cambio;
    @FXML
    private Stage primaryStage;
    @FXML
    private Scene primaryScene;
    @FXML
    private String primaryTitle;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void initWin1(Stage stage){
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
    }

    // Method to set the reference to the main application
    public void setMainApplication(JavaFXMLApplication mainApplication) {
        this.main = mainApplication;
    }

    private void changeAnual(ActionEvent event) throws IOException {
        if (main != null) {
            main.cambiarInicio();
        } else {
            System.out.println("Error: Main application reference is null");
        }
    }

    @FXML
    private void cambiarGasto(ActionEvent event) throws IOException {
        cambio.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/gastos.fxml")); 
        Parent root = loader.load(); 
        cambio.getChildren().add(root); 
    }

    @FXML
    private void cambiarCategoria(ActionEvent event) throws IOException {
        cambio.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/categorias.fxml")); 
        Parent root = loader.load();
        cambio.getChildren().add(root); 
    }

    @FXML
    private void cambiarCuenta(ActionEvent event) throws IOException {
        
    }

    @FXML
    private void exit(ActionEvent event) throws IOException {
    }
}
