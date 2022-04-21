/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package poiupv;

import DBAccess.NavegacionDAOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Math.abs;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Navegacion;

/**
 * FXML Controller class
 *
 * @author marta
 */
public class FXMLRegistroController implements Initializable {
    
    //properties to control valid fieds values. 
    private BooleanProperty validPassword;
    private BooleanProperty validEmail;
    private BooleanProperty equalPasswords;  
    private BooleanProperty validName;
    private BooleanProperty validAge;
    
    //private BooleanBinding validFields;
    
    //When to strings are equal, compareTo returns zero
    private final int EQUALS = 0;  
    
    @FXML
    private Label id_errorLabel;
    @FXML
    private Button id_buttonA;
    @FXML
    private Button id_buttonC;
    @FXML
    private TextField id_nombre;
    @FXML
    private Label id_ErrorName;
    @FXML
    private TextField id_correo;
    @FXML
    private Label id_ErrorCorreo;
    @FXML
    private PasswordField id_contraseña;
    @FXML
    private Label id_ErrorContraValido;
    @FXML
    private PasswordField id_contraseña1;
    @FXML
    private Label id_ErrorContraIgual;
    @FXML
    private DatePicker id_FechaNacimiento;
    @FXML
    private Label id_ErrorEdad;
    @FXML
    private ImageView id_imagen;
    @FXML
    private Label id_SelecImagen;

    //COMO CAMBIAR DE ESCENA (SUSTITUIR)
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
   //CAMBIAR ESCENA
    public void switchToScene(ActionEvent event, String name) throws IOException {
  
        Parent root = FXMLLoader.load(getClass().getResource(name+".fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
   
    //FIN

    /**
     * Initializes the controller class.
     */
    private void manageError(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel,textField);
 
    }
    /**
     * Updates the boolProp to true. Changes the background 
     * of the edit to the default value. Makes the error label invisible. 
     * @param errorLabel label added to alert the user
     * @param textField edit text added to allow user to introduce the value
     * @param boolProp property which stores if the value is correct or not
     */
    private void manageCorrect(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,textField);
        
    }
    /**
     * Changes to red the background of the edit and
     * makes the error label visible
     * @param errorLabel
     * @param textField 
     */
    private void showErrorMessage(Label errorLabel,TextField textField)
    {
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");    
    }
    /**
     * Changes the background of the edit to the default value
     * and makes the error label invisible.
     * @param errorLabel
     * @param textField 
     */
    private void hideErrorMessage(Label errorLabel,TextField textField)
    {
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("");
    }
    
    private void checkEquals(){
        if(id_contraseña.textProperty().getValueSafe().compareTo(id_contraseña1.textProperty().getValueSafe()) != EQUALS) {
            showErrorMessage(id_ErrorContraIgual,id_contraseña1);
            equalPasswords.setValue(Boolean.FALSE);
            }else {
                manageCorrect(id_ErrorContraIgual,id_contraseña1,equalPasswords); 
            }
        }
   
    private void checkEditPassword() {
        if(!Utils.checkPassword(id_contraseña.textProperty().getValueSafe())){
            manageError(id_ErrorContraValido, id_contraseña, validPassword);
            //abrir ventada de aviso con las condiciones de la contraseña
        } else {
            manageCorrect(id_ErrorContraValido, id_contraseña, validPassword);
        }
    }
    

    // COMPROBAR CORREO: si el correo ya se encuentra en base de datos error, sino validEmail = TRUE
    private void checkEditEmail() {
        
        
        if(!Utils.checkEmail(id_correo.textProperty().getValueSafe())){ //condicion comprobar base de datos
            manageError(id_ErrorCorreo, id_correo, validEmail);
        } else {
            manageCorrect(id_ErrorCorreo, id_correo, validEmail);
        }
    }
    
     // COMPROBAR NOMBRE : si el nombre ya se encuentra en base de datos error (textfield rojo), sino validName = TRUE
     private void checkEditName() {
        
        if(!Utils.checkEmail(id_correo.textProperty().getValueSafe())){ //condicion comprobar base de datos
            manageError(id_ErrorCorreo, id_correo, validEmail);
        } else {
            manageCorrect(id_ErrorCorreo, id_correo, validEmail);
        }
    }
    
  
    // COMPROBRA EDAD: si usuario es menor de edad error, sino validAge = TRUE
    private void checkAge() {
        LocalDate fecha = id_FechaNacimiento.getValue();
        LocalDate actual = LocalDate.now();
        
        int años = actual.getYear() - fecha.getYear();
        int meses = abs(actual.getMonthValue() - fecha.getMonthValue());
        int dias = abs(actual.getDayOfMonth() - fecha.getDayOfMonth());
 
        if(años >= 18) {
            id_ErrorEdad.setText("");
            validAge.setValue(Boolean.TRUE);
        }else if (años == 17 && meses == 0 && dias == 0) {
            id_ErrorEdad.setText("");
            validAge.setValue(Boolean.TRUE);
        }else {id_ErrorEdad.setText("El usuario debe ser mayor de edad.");}
        
    }

    
    //=========================================================
    // you must initialize here all related with the object 
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        
        //variables valid_
        validEmail = new SimpleBooleanProperty();
        validPassword = new SimpleBooleanProperty();   
        equalPasswords = new SimpleBooleanProperty();
        validName = new SimpleBooleanProperty();
        validAge = new SimpleBooleanProperty();
        
        //inicializadas a FALSE
        validPassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        equalPasswords.setValue(Boolean.FALSE);
        validName.setValue(Boolean.FALSE);
        validAge.setValue(Boolean.FALSE);
        
        //AND de todas las condiciones, la comprobacion final es sobre validFields
        BooleanBinding validFields = Bindings.and(validEmail, validPassword).and(equalPasswords).and(validName).and(validAge);
                
        id_correo.focusedProperty().addListener((observable, oldValue, newValue)-> {
            if(!newValue){checkEditEmail(); 
            }});
        id_contraseña.focusedProperty().addListener((observable, oldValue, newValue)-> { //HAY QUE SOBREESCRIBIR EL PATRON DE LA CONTRASEÑA
            if(!newValue){checkEditPassword(); 
            }});
        id_contraseña1.focusedProperty().addListener((observable, oldValue, newValue)-> {
            if(!newValue){checkEquals(); 
            }});
        
        //FALTA POR IMPLEMENTAR (necesita usuario y base de datos)
        id_nombre.focusedProperty().addListener((observable, oldValue, newValue)-> {
            if(!newValue){//chekName();; 
            }});
        
        //comprobacion final
        id_buttonA.disableProperty().bind(Bindings.not(validFields));

        
    }    

    @FXML   //CANCELAR... sustituir el string por nombre del .fxml de la ventana INICIAR SESION
    private void handleCancelOnAction(ActionEvent event) throws IOException {
        switchToScene(event, "FXMLDocument");
    }

    @FXML   //ACEPTAR... sustituir el string por nombre del .fxml de la ventana FUNCIONES
    private void handleAcceptOnAction(ActionEvent event) throws IOException {
        switchToScene(event, "FXMLDocument");
    }

    @FXML   //SELECCIONAR IMAGEN... falta mantener el ratio de imagen (sino el resto de bloques se descolocan)
    private void handlePressedAction(MouseEvent event) throws FileNotFoundException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona imagen de perfil");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        
        Image imagen = new Image(new FileInputStream(selectedFile));
        id_imagen.setImage(imagen);  
    }

    @FXML   //SELECCION DE FECHA NACIMIENTO
    private void handleDate(ActionEvent event) {
        checkAge();
    }
    
    
}
