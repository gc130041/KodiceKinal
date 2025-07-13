
package org.olivercontreras.model;

import java.util.Date;

/**
 *
 * @author KODICEKINAL S.A.
 */
public class Productos {
    
    private int idLibro;
    private String titulo;
    private String autor;
    private Date fechaPublicacion;
    private double precio;
    private String disponibilidad;
    private int idCliente;

    public Productos() {
    }

    public Productos(int idLibro, String titulo, String autor, Date fechaPublicacion, double precio, String disponibilidad, int idCliente) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.fechaPublicacion = fechaPublicacion;
        this.precio = precio;
        this.disponibilidad = disponibilidad;
        this.idCliente = idCliente;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return idLibro + "  |   " + titulo;
    }
    
}
