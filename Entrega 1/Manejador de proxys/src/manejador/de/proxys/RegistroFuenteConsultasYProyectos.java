/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejador.de.proxys;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Hilo que escucha espera nuevas fuentes de consultas y proyectos
 * Envía la información de los proxies disponibles a las fuentes de consultas
 * @author LENOVO PC
 */
public class RegistroFuenteConsultasYProyectos extends Thread {
    private static List <Integer> fuentes;

    public RegistroFuenteConsultasYProyectos() {
        fuentes = new ArrayList <Integer>();
    }
    
    public void run() {
        ServerSocket socket;
        try {
            socket = new ServerSocket(5998);           
            do{
                    Socket socket_cli = socket.accept();                
                    ObjectOutputStream out = new ObjectOutputStream (socket_cli.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream (socket_cli.getInputStream());
                    
                    int ID;
                    ID = (Integer)in.readObject();
                    System.out.println(ID);
                    
                    if (!fuentes.contains(ID)){
                        out.writeObject ("El ID es valido\nBienvenido!");
                        System.out.println("Nueva fuente conectada: "+ ID);
                        fuentes.add(ID);
                        out.writeObject(RegistroProxy.getDirectorio());
                    }
                    else{
                        System.out.println("Conexion de nueva fuente rechazada: "+ ID);
                        out.writeObject ("El ID esta siendo usado por otra fuente");        
                    }
                    
                    socket_cli.close();
            }while (true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
