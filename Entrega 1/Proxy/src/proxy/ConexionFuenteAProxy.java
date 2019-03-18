/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import ClasesdeComunicacion.Consulta;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ID = (Integer) in.readObject();
            System.out.println("ID recibido: "+ID);
            agregarFuente();
            while (true){
                in = new ObjectInputStream(socket.getInputStream());  
                consultas = (List<ClasesdeComunicacion.Consulta>) in.readObject();
                for (Consulta consulta: consultas){
                    System.out.println(consulta.getNombre());
                }
                agregarConsultas();
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

}
