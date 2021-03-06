/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuente.de.consultas.y.proyectos;

import ClasesdeComunicacion.Consulta;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.Socket;
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
public class InyectorConsultas extends Thread {
    
    private static String nombreArchivo;

    public static List<ClasesdeComunicacion.Consulta> leer() {

        List<ClasesdeComunicacion.Consulta> consultas = new ArrayList<ClasesdeComunicacion.Consulta>();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(nombreArchivo);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
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
        List<ClasesdeComunicacion.Consulta> consultas = InyectorConsultas.leer();
        List<ClasesdeComunicacion.Consulta> consultasNuevasYAntiguas;
        List<ClasesdeComunicacion.Consulta> consultasNuevas;
        List<ClasesdeComunicacion.Consulta> consultasAntiguas;
        while (true) {
            consultasNuevasYAntiguas = separarConsultas(consultas);  
            consultasAntiguas = FuenteDeConsultasYProyectos.getConsultas();         

            FuenteDeConsultasYProyectos.setConsultas(consultasNuevasYAntiguas);
            consultasNuevas = (List<Consulta>) ((ArrayList) consultasNuevasYAntiguas).clone();
            consultasNuevas.removeAll(consultasAntiguas);
            
            
            

            if (!consultasNuevas.isEmpty()){
                agregarConteoYVotos(consultasNuevas);
                List <Socket> sockets = FuenteDeConsultasYProyectos.getSockets();
                for (Socket socket : sockets){
                    ConexionFuenteAProxy conexionProxy = new ConexionFuenteAProxy(socket, "Envio consultas", consultasNuevas);
                    conexionProxy.start();
                }
            }
            try {
                Thread.sleep(60000); 
            } catch (InterruptedException ex) {
                Logger.getLogger(InyectorConsultas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//To change body of generated methods, choose Tools | Templates.
    }
    private synchronized void agregarConteoYVotos(List<ClasesdeComunicacion.Consulta> consultasNuevas){
        FuenteDeConsultasYProyectos.agregarConteoYVotos(consultasNuevas);        
    }

        public InyectorConsultas(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
}
