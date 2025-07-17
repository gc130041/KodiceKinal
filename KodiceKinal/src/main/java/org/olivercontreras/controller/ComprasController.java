package org.olivercontreras.controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import org.olivercontreras.db.Conexion;
import org.olivercontreras.model.Clientes;
import org.olivercontreras.model.Compras;
import org.olivercontreras.model.Productos;
import org.olivercontreras.system.Main;

/**
 * FXML Controller class
 *
 * @author KODICEKINAL S.A.
 */
public class ComprasController implements Initializable {

    @FXML
    private TableView<Compras> tablaCompras;
    @FXML
    private TableColumn colIdCompra, colIdCliente, colIdLibro, colFecha, colPuntuacion;
    @FXML
    private TextField txtIdCompra, txtBuscar;
    @FXML
    private ComboBox<Clientes> cbxCliente;
    @FXML
    private ComboBox<Productos> cbxLibro;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private RadioButton rbP1, rbP2, rbP3, rbP4, rbP5;
    @FXML
    private ToggleGroup grupoPuntuacion;
    @FXML
    private Button btnNuevo, btnEditar, btnEliminar, btnGuardar, btnCancelar, btnRegresar, btnAnterior, btnSiguiente;

    private Main principal;
    private ObservableList<Compras> listaCompras;
    private ObservableList<Clientes> listaClientes;
    private ObservableList<Productos> listaProductos;
    private Compras modeloCompra;

