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
    private int tipoMensaje;
    private List<ClasesdeComunicacion.Voto> votos;

    public ConexionFuente(Socket socket, int tipoMensaje, List<ClasesdeComunicacion.Voto> votos) {
        this.socket = socket;
        consultas = new ArrayList<ClasesdeComunicacion.Consulta>();
        this.tipoMensaje = tipoMensaje;
        this.votos = votos;
    }
    
    @Override
    public void run() {
        String mensaje;
        if (tipoMensaje == 1){
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
        
        if (tipoMensaje == 2){
            try {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                out.writeObject(votos);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

    }

    public List<Consulta> getConsultas() {
        return consultas;
    }
    
    
    
}
