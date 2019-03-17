/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejador.de.proxys;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
    private List <Integer> clientes;

    public RegistroCliente() {
        clientes = new ArrayList <Integer>();
    }
    
    public void run() {
        ServerSocket socket;
        try {
            socket = new ServerSocket(5999);           
            do{
                    Socket socket_cli = socket.accept();                
                    DataOutputStream out = new DataOutputStream (socket_cli.getOutputStream());
                    DataInputStream in = new DataInputStream (socket_cli.getInputStream());
                    int ID;
                    ID = in.readInt();
                    System.out.println(ID);
                    
                    if (!clientes.contains(ID)){
                        out.writeUTF ("El ID es valido\nBienvenido!");
                        clientes.add(ID);
                    }
                    else
                        out.writeUTF ("El ID esta siendo usado por otro usuario");                            
                    
                    socket_cli.close();
            }while (true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
    
    
    
}
