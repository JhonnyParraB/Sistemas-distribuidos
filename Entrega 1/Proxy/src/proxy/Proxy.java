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

/**
 *
 * @author LENOVO PC
 */
public class Proxy {
    
   
    
    private ServerSocket servidor;
    private int puerto = 6000;
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
        Envia la información del puerto y de la IP
        El puerto está quemado
    */
    private static void conectarConManejador(){
        Scanner reader = new Scanner (System.in);
        BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
        String ipManejador;
        
        System.out.println ("--Creacion del proxy--");
        System.out.println ("Ingrese la IP del manejador de proxies (directorio):");
        
        ipManejador = reader.nextLine ();
        
        Socket socket;
        byte [] mensaje_bytes = new byte [256];
        String mensaje = "";
        
        
        try{
            socket = new Socket (ipManejador, 6000);            
            DataOutputStream out = new DataOutputStream (socket.getOutputStream());
            do{
                mensaje =in.readLine();
                out.writeUTF(mensaje);
            }while (!mensaje.startsWith("fin"));
        
        }catch(Exception e){
            System.err.println(e.getMessage());
            System.out.println("Es posible que no haya un directorio de proxies en la IP indicada");
            System.exit(1);
        }
        
        
        
        //COLOCAR TAMBIÉN EL PUERTO?
        
    }
}
