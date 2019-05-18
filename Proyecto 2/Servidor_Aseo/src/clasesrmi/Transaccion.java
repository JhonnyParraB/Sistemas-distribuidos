/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesrmi;

/**
 *
 * @author green
 */



import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import clasesrmi.Producto;
import java.io.Serializable;

public class Transaccion implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Date tiempoInicio;
    private Date tiempoFinal;
    private List<Producto> conjuntoLectura;
    private List<Producto> conjuntoEscritura;
    


    public Transaccion() {
        conjuntoLectura = new ArrayList<Producto>();
        conjuntoEscritura = new ArrayList<Producto>();
        this.tiempoInicio = new Date();
    }

    public Date getTiempoInicio() {
        return tiempoInicio;
    }

    public void setTiempoInicio(Date tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public Date getTiempoFinal() {
        return tiempoFinal;
    }

    public void setTiempoFinal(Date tiempoFinal) {
        this.tiempoFinal = tiempoFinal;
    }
    
    public void agregarLectura (Producto producto){
        this.conjuntoLectura.add(producto);
    }
    public void agregarEscritura (Producto producto){
        this.conjuntoEscritura.add(producto);
    }

    public List<Producto> getConjuntoLectura() {
        return conjuntoLectura;
    }

    public void setConjuntoLectura(List<Producto> conjuntoLectura) {
        this.conjuntoLectura = conjuntoLectura;
    }

    public List<Producto> getConjuntoEscritura() {
        return conjuntoEscritura;
    }

    public void setConjuntoEscritura(List<Producto> conjuntoEscritura) {
        this.conjuntoEscritura = conjuntoEscritura;
    }
    
    public void modificarEscritura (Producto producto ){
        int i;
        for (i = 0; i<this.conjuntoEscritura.size(); i++){
            if (producto.getNombre().equals(conjuntoEscritura.get(i).getNombre()))
                break;
        }
        conjuntoEscritura.remove(i);        
        agregarEscritura(producto);
        
    }
    
    public void eliminarEscrituraYLectura (Producto producto){
        int i;
        for (i = 0; i<this.conjuntoEscritura.size(); i++){
            if (producto.getNombre().equals(conjuntoEscritura.get(i).getNombre()))
                break;
        }
        conjuntoEscritura.remove(i); 
        
        for (i = 0; i<this.conjuntoLectura.size(); i++){
            if (producto.getNombre().equals(conjuntoLectura.get(i).getNombre()))
                break;
        }
        conjuntoLectura.remove(i); 
    }
    
    public void consumarTransaccion (){
        tiempoFinal = new Date();
    }
    
    
    
    
    
}


