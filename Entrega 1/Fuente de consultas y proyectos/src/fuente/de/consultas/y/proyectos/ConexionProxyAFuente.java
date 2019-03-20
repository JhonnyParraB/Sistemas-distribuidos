/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuente.de.consultas.y.proyectos;

import ClasesdeComunicacion.Voto;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO PC
 */
public class ConexionProxyAFuente extends Thread{
    private Socket socket;
    @Override
    public void run() {
        ObjectInputStream in;
        try {
            while (true){
                in = new ObjectInputStream(socket.getInputStream());              
                Voto voto = (Voto) in.readObject();  
                contarVoto (voto);
                (new ConexionFuenteAProxy(socket, "Confirmacion voto", voto)).start();
            }
        } catch (Exception ex) {
            System.out.println ("Se cayó uno de los proxys");
            FuenteDeConsultasYProyectos.eliminarProxyCaido(socket);
            for (Socket socket: FuenteDeConsultasYProyectos.getSockets() ){
                (new ConexionFuenteAProxy(socket, "Reconexion proxys")).start();
            }
            System.out.println ("Solucionado");
            //Logger.getLogger(ConexionFuenteAProxy.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public ConexionProxyAFuente(Socket socket) {
        this.socket = socket;
    }
    
    private synchronized void contarVoto (Voto voto){
        FuenteDeConsultasYProyectos.contarVoto(voto);
    }
    
    
    
}
