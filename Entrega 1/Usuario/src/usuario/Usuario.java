/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuario;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author LENOVO PC
 */
public class Usuario {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
        
        Socket socket;
        byte [] mensaje_bytes = new byte [256];
        
        String mensaje = "";
        
        try{
            socket = new Socket ("127.0.0.1", 6000);
            
            DataOutputStream out = new DataOutputStream (socket.getOutputStream());
            
            do{
                mensaje =in.readLine();
                out.writeUTF(mensaje);
            }while (!mensaje.startsWith("fin"));
        
        }catch(Exception e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
        
    }
    
}
