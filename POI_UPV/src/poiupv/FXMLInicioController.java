/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package poiupv;

import DBAccess.NavegacionDAOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Navegacion;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLInicioController implements Initializable{
    
    @FXML
    private Label lIncorrectEmail;
    @FXML
    private Label lIncorrectEmail1;
    @FXML
    private Button Cancelar1;
    @FXML
    private Button Cancelar;
    @FXML
    private TextField id_usuario;
    @FXML
    private PasswordField id_contraseña;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            Navegacion navegacion = Navegacion.getSingletonNavegacion();
        } catch (NavegacionDAOException ex) {
            Logger.getLogger(FXMLInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void ExisteUsuario(){
        if(existsNickName(id_usuario)){
            
    }
    }
    
    
}
