package controller;

import com.sun.javafx.logging.PlatformLogger.Level;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafxmlapplication.JavaFXMLApplication;
import model.*; 
import controller.RegistroController.*;
import java.lang.System.Logger;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import model.Acount;
import model.AcountDAOException;

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
    public GridPane cambio;
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
    private VBox pantallaAnual;
    @FXML
    private VBox pantallaTotal;
    @FXML
    private Button buttonAdd;
    private ImageView imagen;
    @FXML
    private Label usuario;
    @FXML
    private ImageView uImagen;
    
    private ObservableList<Charge> listaGastos = null; 
    Stage stage = this.stage;
    
    public Acount cuentas;
    @FXML
    private Tab mensualTab;
    @FXML
    private Tab anualTab;
    @FXML
    private Tab totalTab;
    
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
    ArrayList<Charge> misgastos = new ArrayList<Charge>(); 
    private void inicializaModelo() throws AcountDAOException, IOException{
        
        misgastos.add(Acount.getInstance().getUserCharges().get(0));
        listaGastos =   FXCollections.observableList(misgastos);
        
    }
    
    //getter del gasto 
    Charge c; 
    private void getter(List<Charge> ch){
        for(int i= 0; i< ch.size(); i++){
            c = ch.get(i); 
            String u = String.valueOf(c.getUnits()); 
            String p = String.valueOf(c.getCost()); 
            
        }
    }
    
    
   
    //=============================================================
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*try {
            cuentas=cuentas.getInstance();
        } catch (AcountDAOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
       
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
        Parent root = FXMLLoader.load(getClass().getResource("/vista/homeScreen.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
