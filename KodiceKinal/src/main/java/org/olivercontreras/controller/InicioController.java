
package org.olivercontreras.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.olivercontreras.system.Main;

/**
 * FXML Controller class
 *
 * @author KODICEKINAL S.A.
 */
public class InicioController implements Initializable {

    private Main principal;
    
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    
    @FXML
    private Button btnISesion, btnAcercaDe, btnRegistrarse;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void clickActionEvent(ActionEvent evento){
        if (evento.getSource()==btnISesion) {
            System.out.println("Inicia Sesion porfavor");
            principal.iSesion();
        }else if (evento.getSource()==btnAcercaDe){
            System.out.println("Dirigiendo a Acerca de");
            principal.AcercaDeController();
        }
        else if (evento.getSource()==btnRegistrarse){
            System.out.println("Dirigiendo a Registrarse");
            principal.rController();
        }
    }
    
}
