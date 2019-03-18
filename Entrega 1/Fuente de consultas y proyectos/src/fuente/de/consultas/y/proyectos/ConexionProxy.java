/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuente.de.consultas.y.proyectos;

import ClasesdeComunicacion.Consulta;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author green
 */
public class ConexionProxy extends Thread{
    
    private Socket socket;

    @Override
    public void run() {
         //To change body of generated methods, choose Tools | Templates.
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            String mensaje="";
            do{
                mensaje=(String) in.readObject();
                System.out.println("Llegada mensaje del proxy"+mensaje);
                if (mensaje.equals("1")){
                    List<ClasesdeComunicacion.Consulta> consultas = FuenteDeConsultasYProyectos.getConsultas();
                    out.writeObject(consultas);
                }
            }while(true);
        } catch (Exception ex) {
            Logger.getLogger(ConexionProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ConexionProxy(Socket socket) {
        this.socket = socket;
    }
    
    
    
}
