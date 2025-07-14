
package org.olivercontreras.controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.olivercontreras.db.Conexion;
import org.olivercontreras.model.Clientes;
import org.olivercontreras.system.Main;

/**
 * FXML Controller class
 *
 * @author KODICEKINAL S.A.
 */
public class ClientesController implements Initializable {

    @FXML private TableView<Clientes> tablaClientes;
    @FXML private TableColumn colIdCliente, colNombreCliente, colApellidoCliente, colEmailCliente, colTelefonoCliente, colDireccionCliente, colFechaIngreso;
    
    @FXML
    private TextField txtId, txtNombre, txtApellido, txtTelefono, txtDireccion, txtEmail, txtBuscar;
        
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar, btnRegresar;
    
    @FXML
    private DatePicker dpFecha;
    
    private Main principal;
    private ObservableList<Clientes> listaClientes;
    private Clientes modeloCliente;
    private enum acciones {AGREGAR, ELIMINAR, ACTUALIZAR, NINGUNA}
    acciones tipoDeAccion = acciones.NINGUNA;
     
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    public Main getPrincipal() {
        return principal;
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configurarColumnas();
        cargarTablaClientes();
        
        tablaClientes.setOnMouseClicked(eventHandler -> cargarClienteEnTextField());
    }    
    
    @FXML
    public void clickActionHandler(ActionEvent evento){
        if (evento.getSource()==btnRegresar) {
            System.out.println("Regreso al Menu Principal");
            principal.pPrincipal();
        }
    }
    
    public void configurarColumnas(){
        //Formato de columnas
        colIdCliente.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("idCliente"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombre"));
        colApellidoCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellido"));
        colTelefonoCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefono"));
        colDireccionCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccion"));
        colEmailCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("email"));
        colFechaIngreso.setCellValueFactory(new PropertyValueFactory<Clientes, Date>("fechaIngreso"));
    }
    
    public void cargarTablaClientes(){
        listaClientes = FXCollections.observableArrayList(listarClientes());
        tablaClientes.setItems(listaClientes);
        tablaClientes.getSelectionModel().selectFirst();
        cargarClienteEnTextField();
    }
    
    public void cargarClienteEnTextField() {
        //tablaClientes -> modelo = textField
        Clientes clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        txtId.setText(String.valueOf(clienteSeleccionado.getIdCliente()));
        txtNombre.setText(clienteSeleccionado.getNombre());
        txtApellido.setText(clienteSeleccionado.getApellido());
        txtTelefono.setText(clienteSeleccionado.getTelefono());
        txtDireccion.setText(clienteSeleccionado.getDireccion());
        txtEmail.setText(clienteSeleccionado.getEmail());
        dpFecha.setValue(clienteSeleccionado.getFechaIngreso());
    }
    
    public ArrayList<Clientes> listarClientes(){
        ArrayList<Clientes> clientes = new ArrayList<>();
        
        try {
            Connection conexion = Conexion.getInstancia().getConexion();
            CallableStatement enunciado = (CallableStatement) Conexion.getInstancia().getConexion().prepareCall("call sp_listarCliente();");
            ResultSet resultado = enunciado.executeQuery();
            while (resultado.next()) {
                clientes.add(new Clientes(
                                                            resultado.getInt(1),
                                                            resultado.getString(2),
                                                            resultado.getString(3),
                                                            resultado.getString(4),
                                                            resultado.getString(5),
                                                            resultado.getString(6),
                                                            resultado.getDate(7).toLocalDate()));
            }
            
        } catch (SQLException ex) {
            System.out.println("Error al cargar Clientes de MySQL: " + ex.getSQLState());
            ex.printStackTrace();
        }
        return clientes;
    }
    
    private Clientes obtenerModeloCliente() {
        int codigoCliente;
        if (txtId.getText().isEmpty()) {
            codigoCliente = 1;
        }else {
            codigoCliente = Integer.parseInt(txtId.getText());
        }
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String telefono = txtTelefono.getText();
        String direccion = txtDireccion.getText();
        String email = txtEmail.getText();
        Clientes cliente = new Clientes(codigoCliente, nombre, apellido, email, telefono, direccion, dpFecha.getValue());
        return cliente;
    }
    
    public void agregarCliente(){
        modeloCliente = obtenerModeloCliente();
        try {
            CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("call sp_agregarCliente(?,?,?,?,?,?);");
            enunciado.setString(1, modeloCliente.getNombre());
            enunciado.setString(2, modeloCliente.getApellido());
            enunciado.setString(3, modeloCliente.getTelefono());
            enunciado.setString(4, modeloCliente.getDireccion());
            enunciado.setString(5, modeloCliente.getEmail());
            enunciado.setDate(6, Date.valueOf(modeloCliente.getFechaIngreso()));
            enunciado.execute();
            cargarTablaClientes();
        } catch (SQLException ex) {
            System.out.println("Error al agregar");
            ex.printStackTrace();
        }
    }
    
