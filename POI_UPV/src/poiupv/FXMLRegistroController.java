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
import java.net.URL;
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
        textField.requestFocus();
 
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

    private void checkEditEmail() {
        if(!Utils.checkEmail(id_correo.textProperty().getValueSafe())){
            manageError(id_ErrorCorreo, id_correo, validEmail);
        } else {
            manageCorrect(id_ErrorCorreo, id_correo, validEmail);
        }
    }
    
    private void checkEquals(){
        if(id_contraseña.textProperty().getValueSafe().compareTo(id_contraseña1.textProperty().getValueSafe()) != EQUALS) {
            showErrorMessage(id_ErrorContraIgual,id_contraseña1);
            equalPasswords.setValue(Boolean.FALSE);
            id_contraseña.textProperty().setValue("");
            id_contraseña1.textProperty().setValue("");
            id_contraseña.requestFocus();
            }else {
                manageCorrect(id_ErrorContraIgual,id_contraseña1,equalPasswords); 
            }
        }
   
    private void checkEditPassword() {
        if(!Utils.checkPassword(id_contraseña.textProperty().getValueSafe())){
            manageError(id_errorLabel, id_contraseña, validPassword);
        } else {
            manageCorrect(id_errorLabel, id_contraseña, validPassword);
        }
    }

    
    //=========================================================
    // you must initialize here all related with the object 
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        // TODO
        
        validEmail = new SimpleBooleanProperty();
        validPassword = new SimpleBooleanProperty();   
        equalPasswords = new SimpleBooleanProperty();
        validName = new SimpleBooleanProperty();
        validAge = new SimpleBooleanProperty();
        
        validPassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        equalPasswords.setValue(Boolean.FALSE);
        
      
        BooleanBinding validFields = Bindings.and(validEmail, validPassword).and(equalPasswords).and(validName).and(validAge);
        
        id_correo.focusedProperty().addListener((observable, oldValue, newValue)-> {
            if(!newValue){checkEditEmail(); //si se ha perdido el focus
            }});
        id_contraseña.focusedProperty().addListener((observable, oldValue, newValue)-> {
            if(!newValue){checkEditPassword(); //si se ha perdido el focus
            }});
        id_contraseña1.focusedProperty().addListener((observable, oldValue, newValue)-> {
            if(!newValue){checkEquals(); //si se ha perdido el focus
            }});
        
        
        id_buttonA.disableProperty().bind(Bindings.not(validFields)); // debe pasar a la ventana de FUNCIONES
     
        id_buttonC.setOnAction( (event)->{id_buttonC.getScene().getWindow().hide();});  //debe volver a la ventana de INICIAR SESION

    }    

    @FXML
    private void handleCancelOnAction(ActionEvent event) {
        
    }

    @FXML
    private void handleAcceptOnAction(ActionEvent event) {
        
    }

    @FXML
    private void handlePressedAction(MouseEvent event) throws FileNotFoundException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona imagen de perfil");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        
        Image imagen = new Image(new FileInputStream(selectedFile));
        id_imagen.setImage(imagen);
         
    }
    
    
}
