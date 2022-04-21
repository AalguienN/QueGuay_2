/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package poiupv;

import DBAccess.NavegacionDAOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Navegacion;
import model.User;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLInicioController implements Initializable{
    
    @FXML
    private TextField id_usuario;
    @FXML
    private PasswordField id_contraseña;

    private Navegacion datos;
    @FXML
    private Button id_iniciarSesion;
    @FXML
    private Label id_usuarioIncorrecto;
    @FXML
    private Label id_contraseñaIncorrecta;
    @FXML
    private Button id_Cancelar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            datos = Navegacion.getSingletonNavegacion();
            /*String nickName = "nickName";
            String email = "email@domain.es";
            String password = "miPassword";
            LocalDate birthdate = LocalDate.now().minusYears(18);
            User result = datos.registerUser(nickName, email, password, birthdate);*/
        } catch (NavegacionDAOException ex) {
            Logger.getLogger(FXMLInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
        id_usuarioIncorrecto.visibleProperty().set(false);
        if(!datos.exitsNickName(id_usuario.textProperty().getValueSafe())){
           id_usuarioIncorrecto.visibleProperty().set(true);
        }else{
            User user = datos.loginUser(id_usuario.textProperty().getValueSafe(),
            id_contraseña.textProperty().getValueSafe());
                if(user == null){
                        id_contraseñaIncorrecta.visibleProperty().set(true);
                }
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        
    }
    
    
}
 /*User user = datos.loginUser(id_usuario.textProperty().getValueSafe(),
            id_contraseña.textProperty().getValueSafe());
                if(user.checkCredentials(id_usuario.textProperty().getValueSafe(),
                    id_contraseña.textProperty().getValueSafe())){
                    
                }
*/