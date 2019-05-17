/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinador;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import rmiinterface_banco.RMIInterfaceBanco;
import rmiinterface.RMIInterfaceCoordinador;

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

        System.err.println("Registrando un usuario ...");
        return look_up_banco.registrarUsuario(nombre_usuario, contrasena);
    }
    
    

    public static void main(String[] args){

        try {

            Naming.rebind("//127.0.0.1/Coordinador", new Coordinador());            
            System.out.println("Coordinador preparado");
            look_up_banco = (RMIInterfaceBanco) Naming.lookup("//127.0.0.1/Banco");

        } catch (Exception e) {

            System.out.println("Error al iniciar el coordinador: " + e.toString());
            e.printStackTrace();

        }

    }

}