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
public class ConexionCliente extends Thread {

    private Socket socket;
    private List<ClasesdeComunicacion.Consulta> consultas = new ArrayList <ClasesdeComunicacion.Consulta>();

    public ConexionCliente(Socket socket) {
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
                    List<Socket> socketsFuente = ManejadorFuentes.getSockets();
                    List<ConexionFuente> conexionesFuente = new ArrayList <ConexionFuente>();
                    for (Socket socket : socketsFuente){
                        ConexionFuente conexionFuente = new ConexionFuente(socket);
                        conexionesFuente.add(conexionFuente);
                        conexionFuente.start();
                    }
                    
                    //Sincroniza todos los hilos
                    for (ConexionFuente conexionFuente: conexionesFuente){
                        conexionFuente.join();
                    }
                    //Extrae la información de cada hilo
                    List <ClasesdeComunicacion.Consulta> consultas = new ArrayList <ClasesdeComunicacion.Consulta>();;
                    for (ConexionFuente conexionFuente: conexionesFuente){
                        consultas.addAll(conexionFuente.getConsultas());
                    }
                    out.writeObject(consultas);
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
