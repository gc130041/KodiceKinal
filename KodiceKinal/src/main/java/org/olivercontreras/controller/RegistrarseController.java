package org.olivercontreras.controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.olivercontreras.db.Conexion;
import org.olivercontreras.model.Usuarios;
import org.olivercontreras.system.Main;

/**
 * FXML Controller class
 *
 * @author KODICEKINAL S.A.
 */
public class RegistrarseController implements Initializable {

    private Main principal;

    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;
    @FXML private PasswordField pfPassword;
    @FXML private Button btnRegistrar, btnCancelar, btnRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @FXML
    public void handleButtonAction(ActionEvent evento) {
        if (evento.getSource() == btnRegistrar) {
            agregarUsuario();
        }else if (evento.getSource()==btnCancelar){
            limpiarCampos();
        }else if (evento.getSource()==btnRegresar){
            System.out.println("Regresando a inicio");
            principal.inicio();
        }
    }

    private Usuarios obtenerDatosDeFormulario() {
        Usuarios usuario = new Usuarios();
        usuario.setUsername(txtUsername.getText());
        usuario.setEmail(txtEmail.getText());
        usuario.setPassword(pfPassword.getText());
        return usuario;
    }
    
    public void agregarUsuario() {
        if (txtUsername.getText().isEmpty() || txtEmail.getText().isEmpty() || pfPassword.getText().isEmpty()) {
            mostrarAlerta("Campos Requeridos", "Por favor, complete todos los campos para el registro.");
            return;
        }

        Usuarios nuevoUsuario = obtenerDatosDeFormulario();

        try {
            CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("call sp_agregarRegistro(?, ?, ?)");
            enunciado.setString(1, nuevoUsuario.getUsername());
            enunciado.setString(2, nuevoUsuario.getEmail());
            enunciado.setString(3, nuevoUsuario.getPassword());
            enunciado.execute();

            mostrarAlerta("Registro Exitoso", "El usuario '" + nuevoUsuario.getUsername() + "' ha sido creado.");
            limpiarCampos();

        } catch (SQLException e) {
            mostrarAlerta("Error en el Registro", "No se pudo crear el usuario. El nombre de usuario o el email ya existen.");
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        txtUsername.clear();
        txtEmail.clear();
        pfPassword.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}