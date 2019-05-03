/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import ClasesdeComunicacion.Consulta;
import ClasesdeComunicacion.Voto;
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
public class ConexionProxyACliente extends Thread{
    
    private Socket socket;
    private String tipoMensaje;
    private Voto voto;

    @Override
    public void run() {
         //To change body of generated methods, choose Tools | Templates.
        
        if (tipoMensaje.equals("Confirmacion voto")){
            try {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject("El voto se ha enviado correctamente");
            } catch (Exception ex) {
                Logger.getLogger(ConexionProxyACliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }


    
    public ConexionProxyACliente(Socket socket, String tipoMensaje) {
        this.socket = socket;
        this.tipoMensaje = tipoMensaje;
    }
    
    
}
