package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafxmlapplication.JavaFXMLApplication;

/**
 * FXML Controller class
 * Clase Controller para homeScreen.fxml
 * @author jiaji
 */
public class GastosController implements Initializable {

    @FXML
    private Button gastosAnual;
    private JavaFXMLApplication main; 
    @FXML
    private VBox pantallaCambio;

    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    // Method to set the reference to the main application
    public void setMainApplication(JavaFXMLApplication mainApplication) {
        this.main = mainApplication;
    }

    @FXML
    private void changeAnual(ActionEvent event) throws IOException {
        if (main != null) {
            main.cambiarInicio();
        } else {
            System.out.println("Error: Main application reference is null");
        }
    }
}
