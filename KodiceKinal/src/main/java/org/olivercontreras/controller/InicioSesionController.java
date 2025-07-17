package org.olivercontreras.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.olivercontreras.db.Conexion;
import org.olivercontreras.system.Main;

/**
 * FXML Controller class
 *
 * @author KODICEKINAL S.A.
 */
public class InicioSesionController implements Initializable {

    private Main principal;

    @FXML private TextField txtUsername;
    @FXML private PasswordField pfPassword;
    @FXML private Button btnLogin, btnRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @FXML
    public void handleButtonAction(ActionEvent evento) {
        if (evento.getSource() == btnLogin) {
            verificarCredenciales();
        }else if (evento.getSource()==btnRegresar){
            System.out.println("Regresando a inicio");
            principal.inicio();
        }
    }

    public void verificarCredenciales() {
        String username = txtUsername.getText();
        String password = pfPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Error de Validación", "Debe ingresar su usuario y contraseña.");
            return;
        }

        try {
            PreparedStatement enunciado = Conexion.getInstancia().getConexion().prepareStatement(
                "SELECT password FROM Registros WHERE username = ?"
            );
            enunciado.setString(1, username);
            ResultSet resultado = enunciado.executeQuery();

            if (resultado.next()) {
                String passwordDeDB = resultado.getString("password");
                if (password.equals(passwordDeDB)) {
                    System.out.println("Acceso concedido. Bienvenido " + username);
                    principal.pPrincipal();
                } else {
                    mostrarAlerta("Acceso Denegado", "La contraseña es incorrecta.");
                }
            } else {
                mostrarAlerta("Acceso Denegado", "El nombre de usuario no se encuentra registrado.");
            }

        } catch (SQLException e) {
            mostrarAlerta("Error de Conexión", "No se pudo establecer la conexión con la base de datos.");
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        try {
            String rutaIcono = "/images/iconoKodiceKinal.png"; 
            InputStream streamIcono = getClass().getResourceAsStream(rutaIcono);
            
            if (streamIcono != null) {
                Image icono = new Image(streamIcono);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(icono);
            } else {
                System.err.println("Advertencia: No se pudo encontrar el icono en la ruta: " + rutaIcono);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el icono de la alerta.");
            e.printStackTrace();
        }
        alert.showAndWait();
    }
}