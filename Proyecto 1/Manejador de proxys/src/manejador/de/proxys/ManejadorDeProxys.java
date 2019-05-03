/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejador.de.proxys;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Inicia los hilos que manejan los proxies
 */
public class ManejadorDeProxys {
    
    private static Map <Integer, String> usuariosSistema = new HashMap <Integer, String>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, NoSuchAlgorithmException {
        // TODO code application logic here
        RegistroProxy registradorProxies = new RegistroProxy ();
        RegistroCliente registradorClientes = new RegistroCliente();
        RegistroFuenteConsultasYProyectos registradorFuentes = new RegistroFuenteConsultasYProyectos();
        registradorClientes.start();
        registradorProxies.start();
        registradorFuentes.start();
        System.out.println("Manejador de proxys iniciado en la dirección IP "+
                InetAddress.getLocalHost().getHostAddress());  
    }
    
       
    public static Map<Integer, String> getUsuariosSistema() {
        return usuariosSistema;
    }
    
    public static void leerInformacionUsuarios() {

        List<ClasesdeComunicacion.Consulta> consultas = new ArrayList<ClasesdeComunicacion.Consulta>();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("Usuarios.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split("-");
                String ID = parts[0];
                String contrasena = parts[1];
                usuariosSistema.put(Integer.parseInt(ID), contrasena);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
