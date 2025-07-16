
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import org.olivercontreras.db.Conexion;
import org.olivercontreras.model.Clientes;
import org.olivercontreras.model.Productos;
import org.olivercontreras.system.Main;

/**
 * FXML Controller class
 *
 * @author KODICEKINAL S.A.
 */
public class ProductosController implements Initializable {

    @FXML private TableView<Productos> tablaProductos;
    @FXML private TableColumn colIdProducto, colTitulo, colAutor, colFechaPublicacion, colPrecio, colDisponibilidad, colIDF;
    
    @FXML
    private TextField txtId, txtTitulo, txtAutor, txtBuscar;
        
    @FXML
    private Button btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar, btnRegresar;
    
    @FXML
    RadioButton rbDisponible, rbAgotado;
    
    @FXML
    private Spinner<Double> spPrecio;
    
    @FXML
    private DatePicker dpFecha;
    
    @FXML
    private ComboBox<Clientes> cbxIDF;
    
    private Main principal;
    private ObservableList<Productos> listaProductos;
    private Productos modeloProducto;
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
        configurarSpinner();
        ToggleGroup grupoDisponibilidad = new ToggleGroup();
        rbDisponible.setToggleGroup(grupoDisponibilidad);
        rbAgotado.setToggleGroup(grupoDisponibilidad);
        cargarClientes();
        cargarTablaProductos();
        
