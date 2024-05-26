package controller;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
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
    private Tab graficaTab;
    @FXML
    private Tab totalTab;
    @FXML
    private BarChart<String, Number> grafica;
    @FXML
    private NumberAxis datos;
    @FXML
    private CategoryAxis categoria;
    @FXML
    private TableView<Charge> tablaTot;
    @FXML
    private TableColumn<?, ?> colInfo;
    @FXML
    private TabPane tabPane;
    //Gráfica
    private ObservableList<String> monthNames=FXCollections.observableArrayList();
    String[] months = DateFormatSymbols.getInstance(new Locale("es", "ES")).getMonths();
    Double[] meses = new Double[12];
    List<Charge> listCharge;
    List<Category> listCategory;
    //Tabla gastos totales
    private ObservableList<Charge> listaGastosTot = null; 
    private List<Charge> datosTot=null;
    
    Stage stage = this.stage;
    public Acount cuentas;
    public User user;
    @FXML
    private Button buttonMod;
    @FXML
    private ChoiceBox<String> choiceBox = new ChoiceBox<>();
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
            this.anualSinCategorias();
            //Inicializa la imagen y el texto del usuario 
            uImagen.setImage(user.getImage());
            usuario.setText(user.getNickName());
            inicializaTot();
            inicializarChoiceBox();
        } catch (AcountDAOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    //inicializar el choiceBox
    private void inicializarChoiceBox() {
        choiceBox.getItems().add("Anual");
        choiceBox.getItems().add("Categorias");
        choiceBox.getSelectionModel().selectFirst(); // Set default selection to "Anual"
        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals("Anual")) {
                    anualSinCategorias();
                } else if (newValue.equals("Categorias")) {
                    updateChartByCategory();
                }
            }
        });
        anualSinCategorias(); // Load the initial data for "Anual"
    }
    private void anualSinCategorias() {
      monthNames.addAll(Arrays.asList(this.months));
      grafica.getData().clear();
      try {
         listCharge = Acount.getInstance().getUserCharges();
      } catch (AcountDAOException var8) {
         Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, (String)null, var8);
      } catch (IOException var9) {
         Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, (String)null, var9);
      }

      for(int i = 0; i < 12; ++i) {
         meses[i] = 0.0;
      }

      for(Charge item: listCharge){
          if(item.getDate().getYear() == LocalDate.now().getYear()){
              meses[item.getDate().getMonthValue()-1] = meses[item.getDate().getMonthValue()-1]+item.getCost(); 
          }
      }

      XYChart.Series<String, Number> mes = new XYChart.Series();
      int j = 0;
      
      for(double x: meses) {
         mes.getData().addAll(new XYChart.Data<>(months[j], x));
         j++;
      }

      grafica.getData().add(mes);
    }
private void updateChartByCategory() {
    // Limpiar los datos anteriores de la gráfica
    grafica.getData().clear();
    
    // Crear un mapa para almacenar los gastos por categoría
    Map<String, Double> categoryExpenses = new HashMap<>();
    
    // Obtener la lista de cargos del usuario
    try {
        listCharge = Acount.getInstance().getUserCharges();
    } catch (AcountDAOException | IOException ex) {
        Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    // Sumar los gastos por categoría
    for (Charge charge : listCharge) {
        String category = charge.getCategory() != null ? charge.getCategory().getName() : "Sin categoría";
        categoryExpenses.put(category, categoryExpenses.getOrDefault(category, 0.0) + charge.getCost());
    }
    
    // Crear una serie de datos para la gráfica
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Gastos por Categoría");

    // Añadir datos a la serie
    for (Map.Entry<String, Double> entry : categoryExpenses.entrySet()) {
        series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
    }
    
    // Añadir la serie de datos a la gráfica
    grafica.getData().add(series);
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
    
    private void inicializaTot() throws AcountDAOException, IOException{
        //Carga gastos
        datosTot=cuentas.getUserCharges();
        //Inicializa columnas
        colNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colCategoria.setCellValueFactory(cellData -> {
                Category category = cellData.getValue().getCategory();
                return new SimpleObjectProperty(category != null ? category.getName() : "Sin categoría");
            });
        colFecha.setCellValueFactory(new PropertyValueFactory<>("date"));
        colInfo.setCellValueFactory(new PropertyValueFactory<>("description"));
        //Añade a la tabla
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

    @FXML
    private void exit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/inicioSesion.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Iniciar sesión");
    }

    @FXML
    private void eliminarGasto(ActionEvent event) throws AcountDAOException, IOException {
        Charge selectedCharge = tablaTot.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Eliminar gasto");
        alert.setContentText("¿Estas seguro que quieres eliminar este gasto?");
        ButtonType buttonTypeAccept = new ButtonType("Aceptar");
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeAccept,buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()){
            if (result.get() == buttonTypeAccept){
                Acount.getInstance().removeCharge(selectedCharge);
                inicializaTot();
                tabPane.getSelectionModel().select(totalTab);
            }else{
            }
        }
    }
    
    @FXML
    private void modificarGasto(ActionEvent event) throws IOException {
        cambio.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/EditGastos.fxml")); 
        Parent root = loader.load();
        cambio.getChildren().add(root); 
    }
}