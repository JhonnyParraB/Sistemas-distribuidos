/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuario;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author LENOVO PC
 */
public class Usuario {
    
    private static int puertoManejador = 5999;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        solicitarConexión();
    }
    
    /**
     * Se valida que la ID sea valida y no este repetida ya
     */   
    private static void solicitarConexión (){
        Scanner reader = new Scanner (System.in);
        int ID;
        String ipManejador;
        System.out.println ("Ingrese la IP del manejador de proxies (directorio):");
        ipManejador = reader.nextLine ();
        
        System.out.println("Para acceder al sistema, por favor, ingrese su ID:");
        ID = reader.nextInt();
        
        Socket socket;
        
        try{
            socket = new Socket (ipManejador, puertoManejador);      
            
            DataOutputStream out = new DataOutputStream (socket.getOutputStream());
            DataInputStream in = new DataInputStream (socket.getInputStream());
            
           
            out.writeInt(ID);
                  
            String mensaje = in.readUTF();
            if (mensaje.equals("El ID es valido\nBienvenido!"))
                System.out.println (mensaje);  
            else{
                System.out.println (mensaje);
                System.exit(1);
            }
            socket.close();
                    
        }catch(Exception e){
            System.err.println(e.getMessage());
            System.out.println("Es posible que no haya un directorio de proxies en la IP indicada");
            System.exit(1);
        }
                        
        
        
        
    }
    
}
