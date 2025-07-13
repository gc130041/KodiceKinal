
package org.olivercontreras.model;

import java.util.Date;

/**
 *
 * @author KODICEKINAL S.A.
 */
public class Clientes {
    
    private int idCliente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private String email;
    private Date fechaIngreso;

    public Clientes() {
    }

    public Clientes(int idCliente, String nombre, String apellido, String telefono, String direccion, String email, Date fechaIngreso) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
        this.fechaIngreso = fechaIngreso;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public String toString() {
        return idCliente + "    |   " + nombre + apellido;
    }
    
}
