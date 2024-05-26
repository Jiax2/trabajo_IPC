/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafxmlapplication.JavaFXMLApplication;
import model.Acount;
import model.AcountDAOException;
import model.Category;
/**
 * FXML Controller class
 *
 * @author jiaji
 */

    

public class CategoriasController implements Initializable {
    
    @FXML
    private Button catAdd;
    @FXML
    private Button catDel;
    @FXML
    private TableView<Category> catabla;

    @FXML
    private TableColumn<Acount, String> taylor;

    @FXML
    private TableColumn<Acount, String> swift;

    private ObservableList<Category> categorias = null;
    private List<Category> datos=null;
    private Acount cuentas;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            cuentas=Acount.getInstance();
            inicializaCat();
        } catch (AcountDAOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        catDel.disableProperty().bind(Bindings.equal(catabla.getSelectionModel().selectedIndexProperty(), -1));
        
    }    
    
    private void inicializaCat()throws AcountDAOException, IOException{
        datos=cuentas.getUserCategories();
        
        taylor.setCellValueFactory(new PropertyValueFactory<>("name"));
        swift.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        //Obtener categorias
        categorias=FXCollections.observableList(datos);
        catabla.setItems(categorias);
    }
    
    @FXML
    private void addCategoria(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/addCategory.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Añadir Categoria"); 
    }
    
    @FXML
    private void deleteCategoria(ActionEvent event) throws IOException, AcountDAOException {
        Category selectedCategory = catabla.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Eliminar categoria");
        alert.setContentText("¿Estas seguro que quieres eliminar esta categoria?");
        ButtonType buttonTypeAccept = new ButtonType("Aceptar");
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeAccept,buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()){
            if (result.get() == buttonTypeAccept){
                cuentas.removeCategory(selectedCategory);
                inicializaCat();
            }else{
            }
        }
    }
}