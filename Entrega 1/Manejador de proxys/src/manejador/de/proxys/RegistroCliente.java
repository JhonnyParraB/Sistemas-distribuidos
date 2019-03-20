/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejador.de.proxys;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import ClasesdeComunicacion.Proxy;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hilo que escucha espera nuevos clientes/usuarios Asigna un proxy del
 * directorio al nuevo cliente Valida el ID del cliente
 *
 * @author LENOVO PC
 */
public class RegistroCliente extends Thread {

    private static List<Integer> clientes;

    public RegistroCliente() {
        clientes = new ArrayList<Integer>();
    }

    public void run() {
        try {
            ManejadorDeProxys.quemarUsuarios();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RegistroCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        ServerSocket socket;
        try {
            socket = new ServerSocket(5999);
            do {
                Socket socket_cli = socket.accept();
                ObjectOutputStream out = new ObjectOutputStream(socket_cli.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket_cli.getInputStream());
                String mensaje = (String) in.readObject();
                if (mensaje.equals("Conexion")) {
                    int ID;
                    ID = (Integer) in.readObject();
                    String pass = (String) in.readObject();

                    if (!clientes.contains(ID) &&
                            ManejadorDeProxys.getUsuariosSistema().get(ID).equals(pass)) {
                        out.writeObject("El ID es valido\nBienvenido!");
                        System.out.println("Nuevo cliente conectado: " + ID);
                        clientes.add(ID);
                        ClasesdeComunicacion.Proxy proxy = RegistroProxy.mejorProxy();
                        out.writeObject(proxy);
                    } else {
                        System.out.println("Conexion de nuevo cliente rechazada: " + ID);
                        out.writeObject("La contraseña es incorrecta o el usuario ya está conectado");
                    }
                    socket_cli.close();
                }
                if (mensaje.equals("Reconexion")){
                    Proxy proxyEliminado = (Proxy) in.readObject();           
                    eliminarProxy(proxyEliminado);
                    ClasesdeComunicacion.Proxy proxy = RegistroProxy.mejorProxy();
                    out.writeObject(proxy);
                    socket_cli.close();
                }
            } while (true);
        } catch (Exception ex) {
            Logger.getLogger(RegistroCliente.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }
    
    private static synchronized void eliminarProxy(Proxy proxy){
        RegistroProxy.eliminarProxy(proxy);
    }

}
