/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.File;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Category;
import model.Acount; 
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author jiaji
 */
public class AddGastosController implements Initializable {

    @FXML
    private TextField nameGasto;
    @FXML
    private TextField descripGasto;
    @FXML
    private TextField cantidad;
    @FXML
    private ChoiceBox<String> pickerCategorias = new ChoiceBox<>();
    @FXML
    private TextField unidades;
    @FXML
    private DatePicker dateGasto;
    @FXML
    private Button imagenGasto;
    @FXML
    private Button cancelarButton;
    @FXML
    private Button aceptarButton;
    @FXML
    private ImageView ImagenView;
    @FXML
    private Text error;
    
    private Image gastoImagen; 
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            listacategoria();
        } catch (AcountDAOException ex) {
            Logger.getLogger(AddGastosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AddGastosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        SimpleBooleanProperty pickerCategoriasEmpty = new SimpleBooleanProperty(true);
        pickerCategorias.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            pickerCategoriasEmpty.set(newVal == null);
        });
        
        aceptarButton.disableProperty().bind(Bindings.createBooleanBinding(() ->
                nameGasto.getText().isEmpty() ||
                unidades.getText().isEmpty() ||
                cantidad.getText().isEmpty() ||
                pickerCategoriasEmpty.get() ||
                dateGasto.getValue()==null,
                nameGasto.textProperty(),
                unidades.textProperty(),
                cantidad.textProperty(),
                pickerCategoriasEmpty,
                dateGasto.valueProperty().isNull()
        ));
    }    
    
    @FXML
    private void imagen(ActionEvent event) throws IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");

        // Configurar el filtro de extensión para mostrar solo archivos de imagen
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de imagen(*.png,*.jpg,*.jpeg)" ,
                "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);

        // Mostrar el diálogo de selección de archivos y obtener la imagen seleccionada
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                // Cargar la imagen seleccionada en un objeto Image
                gastoImagen = new Image(selectedFile.toURI().toString());

                // Establecer la imagen en el ImageView correspondiente
                ImagenView.setImage(gastoImagen); 
            } catch (Exception e) {
                // Manejar cualquier excepción que pueda ocurrir al cargar la imagen
                e.printStackTrace();
            }
        }
    }
    
    private void listacategoria() throws AcountDAOException, IOException{
        List<Category> lista = Acount.getInstance().getUserCategories(); 
        for(int i = 0; i< lista.size(); i++){
            pickerCategorias.getItems().addAll( lista.get(i).getName());
        }
    }
    
    public Category findCategory() throws AcountDAOException, IOException{
        Category cat = null; 
        List<Category> list = Acount.getInstance().getUserCategories();
        String s = pickerCategorias.getSelectionModel().getSelectedItem(); 
        for(int i = 0; i<list.size(); i++){
            cat = list.get(i); 
            if(s.equals(cat.getName())){
                return cat; 
            }
        }
        return cat; 
    }
    @FXML
    private void cancelarGasto(ActionEvent event) throws IOException, AcountDAOException {
        //Vuelve a HomeScreen
        Parent root = FXMLLoader.load(getClass().getResource("/vista/homeScreen.fxml"));
        cancelarButton.getScene().setRoot(root);
    } 
    
    @FXML
    private void aceptarGasto(ActionEvent event) throws AcountDAOException, IOException {
        if(Comprobar()){
            error.setText("La unidad tiene que ser números");
            //Añadir valores
            Category cat = findCategory(); 
            String name = nameGasto.getText(); 
            String descripcion = descripGasto.getText(); 
            Double cost = parseDouble(cantidad.getText());
            LocalDate date = dateGasto.getValue(); 
            int unit=parseInt(unidades.getText());
            //Agrega gastos
            Acount.getInstance().registerCharge(name, descripcion,cost , unit, gastoImagen, date, cat);
            //Salto a HomeScreen
            Parent root = FXMLLoader.load(getClass().getResource("/vista/homeScreen.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setTitle("Malgastos");
            
        }
    }
        
    private boolean Comprobar() throws AcountDAOException, IOException{
        String cantidadText = cantidad.getText();
        String unidadText = unidades.getText();

        //Comprabar si cantidad son números o solo hay un punto separador
        if(!cantidadText.matches(".*\\d.*") || !cantidadText.matches("\\d+|\\d*\\.\\d+")){
            error.setText("La cantidad tiene que ser números");
            return false;
        }
        //Comprobar si unidades solo son números
        if(!unidadText.matches(".*\\d.*")){
            error.setText("La unidad tiene que ser números");
            return false;
        }
        return true;
    }
    
    //Codigo comprobacion para asegurarse de que los valores introducidos son válidos: 
    //cantidad debe ser un valor numerico, solo el campo imagen es opcional, los demás no pueden ser nulos, no puede existir dos categorias del mismo nombre
}
