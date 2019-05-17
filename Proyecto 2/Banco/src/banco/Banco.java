/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import rmiinterface.RMIInterfaceBanco;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author green
 */
public class Banco extends UnicastRemoteObject implements RMIInterfaceBanco {

    long numeroTarjetas = 10000000;
    /**
     * @param args the command line arguments
     */
    protected Banco() throws RemoteException {

        super();

    }

    public static void main(String[] args) {
        // TODO code application logic here
        try {

            Naming.rebind("//127.0.0.1/Banco", new Banco());
            System.out.println("Banco preparado");

        } catch (Exception e) {

            System.out.println("Error al iniciar el banco: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public long registrarUsuario(String nombre_usuario, String contrasena) throws RemoteException {
        
        System.out.println("Asignando número de tarjeta a un nuevo usuario");    
        return 10000; 
        //escribir archivo
        
        
    }

}
