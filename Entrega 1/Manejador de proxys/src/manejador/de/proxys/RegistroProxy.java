/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejador.de.proxys;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
    private static List <Proxy> directorio;
    private ServerSocket servidor;

    public RegistroProxy() {
        this.directorio = new ArrayList <Proxy>();
        
    }

    public void run() {
        ServerSocket socket;
        try {
            socket = new ServerSocket(6000);           
            String IP="";
            String puerto="";
            do{
                    Socket socket_cli = socket.accept();
                
                    DataOutputStream out = new DataOutputStream (socket_cli.getOutputStream());
                    DataInputStream in = new DataInputStream (socket_cli.getInputStream());
                    IP = in.readUTF();
                    System.out.println(IP);
                    puerto = in.readUTF();
                    System.out.println(puerto);
                    Proxy proxy = new Proxy();
                    proxy.setIP(IP);
                    proxy.setPuerto(Integer.parseInt(puerto));
                    directorio.add(proxy);
                    
                    out.writeUTF ("Proxy preparado!"); 
                    
                    socket_cli.close();
            }while (true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public static List<manejador.de.proxys.Proxy> getDirectorio() {
        return directorio;
    }
    
    public static Proxy mejorProxy(){
        int min = 1000000;
        Proxy mejor = null;
        for (Proxy proxy: directorio ){
            if (proxy.getNumeroClientes()<=min){
                min = proxy.getNumeroClientes();      
                mejor = proxy;
            }
        }
        mejor.adicionarCliente();
        return mejor;
    }   
}
