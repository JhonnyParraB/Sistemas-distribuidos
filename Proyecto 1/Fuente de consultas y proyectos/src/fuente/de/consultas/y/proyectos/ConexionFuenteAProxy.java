/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuente.de.consultas.y.proyectos;

import ClasesdeComunicacion.Consulta;
import ClasesdeComunicacion.Voto;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author green
 */
public class ConexionFuenteAProxy extends Thread{
    
    private Socket socket;
    private String tipoMensaje;
    private List<ClasesdeComunicacion.Consulta> consultas;
    private Voto voto;

    @Override
    public void run() {
         //To change body of generated methods, choose Tools | Templates.
        
        if (tipoMensaje.equals("Envio ID")){
            
            try {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject("Envio ID");
                out.writeObject(FuenteDeConsultasYProyectos.getID());
            } catch (Exception ex) {
                Logger.getLogger(ConexionFuenteAProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (tipoMensaje.equals("Envio consultas")){
            try {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject("Envio consultas");
                out.writeObject(consultas);

            } catch (Exception ex) {
                Logger.getLogger(ConexionFuenteAProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (tipoMensaje.equals("Confirmacion voto")){
            try {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject("Voto recibido");
                out.writeObject(voto);
            } catch (Exception ex) {
                Logger.getLogger(ConexionFuenteAProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (tipoMensaje.equals("Reconexion proxys")){
            try {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject("Reconexion proxys");
                out.writeObject(FuenteDeConsultasYProyectos.getVotosConsultas());
            } catch (Exception ex) {
                Logger.getLogger(ConexionFuenteAProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    public ConexionFuenteAProxy(Socket socket, String tipoMensaje, List<ClasesdeComunicacion.Consulta> consultas) {
        this.socket = socket;
        this.tipoMensaje = tipoMensaje;
        this.consultas = consultas;
    }
    
    public ConexionFuenteAProxy(Socket socket, String tipoMensaje) {
        this.socket = socket;
        this.tipoMensaje = tipoMensaje;
    }
    
    public ConexionFuenteAProxy(Socket socket, String tipoMensaje, Voto voto) {
        this.socket = socket;
        this.tipoMensaje = tipoMensaje;
        this.voto = voto;
    }
    
    
}
