
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
public class InicioSesionController implements Initializable {

    private Main principal;
    
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    
    @FXML
    private Button btnPPrincipal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void clickActionEvent(ActionEvent evento){
        if (evento.getSource()==btnPPrincipal) {
            System.out.println("Pagina principal");
            principal.iSesion();
        }
    }
    
}
