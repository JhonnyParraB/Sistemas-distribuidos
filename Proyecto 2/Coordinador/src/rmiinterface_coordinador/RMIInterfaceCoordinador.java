/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiinterface_coordinador;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author green
 */
public interface RMIInterfaceCoordinador extends Remote{
    
    public int sumar(int a, int b) throws RemoteException;
    public long registrarUsuarioBanco(String nombre_usuario, String contrasena) throws RemoteException;
    
}
