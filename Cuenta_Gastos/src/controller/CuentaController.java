/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafxmlapplication.JavaFXMLApplication;
import model.Acount;
import model.AcountDAOException;
import model.User;

/**
 * FXML Controller class
 *
 * @author jiaji
 */
public class CuentaController implements Initializable {

    @FXML
    private TextField nombre;
    @FXML
    private TextField apellido;
    @FXML
    private TextField pass;
    @FXML
    private TextField mail;
    @FXML
    private Button saveChange;
    @FXML
    private Button buserImage;
    @FXML
    private ImageView userImagen;
    @FXML
    private TextField usuario;
    @FXML
    private Text errMen;
    
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private Acount cuentas;
    private User user;
    private Image imagen;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            cuentas=Acount.getInstance();
            user=cuentas.getLoggedUser();
            //Inicializa la imagen y el texto del usuario 
            imagen=user.getImage();
            nombre.setText(user.getName());
            apellido.setText(user.getSurname());
            usuario.setText(user.getNickName());
            pass.setText(user.getPassword());
            mail.setText(user.getEmail());
            userImagen.setImage(user.getImage());
        } catch (AcountDAOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        saveChange.disableProperty().bind(Bindings.createBooleanBinding(() ->
                nombre.getText().isEmpty() ||
                apellido.getText().isEmpty() ||
                usuario.getText().isEmpty() ||
                pass.getText().isEmpty() ||
                mail.getText().isEmpty(),
                nombre.textProperty(),
                apellido.textProperty(),
                usuario.textProperty(),
                pass.textProperty(),
                mail.textProperty()
        ));
    }    
    
    @FXML
    private void saveChange(ActionEvent event)throws AcountDAOException, IOException{
        if(Comprobar()){
            errMen.setText("");
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("Guardar cambios");
            alert.setContentText("¿Estas seguro que quieres guardar estos cambios?");
            ButtonType buttonTypeAccept = new ButtonType("Aceptar");
            ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeAccept,buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()){
                if (result.get() == buttonTypeAccept){
                    user.setName(nombre.getText());
                    user.setSurname(apellido.getText());
                    user.setPassword(pass.getText());
                    user.setEmail(mail.getText());
                    user.setImage(imagen);
                    Parent root = FXMLLoader.load(getClass().getResource("/vista/homeScreen.fxml"));
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    stage.setTitle("Malgastos");
                }else{
                }
            }
        }
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
                imagen = new Image(selectedFile.toURI().toString());

                // Establecer la imagen en el ImageView correspondiente
                userImagen.setImage(imagen);
            } catch (Exception e) {
                // Manejar cualquier excepción que pueda ocurrir al cargar la imagen
                e.printStackTrace();
            }
        }
    }
    private boolean comprobarArrobaPunto(String mail){
        // Compilar la expresión regular
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        // Crear un matcher para el string de entrada
        Matcher matcher = pattern.matcher(mail);
        // Devolver si el string coincide con la expresión regular
        return matcher.matches();
    }
    
    private boolean Comprobar() throws AcountDAOException, IOException{
        String usuarioText = usuario.getText();
        String passText = pass.getText();
        String mailText = mail.getText();

        if(passText.length() <= 8 || passText.length() >= 20 ||
        !passText.matches(".*\\d.*") ||
        !passText.matches(".*[a-z].*") ||
        !passText.matches(".*[A-Z].*") ||
        !passText.matches(".*[!@#$%^&+=_.,¿¡?*/ªº€¬-].*") ||
        passText.contains(" ")){
            errMen.setText("Contraseña inválida");
            return false;
        }

        else if(!comprobarArrobaPunto(mailText)){
            errMen.setText("El correo no es válido");
            return false;
        }

        return true;
    }
}
