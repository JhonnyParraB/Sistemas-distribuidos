/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejador.de.proxys;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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
        System.out.println("Manejador de proxies iniciado en la dirección IP "+
                InetAddress.getLocalHost().getHostAddress());  
    }
    
    public static void quemarUsuarios () throws NoSuchAlgorithmException{
        for (int i=1; i<=20; i++){
            String pass = sha1(String.valueOf(i));
            usuariosSistema.put (i, pass);
        }           
    }
    
    static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static Map<Integer, String> getUsuariosSistema() {
        return usuariosSistema;
    }
    
    
}