        tablaProductos.setOnMouseClicked(eventHandler -> cargarProductoEnTextField());
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
        colIdProducto.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("idLibro"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<Productos, String>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<Productos, String>("autor"));
        colFechaPublicacion.setCellValueFactory(new PropertyValueFactory<Productos, Date>("fechaPublicacion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precio"));
        colDisponibilidad.setCellValueFactory(new PropertyValueFactory<Productos, String>("disponibilidad"));
        colIDF.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("idCliente"));
        
    }
    
    private void configurarSpinner() {
        //tipo Double
        SpinnerValueFactory<Double> valoresDecimales = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 0, 0.1);
        spPrecio.setValueFactory(valoresDecimales);
    }
    
    public void cargarTablaProductos(){
        listaProductos = FXCollections.observableArrayList(listarProductos());
        tablaProductos.setItems(listaProductos);
        tablaProductos.getSelectionModel().selectFirst();
        cargarProductoEnTextField();
    }
    
    public void cargarProductoEnTextField() {
        //tablaProductos -> modelo = textField
        Productos productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
            txtId.setText(String.valueOf(productoSeleccionado.getIdCliente()));
            txtTitulo.setText(productoSeleccionado.getTitulo());
            txtAutor.setText(productoSeleccionado.getAutor());
            dpFecha.setValue(productoSeleccionado.getFechaPublicacion());
            spPrecio.getValueFactory().setValue(productoSeleccionado.getPrecio());
            
            for (Clientes c : cbxIDF.getItems()) {
                if (c.getIdCliente() == productoSeleccionado.getIdCliente()) {
                    cbxIDF.setValue(c);
                    break;
                }
            }
            if (productoSeleccionado.getDisponibilidad().equalsIgnoreCase("Disponible")) {
                //veradero = Disponible
                rbDisponible.setSelected(true);
            } else {
                rbAgotado.setSelected(true);
            }
        }
    }
    
    public ArrayList<Productos> listarProductos(){
        ArrayList<Productos> productos = new ArrayList<>();
        
        try {
            Connection conexion = Conexion.getInstancia().getConexion();
            CallableStatement enunciado = (CallableStatement) Conexion.getInstancia().getConexion().prepareCall("call sp_listarProducto();");
            ResultSet resultado = enunciado.executeQuery();
            while (resultado.next()) {
                productos.add(new Productos(
                                                            resultado.getInt(1),
                                                            resultado.getString(2),
                                                            resultado.getString(3),
                                                            resultado.getDate(4).toLocalDate(),
                                                            resultado.getDouble(5),
                                                            resultado.getString(6),
                                                            resultado.getInt(7)));
            }
            
        } catch (SQLException ex) {
            System.out.println("Error al cargar Productos de MySQL: " + ex.getSQLState());
            ex.printStackTrace();
        }
        return productos;
    }
    
    private Productos obtenerModeloProducto() {
        int codigoProducto = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        Clientes clienteSeleccionado = cbxIDF.getSelectionModel().getSelectedItem();
        int codigoCliente = clienteSeleccionado != null ? clienteSeleccionado.getIdCliente(): 0;
        String disponibilidad = rbDisponible.isSelected() ? "Disponible" : "Agotado";
        Productos producto = new Productos(codigoProducto, titulo, autor, dpFecha.getValue(), spPrecio.getValue(), disponibilidad, codigoCliente);
        return producto;
    }
    
     public ArrayList<Clientes> obtenerModeloCliente(){
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
     private void cargarClientes() {
        ObservableList<Clientes> listaClientes = FXCollections.observableArrayList(obtenerModeloCliente());
        cbxIDF.setItems(listaClientes);
    }
     
    
    public void agregarProducto(){
        modeloProducto = obtenerModeloProducto();
        try {
            CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("call sp_agregarProducto(?,?,?,?,?,?);");
            enunciado.setString(1, modeloProducto.getTitulo());
            enunciado.setString(2, modeloProducto.getAutor());
            enunciado.setDate(3, Date.valueOf(modeloProducto.getFechaPublicacion()));
            enunciado.setDouble(4, modeloProducto.getPrecio());
            enunciado.setString(5, modeloProducto.getDisponibilidad());
            enunciado.setInt(6, modeloProducto.getIdCliente());
            enunciado.execute();
            cargarTablaProductos();
        } catch (SQLException ex) {
            System.out.println("Error al agregar");
            ex.printStackTrace();
        }
    }
    
    public void actualizarProducto(){
        modeloProducto = obtenerModeloProducto();
        try {
            CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("call sp_editarProducto(?,?,?,?,?,?,?);");
            enunciado.setInt(1, modeloProducto.getIdLibro());
            enunciado.setString(2, modeloProducto.getTitulo());
            enunciado.setString(3, modeloProducto.getAutor());
            enunciado.setDate(4, Date.valueOf(modeloProducto.getFechaPublicacion()));
            enunciado.setDouble(5, modeloProducto.getPrecio());
            enunciado.setString(6, modeloProducto.getDisponibilidad());
            enunciado.setInt(7, modeloProducto.getIdCliente());
            enunciado.execute();
            cargarTablaProductos();
        }catch (SQLException e) {
            System.out.println("Error al editar | actualizar");
            e.printStackTrace();
        }
    }
    
    public void eliminarProducto(){
        modeloProducto = obtenerModeloProducto();
        try{
            CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("call sp_eliminarCliente(?);");
            enunciado.setInt(1, modeloProducto.getIdLibro());
            enunciado.execute();
            cargarTablaProductos();
        } catch(SQLException e) {
            System.out.println("Error al Eliminar Producto: ");
            e.printStackTrace();
        }
    }
    
    public void limpiarTextField(){
        txtId.clear();
        txtTitulo.clear();
        txtAutor.clear();
        dpFecha.setValue(null);
        configurarSpinner();
        cbxIDF.getSelectionModel().clearSelection();
        
    }
    
    private void cambiarEstado(boolean estado){
        txtTitulo.setDisable(estado);
        txtAutor.setDisable(estado);
        spPrecio.setDisable(estado);
        dpFecha.setDisable(estado);
        cbxIDF.setDisable(estado);
        rbDisponible.setDisable(estado);
        rbAgotado.setDisable(estado);
        btnGuardar.setDisable(estado);
        btnCancelar.setDisable(estado);
    }
    
    private void habilitarDeshabilitarNodo(){
        boolean desactivado = txtTitulo.isDisable();
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
        int indice = tablaProductos.getSelectionModel().getSelectedIndex();
        if (indice > 0) {
            tablaProductos.getSelectionModel().select(indice-1);
            cargarProductoEnTextField();
        }
    }
    
    @FXML
    private void btnSiguienteAction(){
        int indice = tablaProductos.getSelectionModel().getSelectedIndex();
        if (indice < listaProductos.size()-1) {
            tablaProductos.getSelectionModel().select(indice+1);
            cargarProductoEnTextField();
        }
    }
    
    @FXML
    private void btnNuevoAction(){
        limpiarTextField();
        txtTitulo.requestFocus();
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
        eliminarProducto();
    }
    
    @FXML
    private void btnCancelarAction(){
        cargarProductoEnTextField();
        habilitarDeshabilitarNodo();
    }
    
    @FXML
    private void btnGuardarAction(){
        if (tipoDeAccion == acciones.AGREGAR) {
            agregarProducto();
            tipoDeAccion = acciones.NINGUNA;
        }else if (tipoDeAccion == acciones.ACTUALIZAR) {
            actualizarProducto();
            tipoDeAccion = acciones.NINGUNA;
        }
        habilitarDeshabilitarNodo();
    }
    
    @FXML
    private void buscarCliente(){
        ArrayList<Productos> resultadoBusqueda = new ArrayList<>();
        String nombre = txtBuscar.getText();
        for (Productos producto : listaProductos){
            if (producto.getTitulo().toLowerCase().contains(nombre.toLowerCase())) {
                resultadoBusqueda.add(producto);
            }
        }
        tablaProductos.setItems(FXCollections.observableArrayList(resultadoBusqueda));
        if (!resultadoBusqueda.isEmpty()) {
            tablaProductos.getSelectionModel().selectFirst();
        }
        
    } 
    
}