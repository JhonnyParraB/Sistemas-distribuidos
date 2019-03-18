/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author green
 */
public class ManejadorFuentes extends Thread{
    private static List<Socket> sockets;

    public ManejadorFuentes() {
        this.sockets = new ArrayList<Socket>();
    }
    
    
    @Override
    public void run() {
        try {
            ServerSocket socket = new ServerSocket(Proxy.getPuertoFuentes());
            do{
                Socket socket_fue = socket.accept();
                sockets.add(socket_fue);
                ConexionFuente conexionFuente = new ConexionFuente(socket_fue);
                conexionFuente.start();
                
            }while(true);
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println("AQUI");
            System.exit(1);
        }
        
    }

    public static List<Socket> getSockets() {
        return sockets;
    }
    
    
}
