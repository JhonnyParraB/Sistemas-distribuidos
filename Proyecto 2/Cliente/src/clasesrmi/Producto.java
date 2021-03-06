/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesrmi;

import java.io.Serializable;

/**
 *
 * @author LENOVO PC
 */
public class Producto implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private String tipo;
    private long precio;
    private int cantidad;

    public Producto(String nombre, String tipo, long precio, int cantidad) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.cantidad = cantidad;
    }
    
    //ESTE CONSTRUCTOR GENERA UNA COPIA DE UN PRODUCTO CON LA ESCRITURA DE UN PEDIDO
    public Producto(Producto producto, int cantidadPedida){
        this.nombre = producto.getNombre();
        this.tipo = producto.getTipo();
        this.precio = producto.getPrecio();
        this.cantidad = producto.getCantidad() - cantidadPedida;
  
    }

    public Producto() {
    }
    
    
    
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.nombre.equals(((Producto) obj).getNombre()) && this.tipo.equals(((Producto) obj).getTipo()) && this.precio == ((Producto) obj).getPrecio() ){
            return true;
        }
        else{
            return false;
        }
    }
    
    
    
    
}
