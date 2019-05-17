/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import rmiinterface_banco.RMIInterfaceBanco;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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

            Registry registry= LocateRegistry.createRegistry(1235);
            registry.rebind("//127.0.0.1/Banco", new Banco());
            System.out.println("Banco preparado");

        } catch (Exception e) {

            System.out.println("Error al iniciar el banco: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public long registrarUsuario(String nombre_usuario, String contrasena) throws RemoteException {
        
        System.out.println("Asignando número de tarjeta a un nuevo usuario"); 
        long saldo = 50000;
        numeroTarjetas++;
        long numTarjeta = numeroTarjetas;
        escribirArchivo(nombre_usuario, contrasena, numTarjeta, saldo);
        return numTarjeta; 
        
        //escribir archivo
        
        
    }
    
    public void escribirArchivo(String nombre_usuario, String contrasena, long numTarjeta, long saldo){
      FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("RegistroBanco.txt",true);
            pw = new PrintWriter(fichero);
            pw.println(nombre_usuario+" "+contrasena+" "+numTarjeta+" "+saldo);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }

}
