
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
public class PaginaPrincipalController implements Initializable {

    
    private Main principal;
    
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    
    @FXML
    private Button btnClientes, btnProductos, btnRegresar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void clickActionHandler(ActionEvent evento){
        if (evento.getSource()==btnRegresar) {
            System.out.println("Regresando a la pagina de inicio");
            principal.inicio();
        }else if (evento.getSource()==btnClientes){
            System.out.println("Dirigiendo a Clientes");
            principal.cController();
        }
    }
    
}
    
