/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinador;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import rmiinterface_banco.RMIInterfaceBanco;
import rmiinterface_coordinador.RMIInterfaceCoordinador;

public class Coordinador extends UnicastRemoteObject implements RMIInterfaceCoordinador{

    private static final long serialVersionUID = 1L;
    private static RMIInterfaceBanco look_up_banco;

    protected Coordinador() throws RemoteException {

        super();

    }

    @Override
    public int sumar(int a, int b) throws RemoteException{

        System.err.println("Haciendo suma");
        return a+b;
    }
    
    @Override
    public long registrarUsuarioBanco(String nombre_usuario, String contrasena) throws RemoteException{

        System.out.println("Registrando un usuario ...");
        return 5;
        //return look_up_banco.registrarUsuario(nombre_usuario, contrasena);
    }
    
    

    public static void main(String[] args){

        try {
            
            Registry registryClientes = LocateRegistry.createRegistry(1234);
            registryClientes.rebind("//127.0.0.1/Coordinador", new Coordinador());
            
            Registry registryBanco = LocateRegistry.getRegistry(1235);
            
            System.out.println("Coordinador preparado");
            look_up_banco = (RMIInterfaceBanco) registryBanco.lookup("//127.0.0.1/Banco");

        } catch (Exception e) {

            System.out.println("Error al iniciar el coordinador: " + e.toString());
            e.printStackTrace();

        }

    }

}