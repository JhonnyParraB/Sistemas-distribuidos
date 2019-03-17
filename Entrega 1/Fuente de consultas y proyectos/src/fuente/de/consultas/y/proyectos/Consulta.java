/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuente.de.consultas.y.proyectos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author green
 */
public class Consulta extends Thread {

    List<Proyecto> proyectos;

    public Consulta(List<Proyecto> ejemploLista) {
        this.proyectos = ejemploLista;
    }
    
    public Consulta() {
        this.proyectos = new ArrayList <Proyecto>();
    }

    public void evaluarTiempos() {
        Date actual=java.util.Calendar.getInstance().getTime();
        System.out.println(actual.toString());
        for (Proyecto proy : this.proyectos) {
            //imprimimos el objeto pivote
            if (proy.getFecha().before(actual)){
                System.out.println("Ya paso"+proy.getNombre());
            }
        }
    }

    @Override
    public void run() {
        this.evaluarTiempos();
    }

}
