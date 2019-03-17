/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejador.de.proxys;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Inicia los hilos que manejan los proxies
 */
public class ManejadorDeProxys {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException {
        // TODO code application logic here
        RegistroProxy registradorProxies = new RegistroProxy ();
        RegistroCliente registradorClientes = new RegistroCliente();
        
        registradorClientes.start();
        registradorProxies.start();  
        System.out.println("Manejador de proxies iniciado en la dirección IP "+
                InetAddress.getLocalHost().getHostAddress());  
    }
    
}
