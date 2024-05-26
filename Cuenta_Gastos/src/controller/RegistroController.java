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
import java.io.InputStream;
import java.time.LocalDate;
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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafxmlapplication.JavaFXMLApplication;
import model.Acount;
import model.AcountDAOException;


/**
 * FXML Controller class
 *
 * @author jiaji
 */
public class RegistroController implements Initializable {
    
    @FXML
    private TextField nombre;
    @FXML
    private TextField usuario;
    @FXML
    private PasswordField pass1;
    @FXML
    private PasswordField pass2;
    @FXML
    private TextField mail;
    @FXML
    private Text error;
    @FXML
    private ImageView imagen;
    @FXML
    private TextField apellido;
    @FXML
    private Button registro;
    /**
     * Initializes the controller class.
     */
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    public Acount cuentas;
    private Image userImagen=null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Carga Acount
        try {
            cuentas = Acount.getInstance();
            
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Carga Imagen
        userImagen = defaultImagen();
        imagen.setImage(userImagen);
        //Desahabilita el boton Registrarse
        registro.disableProperty().bind(Bindings.createBooleanBinding(() ->
                nombre.getText().isEmpty() ||
                apellido.getText().isEmpty() ||
                usuario.getText().isEmpty() ||
                pass1.getText().isEmpty() ||
                pass2.getText().isEmpty() ||
                mail.getText().isEmpty(),
                nombre.textProperty(),
                apellido.textProperty(),
                usuario.textProperty(),
                pass1.textProperty(),
                pass2.textProperty(),
                mail.textProperty()
        ));
    }    
    
    @FXML
    private void irInicioSesion(MouseEvent event) throws IOException {
        //Cambia a iniciar sesión
        Parent root = FXMLLoader.load(getClass().getResource("/vista/inicioSesion.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Iniciar sesión");
    }
    
    @FXML
    private void pulsadoRegistro(ActionEvent event) throws IOException, AcountDAOException{
        if(Comprobar()){
            error.setText("");
            //Registrar el usuario
            cuentas.registerUser(nombre.getText(), apellido.getText(), mail.getText(),
                    usuario.getText(), pass1.getText(), userImagen, LocalDate.now());
            //Inicia usuario y cambia a homeScreen
            if (Acount.getInstance().logInUserByCredentials(usuario.getText(), pass1.getText())==true){
                Parent root = FXMLLoader.load(getClass().getResource("/vista/homeScreen.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                stage.setTitle("MalGastos");   
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
                userImagen = new Image(selectedFile.toURI().toString());

                // Establecer la imagen en el ImageView correspondiente
                imagen.setImage(userImagen);
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
        String pass1Text = pass1.getText();
        String pass2Text = pass2.getText();
        String mailText = mail.getText();

        if(Acount.getInstance().logInUserByCredentials(usuarioText, pass1Text)){
            error.setText("Ya existe este usuario");
            return false;
        }

        if(pass1Text.length() <= 8 || pass1Text.length() >= 20 ||
        !pass1Text.matches(".*\\d.*") ||
        !pass1Text.matches(".*[a-z].*") ||
        !pass1Text.matches(".*[A-Z].*") ||
        !pass1Text.matches(".*[!@#$%^&+=_¿¡?*/ªº€¬-].*") ||
        pass1Text.contains(" ")){
            error.setText("Contraseña inválida");
            pass1.clear();
            pass2.clear();
            return false;
        }

        if(!pass1Text.equals(pass2Text)){
            error.setText("Las contraseñas no coinciden");
            return false;
        }

        if(!comprobarArrobaPunto(mailText)){
            error.setText("El correo no es válido");
            return false;
        }

        if(usuarioText.length() <= 6 || usuarioText.length() >= 15 || usuarioText.contains(" ")){
            error.setText("Usuario inválido");
            return false;
        }

        return true;
    }
    
    //Carga imagen por defecto
     private Image defaultImagen(){
        InputStream input = getClass().getResourceAsStream("/images/default.png");
        return new Image(input);
    }

    @FXML
    private void info(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Información de la aplicación");
        alert.setContentText("MalGastos\n"+"Es una aplicación para gestión de gastos.");
        alert.showAndWait();
    }

    @FXML
    private void salir(ActionEvent event) {
        System.exit(0);
    }
}
