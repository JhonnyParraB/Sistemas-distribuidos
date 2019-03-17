/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author green
 */
public class ManejadorFuentes extends Thread{
    
    @Override
    public void run() {
        try {
            ServerSocket socket = new ServerSocket(Proxy.getPuertoFuentes());
            do{
                Socket socket_fue = socket.accept();
                ConexionFuente conexionfuente = new ConexionFuente (socket_fue);
                conexionfuente.start();                
            }while(true);
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println("AQUI");
            System.exit(1);
        }
        
    }
}
