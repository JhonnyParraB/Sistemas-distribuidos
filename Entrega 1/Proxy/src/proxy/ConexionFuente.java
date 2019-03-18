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
public class ConexionFuente extends Thread {

    private Socket socket;
    List<ClasesdeComunicacion.Consulta> consultas;

    public ConexionFuente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        
        ObjectOutputStream out;
        ObjectInputStream in;
        try {
            while (true){
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                consultas = (List<ClasesdeComunicacion.Consulta>) in.readObject();
                agregarConsultas();
                /*for(ClasesdeComunicacion.Consulta consulta : consultas){
                    System.out.println(consulta.getNombre());
                }*/
            }
        } catch (Exception ex) {
            Logger.getLogger(ConexionFuente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private synchronized void agregarConsultas(){
        Proxy.agregarConsultas(consultas);
    }

}
