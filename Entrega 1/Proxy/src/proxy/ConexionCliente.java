/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Maneja en un hilo las solicitudes de un unico cliente
 *
 * @author LENOVO PC
 */
public class ConexionCliente extends Thread {

    private Socket socket;

    public ConexionCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String mensaje;
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            do {

                mensaje = "";
                mensaje = in.readUTF();

                //Solicitud de proyectos y voto
                if (mensaje.equals("1")) {

                }
                //Desconexión
                if (mensaje.equals("2")) {

                }

            } while (true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

}
