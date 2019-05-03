/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ClasesdeComunicacion.*;
import java.util.ArrayList;

/**
 * Maneja en un hilo las solicitudes de un unico cliente
 *
 * @author LENOVO PC
 */
public class ConexionClienteAProxy extends Thread {

    private Socket socket;
    private int ID;
    private List<ClasesdeComunicacion.Consulta> consultas = new ArrayList <ClasesdeComunicacion.Consulta>();

    public ConexionClienteAProxy(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String mensaje;
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            do {            
                mensaje = "";
                mensaje = (String) in.readObject();
                if (mensaje.equals("0")){
                    ID = (Integer) in.readObject();
                    agregarCliente();
                }
                if (mensaje.equals("1")){
                    int ID = (Integer) in.readObject();
                    out.writeObject(Proxy.consultasParaUsuario(ID));
                }

                if (mensaje.equals("2")) {                          
                    int ID = (Integer) in.readObject();
                    List <Consulta> consultas = Proxy.consultasParaUsuario(ID);
                    out.writeObject(consultas);
                    if (!consultas.isEmpty()){
                        Voto voto = (Voto) in.readObject();
                        Socket socketFuente = ManejadorFuentes.getSocketFuente(voto.getConsulta().getIDFuente());
                        new ConexionProxyAFuente(socketFuente, "Envio voto", voto).start();   
                    }
                }
                if (mensaje.equals("3")) {

                }
            } while (true);
        } catch (Exception ex) {
            Logger.getLogger(ConexionClienteAProxy.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private synchronized void agregarCliente (){
        ManejadorClientes.agregarCliente(ID, socket);
        System.out.println ("Binding de cliente: "+ ID);
    }

}
