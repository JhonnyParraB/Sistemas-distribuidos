/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author green
 */
public class ManejadorFuentes extends Thread{
    private static List<Socket> sockets;
    private static Map <Integer, Socket> mapaFuentes;

    

    public ManejadorFuentes() {
        sockets = new ArrayList<Socket>();
        mapaFuentes = new HashMap<Integer, Socket>();
    }
    
    
    @Override
    public void run() {
        try {
            ServerSocket socket = new ServerSocket(Proxy.getPuertoFuentes());
            do{
                Socket socket_fue = socket.accept();
                sockets.add(socket_fue);
                ConexionFuenteAProxy conexionFuente = new ConexionFuenteAProxy(socket_fue);
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
    
    static void agregarFuente(Integer ID, Socket socket) {
        mapaFuentes.put(ID, socket);
    }

    
    
    public static Socket getSocketFuente (Integer IDFuente){
        return (Socket)mapaFuentes.get(IDFuente);
    }
    
    
    
    
    
    
}
