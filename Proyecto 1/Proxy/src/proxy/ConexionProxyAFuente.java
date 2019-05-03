/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import ClasesdeComunicacion.Voto;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO PC
 */
public class ConexionProxyAFuente extends Thread {

    private Socket socket;
    private String tipoMensaje;
    private Voto voto;

    @Override
    public void run() {
        if (tipoMensaje.equals("Envio voto")) {
            ObjectOutputStream out;
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(voto);
            } catch (Exception ex) {
                Logger.getLogger(ConexionFuenteAProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ConexionProxyAFuente(Socket socket, String tipoMensaje, Voto voto) {
        this.socket = socket;
        this.tipoMensaje = tipoMensaje;
        this.voto = voto;
    }

}
