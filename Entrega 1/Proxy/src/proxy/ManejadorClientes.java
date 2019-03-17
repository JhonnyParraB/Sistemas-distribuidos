/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Crea hilos para cada uno de los clientes que hacen conexión con el proxy
 * @author LENOVO PC
 */
public class ManejadorClientes extends Thread{

    @Override
    public void run() {
        try {
            ServerSocket socket = new ServerSocket(Proxy.getPuerto());
            do{
                Socket socket_cli = socket.accept();
                ConexionCliente conexionCliente = new ConexionCliente (socket_cli);
                conexionCliente.start();                
            }while(true);
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        
    }
    
}
