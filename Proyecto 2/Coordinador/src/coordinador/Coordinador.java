/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinador;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import rmiinterface.RMIInterface;

public class Coordinador extends UnicastRemoteObject implements RMIInterface{

    private static final long serialVersionUID = 1L;

    protected Coordinador() throws RemoteException {

        super();

    }

    @Override
    public int sumar(int a, int b) throws RemoteException{

        System.err.println("Haciendo suma");
        return a+b;
    }

    public static void main(String[] args){

        try {

            Naming.rebind("//127.0.0.1/Coordinador", new Coordinador());            
            System.err.println("Coordinador preparado");

        } catch (Exception e) {

            System.err.println("Error al iniciar el coordinador: " + e.toString());
            e.printStackTrace();

        }

    }

}