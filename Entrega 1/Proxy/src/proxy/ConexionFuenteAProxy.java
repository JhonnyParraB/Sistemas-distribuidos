/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import ClasesdeComunicacion.Consulta;
import ClasesdeComunicacion.Voto;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author green
 */
public class ConexionFuenteAProxy extends Thread {

    private Socket socket;
    private int ID;
    List<ClasesdeComunicacion.Consulta> consultas;

    public ConexionFuenteAProxy(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
            
        try {
            
            while (true){
                
                String mensaje ="";
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                
                mensaje = (String) in.readObject();
                if(mensaje.equals("Envio ID")){
                    ID = (Integer) in.readObject();
                    agregarFuente();
                }
                
                if(mensaje.equals("Envio consultas")){
                    consultas = (List<ClasesdeComunicacion.Consulta>) in.readObject();
                    agregarConsultas();
                }
                if(mensaje.equals("Reconexion proxys")){
                    Map <String, List<Integer>> votosConsultasReconexion = new HashMap <String, List<Integer>>();
                    votosConsultasReconexion = (Map<String, List<Integer>>) in.readObject();
                    agregarVotosConsultasReconexion(votosConsultasReconexion);
                }
                if(mensaje.equals("Voto recibido")){
                    Voto voto = (Voto) in.readObject();
                    agregarVoto(voto);
                    (new ConexionProxyACliente(socket, "Confirmacion voto")).start();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ConexionFuenteAProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private synchronized void agregarConsultas(){
        Proxy.agregarConsultas(consultas);
    }
    private synchronized void agregarFuente(){
        ManejadorFuentes.agregarFuente(ID, socket);
    }
    private synchronized void agregarVotosConsultasReconexion (Map<String, List<Integer>> votosConsultasReconexion){
        Proxy.agregarVotosConsultasReconexion(votosConsultasReconexion);
    }
    private synchronized void agregarVoto (Voto voto){
        Proxy.agregarVoto(voto);
    }

}
