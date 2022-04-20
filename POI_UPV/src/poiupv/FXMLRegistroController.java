/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 *
 * @author svalero
 */
public class FXMLRegistroController implements Initializable {


 
    //properties to control valid fieds values. 
    private BooleanProperty validPassword;
    private BooleanProperty validEmail;
    private BooleanProperty equalPasswords;  

    //private BooleanBinding validFields;
    
    //When to strings are equal, compareTo returns zero
    private final int EQUALS = 0;  
    @FXML
    private TextField id_email;
    @FXML
    private Label id_incorrectEmail;
    @FXML
    private PasswordField id_password;
    @FXML
    private PasswordField id_password2;
    private Label id_passwordDiferente;
    @FXML
    private Label id_errorLabel;
    @FXML
    private Button id_buttonA;
    @FXML
    private Button id_buttonC;
    
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
     * Updates the boolProp to false.Changes to red the background of the edit. 
     * Makes the error label visible and sends the focus to the edit. 
     * @param errorLabel label added to alert the user
     * @param textField edit text added to allow user to introduce the value
     * @param boolProp property which stores if the value is correct or not
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
        if(!Utils.checkEmail(id_email.textProperty().getValueSafe())){
            manageError(id_incorrectEmail, id_email, validEmail);
        } else {
            manageCorrect(id_incorrectEmail, id_email, validEmail);
        }
    }
    
    private void checkEquals(){
        if(id_password.textProperty().getValueSafe().compareTo(id_password2.textProperty().getValueSafe()) != EQUALS) {
            showErrorMessage(id_passwordDiferente,id_password2);
            equalPasswords.setValue(Boolean.FALSE);
            id_password.textProperty().setValue("");
            id_password2.textProperty().setValue("");
            id_password.requestFocus();
            }else {
                manageCorrect(id_passwordDiferente,id_password2,equalPasswords); 
            }
        }
   
    private void checkEditPassword() {
        if(!Utils.checkPassword(id_password.textProperty().getValueSafe())){
            manageError(id_errorLabel, id_password, validPassword);
        } else {
            manageCorrect(id_errorLabel, id_password, validPassword);
        }
    }

    
    //=========================================================
    // you must initialize here all related with the object 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        validEmail = new SimpleBooleanProperty();
        validPassword = new SimpleBooleanProperty();   
        equalPasswords = new SimpleBooleanProperty();
        
        validPassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        equalPasswords.setValue(Boolean.FALSE);
        
      
        BooleanBinding validFields = Bindings.and(validEmail, validPassword).and(equalPasswords);
        
        id_email.focusedProperty().addListener((observable, oldValue, newValue)-> {
            if(!newValue){checkEditEmail(); //si se ha perdido el focus
            }});
        id_password.focusedProperty().addListener((observable, oldValue, newValue)-> {
            if(!newValue){checkEditPassword(); //si se ha perdido el focus
            }});
        id_password2.focusedProperty().addListener((observable, oldValue, newValue)-> {
            if(!newValue){checkEquals(); //si se ha perdido el focus
            }});
        
        
        id_buttonA.disableProperty().bind(Bindings.not(validFields));
     
        id_buttonC.setOnAction( (event)->{id_buttonC.getScene().getWindow().hide();});

    } 

    @FXML
    private void handleAcceptOnAction(ActionEvent event) throws IOException {
        id_email.textProperty().setValue("");
        id_password.textProperty().setValue("");
        id_password2.textProperty().setValue("");
        validEmail.setValue(Boolean.FALSE);
        validPassword.setValue(Boolean.FALSE);
        equalPasswords.setValue(Boolean.FALSE);
        
        switchToScene(event, "otro");
        
    }

   
    
}