
package org.olivercontreras.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author KODICEKINAL S.A.
 */
public class Conexion {
    
    private static Conexion instancia;
    private Connection conexion;
    
    //DATOS SOLAMENTE UTILIZADOS PARA PRUEBA, REEMPLAZAR AL MOMENTO DE EJECUTAR
    private static final String URL = "jdbc:mysql://localhost:3306/DBlibroskinal?userSSL=false";
                                    //jdbc:mysql://localhost:3306/?user=root
    private static final String USER = "root";
    private static final String PASSWORD = "H4rp4l1K3";
    
    
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    // Constructor
    public Conexion() {
        conectar();
    }
    
    // Configurar
    public void conectar(){
        try{
            Class.forName(DRIVER).newInstance();
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion exitosa!");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException|SQLException ex){
            System.out.println("Error en la conexion");            
        }        
    }
    
    // get Instancia
    public static Conexion getInstancia() {
        if (instancia == null){
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConexion() {
        try{
            if (conexion == null || conexion.isClosed()){
                conectar();
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar");
            e.getMessage();
        }
        return conexion;
    }
    
}
