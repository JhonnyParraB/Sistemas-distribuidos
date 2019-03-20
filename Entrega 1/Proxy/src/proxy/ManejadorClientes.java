/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Crea hilos para cada uno de los clientes que hacen conexión con el proxy
 * @author LENOVO PC
 */
public class ManejadorClientes extends Thread{
    private static List<Socket> sockets;
    private static Map <Integer, Socket> mapaClientes;
    
     public ManejadorClientes() {
        sockets = new ArrayList<Socket>();
        mapaClientes = new HashMap<Integer, Socket>();
    }
    
    @Override
    public void run() {
        try {
            ServerSocket socket = new ServerSocket(Proxy.getPuertoClientes());
            do{
                Socket socket_cli = socket.accept();
                ConexionClienteAProxy conexionCliente = new ConexionClienteAProxy (socket_cli);
                conexionCliente.start();                
            }while(true);
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println("AQUI");
            System.exit(1);
        }
        
    }
    
    public static void agregarCliente (int ID, Socket socket){
        mapaClientes.put(ID, socket);
    }
    
    public static Socket getSocketCliente (Integer IDCliente){
        return (Socket)mapaClientes.get(IDCliente);
    }
    
}
