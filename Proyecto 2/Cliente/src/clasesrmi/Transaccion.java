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

public class Transaccion {
    
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
    
    
    
}


