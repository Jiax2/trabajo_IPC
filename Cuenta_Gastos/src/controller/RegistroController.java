/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.awt.Image;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
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
    private Button bimagen;
    /**
     * Initializes the controller class.
     */
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    public Acount cuenta;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cuenta=cuenta.getInstance();
        } catch (AcountDAOException ex) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        while(Comprobar()){
            //Registrar el usuario
            cuenta.registerUser(nombre.getText(), apellido.getText(), mail.getText(), usuario.getText(), pass1.getText(), null, LocalDate.MAX);
            //Cambia a homeScreen
            Parent root = FXMLLoader.load(getClass().getResource("/vista/homeScreen.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setTitle("MalGastos");   
        }
    }
    
  /*@FXML
  private void imagen(ActionEvent event) throws FileNotFoundException{
      String url = "c:"+File.separator+"images"+File.separator;
      Image avatar = new Image(new FileInputStream(url));
      imagen.imageProperty().setValue(avatar);
}*/
    
    private boolean comprobarArrobaPunto(String mail){
        // Compilar la expresión regular
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        // Crear un matcher para el string de entrada
        Matcher matcher = pattern.matcher(mail);
        // Devolver si el string coincide con la expresión regular
        return matcher.matches();
    }
    
    private boolean Comprobar() throws AcountDAOException, IOException{
       String caseType = "";
        // Identificación de la condición que se cumple
        if (nombre.getText().isEmpty() || usuario.getText().isEmpty() || pass1.getText().isEmpty() || pass2.getText().isEmpty() || mail.getText().isEmpty()) {
            caseType = "faltanCampos";
        }
        if (Acount.getInstance().logInUserByCredentials(usuario.getText(), pass1.getText())) {
            caseType = "usuarioExiste";
        }
        if (!pass1.getText().equals(pass2.getText())) {
            caseType = "contraseñaDif";
        }
        if(!comprobarArrobaPunto(mail.getText())){
            caseType="mailValido";
        }
        if(pass1.getText().length()>20){
        caseType="contraLarga";
        }
        // Manejo de los casos identificados con switch
        switch (caseType) {
            case "faltanCampos":
                error.setText("Faltan campos por completar");
                return false;
            case "usuarioExiste":
                error.setText("Ya existe este usuario");
                return false;
            case "contraseñaDif":
                error.setText("Las contraseñas no coinciden");
                return false;
            case "mailValido":
                error.setText("El correo no es válido");
                return false;
            case "contraLarga": 
                error.setText("La contraseña es muy larga");
                return false;
            default:
                return true;
        }
    }
}
