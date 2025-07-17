
package org.olivercontreras.model;

import java.time.LocalDate;

/**
 *
 * @author KODICEKINAL S.A.
 */
public class Compras {
    
    private int idCompra;
    private int idCliente;
    private int idLibro;
    private LocalDate fechaCompra;
    private String puntuacion;

    public Compras() {
    }

    public Compras(int idCompra, int idCliente, int idLibro, LocalDate fechaCompra, String puntuacion) {
        this.idCompra = idCompra;
        this.idCliente = idCliente;
        this.idLibro = idLibro;
        this.fechaCompra = fechaCompra;
        this.puntuacion = puntuacion;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    public String toString() {
        return idCompra + "   |   " + puntuacion;
    }
    
    
    
}
