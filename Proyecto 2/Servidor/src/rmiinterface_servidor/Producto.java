/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiinterface_servidor;

import java.io.Serializable;

/**
 *
 * @author LENOVO PC
 */
public class Producto implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private long precio;
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getPrecio() {
        return precio;
    }

    public void setPrecio(long precio) {
        this.precio = precio;
    }
    
    
    
}
