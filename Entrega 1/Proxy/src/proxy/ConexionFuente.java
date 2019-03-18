/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author green
 */
public class ConexionFuente extends Thread{
    
    private Socket socket;

    public ConexionFuente(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        String mensaje;
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            
            out.writeObject("1");
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
