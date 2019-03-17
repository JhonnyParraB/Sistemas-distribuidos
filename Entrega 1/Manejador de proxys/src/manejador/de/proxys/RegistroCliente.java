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
 * Hilo que escucha espera nuevos clientes/usuarios
 * Asigna un proxy del directorio al nuevo cliente
 * Valida el ID del cliente
 * @author LENOVO PC
 */
public class RegistroCliente extends Thread{
    private static List <Integer> clientes;

    public RegistroCliente() {
        clientes = new ArrayList <Integer>();
    }
    
    public void run() {
        ServerSocket socket;
        try {
            socket = new ServerSocket(5999);           
            do{
                    Socket socket_cli = socket.accept();                
                    ObjectOutputStream out = new ObjectOutputStream (socket_cli.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream (socket_cli.getInputStream());
                    
                    int ID;
                    ID = (Integer)in.readObject();
                    System.out.println(ID);
                    
                    if (!clientes.contains(ID)){
                        out.writeObject ("El ID es valido\nBienvenido!");
                        Proxy proxy = RegistroProxy.mejorProxy();
                        out.writeObject(proxy);
                    }
                    else
                        out.writeObject ("El ID esta siendo usado por otro usuario");                            
                    
                    socket_cli.close();
            }while (true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

        
    
    
    
    
}
