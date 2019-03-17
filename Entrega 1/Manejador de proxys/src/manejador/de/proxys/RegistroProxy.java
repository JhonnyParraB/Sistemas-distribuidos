/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejador.de.proxys;

import java.io.DataInputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Hilo que se encarga de ir registrando los proxys
 * Registra la IP y el puerto del proxy
 * Lo agrega al directorio de proxys
 * Se envian nuevamente los proxys a las fuentes
 * @author LENOVO PC
 */
public class RegistroProxy extends Thread{
    private List <Proxy> proxies;
    private ServerSocket servidor;

    public RegistroProxy() {
        this.proxies = new ArrayList <Proxy>();
        
    }

    public void run() {
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
    
    
    
    
    
    
}
