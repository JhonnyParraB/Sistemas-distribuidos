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
import ClasesdeComunicacion.Proxy;
import java.net.Socket;

/**
 *
 * @author green
 */
public class Fuente extends Thread {

    private List<Consulta> consultas;
    /*private List <ClasesdeComunicacion.Proxy> directorio;
    private static int puertoManejador = 5999;
    private static Socket socket;*/

    public Fuente(List<Consulta> ejemploLista) {
        this.consultas = ejemploLista;
    }
    
    public Fuente() {
        this.consultas = new ArrayList <Consulta>();
    }

    public void evaluarTiempos() {
        Date actual=java.util.Calendar.getInstance().getTime();
        System.out.println(actual.toString());
        for (Consulta proy : this.consultas) {
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

    public List<Consulta> getConsultas() {
        return consultas;
    }

    
}