    private enum acciones {
        AGREGAR, ACTUALIZAR, NINGUNA
    }
    private acciones tipoDeAccion = acciones.NINGUNA;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        configurarControles();
        cargarClientes();
        cargarProductos();
        cargarTablaCompras();
        tablaCompras.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cargarCompraEnFormulario();
            }
        });
    }

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    private void configurarControles() {
        grupoPuntuacion = new ToggleGroup();
        rbP1.setToggleGroup(grupoPuntuacion);
        rbP2.setToggleGroup(grupoPuntuacion);
        rbP3.setToggleGroup(grupoPuntuacion);
        rbP4.setToggleGroup(grupoPuntuacion);
        rbP5.setToggleGroup(grupoPuntuacion);
    }

    private void configurarColumnas() {
        colIdCompra.setCellValueFactory(new PropertyValueFactory<>("idCompra"));
        colIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colIdLibro.setCellValueFactory(new PropertyValueFactory<>("idLibro"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaCompra"));
        colPuntuacion.setCellValueFactory(new PropertyValueFactory<>("puntuacion"));
    }

    private void cargarTablaCompras() {
        listaCompras = FXCollections.observableArrayList(listarCompras());
        tablaCompras.setItems(listaCompras);
        if (!listaCompras.isEmpty()) {
            tablaCompras.getSelectionModel().selectFirst();
        }
    }

    private ArrayList<Compras> listarCompras() {
        ArrayList<Compras> compras = new ArrayList<>();
        try {
            CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("call sp_listarcompras()");
            ResultSet resultado = enunciado.executeQuery();
            while (resultado.next()) {
                compras.add(new Compras(
                        resultado.getInt("idCompra"),
                        resultado.getInt("idCliente"),
                        resultado.getInt("idLibro"),
                        resultado.getDate("fechaCompra").toLocalDate(),
                        resultado.getString("puntuacion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return compras;
    }

    private void cargarClientes() {
        listaClientes = FXCollections.observableArrayList(listarClientes());
        cbxCliente.setItems(listaClientes);
    }

    private ArrayList<Clientes> listarClientes() {
        ArrayList<Clientes> clientes = new ArrayList<>();
        try {
            CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("call sp_listarcliente()");
            ResultSet resultado = enunciado.executeQuery();
            while (resultado.next()) {
                clientes.add(new Clientes(resultado.getInt(1), resultado.getString(2), resultado.getString(3), resultado.getString(4), resultado.getString(5), resultado.getString(6), resultado.getDate(7).toLocalDate()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    private void cargarProductos() {
        listaProductos = FXCollections.observableArrayList(listarProductos());
        cbxLibro.setItems(listaProductos);
    }

    private ArrayList<Productos> listarProductos() {
        ArrayList<Productos> productos = new ArrayList<>();
        try {
            CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("call sp_listarproducto()");
            ResultSet resultado = enunciado.executeQuery();
            while (resultado.next()) {
                productos.add(new Productos(resultado.getInt(1), resultado.getString(2), resultado.getString(3), resultado.getDate(4).toLocalDate(), resultado.getDouble(5), resultado.getString(6), resultado.getInt(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    private void cargarCompraEnFormulario() {
        Compras compraSeleccionada = tablaCompras.getSelectionModel().getSelectedItem();
        if (compraSeleccionada != null) {
            txtIdCompra.setText(String.valueOf(compraSeleccionada.getIdCompra()));
            dpFecha.setValue(compraSeleccionada.getFechaCompra());

            for (Clientes c : cbxCliente.getItems()) {
                if (c.getIdCliente() == compraSeleccionada.getIdCliente()) {
                    cbxCliente.setValue(c);
                    break;
                }
            }

            for (Productos p : cbxLibro.getItems()) {
                if (p.getIdLibro() == compraSeleccionada.getIdLibro()) {
                    cbxLibro.setValue(p);
                    break;
                }
            }

            if (compraSeleccionada.getPuntuacion() != null) {
                switch (compraSeleccionada.getPuntuacion()) {
                    case "1":
                        rbP1.setSelected(true);
                        break;
                    case "2":
                        rbP2.setSelected(true);
                        break;
                    case "3":
                        rbP3.setSelected(true);
                        break;
                    case "4":
                        rbP4.setSelected(true);
                        break;
                    case "5":
                        rbP5.setSelected(true);
                        break;
                    default:
                        grupoPuntuacion.selectToggle(null);
                        break;
                }
            } else {
                grupoPuntuacion.selectToggle(null);
            }
        }
    }

    private Compras obtenerModeloCompra() {
        int id = txtIdCompra.getText().isEmpty() ? 0 : Integer.parseInt(txtIdCompra.getText());
        int idCliente = cbxCliente.getSelectionModel().getSelectedItem().getIdCliente();
        int idLibro = cbxLibro.getSelectionModel().getSelectedItem().getIdLibro();
        LocalDate fecha = dpFecha.getValue();
        String puntuacion = null;
        if (rbP1.isSelected()) {
            puntuacion = "1";
        } else if (rbP2.isSelected()) {
            puntuacion = "2";
        } else if (rbP3.isSelected()) {
            puntuacion = "3";
        } else if (rbP4.isSelected()) {
            puntuacion = "4";
        } else if (rbP5.isSelected()) {
            puntuacion = "5";
        }

        return new Compras(id, idCliente, idLibro, fecha, puntuacion);
    }

    private void agregarCompra() {
        modeloCompra = obtenerModeloCompra();
        try {
            CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("call sp_agregarcompra(?, ?, ?, ?)");
            enunciado.setInt(1, modeloCompra.getIdCliente());
            enunciado.setInt(2, modeloCompra.getIdLibro());
            enunciado.setDate(3, Date.valueOf(modeloCompra.getFechaCompra()));
            enunciado.setString(4, modeloCompra.getPuntuacion());
            enunciado.execute();
            cargarTablaCompras();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void actualizarCompra() {
        modeloCompra = obtenerModeloCompra();
        try {
            CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("call sp_editarcompra(?, ?, ?, ?, ?)");
            enunciado.setInt(1, modeloCompra.getIdCompra());
            enunciado.setInt(2, modeloCompra.getIdCliente());
            enunciado.setInt(3, modeloCompra.getIdLibro());
            enunciado.setDate(4, Date.valueOf(modeloCompra.getFechaCompra()));
            enunciado.setString(5, modeloCompra.getPuntuacion());
            enunciado.execute();
            cargarTablaCompras();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void eliminarCompra() {
        modeloCompra = tablaCompras.getSelectionModel().getSelectedItem();
        try {
            CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("call sp_eliminarcompra(?)");
            enunciado.setInt(1, modeloCompra.getIdCompra());
            enunciado.execute();
            cargarTablaCompras();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void limpiarFormulario() {
        txtIdCompra.clear();
        cbxCliente.getSelectionModel().clearSelection();
        cbxLibro.getSelectionModel().clearSelection();
        dpFecha.setValue(null);
        grupoPuntuacion.selectToggle(null);
    }

    private void habilitarDeshabilitarFormulario(boolean estado) {
        cbxCliente.setDisable(estado);
        cbxLibro.setDisable(estado);
        dpFecha.setDisable(estado);
        rbP1.setDisable(estado);
        rbP2.setDisable(estado);
        rbP3.setDisable(estado);
        rbP4.setDisable(estado);
        rbP5.setDisable(estado);

        btnGuardar.setDisable(estado);
        btnCancelar.setDisable(estado);

        btnNuevo.setDisable(!estado);
        btnEditar.setDisable(!estado);
        btnEliminar.setDisable(!estado);
        btnAnterior.setDisable(!estado);
        btnSiguiente.setDisable(!estado);
    }

    @FXML
    private void buscarCompraAction() {
        ArrayList<Compras> resultadoBusqueda = new ArrayList<>();
        String textoId = txtBuscar.getText();

        try {
            int idBuscada = Integer.parseInt(textoId);

            for (Compras compra : listaCompras) {
                if (compra.getIdCompra() == idBuscada) {
                    resultadoBusqueda.add(compra);
                }
            }
        } catch (NumberFormatException e) {

        }

        tablaCompras.setItems(FXCollections.observableArrayList(resultadoBusqueda));

        if (!resultadoBusqueda.isEmpty()) {
            tablaCompras.getSelectionModel().selectFirst();
        } else if (textoId.isEmpty()) {
            tablaCompras.setItems(listaCompras);
        }
    }

    @FXML
    private void btnRegresarAction(ActionEvent event) {
        principal.pPrincipal();
    }

    @FXML
    private void btnNuevoAction(ActionEvent event) {
        tipoDeAccion = acciones.AGREGAR;
        limpiarFormulario();
        habilitarDeshabilitarFormulario(false);
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        tipoDeAccion = acciones.ACTUALIZAR;
        habilitarDeshabilitarFormulario(false);
    }

    @FXML
    private void btnEliminarAction(ActionEvent event) {
        eliminarCompra();
        limpiarFormulario();
    }

    @FXML
    private void btnGuardarAction(ActionEvent event) {
        if (tipoDeAccion == acciones.AGREGAR) {
            agregarCompra();
        } else if (tipoDeAccion == acciones.ACTUALIZAR) {
            actualizarCompra();
        }
        tipoDeAccion = acciones.NINGUNA;
        habilitarDeshabilitarFormulario(true);
    }

    @FXML
    private void btnCancelarAction(ActionEvent event) {
        tipoDeAccion = acciones.NINGUNA;
        cargarCompraEnFormulario();
        habilitarDeshabilitarFormulario(true);
    }

    @FXML
    private void btnAnteriorAction(ActionEvent event) {
        int indice = tablaCompras.getSelectionModel().getSelectedIndex();
        if (indice > 0) {
            tablaCompras.getSelectionModel().select(indice - 1);
        }
    }

    @FXML
    private void btnSiguienteAction(ActionEvent event) {
        int indice = tablaCompras.getSelectionModel().getSelectedIndex();
        if (indice < listaCompras.size() - 1) {
            tablaCompras.getSelectionModel().select(indice + 1);
        }
    }
}
