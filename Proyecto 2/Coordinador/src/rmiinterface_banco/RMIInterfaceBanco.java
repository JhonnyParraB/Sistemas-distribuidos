/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiinterface_banco;

import rmiinterface.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author green
 */
public interface RMIInterfaceBanco extends Remote{
    
    public long registrarUsuario(String nombre_usuario, String contrasena) throws RemoteException;
    
}