    public void actualizarCliente(){
        modeloCliente = obtenerModeloCliente();
        try {
            CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("call sp_editarCliente(?,?,?,?,?,?,?);");
            enunciado.setInt(1, modeloCliente.getIdCliente());
            enunciado.setString(2, modeloCliente.getNombre());
            enunciado.setString(3, modeloCliente.getApellido());
            enunciado.setString(4, modeloCliente.getTelefono());
            enunciado.setString(5, modeloCliente.getDireccion());
            enunciado.setString(6, modeloCliente.getEmail());
            enunciado.setDate(7, Date.valueOf(modeloCliente.getFechaIngreso()));
            enunciado.execute();
            cargarTablaClientes();
        }catch (SQLException e) {
            System.out.println("Error al editar | actualizar");
            e.printStackTrace();
        }
    }
    
    public void eliminarCliente(){
        modeloCliente = obtenerModeloCliente();
        try{
            CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("call sp_eliminarCliente(?);");
            enunciado.setInt(1, modeloCliente.getIdCliente());
            enunciado.execute();
            cargarTablaClientes();
        } catch(SQLException e) {
            System.out.println("Error al Eliminar Cliente: ");
            e.printStackTrace();
        }
    }
    
    public void limpiarTextField(){
        txtId.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        txtEmail.clear();
        dpFecha.setValue(null);
    }
    
    private void cambiarEstado(boolean estado){
        txtNombre.setDisable(estado);
        txtApellido.setDisable(estado);
        txtTelefono.setDisable(estado);
        txtDireccion.setDisable(estado);
        txtEmail.setDisable(estado);
        dpFecha.setDisable(estado);
        btnGuardar.setDisable(estado);
        btnCancelar.setDisable(estado);
    }
    
    private void habilitarDeshabilitarNodo(){
        boolean desactivado = txtNombre.isDisable();
        cambiarEstado(!desactivado);
        btnSiguiente.setDisable(desactivado);
        btnAnterior.setDisable(desactivado);
        btnNuevo.setDisable(desactivado);
        btnEditar.setDisable(desactivado);
        btnEliminar.setDisable(desactivado);
        btnGuardar.setDisable(!desactivado);
        btnCancelar.setDisable(!desactivado);
    }
    
    //ACCIONES: anterior, siguiente, nuevo, editar, eliminar, guardar, cancelar
    @FXML
    private void btnAnteriorAction(){
        int indice = tablaClientes.getSelectionModel().getSelectedIndex();
        if (indice > 0) {
            tablaClientes.getSelectionModel().select(indice-1);
            cargarClienteEnTextField();
        }
    }
    
    @FXML
    private void btnSiguienteAction(){
        int indice = tablaClientes.getSelectionModel().getSelectedIndex();
        if (indice < listaClientes.size()-1) {
            tablaClientes.getSelectionModel().select(indice+1);
            cargarClienteEnTextField();
        }
    }
    
    @FXML
    private void btnNuevoAction(){
        limpiarTextField();
        txtNombre.requestFocus();
        tipoDeAccion = acciones.AGREGAR;
        habilitarDeshabilitarNodo();
    }
    
    @FXML
    private void btnEditarAction(){
        tipoDeAccion = acciones.ACTUALIZAR;
        habilitarDeshabilitarNodo();
    }
    
    @FXML
    private void btnEliminarAction(){
        eliminarCliente();
    }
    
    @FXML
    private void btnCancelarAction(){
        cargarClienteEnTextField();
        habilitarDeshabilitarNodo();
    }
    
    @FXML
    private void btnGuardarAction(){
        if (tipoDeAccion == acciones.AGREGAR) {
            agregarCliente();
            tipoDeAccion = acciones.NINGUNA;
        }else if (tipoDeAccion == acciones.ACTUALIZAR) {
            actualizarCliente();
            tipoDeAccion = acciones.NINGUNA;
        }
        habilitarDeshabilitarNodo();
    }
    
    @FXML
    private void buscarCliente(){
        ArrayList<Clientes> resultadoBusqueda = new ArrayList<>();
        String nombre = txtBuscar.getText();
        for (Clientes cliente : listaClientes){
            if (cliente.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultadoBusqueda.add(cliente);
            }
        }
        tablaClientes.setItems(FXCollections.observableArrayList(resultadoBusqueda));
        if (!resultadoBusqueda.isEmpty()) {
            tablaClientes.getSelectionModel().selectFirst();
        }
        //cargarTablaClientes();
    }
    
}
