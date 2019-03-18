/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ClasesdeComunicacion.*;
import java.util.ArrayList;

/**
 * Maneja en un hilo las solicitudes de un unico cliente
 *
 * @author LENOVO PC
 */
public class ConexionClienteAProxy extends Thread {

    private Socket socket;
    private List<ClasesdeComunicacion.Consulta> consultas = new ArrayList <ClasesdeComunicacion.Consulta>();

    public ConexionClienteAProxy(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String mensaje;
        try {
            
            do {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            
                mensaje = "";
                mensaje = (String) in.readObject();

                if (mensaje.equals("1")) {                
                    out.writeObject(Proxy.getConsultas());
                    List<ClasesdeComunicacion.Voto> votos;
                    votos = (List<ClasesdeComunicacion.Voto>) in.readObject();
                    
                    for (Voto voto: votos){
                        Socket socketFuente = ManejadorFuentes.getSocketFuente(voto.getConsulta().getIDFuente());
                        new ConexionProxyAFuente(socketFuente, "Envio voto", voto).start();
                    }
                }
                //Desconexión
                if (mensaje.equals("2")) {

                }

            } while (true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

}
