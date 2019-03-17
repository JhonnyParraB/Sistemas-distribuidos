/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO PC
 */
public class Proxy {
    
   
    
    private static ServerSocket Pservidor;
    private static int puertoManejador = 6000;
    private static int conexionesActuales = 0;
    private static String puerto;
    private static Socket tabla [] = new Socket [500];
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        conectarConManejador();
    }
    
    /*
        Realiza una conexión con el manejador para registrarse como proxy disponible
        Envia la información del puerto y de la IP
        El puerto está quemado
    */
    private static void conectarConManejador(){
        Scanner reader = new Scanner (System.in);
        String ipManejador;
        
        System.out.println ("--Creacion del proxy--");
        System.out.println ("Ingrese la IP del manejador de proxies (directorio):");
             
        ipManejador = reader.nextLine ();
        
        System.out.println ("Ingrese el puerto en el que se comunicara el proxy:");
        puerto = reader.nextLine ();
        
        Socket socket;
        String mensaje = "";       
        
        try{
            socket = new Socket (ipManejador, puertoManejador);      
            
            DataOutputStream out = new DataOutputStream (socket.getOutputStream());
            DataInputStream in = new DataInputStream (socket.getInputStream());
            
            
            mensaje = InetAddress.getLocalHost().getHostAddress().toString();
            out.writeUTF(mensaje);
            mensaje = String.valueOf(puerto);
            out.writeUTF(mensaje);
            
            mensaje = in.readUTF();
            System.out.println (mensaje);   
            socket.close();
                    
        }catch(Exception e){
            System.err.println(e.getMessage());
            System.out.println("Es posible que no haya un directorio de proxies en la IP indicada");
            System.exit(1);
        }
        
        
       
        
    }
    
    private static void inicializarProxy (){
        try {
            Pservidor = new ServerSocket(Integer.parseInt(puerto));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
    
}