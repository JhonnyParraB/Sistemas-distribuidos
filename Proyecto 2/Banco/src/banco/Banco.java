/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author green
 */
public class Banco extends UnicastRemoteObject implements RMIInterfaceBanco {

    int ids = 0;
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
            System.err.println("Banco preparado");

        } catch (Exception e) {

            System.err.println("Error al iniciar el coordinador: " + e.toString());
            e.printStackTrace();

        }
    }

    @Override
    public boolean registrarUsuario(String contrasena) throws RemoteException {
        
        long saldo;
        ids++;
        numeroTarjetas++;
        saldo = 50000;
        
        return true; 
        //escribir archivo
        
        
    }

}
