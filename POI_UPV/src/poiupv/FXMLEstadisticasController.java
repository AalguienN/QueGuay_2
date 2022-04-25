/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import DBAccess.NavegacionDAOException;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Navegacion;

/**
 * FXML Controller class
 *
 * @author Adrián
 */
public class FXMLEstadisticasController implements Initializable {

    private Navegacion datos;
    private Stage primaryStage;
    private Scene scene;
    @FXML
    private Button id_volverMenuPrincipalButton;
    @FXML
    private Label id_porcentajeAcierto;
    @FXML
    private CheckBox id_checkFecha;
    @FXML
    private DatePicker id_fechaFiltrar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
    private void switchToScene(ActionEvent event, String name) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(name+".fxml"));
        primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void volverMenuPrincipal(ActionEvent event) throws IOException {
        switchToScene(event, "FXMLPrincipal");
    }
    
    
}
