package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxmlapplication.JavaFXMLApplication;
import model.*; 

/**
 * FXML Controller class
 * Clase Controller para homeScreen.fxml
 * @author jiaji
 */
public class HomeScreenController extends JavaFXMLApplication implements Initializable {

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
    public VBox cambio;
    @FXML
    private TableColumn<Charge, Double> colCantidad;
    @FXML
    private TableColumn<Charge, LocalDate> colFecha;
    @FXML
    private TableColumn<Charge, String> colNombre;
    @FXML
    private TableColumn<Charge, Category> colCategoria;
    @FXML
    private Button deleteGasto;
    @FXML
    private VBox pantallaMensual;
    @FXML
    private VBox pantallaAnual;
    @FXML
    private VBox pantallaTotal;
    @FXML
    private Button buttonAdd;
    
    private ObservableList<Charge> listaGastos = null; 
    Stage stage = this.stage;
    //===============================================================
    //Botones de añadir y eliminar gastos
    @FXML
    private void actionAddGasto(ActionEvent event) throws IOException {
        cambio.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/addGastos.fxml")); 
        Parent root = loader.load();
        cambio.getChildren().add(root); 
    }
    //=============================================================
    //Muestreo de los gastos en la lista
    private void inicializaModelo(){
        ArrayList<Charge> misgatos = new ArrayList<Charge>(); 
       
    }
    
    
   
    //=============================================================
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    

    // Method to set the reference to the main application
    public void setMainApplication(JavaFXMLApplication mainApplication) {
        this.main = mainApplication;
    }
    
    //=======================================================================
    //Cambios de pantalla al pulsar los botones 

    private void changeAnual(ActionEvent event) throws IOException {
        if (main != null) {
            main.cambiarInicio();
        } else {
            System.out.println("Error: Main application reference is null");
        }
    }

    @FXML
    private void cambiarGasto(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/homeScreen.fxml")); 
        Parent root = loader.load(); 
        setRoot(root);
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
        cambio.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/cuenta.fxml")); 
        Parent root = loader.load();
        cambio.getChildren().add(root);
    }
    
//    @FXML
//    public void addCategoria(ActionEvent event) throws IOException{
//        cambio.getChildren().clear();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/addCategory.fxml")); 
//        Parent root = loader.load();
//        cambio.getChildren().add(root);
//    }

    @FXML
    private void exit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/inicioSesion.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Iniciar sesión");
    }
    //===============================================================================================
    
}
