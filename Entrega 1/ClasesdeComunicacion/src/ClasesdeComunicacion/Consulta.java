/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasesdeComunicacion;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author green
 */
public class Consulta implements Serializable{
    private Date fecha;
    private String nombre;
    private int IDFuente;

    public Consulta(Date fecha, String nombre, int IDFuente) {
        this.fecha = fecha;
        this.nombre = nombre;
        this.IDFuente = IDFuente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIDFuente() {
        return IDFuente;
    }

    public void setIDFuente(int IDFuente) {
        this.IDFuente = IDFuente;
    }

    @Override
    public boolean equals(Object o) {
        Date fecha = ((Consulta) o).getFecha();
        String nombre = ((Consulta) o).getNombre();
        int IDFuente = ((Consulta) o).getIDFuente();
        if ( this.fecha.equals(fecha) && this.nombre.equals(nombre) && this.IDFuente == IDFuente)
            return true;
        return false;
    }
    
    
    
    
    
    
    
}
