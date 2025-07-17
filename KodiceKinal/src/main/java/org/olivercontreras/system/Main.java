package org.olivercontreras.system;

/**
 *
 * @author KODICEKINAL S.A.
 */

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.olivercontreras.controller.AcercaDeController;
import org.olivercontreras.controller.ClientesController;
import org.olivercontreras.controller.InicioController;
import org.olivercontreras.controller.InicioSesionController;
import org.olivercontreras.controller.PaginaPrincipalController;
import org.olivercontreras.controller.ProductosController;
import org.olivercontreras.controller.RegistrarseController;

public class Main extends Application{

    private Scene escena;
    private Stage Inicio;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage escenario) throws Exception {
        this.Inicio = escenario;
        inicio();
        
        Inicio.setScene(escena);
        Inicio.setTitle("KodiceKinal");
        Inicio.show();
    }
    
    public Initializable cambiarEscena(String fxml, double ancho, double alto) throws Exception {
        Initializable interfazCargada = null;
        
        FXMLLoader cargadorFXML = new FXMLLoader();
        
        InputStream archivoFXML = Main.class.getResourceAsStream("/view/" + fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Main.class.getResource("/view/"+fxml));
        
        escena = new Scene(cargadorFXML.load(archivoFXML), ancho, alto);
        Inicio.setScene(escena);
        Inicio.sizeToScene();
        
        interfazCargada = cargadorFXML.getController();
        
        return interfazCargada;
    }
    
    public void inicio(){
    try {
        InicioController ic = (InicioController) cambiarEscena("InicioView.fxml", 600, 400);
        ic.setPrincipal(this);
    } catch (Exception ex) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
    public void iSesion(){
    try {
        InicioSesionController isc = (InicioSesionController) cambiarEscena("InicioSesionView.fxml", 600, 400);
        isc.setPrincipal(this);
    } catch (Exception ex) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
    public void pPrincipal(){
    try {
        PaginaPrincipalController ppc = (PaginaPrincipalController) cambiarEscena("PaginaPrincipalView.fxml", 600, 400);
        ppc.setPrincipal(this);
    } catch (Exception ex) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
    public void cController(){
    try {
        ClientesController cc = (ClientesController) cambiarEscena("ClientesView.fxml", 794, 537);
        cc.setPrincipal(this);
    } catch (Exception ex) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
    public void AcercaDeController(){
    try {
        AcercaDeController adc = (AcercaDeController) cambiarEscena("AcercaDeView.fxml", 600, 400);
        adc.setPrincipal(this);
    } catch (Exception ex) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
}
 
    public void pController(){
    try {
        ProductosController pc = (ProductosController) cambiarEscena("ProductosView.fxml", 821, 535);
        pc.setPrincipal(this);
    } catch (Exception ex) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
    public void rController(){
    try {
        RegistrarseController rc = (RegistrarseController) cambiarEscena("RegistrarseView.fxml", 600, 400);
        rc.setPrincipal(this);
    } catch (Exception ex) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
}