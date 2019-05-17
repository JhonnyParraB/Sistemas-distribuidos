/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinador;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import rmiinterfacebanco.RMIInterfaceBanco;
import rmiinterface.RMIInterfaceCoordinador;

public class Coordinador extends UnicastRemoteObject implements RMIInterfaceCoordinador{

    private static final long serialVersionUID = 1L;
    private static RMIInterfaceBanco look_up;

    protected Coordinador() throws RemoteException {

        super();

    }

    @Override
    public int sumar(int a, int b) throws RemoteException{

        System.err.println("Haciendo suma");
        return a+b;
    }
    
    @Override
    public boolean registrarUsuarioBanco(String contrasena) throws RemoteException{

        System.err.println("Registrando un usuario ...");
        if (look_up.registrarUsuario(contrasena)){
            return true;
        }
        else{
            return false;
        }
    }
    
    

    public static void main(String[] args){

        try {

            Naming.rebind("//127.0.0.1/Coordinador", new Coordinador());            
            System.err.println("Coordinador preparado");
            look_up = (RMIInterfaceBanco) Naming.lookup("//127.0.0.1/Banco");

        } catch (Exception e) {

            System.err.println("Error al iniciar el coordinador: " + e.toString());
            e.printStackTrace();

        }

    }

}