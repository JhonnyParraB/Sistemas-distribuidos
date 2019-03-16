/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

/**
 *
 * @author LENOVO PC
 */
public class Proxy {
    
    static ServerSocket servidor;
    public final int puerto = 6000;
    static int conexionesActuales = 0;
    static Socket tabla [] = new Socket [500];
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ServerSocket socket;
        try {
            socket = new ServerSocket(6000);
            Socket socket_cli = socket.accept();

            DataInputStream in = new DataInputStream(socket_cli.getInputStream());
            do {
                String mensaje = "";
                mensaje = in.readUTF();
                System.out.println(mensaje);
            } while (true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
    
    /*
        Realiza una conexión con el manejador para registrarse como proxy disponible    
    */
    private static void conectarConManejador(){
        
    }
}
