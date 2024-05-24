/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Category;
import model.Acount; 
import model.AcountDAOException;
import model.Charge;

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
    
    private Image imagen = null; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        try {
//            categoriaPrueba();
//        } catch (AcountDAOException ex) {
//            Logger.getLogger(AddGastosController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(AddGastosController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }    
    
    //=========
    //prueba categoria
//    public boolean crearCategoriaPrueba() throws AcountDAOException, IOException{
//            boolean creado1 = Acount.getInstance().registerCategory("ct", "ct");
//            boolean creado2 = Acount.getInstance().registerCategory("ca", "ca");
//            if(creado1&&creado2){
//                System.out.println("categoria creada");
//                return true; 
//            }
//            return false; 
//    }
//    private void categoriaPrueba() throws AcountDAOException, IOException{
//        if(crearCategoriaPrueba()){
//            pickerCategorias.getItems().addAll(Acount.getInstance().getUserCategories().get(3).getName());
//            pickerCategorias.getItems().addAll(Acount.getInstance().getUserCategories().get(4).getName());
//            pickerCategorias.getItems().addAll(Acount.getInstance().getUserCategories().get(1).getName());
//            pickerCategorias.getItems().addAll(Acount.getInstance().getUserCategories().get(2).getName());
//        }
//    }

    @FXML
    private void cancelarGasto(ActionEvent event) throws IOException, AcountDAOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/homeScreen.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Iniciar sesión");
       
    }
    Category c = null; 
    //==============================================================================================
    @FXML
    private void aceptarGasto(ActionEvent event) throws AcountDAOException, IOException {
//        Acount.getInstance().registerCharge(nameGasto.getText(), descripGasto.getText(), parseDouble(cantidad.getText()), parseInt(unidades.getText()), imagen, LocalDate.now(),c);
//        System.out.println("creado");
    }
    
    //Codigo comprobacion para asegurarse de que los valores introducidos son válidos: 
    //cantidad debe ser un valor numerico, solo el campo imagen es opcional, los demás no pueden ser nulos, no puede existir dos categorias del mismo nombre

}
