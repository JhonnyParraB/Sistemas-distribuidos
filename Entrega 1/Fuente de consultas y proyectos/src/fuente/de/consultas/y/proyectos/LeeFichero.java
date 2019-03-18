/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuente.de.consultas.y.proyectos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author green
 */
public class LeeFichero extends Thread {

    public static List<ClasesdeComunicacion.Consulta> leer() {

        List<ClasesdeComunicacion.Consulta> consultas = new ArrayList<ClasesdeComunicacion.Consulta>();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("Consultas.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            System.out.println("Leyendo el contendio del archivo.txt");
            String linea;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split("-");
                Date fecha = formatter.parse(parts[0]);
                fecha = formatter.parse(parts[0]);
                String nombre = parts[1];
                ClasesdeComunicacion.Consulta consulta = new ClasesdeComunicacion.Consulta(fecha, nombre, FuenteDeConsultasYProyectos.getID());
                consultas.add(consulta);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return consultas;
    }

    public static boolean evaluarTiempos(Date fecha) {
        Date actual = java.util.Calendar.getInstance().getTime();
        if (fecha.before(actual)) {
            System.out.println("Ya paso");
            return true;
        } else {
            return false;
        }
    }

    public static List<ClasesdeComunicacion.Consulta> separarConsultas(List<ClasesdeComunicacion.Consulta> consultas) {
        List<ClasesdeComunicacion.Consulta> consultasFiltradas = new ArrayList<ClasesdeComunicacion.Consulta>();
        for (ClasesdeComunicacion.Consulta consulta : consultas) {
            if (evaluarTiempos(consulta.getFecha())) {
                consultasFiltradas.add(consulta);
            }
        }
        return consultasFiltradas;
    }

    @Override
    public void run() {
        List<ClasesdeComunicacion.Consulta> consultas = LeeFichero.leer();
        while (true) {
            consultas = separarConsultas(consultas);
            FuenteDeConsultasYProyectos.setConsultas(consultas);
            try {
                Thread.sleep(60000);
            } catch (InterruptedException ex) {
                Logger.getLogger(LeeFichero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//To change body of generated methods, choose Tools | Templates.
    }

}
