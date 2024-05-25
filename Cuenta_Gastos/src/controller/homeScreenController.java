package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafxmlapplication.JavaFXMLApplication;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;
import model.User;

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
    public GridPane cambio;
    @FXML
    private TableColumn<Charge, Double> colCantidad;
    @FXML
    private TableColumn<Charge, LocalDate> colFecha;
    @FXML
    private TableColumn<Charge, String> colNombre;
    @FXML
    private TableColumn<Charge, String> colCategoria;
    @FXML
    private Button deleteGasto;
    @FXML
    private GridPane pantallaTotal;
    @FXML
    private Button buttonAdd;
    @FXML
    private Label usuario;
    @FXML
    private ImageView uImagen;
    @FXML
    private Tab mensualTab;
    @FXML
    private Tab anualTab;
    @FXML
    private Tab totalTab;
     @FXML
    private TableView<Charge> tablaMes;
    @FXML
    private BarChart<?, ?> grafica;
    @FXML
    private TableView<Charge> tablaTot;
    
    private ObservableList<Charge> listaGastosMes = null; 
    private List<Charge> datosMes=null;
    
    private ObservableList<Charge> listaGastosTot = null; 
    private List<Charge> datosTot=null;
    
    Stage stage = this.stage;
    public Acount cuentas;
    public User user;
    @FXML
    private TableColumn<?, ?> colInfo;
    //===============================================================
    /**
     * Initializes the controller class.
 */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Carga el usuario y el acount
        try{
            cuentas=Acount.getInstance();
            user=cuentas.getLoggedUser();
            //Inicializa la imagen y el texto del usuario 
            uImagen.setImage(user.getImage());
            usuario.setText(user.getNickName());
            inicializaMes();
            inicializaTot();
        } catch (AcountDAOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
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
    
    private void inicializaMes() throws AcountDAOException, IOException{
        datosMes=cuentas.getUserCharges();
        listaGastosMes=FXCollections.observableList(datosMes);
        tablaMes.setItems(listaGastosMes);
        
    }
    
    private void inicializaTot() throws AcountDAOException, IOException{
        datosTot=cuentas.getUserCharges();

        colNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colCategoria.setCellValueFactory(cellData -> {
                Category category = cellData.getValue().getCategory();
                return new SimpleObjectProperty(category != null ? category.getName() : "Sin categoría");
            });
        colFecha.setCellValueFactory(new PropertyValueFactory<>("date"));
        colInfo.setCellValueFactory(new PropertyValueFactory<>("description"));
         listaGastosTot=FXCollections.observableList(datosTot);
        tablaTot.setItems(listaGastosTot);
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
        gastos.getScene().setRoot(root);
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
    public void setupChargeTable(TableView<Charge> tableView, TableColumn<Charge, String> nameColumn, TableColumn<Charge, String> categoryColumn, TableColumn<Charge, LocalDate> dateColumn, ObservableList<Charge> charges) {
        // Asigna la lista observable a la tabla
        tableView.setItems(charges);

        // Asigna las propiedades de Charge a cada columna
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        categoryColumn.setCellValueFactory(cellData -> {
            Charge charge = cellData.getValue();
            Category category = charge.getCategory();
            if (category != null) {
                return new SimpleStringProperty(category.getName());
            } else {
                return new SimpleStringProperty("");
            }
        });
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));
    }

    @FXML
    private void eliminarGasto(ActionEvent event) throws AcountDAOException, IOException {
        Charge selectedCharge = tablaTot.getSelectionModel().getSelectedItem();
        Acount.getInstance().removeCharge(selectedCharge);
        System.out.println("eliminado");
//        try {
//            Acount acount = Acount.getInstance();
//           listaGastosTot.addAll(acount.getUserCharges());
//        } catch (IOException | AcountDAOException e) {
//        }
        Parent root = FXMLLoader.load(getClass().getResource("/vista/homeScreen.fxml"));
        gastos.getScene().setRoot(root);
        changeTotal(event);
    }

    @FXML
    private void changeMensual(Event event) {
        mensualTab.getOnSelectionChanged(); 
    }

    @FXML
    private void changeAnual(Event event) {
        anualTab.getOnSelectionChanged(); 
    }

    @FXML
    private void changeTotal(Event event) {
        anualTab.getOnSelectionChanged();
    }
    
    
    
}
