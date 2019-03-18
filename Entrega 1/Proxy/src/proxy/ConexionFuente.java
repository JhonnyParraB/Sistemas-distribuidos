/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import ClasesdeComunicacion.Consulta;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author green
 */
public class ConexionFuente extends Thread{
    
    private Socket socket;
    private List<ClasesdeComunicacion.Consulta> consultas;

    public ConexionFuente(Socket socket) {
        this.socket = socket;
        consultas = new ArrayList<ClasesdeComunicacion.Consulta>();
    }
    
    @Override
    public void run() {
        String mensaje;
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            
            out.writeObject("1");
            consultas = (List<ClasesdeComunicacion.Consulta>) in.readObject();  
            for (ClasesdeComunicacion.Consulta consulta: consultas){
                System.out.println (consulta.getNombre());
            }
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public List<Consulta> getConsultas() {
        return consultas;
    }
    
    
    
}
